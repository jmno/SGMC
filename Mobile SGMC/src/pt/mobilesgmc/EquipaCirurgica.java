package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.ParseException;
import org.json.JSONException;

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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilegsmc.R;

//import pt.mobilesgmc.modelo.Notifications;

public class EquipaCirurgica extends Activity {
	private int idTipo;
	private ArrayAdapter<Tipo> adaptadorTipo;
	private ArrayAdapter<ProfissonalSaude> adaptador;
	private Spinner spinnerTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipa_cirurgica);
		// new getProfissionaisSaude().execute();
		atualizaAGui();
		
		Button btnAdd = (Button) findViewById(R.id.btn_AdicionarProfissional);
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new getTipo().execute();
				// create a Dialog component
				final Dialog dialog = new Dialog(EquipaCirurgica.this);

				// tell the Dialog to use the dialog.xml as it's layout
				// description
				dialog.setContentView(R.layout.dialog_novoprofissional);
				dialog.setTitle("Defina o Novo Profissional:");
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				final EditText nomeEditText = (EditText) dialog
						.findViewById(R.id.editText_DialogNovoProfissional_Nome);
				spinnerTipo = (Spinner) dialog
						.findViewById(R.id.spinner_DialogNovoProfissional_Tipo);
				final EditText ccEditText = (EditText) dialog
						.findViewById(R.id.editText_DialogNovoProfissional_cc);

				Button guardar = (Button) dialog
						.findViewById(R.id.btn_DialogNovoProfissional_Guardar);

				guardar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!nomeEditText.getText().equals("")){
						ProfissonalSaude p = new ProfissonalSaude();

						p.setNome(nomeEditText.getText().toString());

						p.setCc(ccEditText.getText().toString());
						Tipo ti = (Tipo) spinnerTipo.getSelectedItem();
						p.setIdTipo(ti.getId());
						try {
							new adicionarProfissionalSaude().execute(p);
							atualizaAGui();
						} catch (Exception e) {

						}}
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

	private void atualizaAGui() {
		new getProfissionaisSaudeByTipo().execute(1);
		new getProfissionaisSaudeByTipo().execute(4);
		new getProfissionaisSaudeByTipo().execute(5);
		new getProfissionaisSaudeByTipo().execute(7);
		new getProfissionaisSaudeByTipo().execute(8);
		new getProfissionaisSaudeByTipo().execute(9);
		new getProfissionaisSaudeByTipo().execute(6);

		
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

	// private class getProfissionaisSaude extends
	// AsyncTask<String, Void, ArrayList<ProfissonalSaude>> {
	//
	// @Override
	// protected ArrayList<ProfissonalSaude> doInBackground(String... params) {
	// ArrayList<ProfissonalSaude> lista = null;
	//
	// try {
	// lista = WebServiceUtils.getAllProfissionalSaude();
	// } catch (IOException | RestClientException | ParseException
	// | JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return lista;
	// }
	//
	// @Override
	// protected void onPostExecute(ArrayList<ProfissonalSaude> lista) {
	// if (lista != null) {
	// adapter = new ArrayAdapter<ProfissonalSaude>(getBaseContext(),
	// android.R.layout.simple_list_item_1, lista);
	// Spinner spinnerCirurgião = (Spinner)
	// findViewById(R.id.spinner_Cirurgião);
	// spinnerCirurgião.setAdapter(adapter);
	// // new Notifications(getApplicationContext(),
	// // "Connexão Efetuada com Sucesso!");
	// Toast.makeText(getApplicationContext(),
	// "Get ProfissionaisSaude successful!", Toast.LENGTH_LONG)
	// .show();
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Get ProfissionaisSaude unsuccessful...",
	// Toast.LENGTH_LONG).show();
	//
	// }
	// }
	//
	// }

	private class adicionarProfissionalSaude extends
			AsyncTask<ProfissonalSaude, Void, Boolean> {

		@Override
		protected Boolean doInBackground(ProfissonalSaude... params) {
			Boolean adicionou = false;

			try {
				adicionou = WebServiceUtils
						.adicionarProfissionalSaude(params[0]);
			} catch (ParseException | IOException | JSONException
					| RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return adicionou;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String a = (result ? "Profissional Adicionado com Sucesso!"
					: "Profissional Não Adicionado!");
			Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
					.show();
			super.onPostExecute(result);
		}

	}

	private class getTipo extends AsyncTask<String, Void, ArrayList<Tipo>> {

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
				spinnerTipo.setAdapter(adaptadorTipo);
				// new Notifications(getApplicationContext(),
				// "Connexão Efetuada com Sucesso!");
				Toast.makeText(getApplicationContext(), "Get Tipo successful!",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Get Tipo unsuccessful...", Toast.LENGTH_LONG).show();

			}
		}

	}

	private class getProfissionaisSaudeByTipo extends
			AsyncTask<Integer, Void, ArrayList<ProfissonalSaude>> {

		@Override
		protected ArrayList<ProfissonalSaude> doInBackground(Integer... params) {
			ArrayList<ProfissonalSaude> lista = null;

			try {
				lista = WebServiceUtils
						.getAllProfissionalSaudeByIdTipo(params[0]);
				idTipo = params[0];
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<ProfissonalSaude> lista) {
			if (lista != null) {

				populateSpinners(lista, idTipo);

			} else {
				Toast.makeText(getApplicationContext(),
						"Get ProfissionaisSaude unsuccessful...",
						Toast.LENGTH_LONG).show();

			}
		}
	}

	public void populateSpinners(ArrayList<ProfissonalSaude> lista, int id) {
		adaptador = new ArrayAdapter<ProfissonalSaude>(getBaseContext(),
				android.R.layout.simple_list_item_1, lista);
		switch (id) {
		case 1:
			Spinner spinnerCirurgião = (Spinner) findViewById(R.id.spinner_Cirurgião);
			spinnerCirurgião.setAdapter(adaptador);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			Spinner spinnerPrimAjudante = (Spinner) findViewById(R.id.spinner_1Ajudante);
			spinnerPrimAjudante.setAdapter(adaptador);
			Spinner spinnerSegundoAjudante = (Spinner) findViewById(R.id.spinner_2Ajudante);
			spinnerSegundoAjudante.setAdapter(adaptador);
			Spinner spinnerTerceiroAjudante = (Spinner) findViewById(R.id.spinner_3Ajudante);
			spinnerTerceiroAjudante.setAdapter(adaptador);

			break;
		case 5:
			Spinner spinnerAnestesista = (Spinner) findViewById(R.id.spinner_Anestesista);
			spinnerAnestesista.setAdapter(adaptador);
			break;
		case 6:
			Spinner spinnerAssistente = (Spinner) findViewById(R.id.spinner_Assistente_Operacional);
			spinnerAssistente.setAdapter(adaptador);
			break;
		case 7:
			Spinner spinnerEnfermeiroinstrumentista = (Spinner) findViewById(R.id.spinner_Enf_Instrumentista);
			spinnerEnfermeiroinstrumentista.setAdapter(adaptador);
			break;
		case 8:
			Spinner spinnerEnfermeiroCiruculante = (Spinner) findViewById(R.id.spinner_Enf_Circulante);
			spinnerEnfermeiroCiruculante.setAdapter(adaptador);
			break;
		case 9:
			Spinner spinnerEnfermeiroAnestesia = (Spinner) findViewById(R.id.spinner_Enf_de_Anestesia);
			spinnerEnfermeiroAnestesia.setAdapter(adaptador);
			break;

		}

	}

}
