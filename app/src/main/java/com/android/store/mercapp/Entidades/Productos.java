package com.android.store.mercapp.Entidades;

public class Productos  {
    private  int Precio;
    private String Nombre, idproducto, idImageProducto;


    public Productos() {
    }

    public Productos(int precio, String nombre, String idproducto, String idImageProducto) {
        Precio = precio;
        Nombre = nombre;
        this.idproducto = idproducto;
        this.idImageProducto = idImageProducto;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getIdImageProducto() {
        return idImageProducto;
    }

    public void setIdImageProducto(String idImageProducto) {
        this.idImageProducto = idImageProducto;
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
