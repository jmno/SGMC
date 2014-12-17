package pt.mobilesgmc;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

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
	private EditText editTextLocalizacao;
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
	private TextView textoHora;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_testes);
		
//		setContentView(R.layout.activity_dados_intra_operatorio);
		
//		spinnerTipoAnestesia = (Spinner) findViewById(R.id.spinnerTipoAnestesia);
//		editTextTET = (EditText) findViewById(R.id.editTextTET);
//		editTextML = (EditText) findViewById(R.id.editTextML);
//		editTextAgulhaPlCalibre = (EditText) findViewById(R.id.editTextAgulhaPLCalibre);
//		editTextTipo = (EditText) findViewById(R.id.editTextTipo);
//		editTextCalibre = (EditText) findViewById(R.id.editTextCalibre);
//		editTextLocalizacao = (EditText) findViewById(R.id.editTextLocalizacao);
//		textViewHoraSinais1 = (TextView) findViewById(R.id.textViewHora1);
//		editTextTa1 = (EditText) findViewById(R.id.editTextTa);
//		editTextFc1 = (EditText) findViewById(R.id.editTextFc);
//		editTextSpo2_1 = (EditText) findViewById(R.id.editTextSpo2);
//		editTextTemp1 = (EditText) findViewById(R.id.editTexttemp);
//		editTextTa3 = (EditText) findViewById(R.id.editTextTa3);
//		editTextFc3 = (EditText) findViewById(R.id.editTextFc3);
//		editTextSpo2_3 = (EditText) findViewById(R.id.editTextSpo23);
//		editTextTemp3 = (EditText) findViewById(R.id.editTexttemp3);
//		textViewHoraFarmaco1 = (TextView) findViewById(R.id.textViewHoraFaramaco1);
//		textViewHoraFarmaco2 = (TextView) findViewById(R.id.textViewHoraFaramaco2);
//		textViewHoraFarmaco3 = (TextView) findViewById(R.id.textViewHoraFaramaco3);
//		editTextFarmaco1 = (EditText) findViewById(R.id.editTextFaramaco1);
//		editTextFarmaco2 = (EditText) findViewById(R.id.editTextFaramaco2);
//		editTextFarmaco3 = (EditText) findViewById(R.id.editTextFaramaco3);
//		editTextFarmaco4 = (EditText) findViewById(R.id.editTextFaramaco4);
//		editTextFarmaco5 = (EditText) findViewById(R.id.editTextFaramaco5);
//		editTextFarmaco6 = (EditText) findViewById(R.id.editTextFaramaco6);
//		editTextFarmaco7 = (EditText) findViewById(R.id.editTextFaramaco7);
//		editTextFarmaco8 = (EditText) findViewById(R.id.editTextFaramaco8);
//		textViewHoraInicioTransfusao= (TextView) findViewById(R.id.textViewInicioTransfusao);
//		editTextTaInicioTransfusao = (EditText) findViewById(R.id.editTexttaInicioTransfusao);
//		editTexfcInicioTransfusao = (EditText) findViewById(R.id.editTextfcInicioTransfusao);
//		editTextspo2InicioTransfusao =  (EditText) findViewById(R.id.editTextspo2InicioTransfusao);
//		textViewHora15MinAposTransfusao= (TextView) findViewById(R.id.textView15MinAposTransfusao);
//		editTextTa15MinTransfusao = (EditText) findViewById(R.id.editTextta15MinAposTransfusao);
//		editTexfc15MinTransfusao= (EditText) findViewById(R.id.editTextfc15MinAposTransfusao);
//		editTextspo215MinTransfusao =  (EditText) findViewById(R.id.editTextspo215MinAposTransfusao);
//		textViewHoraFimTransfusao= (TextView) findViewById(R.id.textViewFimMinAposTransfusao);
//		editTextTaFimTransfusao = (EditText) findViewById(R.id.editTexttaFimMinAposTransfusao);
//		editTexfcFimTransfusao= (EditText) findViewById(R.id.editTextfcFimMinAposTransfusao);
//		editTextspo2FimTransfusao =  (EditText) findViewById(R.id.editTextspo2FimMinAposTransfusao);
//		spinnerPosicaoOperatoria = (Spinner) findViewById(R.id.spinnerPosicaoOperatoria);
//		editTextAlivioZonaPressao= (EditText) findViewById(R.id.editTextaliviosZonaPressao);
//		editTextLocalAlivioZonaPressao = (EditText) findViewById(R.id.editTextLocalaliviosZonaPressao);
//		spinnerMantaTermica = (Spinner) findViewById(R.id.spinnerMantaTermicaProtecao);
//		editTextLocalMantaTermica = (EditText) findViewById(R.id.editTextLocalMantaTermicaProtecao);
//		spinnerLocalizacaoGarrote = (Spinner) findViewById(R.id.spinnerLocalizacao);
//		textViewHoraInicioGarrote = (TextView) findViewById(R.id.textViewInicio);
//		textViewHoraFimGarrote = (TextView) findViewById(R.id.textViewFim);
//		editTextPressaoGarrote = (EditText) findViewById(R.id.editTextPressao);
//		spinnerPlacaEletrodo = (Spinner) findViewById(R.id.spinnerPlacaEletrodoNeutro);
//		editTextPecaBiopsiaDescricao = (EditText) findViewById(R.id.editTextDescricao);
//		editTextPecaBiopsiaLaboratorio =(EditText) findViewById(R.id.editTextLaboratorio);
//		
//		
//	//	carregarTimes();
//		
//		tableSinaisVitais = (TableLayout) findViewById(R.id.table_layout_sinais_vitais);
//		tableMedicacaoAdministrada = (TableLayout) findViewById(R.id.table_layout_medicacao_administrada);
//		tableBalancoHidrico = (TableLayout) findViewById(R.id.tablelayout_balanco_hidrico);
//		tableDrenagemVesical = (TableLayout) findViewById(R.id.tablelayout_drenagem_vesical);
//		tableDrenagemNasogastrica = (TableLayout) findViewById(R.id.tablelayout_drenagem_nasogastrica);
//		texto = (TextView) findViewById(R.id.txtview_sinaisVitais);
//		texto.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				addTableRowSinaisVitais();
//				addTableRowMedicacaoAdministrada();
//				addTableRowBalancoHidrico();
//				addTableRowDrenagemVesical();
//				addTableRowDrenagemNasogastrica();
//			}
//		});
	}

