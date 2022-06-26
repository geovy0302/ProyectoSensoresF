package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuariogeneral.cuidadoLista;


import java.util.List;

public class CuidadoUGenedaptor extends RecyclerView.Adapter<CuidadoUGenedaptor.CuidadosViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<cuidadoLista> ListadeCuidados;
    Context MainActivity;
    //private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public CuidadoUGenedaptor(Context MainActivity, List<cuidadoLista> ListadeCuidados){
        this.MainActivity= MainActivity;
        this.ListadeCuidados = ListadeCuidados;
        //this.mListener= mListener;
    }


    @NonNull
    @Override
    public CuidadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cuidadosug,null, false);
       return new CuidadosViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull CuidadosViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        cuidadoLista cuidados_listas = ListadeCuidados.get(position);
        holder.viewDescripcion.setText(String.valueOf(cuidados_listas.getView_Descripcion())); //Transformo de int a String;

    }

    @Override
    public int getItemCount() {
        return ListadeCuidados.size(); // aquí se implementa el contructor

    }


    /*public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }*/

    public class CuidadosViewHolder extends RecyclerView.ViewHolder {

        TextView viewDescripcion;
        public CuidadosViewHolder(@NonNull View itemView) {
            super(itemView);
            viewDescripcion = itemView.findViewById(R.id.View_Descripcion);
        }
    }

}
