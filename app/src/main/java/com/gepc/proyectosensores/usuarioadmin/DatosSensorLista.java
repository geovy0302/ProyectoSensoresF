package com.gepc.proyectosensores.usuarioadmin;

public class DatosSensorLista {

    private String View_Indice;
    private String View_hora;

    public DatosSensorLista(String view_Indice, String view_hora) {
        View_Indice = view_Indice;
        View_hora = view_hora;
    }

    public String getView_Indice() {
        return View_Indice;
    }

    public void setView_Indice(String view_Indice) {
        View_Indice = view_Indice;
    }

    public String getView_hora() {
        return View_hora;
    }

    public void setView_hora(String view_hora) {
        View_hora = view_hora;
    }
}
