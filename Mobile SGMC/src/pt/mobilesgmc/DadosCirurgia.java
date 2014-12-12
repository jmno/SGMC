package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.xml.sax.DTDHandler;

import pt.mobilesgmc.modelo.BlocoOperatorio;
import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;

public class DadosCirurgia extends Activity {

	private Spinner especialidadeCirurgica;
	private TextView data;
	private Spinner tipoCirurgia;
	private EditText cirurgia;
	private Spinner sala;
	private Spinner lateralidade;
	private Spinner classificacaoASA;
	private TextView horaChamadaUtente;
	private TextView horaEntradaBO;
	private TextView horaSaidaBO;
	private TextView horaEntradaSala;
	private TextView horaSaidaSala;
	private TextView horaInicioAnestesia;
	private TextView horaFimAnestesia;
	private TextView horaInicioCirurgia;
	private TextView horaFimCirurgia;
	private TextView horaEntradaRecobro;
	private TextView horaSaidaRecobro;
	private Spinner destinoDoente;
	private EditText informacoesRelevantes;
	FlyOutContainer root;
	private ScrollView scrollDadosCirurgia;
	private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
	private Spinner bloco;
	private ArrayAdapter<BlocoOperatorio> adaptadorBloco;
	private String token;
	private int idCirurgia;
	private Button btnGuardar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_cirurgia);

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_dados_cirurgia, null);
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;
		int margin = (80 * (int) dpWidth) / 100;
		root.setMargin(margin);
		this.setContentView(root);

		// idEquipa = Integer.parseInt(PreferenceManager
		// .getDefaultSharedPreferences(getApplicationContext())
		// .getString("idEquipa", "-1"));

		scrollDadosCirurgia = (ScrollView) root
				.findViewById(R.id.scrollDadosCirurgia);

		scrollDadosCirurgia.setOnTouchListener(new OnSwipeTouchListener(
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

	//	new getBlocoOperatorios().execute();
		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");
		idCirurgia = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext())
				.getString("idCirurgia", "0"));

		data = (TextView) findViewById(R.id.TextViewData);
		horaChamadaUtente = (TextView) findViewById(R.id.editTextHoraChamadaUtente);
		especialidadeCirurgica = (Spinner) findViewById(R.id.spinnerEspecialidade);
		tipoCirurgia = (Spinner) findViewById(R.id.spinnerTipoCirurgia);
		sala = (Spinner) findViewById(R.id.spinnerSala);
		lateralidade = (Spinner) findViewById(R.id.spinnerLateralidade);
		classificacaoASA = (Spinner) findViewById(R.id.spinnerASA);
		horaEntradaBO = (TextView) findViewById(R.id.editTextHoraEntradaBO);
		horaSaidaBO = (TextView) findViewById(R.id.editTextHoraSaidaBO);
		horaEntradaSala = (TextView) findViewById(R.id.editTextHoraEntradaSala);
		horaSaidaSala = (TextView) findViewById(R.id.editTextHoraSaidaSala);
		horaInicioAnestesia = (TextView) findViewById(R.id.editTextHoraInicioAnestesia);
		horaFimAnestesia = (TextView) findViewById(R.id.editTextHoraFimAnestesia);
		horaInicioCirurgia = (TextView) findViewById(R.id.editTextHoraInicioCirurgia);
		horaFimCirurgia = (TextView) findViewById(R.id.editTextHoraFimCirurgia);
		horaEntradaRecobro = (TextView) findViewById(R.id.editTextHoraEntradaRecobro);
		horaSaidaRecobro = (TextView) findViewById(R.id.editTextHoraSaidaRecobro);
		destinoDoente = (Spinner) findViewById(R.id.spinnerDestinoDoente);
		informacoesRelevantes = (EditText) findViewById(R.id.editTextInformacoes);
		cirurgia = (EditText) findViewById(R.id.editTextCirurgia);
		bloco = (Spinner) findViewById(R.id.spinnerBloco);
		carregaOsListeners();
		preencherAtividade(HomeActivity.getCirurgia());
		
		final Cirurgia p = HomeActivity.getCirurgia();
		btnGuardar = (Button) findViewById(R.id.button3);
		btnGuardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cirurgia ci = new Cirurgia();
				ci.setHora(p.getHora());
				ci.setEspecialidade(especialidadeCirurgica.getSelectedItem().toString());
				ci.setTipoCirurgia(tipoCirurgia.getSelectedItem().toString());
				ci.setIdSala(p.getIdSala());
				ci.setIdUtente(p.getIdUtente());
				ci.setIdEquipa(p.getIdEquipa());
				ci.setLateralidade(lateralidade.getSelectedItem().toString());
				ci.setClassificacaoASA(classificacaoASA.getSelectedItem().toString());
				ci.setHoraEntradaBlocoOperatorio(p.getHoraEntradaBlocoOperatorio());
				ci.setHoraSaideBlocoOperatorio(p.getHoraSaideBlocoOperatorio());
				ci.setHoraSaidaSala(p.getHoraSaidaSala());
			    ci.setHoraEntradaSala(p.getHoraEntradaSala());
				ci.setHoraInicioAnestesia(p.getHoraInicioAnestesia());
				ci.setHoraFimAnestesia(p.getHoraFimAnestesia());
				ci.setHoraFimRecobro(p.getHoraFimRecobro());
				ci.setHoraFimCirurgia(p.getHoraFimCirurgia());
				ci.setHoraInicioCirurgia(p.getHoraInicioCirurgia());
				ci.setHoraFimCirurgia(p.getHoraFimCirurgia());
				ci.setHoraInicioAnestesia(p.getHoraInicioAnestesia());
				ci.setHoraFimCirurgia(p.getHoraFimCirurgia());
				ci.setHoraEntradaRecobro(p.getHoraEntradaRecobro());
				ci.setDestinoDoente(destinoDoente.getSelectedItem().toString());
				ci.setCirurgia(cirurgia.getText().toString());
				ci.setInfoRelevante(informacoesRelevantes.getText().toString());
