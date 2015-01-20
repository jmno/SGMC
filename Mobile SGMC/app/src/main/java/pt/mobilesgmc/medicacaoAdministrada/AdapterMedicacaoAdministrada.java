package pt.mobilesgmc.medicacaoAdministrada;

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

import pt.mobilesgmc.modelo.MedicacaoAdministrada;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterMedicacaoAdministrada extends ArrayAdapter<MedicacaoAdministrada> {

    private final Context context;
    private final ArrayList<MedicacaoAdministrada> itemsArrayList;
    private TextView textView_hora;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

    public AdapterMedicacaoAdministrada(Context context, ArrayList<MedicacaoAdministrada> itemsArrayList) {
        super(context, R.layout.child_medicacao_administrada, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_medicacao_administrada, parent, false);

        // 3. Get the two text view from the rowView
        textView_hora = (TextView) rowView.findViewById(R.id.textViewHoraMed);
        EditText editText_farmaco = (EditText) rowView.findViewById(R.id.editTextFarmaco);
        EditText editText_dose = (EditText) rowView.findViewById(R.id.editTextDose);
        EditText editText_via = (EditText) rowView.findViewById(R.id.editTextVia);

        textView_hora.setText(itemsArrayList.get(position).getHora());
        textView_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        editText_farmaco.addTextChangedListener(new EditTextWatcherFarmacoMedicacaoAdministrada(itemsArrayList.get(position)));
        editText_dose.addTextChangedListener(new EditTextWatcherDoseMedicacaoAdministrada(itemsArrayList.get(position)));
        editText_via.addTextChangedListener(new EditTextWatcherViaMedicacaoAdministrada(itemsArrayList.get(position)));

        // 5. retrn rowView
        return rowView;
    }


}
