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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityDatosPorSensorUaBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatosPorSensor_UA extends BasedelMenuOpcUAdmin  {
    ActivityDatosPorSensorUaBinding activityDatosPorSensorUaBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, Localizacion_esogida, tipoDeSensorEscogido, SensorEscogido ;
    TextView Signedinusername,titulo1, titulo2 ;
    ProgressDialog pdDialog;
    ClaseGlobal objGlobalAux;
    EditText idUser,loginUser, nombreUser, telefono_user,Edad_user, PasswordUser ;
    EditText useraBuscar;
    Spinner localizacionesS, TipodeSensoresS, SensoresEncontrados ;

    Button buscarDatosPorLocaliyTipo, BuscarElSensor;
    static final String URL_BUSCARLOCAL = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeLocalizaciones.php";
    static final String URL_BUSCARTIPOSENSOR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeTipoDeSensores.php";
    static final String URL_BUSCARSENSORES = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadeUsuarios/BusquedadeDatosSensores.php";
    ArrayList<ClaseGlobalLocalizaciones> arrayLocaliazciones = new ArrayList<ClaseGlobalLocalizaciones>();
    ArrayList<TiposDeSensoresUni> arrayTipoSensores = new ArrayList<TiposDeSensoresUni>();
    ArrayList<nombreSensorBuscar> arraySensoresBuscar = new ArrayList<nombreSensorBuscar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_por_sensor_ua);

        activityDatosPorSensorUaBinding = ActivityDatosPorSensorUaBinding.inflate(getLayoutInflater());
        setContentView( activityDatosPorSensorUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        localizacionesS = (Spinner) findViewById(R.id.SpinnerLocali);
        TipodeSensoresS = (Spinner) findViewById(R.id.spinnerTipoSensor);
        SensoresEncontrados = (Spinner) findViewById(R.id.spinnerSensoresEnco);

        titulo1 = (TextView) findViewById(R.id.textView4);
        titulo2 = (TextView) findViewById(R.id.textView3);
        titulo1.setVisibility(View.INVISIBLE);
        titulo2.setVisibility(View.INVISIBLE);
        SensoresEncontrados.setVisibility(View.INVISIBLE);
        BuscarElSensor= (Button) findViewById(R.id.Retornar2);
        BuscarElSensor.setVisibility(View.INVISIBLE);

        buscarDatosPorLocaliyTipo = (Button) findViewById(R.id.Regresar);

        localizacionesBuscar();
        localizacionesS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Localizacion_esogida = parent.getSelectedItem().toString();
                //Toast.makeText(parent.getContext(), "Ha seleccionado: "+ Localizacion_esogida , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tiposSensoreBuscar();
        TipodeSensoresS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDeSensorEscogido = parent.getSelectedItem().toString();
                //Toast.makeText(parent.getContext(), "Ha seleccionado: "+ tipoDeSensorEscogido , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        buscarDatosPorLocaliyTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SensoresaBuscar();
                SensoresEncontrados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SensorEscogido = parent.getSelectedItem().toString();
                        //Toast.makeText(parent.getContext(), "Ha seleccionado: "+ SensorEscogido , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                BuscarElSensor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intenbuscaruser = new Intent(DatosPorSensor_UA.this, com.gepc.proyectosensores.DatosPorSensorPF_UA.class);
                        intenbuscaruser.putExtra("Localizacion_esogida", Localizacion_esogida );
                        intenbuscaruser.putExtra("tipoDeSensorEscogido",tipoDeSensorEscogido);
                        intenbuscaruser.putExtra("SensorEscogido", SensorEscogido );
                        startActivity(intenbuscaruser);
                        finish();
                    }
                });
            }
        });

    }

    public void localizacionesBuscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSCARLOCAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //listaTotal.clear();
                        arrayLocaliazciones.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                ClaseGlobalLocalizaciones localizacion = new ClaseGlobalLocalizaciones();
                                localizacion.setDescripcion(array.getJSONObject(indice).getString("Descripcion_Lregion"));
                                arrayLocaliazciones.add(localizacion);
                            }
                            ArrayAdapter<ClaseGlobalLocalizaciones> adapterLocakizaciones = new ArrayAdapter<ClaseGlobalLocalizaciones>(DatosPorSensor_UA.this,android.R.layout.simple_dropdown_item_1line,arrayLocaliazciones);
                            localizacionesS.setAdapter(adapterLocakizaciones);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error cargar las locaizaciones  " + error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void tiposSensoreBuscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSCARTIPOSENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayTipoSensores.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                TiposDeSensoresUni TipoSensoress = new TiposDeSensoresUni();
                                TipoSensoress.setDescripcion(array.getJSONObject(indice).getString("Descripcion_TS"));
                                arrayTipoSensores.add(TipoSensoress);
                            }
                            ArrayAdapter<TiposDeSensoresUni> adapterTipoSensores = new ArrayAdapter<TiposDeSensoresUni>(DatosPorSensor_UA.this,android.R.layout.simple_dropdown_item_1line,arrayTipoSensores);
                            TipodeSensoresS.setAdapter(adapterTipoSensores);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en cargar los tipos de Sensores  " + error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void SensoresaBuscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BUSCARSENSORES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arraySensoresBuscar.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                for ( int indice = 0; indice < array.length(); indice++){
                                    nombreSensorBuscar TipoSensoress = new nombreSensorBuscar();
                                    TipoSensoress.setDescripcion(array.getJSONObject(indice).getString("Nombre_Sensor"));
                                    arraySensoresBuscar.add(TipoSensoress);
                                }
                                ArrayAdapter<nombreSensorBuscar> adapterSensorBuscar = new ArrayAdapter<nombreSensorBuscar>(DatosPorSensor_UA.this,android.R.layout.simple_dropdown_item_1line,arraySensoresBuscar);
                                SensoresEncontrados.setAdapter(adapterSensorBuscar);
                                titulo1.setVisibility(View.VISIBLE);
                                titulo2.setVisibility(View.VISIBLE);
                                SensoresEncontrados.setVisibility(View.VISIBLE);
                                BuscarElSensor.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(getApplicationContext(), "No se encuentra ningún sensor con esa localización y de ese tipo ", Toast.LENGTH_LONG).show();
                                titulo1.setVisibility(View.INVISIBLE);
                                titulo2.setVisibility(View.INVISIBLE);
                                SensoresEncontrados.setVisibility(View.INVISIBLE);
                                BuscarElSensor.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en cargar la lista de estos  Sensores  " + error, Toast.LENGTH_LONG).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
            Map<String,String> params = new HashMap<>();
            params.put("Localizacion_esogida",Localizacion_esogida);
            params.put("tipoDeSensorEscogido",tipoDeSensorEscogido);
            return params;
        }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

}