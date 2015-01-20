package pt.mobilesgmc.acessosVenosos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.mobilegsmc.R;

import java.util.ArrayList;

import pt.mobilesgmc.modelo.AcessoVenoso;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterAcessosVenosos extends ArrayAdapter<AcessoVenoso> {

    private final Context context;
    private final ArrayList<AcessoVenoso> itemsArrayList;


    public AdapterAcessosVenosos(Context context, ArrayList<AcessoVenoso> itemsArrayList) {
        super(context, R.layout.child_acessos_venosos, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_acessos_venosos, parent, false);

        // 3. Get the two text view from the rowView
        EditText editText_tipo = (EditText) rowView.findViewById(R.id.editText_Tipo_AcessoVenoso);
        EditText editText_calibre = (EditText) rowView.findViewById(R.id.editText_Calibre_AcessoVenoso);
        EditText editText_localizacao = (EditText) rowView.findViewById(R.id.editText_Localizacao_AcessoVenoso);

        // 4. Set the text for textView
        editText_tipo.setText(itemsArrayList.get(position).getTipoAcessoVenoso());
        editText_calibre.setText(itemsArrayList.get(position).getCalibreAcessoVenoso()+"");
        editText_localizacao.setText(itemsArrayList.get(position).getLocalizacaoAcessoVenoso());


        editText_tipo.addTextChangedListener(new EditTextWatcherTipoAcessoVenoso(itemsArrayList.get(position)));
        editText_calibre.addTextChangedListener(new EditTextWatcherCalibreAcessoVenoso(itemsArrayList.get(position)));
        editText_localizacao.addTextChangedListener(new EditTextWatcherLocalizacaoAcessoVenoso(itemsArrayList.get(position)));

        // 5. retrn rowView
        return rowView;
    }


}
