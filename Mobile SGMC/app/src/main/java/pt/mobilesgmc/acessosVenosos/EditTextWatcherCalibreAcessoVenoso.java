package pt.mobilesgmc.acessosVenosos;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import pt.mobilesgmc.modelo.AcessoVenoso;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherCalibreAcessoVenoso implements TextWatcher {


    private AcessoVenoso acessoVenoso;

    public EditTextWatcherCalibreAcessoVenoso(AcessoVenoso data) {
        this.acessoVenoso = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            Double d = Double.parseDouble(s.toString());
            acessoVenoso.setCalibreAcessoVenoso(d);
        }
        catch (Exception e){
            acessoVenoso.setCalibreAcessoVenoso(0.0);
            Log.i("ExcecaoCalibre", "Inferior a Zero");
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