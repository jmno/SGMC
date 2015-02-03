package info.mobilesgmc.medicacaoAdministrada;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.MedicacaoAdministrada;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherDoseMedicacaoAdministrada implements TextWatcher {


    private MedicacaoAdministrada medicacaoAdministrada;

    public EditTextWatcherDoseMedicacaoAdministrada(MedicacaoAdministrada data) {
        this.medicacaoAdministrada = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            Double d = Double.parseDouble(s.toString());
            medicacaoAdministrada.setDose(d);
        }
        catch (Exception e){
            medicacaoAdministrada.setDose(0.0);
            Log.i("ExcecaoDose", "Valor inferior a 0");
        }

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }


}