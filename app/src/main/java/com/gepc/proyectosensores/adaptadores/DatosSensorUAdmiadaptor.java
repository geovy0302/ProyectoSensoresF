package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.DatosSensorLista;

import java.util.List;

public class DatosSensorUAdmiadaptor extends RecyclerView.Adapter<DatosSensorUAdmiadaptor.DatosGeneViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<DatosSensorLista> ListadeSensoresDatos;
    Context MainActivity;
    //private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public DatosSensorUAdmiadaptor(Context MainActivity, List<DatosSensorLista> ListadeSensoresDatos){
        this.MainActivity= MainActivity;
        this.ListadeSensoresDatos = ListadeSensoresDatos;
        //this.mListener= mListener;
    }


    @NonNull
    @Override
    public DatosGeneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_datoscargadosua,null, false);
       return new DatosGeneViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull DatosGeneViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        DatosSensorLista sensorLista = ListadeSensoresDatos.get(position);
        holder.View_Indice.setText(sensorLista.getView_Indice());
        holder.View_hora.setText(sensorLista.getView_hora());
    }

    @Override
    public int getItemCount() {
        return ListadeSensoresDatos.size(); // aquí se implementa el contructor

    }

    public class DatosGeneViewHolder extends RecyclerView.ViewHolder {

        TextView View_Indice, View_hora;
        public DatosGeneViewHolder(@NonNull View itemView) {
            super(itemView);
            View_Indice = itemView.findViewById(R.id.View_Indice);
            View_hora = itemView.findViewById(R.id.View_hora);
        }
    }
}
