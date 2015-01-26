package pt.mobilesgmc.acessosVenosos;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import pt.mobilesgmc.modelo.AcessoVenoso;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherTipoAcessoVenoso implements TextWatcher {


    private AcessoVenoso acessoVenoso;

    public EditTextWatcherTipoAcessoVenoso(AcessoVenoso data) {
        this.acessoVenoso = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            acessoVenoso.setTipoAcessoVenoso(s.toString());
        }
        catch (Exception e){
            acessoVenoso.setTipoAcessoVenoso("");
            Log.i("ExcecaoTipo", "Nenhum Valor");
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