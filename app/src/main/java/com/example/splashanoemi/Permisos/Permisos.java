package com.example.splashanoemi.Permisos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.Serializable;

public class Permisos implements Serializable {

<<<<<<< HEAD
    public boolean tienePermisoCamara = false, tienePermisoInternet= false;
    public static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_PERMISOS_INTERNET=2;
    public void verificarYPedirPermisosDeInternet(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoDeInternetConcedido(context);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.INTERNET},
                    CODIGO_PERMISOS_INTERNET);
=======
    public boolean tienePermisoCamara = false, tienePermisoCel= false, tienePermisoVibra= false;
    public static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_PERMISOS_CEL=2, CODIGO_PERMISOS_VIBRA=3;
    public void verificarYPedirPermisosCel(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoCelConcedido(context);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CODIGO_PERMISOS_CEL);
>>>>>>> e79ee19e92e7fdbaed078895b45a590700fad7a5
        }
    }
    public void verificarYPedirPermisosDeCamara(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoDeCamaraConcedido(context);
        } else {
<<<<<<< HEAD
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
=======
            // Si no, entonces pedimos permisos
>>>>>>> e79ee19e92e7fdbaed078895b45a590700fad7a5
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }
<<<<<<< HEAD
=======
    public void verificarYPedirPermisosVibra(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoVibraConcedido(context);
        } else {
            // Si no, entonces pedimos permisos
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.VIBRATE},
                    CODIGO_PERMISOS_VIBRA);
        }
    }

>>>>>>> e79ee19e92e7fdbaed078895b45a590700fad7a5
    public void permisoDeCamaraConcedido(Context context) {
        Toast.makeText(context, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCamara = true;
    }

    public void permisoDeCamaraDenegado(Context context) {
        Toast.makeText(context, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }
<<<<<<< HEAD
    public void permisoDeInternetConcedido(Context context) {
        Toast.makeText(context, "El permiso para el internet está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoInternet = true;
    }

    public void permisoDeInternetDenegado(Context context) {
        Toast.makeText(context, "El permiso para el internet está denegado", Toast.LENGTH_SHORT).show();
=======
    public void permisoCelConcedido(Context context) {
        Toast.makeText(context, "El permiso para llamadas está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCel = true;
    }

    public void permisoCelDenegado(Context context) {
        Toast.makeText(context, "El permiso para llamadas está denegado", Toast.LENGTH_SHORT).show();
    }
    public void permisoVibraConcedido(Context context) {
        Toast.makeText(context, "El permiso para que el celular vibre está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoVibra = true;
    }

    public void permisoVibraDenegado(Context context) {
        Toast.makeText(context, "El permiso para que el celular vibre está denegado", Toast.LENGTH_SHORT).show();
>>>>>>> e79ee19e92e7fdbaed078895b45a590700fad7a5
    }
}
