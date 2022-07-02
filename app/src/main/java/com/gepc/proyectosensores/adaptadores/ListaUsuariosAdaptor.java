package com.gepc.proyectosensores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gepc.proyectosensores.R;
import com.gepc.proyectosensores.usuarioadmin.Usuarios;

import java.util.List;

public class ListaUsuariosAdaptor extends RecyclerView.Adapter<ListaUsuariosAdaptor.UsuarioViewHolder> {

    //Crearé un contructor al inicio de la clase ya que este me permitaá obtener el tamañao exacto o la cantidad ed campos que tiene la lista  y para ello se necesita un Arrayslist
    List<Usuarios> ListadeUsuariosOficiales;
    Context MainActivity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ListaUsuariosAdaptor(Context MainActivity, List<Usuarios> ListadeUsuariosOficiales, OnItemClickListener mListener){
        this.MainActivity= MainActivity;
        this.ListadeUsuariosOficiales = ListadeUsuariosOficiales;
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //este Ayuda a asignar el diseño que va a atener cada elemento de la lista a mostrar
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items_usuarios,null, false);
       return new UsuarioViewHolder (view);
    }

    @Override
    public void onBindViewHolder( @NonNull UsuarioViewHolder holder, int position) {//Este ayuda a asiganr los elementos que corrsponde para cada campo por ejemplo "el dato que corresponde al nombre, el que coreespondea al password y así ...."
        Usuarios usuario = ListadeUsuariosOficiales.get(position);
        holder.viewId.setText(String.valueOf(usuario.getView_IdUsuario())); //Transformo de int a String;
        holder.viewNombre.setText(usuario.getView_IdNombre());
        holder.view_LoginUser.setText(usuario.getView_LoginUser());
        holder.view_tipoUser.setText(usuario.getView_TipoUser());
    }

    @Override
    public int getItemCount() {
        return ListadeUsuariosOficiales.size(); // aquí se implementa el contructor

    }


    public void setOnItemClickListener( OnItemClickListener onItemClick){
        this.mListener=onItemClick;
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView viewId, viewNombre, view_LoginUser, view_tipoUser;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //código que queremos hacer cuando se pulse sobre un elemento
                    mListener.onItemClick(view, getAdapterPosition());
                }
            });
            viewId = itemView.findViewById(R.id.View_Indice);
            viewNombre = itemView.findViewById(R.id.View_hora);
            view_LoginUser = itemView.findViewById(R.id.View_LoginUser);
            view_tipoUser = itemView.findViewById(R.id.View_tipoUser);
        }
    }

}
