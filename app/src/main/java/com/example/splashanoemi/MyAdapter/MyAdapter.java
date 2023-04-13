package com.example.splashanoemi.MyAdapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.splashanoemi.Json.MyData;
import com.example.splashanoemi.R;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {
    private List<MyData> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<MyData> list, Context context)
    {
        this.list = list;
        this.context = context;
        if( context != null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public boolean isEmptyOrNull( )
    {
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount()
    {
        if(isEmptyOrNull())
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        if(isEmptyOrNull())
        {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }
    //Modificar MyAdapter agregar el imageview
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        TextView textView = null;
        TextView textView2 = null;
        ImageView imageView = null;
        byte[] imagenBytes = list.get(i).getData();
        Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        view = layoutInflater.inflate(R.layout.activity_list_view, null );
        textView = view.findViewById(R.id.textViewId2);
        textView2 = view.findViewById(R.id.textViewId1);
        textView.setText(list.get(i).getContra());
        textView2.setText(list.get(i).getRed());
        imageView = view.findViewById(R.id.imageView5);
        imageView.setImageBitmap(imagenBitmap);
        return view;
    }
}