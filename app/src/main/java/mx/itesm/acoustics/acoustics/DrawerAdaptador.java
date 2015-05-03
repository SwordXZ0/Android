package mx.itesm.acoustics.acoustics;

/**
 * Created by sky on 12/02/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdaptador extends ArrayAdapter<Objeto> {

    Context mContext;
    int layoutResourceId;
    Objeto data[] = null;

    public DrawerAdaptador(Context mContext, int layoutResourceId, Objeto[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textView);
        Objeto objeto = data[position];
        imageViewIcon.setImageResource(objeto.icono);
        textViewName.setText(objeto.nombre);

        return listItem;
    }

}