package pt.mobilesgmc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import pt.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.SinaisVitais;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.sinaisVitais.AdapterSinaisVitais;

public class DadosINtraOperatorioActivity extends Activity {

	String token;
	ProgressDialog ringProgressDialog = null;
    private Spinner spinner_tipoAnestesia;
    private EditText editText_TET;
    private EditText editText_ML;
    private EditText editText_AgulhaCalibre;
    private ListView listView_SinaisVitais;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private ArrayList<SinaisVitais> itemsSinaisVitais;
    private AdapterSinaisVitais adapterSinaisVitais;
    private TextView horaSinalVital;
    private ImageView adicionarSinalVital;



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");


        spinner_tipoAnestesia = (Spinner) findViewById(R.id.spinner_DadosIntra_TipoAnestesia);
        editText_TET = (EditText) findViewById(R.id.editText_DadosIntra_TET);
        editText_ML = (EditText) findViewById(R.id.editText_DadosIntra_ML);
        editText_AgulhaCalibre = (EditText) findViewById(R.id.editText_DadosIntra_AgulhaPLCalibre);
        listView_SinaisVitais = (ListView) findViewById(R.id.listView_DadosIntra_SinaisVitais);
        SinaisVitais s = new SinaisVitais();
        s.setFc(1.2);
        s.setHora("10:10");
        s.setSpo2(2);
        s.setTamax(2.1);
        s.setTamin(2.2);
        s.setTemp(36);
        SinaisVitais s1 = new SinaisVitais();
        s1.setFc(1.2);
        s1.setHora("10:10");
        s1.setSpo2(2);
        s1.setTamax(2.1);
        s1.setTamin(2.2);
        s1.setTemp(36);
        itemsSinaisVitais = new ArrayList<SinaisVitais>();
        itemsSinaisVitais.add(s);
        itemsSinaisVitais.add(s1);
        setListViewHeightBasedOnChildren(listView_SinaisVitais);
        adapterSinaisVitais = new AdapterSinaisVitais(this,itemsSinaisVitais);
        listView_SinaisVitais.setAdapter(adapterSinaisVitais);
        adicionarSinalVital = (ImageView) findViewById(R.id.imageView_DadosIntra_AddSinalVital);
        adicionarSinalVital.setClickable(true);
        adicionarSinalVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_SinaisVitais.getVisibility() == View.VISIBLE){
                listView_SinaisVitais.setVisibility(View.GONE);
                }
                else
                    listView_SinaisVitais.setVisibility(View.VISIBLE);
            }
        });

        listView_SinaisVitais.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


        //new verificaIntraOperatorio().execute();

	}

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
