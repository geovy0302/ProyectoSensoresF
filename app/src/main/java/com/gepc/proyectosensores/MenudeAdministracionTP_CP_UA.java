package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gepc.proyectosensores.databinding.ActivityMenudeAdministracionTpCpUaBinding;

public class MenudeAdministracionTP_CP_UA extends BasedelMenuOpcUAdmin {
    ActivityMenudeAdministracionTpCpUaBinding activityMenudeAdministracionTpCpUaBinding;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences mPreferences;
    //SharedPreferences.Editor preferencesEditor;
    String nombre,tipo_User, indice_tip;
    Button logout;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menude_administracion_tp_cp_ua);

        activityMenudeAdministracionTpCpUaBinding = ActivityMenudeAdministracionTpCpUaBinding.inflate(getLayoutInflater());
        setContentView( activityMenudeAdministracionTpCpUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        Button Opcion1 = (Button)findViewById(R.id.Opcion1);
        Opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenOpcion1 = new Intent(MenudeAdministracionTP_CP_UA.this,com.gepc.proyectosensores.RegistroDeTipsDelDia.class);
                startActivity(intenOpcion1);
                finish();
            }
        });

        Button Opcion2 = (Button)findViewById(R.id.Opcion2);
        Opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intenOpcion2 = new Intent(MenudeAdministracionTP_CP_UA.this,com.gepc.proyectosensores.RegistrodeTipodeSensores_UA.class);
                startActivity(intenOpcion2);
                finish();*/
            }
        });
    }
}