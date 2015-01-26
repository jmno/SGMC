package pt.mobilesgmc.sinaisVitais;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import pt.mobilesgmc.modelo.SinaisVitais;

/**
 * Created by Nicolau on 20/01/15.
 */
public class EditTextWatcherTaMax implements TextWatcher{


        private SinaisVitais sinaisVitais;

        public EditTextWatcherTaMax(SinaisVitais data) {
            this.sinaisVitais = data;
        }

        @Override
        public void afterTextChanged(Editable s) {
            Double d = 0.0;
            try{
                d = Double.parseDouble(s.toString());
                sinaisVitais.setTaMax(Double.parseDouble(s.toString()));
            }
            catch (Exception e){
                sinaisVitais.setTaMax(0.0);
                Log.i("ExcecaoTAmax","Valor Inferior a 0");
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
