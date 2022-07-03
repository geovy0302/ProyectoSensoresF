package com.gepc.proyectosensores.usuarioadmin;

public class LocalizacionesUA {

   private int View_IdLocalizacion;
    private String View_Nombre_Localizacion;



    public LocalizacionesUA(int view_IdLocalizacion, String view_Nombre_Localizacion ) {
        this.View_IdLocalizacion = view_IdLocalizacion;
        this.View_Nombre_Localizacion = view_Nombre_Localizacion;

    }

    public int getView_IdLocalizacion() {
        return View_IdLocalizacion;
    }

    public void setView_IdLocalizacion(int view_IdLocalizacion) {
        View_IdLocalizacion = view_IdLocalizacion;
    }

    public String getView_Nombre_Localizacion() {
        return View_Nombre_Localizacion;
    }

    public void setView_Nombre_Localizacion(String view_Nombre_Localizacion) {
        View_Nombre_Localizacion = view_Nombre_Localizacion;
    }
}
