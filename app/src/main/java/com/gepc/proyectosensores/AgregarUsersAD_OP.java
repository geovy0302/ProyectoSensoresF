package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityAgregarUsersAdOpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgregarUsersAD_OP extends BasedelMenuOpcUAdmin  {

    ActivityAgregarUsersAdOpBinding activityAgregarUsersAdOpBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, user_a_buscar, indice_tip,id_User,login_User,tipo_User,tipo_UserAux, nombre_User, telefonouser,Edaduser, Password_User ;
    TextView Signedinusername, Contenedor_Tips;
    ProgressDialog pdDialog;
    ClaseGlobal objGlobalAux;
    EditText idUser,loginUser, nombreUser, telefono_user,Edad_user, PasswordUser ;
    EditText useraBuscar;
    Spinner tipoUser;

    Button retornar, Añadir;
    static final String URL_REGISTER = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/registration.php";
    String[] tipoUseres = { "Administrador/General", "Operador/General"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_users_ad_op);

        activityAgregarUsersAdOpBinding = ActivityAgregarUsersAdOpBinding.inflate(getLayoutInflater());
        setContentView( activityAgregarUsersAdOpBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        nombreUser = (EditText) findViewById(R.id.Nombre_locali);
        loginUser = (EditText) findViewById(R.id.Login_user);
        telefono_user = (EditText) findViewById(R.id.Telefono_user);
        Edad_user = (EditText) findViewById(R.id.edad_user);
        PasswordUser = (EditText) findViewById(R.id.contrasena_user);
        tipoUser = (Spinner) findViewById(R.id.Tipo_user);

        retornar= (Button) findViewById(R.id.retornar);
        Añadir= (Button) findViewById(R.id.anadiruser);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tipoUseres);
        aa.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        tipoUser.setAdapter(aa);
        tipoUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                tipo_User= parent.getItemAtPosition(i).toString();
                Toast.makeText(parent.getContext(), "Ha seleccionado: "+ tipo_User , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_User=nombreUser.getText().toString();
                login_User=loginUser.getText().toString();
                telefonouser=telefono_user.getText().toString();
                Edaduser=Edad_user.getText().toString();
                Password_User=PasswordUser.getText().toString();
                pdDialog= new ProgressDialog(AgregarUsersAD_OP.this);
                pdDialog.setTitle(" Registrando Usuario...");
                pdDialog.setCancelable(false);
                if(nombre_User.isEmpty()||login_User.isEmpty()||telefonouser.isEmpty()||Edaduser.isEmpty()||Password_User.isEmpty())
                {
                    pdDialog.dismiss();
                    Toast.makeText(AgregarUsersAD_OP.this," Campos Vacíos, Por favor completelos ",Toast.LENGTH_SHORT).show();
                }
                else{
                    Register();
                }

            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(AgregarUsersAD_OP.this, com.gepc.proyectosensores.RegistroUsuario_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });
    }

    private void Register()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String message = jsonObject.getString("message");
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                            }
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                Intent intenregresar_menu = new Intent(AgregarUsersAD_OP.this, com.gepc.proyectosensores.AgregarUsersAD_OP.class);
                                startActivity(intenregresar_menu);
                                finish();
                            }
                            if(success.equals("success 3")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en registrar el Usuario "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de ingresión "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Nombre_Com",nombre_User);
                params.put("cedula_Usuario",login_User);
                params.put("telefono_Usuario",telefonouser);
                params.put("Edad",Edaduser);
                params.put("contrasena",Password_User);
                params.put("TipodeUsuario",tipo_User);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}