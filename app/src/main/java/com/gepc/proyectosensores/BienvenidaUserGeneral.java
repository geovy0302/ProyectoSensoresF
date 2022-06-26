package com.gepc.proyectosensores;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityBienvenidaUserGeneralBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class BienvenidaUserGeneral extends BasedelMenuOpcUGeneral {

ActivityBienvenidaUserGeneralBinding ActivityBienvenidaUserGeneralBinding;
    static final String URL_LOGIN = "http://gpssandcloud.com/RAYOSV_APIS/traerTips.php";
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre,tipo_User, indice_tip;
    //Button logout;
    TextView Signedinusername, Contenedor_Tips;
    Random random;
    int nummeroRandom;
    //ClaseGlobal objGlobalAux = new ClaseGlobal() ;
    ClaseGlobal objGlobalAux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_user_general);
        ActivityBienvenidaUserGeneralBinding = ActivityBienvenidaUserGeneralBinding.inflate(getLayoutInflater());
        setContentView( ActivityBienvenidaUserGeneralBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        /*if(nombre.isEmpty())
        {
            nombre= "pudrete";
        }*/
        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        //preferencesEditor = mPreferences.edit();
        //Bundle extras = getIntent().getExtras();
        //tipo_User=extras.getString("tipo_User");
        //nombre=extras.getString("nombreUser");
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        //nombre = objGlobalAux.getNombreUsuario();
       // tipo_User=mPreferences.getString("tipo_User","null");
        //nombre=mPreferences.getString("nombreUser","null");
        //nombre = "Usuario General";
        Signedinusername.setText(nombre);
        Contenedor_Tips = (TextView)findViewById(R.id.contenedor_Tip);
        random = new Random();
        nummeroRandom= random.nextInt(7-1)+1;
        indice_tip= Integer.toString(nummeroRandom);

        Tips_Buscar();
    }

    private void Tips_Buscar()
    {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","["+response+"]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            String descripcion = jsonObject.getString("Descripcion_Tip");
                            if(success.equals("success")){
                                //Toast.makeText(getApplicationContext(),"Mensaje prueba",Toast.LENGTH_LONG).show();
                                Contenedor_Tips.setText(descripcion);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Tips no encontrado",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en el proceso " +e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en traer el tips al Usuario  " +error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Id_tips",indice_tip);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
