package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.Sensores;

import java.util.List;

public class ListaSensoresAdaptor extends RecyclerView.Adapter<ListaSensoresAdaptor.SensoresViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<Sensores> ListaSensoresOficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaSensoresAdaptor(Context MainActivity, List<Sensores> ListaSensoresOficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListaSensoresOficiales = ListaSensoresOficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public SensoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items_sensores,null, false);
       return new SensoresViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull SensoresViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        Sensores sensores = ListaSensoresOficiales.get(position);
        holder.viewIdSensores.setText(String.valueOf(sensores.getView_ID_Sensor())); //Transformo de int a String;
        holder.viewNombreSensores.setText(sensores.getView_Nombre_DelSensor());
        holder.view_localiSensores.setText(sensores.getView_localizacionSensor());
        holder.view_tipoSensor.setText(sensores.getView_tipodeSensores());
    }

    @Override
    public int getItemCount() {
        return ListaSensoresOficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class SensoresViewHolder extends RecyclerView.ViewHolder {

        TextView viewIdSensores, viewNombreSensores, view_localiSensores, view_tipoSensor;
        public SensoresViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            viewIdSensores = itemView.findViewById(R.id.View_ID_Sensor);
            viewNombreSensores = itemView.findViewById(R.id.View_Nombre_DelSensor);
            view_localiSensores = itemView.findViewById(R.id.View_localizacionSensor);
            view_tipoSensor = itemView.findViewById(R.id.View_tipodeSensores);
        }
    }

}
