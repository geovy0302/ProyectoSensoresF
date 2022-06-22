package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalOperadores extends AppCompatActivity {
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.practicabd_gepc";
    String nombre,tipo_User;
    //SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_operadores);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        //preferencesEditor = mPreferences.edit();
        //tipo_User=extras.getString("tipo_User");
        //nombre=extras.getString("nombreUser");

        Button Opcion1 = (Button)findViewById(R.id.opcion1);
        Opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenOpcion1 = new Intent(MenuPrincipalOperadores.this,com.gepc.proyectosensores.BienvenidaUserGeneral.class);
                startActivity(intenOpcion1);
                finish();
            }
        });

        Button Opcion2 = (Button)findViewById(R.id.opcion2);
        Opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenOpcion2 = new Intent(MenuPrincipalOperadores.this,com.gepc.proyectosensores.BienvenidaUserOperador.class);
                startActivity(intenOpcion2);
                finish();
            }
        });

        Button Opcion3 = (Button)findViewById(R.id.Opcion3);
        Opcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenOpcion3 = new Intent(MenuPrincipalOperadores.this,com.gepc.proyectosensores.loginNormal.class);
                startActivity(intenOpcion3);
                finish();
            }
        });
    }
}