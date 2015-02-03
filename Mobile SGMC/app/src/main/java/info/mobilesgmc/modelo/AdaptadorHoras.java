package info.mobilesgmc.modelo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import info.nicolau.mobilegsmc.R;

/**
 * Created by Nicolau on 06/01/15.
 */
public class AdaptadorHoras extends ArrayAdapter<Horas> {
    private final Context context;
    private final ArrayList<Horas> itemsArrayList;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private String dataAux;
    public AdaptadorHoras(Context context, ArrayList<Horas> itemsArrayList) {

        super(context, R.layout.layout_rowhoras, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.layout_rowhoras, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        final TextView valueView = (TextView) rowView.findViewById(R.id.value);

        valueView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAux = "";
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                dataAux =(year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth);
                                TimePickerDialog dpd = new TimePickerDialog(context,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view,
                                                                  int hourOfDay, int minute) {
                                                dataAux+=" "+hourOfDay + ":"
                                                        + minute + ":00";
                                                itemsArrayList.get(position).setDescription(dataAux);
                                                valueView.setText(dataAux);
                                            }

                                        }, mHour, mMinute, true);

                                dpd.show();
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();





            }
        });



        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());

        // 5. retrn rowView
        return rowView;
    }
}
