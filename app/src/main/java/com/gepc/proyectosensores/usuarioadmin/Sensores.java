package com.gepc.proyectosensores.usuarioadmin;

public class Sensores {

   private int View_ID_Sensor;
    private String View_Nombre_DelSensor;
    private String View_COOR_LATITUD;
    private String View_COOR_LONGITUD;
    private String View_localizacionSensor;
    private String View_tipodeSensores;

    public Sensores(int view_ID_Sensor, String view_Nombre_DelSensor, String view_COOR_LATITUD, String view_COOR_LONGITUD, String view_localizacionSensor, String view_tipodeSensores) {
        this.View_ID_Sensor = view_ID_Sensor;
        this.View_Nombre_DelSensor = view_Nombre_DelSensor;
        this.View_COOR_LATITUD = view_COOR_LATITUD;
        this.View_COOR_LONGITUD = view_COOR_LONGITUD;
        this.View_localizacionSensor = view_localizacionSensor;
        this.View_tipodeSensores = view_tipodeSensores;
    }

    public int getView_ID_Sensor() {
        return View_ID_Sensor;
    }

    public void setView_ID_Sensor(int view_ID_Sensor) {
        View_ID_Sensor = view_ID_Sensor;
    }

    public String getView_Nombre_DelSensor() {
        return View_Nombre_DelSensor;
    }

    public void setView_Nombre_DelSensor(String view_Nombre_DelSensor) {
        View_Nombre_DelSensor = view_Nombre_DelSensor;
    }

    public String getView_COOR_LATITUD() {
        return View_COOR_LATITUD;
    }

    public void setView_COOR_LATITUD(String view_COOR_LATITUD) {
        View_COOR_LATITUD = view_COOR_LATITUD;
    }

    public String getView_COOR_LONGITUD() {
        return View_COOR_LONGITUD;
    }

    public void setView_COOR_LONGITUD(String view_COOR_LONGITUD) {
        View_COOR_LONGITUD = view_COOR_LONGITUD;
    }

    public String getView_localizacionSensor() {
        return View_localizacionSensor;
    }

    public void setView_localizacionSensor(String view_localizacionSensor) {
        View_localizacionSensor = view_localizacionSensor;
    }

    public String getView_tipodeSensores() {
        return View_tipodeSensores;
    }

    public void setView_tipodeSensores(String view_tipodeSensores) {
        View_tipodeSensores = view_tipodeSensores;
    }
}
