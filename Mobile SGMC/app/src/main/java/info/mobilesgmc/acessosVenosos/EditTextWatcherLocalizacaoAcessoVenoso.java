package info.mobilesgmc.acessosVenosos;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import info.mobilesgmc.modelo.AcessoVenoso;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherLocalizacaoAcessoVenoso implements TextWatcher {


    private AcessoVenoso acessoVenoso;

    public EditTextWatcherLocalizacaoAcessoVenoso(AcessoVenoso data) {
        this.acessoVenoso = data;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            acessoVenoso.setLocalizacaoAcessoVenoso(s.toString());
        }
        catch (Exception e){
            acessoVenoso.setLocalizacaoAcessoVenoso("");
            Log.i("ExcecaoLocalizacao", "Nenhum valor");
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