package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityModificarDatosUsersBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarDatosUsers extends BasedelMenuOpcUAdmin {
    ActivityModificarDatosUsersBinding activityModificarDatosUsersBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip,id_User,login_User,tipo_User, nombre_User, telefonouser,Edaduser, Password_User ;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    int position;
    TextView idUser,loginUser,tipoUser;
    EditText nombreUser, telefono_user,Edad_user, PasswordUser;
    Button retornar, modificar;
    ProgressDialog pdDialog;
    static final String URL_MODIFICAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadeUsuarios/Modificar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_users);

        activityModificarDatosUsersBinding = ActivityModificarDatosUsersBinding.inflate(getLayoutInflater());
        setContentView( activityModificarDatosUsersBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        idUser = (TextView) findViewById(R.id.Id_User);
        nombreUser = (EditText) findViewById(R.id.Nombre_user);
        loginUser = (TextView) findViewById(R.id.Login_user);
        telefono_user = (EditText) findViewById(R.id.Telefono_user);
        Edad_user = (EditText) findViewById(R.id.edad_user);
        PasswordUser = (EditText) findViewById(R.id.contrasena_user);
        tipoUser = (TextView) findViewById(R.id.Tipo_user);
        retornar= (Button) findViewById(R.id.buscarUser);
        modificar= (Button) findViewById(R.id.Modificar);
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
                Intent intenregresar_menu = new Intent(ModificarDatosUsers.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_User= idUser.getText().toString();
                nombre_User= nombreUser.getText().toString();
                telefonouser= telefono_user.getText().toString();
                Edaduser= Edad_user.getText().toString();
                Password_User= PasswordUser.getText().toString();
                pdDialog= new ProgressDialog(ModificarDatosUsers.this);
                pdDialog.setTitle(" Actulizando los datos del Usuario...");
                pdDialog.setCancelable(false);
                Modificar();
            }
        });


    }

    private void Modificar()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MODIFICAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Los datos han sido modificacdos exitosamente",Toast.LENGTH_LONG).show();
                                Intent intenregresar_menu = new Intent(ModificarDatosUsers.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                                startActivity(intenregresar_menu);
                                finish();
                            }
                            if(success.equals("success 3")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"La modificación no llevó a cabo porque hubo un error",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en Modificar el Usuario "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de modificación  "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idUsuario",id_User);
                params.put("Nombre_Com",nombre_User);
                params.put("telefono_Usuario",telefonouser);
                params.put("Edad",Edaduser);
                params.put("contrasena",Password_User);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}