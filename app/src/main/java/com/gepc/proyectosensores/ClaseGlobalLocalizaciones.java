package com.gepc.proyectosensores;

public class ClaseGlobalLocalizaciones {
    private int id_localizacion;
    private String Descripcion;



    public int getId_localizacion() {
        return id_localizacion;
    }

    public void setId_localizacion(int id_localizacion) {
        this.id_localizacion = id_localizacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Override
    public String toString(){
        return Descripcion;
    }
}
