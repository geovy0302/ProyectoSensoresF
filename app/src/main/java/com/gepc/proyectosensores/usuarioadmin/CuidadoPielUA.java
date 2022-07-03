package com.gepc.proyectosensores.usuarioadmin;

public class CuidadoPielUA {

   private int View_IdCuidadoPiel;
    private String View_Nombre_CuidadoPiel;


    public CuidadoPielUA(int view_IdCuidadoPiel, String view_Nombre_CuidadoPiel) {
        this.View_IdCuidadoPiel = view_IdCuidadoPiel;
        this.View_Nombre_CuidadoPiel = view_Nombre_CuidadoPiel;
    }

    public int getView_IdCuidadoPiel() {
        return View_IdCuidadoPiel;
    }

    public void setView_IdCuidadoPiel(int view_IdCuidadoPiel) {
        View_IdCuidadoPiel = view_IdCuidadoPiel;
    }

    public String getView_Nombre_CuidadoPiel() {
        return View_Nombre_CuidadoPiel;
    }

    public void setView_Nombre_CuidadoPiel(String view_Nombre_CuidadoPiel) {
        View_Nombre_CuidadoPiel = view_Nombre_CuidadoPiel;
    }
}
