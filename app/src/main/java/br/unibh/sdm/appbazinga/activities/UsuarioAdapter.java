package br.unibh.sdm.appbazinga.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.unibh.sdm.appbazinga.entidades.Usuario;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

   // private final List<Usuario> lista;
    private final Context context;

    public UsuarioAdapter(Context context, List<Usuario> body) {
        this.context = context;
        //this.lista = lista;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
