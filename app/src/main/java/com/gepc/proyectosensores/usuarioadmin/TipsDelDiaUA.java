package com.gepc.proyectosensores.usuarioadmin;

public class TipsDelDiaUA {

   private int View_IdTipsdelDia;
    private String View_Nombre_TipsDia;


    public TipsDelDiaUA(int view_IdTipsdelDia, String view_Nombre_TipsDia) {
        this.View_IdTipsdelDia = view_IdTipsdelDia;
        this.View_Nombre_TipsDia = view_Nombre_TipsDia;
    }

    public int getView_IdTipsdelDia() {
        return View_IdTipsdelDia;
    }

    public void setView_IdTipsdelDia(int view_IdTipsdelDia) {
        View_IdTipsdelDia = view_IdTipsdelDia;
    }

    public String getView_Nombre_TipsDia() {
        return View_Nombre_TipsDia;
    }

    public void setView_Nombre_TipsDia(String view_Nombre_TipsDia) {
        View_Nombre_TipsDia = view_Nombre_TipsDia;
    }
}
