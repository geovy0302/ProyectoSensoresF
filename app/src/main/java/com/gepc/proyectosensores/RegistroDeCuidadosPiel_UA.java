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
import com.gepc.proyectosensores.adaptadores.ListaCuidadosPielAdaptor;
import com.gepc.proyectosensores.databinding.ActivityRegistroDeCuidadosPielUaBinding;
import com.gepc.proyectosensores.usuarioadmin.CuidadoPielUA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroDeCuidadosPiel_UA extends BasedelMenuOpcUAdmin {
    ActivityRegistroDeCuidadosPielUaBinding activityRegistroDeCuidadosPielUaBinding;
    public static List<CuidadoPielUA> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    RecyclerView ListaCuidadosPiel;
    ListaCuidadosPielAdaptor AdapterNecesario;
    Button añadir, retornar;
    static final String URL_localizaciones = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeCuidadoPiel/listadoDeCuidadosdePiel.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeCuidadoPiel/Eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_cuidados_piel_ua);

        activityRegistroDeCuidadosPielUaBinding = ActivityRegistroDeCuidadosPielUaBinding.inflate(getLayoutInflater());
        setContentView( activityRegistroDeCuidadosPielUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        añadir = (Button)findViewById(R.id.AñadirLocali);
        retornar = (Button)findViewById(R.id.Retonarmenu);

        ListaCuidadosPiel = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaCuidadosPiel.setHasFixedSize(true);
        ListaCuidadosPiel.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroDeCuidadosPiel_UA.this, com.gepc.proyectosensores.AgregarCuidadosPiel_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroDeCuidadosPiel_UA.this, com.gepc.proyectosensores.MenudeAdministracionTP_CP_UA.class);
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
                                listaTotal.add(new CuidadoPielUA(usuariosG.getInt("Id_CuidadoPiel"),
                                        usuariosG.getString("Descripcion_CP")));
                            }
                            AdapterNecesario = new ListaCuidadosPielAdaptor(getApplicationContext(), listaTotal, new ListaCuidadosPielAdaptor.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                    String[] dialogItem ={"Eliminar este cuidado de la piel", "Modificar la Info. de este cuidado de piel "};
                                    builder.setTitle(listaTotal.get(position).getView_Nombre_CuidadoPiel());
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int opcion) {
                                            switch (opcion){
                                                case 0:
                                                    int Auxiliar;
                                                    String IdAuxiliar;
                                                    Auxiliar= listaTotal.get(position).getView_IdCuidadoPiel() ;
                                                    IdAuxiliar= Integer.toString(Auxiliar);
                                                    EliminarDatos(IdAuxiliar);
                                                    break;
                                                case 1:
                                                    startActivity(new Intent(getApplicationContext(), ModificarCuidadosPiel_UA.class).
                                                            putExtra("position", position));
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                                    builder.create().show();
                                }
                            });
                            ListaCuidadosPiel.setAdapter(AdapterNecesario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en buscar  el registro del cuidado del piel  " + error, Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void EliminarDatos (final String idCuidadoPiel){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Eliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String successAux = jsonObject.getString("success");
                            if(successAux.equals("logrado")){
                                Toast.makeText(getApplicationContext()," El cuidado de la piel  ha sido eliminado correctamente",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),RegistroDeCuidadosPiel_UA.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext()," El cuidado de la piel  no ha sido eliminado ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Eliminar este cuidado de la piel  "+e,Toast.LENGTH_LONG).show();
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
                params.put("idCuidadoPiel",idCuidadoPiel);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}