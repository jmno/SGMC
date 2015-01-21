package pt.mobilesgmc.dreanagem;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import pt.mobilesgmc.modelo.Drenagem;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherDrenagem implements TextWatcher {


    private Drenagem drenagem;

    public EditTextWatcherDrenagem(Drenagem data) {
        this.drenagem = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            drenagem.setDrenagem(s.toString());
        }
        catch (Exception e){
            drenagem.setDrenagem("");
            Log.i("ExcecaoDrenagem", "Valor inferior a 0");
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