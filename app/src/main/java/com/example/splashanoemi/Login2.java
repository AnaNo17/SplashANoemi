package com.example.splashanoemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.service.BdUser;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Login2 extends AppCompatActivity {

    Button registro;
    private List<MyInfo> list;
    public static String TAG = "Login";
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public static String usr;

    //modificacion de validar
    private EditText pswds, usuario;
    private TextView txtpas, txtnewpass;

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte[] res = null;
    byte[] res2 = null;
    String pass, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        registro = (Button) findViewById(R.id.RegistroB);

        BdUser Usuariobd = new BdUser(Login2.this);
        if(Usuariobd.getUsuarios() == null){
            Toast.makeText(getApplicationContext(), "No hay usuarios registrados", Toast.LENGTH_LONG).show();
        }

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login2.this, Login.class);
                startActivity(registro);
            }
        });
        Button button2 = findViewById(R.id.accesarB);
        usuario = findViewById(R.id.user);

        //modificacion validar
        txtnewpass = findViewById(R.id.newPass);

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
                        res2 = createSha1(String.valueOf(txtnewpass.getText())+"ola");
                        if (res != null) {
                            Log.d(TAG, String.format("%s", bytesToHex(res)));
                            pass = bytesToHex(res);
                            pass2 = bytesToHex(res2);
                        }
                        acceso();
                    } else {
                        Toast.makeText(getApplicationContext(), "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), String.format(String.valueOf(e)), Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf(e));
                }

            }
        });

        //hasta aqui la funcionalidad de botones
    }
    private boolean validar() {
        boolean retorno = true;
        String usuario1, password, newpass;
        usuario1 = usuario.getText().toString();
        password = pswds.getText().toString();
        newpass = txtnewpass.getText().toString();
        if (usuario1.isEmpty()) {
            usuario.setError("Ingrese su usuario");
            retorno = false;
        } else {

        }
        if (password.isEmpty()) {
            txtpas.setError("Ingrese su contraseña");
            retorno = false;
        } else {

        }
        if (newpass.isEmpty()) {
            txtpas.setError("Ingrese su contraseña");
            retorno = false;
        } else {

        }
        return retorno;
    }


    public void acceso() {
        int i = 0;
        BdUser Usuariobd = new BdUser(Login2.this);
        MyInfo myInfo2 = Usuariobd.GetUsuario(usr);
        Intent intent2 = getIntent();
        String contraCorreo = intent2.getStringExtra("Contrasena" );
        Usuariobd.editaContra(myInfo2.getIdUser(), pass2);
        if(myInfo2 == null){
            Toast.makeText(getApplicationContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
        }else{
            if (myInfo2.getUsuario().equals(usr) && contraCorreo.equals(pass)) {
                Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login2.this, Login.class);
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

    }//modificacion validar
}