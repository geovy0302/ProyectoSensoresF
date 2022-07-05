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
 import android.widget.Toast;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;
 import com.gepc.proyectosensores.databinding.ActivityModificarInfoSensoresUaBinding;

 import org.json.JSONException;
 import org.json.JSONObject;

 import java.util.HashMap;
 import java.util.Map;

 public class ModificarInfoSensores_UA extends BasedelMenuOpcUAdmin {
     ActivityModificarInfoSensoresUaBinding activityModificarInfoSensoresUaBinding;
     SharedPreferences mPreferences;
     String sharedprofFile="com.gepc.proyectosensores";
     SharedPreferences.Editor preferencesEditor;
     String nombre, app_a_visitar, indice_tip,id_User,login_User,tipo_User, nombre_User, telefonouser,Edaduser, Password_User ;
     TextView Signedinusername, Contenedor_Tips;
     ClaseGlobal objGlobalAux;
     int position;
     ProgressDialog pdDialog;
     TextView idUser, nombreUser,Edad_user, PasswordUser;
     EditText loginUser,telefono_user;
     Button retornar, modificar;
     static final String URL_MODIFICAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/Modificar.php";


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_modificar_info_sensores_ua);

         activityModificarInfoSensoresUaBinding = ActivityModificarInfoSensoresUaBinding.inflate(getLayoutInflater());
         setContentView( activityModificarInfoSensoresUaBinding.getRoot());

         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

         mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
         Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
         objGlobalAux= (ClaseGlobal) getApplicationContext();
         nombre = objGlobalAux.getNombreUsuario();
         Signedinusername.setText(nombre);

         idUser = (TextView) findViewById(R.id.Id_locali);
         nombreUser = (TextView) findViewById(R.id.Nombre_locali);
         loginUser = (EditText) findViewById(R.id.Login_user);
         telefono_user = (EditText) findViewById(R.id.Telefono_user);
         Edad_user = (TextView) findViewById(R.id.edad_user);
         PasswordUser = (TextView) findViewById(R.id.contrasena_user);
         retornar= (Button) findViewById(R.id.Regresar);
         modificar= (Button) findViewById(R.id.Modificar);
         Intent intent = getIntent();
         position= intent.getExtras().getInt("position");

         idUser.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_ID_Sensor());
         nombreUser.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_Nombre_DelSensor());
         loginUser.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_COOR_LATITUD());
         telefono_user.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_COOR_LONGITUD());
         Edad_user.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_localizacionSensor());
         PasswordUser.setText(""+ BusquedaSensores_UA.listaTotal.get(position).getView_tipodeSensores());

         retornar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intenregresar_menu = new Intent(ModificarInfoSensores_UA.this, com.gepc.proyectosensores.BusquedaSensores_UA.class);
                 startActivity(intenregresar_menu);
                 finish();
             }
         });

         modificar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              id_User= idUser.getText().toString();
              login_User= loginUser.getText().toString();
              telefonouser= telefono_user.getText().toString();
              pdDialog= new ProgressDialog(ModificarInfoSensores_UA.this);
              pdDialog.setTitle(" Actulizando los datos de este Sensor...");
              pdDialog.setCancelable(false);
              Modificar();
          }
         });
    }

    private void Modificar() {
         pdDialog.show();
         StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MODIFICAR,
              new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Log.e("tagconvertstr","[ "+response+" ]");
                   try{
                       JSONObject jsonObject = new JSONObject(response.toString());
                       String success = jsonObject.getString("success");
                       if(success.equals("success 2")){
                           pdDialog.dismiss();
                           Toast.makeText(getApplicationContext(),"Los datos han sido modificacdos exitosamente",Toast.LENGTH_LONG).show();
                           Intent intenregresar_menu = new Intent(ModificarInfoSensores_UA.this, com.gepc.proyectosensores.BusquedaSensores_UA.class);
                           startActivity(intenregresar_menu);
                           finish();
                       }
                       if(success.equals("success 3")){
                           Toast.makeText(getApplicationContext(),"La modificación no llevó a cabo porque hubo un error",Toast.LENGTH_LONG).show();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                       Toast.makeText(getApplicationContext(),"Error en Modificar el Sensores  "+e,Toast.LENGTH_LONG).show();
                   }
               }
               }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de modificación  "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idSensor",id_User);
                params.put("Coordena_latidud",login_User);
                params.put("Coordena_Longitud",telefonouser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
     }
 }