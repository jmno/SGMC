package info.mobilesgmc.biopsia;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.PecaBiopsia;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherLaboratorioPecaBiopsia implements TextWatcher {


    private PecaBiopsia pecaBiopsia;

    public EditTextWatcherLaboratorioPecaBiopsia(PecaBiopsia data) {
        this.pecaBiopsia = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            pecaBiopsia.setLaboratotio(s.toString());
        }
        catch (Exception e){
            pecaBiopsia.setLaboratotio("");
            Log.i("ExcecaoLaboratorio", "Erro valor");
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