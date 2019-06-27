package com.android.store.mercapp.Entidades;

import java.io.Serializable;

public class Productos  {
    private  int Precio;
    private String Nombre;


    public Productos() {
    }

    public Productos(int precio, String nombre) {
        Precio = precio;
        Nombre = nombre;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
