package com.gepc.proyectosensores;

import android.app.Application;

public class ClaseGlobal extends Application {
    private String NombreUsuario;
    private String Tipo_usuario;


    public String getTipo_usuario() {
        return Tipo_usuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        Tipo_usuario = tipo_usuario;
    }


/*public void setValores(String nombreUsuario, String tipo_usuario) {
        this.NombreUsuario = nombreUsuario;
        this.Tipo_usuario = tipo_usuario;
    }*/

    /*public String getEstadoCivilAux() {
        return EstadoCivilAux;
    }*/

   /* public void setEstadoCivilAux(String estadoCivilAux) {
        EstadoCivilAux = estadoCivilAux;
    }*/


   }
