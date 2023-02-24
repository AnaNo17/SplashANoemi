package com.example.splashanoemi;

import static com.example.splashanoemi.Registro.archivo;
import static com.example.splashanoemi.olvideContra.KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.des.MyDesUtil;
import com.example.splashanoemi.service.BdContras;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Agregarcontra extends AppCompatActivity {
    public static String TAG = "mensaje";
    private List<MyData> lista;
    Button regiscontra;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private EditText contra2, red;
    private TextView contrasena, red2;

    private int []images = { R.drawable.cerrar,R.drawable.llave,R.drawable.cerrar,R.drawable.llave,
            R.drawable.cerrar,R.drawable.llave,R.drawable.cerrar};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarcontra);
        regiscontra = findViewById(R.id.regiscontra);
        red = findViewById(R.id.red);
        contra2 = findViewById(R.id.nuevacontra);
        contrasena = findViewById(R.id.nuevacontra);
        red2 = findViewById(R.id.red);
        Intent intent = getIntent();
        Object object = null;
        MyInfo info = null;
        List<MyInfo> list =new ArrayList<MyInfo>();
        regiscontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyData myData = null;
                Object object = null;
                MyInfo info = null;
                BdContras contrasbd = null;
                contrasbd = new BdContras(getBaseContext());
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                lista = contrasbd.getContras(info.getIdUser());
                myData = new MyData();
                myData.setIdContra(info.getIdUser());
                myData.setContra(String.valueOf(contrasena.getText()));
                myData.setRed(String.valueOf(red.getText()));
                myData.setImage(images[lista.size()]);
                contrasbd.saveContra(myData);
                lista.add(myData);
                info.setContras(lista);
                //List2Json(info,list);
                Intent intent = new Intent(Agregarcontra.this, Principal.class);
                intent.putExtra("MyInfo", info);
                startActivity(intent);
            }
        });

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
    public boolean isNotNullAndNotEmpty( String aux )
    {
        return aux != null && aux.length() > 0;
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

}