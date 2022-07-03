package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gepc.proyectosensores.databinding.ActivityMostrarDatosUsersBinding;

public class MostrarDatosUsers extends BasedelMenuOpcUAdmin {

    ActivityMostrarDatosUsersBinding activityMostrarDatosUsersBinding;
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
        setContentView(R.layout.activity_mostrar_datos_users);

        activityMostrarDatosUsersBinding = ActivityMostrarDatosUsersBinding.inflate(getLayoutInflater());
        setContentView( activityMostrarDatosUsersBinding.getRoot());

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
        tipoUser = (TextView) findViewById(R.id.Tipo_user);
        retornar= (Button) findViewById(R.id.Regresar);
        Intent intent = getIntent();
        position= intent.getExtras().getInt("position");

        idUser.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_IdUsuario());
        nombreUser.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_IdNombre());
        loginUser.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_LoginUser());
        telefono_user.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_telefonoUser());
        Edad_user.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_EdadUser());
        PasswordUser.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_PasswordUser());
        tipoUser.setText(""+ RegistroUsuario_UA.listaTotal.get(position).getView_TipoUser());

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(MostrarDatosUsers.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });




    }
}