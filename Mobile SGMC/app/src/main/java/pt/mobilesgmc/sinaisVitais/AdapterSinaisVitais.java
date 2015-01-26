package pt.mobilesgmc.sinaisVitais;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilegsmc.R;

import java.util.ArrayList;
import java.util.Calendar;

import pt.mobilesgmc.modelo.SinaisVitais;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterSinaisVitais extends ArrayAdapter<SinaisVitais> {

    private final Context context;
    private final ArrayList<SinaisVitais> itemsArrayList;
    private TextView textView_hora;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;


    public AdapterSinaisVitais(Context context, ArrayList<SinaisVitais> itemsArrayList) {

        super(context, R.layout.child_sinais_vitais, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_sinais_vitais, parent, false);

        // 3. Get the two text view from the rowView
        textView_hora = (TextView) rowView.findViewById(R.id.textView_Hora);
        EditText editText_tamin = (EditText) rowView.findViewById(R.id.editText_TaMin);
        EditText editText_tamax = (EditText) rowView.findViewById(R.id.editText_TaMax);
        EditText editText_fc = (EditText) rowView.findViewById(R.id.editText_fc);
        EditText editText_spo2 = (EditText) rowView.findViewById(R.id.editText_spo2);
        EditText editText_temp = (EditText) rowView.findViewById(R.id.editText_temp);

        // 4. Set the text for textView

        textView_hora.setText(itemsArrayList.get(position).getHora());
                textView_hora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final TextView horaSinalVital = (TextView) v.findViewById(R.id.textView_Hora);

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
                                horaSinalVital.setText(hr);
                            }

                        }, mHour, mMinute, true);

                dpd.show();
            }
        });

        editText_tamin.setText(itemsArrayList.get(position).getTaMin() + "");

        editText_tamax.setText(itemsArrayList.get(position).getTaMax()+"");
        editText_fc.setText(itemsArrayList.get(position).getFc()+"");
        editText_spo2.setText(itemsArrayList.get(position).getSpo2()+"");
        editText_temp.setText(itemsArrayList.get(position).getTemp()+"");

        editText_tamin.addTextChangedListener(new EditTextWatcherTaMin(itemsArrayList.get(position)));
        editText_tamax.addTextChangedListener(new EditTextWatcherTaMax(itemsArrayList.get(position)));
        editText_fc.addTextChangedListener(new EditTextWatcherFc(itemsArrayList.get(position)));
        editText_spo2.addTextChangedListener(new EditTextWatcherSpo2(itemsArrayList.get(position)));
        editText_temp.addTextChangedListener(new EditTextWatcherTem(itemsArrayList.get(position)));


        Log.i("tamin", itemsArrayList.get(position).getTaMin()+"");
        // 5. retrn rowView
        return rowView;
    }


}