//				String datad= (String) data.getText();
//				String[] dataF = datad.split("/");
//				int dia = Integer.parseInt(dataF[0]);
//				int mes = Integer.parseInt(dataF[1]);
//				int ano = Integer.parseInt(dataF[2]);
//				int hora ;
//				int minuto;
//				int segundo;
//				java.sql.Time horas = null ;
//				
//				Date dataFinal = new Date(dia, mes, ano);
//				ci.setData(dataFinal);
//				
//				String horaChamU = (String)horaChamadaUtente.getText();
//				String[] horaChamada = horaChamU.split(":");
//				if(horaChamada.length==2)
//				{
//					 hora = Integer.parseInt(horaChamada[0]);
//					 minuto = Integer.parseInt(horaChamada[1]);
//					 horas = new java.sql.Time(hora, minuto, 00) ;
//				}
//				else if(horaChamada.length==3)
//				{
//					hora = Integer.parseInt(horaChamada[0]);
//					 minuto = Integer.parseInt(horaChamada[1]);
//					 segundo = Integer.parseInt(horaChamada[2]);
//					 horas = new java.sql.Time(hora, minuto, segundo) ;
//				}
//				
//				ci.setHoraChamadaUtente(horas);
//				
//				String horaEntB = (String)horaEntradaBO.getText();
//				String[] horaEntBo = horaEntB.split(":");
//				if(horaEntBo.length==2)
//				{
//					 hora = Integer.parseInt(horaEntBo[0]);
//					 minuto = Integer.parseInt(horaEntBo[1]);
//					 horas = new java.sql.Time(hora, minuto, 00) ;
//				}
//				else if(horaEntBo.length==3)
//				{
//					hora = Integer.parseInt(horaEntBo[0]);
//					 minuto = Integer.parseInt(horaEntBo[1]);
//					 segundo = Integer.parseInt(horaEntBo[2]);
//					 horas = new java.sql.Time(hora, minuto, segundo) ;
//				}
//				
//				ci.setHoraEntradaBlocoOperatorio(horas);
//				
//				String horaSaidaB = (String)horaSaidaBO.getText();
//				String[] horaSaidaBo = horaSaidaB.split(":");
//				if(horaSaidaBo.length==2)
//				{
//					 hora = Integer.parseInt(horaSaidaBo[0]);
//					 minuto = Integer.parseInt(horaSaidaBo[1]);
//					 horas = new java.sql.Time(hora, minuto, 00) ;
//				}
//				else if(horaSaidaBo.length==3)
//				{
//					hora = Integer.parseInt(horaSaidaBo[0]);
//					 minuto = Integer.parseInt(horaSaidaBo[1]);
//					 segundo = Integer.parseInt(horaSaidaBo[2]);
//					 horas = new java.sql.Time(hora, minuto, segundo) ;
//				}
//				
//				ci.setHoraSaideBlocoOperatorio(horas);
//				
//				
//				String horaEntSala = (String)horaEntradaSala.getText();
//				String[] horaEntSa = horaEntSala.split(":");
//				if(horaEntSa.length==2)
//				{
//					 hora = Integer.parseInt(horaEntSa[0]);
//					 minuto = Integer.parseInt(horaEntSa[1]);
//					 horas = new java.sql.Time(hora, minuto, 00) ;
//				}
//				else if(horaEntSa.length==3)
//				{
//					hora = Integer.parseInt(horaEntSa[0]);
//					 minuto = Integer.parseInt(horaEntSa[1]);
//					 segundo = Integer.parseInt(horaEntSa[2]);
//					 horas = new java.sql.Time(hora, minuto, segundo) ;
//				}
//				
//				ci.setHoraSaidaSala(horas);
				
