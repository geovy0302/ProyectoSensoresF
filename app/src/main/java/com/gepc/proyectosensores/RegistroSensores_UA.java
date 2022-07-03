package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.gepc.proyectosensores.databinding.ActivityRegistroSensoresUaBinding;
import com.gepc.proyectosensores.usuarioadmin.Sensores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroSensores_UA extends BasedelMenuOpcUAdmin {
    ActivityRegistroSensoresUaBinding activityRegistroSensoresUaBinding;
    public static List<Sensores> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    RecyclerView ListaSensores;
    ListaSensoresAdaptor AdapterNecesario;
    Button añadir, retornar;

    static final String URL_SENSOR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/listadoDeSensores.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/Eliminar.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sensores_ua);

        activityRegistroSensoresUaBinding = ActivityRegistroSensoresUaBinding.inflate(getLayoutInflater());
        setContentView( activityRegistroSensoresUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        añadir = (Button)findViewById(R.id.AñadirLocali);
        retornar = (Button)findViewById(R.id.Retonarmenu);

        ListaSensores = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaSensores.setHasFixedSize(true);
        ListaSensores.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroSensores_UA.this, com.gepc.proyectosensores.AgregarSensor_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroSensores_UA.this, com.gepc.proyectosensores.MenudeInforSenso_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });
    }

    private void Listado_de_Usuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
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
                                                    startActivity(new Intent(getApplicationContext(), MostrarSensores_UA.class).
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
                                                    startActivity(new Intent(getApplicationContext(), ModificarSensores_UA.class).
                                                            putExtra("position", position));
                                                    finish();

                                            }
                                        }
                                    });
                                    builder.create().show();
                                }
                            });
                            ListaSensores.setAdapter(AdapterNecesario);
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
        });
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
                                startActivity(new Intent(getApplicationContext(),RegistroSensores_UA.class));
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