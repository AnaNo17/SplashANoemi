package com.example.splashanoemi.Json;

import java.io.Serializable;

public class MyData implements Serializable {

    public byte[] getData() {
        return data;
    }
    private String latitud;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    private String longitud;

    public void setData(byte[] data) {
        this.data = data;
    }

    private byte[] data;
    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String contra;
    private int image;

    public int getIdContra() {
        return idContra;
    }

    public void setIdContra(int idContra) {
        this.idContra = idContra;
    }

    private int idContra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    private String red;
}
