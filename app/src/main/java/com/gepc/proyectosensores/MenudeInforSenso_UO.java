package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gepc.proyectosensores.databinding.ActivityMenudeInforSensoUoBinding;

public class MenudeInforSenso_UO extends BasedelMenuOpcUOperador {
    ActivityMenudeInforSensoUoBinding activityMenudeInforSensoUoBinding;
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
        setContentView(R.layout.activity_menude_infor_senso_uo);

        activityMenudeInforSensoUoBinding = ActivityMenudeInforSensoUoBinding.inflate(getLayoutInflater());
        setContentView( activityMenudeInforSensoUoBinding.getRoot());

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
                Intent intenOpcion1 = new Intent(MenudeInforSenso_UO.this,com.gepc.proyectosensores.RegistrodeSensores_UO.class);
                startActivity(intenOpcion1);
                finish();
            }
        });

        Button Opcion2 = (Button)findViewById(R.id.Opcion2);
        Opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenOpcion2 = new Intent(MenudeInforSenso_UO.this,com.gepc.proyectosensores.RegistrodeTipodeSensores_UO.class);
                startActivity(intenOpcion2);
                finish();
            }
        });

        Button Opcion3 = (Button)findViewById(R.id.Opcione3);
        Opcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intenOpcion3 = new Intent(MenudeInforSenso_UA.this,com.gepc.proyectosensores.loginNormal.class);
                startActivity(intenOpcion3);
                finish();*/
            }
        });

        Button Opcion4 = (Button)findViewById(R.id.Opcion6);
        Opcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intenOpcion4 = new Intent(MenudeInforSenso_UA.this,com.gepc.proyectosensores.loginNormal.class);
                startActivity(intenOpcion4);
                finish();*/
            }
        });
    }
}