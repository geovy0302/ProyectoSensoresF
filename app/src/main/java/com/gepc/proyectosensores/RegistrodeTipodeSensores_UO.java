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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.adaptadores.ListaTipoSensoresAdaptor;
import com.gepc.proyectosensores.databinding.ActivityRegistrodeTipodeSensoresUoBinding;
import com.gepc.proyectosensores.usuarioadmin.TipoDeSensoresUA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrodeTipodeSensores_UO extends BasedelMenuOpcUOperador {
    ActivityRegistrodeTipodeSensoresUoBinding activityRegistrodeTipodeSensoresUoBinding;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_tipode_sensores_uo);
        activityRegistrodeTipodeSensoresUoBinding = ActivityRegistrodeTipodeSensoresUoBinding.inflate(getLayoutInflater());
        setContentView( activityRegistrodeTipodeSensoresUoBinding.getRoot());

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
                Intent intenbuscaruser = new Intent(RegistrodeTipodeSensores_UO.this, com.gepc.proyectosensores.AgregarTipodeSensor_UO.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistrodeTipodeSensores_UO.this, com.gepc.proyectosensores.MenudeInforSenso_UO.class);
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
                                    String[] dialogItem ={"Modificar información de Tipo Sensor"};
                                    builder.setTitle(listaTotal.get(position).getView_Nombre_TipoSensor());
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int opcion) {
                                            switch (opcion){
                                                case 0:
                                                    startActivity(new Intent(getApplicationContext(), ModificarTipoSensor_UO.class).
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
}