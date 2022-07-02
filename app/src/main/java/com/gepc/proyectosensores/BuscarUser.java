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
import com.gepc.proyectosensores.databinding.ActivityBuscarUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuscarUser extends BasedelMenuOpcUAdmin  {

    ActivityBuscarUserBinding activityBuscarUserBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, user_a_buscar, indice_tip,id_User,login_User,tipo_User, nombre_User, telefonouser,Edaduser, Password_User ;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    TextView idUser,loginUser,tipoUser, nombreUser, telefono_user,Edad_user, PasswordUser,titular1, titular2 ,titulo1, titulo2, titulo3, titulo4, titulo5, titulo6, titulo7 ;
    EditText useraBuscar;
    Button retornar1, retornar2, retornarAux,  buscar, buscarOtro;
    ProgressDialog pdDialog;
    static final String URL_BUSCAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadeUsuarios/BuscarUsers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_user);

        activityBuscarUserBinding = ActivityBuscarUserBinding.inflate(getLayoutInflater());
        setContentView( activityBuscarUserBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        titular1 = (TextView) findViewById(R.id.textView17);
        titular2 = (TextView) findViewById(R.id.textView4);

        titulo1 = (TextView) findViewById(R.id.textView3);
        titulo2 = (TextView) findViewById(R.id.textView8);
        titulo3 = (TextView) findViewById(R.id.textView2);
        titulo4 = (TextView) findViewById(R.id.textView9);
        titulo5 = (TextView) findViewById(R.id.textView14);
        titulo6 = (TextView) findViewById(R.id.textView15);
        titulo7 = (TextView) findViewById(R.id.textView16);


        titular2.setVisibility(View.INVISIBLE);


        titulo1.setVisibility(View.INVISIBLE);
        titulo2.setVisibility(View.INVISIBLE);
        titulo3.setVisibility(View.INVISIBLE);
        titulo4.setVisibility(View.INVISIBLE);
        titulo5.setVisibility(View.INVISIBLE);
        titulo6.setVisibility(View.INVISIBLE);
        titulo7.setVisibility(View.INVISIBLE);

        idUser = (TextView) findViewById(R.id.Id_User);


        idUser = (TextView) findViewById(R.id.Id_User);
        nombreUser = (TextView) findViewById(R.id.Nombre_user);
        loginUser = (TextView) findViewById(R.id.Login_user);
        telefono_user = (TextView) findViewById(R.id.Telefono_user);
        Edad_user = (TextView) findViewById(R.id.edad_user);
        PasswordUser = (TextView) findViewById(R.id.contrasena_user);
        tipoUser = (TextView) findViewById(R.id.Tipo_user);
        retornar1= (Button) findViewById(R.id.Retornar1);
         retornarAux= (Button) findViewById(R.id.retornar);
        retornar2= (Button) findViewById(R.id.anadiruser);
        buscar= (Button) findViewById(R.id.Retornar2);
        buscarOtro= (Button) findViewById(R.id.anadiruser);
        useraBuscar = (EditText) findViewById(R.id.UserBuscar);
        //user_a_buscar=useraBuscar.getText().toString();
        buscar.setVisibility(View.VISIBLE);
        useraBuscar.setVisibility(View.VISIBLE);


        idUser.setVisibility(View.INVISIBLE);
        nombreUser.setVisibility(View.INVISIBLE);
        loginUser.setVisibility(View.INVISIBLE);
        telefono_user.setVisibility(View.INVISIBLE);
        Edad_user.setVisibility(View.INVISIBLE);
        PasswordUser.setVisibility(View.INVISIBLE);
        tipoUser.setVisibility(View.INVISIBLE);
        retornar2.setVisibility(View.INVISIBLE);
        buscarOtro.setVisibility(View.INVISIBLE);
        retornarAux.setVisibility(View.INVISIBLE);


        retornar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(BuscarUser.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        retornar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(BuscarUser.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        buscarOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(BuscarUser.this, com.gepc.proyectosensores.BuscarUser.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_a_buscar=useraBuscar.getText().toString();
                BuscarUser();

            }
        });

    }
    private void BuscarUser() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BUSCAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","["+response+"]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                Toast.makeText(getApplicationContext(),"Búsqueda de Usuario Exitosa",Toast.LENGTH_LONG).show();
                                int idA= jsonObject.getInt("Id_usuarios");
                                String id=Integer.toString(idA);
                                nombre_User = jsonObject.getString("NombreC_U");
                                login_User = jsonObject.getString("cedula-Usuario");
                                telefonouser= jsonObject.getString("telefono_user");
                                Edaduser= jsonObject.getString("Edad");
                                Password_User= jsonObject.getString("contrasena");
                                tipo_User= jsonObject.getString("Descripcion_TU");

                                idUser.setText(id);
                                nombreUser.setText(nombre_User);
                                loginUser.setText(login_User);
                                telefono_user.setText(telefonouser);
                                Edad_user.setText(Edaduser);
                                PasswordUser.setText(Password_User);
                                tipoUser.setText(tipo_User);

                                titular2.setVisibility(View.VISIBLE);
                                titulo1.setVisibility(View.VISIBLE);
                                titulo2.setVisibility(View.VISIBLE);
                                titulo3.setVisibility(View.VISIBLE);
                                titulo4.setVisibility(View.VISIBLE);
                                titulo5.setVisibility(View.VISIBLE);
                                titulo6.setVisibility(View.VISIBLE);
                                titulo7.setVisibility(View.VISIBLE);

                                idUser.setVisibility(View.VISIBLE);
                                nombreUser.setVisibility(View.VISIBLE);
                                loginUser.setVisibility(View.VISIBLE);
                                telefono_user.setVisibility(View.VISIBLE);
                                Edad_user.setVisibility(View.VISIBLE);
                                PasswordUser.setVisibility(View.VISIBLE);
                                tipoUser.setVisibility(View.VISIBLE);
                                retornar2.setVisibility(View.VISIBLE);
                                buscarOtro.setVisibility(View.VISIBLE);
                                retornarAux.setVisibility(View.VISIBLE);

                                titular1.setVisibility(View.INVISIBLE);
                                buscar.setVisibility(View.INVISIBLE);
                                useraBuscar.setVisibility(View.INVISIBLE);
                                retornar1.setVisibility(View.INVISIBLE);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Usuario a buscar no encontrado ",Toast.LENGTH_LONG).show();
                                titular1.setVisibility(View.VISIBLE);
                                buscar.setVisibility(View.VISIBLE);
                                useraBuscar.setVisibility(View.VISIBLE);
                                retornar1.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en el proceso " +e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en el login el Usuario  " +error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("cedula_Usuario",user_a_buscar);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}