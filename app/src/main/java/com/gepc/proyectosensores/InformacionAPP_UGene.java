package com.gepc.proyectosensores;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.gepc.proyectosensores.databinding.ActivityInformacionAppUgeneBinding;

public class InformacionAPP_UGene extends BasedelMenuOpcUGeneral {

    ActivityInformacionAppUgeneBinding activityInformacionAppUgeneBinding;

    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre;
    TextView Signedinusername;
    ClaseGlobal objGlobalAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_app_ugene);
        activityInformacionAppUgeneBinding = ActivityInformacionAppUgeneBinding.inflate(getLayoutInflater());
        setContentView( activityInformacionAppUgeneBinding.getRoot());
        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);
    }
}