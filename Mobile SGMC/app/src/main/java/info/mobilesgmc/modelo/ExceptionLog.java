package info.mobilesgmc.modelo;

import android.util.Log;

/**
 * Created by Nicolau on 27/01/15.
 */
public class ExceptionLog extends Exception {
    public ExceptionLog(String message){

        super(message);
        Log.i("ExceptionLog", message);
    }
}


