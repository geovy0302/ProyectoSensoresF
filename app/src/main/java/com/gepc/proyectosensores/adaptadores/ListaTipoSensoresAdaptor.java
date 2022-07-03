package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.TipoDeSensoresUA;

import java.util.List;

public class ListaTipoSensoresAdaptor extends RecyclerView.Adapter<ListaTipoSensoresAdaptor.TipoSensoresViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<TipoDeSensoresUA> ListadeTiposSensoresOficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaTipoSensoresAdaptor(Context MainActivity, List<TipoDeSensoresUA> ListadeTiposSensoresOficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListadeTiposSensoresOficiales = ListadeTiposSensoresOficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public TipoSensoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_detiposnesores_ua,null, false);
       return new TipoSensoresViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull TipoSensoresViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        TipoDeSensoresUA tipoDeSensores = ListadeTiposSensoresOficiales.get(position);
        holder.view_ID_TipodeSensor.setText(String.valueOf(tipoDeSensores.getView_IdTipoSensor())); //Transformo de int a String;
        holder.view_Nombre_TipodeSensor.setText(tipoDeSensores.getView_Nombre_TipoSensor());
    }

    @Override
    public int getItemCount() {
        return ListadeTiposSensoresOficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class TipoSensoresViewHolder extends RecyclerView.ViewHolder {

        TextView view_ID_TipodeSensor, view_Nombre_TipodeSensor;
        public TipoSensoresViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            view_ID_TipodeSensor = itemView.findViewById(R.id.View_ID_TipoSensor);
            view_Nombre_TipodeSensor = itemView.findViewById(R.id.View_Nombre_TipoSensor);

        }
    }

}