//				String horaSaidaSala = (String)horaSaidaSala.getText();
//				String[] horaEntSa = horaSaidaSala.split(":");
//				if(horaEntSa.length==2)
//				{
//					 hora = Integer.parseInt(horaEntSa[0]);
//					 minuto = Integer.parseInt(horaEntSa[1]);
//					 horas = new java.sql.Time(hora, minuto, 00) ;
//				}
//				else if(horaEntSa.length==3)
//				{
//					hora = Integer.parseInt(horaEntSa[0]);
//					 minuto = Integer.parseInt(horaEntSa[1]);
//					 segundo = Integer.parseInt(horaEntSa[2]);
//					 horas = new java.sql.Time(hora, minuto, segundo) ;
//				}
//				
//				c.setHoraSaidaSala(horas);
				
				
				try{
				new atualizarCirurgia().execute(ci);
			}catch(Exception rn){}}
		});

	}

	private void carregaOsListeners() {

		data.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(DadosCirurgia.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// Display Selected date in textbox
								data.setText(dayOfMonth + "-"
										+ (monthOfYear + 1) + "-" + year);
							}
						}, mYear, mMonth, mDay);
				dpd.show();

			}
		});

		horaChamadaUtente.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaChamadaUtente.setText(hourOfDay + ":"
										+ minute );

							}

							
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		
		horaEntradaBO.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaEntradaBO.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaSaidaBO.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaSaidaBO.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaEntradaSala.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaEntradaSala.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaSaidaSala.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaSaidaSala.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		
		horaInicioAnestesia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaInicioAnestesia.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaFimAnestesia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaFimAnestesia.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaInicioCirurgia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaInicioCirurgia.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		
		horaFimCirurgia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaFimCirurgia.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		horaEntradaRecobro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaEntradaRecobro.setText(hourOfDay + ":"
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});
		
		horaSaidaRecobro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Process to get Current Date
				final Calendar c = Calendar.getInstance();
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);
				mSecond = c.get(Calendar.SECOND);

				// Launch Date Picker Dialog
				TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								horaSaidaRecobro.setText(hourOfDay + ":"
										+ minute  );

							}
						}, mHour, mMinute,  true);

				dpd.show();

			}
		});

	}

	private void preencherAtividade(Cirurgia c) {

		data.setText(c.getData().getDay() + "/" + c.getData().getMonth() + "/"
				+ c.getData().getYear());
		horaChamadaUtente.setText(c.getHoraChamadaUtente().toString());
		especialidadeCirurgica.setSelection(2);
		tipoCirurgia.setSelection(4);
		sala.setSelection(5);
		lateralidade.setSelection(2);
		classificacaoASA.setSelection(4);
		horaEntradaBO.setText(c.getHoraEntradaBlocoOperatorio().toString());
		horaSaidaBO.setText(c.getHoraSaideBlocoOperatorio().toString());
		horaEntradaSala.setText(c.getHoraEntradaSala().toString());
		horaSaidaSala.setText(c.getHoraSaidaSala().toString());
		horaInicioAnestesia.setText(c.getHoraInicioAnestesia().toString());
		horaFimAnestesia.setText(c.getHoraFimAnestesia().toString());
		horaInicioCirurgia.setText(c.getHoraInicioCirurgia().toString());
		horaFimCirurgia.setText(c.getHoraFimCirurgia().toString());
		horaEntradaRecobro.setText(c.getHoraEntradaRecobro().toString());
		horaSaidaRecobro.setText(c.getHoraFimRecobro().toString());
		destinoDoente.setSelection(7);
		informacoesRelevantes.setText(c.getInfoRelevante());
		cirurgia.setText(c.getCirurgia().toString());
		bloco.setSelection(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dados_cirurgia, menu);

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

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		String estado = root.getState().toString();
		if (estado.equals("OPEN"))
			this.root.toggleMenu();
		return super.onTouchEvent(event);
	}

	public void preencher(Cirurgia c) {

	}

//	private class getBlocoOperatorios extends
//			AsyncTask<String, Void, ArrayList<BlocoOperatorio>> {
//
//		@Override
//		protected ArrayList<BlocoOperatorio> doInBackground(String... params) {
//			ArrayList<BlocoOperatorio> lista = null;
//
//			try {
//				lista = WebServiceUtils.getAllBloco(token);
//
//			} catch (IOException | RestClientException | ParseException
//					| JSONException e) {
//				e.printStackTrace();
//			}
//
//			return lista;
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<BlocoOperatorio> lista) {
//			if (lista != null) {
//				adaptadorBloco = new ArrayAdapter<>(getBaseContext(),
//						android.R.layout.simple_list_item_1, lista);
//				bloco.setAdapter(adaptadorBloco);
//			} else {
//				Toast.makeText(getApplicationContext(),
//						"Get Bloco unsuccessful...", Toast.LENGTH_LONG).show();
//
//			}
//		}
//	}
	
	private class atualizarCirurgia extends
	AsyncTask<Cirurgia, Void, Boolean> {

@Override
protected Boolean doInBackground(Cirurgia... params) {
	Boolean adicionou = false;

	try {
		adicionou = WebServiceUtils.updateCirurgia(params[0], idCirurgia, token);
			
	} catch (ParseException | IOException | JSONException
			| RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return adicionou;
}

@Override
protected void onPostExecute(Boolean result) {
	String a = (result ? "Cirurgia Alterada com Sucesso!"
			: "Cirurgoa Não Alterada!");

	Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
			.show();

	super.onPostExecute(result);
}

}
}
