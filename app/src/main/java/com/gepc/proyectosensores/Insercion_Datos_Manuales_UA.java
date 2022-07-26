package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityInsercionDatosManualesUaBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Insercion_Datos_Manuales_UA extends BasedelMenuOpcUAdmin {
    ActivityInsercionDatosManualesUaBinding activityInsercionDatosManualesUaBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    TimePicker timepicker;
    String currentTime;
    SharedPreferences.Editor preferencesEditor;
    String nombre, Localizacion_esogida, tipoDeSensorEscogido, SensorEscogido, fecha_a_buscar, cantidad_a_Registar ;
    TextView Signedinusername,titulo1, titulo2 ;
    ProgressDialog pdDialog;
    ClaseGlobal objGlobalAux;
    TextView idLocali,TipoSensor, SensorEscogidoF ;
    EditText datoAingresar;
    Button buscarDatosPorLocaliyTipo, BuscarRetornar;
    int dia,mes,año, mesAux;
    Calendar fecha;

    static final String URL_Insertar = "http://gpssandcloud.com/ExamenSemestralGEPC/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insercion_datos_manuales_ua);

        activityInsercionDatosManualesUaBinding = ActivityInsercionDatosManualesUaBinding.inflate(getLayoutInflater());
        setContentView( activityInsercionDatosManualesUaBinding.getRoot());

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

        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes= fecha.get(Calendar.MONTH);
        mesAux=mes+1;
        año= fecha.get(Calendar.YEAR);
        fecha_a_buscar= año+"-0"+mesAux+"-"+dia;

        timepicker=(TimePicker)findViewById(R.id.time_picker);
        timepicker.setVisibility(View.INVISIBLE);
        timepicker.setIs24HourView(true);
        currentTime=timepicker.getCurrentHour()+":"+timepicker.getCurrentMinute();

        buscarDatosPorLocaliyTipo= (Button) findViewById(R.id.Regresar);
        BuscarRetornar= (Button) findViewById(R.id.Retornar2);


        idLocali = (TextView)findViewById(R.id.LocalizacionEscogida);
        TipoSensor = (TextView)findViewById(R.id.TipoSensorEscogido);
        SensorEscogidoF = (TextView)findViewById(R.id.sensorEscogido);
        datoAingresar = (EditText) findViewById(R.id.Dato_A_Registrar);


        idLocali.setText(Localizacion_esogida);
        TipoSensor.setText(tipoDeSensorEscogido);
        SensorEscogidoF.setText(SensorEscogido);
        //cantidad_a_Registar = datoAingresar.getText().toString();
        //cantidad_a_Registar = "10.11";

        BuscarRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(Insercion_Datos_Manuales_UA.this, com.gepc.proyectosensores.Escoger_LTS_Manual_Admin.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        buscarDatosPorLocaliyTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdDialog= new ProgressDialog(Insercion_Datos_Manuales_UA.this);
                pdDialog.setTitle(" Registrando Dato Manual...");
                pdDialog.setCancelable(false);
                Register();
            }
        });
    }
    private void Register()
    {
        pdDialog.show();
        cantidad_a_Registar = datoAingresar.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Insertar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("Success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Dato registrado con éxito",Toast.LENGTH_LONG).show();
                            }else{
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Error en registrar este dato",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en registrar el Dato "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de ingresión "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Localizacion_esogida",Localizacion_esogida);
                params.put("tipoDeSensorEscogido",tipoDeSensorEscogido);
                params.put("SensorEscogido",SensorEscogido);
                params.put("Cantidad_a_registar",cantidad_a_Registar);
                params.put("hora",currentTime);
                params.put("fecha",fecha_a_buscar);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}