package info.mobilesgmc.adminSangue;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.AdministracaoSangue;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherCodigo implements TextWatcher {


    private AdministracaoSangue administracaoSangue;

    public EditTextWatcherCodigo(AdministracaoSangue data) {
        this.administracaoSangue = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            administracaoSangue.setCodigo(s.toString());
        }
        catch (Exception e){
            administracaoSangue.setCodigo("");
            Log.i("ExcecaoCodigo", "Valor");
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