package info.mobilesgmc.balancoHidrico;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.mobilesgmc.modelo.BalancoHidrico;
import info.nicolau.mobilegsmc.R;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterBalancoHidrico extends ArrayAdapter<BalancoHidrico> {

    private final Context context;
    private final ArrayList<BalancoHidrico> itemsArrayList;
    private TextView textView_hora;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

    public AdapterBalancoHidrico(Context context, ArrayList<BalancoHidrico> itemsArrayList) {
        super(context, R.layout.child_balanco_hidrico, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_balanco_hidrico, parent, false);

        // 3. Get the two text view from the rowView
        textView_hora = (TextView) rowView.findViewById(R.id.textViewHoraBalanco);
        TextView textView_ValorAdminSangue = (TextView) rowView.findViewById(R.id.textViewValorAdministracao);
        TextView textView_ValorEliminacao  = (TextView) rowView.findViewById(R.id.textViewValorEliminacao);
        TextView textView_ValorTotal  = (TextView) rowView.findViewById(R.id.textViewValorTotal);

        // 4. Set the text for textView


        textView_hora.setText(itemsArrayList.get(position).getHora());
        textView_ValorAdminSangue.setText(itemsArrayList.get(position).getValorAdministracaoSangue()+"");
        textView_ValorEliminacao.setText(itemsArrayList.get(position).getValorEliminacao()+"");
        textView_ValorTotal.setText(itemsArrayList.get(position).getValorTotal()+"");
        if(itemsArrayList.get(position).getValorTotal()<0)
            textView_ValorTotal.setTextColor(Color.RED);


        // 5. retrn rowView
        return rowView;
    }


}
