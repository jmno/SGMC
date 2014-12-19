package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import org.apache.http.ParseException;
import org.json.JSONException;

import pt.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import pt.mobilesgmc.modelo.EquipaComJuncao;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony.Sms.Conversations;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;

public class DadosINtraOperatorioActivity extends Activity {
	private TableLayout tableSinaisVitais;
	private TableRow tableRowSinaisVitais;
	private TableLayout tableMedicacaoAdministrada;
	private TableRow tableRowMedicacaoAdministrada;
	private TableLayout tableBalancoHidrico;
	private TableRow tableRowBalancoHidrico;
	private TableLayout tableDrenagemVesical;
	private TableRow tableRowDrenagemVesical;
	private TableLayout tableDrenagemNasogastrica;
	private TableRow tableRowDrenagemNasogastrica;
	private TextView texto;
	private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

	private Spinner spinnerTipoAnestesia;
	private EditText editTextTET;
	private EditText editTextML;
	private EditText editTextAgulhaPlCalibre;
	private EditText editTextTipo;
	private EditText editTextCalibre;
	private EditText editTextLocalizacaoAcessoVenoso;
	private TextView textViewHoraSinais1;
	private EditText editTextTa1;
	private EditText editTextFc1;
	private EditText editTextSpo2_1;
	private EditText editTextTemp1;
	private TextView textViewHoraSinais2;
	private EditText editTextTa2;
	private EditText editTextFc2;
	private EditText editTextSpo2_2;
	private EditText editTextTemp2;
	private TextView textViewHoraSinais3;
	private EditText editTextTa3;
	private EditText editTextFc3;
	private EditText editTextSpo2_3;
	private EditText editTextTemp3;
	private TextView textViewHoraFarmaco1;
	private TextView textViewHoraFarmaco2;
	private TextView textViewHoraFarmaco3;
	private EditText editTextFarmaco1;
	private EditText editTextFarmaco2;
	private EditText editTextFarmaco3;
	private EditText editTextFarmaco4;
	private EditText editTextFarmaco5;
	private EditText editTextFarmaco6;
	private EditText editTextFarmaco7;
	private EditText editTextFarmaco8;
	private TextView textViewHoraInicioTransfusao;
	private EditText editTextTaInicioTransfusao;
	private EditText editTexfcInicioTransfusao;
	private EditText editTextspo2InicioTransfusao;
	private TextView textViewHora15MinAposTransfusao;
	private EditText editTextTa15MinTransfusao;
	private EditText editTexfc15MinTransfusao;
	private EditText editTextspo215MinTransfusao;
	private TextView textViewHoraFimTransfusao;
	private EditText editTextTaFimTransfusao;
	private EditText editTexfcFimTransfusao;
	private EditText editTextspo2FimTransfusao;
	private Spinner spinnerPosicaoOperatoria;
	private EditText editTextAlivioZonaPressao;
	private EditText editTextLocalAlivioZonaPressao;
	private Spinner spinnerMantaTermica;
	private EditText editTextLocalMantaTermica;
	private EditText editTextPressaoGarrote;
	private Spinner spinnerLocalizacaoGarrote;
	private TextView textViewHoraInicioGarrote;
	private TextView textViewHoraFimGarrote;
	private Spinner spinnerPlacaEletrodo;
	private EditText editTextPecaBiopsiaDescricao;
	private EditText editTextPecaBiopsiaLaboratorio;
	private Spinner spinnerLocalizacaoEletrodo;
	private TextView textoHora;
	private int tamanhoPadding;
	private LinearLayout layoutIntra;
	FlyOutContainer root;
	String token;
	private ScrollView scrollLayoutDados;
	private Button buttonGuardarDadosIntraOperatorio;
	ProgressDialog ringProgressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_dados_intra_operatorio, null);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;
		int margin = (80 * (int) dpWidth) / 100;
		root.setMargin(margin);
		this.setContentView(root);

		scrollLayoutDados = (ScrollView) root
				.findViewById(R.id.scrollViewDadosIntraOperatorio);

		scrollLayoutDados.setOnTouchListener(new OnSwipeTouchListener(
				getApplicationContext()) {
			public void onSwipeRight() {
				String estado = root.getState().toString();
				if (estado.equals("CLOSED"))
					toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));
				// Toast.makeText(SampleActivity.this, "right",
				// Toast.LENGTH_SHORT).show();
			}

			public void onSwipeLeft() {
				String estado = root.getState().toString();
				if (estado.equals("OPEN"))
					toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));
			}

			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}

		});

		spinnerTipoAnestesia = (Spinner) findViewById(R.id.spinner_TipoAnestesia);
		editTextTET = (EditText) findViewById(R.id.editText_TETn);
		editTextML = (EditText) findViewById(R.id.editText_ML_n);
		editTextAgulhaPlCalibre = (EditText) findViewById(R.id.editText_Agulha_PL_Calibre);
		editTextTipo = (EditText) findViewById(R.id.editText_Tipo);
		editTextCalibre = (EditText) findViewById(R.id.editText_Calibre);
		editTextLocalizacaoAcessoVenoso = (EditText) findViewById(R.id.editText_Localizacao);

		textViewHoraInicioTransfusao = (TextView) findViewById(R.id.txtViewHoraInicioTransfusao);
		editTextTaInicioTransfusao = (EditText) findViewById(R.id.editTextTAAdministracaoSangue);
		editTexfcInicioTransfusao = (EditText) findViewById(R.id.editTextFCAdministracaoSangue);
		editTextspo2InicioTransfusao = (EditText) findViewById(R.id.editTextSPAdministracaoSangue);
		textViewHora15MinAposTransfusao = (TextView) findViewById(R.id.txtViewHora15MInicioTransfusao);
		editTextTa15MinTransfusao = (EditText) findViewById(R.id.editTextTA15MAdministracaoSangue);
		editTexfc15MinTransfusao = (EditText) findViewById(R.id.editTextFC15MAdministracaoSangue);
		editTextspo215MinTransfusao = (EditText) findViewById(R.id.editTextSP15MAdministracaoSangue);
		textViewHoraFimTransfusao = (TextView) findViewById(R.id.txtViewHora15MInicioTransfusao);
		editTextTaFimTransfusao = (EditText) findViewById(R.id.editTextTAFMAdministracaoSangue);
		editTexfcFimTransfusao = (EditText) findViewById(R.id.editTextFCFMAdministracaoSangue);
		editTextspo2FimTransfusao = (EditText) findViewById(R.id.editTextSPFMAdministracaoSangue);
		spinnerPosicaoOperatoria = (Spinner) findViewById(R.id.spinnerPosicOperatoria);
		editTextAlivioZonaPressao = (EditText) findViewById(R.id.editTextZonasdePressao);
		editTextLocalAlivioZonaPressao = (EditText) findViewById(R.id.editTextLocal);
		spinnerMantaTermica = (Spinner) findViewById(R.id.spinnerMantaTermica);
		editTextLocalMantaTermica = (EditText) findViewById(R.id.editTextLocalMantaTermica);
		spinnerLocalizacaoGarrote = (Spinner) findViewById(R.id.spinnerLocalGarrote);
		textViewHoraInicioGarrote = (TextView) findViewById(R.id.txtViewHoraInicioGarrote);
		textViewHoraFimGarrote = (TextView) findViewById(R.id.txtViewHoraFimGarrote);
		editTextPressaoGarrote = (EditText) findViewById(R.id.editTextPressaoGarrote);
		spinnerPlacaEletrodo = (Spinner) findViewById(R.id.spinnerPlacaElectrodo);
		spinnerLocalizacaoEletrodo = (Spinner) findViewById(R.id.spinnerLocalGarrote);
		editTextPecaBiopsiaDescricao = (EditText) findViewById(R.id.editTextDescricaoBiopsia);
		editTextPecaBiopsiaLaboratorio = (EditText) findViewById(R.id.editTextLaboratorioBiopsia);
		layoutIntra = (LinearLayout) findViewById(R.id.layoutIntra);
		tamanhoPadding = layoutIntra.getPaddingTop();

		// carregarTimes();

		tableSinaisVitais = (TableLayout) findViewById(R.id.tableLayout_SinaisVitais);
		tableMedicacaoAdministrada = (TableLayout) findViewById(R.id.tableLayout_MedicacaoAdministrada);
		tableBalancoHidrico = (TableLayout) findViewById(R.id.tableLayout_BalancoHidrico);
		tableDrenagemVesical = (TableLayout) findViewById(R.id.tableLayout_DrenagemCataterismo);
		tableDrenagemNasogastrica = (TableLayout) findViewById(R.id.tableLayout_DrenagemNasogastrica);
		// texto = (TextView) findViewById(R.id.txtview_sinaisVitais);
		// texto.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// addTableRowSinaisVitais();
		// addTableRowMedicacaoAdministrada();
		// addTableRowBalancoHidrico();
		// addTableRowDrenagemVesical();
		// addTableRowDrenagemNasogastrica();
		// }
		// });

		buttonGuardarDadosIntraOperatorio = (Button) findViewById(R.id.buttonGuardarDadosIntraOperatorio);
		buttonGuardarDadosIntraOperatorio
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String a = "";
						// a +=
						// spinnerTipoAnestesia.getSelectedItem().toString();
						// a += editTextTET.getText().toString();
						// a += editTextML.getText().toString();
						// a += editTextAgulhaPlCalibre.getText();
						// a += editTextTipo.getText().toString();
						// a += editTextCalibre.getText().toString();
						// a +=
						// editTextLocalizacaoAcessoVenoso.getText().toString();
						// a +=
						// textViewHoraInicioTransfusao.getText().toString();

						a += getSinaisVitais();
						a += getMedicacaoAdministrada();
						a += getBalancoHidrico();
						a += getDrenagemVesical();
						a += getDrenagemNasoastrica();

						Log.i("IntraOperatorio", a);
					}
				});

		new verificaIntraOperatorio().execute();

	}

	public String getSinaisVitais() {
		String a = "";
		for (int i = 1, j = tableSinaisVitais.getChildCount(); i < j; i++) {
			TableRow row = (TableRow) tableSinaisVitais.getChildAt(i);

			TextView texto = (TextView) row.getChildAt(0);
			EditText texto1 = (EditText) row.getChildAt(1);
			EditText texto2 = (EditText) row.getChildAt(2);
			EditText texto3 = (EditText) row.getChildAt(3);
			EditText texto4 = (EditText) row.getChildAt(4);
			a += "\n";
			a += texto.getText();
			a += "\n";
			a += texto1.getText();
			a += "\n";
			a += texto2.getText();
			a += "\n";
			a += texto3.getText();
			a += "\n";
			a += texto4.getText();
			a += "\n";

		}
		return a;
	}

	public String getMedicacaoAdministrada() {
		String a = "";
		for (int i = 0, j = tableMedicacaoAdministrada.getChildCount(); i < j; i++) {
			TableRow row = (TableRow) tableMedicacaoAdministrada.getChildAt(i);

			if (i == 0) {
				TextView hora1 = (TextView) row.getChildAt(1);
				TextView hora2 = (TextView) row.getChildAt(2);
				TextView hora3 = (TextView) row.getChildAt(3);
				a += "\n";
				a += hora1.getText();
				a += "\n";
				a += hora2.getText();
				a += "\n";
				a += hora3.getText();
			}

			else {
				EditText texto = (EditText) row.getChildAt(0);
				CheckBox check1 = (CheckBox) row.getChildAt(1);
				CheckBox check2 = (CheckBox) row.getChildAt(2);
				CheckBox check3 = (CheckBox) row.getChildAt(3);
				a += "\n";
				a += texto.getText();
				a += "\n";
				a += String.valueOf(check1.isChecked());
				a += "\n";
				a += String.valueOf(check2.isChecked());
				a += "\n";
				a += String.valueOf(check3.isChecked());
				a += "\n";
			}

		}
		return a;
	}

	public String getBalancoHidrico() {
		String a = "";
		for (int i = 0, j = tableBalancoHidrico.getChildCount(); i < j; i++) {
			TableRow row = (TableRow) tableBalancoHidrico.getChildAt(i);

			if (i == 0) {
				TextView hora1 = (TextView) row.getChildAt(1);
				TextView hora2 = (TextView) row.getChildAt(2);
				TextView hora3 = (TextView) row.getChildAt(3);
				a += "\n";
				a += hora1.getText();
				a += "\n";
				a += hora2.getText();
				a += "\n";
				a += hora3.getText();
			}

			else {
				EditText texto = (EditText) row.getChildAt(0);
				CheckBox check1 = (CheckBox) row.getChildAt(1);
				CheckBox check2 = (CheckBox) row.getChildAt(2);
				CheckBox check3 = (CheckBox) row.getChildAt(3);
				a += "\n";
				a += texto.getText();
				a += "\n";
				a += String.valueOf(check1.isChecked());
				a += "\n";
				a += String.valueOf(check2.isChecked());
				a += "\n";
				a += String.valueOf(check3.isChecked());
				a += "\n";
			}

		}
		return a;
	}

	public String getDrenagemVesical() {
		String a = "";
		for (int i = 1, j = tableDrenagemVesical.getChildCount(); i < j; i++) {
			TableRow row = (TableRow) tableDrenagemVesical.getChildAt(i);

			TextView texto = (TextView) row.getChildAt(0);
			EditText texto1 = (EditText) row.getChildAt(1);
			EditText texto2 = (EditText) row.getChildAt(2);

			a += "\n";
			a += texto.getText();
			a += "\n";
			a += texto1.getText();
			a += "\n";
			a += texto2.getText();
			a += "\n";

		}
		return a;
	}

	public String getDrenagemNasoastrica() {
		String a = "";
		for (int i = 1, j = tableDrenagemNasogastrica.getChildCount(); i < j; i++) {
			TableRow row = (TableRow) tableDrenagemNasogastrica.getChildAt(i);

			TextView texto = (TextView) row.getChildAt(0);
			EditText texto1 = (EditText) row.getChildAt(1);
			EditText texto2 = (EditText) row.getChildAt(2);

			a += "\n";
			a += texto.getText();
			a += "\n";
			a += texto1.getText();
			a += "\n";
			a += texto2.getText();
			a += "\n";

		}
		return a;
	}

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}

	public void setTimeHoraSinalVital(View v) {
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond = c.get(Calendar.SECOND);
		textoHora = (TextView) v;

		// Launch Date Picker Dialog
		TimePickerDialog dpd = new TimePickerDialog(
				DadosINtraOperatorioActivity.this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						textoHora.setText(hourOfDay + ":" + minute + ":00");

					}
				}, mHour, mMinute, true);

		dpd.show();
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

	public void addTableRowSinaisVitais(View v) {
		tableRowSinaisVitais = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowSinaisVitais = (TableRow) LayoutInflater.from(
				DadosINtraOperatorioActivity.this).inflate(
				R.layout.layout_row_sinais_vitais, null);

		tableSinaisVitais.addView(tableRowSinaisVitais);

		tamanhoPadding += 39;
		layoutIntra.setPadding(0, tamanhoPadding, 0, 0);
	}

	public void addTableRowMedicacaoAdministrada(View v) {
		tableRowMedicacaoAdministrada = new TableRow(
				DadosINtraOperatorioActivity.this);
		tableRowMedicacaoAdministrada = (TableRow) LayoutInflater.from(
				DadosINtraOperatorioActivity.this).inflate(
				R.layout.layout_row_medicacao_administrada, null);

		tableMedicacaoAdministrada.addView(tableRowMedicacaoAdministrada);

		tamanhoPadding += 39;
		layoutIntra.setPadding(0, tamanhoPadding, 0, 0);
	}

	public void addTableRowBalancoHidrico(View v) {
		tableRowBalancoHidrico = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowBalancoHidrico = (TableRow) LayoutInflater.from(
				DadosINtraOperatorioActivity.this).inflate(
				R.layout.layout_row_balanco_hidrico, null);

		tableBalancoHidrico.addView(tableRowBalancoHidrico);

		tamanhoPadding += 39;
		layoutIntra.setPadding(0, tamanhoPadding, 0, 0);
	}

	public void addTableRowDrenagemVesical(View v) {
		tableRowDrenagemVesical = new TableRow(
				DadosINtraOperatorioActivity.this);
		tableRowDrenagemVesical = (TableRow) LayoutInflater.from(
				DadosINtraOperatorioActivity.this).inflate(
				R.layout.layout_row_drenagem_vesical, null);

		tableDrenagemVesical.addView(tableRowDrenagemVesical);

		tamanhoPadding += 39;
		layoutIntra.setPadding(0, tamanhoPadding, 0, 0);
	}

	public void addTableRowDrenagemNasogastrica(View v) {
		tableRowDrenagemNasogastrica = new TableRow(
				DadosINtraOperatorioActivity.this);
		tableRowDrenagemNasogastrica = (TableRow) LayoutInflater.from(
				DadosINtraOperatorioActivity.this).inflate(
				R.layout.layout_row_drenagem_nasogastrica, null);

		tableDrenagemNasogastrica.addView(tableRowDrenagemNasogastrica);

		tamanhoPadding += 39;
		layoutIntra.setPadding(0, tamanhoPadding, 0, 0);

	}

	public void setOnClickTimer(View v) {
		// TODO Auto-generated method stub
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond = c.get(Calendar.SECOND);
		textoHora = (TextView) v;
		// Launch Date Picker Dialog
		TimePickerDialog dpd = new TimePickerDialog(
				DadosINtraOperatorioActivity.this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						textoHora.setText(hourOfDay + ":" + minute + ":00");

					}
				}, mHour, mMinute, true);

		dpd.show();
	}

	public void preencherAtividade(DadosIntraoperatorioFinal dados) {
		try {
			// if(dados.getDados().getTipoAnestesia()!=null)
			spinnerTipoAnestesia.setSelection(getIndex(spinnerTipoAnestesia,
					dados.getDados().getTipoAnestesia()));

			// if(dados.getDados().getTet()!=0)
			editTextTET.setText(dados.getDados().getTet());

			// if(dados.getDados().getMl()!=0)
			editTextML.setText(dados.getDados().getMl());

			// if(dados.getDados().getCalibreAgulha()!=0)
			editTextAgulhaPlCalibre.setText(dados.getDados().getCalibreAgulha()
					+ "");

			// if(dados.getDados().getTipoAcessovenoso()!=null)
			editTextTipo.setText(dados.getDados().getTipoAnestesia());

			// if(dados.getDados().getCalibreAcessoVenoso()!=0)
			editTextCalibre.setText(dados.getDados().getCalibreAcessoVenoso()
					+ "");

			// if(dados.getDados().getLocalizacaoAcessoVenoso()!=null)
			editTextLocalizacaoAcessoVenoso.setText(dados.getDados()
					.getLocalizacaoAcessoVenoso());

			for (int k = 0; k < dados.getListaSinais().size(); k++) {

				if (k == 0) {
					TableRow row = (TableRow) tableSinaisVitais.getChildAt(0);
					TextView texto = (TextView) row.getChildAt(0);
					EditText texto1 = (EditText) row.getChildAt(1);
					EditText texto2 = (EditText) row.getChildAt(2);
					EditText texto3 = (EditText) row.getChildAt(3);
					EditText texto4 = (EditText) row.getChildAt(4);
					texto.setText(dados.getListaSinais().get(k).getHora());
					texto1.setText(dados.getListaSinais().get(k).getTa());
					texto2.setText(dados.getListaSinais().get(k).getFc());
					texto3.setText(dados.getListaSinais().get(k).getSpo2() + "");
					texto4.setText(dados.getListaSinais().get(k).getTemp() + "");
				} else {
					for (int in = 1, j = tableSinaisVitais.getChildCount(); in < j; in++) {
						tableRowSinaisVitais = new TableRow(
								DadosINtraOperatorioActivity.this);
						tableRowSinaisVitais = (TableRow) LayoutInflater.from(
								DadosINtraOperatorioActivity.this).inflate(
								R.layout.layout_row_sinais_vitais, null);

						tableSinaisVitais.addView(tableRowSinaisVitais);

						tamanhoPadding += 39;
						layoutIntra.setPadding(0, tamanhoPadding, 0, 0);
						tableSinaisVitais.addView(tableRowSinaisVitais);

						TableRow row = (TableRow) tableSinaisVitais
								.getChildAt(in);

						TextView texto = (TextView) row.getChildAt(0);
						EditText texto1 = (EditText) row.getChildAt(1);
						EditText texto2 = (EditText) row.getChildAt(2);
						EditText texto3 = (EditText) row.getChildAt(3);
						EditText texto4 = (EditText) row.getChildAt(4);
						texto.setText(dados.getListaSinais().get(k).getHora());
						texto1.setText(dados.getListaSinais().get(k).getTa());
						texto2.setText(dados.getListaSinais().get(k).getFc());
						texto3.setText(dados.getListaSinais().get(k).getSpo2()
								+ "");
						texto4.setText(dados.getListaSinais().get(k).getTemp()
								+ "");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

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
				preencherAtividade(dados);

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

		}
	}

}
