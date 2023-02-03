package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qhatusubasta.R;

import java.util.List;

import Model.Oferta;
import Model.Producto;
import Model.Usuario;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<Oferta> listaChat;
    public ChatAdapter(Context context, List listaoferta){
        this.context = context;
        this.listaChat = listaoferta;
    }
    @Override
    public int getCount() {
        return listaChat.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = View.inflate(context, R.layout.activity_oferta,null);



        TextView txtoferta = root.findViewById(R.id.txtOfertaUser);


        txtoferta.setText(listaChat.get(position).getOferta());

        return root;
    }
}
