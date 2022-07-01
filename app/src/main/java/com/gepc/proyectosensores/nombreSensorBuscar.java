package com.gepc.proyectosensores;

public class nombreSensorBuscar {
    private String Descripcion;


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
