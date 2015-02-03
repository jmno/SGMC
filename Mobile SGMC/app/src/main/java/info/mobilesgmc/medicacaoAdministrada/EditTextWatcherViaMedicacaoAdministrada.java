package info.mobilesgmc.medicacaoAdministrada;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.MedicacaoAdministrada;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherViaMedicacaoAdministrada implements TextWatcher {


    private MedicacaoAdministrada medicacaoAdministrada;

    public EditTextWatcherViaMedicacaoAdministrada(MedicacaoAdministrada data) {
        this.medicacaoAdministrada = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            medicacaoAdministrada.setVia(s.toString());
        }
        catch (Exception e){
            medicacaoAdministrada.setVia("");
            Log.i("ExcecaoDose", "Nenhum Valor");
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