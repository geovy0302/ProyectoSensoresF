package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.gepc.proyectosensores.adaptadores.CuidadoUGenedaptor;
import com.gepc.proyectosensores.databinding.ActivityIndiceCalculadoUgeneralBinding;
import com.gepc.proyectosensores.usuariogeneral.cuidadoLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndiceCalculado_UGeneral extends BasedelMenuOpcUGeneral {

    ActivityIndiceCalculadoUgeneralBinding indiceCalculadoUgeneralBinding;
    public static List<cuidadoLista> listaTotal = new ArrayList<>();
    static final String URL_Indice = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioGeneral/DatosGenerado.php";
    static final String URL_CUIDADOS = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioGeneral/traerCuidados.php";
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre,indiceTraido, fecha_a_buscar, Localizacion_esogida, success, horaTraida, calificacion, calificaionaEnviar ;
    Button Buscar;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    Calendar fecha ;
    Double indiceCalculado;
    int dia,mes,año, mesAux;
    ImageView icono1, icono2,icono3,icono4,icono5;
    TextView indiceGenerado, horaGenerada, calificacionBaja,calificacionModerada, calificacionAlta, calificacionMuyAlta, calificacionExtrema ;
    RecyclerView ListaCuidados;
    Button añadir, retornar;
    CuidadoUGenedaptor AdapterNecesario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indice_calculado_ugeneral);
        indiceCalculadoUgeneralBinding = ActivityIndiceCalculadoUgeneralBinding.inflate(getLayoutInflater());
        setContentView( indiceCalculadoUgeneralBinding.getRoot());

        Bundle extras = getIntent().getExtras();
        Localizacion_esogida =extras.getString("Localizacion_esogida");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        ListaCuidados = findViewById(R.id.listaCuidados);//llamado al RecycleView
        ListaCuidados.setHasFixedSize(true);
        ListaCuidados.setLayoutManager( new LinearLayoutManager(this));

        icono1= (ImageView) findViewById(R.id.imageView3);
        icono2= (ImageView) findViewById(R.id.imageView5);
        icono3= (ImageView) findViewById(R.id.imageView6);
        icono4= (ImageView) findViewById(R.id.imageView7);
        icono5= (ImageView) findViewById(R.id.imageView8);
        icono1.setVisibility(View.INVISIBLE);
        icono2.setVisibility(View.INVISIBLE);
        icono3.setVisibility(View.INVISIBLE);
        icono4.setVisibility(View.INVISIBLE);
        icono5.setVisibility(View.INVISIBLE);
        retornar = (Button)findViewById(R.id.regresar2);

        indiceGenerado = (TextView)findViewById(R.id.indiceEncontrado);
        horaGenerada = (TextView)findViewById(R.id.horaRegistro);

        calificacionBaja = (TextView)findViewById(R.id.calificacionBaja);
        calificacionModerada = (TextView)findViewById(R.id.calificacionModerada);
        calificacionAlta = (TextView)findViewById(R.id.calificacionAlta);
        calificacionMuyAlta = (TextView)findViewById(R.id.calificacionMuyAlta);
        calificacionExtrema = (TextView)findViewById(R.id.calificacionExtrema);
        calificacionBaja.setVisibility(View.INVISIBLE);
        calificacionModerada.setVisibility(View.INVISIBLE);
        calificacionAlta.setVisibility(View.INVISIBLE);
        calificacionMuyAlta.setVisibility(View.INVISIBLE);
        calificacionExtrema.setVisibility(View.INVISIBLE);


        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes= fecha.get(Calendar.MONTH);
        mesAux=mes+1;
        año= fecha.get(Calendar.YEAR);
        fecha_a_buscar= año+"-0"+mesAux+"-"+dia;

        TraerIndice();

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(IndiceCalculado_UGeneral.this, com.gepc.proyectosensores.EscogerLocalizacion_UGeneral.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });


    }

    private void TraerIndice()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Indice,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","["+response+"]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            success = jsonObject.getString("success");
                            indiceCalculado= jsonObject.getDouble("Cantidad_Calculada");
                            indiceTraido = String.valueOf(indiceCalculado);
                            objGlobalAux = (ClaseGlobal) getApplicationContext();
                            objGlobalAux.setIndiceGenerado(indiceCalculado);
                            horaTraida = jsonObject.getString("Hora_tiempo");
                            indiceGenerado.setText(indiceTraido);
                            horaGenerada.setText(horaTraida);
                                if((indiceCalculado>=0)&&(indiceCalculado < 3))
                                {
                                    icono1.setVisibility(View.VISIBLE);
                                    calificacion="Baja";
                                    calificaionaEnviar="1";
                                    calificacionBaja.setVisibility(View.VISIBLE);
                                    calificacionBaja.setText(calificacion);
                                    CargarRecycle(calificaionaEnviar);

                                }
                                
                                if((indiceCalculado >=3)&&(indiceCalculado < 6))
                                {
                                    icono2.setVisibility(View.VISIBLE);
                                    calificacion=" Moderada ";
                                    calificaionaEnviar="2";
                                    calificacionModerada.setVisibility(View.VISIBLE);
                                    calificacionModerada.setText(calificacion);
                                    CargarRecycle(calificaionaEnviar);
                                }
                                
                                if((indiceCalculado >=6)&&(indiceCalculado < 8))
                                {
                                    icono3.setVisibility(View.VISIBLE);
                                    calificacion=" Alta ";
                                    calificaionaEnviar="3";
                                    calificacionAlta.setVisibility(View.VISIBLE);
                                    calificacionAlta.setText(calificacion);
                                    CargarRecycle(calificaionaEnviar);
                                }
                                if((indiceCalculado >=8)&&(indiceCalculado < 11))
                                {
                                    icono4.setVisibility(View.VISIBLE);
                                    calificacion=" Muy Alta ";
                                    calificaionaEnviar="4";
                                    calificacionMuyAlta.setVisibility(View.VISIBLE);
                                    calificacionMuyAlta.setText(calificacion);
                                    CargarRecycle(calificaionaEnviar);
                                }
                                if((indiceCalculado >=11))
                                {
                                    icono5.setVisibility(View.VISIBLE);
                                    calificacion=" Alta ";
                                    calificaionaEnviar="5";
                                    calificacionAlta.setVisibility(View.VISIBLE);
                                    calificacionAlta.setText(calificaionaEnviar);
                                    CargarRecycle(calificaionaEnviar);
                                }

                            if(success.equals("no success")){
                                Toast.makeText(getApplicationContext(),"Nombre de la localcización o la fecha de dispositivo no coinciden ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en procesar la petición  " +e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error traer el índice calculado  " +error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("localizacion",Localizacion_esogida);
                params.put("fecha",fecha_a_buscar);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void CargarRecycle (final String calificacio_final){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CUIDADOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                JSONObject usuariosG = (JSONObject) array.get(indice);
                                listaTotal.add(new cuidadoLista(
                                        usuariosG.getString("Descripcion_CP")));
                            }
                            AdapterNecesario = new CuidadoUGenedaptor(getApplicationContext(), listaTotal);
                            ListaCuidados.setAdapter(AdapterNecesario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error cargar la lista de Cuidados "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de busqueda de cuidados "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("calificacion",calificacio_final);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}