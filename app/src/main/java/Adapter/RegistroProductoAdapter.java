package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qhatusubasta.R;
import com.qhatusubasta.Subasta;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Producto;

public class RegistroProductoAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> gridIndex;

    public RegistroProductoAdapter(Context context, List lista) {
        this.context = context;
        this.gridIndex= lista;
    }
    @Override
    public int getCount() {
        return gridIndex.size();
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

        View root = View.inflate(context, R.layout.activity_carta,null);

        ImageView imageView= (ImageView) root.findViewById(R.id.imgCarta);
        TextView txtdescripcion = root.findViewById(R.id.txtDescripcionCarta);
        TextView txtprecio = root.findViewById(R.id.txtValorCarta);

        txtdescripcion.setText(gridIndex.get(position).getDescripcion());
        txtprecio.setText(gridIndex.get(position).getOferta_inicial());

        String imageUri=null;
        imageUri= gridIndex.get(position).getFoto();
        Picasso.get().load(imageUri).into(imageView);
        return root;

    }
}
