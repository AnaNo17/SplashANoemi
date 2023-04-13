package com.example.splashanoemi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.service.BdContras;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agregarcontra extends AppCompatActivity implements LocationListener{
    public static String TAG = "mensaje";
    private List<MyData> lista;
    Button regiscontra, imagenF;
    ImageView previsualizacion;
    protected ActivityResultLauncher<Intent> someActivityResultLauncher;
    private EditText contra2, red;
    private LocationManager locationManager;
    TextView lat,lon;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private TextView contrasena, red2;
    private int []images = { R.drawable.icono1,R.drawable.icono2,R.drawable.icono3, R.drawable.icono4, R.drawable.icono5,
            R.drawable.icono6, R.drawable.icono7, R.drawable.icono8, R.drawable.icono9, R.drawable.icono10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarcontra);
        regiscontra = findViewById(R.id.regiscontra);
        red = findViewById(R.id.red);
        contra2 = findViewById(R.id.nuevacontra);
        contrasena = findViewById(R.id.nuevacontra);
        red2 = findViewById(R.id.red);
        lat = findViewById(R.id.latitud);
        lon = findViewById(R.id.longitud);
        imagenF = findViewById(R.id.imagenFoto);
        previsualizacion = findViewById(R.id.imageView);
        takePhotoRegister();
        Intent intent = getIntent();
        Object object = null;
        MyInfo info = null;
        startGps();
        List<MyInfo> list =new ArrayList<MyInfo>();
        imagenF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
                someActivityResultLauncher.launch( cameraIntent );
            }
        });
        regiscontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyData myData = null;
                stopGps( );
                Object object = null;
                MyInfo info = null;
                ImageView imageView = findViewById(R.id.imageView);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                BdContras contrasbd = null;
                contrasbd = new BdContras(getBaseContext());
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                lista = contrasbd.getContras(info.getIdUser());
                myData = new MyData();
                myData.setIdContra(info.getIdUser());
                myData.setContra(String.valueOf(contrasena.getText()));
                myData.setRed(String.valueOf(red.getText()));
                if (lista == null){
                    lista = new ArrayList<MyData>(Collections.singleton(myData));
                    myData.setImage(0);
                    myData.setData(byteArray);
                    myData.setLatitud(lat.getText().toString());
                    myData.setLongitud(lon.getText().toString());
                    contrasbd.saveContra(myData);
                    lista.add(myData);
                    info.setContras(lista);
                }else{
                    myData.setImage(images[lista.size()]);
                    myData.setData(byteArray);
                    myData.setLatitud(lat.getText().toString());
                    myData.setLongitud(lon.getText().toString());
                    contrasbd.saveContra(myData);
                    lista.add(myData);
                    info.setContras(lista);
                }

                //List2Json(info,list);
                Intent intent = new Intent(Agregarcontra.this, Principal.class);
                intent.putExtra("MyInfo", info);
                startActivity(intent);
            }
        });

    }
    private void startGps()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION} , 3);
            return;
        }
        if( locationManager == null )
        {
            locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
            lat.setText( "" );
            lon.setText( "" );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 3:
                if( android.Manifest.permission.ACCESS_FINE_LOCATION.equals( permissions[ 0 ]) && grantResults[ 0 ] == 0 )
                {
                    startGps();
                    return;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        lat.setText("" + location.getLatitude( ) );
        lon.setText("" + location.getLongitude( ) );
        if( !regiscontra.isEnabled( ) )
        {
            regiscontra.setEnabled( true );
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider)
    {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }
    private void stopGps( )
    {
        locationManager.removeUpdates((LocationListener) this);
        locationManager = null;
    }

    public void takePhotoRegister( )
    {
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            if( data != null && data.getExtras() != null)
                            {
                                Bitmap photo = (Bitmap) data.getExtras().get( "data" );
                                previsualizacion.setImageBitmap(photo);
                            }
                        }
                    }
                });
    }
    //metodos de fotografia
    public void tomarfoto(View view) {

    }

    public void elegirfoto(View view) {
        cargarImagen();
    }
    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path=data.getData();
            previsualizacion.setImageURI(path);
        }
    }
}