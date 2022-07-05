package com.gepc.proyectosensores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gepc.proyectosensores.databinding.ActivityRedesSocialesUgeneralBinding;

public class RedesSocialesUGeneral extends BasedelMenuOpcUGeneral {
    ActivityRedesSocialesUgeneralBinding activityRedesSocialesUgeneralBinding;

    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;

    ListView list;

    String[] maintitle ={
            " Facebook "," Instagram ",
            " YouTube ",

    };

    String[] subtitle ={
            " Faceapp "," Instapp ",
           " YouTapp ",

    };

    Integer[] imgid={
            R.drawable.facebook,R.drawable.instagram,
           R.drawable.youtube
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_sociales_ugeneral);
        activityRedesSocialesUgeneralBinding = ActivityRedesSocialesUgeneralBinding.inflate(getLayoutInflater());
        setContentView( activityRedesSocialesUgeneralBinding.getRoot());

        MyListAdapter adapter=new MyListAdapter(this, maintitle, subtitle,imgid);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);



        list=(ListView)findViewById(R.id.listV);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {
                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de Facebook ",Toast.LENGTH_SHORT).show();
                    app_a_visitar="https://www.facebook.com/profile.php?id=100082510822198";
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(app_a_visitar));
                    startActivity(intent);
                }

                else if(position == 1) {//code specific to 2nd list item
                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de Instagram ",Toast.LENGTH_SHORT).show();
                    app_a_visitar="https://www.instagram.com/uv_phyton_app/";
                    Intent intent2=new Intent(Intent.ACTION_VIEW, Uri.parse(app_a_visitar));
                    startActivity(intent2);
                }

                else if(position == 2) {
                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de YouTube ",Toast.LENGTH_SHORT).show();
                    app_a_visitar="https://www.youtube.com/channel/UChRRSg7FQRSd8_t0JMLyVFA";
                    Intent intent3=new Intent(Intent.ACTION_VIEW, Uri.parse(app_a_visitar));
                    startActivity(intent3);
                }
            }
        });

    }
}