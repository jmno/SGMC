package pt.mobilesgmc.dreanagem;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilegsmc.R;

import java.util.ArrayList;
import java.util.Calendar;

import pt.mobilesgmc.DadosINtraOperatorioActivity;
import pt.mobilesgmc.modelo.Drenagem;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterDrenagem extends ArrayAdapter<Drenagem> {

    private final Context context;
    private final ArrayList<Drenagem> itemsArrayList;
    private TextView textView_hora;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

    public AdapterDrenagem(Context context, ArrayList<Drenagem> itemsArrayList) {
        super(context, R.layout.child_drenagem, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_drenagem, parent, false);

        // 3. Get the two text view from the rowView
        textView_hora = (TextView) rowView.findViewById(R.id.textViewHoraDrenagem);
        EditText editText_drenagem = (EditText) rowView.findViewById(R.id.editTextDrenagem);
        final Spinner spinner_caracteristicas = (Spinner) rowView.findViewById(R.id.spinner_DadosIntra_Caracteristicas);

        textView_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView_hora = (TextView) v;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                Log.i("hora inicio", itemsArrayList.get(position).getHora());
                                String hr = (hourOfDay + ":"
                                        + minute + ":00");
                                itemsArrayList.get(position).setHora(hr);
                                Log.i("hora fim:", itemsArrayList.get(position).getHora());
                                textView_hora.setText(hr);
                                DadosINtraOperatorioActivity.refreshBalancos();
                            }

                        }, mHour, mMinute, true);

                dpd.show();
            }
        });
        // 4. Set the text for textView


        textView_hora.setText(itemsArrayList.get(position).getHora());
        editText_drenagem.setText(itemsArrayList.get(position).getDrenagem()+"");
        int tipo = spinnerDaMeATuaPosicao(spinner_caracteristicas, itemsArrayList.get(position).getCaracteristicas());
        if(tipo != -1)
            spinner_caracteristicas.setSelection(tipo);
        else
            spinner_caracteristicas.setSelection(0);



        spinner_caracteristicas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                itemsArrayList.get(position).setCaracteristicas(spinner_caracteristicas.getItemAtPosition(posicao).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editText_drenagem.addTextChangedListener(new EditTextWatcherDrenagem(itemsArrayList.get(position)));


        // 5. retrn rowView
        return rowView;
    }

    public int spinnerDaMeATuaPosicao(Spinner s,String valor)
    {
        int resultado = -1;

        for(int i=0; i<s.getAdapter().getCount(); i++)
        {
            if(s.getItemAtPosition(i).toString().toLowerCase().equals(valor.toLowerCase()))
                resultado = i;
        }

        return resultado;
    }

    public String getCodigo(int posicao)
    {
        String resultado ="";
        switch (posicao){
            case 1:
                resultado =  "Código 1";
                break;
            default:
                resultado = "";
                break;
        }

        return resultado;

    }


}
