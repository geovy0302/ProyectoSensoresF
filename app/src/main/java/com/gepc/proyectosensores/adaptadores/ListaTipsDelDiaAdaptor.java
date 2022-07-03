package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.TipsDelDiaUA;

import java.util.List;

public class ListaTipsDelDiaAdaptor extends RecyclerView.Adapter<ListaTipsDelDiaAdaptor.TipsDelDiaViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<TipsDelDiaUA> ListadeTipsDelDiaficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaTipsDelDiaAdaptor(Context MainActivity, List<TipsDelDiaUA> ListadeTipsDelDiaficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListadeTipsDelDiaficiales = ListadeTipsDelDiaficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public TipsDelDiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_detips_ua,null, false);
       return new TipsDelDiaViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull TipsDelDiaViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        TipsDelDiaUA tipsDelDia = ListadeTipsDelDiaficiales.get(position);
        holder.view_ID_TipsDelDia.setText(String.valueOf(tipsDelDia.getView_IdTipsdelDia())); //Transformo de int a String;
        holder.view_Nombre_TipsDelDia.setText(tipsDelDia.getView_Nombre_TipsDia());
    }

    @Override
    public int getItemCount() {
        return ListadeTipsDelDiaficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class TipsDelDiaViewHolder extends RecyclerView.ViewHolder {

        TextView view_ID_TipsDelDia, view_Nombre_TipsDelDia;
        public TipsDelDiaViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            view_ID_TipsDelDia = itemView.findViewById(R.id.View_ID_TipsdelDia);
            view_Nombre_TipsDelDia = itemView.findViewById(R.id.View_Nombre_TipsdelDia);

        }
    }

}
