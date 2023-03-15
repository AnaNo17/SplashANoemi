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
        }
    }
    public void verificarYPedirPermisosDeCamara(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoDeCamaraConcedido(context);
        } else {
            // Si no, entonces pedimos permisos
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }
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

    public void permisoDeCamaraConcedido(Context context) {
        Toast.makeText(context, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCamara = true;
    }

    public void permisoDeCamaraDenegado(Context context) {
        Toast.makeText(context, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }
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
    }
}