//	public void setTimeHoraSinalVital(View v)
//	{
//		final Calendar c = Calendar.getInstance();
//		mHour = c.get(Calendar.HOUR_OF_DAY);
//		mMinute = c.get(Calendar.MINUTE);
//		mSecond = c.get(Calendar.SECOND);
//		textoHora = (TextView) v;
//		
//
//		// Launch Date Picker Dialog
//		TimePickerDialog dpd = new TimePickerDialog(
//				DadosINtraOperatorioActivity.this,
//				new TimePickerDialog.OnTimeSetListener() {
//
//					@Override
//					public void onTimeSet(TimePicker view,
//							int hourOfDay, int minute) {
//						textoHora.setText(hourOfDay + ":"
//								+ minute + ":00");
//
//					}
//				}, mHour, mMinute, true);
//
//		dpd.show();
//	}
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

//	public void addTableRowSinaisVitais() {
//		tableRowSinaisVitais = new TableRow(DadosINtraOperatorioActivity.this);
//		tableRowSinaisVitais = (TableRow) LayoutInflater.from(
//				DadosINtraOperatorioActivity.this).inflate(
//				R.layout.layout_row_sinais_vitais, null);
//
//		tableSinaisVitais.addView(tableRowSinaisVitais);
//	}
//
//	public void addTableRowMedicacaoAdministrada() {
//		tableRowMedicacaoAdministrada = new TableRow(
//				DadosINtraOperatorioActivity.this);
//		tableRowMedicacaoAdministrada = (TableRow) LayoutInflater.from(
//				DadosINtraOperatorioActivity.this).inflate(
//				R.layout.layout_row_medicacao_administrada, null);
//
//		tableMedicacaoAdministrada.addView(tableRowMedicacaoAdministrada);
//	}
//
//	public void addTableRowBalancoHidrico() {
//		tableRowBalancoHidrico = new TableRow(DadosINtraOperatorioActivity.this);
//		tableRowBalancoHidrico = (TableRow) LayoutInflater.from(
//				DadosINtraOperatorioActivity.this).inflate(
//				R.layout.layout_row_balanco_hidrico, null);
//
//		tableBalancoHidrico.addView(tableRowBalancoHidrico);
//	}
//
//	public void addTableRowDrenagemVesical() {
//		tableRowDrenagemVesical = new TableRow(
//				DadosINtraOperatorioActivity.this);
//		tableRowDrenagemVesical = (TableRow) LayoutInflater.from(
//				DadosINtraOperatorioActivity.this).inflate(
//				R.layout.layout_row_drenagem_vesical, null);
//
//		tableDrenagemVesical.addView(tableRowDrenagemVesical);
//	}
//
//	public void addTableRowDrenagemNasogastrica() {
//		tableRowDrenagemNasogastrica = new TableRow(
//				DadosINtraOperatorioActivity.this);
//		tableRowDrenagemNasogastrica = (TableRow) LayoutInflater.from(
//				DadosINtraOperatorioActivity.this).inflate(
//				R.layout.layout_row_drenagem_nasogastrica, null);
//
//		tableDrenagemNasogastrica.addView(tableRowDrenagemNasogastrica);
//
//	}
//
//	public void carregarTimes() {
//
//		textViewHoraFarmaco1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraFarmaco1.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});
//		
//		textViewHoraFarmaco2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraFarmaco2.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});
//		textViewHoraFarmaco3.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraFarmaco3.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});
//		
//		textViewHoraInicioTransfusao.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraInicioTransfusao.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});
//
//
//		textViewHoraInicioGarrote.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraInicioGarrote.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});
//		
//
//		textViewHoraFimGarrote.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final Calendar c = Calendar.getInstance();
//				mHour = c.get(Calendar.HOUR_OF_DAY);
//				mMinute = c.get(Calendar.MINUTE);
//				mSecond = c.get(Calendar.SECOND);
//
//				// Launch Date Picker Dialog
//				TimePickerDialog dpd = new TimePickerDialog(
//						DadosINtraOperatorioActivity.this,
//						new TimePickerDialog.OnTimeSetListener() {
//
//							@Override
//							public void onTimeSet(TimePicker view,
//									int hourOfDay, int minute) {
//								textViewHoraFimGarrote.setText(hourOfDay + ":"
//										+ minute + ":00");
//
//							}
//						}, mHour, mMinute, true);
//
//				dpd.show();
//
//			}
//		});



//	}


}
