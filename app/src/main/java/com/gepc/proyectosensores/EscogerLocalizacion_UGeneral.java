package com.gepc.proyectosensores;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityEscogerLocalizacionUgeneralBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class EscogerLocalizacion_UGeneral extends BasedelMenuOpcUGeneral{

   ActivityEscogerLocalizacionUgeneralBinding activityEscogerLocalizacionUgeneralBinding;
    static final String URL_BUSCAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeLocalizaciones.php";
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre,tipo_User, indice_tip;
    Button Buscar;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;

    Spinner SpinnnerLoccalizacion;
    ArrayList<ClaseGlobalLocalizaciones> arrayLocaliazciones = new ArrayList<ClaseGlobalLocalizaciones>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoger_localizacion_ugeneral);
        activityEscogerLocalizacionUgeneralBinding = ActivityEscogerLocalizacionUgeneralBinding.inflate(getLayoutInflater());
        setContentView( activityEscogerLocalizacionUgeneralBinding.getRoot());



        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        //nombre = objGlobalAux.getNombreUsuario();
        // tipo_User=mPreferences.getString("tipo_User","null");
        //nombre=mPreferences.getString("nombreUser","null");
        //nombre = "Usuario General";
        Signedinusername.setText(nombre);
        Buscar = (Button) findViewById(R.id.Buscar);

        SpinnnerLoccalizacion = (Spinner) findViewById(R.id.spinnerLocalizacion);
        init();
    }

    public void init(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSCAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                ClaseGlobalLocalizaciones localizacion = new ClaseGlobalLocalizaciones();
                                localizacion.setDescripcion(array.getJSONObject(indice).getString("Descripcion_Lregion"));
                                //JSONObject usuariosG = (JSONObject) array.get(indice);
                                //String Descripcion= usuariosG.optString("Descripcion_Lregion");
                                arrayLocaliazciones.add(localizacion);
                            }
                            ArrayAdapter<ClaseGlobalLocalizaciones> adapterLocakizaciones = new ArrayAdapter<ClaseGlobalLocalizaciones>(EscogerLocalizacion_UGeneral.this,android.R.layout.simple_dropdown_item_1line,arrayLocaliazciones);
                            SpinnnerLoccalizacion.setAdapter(adapterLocakizaciones);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el login el Usuario  " + error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}