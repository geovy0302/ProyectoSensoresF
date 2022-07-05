package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gepc.proyectosensores.databinding.ActivityMostrarSensoresUoBinding;

public class MostrarSensores_UO extends BasedelMenuOpcUOperador {
    ActivityMostrarSensoresUoBinding activityMostrarSensoresUoBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    int position;
    TextView idUser, nombreUser,loginUser,telefono_user,Edad_user, PasswordUser,tipoUser;
    Button retornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_sensores_uo);

        activityMostrarSensoresUoBinding = ActivityMostrarSensoresUoBinding.inflate(getLayoutInflater());
        setContentView( activityMostrarSensoresUoBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        idUser = (TextView) findViewById(R.id.Id_locali);
        nombreUser = (TextView) findViewById(R.id.Nombre_locali);
        loginUser = (TextView) findViewById(R.id.Login_user);
        telefono_user = (TextView) findViewById(R.id.Telefono_user);
        Edad_user = (TextView) findViewById(R.id.edad_user);
        PasswordUser = (TextView) findViewById(R.id.contrasena_user);
        retornar= (Button) findViewById(R.id.Regresar);
        Intent intent = getIntent();
        position= intent.getExtras().getInt("position");

        idUser.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_ID_Sensor());
        nombreUser.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_Nombre_DelSensor());
        loginUser.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_COOR_LATITUD());
        telefono_user.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_COOR_LONGITUD());
        Edad_user.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_localizacionSensor());
        PasswordUser.setText(""+ RegistroSensores_UO.listaTotal.get(position).getView_tipodeSensores());


        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(MostrarSensores_UO.this, com.gepc.proyectosensores.RegistroSensores_UO.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });
    }
}