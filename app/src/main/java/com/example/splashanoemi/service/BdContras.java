package com.example.splashanoemi.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.contract.UsuariosContract;

import java.util.ArrayList;
import java.util.List;
public class BdContras extends UsuariosDBService{
    Context context;

    public BdContras(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public Boolean saveContra(MyData myData){
        Boolean correcto = false;
        try{
            UsuariosDBService usuariosDBService = new UsuariosDBService(context);
            SQLiteDatabase db =usuariosDBService.getWritableDatabase();
            ContentValues values= new ContentValues();
            db.insert(TABLE_CONTRAS,null, UsuariosContract.MyDataEntry.toContentValues(myData));
            correcto = true;

        }catch(Exception ex){
            ex.toString();
            correcto = false;
        }
        finally{
            return correcto;
        }

    }
    public List<MyData> getContras(int id )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<MyData>contras = null;
        MyData myData = null;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM " + TABLE_CONTRAS +" WHERE idusu = "+id+" "  ,null);
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
        Log.d(TAG, "" + cursor.getCount());
        contras = new ArrayList<MyData>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            myData = new MyData( );
            myData.setId(cursor.getInt(0));
            myData.setContra(cursor.getString(1));
            myData.setRed(cursor.getString(2));
            myData.setIdContra(cursor.getInt(3));
            myData.setImage(cursor.getInt(4));
            contras.add(myData);
            cursor.moveToNext( );
        }
        return contras;
    }
    public boolean editaContras(int id, String contra, String red){
        boolean correcto = false;
        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = getWritableDatabase();
        try{
            sqLiteDatabase.execSQL("UPDATE "+TABLE_CONTRAS+" SET contra = '"+contra+"', red = '"+red+"' WHERE id_contra ='"+id+"'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            sqLiteDatabase.close();
        }
        return correcto;
    }
    public boolean eliminaContra(String Contra, int id){
        boolean correcto = false;
        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = getWritableDatabase();
        try{
            sqLiteDatabase.execSQL("DELETE FROM "+TABLE_CONTRAS+" WHERE contra ='"+Contra+"' and idusu = '"+id+"'");
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            sqLiteDatabase.close();
        }
        return correcto;
    }

}
