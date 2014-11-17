package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.ParseException;
import org.json.JSONException;

import pt.mobilesgmc.modelo.Notifications;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.Tipo;
import pt.mobilesgmc.modelo.WebServiceUtils;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilegsmc.R;

public class EquipaCirurgica extends Activity {

	private ArrayAdapter<ProfissonalSaude> adapter;
	private ArrayAdapter<Tipo> adaptadorTipo;
	private int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipa_cirurgica);
		new getProfissionaisSaude().execute();
		new getTipo().execute();
		Button btnWebservice = (Button) findViewById(R.id.btn_TesteWebservice);

		btnWebservice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new getProfissionaisSaude().execute();

			}
		});

		Button btnAdd = (Button) findViewById(R.id.btn_AdicionarProfissional);
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create a Dialog component
				final Dialog dialog = new Dialog(EquipaCirurgica.this);

				// tell the Dialog to use the dialog.xml as it's layout
				// description
				dialog.setContentView(R.layout.dialog_novoprofissional);
				dialog.setTitle("Defina o Novo Profissional:");
				final EditText nomeEditText = (EditText) dialog
						.findViewById(R.id.editText_DialogNovoProfissional_Nome);
				final Spinner spinnerTipo = (Spinner) dialog
						.findViewById(R.id.spinner_DialogNovoProfissional_Tipo);
				final EditText ccEditText = (EditText) dialog
						.findViewById(R.id.editText_DialogNovoProfissional_cc);
				ArrayList<String> lista = new ArrayList<>(Arrays.asList(
						"Medico", "Enfermeiro", "Tótó"));
				spinnerTipo.setAdapter(adaptadorTipo);
				Button guardar = (Button) dialog
						.findViewById(R.id.btn_DialogNovoProfissional_Guardar);

				guardar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ProfissonalSaude p = new ProfissonalSaude();
						
						p.setNome(nomeEditText.getText().toString());
						
						p.setCc (ccEditText.getText().toString());
						p.setTipo((Tipo)spinnerTipo.getSelectedItem());
						try {
							new adicionarProfissionalSaude()
									.execute(p);
						} catch (Exception e) {

						}
						dialog.dismiss();
					}
				});

				Button cancelar = (Button) dialog
						.findViewById(R.id.btn_DialogNovoProfissional_Cancelar);
				cancelar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.equipa_cirurgica, menu);
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

	private class getProfissionaisSaude extends
			AsyncTask<String, Void, ArrayList<ProfissonalSaude>> {

		@Override
		protected ArrayList<ProfissonalSaude> doInBackground(String... params) {
			ArrayList<ProfissonalSaude> lista = null;

			try {
				lista = WebServiceUtils.getAllProfissionalSaude();
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<ProfissonalSaude> lista) {
			if (lista != null) {
				adapter = new ArrayAdapter<ProfissonalSaude>(getBaseContext(),
						android.R.layout.simple_list_item_1, lista);
				Spinner spinnerCirurgião = (Spinner) findViewById(R.id.spinner_Cirurgião);
				spinnerCirurgião.setAdapter(adapter);
				new Notifications(getApplicationContext(),
						"Connexão Efetuada com Sucesso!");
				Toast.makeText(getApplicationContext(),
						"Get ProfissionaisSaude successful!", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Get ProfissionaisSaude unsuccessful...",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	private class adicionarProfissionalSaude extends
			AsyncTask<ProfissonalSaude, Void, Boolean> {

		@Override
		protected Boolean doInBackground(ProfissonalSaude... params) {
			Boolean adicionou = false;

			try {
				adicionou = WebServiceUtils.adicionarProfissionalSaude(params[0]);
			} catch (ParseException | IOException | JSONException
					| RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return adicionou;
		}

	}

	private class getTipo extends
			AsyncTask<String, Void, ArrayList<Tipo>> {

		@Override
		protected ArrayList<Tipo> doInBackground(String... params) {
			ArrayList<Tipo> lista = null;

			try {
				lista = WebServiceUtils.getAllTipo();
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<Tipo> lista) {
			if (lista != null) {
				adaptadorTipo = new ArrayAdapter<Tipo>(getBaseContext(),
						android.R.layout.simple_list_item_1, lista);
				
				new Notifications(getApplicationContext(),
						"Connexão Efetuada com Sucesso!");
				Toast.makeText(getApplicationContext(),
						"Get Tipo successful!", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Get Tipo unsuccessful...",
						Toast.LENGTH_LONG).show();

			}
		}

	}
}
