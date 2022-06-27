package com.gepc.proyectosensores;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gepc.proyectosensores.databinding.ActivityRedesSocialesUgeneralBinding;

import java.util.Random;

public class RedesSocialesUGeneral extends BasedelMenuOpcUGeneral {
    ActivityRedesSocialesUgeneralBinding activityRedesSocialesUgeneralBinding;

    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre,tipo_User, indice_tip;
    //Button logout;
    TextView Signedinusername, Contenedor_Tips;
    Random random;
    int nummeroRandom;
    //ClaseGlobal objGlobalAux = new ClaseGlobal() ;
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
                    //code specific to first list item
                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de Facebook ",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de Instagram ",Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {

                    Toast.makeText(getApplicationContext()," Haz seleccionado el App de YouTube ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}