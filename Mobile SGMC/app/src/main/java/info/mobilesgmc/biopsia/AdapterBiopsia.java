package info.mobilesgmc.biopsia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import info.mobilesgmc.modelo.PecaBiopsia;
import info.nicolau.mobilegsmc.R;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterBiopsia extends ArrayAdapter<PecaBiopsia> {

    private final Context context;
    private final ArrayList<PecaBiopsia> itemsArrayList;


    public AdapterBiopsia(Context context, ArrayList<PecaBiopsia> itemsArrayList) {
        super(context, R.layout.child_peca_biopsia, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_peca_biopsia, parent, false);

        // 3. Get the two text view from the rowView
        EditText editText_descricao = (EditText) rowView.findViewById(R.id.editTextDescricaoPecaBiopsia);
        EditText editText_laboratorio = (EditText) rowView.findViewById(R.id.editTextLaboratorioPecaBiopsia);


        // 4. Set the text for textView
        editText_descricao.setText(itemsArrayList.get(position).getDescricao());
        editText_laboratorio.setText(itemsArrayList.get(position).getLaboratotio());


        editText_descricao.addTextChangedListener(new EditTextWatcherDescricaoPecaBiopsia(itemsArrayList.get(position)));
        editText_laboratorio.addTextChangedListener(new EditTextWatcherLaboratorioPecaBiopsia(itemsArrayList.get(position)));

        // 5. retrn rowView
        return rowView;
    }


}
