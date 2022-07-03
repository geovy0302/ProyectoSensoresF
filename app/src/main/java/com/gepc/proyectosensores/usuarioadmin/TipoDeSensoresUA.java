package com.gepc.proyectosensores.usuarioadmin;

public class TipoDeSensoresUA {

   private int View_IdTipoSensor;
    private String View_Nombre_TipoSensor;


    public TipoDeSensoresUA(int view_IdTipoSensor, String view_Nombre_TipoSensor) {
        this.View_IdTipoSensor = view_IdTipoSensor;
        this.View_Nombre_TipoSensor = view_Nombre_TipoSensor;
    }

    public int getView_IdTipoSensor() {
        return View_IdTipoSensor;
    }

    public void setView_IdTipoSensor(int view_IdTipoSensor) {
        View_IdTipoSensor = view_IdTipoSensor;
    }

    public String getView_Nombre_TipoSensor() {
        return View_Nombre_TipoSensor;
    }

    public void setView_Nombre_TipoSensor(String view_Nombre_TipoSensor) {
        View_Nombre_TipoSensor = view_Nombre_TipoSensor;
    }
}
