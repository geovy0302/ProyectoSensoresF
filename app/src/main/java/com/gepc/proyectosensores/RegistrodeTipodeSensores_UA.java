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
import com.gepc.proyectosensores.adaptadores.ListaTipoSensoresAdaptor;
import com.gepc.proyectosensores.databinding.ActivityRegistrodeTipodeSensoresUaBinding;
import com.gepc.proyectosensores.usuarioadmin.TipoDeSensoresUA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrodeTipodeSensores_UA extends BasedelMenuOpcUAdmin {
    ActivityRegistrodeTipodeSensoresUaBinding activityRegistrodeTipodeSensoresUaBinding;
    public static List<TipoDeSensoresUA> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    RecyclerView ListaTipoSensores;
    ListaTipoSensoresAdaptor AdapterNecesario;
    Button añadir, retornar;
    static final String URL_localizaciones = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeTiposSensores/listadodeTipoDeSensores.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeTiposSensores/Eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_tipode_sensores_ua);

        activityRegistrodeTipodeSensoresUaBinding = ActivityRegistrodeTipodeSensoresUaBinding.inflate(getLayoutInflater());
        setContentView( activityRegistrodeTipodeSensoresUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        añadir = (Button)findViewById(R.id.AñadirLocali);
        retornar = (Button)findViewById(R.id.Retonarmenu);

        ListaTipoSensores = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaTipoSensores.setHasFixedSize(true);
        ListaTipoSensores.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistrodeTipodeSensores_UA.this, com.gepc.proyectosensores.AgregarTipodeSensor_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistrodeTipodeSensores_UA.this, com.gepc.proyectosensores.MenudeInforSenso_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });
    }

    private void Listado_de_Usuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_localizaciones,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                JSONObject usuariosG = (JSONObject) array.get(indice);
                                listaTotal.add(new TipoDeSensoresUA(usuariosG.getInt("Id_Tipo_Sensor"),
                                        usuariosG.getString("Descripcion_TS")));
                            }
                            AdapterNecesario = new ListaTipoSensoresAdaptor(getApplicationContext(), listaTotal, new ListaTipoSensoresAdaptor.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                    String[] dialogItem ={"Eliminar este Tipo de Sensor", "Modificar información de Tipo Sensor"};
                                    builder.setTitle(listaTotal.get(position).getView_Nombre_TipoSensor());
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int opcion) {
                                            switch (opcion){
                                                case 0:
                                                    int Auxiliar;
                                                    String IdAuxiliar;
                                                    Auxiliar= listaTotal.get(position).getView_IdTipoSensor() ;
                                                    IdAuxiliar= Integer.toString(Auxiliar);
                                                    EliminarDatos(IdAuxiliar);
                                                    break;
                                                case 1:
                                                    startActivity(new Intent(getApplicationContext(), ModificarTipoSensor_UA.class).
                                                            putExtra("position", position));
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                                    builder.create().show();
                                }
                            });
                            ListaTipoSensores.setAdapter(AdapterNecesario);
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

    private void EliminarDatos (final String idTipoSensor){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Eliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String successAux = jsonObject.getString("success");
                            if(successAux.equals("logrado")){
                                Toast.makeText(getApplicationContext()," El tipo de Sensor  ha sido eliminado correctamente",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),RegistrodeTipodeSensores_UA.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext()," El tipo Sensor  no ha sido eliminado ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Eliminar este tipo de Sensor  "+e,Toast.LENGTH_LONG).show();
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
                params.put("idTipoSensor",idTipoSensor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}