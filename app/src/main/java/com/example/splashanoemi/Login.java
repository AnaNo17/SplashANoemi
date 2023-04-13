package com.example.splashanoemi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.Permisos.Permisos;
import com.example.splashanoemi.des.MyDesUtil;
import com.example.splashanoemi.service.BdUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button registro;
    private List<MyInfo> list;
    public static String TAG = "Login";
    String json = null;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    String json2 = null;
    public static String usr;
    private boolean tienePermisoCel = false, tienePermisoCamara= false, tienePermisoVibra=false;
    private static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_PERMISOS_CEL=2, CODIGO_PERMISOS_VIBRA=3;

    private EditText pswds, usuario;
    private TextView txtpas, txtusu;

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte[] res = null;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Permisos permisos = new Permisos();
        permisos.verificarYPedirPermisosDeCamara(getApplicationContext(),Login.this);
        permisos.verificarYPedirPermisosCel(getApplicationContext(),Login.this);
        permisos.verificarYPedirPermisosVibra(getApplicationContext(),Login.this);

        registro = (Button) findViewById(R.id.RegistroB);
        BdUser Usuariobd = new BdUser(Login.this);
        if(Usuariobd.getUsuarios() == null){
            Toast.makeText(getApplicationContext(), "No hay usuarios registrados", Toast.LENGTH_LONG).show();
        }

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this, Registro.class);
                startActivity(registro);
            }
        });
        Button button2 = findViewById(R.id.accesarB);
        usuario = findViewById(R.id.userNameId);

        //modificacion validar
        txtpas = findViewById(R.id.passwordId);
        txtusu = findViewById(R.id.userNameId);

        pswds = findViewById(R.id.passwordId);
        //Read();
        //json2List(json);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validar()) {
                        usr = String.valueOf(usuario.getText());
                        res = createSha1(String.valueOf(pswds.getText()) + "ola");
                        if (res != null) {
                            Log.d(TAG, String.format("%s", bytesToHex(res)));
                            pass = bytesToHex(res);
                        }
                        acceso();
                    } else {
                        Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), String.format(String.valueOf(e)), Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf(e));
                }

            }
        });
        Button OlvideContra = findViewById(R.id.olvidePassB);
        OlvideContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent olvideContra = new Intent(Login.this, olvideContra.class);
                startActivity(olvideContra);
            }
        });
        //hasta aqui la funcionalidad de botones
    }
    private boolean validar() {
        boolean retorno = true;
        String usuario1, password;
        usuario1 = usuario.getText().toString();
        password = pswds.getText().toString();
        if (usuario1.isEmpty()) {
            txtusu.setError("Ingrese su usuario");
            retorno = false;
        } else {

        }
        if (password.isEmpty()) {
            txtpas.setError("Ingrese su contraseña");
            retorno = false;
        } else {

        }
        return retorno;
    }
    public boolean Read() {
        if (!isFileExits()) {
            return false;
        }
        MyDesUtil myDesUtil = null;
        myDesUtil = new MyDesUtil();
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json2 = new String(bytes);
            Log.d(TAG, json2);
            if( isNotNullAndNotEmpty( KEY ) )
            {
                myDesUtil.addStringKeyBase64( KEY );
            }
            json = myDesUtil.desCifrar(json2);
            if(!json.isEmpty()) {
                Log.d(TAG, json);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isNotNullAndNotEmpty( String aux )
    {
        return aux != null && aux.length() > 0;
    }

    public void json2List(String json) {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0) {
            Toast.makeText(getApplicationContext(), "Error BD null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>() {
        }.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0) {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private File getFile() {//
        return new File(getDataDir(), Registro.archivo);
    }

    private boolean isFileExits() {
        File file = getFile();
        if (file == null) {
            return false;
        }
        return file.isFile() && file.exists();

    }//ojito

    public void acceso() {
        int i = 0;
        BdUser Usuariobd = new BdUser(Login.this);
        MyInfo myInfo2 = Usuariobd.GetUsuario(usr);
        if(myInfo2 == null){
            Toast.makeText(getApplicationContext(), "Error en la BD", Toast.LENGTH_LONG).show();
        }else{
            if (myInfo2.getUsuario().equals(usr) && myInfo2.getPassword().equals(pass)) {
                Toast.makeText(getApplicationContext(), "Inicio de sesión correcto", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, Principal.class);
                intent.putExtra("MyInfo", myInfo2);
                startActivity(intent);
                i = 1;
            }
            if (i == 0) {
                Toast.makeText(getApplicationContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                Log.d(TAG, pass);
            }
        }
    }

    public byte[] createSha1(String text) {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);

    }
    //Para la funcionalidad de los permisos de la App
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permisos permisos = new Permisos();
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisos.permisoDeCamaraConcedido(getApplicationContext());
                } else {
                    permisos.permisoDeCamaraDenegado(getApplicationContext());
                }
                break;

            case CODIGO_PERMISOS_CEL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisos.permisoCelConcedido(getApplicationContext());
                } else {
                    permisos.permisoCelDenegado(getApplicationContext());
                }
                break;
            case CODIGO_PERMISOS_VIBRA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisos.permisoVibraConcedido(getApplicationContext());
                } else {
                    permisos.permisoVibraDenegado(getApplicationContext());
                }
                break;
        }
    }
}