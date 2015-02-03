package info.mobilesgmc.medicacaoAdministrada;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.MedicacaoAdministrada;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherFarmacoMedicacaoAdministrada implements TextWatcher {


    private MedicacaoAdministrada medicacaoAdministrada;

    public EditTextWatcherFarmacoMedicacaoAdministrada(MedicacaoAdministrada data) {
        this.medicacaoAdministrada = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            medicacaoAdministrada.setFarmaco(s.toString());
        }
        catch (Exception e){
            medicacaoAdministrada.setFarmaco("");
            Log.i("ExcecaoFarmaco", "Nenhum Valor");
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