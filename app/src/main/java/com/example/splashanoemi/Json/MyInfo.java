package com.example.splashanoemi.Json;
import java.io.Serializable;
import java.util.List;

public class MyInfo implements Serializable {
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    private int idUser;
    private String usuario;
    private String password;
    private String correo;
    private String edad;
    private String sexo;
    private String Tusu;
    private String mascotas;
    private String Telefono;
    private String FechaNac;
    private List<MyData> contras;
    public List<MyData> getContras() {
        return contras;
    }
    public void setContras(List<MyData> contras) {
        this.contras = contras;
    }
    public MyInfo(String usuario, String password, String correo,String edad, String sexo, String Tusu, String hijos, String Telefono, String FechaNac) {
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;
        this.edad = edad;
        this.sexo = sexo;
        this.Tusu =Tusu;
        this.mascotas = mascotas;
        this.Telefono =Telefono;
        this.FechaNac = FechaNac;
    }

    public MyInfo() {
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTusu() {
        return Tusu;
    }

    public void setTusu(String tusu) {
        Tusu = tusu;
    }

    public String getHijos() {
        return mascotas;
    }

    public void setHijos(String hijos) {
        this.mascotas = hijos;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }


}
