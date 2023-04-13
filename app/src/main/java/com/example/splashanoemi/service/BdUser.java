package com.example.splashanoemi.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.splashanoemi.Json.MyInfo;
import com.example.splashanoemi.contract.UsuariosContract;

import java.util.ArrayList;
import java.util.List;

public class BdUser extends UsuariosDBService{
    Context context;
    public BdUser(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public boolean saveUsuario(MyInfo info){
        boolean correcto = false;
        try{
            UsuariosDBService usuariosDBService = new UsuariosDBService(context);
            SQLiteDatabase db =usuariosDBService.getWritableDatabase();
            ContentValues values= new ContentValues();
            db.insert(TABLE_USUARIOS,null, UsuariosContract.UsuarioEntry.toContentValues(info));
            correcto = true;
        }catch(Exception ex){
            ex.toString();
            correcto = false;
        }
        finally{
            return correcto;
        }
    }
    public List<MyInfo> getUsuarios( )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<MyInfo>usuarios = null;
        MyInfo usuario = null;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM "+TABLE_USUARIOS,null);
        if( cursor == null )
        {
            return null;
        }
        if( cursor.getCount() < 1)
        {
            return null;
        }
        if( !cursor.moveToFirst() )
        {
            return null;
        }
        Log.d(TAG, "TamanoBd" + cursor.getCount());
        usuarios = new ArrayList<MyInfo>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            usuario = new MyInfo( );
            usuario.setIdUser(cursor.getInt(0));
            usuario.setUsuario( cursor.getString( 1 ) );
            usuario.setPassword(cursor.getString(2));
            usuario.setCorreo(cursor.getString(3));
            usuario.setEdad(cursor.getString(4));
            usuario.setSexo(cursor.getString(5));
            usuario.setTusu(cursor.getString(6));
            usuario.setHijos(cursor.getString(7));
            usuario.setTelefono(cursor.getString(8));
            usuario.setFechaNac(cursor.getString(9));
            usuarios.add( usuario );
            cursor.moveToNext( );
        }
        return usuarios;
    }
    public boolean editaContra(int id, String password){
        boolean correcto = false;
        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = getWritableDatabase();
        try{
            sqLiteDatabase.execSQL("UPDATE "+TABLE_USUARIOS+" SET PASSWORD = '"+password+"'WHERE id ='"+id+"'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            sqLiteDatabase.close();
        }
        return correcto;
    }
    public MyInfo GetUsuario(String user){
        MyInfo info = new MyInfo();
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db = usuariosDBService.getReadableDatabase();
        Cursor cursor=null;
        String query = "SELECT * FROM TABLE_USUARIOS WHERE usuario = ?";
        String[] args = {user};

        cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()) {
            info.setIdUser(cursor.getInt(0));
            info.setUsuario( cursor.getString( 1 ) );
            info.setPassword(cursor.getString(2));
            info.setCorreo(cursor.getString(3));
            info.setEdad(cursor.getString(4));
            info.setSexo(cursor.getString(5));
            info.setTusu(cursor.getString(6));
            info.setHijos(cursor.getString(7));
            info.setTelefono(cursor.getString(8));
            info.setFechaNac(cursor.getString(9));
            return info;
        }

        cursor.close();
        return null;
    }
}
