package com.gepc.proyectosensores.usuarioadmin;

public class Usuarios {

   private int View_IdUsuario;
    private String View_IdNombre;
    private String View_LoginUser;
    private String View_telefonoUser;
    private String View_EdadUser;
    private String View_PasswordUser;
    private String View_TipoUser;


    public Usuarios(int view_IdUsuario, String view_IdNombre, String view_LoginUser, String view_telefonoUser, String view_EdadUser, String view_PasswordUser, String view_TipoUser) {
        this.View_IdUsuario = view_IdUsuario;
        this.View_IdNombre = view_IdNombre;
        this.View_LoginUser = view_LoginUser;
        this.View_telefonoUser = view_telefonoUser;
        this.View_EdadUser = view_EdadUser;
        this.View_PasswordUser = view_PasswordUser;
        this.View_TipoUser = view_TipoUser;
    }

    public int getView_IdUsuario() {
        return View_IdUsuario;
    }

    public void setView_IdUsuario(int view_IdUsuario) {
        View_IdUsuario = view_IdUsuario;
    }

    public String getView_IdNombre() {
        return View_IdNombre;
    }

    public void setView_IdNombre(String view_IdNombre) {
        View_IdNombre = view_IdNombre;
    }

    public String getView_LoginUser() {
        return View_LoginUser;
    }

    public void setView_LoginUser(String view_LoginUser) {
        View_LoginUser = view_LoginUser;
    }

    public String getView_telefonoUser() {
        return View_telefonoUser;
    }

    public void setView_telefonoUser(String view_telefonoUser) {
        View_telefonoUser = view_telefonoUser;
    }

    public String getView_EdadUser() {
        return View_EdadUser;
    }

    public void setView_EdadUser(String view_EdadUser) {
        View_EdadUser = view_EdadUser;
    }

    public String getView_PasswordUser() {
        return View_PasswordUser;
    }

    public void setView_PasswordUser(String view_PasswordUser) {
        View_PasswordUser = view_PasswordUser;
    }

    public String getView_TipoUser() {
        return View_TipoUser;
    }

    public void setView_TipoUser(String view_TipoUser) {
        View_TipoUser = view_TipoUser;
    }
}
