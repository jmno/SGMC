package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.ParseException;
import org.json.JSONException;

import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.Tipo;
import pt.mobilesgmc.modelo.Utente;
import pt.mobilesgmc.modelo.WebServiceUtils;

import com.example.mobilegsmc.R;
import com.example.mobilegsmc.R.id;
import com.example.mobilegsmc.R.layout;
import com.example.mobilegsmc.R.menu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class UtentesActivity extends Activity {
	
	private ArrayAdapter<Utente> adaptadorUtente;
	private ListView listaUtentes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utentes);
		
		listaUtentes = (ListView) findViewById(R.id.listViewUtentes);
		
		new getUtentes().execute();
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.utentes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class getUtentes extends AsyncTask<String, Void, ArrayList<Utente>> {

		@Override
		protected ArrayList<Utente> doInBackground(String... params) {
			ArrayList<Utente> lista = null;

			try {
				lista = WebServiceUtils.getAllUtentes();
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}}
	
	
	protected void onPostExecute(ArrayList<Utente> lista) {
		if (lista != null) {
			adaptadorUtente = new ArrayAdapter<Utente>(getBaseContext(),
					android.R.layout.simple_list_item_1, lista);
			listaUtentes.setAdapter(adaptadorUtente);
			// new Notifications(getApplicationContext(),
			// "Connexão Efetuada com Sucesso!");
			Toast.makeText(getApplicationContext(), "Get Utentes successful!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					"Get Utentes unsuccessful...", Toast.LENGTH_LONG).show();

		}
	}


}
