package com.gepc.proyectosensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityBienvenidaUserOperadorBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BienvenidaUserOperador extends BasedelMenuOpcUOperador {
    ActivityBienvenidaUserOperadorBinding activityBienvenidaUserOperadorBinding;

    static final String URL_LOGIN = "http://gpssandcloud.com/RAYOSV_APIS/traerTips.php";
    //SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    //SharedPreferences.Editor preferencesEditor;
    String nombre,tipo_User, indice_tip;
    Button logout;
    TextView Signedinusername, Contenedor_Tips;
    Random random;
    int nummeroRandom;
    ClaseGlobal objGlobalAux ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_user_operador);
        activityBienvenidaUserOperadorBinding = ActivityBienvenidaUserOperadorBinding.inflate(getLayoutInflater());
        setContentView( activityBienvenidaUserOperadorBinding.getRoot());


        //preferencesEditor = mPreferences.edit();
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        //nombre = objGlobalAux.getNombreUsuario();
        //nombre = "Usuario General";
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
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