package com.android.store.mercapp.Entidades;


import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;

public class Storage implements Serializable {
    private String Nombre,Direccion;
    private String Estado, Id;



    public Storage(){

    }

    public Storage(String nombre, String direccion, String estado, String id) {
        Nombre = nombre;
        Direccion = direccion;
        Estado = estado;
        Id= id;
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
