package com.example.splashema.contract;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.splashema.Json.MyData;
import com.example.splashema.Json.MyInfo;

import java.io.Serializable;

public class UsuariosContract implements Serializable {
    public static final String TAG = "UsuariosContract";
    public static abstract class UsuarioEntry implements BaseColumns
    {
        public static final String TABLE_USUARIOS = "TABLE_USUARIOS";
        public static final String USUARIO = "USUARIO";
        public static final String PASSWORD= "PASSWORD";
        public static final String CORREO = "CORREO";
        public static final String EDAD= "EDAD";
        public static final String SEXO = "SEXO";
        public static final String TUSU= "TUSU";
        public static final String HIJOS = "HIJOS";
        public static final String FECHANAC= "FECHANAC";
        public static final String TELEFONO= "TELEFONO";
        public static final String getCreateTable( )
    {
        String table = "CREATE TABLE "+TABLE_USUARIOS+ "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO TEXT NOT NULL UNIQUE," +
                "PASSWORD TEXT NOT NULL," +
                "CORREO TEXT NOT NULL," +
                "EDAD TEXT," +
                "SEXO TEXT," +
                "TUSU TEXT," +
                "HIJOS TEXT," +
                "TELEFONO TEXT," +
                "FECHANAC TEXT" +
                ")";
        return table;
    }

        public static ContentValues toContentValues(MyInfo myInfo)
        {
            ContentValues values = new ContentValues();
            values.put(USUARIO, myInfo.getUsuario() );
            values.put(PASSWORD, myInfo.getPassword());
            values.put(CORREO, myInfo.getCorreo());
            values.put(EDAD, myInfo.getEdad());
            values.put(SEXO, myInfo.getSexo());
            values.put(TUSU, myInfo.getTusu());
            values.put(HIJOS, myInfo.getHijos());
            values.put(TELEFONO, myInfo.getTelefono());
            values.put(FECHANAC, myInfo.getFechaNac());
            return values;
        }
    }
    public abstract static class MyDataEntry implements BaseColumns{
        public static final String TABLE_CONTRA = "TABLE_CONTRAS";
        public static final String getCreateTable( )
        {
            String table ="CREATE TABLE "+TABLE_CONTRA+"(" +
                    "id_contra INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "contra TEXT NOT NULL," +
                    "red TEXT NOT NULL," +
                    "idusu INTEGER NOT NULL," +
                    "imagen INTEGER NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues(MyData myData)
        {
            ContentValues values = new ContentValues();
            values.put("contra", myData.getContra());
            values.put("red", myData.getRed());
            values.put("idusu", myData.getIdContra());
            values.put("imagen", myData.getImage());

            return values;
        }
    }
}
