package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UtentesActivity extends Activity {

	private ArrayAdapter<Utente> adaptadorUtente;
	public static ListView listaUtentes;
	private TextView txt_nomeUtente;
	private TextView txt_numProcessoUtente;
	private TextView txt_dataNascimentoUtente;
	private TextView txt_subSistemaUtente;
	private TextView txt_alergiasUtente;
	private TextView txt_patologiasUtente;
	private TextView txt_antecedentesUtente;
	private EditText inputSearch;
	private String token;
	private int idUtente = 0;
	ProgressDialog ringProgressDialog = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utentes);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

		listaUtentes = (ListView) findViewById(R.id.listViewUtentes);
		inputSearch = (EditText) findViewById(R.id.editText_findUtente);
		txt_nomeUtente = (TextView) findViewById(R.id.textViewNomeUtente);
		txt_numProcessoUtente = (TextView) findViewById(R.id.textViewNumProcessoUtente);
		txt_dataNascimentoUtente = (TextView) findViewById(R.id.textViewDataNascimentoUtente);
		txt_subSistemaUtente = (TextView) findViewById(R.id.textViewSubsistemaUtente);
		txt_alergiasUtente = (TextView) findViewById(R.id.textViewAlergiasUtente);
		txt_patologiasUtente = (TextView) findViewById(R.id.textViewPatologiasUtente);
		txt_antecedentesUtente = (TextView) findViewById(R.id.textViewAntecedentesUtente);
		listaUtentes.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Utente a = (Utente) listaUtentes.getItemAtPosition(arg2);
				txt_nomeUtente.setText(a.getNome().toString());
				txt_numProcessoUtente.setText(String.valueOf(a.getNumProcesso()));
				txt_dataNascimentoUtente.setText(a.getDataNascimento()
						.toString());
				txt_subSistemaUtente.setText(a.getSubsistema().toString());
				if (a.getAlergias().equals("null")
						|| a.getAlergias().equals("NULL")
						|| a.getAlergias().equals(""))
					txt_alergiasUtente.setText("N/A");
				else
					txt_alergiasUtente.setText(a.getAlergias().toString());

				if (a.getPatologias().equals("null")
						|| a.getPatologias().equals("NULL")
						|| a.getPatologias().equals(""))
					txt_patologiasUtente.setText("N/A");
				else
					txt_patologiasUtente.setText(a.getPatologias().toString());

				if (a.getAntecedentesCirurgicos().equals("null")
						|| a.getAntecedentesCirurgicos().equals("NULL")
						|| a.getAntecedentesCirurgicos().equals(""))
					txt_antecedentesUtente.setText("N/A");
				else
					txt_antecedentesUtente.setText(a
							.getAntecedentesCirurgicos().toString());

			}
		});
		idUtente = PreferenceManager
				.getDefaultSharedPreferences(
						getApplicationContext()).getInt("idUtente", 0);
		new getUtentes().execute(idUtente);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				adaptadorUtente.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

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

	private class getUtentes extends AsyncTask<Integer, Void, ArrayList<Utente>> {
		@Override
		protected void onPreExecute() {

			ringProgressDialog = new ProgressDialog(UtentesActivity.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Aguarde...");
			ringProgressDialog.setMessage("A carregar Dados...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(false);

			ringProgressDialog.show();
		};
		@Override
		protected ArrayList<Utente> doInBackground(Integer... params) {
			ArrayList<Utente> lista = null;

			try {
				lista = WebServiceUtils.getAllUtentes(token);
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		protected void onPostExecute(ArrayList<Utente> lista) {
			if (lista != null) {
				adaptadorUtente = new ArrayAdapter<Utente>(getBaseContext(),
						android.R.layout.simple_list_item_1, lista);
				adaptadorUtente.sort(new Comparator<Utente>() {

					@Override
					public int compare(Utente lhs, Utente rhs) {
						return lhs.getNome().toLowerCase()
								.compareTo(rhs.getNome().toLowerCase());
					}
				});
				listaUtentes.setAdapter(adaptadorUtente);
				if(idUtente!=0)
				populateList(idUtente);
				// new Notifications(getApplicationContext(),
				// "Connexão Efetuada com Sucesso!");
				Toast.makeText(getApplicationContext(),
						"Get Utentes successful!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Get Utentes unsuccessful...", Toast.LENGTH_LONG)
						.show();

			}
			ringProgressDialog.dismiss();
		}

		private void populateList(int idUtente) {
			
			int posicao=0;
			String nomeUtente = "";
		
			for(int i = 0; i < adaptadorUtente.getCount(); i++)
			{
				Utente novo = (Utente) listaUtentes.getItemAtPosition(i);
				int id = novo.getId();
				if (id == idUtente){
					posicao = i;
					nomeUtente = novo.getNome();
					listaUtentes.setSelection(posicao);
					adaptadorUtente.isEnabled(posicao);
				}
			}
			
			
			Utente a = (Utente) listaUtentes.getItemAtPosition(posicao);
			txt_nomeUtente.setText(a.getNome().toString());
			txt_numProcessoUtente.setText(String.valueOf(a.getNumProcesso()));
			txt_dataNascimentoUtente.setText(a.getDataNascimento()
					.toString());
			txt_subSistemaUtente.setText(a.getSubsistema().toString());
			if (a.getAlergias().equals("null")
					|| a.getAlergias().equals("NULL")
					|| a.getAlergias().equals(""))
				txt_alergiasUtente.setText("N/A");
			else
				txt_alergiasUtente.setText(a.getAlergias().toString());

			if (a.getPatologias().equals("null")
					|| a.getPatologias().equals("NULL")
					|| a.getPatologias().equals(""))
				txt_patologiasUtente.setText("N/A");
			else
				txt_patologiasUtente.setText(a.getPatologias().toString());

			if (a.getAntecedentesCirurgicos().equals("null")
					|| a.getAntecedentesCirurgicos().equals("NULL")
					|| a.getAntecedentesCirurgicos().equals(""))
				txt_antecedentesUtente.setText("N/A");
			else
				txt_antecedentesUtente.setText(a
						.getAntecedentesCirurgicos().toString());
			
		}
	}

}