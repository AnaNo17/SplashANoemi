package com.example.splashanoemi;

import static com.example.splashanoemi.Registro.archivo;
import static com.example.splashanoemi.olvideContra.KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.MyAdapter.MyAdapter;
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

public class editacontra extends AppCompatActivity {
    public static String TAG = "mensaje";
    private List<MyData> lista;
    Button editacontra;
    private ListView listView;
    private EditText contra, red;
    private TextView indice;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private int []images = { R.drawable.cerrar,R.drawable.llave,
            R.drawable.cerrar,R.drawable.llave,R.drawable.cerrar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editacontra);
        listView = (ListView) findViewById(R.id.lista2);
        editacontra = findViewById(R.id.editacontra);
        Intent intent = getIntent();
        Object object = null;
        MyInfo info = null;
        int idusu;
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        BdContras contrasbd = null;
        contrasbd = new BdContras(getBaseContext());
        idusu = info.getIdUser();
        lista = contrasbd.getContras(idusu);
        contra = findViewById(R.id.contra);
        red = findViewById(R.id.socialred);
        indice = findViewById(R.id.indice);
        MyAdapter myAdapter = new MyAdapter( lista , getBaseContext() );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast(i);
                contra.setText(lista.get(i).getContra());
                red.setText(lista.get(i).getRed());
                indice.setText(String.format(String.valueOf(i)));
            }

        });
        editacontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = null;
                MyInfo info = null;
                int i = 0;
                BdContras contrasbd = null;
                contrasbd = new BdContras(getBaseContext());
                i= Integer.parseInt(String.valueOf(indice.getText()));
                Log.d(TAG, String.valueOf(i));
                lista.get(i).setContra(String.valueOf(contra.getText()));
                lista.get(i).setRed(String.valueOf(red.getText()));

                if(contrasbd.editaContras((i+1),String.valueOf(contra.getText()),String.valueOf(red.getText()))){
                    Log.d(TAG, "La contraseña ha sido editada");
                }else{
                    Log.d(TAG, "La contraseña no se edito");
                }
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                info.setContras(lista);

                Intent intent2 = new Intent(editacontra.this, Principal.class);
                intent2.putExtra("MyInfo", info);
                startActivity(intent2);
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
            Log.d(TAG, "Error BD");
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
    public int toast(int i)
    {
        return i;
    }
}