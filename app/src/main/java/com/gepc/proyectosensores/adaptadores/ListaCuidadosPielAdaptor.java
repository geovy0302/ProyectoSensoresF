package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.CuidadoPielUA;

import java.util.List;

public class ListaCuidadosPielAdaptor extends RecyclerView.Adapter<ListaCuidadosPielAdaptor.CuidadosPielViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<CuidadoPielUA> ListadeCuidadoPielficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaCuidadosPielAdaptor(Context MainActivity, List<CuidadoPielUA> ListadeCuidadoPielficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListadeCuidadoPielficiales = ListadeCuidadoPielficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public CuidadosPielViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_decuidado_ua,null, false);
       return new CuidadosPielViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull CuidadosPielViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        CuidadoPielUA cuidadoPiel = ListadeCuidadoPielficiales.get(position);
        holder.view_IdCuidadoPiel.setText(String.valueOf(cuidadoPiel.getView_IdCuidadoPiel())); //Transformo de int a String;
        holder.view_Nombre_CuidadoPiel.setText(cuidadoPiel.getView_Nombre_CuidadoPiel());
    }

    @Override
    public int getItemCount() {
        return ListadeCuidadoPielficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class CuidadosPielViewHolder extends RecyclerView.ViewHolder {

        TextView view_IdCuidadoPiel, view_Nombre_CuidadoPiel;
        public CuidadosPielViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            view_IdCuidadoPiel = itemView.findViewById(R.id.View_ID_CuidadoPiel);
            view_Nombre_CuidadoPiel = itemView.findViewById(R.id.View_Nombre_CuidadoPiel);

        }
    }

}
