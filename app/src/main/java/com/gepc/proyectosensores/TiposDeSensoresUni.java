package com.gepc.proyectosensores;

public class TiposDeSensoresUni {
    private int id_TipoSensor;
    private String Descripcion;


    public int getId_localizacion() {
        return id_TipoSensor;
    }

    public void setId_localizacion(int id_localizacion) {
        this.id_TipoSensor = id_localizacion;
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
