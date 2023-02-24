package com.example.splashanoemi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.MyAdapter.MyAdapter;
import com.example.splashanoemi.service.BdContras;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    private TextView usuario;
    Button regresor, api;
    private ListView listView;
    private List<MyData> list;
    public static String TAG = "ola";
    private int []images = { R.drawable.cerrar,R.drawable.llave,R.drawable.cerrar,
            R.drawable.llave, R.drawable.cerrar,R.drawable.llave};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        int idusu = 0;
        String aux = null;
        MyInfo info = null;
        Object object = null;
        MyData myData = null;
        BdContras contrasbd = null;
        contrasbd = new BdContras(getBaseContext());

        usuario = findViewById(R.id.textUser);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewId);
        api= (Button)findViewById(R.id.buttonApi);
        api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent api = new Intent(Principal.this, ApiPlus.class);
                startActivity(api);
            }
        });


        regresor= (Button)findViewById(R.id.salir);
        regresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresador = new Intent(Principal.this, Login.class);
                startActivity(regresador);
            }
        });
        list = new ArrayList<MyData>();
        if( intent != null)
        {
            aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                usuario.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("MyInfo");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        info = (MyInfo) object;
                        usuario.setText("Bienvenid@ " + info.getUsuario());
                        idusu = info.getIdUser();
                        Log.d(TAG, String.valueOf(idusu));
                        list = contrasbd.getContras(idusu);
                        if(list == null){
                            Toast.makeText(getBaseContext(), "No hay contraseñas registradas en la BD", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

        MyAdapter myAdapter = new MyAdapter( list , getBaseContext() );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        MyInfo info = null;
        Object object = null;
        Intent intent = getIntent();
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        switch (item.getItemId() ) {
            case R.id.agregarId:
                Intent olvideContra = new Intent(Principal.this, Agregarcontra.class);
                olvideContra.putExtra("MyInfo", info);
                startActivity(olvideContra);
                break;
            case R.id.elimId:
                Intent elimContra = new Intent(Principal.this, eliminacontra.class);
                elimContra.putExtra("MyInfo", info);
                startActivity(elimContra);
                break;
            case R.id.editarId:
                Intent editaContra = new Intent(Principal.this, editacontra.class);
                editaContra.putExtra("MyInfo", info);
                startActivity(editaContra);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
    private void toast( int i )
    {
        Toast.makeText(getBaseContext(), list.get(i).getContra(), Toast.LENGTH_SHORT).show();
    }

}