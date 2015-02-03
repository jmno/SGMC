package info.mobilesgmc.adminSangue;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.AdministracaoSangue;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherSpo2 implements TextWatcher {


    private AdministracaoSangue administracaoSangue;

    public EditTextWatcherSpo2(AdministracaoSangue data) {
        this.administracaoSangue = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            Double d = Double.parseDouble(s.toString());
            administracaoSangue.setSpo2(d);
        }
        catch (Exception e){
            administracaoSangue.setSpo2(0.0);
            Log.i("ExcecaoSpo2", "Valor inferior a 0");
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