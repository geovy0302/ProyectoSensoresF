package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.LocalizacionesUA;

import java.util.List;

public class ListaLocalizacioneAdaptor extends RecyclerView.Adapter<ListaLocalizacioneAdaptor.LocalizacionesViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<LocalizacionesUA> ListadeLocalizacionesOficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaLocalizacioneAdaptor(Context MainActivity, List<LocalizacionesUA> ListadeLocalizacionesOficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListadeLocalizacionesOficiales = ListadeLocalizacionesOficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public LocalizacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_delocalizaciones_ua,null, false);
       return new LocalizacionesViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull LocalizacionesViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        LocalizacionesUA localizaciones = ListadeLocalizacionesOficiales.get(position);
        holder.view_ID_Localizacion.setText(String.valueOf(localizaciones.getView_IdLocalizacion())); //Transformo de int a String;
        holder.view_Nombre_Locali.setText(localizaciones.getView_Nombre_Localizacion());

    }

    @Override
    public int getItemCount() {
        return ListadeLocalizacionesOficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class LocalizacionesViewHolder extends RecyclerView.ViewHolder {

        TextView view_ID_Localizacion, view_Nombre_Locali;
        public LocalizacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            view_ID_Localizacion = itemView.findViewById(R.id.View_ID_Localizacion);
            view_Nombre_Locali = itemView.findViewById(R.id.View_Nombre_Locali);

        }
    }

}
