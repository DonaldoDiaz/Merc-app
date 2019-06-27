package com.android.store.mercapp.Entidades;


import java.io.Serializable;

public class Storage implements Serializable {
    private String Nombre,Direccion;
    private String Estado, Id, urlimage;



    public Storage(){

    }

    public Storage(String nombre, String direccion, String estado, String id, String urlimage) {
        Nombre = nombre;
        Direccion = direccion;
        Estado = estado;
        Id= id;
        this.urlimage = urlimage;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
