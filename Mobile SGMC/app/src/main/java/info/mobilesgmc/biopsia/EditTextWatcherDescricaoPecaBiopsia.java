package info.mobilesgmc.biopsia;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.PecaBiopsia;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherDescricaoPecaBiopsia implements TextWatcher {


    private PecaBiopsia pecaBiopsia;

    public EditTextWatcherDescricaoPecaBiopsia(PecaBiopsia data) {
        this.pecaBiopsia = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            pecaBiopsia.setDescricao(s.toString());
        }
        catch (Exception e){
            pecaBiopsia.setDescricao("");
            Log.i("ExcecaoDescricao", "Erro valor");
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