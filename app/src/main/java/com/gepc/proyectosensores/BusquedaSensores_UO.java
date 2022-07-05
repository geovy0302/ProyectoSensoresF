package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.adaptadores.ListaSensoresAdaptor;
import com.gepc.proyectosensores.databinding.ActivityBusquedaSensoresUoBinding;
import com.gepc.proyectosensores.usuarioadmin.Sensores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusquedaSensores_UO extends BasedelMenuOpcUOperador {
    ActivityBusquedaSensoresUoBinding activityBusquedaSensoresUoBinding;
    public static List<Sensores> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, Localizacion_esogida, tipoDeSensorEscogido, SensorEscogido ;
    TextView Signedinusername,titulo1, titulo2 ;
    ProgressDialog pdDialog;
    ClaseGlobal objGlobalAux;
    EditText idUser,loginUser, nombreUser, telefono_user,Edad_user, PasswordUser ;
    EditText useraBuscar;
    RecyclerView ListaSensores;
    ListaSensoresAdaptor AdapterNecesario;
    Spinner localizacionesS, TipodeSensoresS, SensoresEncontrados ;

    Button retornar, buscar;
    static final String URL_BUSCARLOCAL = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeLocalizaciones.php";
    static final String URL_BUSCARTIPOSENSOR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeTipoDeSensores.php";
    static final String URL_SENSOR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/BusquedadeSensores.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/Eliminar.php";
    ArrayList<ClaseGlobalLocalizaciones> arrayLocaliazciones = new ArrayList<ClaseGlobalLocalizaciones>();
    ArrayList<TiposDeSensoresUni> arrayTipoSensores = new ArrayList<TiposDeSensoresUni>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_sensores_uo);
        activityBusquedaSensoresUoBinding = ActivityBusquedaSensoresUoBinding.inflate(getLayoutInflater());
        setContentView( activityBusquedaSensoresUoBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        localizacionesS = (Spinner) findViewById(R.id.SpinnerLocali);
        TipodeSensoresS = (Spinner) findViewById(R.id.spinnerTipoSensor);
        SensoresEncontrados = (Spinner) findViewById(R.id.spinnerSensoresEnco);

        ListaSensores = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaSensores.setHasFixedSize(true);
        ListaSensores.setLayoutManager( new LinearLayoutManager(this));
        ListaSensores.setVisibility(View.INVISIBLE);


        titulo1 = (TextView) findViewById(R.id.textView4);
        titulo1.setVisibility(View.INVISIBLE);


        buscar= (Button) findViewById(R.id.Regresar);
        retornar = (Button) findViewById(R.id.Retornar2);


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

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(BusquedaSensores_UO.this, com.gepc.proyectosensores.MenudeInforSenso_UO.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Listado_de_Usuarios();
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
                            ArrayAdapter<ClaseGlobalLocalizaciones> adapterLocakizaciones = new ArrayAdapter<ClaseGlobalLocalizaciones>(BusquedaSensores_UO.this,android.R.layout.simple_dropdown_item_1line,arrayLocaliazciones);
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
                            ArrayAdapter<TiposDeSensoresUni> adapterTipoSensores = new ArrayAdapter<TiposDeSensoresUni>(BusquedaSensores_UO.this,android.R.layout.simple_dropdown_item_1line,arrayTipoSensores);
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

    private void Listado_de_Usuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                for ( int indice = 0; indice < array.length(); indice++){
                                    JSONObject usuariosG = (JSONObject) array.get(indice);
                                    listaTotal.add(new Sensores(usuariosG.getInt("Id_Sensores"),
                                            usuariosG.getString("Nombre_Sensor"),
                                            usuariosG.getString("Coordena_latidud"),
                                            usuariosG.getString("Coordena_Longitud"),
                                            usuariosG.getString("Descripcion_Lregion"),
                                            usuariosG.getString("Descripcion_TS")));
                                }
                                AdapterNecesario = new ListaSensoresAdaptor(getApplicationContext(), listaTotal, new ListaSensoresAdaptor.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                        String[] dialogItem ={"Ver datos de este Sensor","Eliminar este Sensor", "Modificar información de este Sensor"};
                                        builder.setTitle(listaTotal.get(position).getView_Nombre_DelSensor());
                                        builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int opcion) {
                                                switch (opcion){
                                                    case 0:
                                                        startActivity(new Intent(getApplicationContext(), MostrarInfoSensores_UO.class).
                                                                putExtra("position", position));
                                                        finish();
                                                        break;
                                                    case 1:
                                                        int Auxiliar;
                                                        String IdAuxiliar;
                                                        Auxiliar= listaTotal.get(position).getView_ID_Sensor();
                                                        IdAuxiliar= Integer.toString(Auxiliar);
                                                        EliminarDatos(IdAuxiliar);
                                                        break;
                                                    case 2:
                                                        startActivity(new Intent(getApplicationContext(), ModificarInfoSensores_UO.class).
                                                                putExtra("position", position));
                                                        finish();

                                                }
                                            }
                                        });
                                        builder.create().show();
                                    }
                                });
                                ListaSensores.setAdapter(AdapterNecesario);
                                ListaSensores.setVisibility(View.VISIBLE);
                                titulo1.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(getApplicationContext(), "No se encuentra ningún sensor con esa localización y de ese tipo ", Toast.LENGTH_LONG).show();
                                ListaSensores.setVisibility(View.INVISIBLE);
                                titulo1.setVisibility(View.INVISIBLE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en buscar  el registro Usuario  " + error, Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Localizacion_esogida", Localizacion_esogida);
                params.put("tipoDeSensorEscogido", tipoDeSensorEscogido);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void EliminarDatos (final String idSensor){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Eliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String successAux = jsonObject.getString("success");
                            if(successAux.equals("logrado")){
                                Toast.makeText(getApplicationContext()," El Sensor  ha sido eliminado correctamente",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),BusquedaSensores_UO.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext()," El Sensor  no ha sido eliminado ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Eliminar este Sensor  "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de eliminación "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idSensor",idSensor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}