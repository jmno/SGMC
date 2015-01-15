package pt.mobilesgmc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import pt.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.sinaisVitais.AdapterSinaisVitais;
import pt.mobilesgmc.sinaisVitais.ChildSinaisVitais;
import pt.mobilesgmc.sinaisVitais.ParentSinaisVitais;

public class DadosINtraOperatorioActivity extends Activity {

	String token;
	ProgressDialog ringProgressDialog = null;
    private Spinner spinner_tipoAnestesia;
    private EditText editText_TET;
    private EditText editText_ML;
    private EditText editText_AgulhaCalibre;
    private ExpandableListView expandable_sinaisVitais;
    private AdapterSinaisVitais adaptadorSinaisVitais;
    private ArrayList<ParentSinaisVitais> listaParentSinaisVitais;
    private ArrayList<ChildSinaisVitais> listaChildSinaisVitais;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

/*
        spinner_tipoAnestesia = (Spinner) findViewById(R.id.spinner_DadosIntra_TipoAnestesia);
        editText_TET = (EditText) findViewById(R.id.editText_DadosIntra_TET);
        editText_ML = (EditText) findViewById(R.id.editText_DadosIntra_ML);
        editText_AgulhaCalibre = (EditText) findViewById(R.id.editText_DadosIntra_AgulhaPLCalibre);
        expandable_sinaisVitais = (ExpandableListView) findViewById(R.id.expandableListView_DadosIntra_SinaisVitais);
        ChildSinaisVitais c = new ChildSinaisVitais();
        c.setHora("10:10");
        ChildSinaisVitais b = new ChildSinaisVitais();
        b.setHora("10:10");
        ChildSinaisVitais a = new ChildSinaisVitais();
        a.setHora("10:10");
        listaChildSinaisVitais = new ArrayList<ChildSinaisVitais>();
        listaChildSinaisVitais.add(a);
        listaChildSinaisVitais.add(b);
        listaChildSinaisVitais.add(c);
        ParentSinaisVitais p = new ParentSinaisVitais();
        p.setSinaisVitais("Sinais Vitais");
        p.setChildren(listaChildSinaisVitais);
        listaParentSinaisVitais = new ArrayList<ParentSinaisVitais>();
        listaParentSinaisVitais.add(p);
        adaptadorSinaisVitais = new AdapterSinaisVitais(this,listaParentSinaisVitais);
        expandable_sinaisVitais.setAdapter(adaptadorSinaisVitais);
        expandable_sinaisVitais.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        expandable_sinaisVitais.setChoiceMode(ListView.CHOICE_MODE_SINGLE);*/

        //new verificaIntraOperatorio().execute();

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}





	private int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString().toLowerCase()
					.equals(myString.toLowerCase())) {
				index = i;
			}
		}
		return index;
	}

	private class verificaIntraOperatorio extends
			AsyncTask<String, Void, DadosIntraoperatorioFinal> {

		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(
					DadosINtraOperatorioActivity.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Please wait...");
			ringProgressDialog
					.setMessage("A verificar Dados Intra Operatórios...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(true);
			ringProgressDialog.show();
		};

		@Override
		protected DadosIntraoperatorioFinal doInBackground(String... params) {
			DadosIntraoperatorioFinal dados = new DadosIntraoperatorioFinal();

			try {
				dados = WebServiceUtils.verificaIntraOperatorioID(token,
						HomeActivity.getCirurgia().getId());

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return dados;
		}

		@Override
		protected void onPostExecute(DadosIntraoperatorioFinal dados) {
			if (dados != null) {

				ringProgressDialog.dismiss();
				//preencherAtividade(dados);

				// Log.i("Dados",
				// dados.getAdministracao().getFc15minAposTransfusao()+"");
				// adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
				// getBaseContext(), android.R.layout.simple_list_item_1,
				// lista);
				// adaptadorEquipa.sort(new Comparator<EquipaComJuncao>() {
				//
				// @Override
				// public int compare(EquipaComJuncao lhs, EquipaComJuncao rhs)
				// {
				// return lhs.getNomeEquipa().toLowerCase()
				// .compareTo(rhs.getNomeEquipa().toLowerCase());
				// }
				// });
				// listaEquipas.setAdapter(adaptadorEquipa);
				// dialogoEquipas.show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Erro Verificar IntraOperatorio - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
