package pt.mobilesgmc.adminSangue;

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

import pt.mobilesgmc.modelo.AdministracaoSangue;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterAdministracaoSangue extends ArrayAdapter<AdministracaoSangue> {

    private final Context context;
    private final ArrayList<AdministracaoSangue> itemsArrayList;
    private TextView textView_hora;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

    public AdapterAdministracaoSangue(Context context, ArrayList<AdministracaoSangue> itemsArrayList) {
        super(context, R.layout.child_administracao_sangue, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_administracao_sangue, parent, false);

        // 3. Get the two text view from the rowView
        textView_hora = (TextView) rowView.findViewById(R.id.textViewHoraAdministracaoSangue);
        EditText editText_tamin = (EditText) rowView.findViewById(R.id.editText_AdminSangue_TaMinAdminSangue);
        EditText editText_tamax = (EditText) rowView.findViewById(R.id.editText_AdminSangue_TaMax);
        EditText editText_fc = (EditText) rowView.findViewById(R.id.editText_AdminSangue_Fc);
        EditText editText_spo2 = (EditText) rowView.findViewById(R.id.editText_AdminSangue_Spo2AdminSangue);
        final Spinner spinner_tipo = (Spinner) rowView.findViewById(R.id.spinnerTipoAdminSangue);
        final EditText editText_codigo = (EditText) rowView.findViewById(R.id.editText_AdminSangue_codigo);

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
                            }

                        }, mHour, mMinute, true);

                dpd.show();
            }
        });
        // 4. Set the text for textView


        textView_hora.setText(itemsArrayList.get(position).getHora());
        editText_tamax.setText(itemsArrayList.get(position).getTaMax()+"");
        editText_fc.setText(itemsArrayList.get(position).getFc()+"");
        editText_spo2.setText(itemsArrayList.get(position).getSpo2()+"");

        int tipo = spinnerDaMeATuaPosicao(spinner_tipo, itemsArrayList.get(position).getTipo());
        if(tipo != -1)
            spinner_tipo.setSelection(tipo);
        else
            spinner_tipo.setSelection(0);


        editText_codigo.setText(getCodigo(tipo));
        editText_codigo.setEnabled(false);
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                itemsArrayList.get(position).setTipo(spinner_tipo.getItemAtPosition(posicao).toString());
                editText_codigo.setText(getCodigo(posicao));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        editText_tamin.addTextChangedListener(new EditTextWatcherTaMin(itemsArrayList.get(position)));
        editText_tamax.addTextChangedListener(new EditTextWatcherTaMax(itemsArrayList.get(position)));
        editText_fc.addTextChangedListener(new EditTextWatcherFC(itemsArrayList.get(position)));
        editText_spo2.addTextChangedListener(new EditTextWatcherSpo2(itemsArrayList.get(position)));


        // 5. retrn rowView
        return rowView;
    }

    public int spinnerDaMeATuaPosicao(Spinner s,String valor)
    {
        int resultado = -1;

        for(int i=0; i<s.getCount(); i++)
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
