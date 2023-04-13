package com.example.splashanoemi;

import static com.example.splashanoemi.Login.KEY;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.des.MyDesUtil;
import com.example.splashanoemi.service.BdContras;
import com.example.splashanoemi.service.BdUser;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class Registro extends AppCompatActivity {
    Switch SwitchH;
    RadioButton sexoB;
    Button login;
    Button Registro;
    ImageButton Calendario;
    EditText FechaNac;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    List<MyData> lista;
    int []images = { R.drawable.icono1,R.drawable.icono2,R.drawable.icono3};
    private static final String TAG = "Registro";
    public static final String archivo = "archivo.json";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte []res = null;
    String pass, mascotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        lista = null;
        sexoB = (RadioButton)findViewById(R.id.radioBMujer);
        SwitchH= (Switch) findViewById(R.id.idswitch);
        login = (Button)findViewById(R.id.sesion);
        setContentView(R.layout.activity_registro);
        List<MyInfo> list =new ArrayList<MyInfo>();
        Registro = findViewById(R.id.Registro);
        Button Registro = findViewById(R.id.Registro);
        Button login = findViewById(R.id.sesion);
        ImageButton calendario = findViewById(R.id.imageButton);
        RadioButton sexoB = findViewById(R.id.radioBMujer);
        Switch SwitchH = findViewById(R.id.idswitch);
        EditText usuario = findViewById(R.id.TxtUsu);
        EditText pswd = findViewById(R.id.TxtContra);
        EditText mail = findViewById(R.id.txtCorreo);
        EditText edad = findViewById(R.id.txtEdad);
        EditText FechaNac = findViewById(R.id.TxtNac);
        EditText Telefono = findViewById(R.id.TxtTel);
        CheckBox trabajador = findViewById(R.id.checkBoxEsTra);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Registro.this, Login.class);
                startActivity(login);
            }
        });
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String fecha = i+"/"+i1+"/"+i2;
                        FechaNac.setText(fecha);
                    }
                }, anio, mes, dia);
                dpd.show();
            }
        });

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BdContras contrasbd = null;
                contrasbd = new BdContras(getBaseContext());
                BdUser usuariosDB = null;
                usuariosDB = new BdUser( getBaseContext( ) );
                int id = 0;
                if(usuariosDB.getUsuarios() == null){
                    id = 0;
                    Log.d("Id usuario", String.valueOf(id));
                }
                else{
                    id = (usuariosDB.getUsuarios().size());
                    Log.d("Id usuario", String.valueOf(id));
                }
                res = createSha1(String.valueOf(pswd.getText())+"ola");
                if( res != null ) {
                    Log.d(TAG, String.format("%s", bytesToHex(res)));
                    pass = bytesToHex(res);
                }
                boolean estadoRb = sexoB.isChecked();
                String sexo = null;
                if (estadoRb==true){
                    sexo = "Femenino";
                }else{
                    sexo = "Masculino";
                }

                boolean estadoCb = trabajador.isChecked();
                String tipoU = null;
                if(estadoCb==true){
                    tipoU = "Trabajador";
                }else{
                    tipoU = "Estudiante";
                }

                if(SwitchH.isChecked()){
                    mascotas = "Tiene mascotas";
                }else{
                    mascotas = "No tiene mascotas";
                }
                MyInfo info= new MyInfo();
                info.setIdUser(id);
                info.setUsuario(String.valueOf(usuario.getText()));
                info.setPassword(pass);
                info.setCorreo(String.valueOf(mail.getText()));
                info.setEdad(String.valueOf(edad.getText()));
                info.setSexo(sexo);
                info.setTusu(tipoU);
                info.setHijos(mascotas);
                info.setTelefono(String.valueOf(Telefono.getText()));
                info.setFechaNac(String.valueOf(FechaNac.getText()));
                info.setContras(lista);
                //List2Json(info,list);
                if(usuariosDB.saveUsuario(info)){
                    Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void Objet2Json(MyInfo info){
        Gson gson =null;
        String json= null;
        String mensaje = null;
        gson =new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        MyDesUtil myDesUtil = null;
        String json= null;
        String json2= null;
        gson =new Gson();
        myDesUtil = new MyDesUtil();
        list.add(info);
        json2 =gson.toJson(list, ArrayList.class);
        if( isNotNullAndNotEmpty( KEY ) )
        {
            myDesUtil.addStringKeyBase64( KEY );
        }
        json= myDesUtil.cifrar(json2);

        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
    public byte[] createSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public boolean isNotNullAndNotEmpty( String aux )
    {
        return aux != null && aux.length() > 0;
    }
}