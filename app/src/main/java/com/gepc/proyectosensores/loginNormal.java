package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginNormal extends AppCompatActivity {
 static final String URL_LOGIN = "http://gpssandcloud.com/RAYOSV_APIS/login.php";
    TextView Registernow;
    ProgressDialog pdDialog;
    String luser= "",lpass="";
    TextInputEditText username,password;
    Button loginButton;
    String is_signed_in="";
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
     ClaseGlobal objGlobalAux;
    //ClaseGlobal objGlobalAux = new ClaseGlobal() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();
        is_signed_in = mPreferences.getString("issignedin","false");
        if(is_signed_in.equals("true"))
        {
            Intent i = new Intent(loginNormal.this, MenuPrincipalAdmin.class);
            startActivity(i);
            finish();
        }
        Registernow =(TextView)findViewById(R.id.regis_cuenta_nueva);
        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();
        username = (TextInputEditText) findViewById(R.id.Usuario_regis);
        password = (TextInputEditText)findViewById(R.id.contrasena);
        loginButton=(Button) findViewById(R.id.Btn_ingresar);
        Registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(loginNormal.this,com.gepc.proyectosensores.AgregarUsuario.class);
                startActivity(register);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luser=username.getText().toString().trim();
                lpass=password.getText().toString().trim();
                if(luser.isEmpty()||lpass.isEmpty())
                {
                    Toast.makeText(loginNormal.this,"Datos no ingresados, Inténtelo nuevamente",Toast.LENGTH_SHORT).show();
                }else {
                    pdDialog= new ProgressDialog(loginNormal.this);
                    pdDialog.setTitle("Inicio de Sesión en proceso...");
                    pdDialog.setCancelable(false);
                    Login();
                }
            }
        });
    }

    private void Login()
    {
        pdDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","["+response+"]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Inicio de Sesión Exitoso",Toast.LENGTH_LONG).show();
                                String name = jsonObject.getString("NombreC_U");
                                String username = jsonObject.getString("cedula-Usuario");
                                int idA= jsonObject.getInt("Id_usuarios");
                                String id=Integer.toString(idA);
                                int id_Tipo= jsonObject.getInt("Id_TipoUsu-fk");
                                String id_tipoU=Integer.toString(id_Tipo);
                                objGlobalAux = (ClaseGlobal) getApplicationContext();
                                objGlobalAux.setNombreUsuario(name);
                                objGlobalAux.setTipo_usuario(id_tipoU);
                                if(id_tipoU.equals("1")){
                                    Intent pantallaUsuarioGeneral = new Intent(loginNormal.this, BienvenidaUserGeneral.class);
                                    startActivity(pantallaUsuarioGeneral);
                                    finish();
                                }
                                if(id_tipoU.equals("2")){
                                    Intent pantallaUsuarioGeneral = new Intent(loginNormal.this, MenuPrincipalAdmin.class);
                                    startActivity(pantallaUsuarioGeneral);
                                    finish();
                                }

                                if(id_tipoU.equals("3")){
                                    Intent pantallaUsuarioGeneral = new Intent(loginNormal.this, MenuPrincipalOperadores.class);
                                    startActivity(pantallaUsuarioGeneral);
                                    finish();
                                }


                            }
                            else{
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Nombre de usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en el proceso " +e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en el login el Usuario  " +error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("cedula_Usuario",luser);
                params.put("contrasena",lpass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}