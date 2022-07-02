package com.gepc.proyectosensores;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.adaptadores.DatosSensorUAdmiadaptor;
import com.gepc.proyectosensores.databinding.ActivityDatosPorSensorPfUaBinding;
import com.gepc.proyectosensores.usuarioadmin.DatosSensorLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatosPorSensorPF_UA extends BasedelMenuOpcUAdmin {
    ActivityDatosPorSensorPfUaBinding activityDatosPorSensorPfUaBinding;
    public static List<DatosSensorLista> listaTotal = new ArrayList<>();
    static final String URL_DATOSGEN = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/BuscarDatosSensores/BusquedadeDatosSensores.php";
    static final String URL_ULTDASOT = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/BuscarDatosSensores/UltimoDatoGenerado.php";
    DatosSensorUAdmiadaptor AdapterNecesario;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    Calendar fecha ;
    SharedPreferences.Editor preferencesEditor;
    int dia,mes,año, mesAux;
    String nombre, Localizacion_esogida, tipoDeSensorEscogido, SensorEscogido,fecha_a_buscar, success,indiceTraido, horaTraida;
    TextView Signedinusername,titulo1, titulo2, titulo3, titulo4, titulo5, titulo6 ;
    ClaseGlobal objGlobalAux;
    Double indiceCalculado;
    EditText idUser,loginUser, nombreUser, telefono_user,Edad_user, PasswordUser ;
    RecyclerView listaDatosG;
    Button Retornar1, Retornar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_por_sensor_pf_ua);

        activityDatosPorSensorPfUaBinding = ActivityDatosPorSensorPfUaBinding.inflate(getLayoutInflater());
        setContentView( activityDatosPorSensorPfUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        Bundle extras = getIntent().getExtras();
        Localizacion_esogida= extras.getString("Localizacion_esogida");
        tipoDeSensorEscogido= extras.getString("tipoDeSensorEscogido");
        SensorEscogido= extras.getString("SensorEscogido");


        listaDatosG = findViewById(R.id.listaDatosG);//llamado al RecycleView
        listaDatosG.setHasFixedSize(true);
        listaDatosG.setLayoutManager( new LinearLayoutManager(this));
        listaDatosG.setVisibility(View.INVISIBLE);

        Retornar1= (Button) findViewById(R.id.Retornar1);
        Retornar2= (Button) findViewById(R.id.Retornar2);
        Retornar1.setVisibility(View.INVISIBLE);
        Retornar2.setVisibility(View.INVISIBLE);

        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes= fecha.get(Calendar.MONTH);
        mesAux=mes+1;
        año= fecha.get(Calendar.YEAR);
        fecha_a_buscar= año+"-0"+mesAux+"-"+dia;



        titulo1 = (TextView)findViewById(R.id.textView21);
        titulo2 = (TextView)findViewById(R.id.textView18);
        titulo3 = (TextView)findViewById(R.id.textView19);
        titulo4 = (TextView)findViewById(R.id.textView20);
        titulo5 = (TextView)findViewById(R.id.indiceEncontrado2);
        titulo6 = (TextView)findViewById(R.id.horaRegistro2);
        titulo1.setVisibility(View.INVISIBLE);
        titulo2.setVisibility(View.INVISIBLE);
        titulo3.setVisibility(View.INVISIBLE);
        titulo4.setVisibility(View.INVISIBLE);
        titulo5.setVisibility(View.INVISIBLE);
        titulo6.setVisibility(View.INVISIBLE);

        CargarRecycle ();

        Retornar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(DatosPorSensorPF_UA.this, com.gepc.proyectosensores.DatosPorSensor_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        Retornar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(DatosPorSensorPF_UA.this, com.gepc.proyectosensores.DatosPorSensor_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

    }

    private void CargarRecycle (){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATOSGEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                for ( int indice = 0; indice < array.length(); indice++){
                                    JSONObject usuariosG = (JSONObject) array.get(indice);
                                    listaTotal.add(new DatosSensorLista(usuariosG.getString("Cantidad_Calculada"),
                                            usuariosG.getString("Hora_tiempo")));
                                }
                                AdapterNecesario = new DatosSensorUAdmiadaptor(getApplicationContext(), listaTotal);
                                listaDatosG.setAdapter(AdapterNecesario);
                                listaDatosG.setVisibility(View.VISIBLE);
                                Cargarcomponentes ();
                            }else{
                                titulo1.setVisibility(View.VISIBLE);
                                Retornar1.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error cargar el recycle de los datos "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de búsqueda de datos "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Localizacion_esogida",Localizacion_esogida);
                params.put("tipoDeSensorEscogido",tipoDeSensorEscogido);
                params.put("SensorEscogido",SensorEscogido);
                params.put("fecha",fecha_a_buscar);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Cargarcomponentes (){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ULTDASOT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //listaTotal.clear();
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            success = jsonObject.getString("success");
                            if(success.equals("success")){
                                indiceCalculado= jsonObject.getDouble("Cantidad_Calculada");
                                indiceTraido = String.valueOf(indiceCalculado);
                                objGlobalAux = (ClaseGlobal) getApplicationContext();
                                objGlobalAux.setIndiceGenerado(indiceCalculado);
                                horaTraida = jsonObject.getString("Hora_tiempo");
                                titulo5.setText(indiceTraido);
                                titulo6.setText(horaTraida);
                                titulo2.setVisibility(View.VISIBLE);
                                titulo3.setVisibility(View.VISIBLE);
                                titulo4.setVisibility(View.VISIBLE);
                                titulo5.setVisibility(View.VISIBLE);
                                titulo6.setVisibility(View.VISIBLE);
                                Retornar2.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error cargar el recycle de los datos "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de búsqueda de datos "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Localizacion_esogida",Localizacion_esogida);
                params.put("tipoDeSensorEscogido",tipoDeSensorEscogido);
                params.put("SensorEscogido",SensorEscogido);
                params.put("fecha",fecha_a_buscar);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}