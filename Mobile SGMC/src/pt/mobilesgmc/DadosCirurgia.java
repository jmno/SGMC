package pt.mobilesgmc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.xml.sax.DTDHandler;

import pt.mobilesgmc.modelo.BlocoComSala;
import pt.mobilesgmc.modelo.BlocoOperatorio;
import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.EquipaComJuncao;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.SpinnerAdapter;
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
	private TextView horaCirurgia;
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
	private Cirurgia cir;
	private ArrayAdapter<BlocoComSala> adaptadorBlococomSala;
	ProgressDialog ringProgressDialog = null;

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
		// .getString("icdEquipa", "-1"));

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

		// new getBlocoOperatorios().execute();
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
		horaCirurgia = (TextView) findViewById(R.id.TextViewHoraCirurgia);
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
		carregaOsListeners();
		preencherAtividade(HomeActivity.getCirurgia());
<<<<<<< HEAD
=======
		new getBlocosComSala().execute(token);
>>>>>>> FETCH_HEAD

		final Cirurgia p = HomeActivity.getCirurgia();
		btnGuardar = (Button) findViewById(R.id.button3);
		btnGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Cirurgia ci = new Cirurgia();
<<<<<<< HEAD
				ci.setHora(p.getHora());
=======
>>>>>>> FETCH_HEAD
				ci.setEspecialidade(especialidadeCirurgica.getSelectedItem()
						.toString());
				ci.setTipoCirurgia(tipoCirurgia.getSelectedItem().toString());
				ci.setIdSala(p.getIdSala());
				ci.setIdUtente(p.getIdUtente());
				ci.setIdEquipa(p.getIdEquipa());
				ci.setLateralidade(lateralidade.getSelectedItem().toString());
				ci.setClassifASA(classificacaoASA.getSelectedItem()
						.toString());
<<<<<<< HEAD
				ci.setHoraEntradaBlocoOperatorio(p
						.getHoraEntradaBlocoOperatorio());
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
				String datad = (String) data.getText();
				String[] dataF = datad.split("/");
				int dia = Integer.parseInt(dataF[0]);
				int mes = Integer.parseInt(dataF[1]);
				int ano = Integer.parseInt(dataF[2]);
				int hora;
				int minuto;
				int segundo;
				java.sql.Time horas = null;

				Date dataFinal = new Date(dia, mes, ano);
				ci.setData(dataFinal);

				String horaChamU = (String) horaChamadaUtente.getText();
				String[] horaChamada = horaChamU.split(":");
				if (horaChamada.length == 2) {
					hora = Integer.parseInt(horaChamada[0]);
					minuto = Integer.parseInt(horaChamada[1]);
					horas = new java.sql.Time(hora, minuto, 00);
				} else if (horaChamada.length == 3) {
					hora = Integer.parseInt(horaChamada[0]);
					minuto = Integer.parseInt(horaChamada[1]);
					segundo = Integer.parseInt(horaChamada[2]);
					horas = new java.sql.Time(hora, minuto, segundo);
				}

				ci.setHoraChamadaUtente(horas);

				String horaEntB = (String) horaEntradaBO.getText();
				String[] horaEntBo = horaEntB.split(":");
				if (horaEntBo.length == 2) {
					hora = Integer.parseInt(horaEntBo[0]);
					minuto = Integer.parseInt(horaEntBo[1]);
					horas = new java.sql.Time(hora, minuto, 00);
				} else if (horaEntBo.length == 3) {
					hora = Integer.parseInt(horaEntBo[0]);
					minuto = Integer.parseInt(horaEntBo[1]);
					segundo = Integer.parseInt(horaEntBo[2]);
					horas = new java.sql.Time(hora, minuto, segundo);
				}

				ci.setHoraEntradaBlocoOperatorio(horas);

				String horaSaidaB = (String) horaSaidaBO.getText();
				String[] horaSaidaBo = horaSaidaB.split(":");
				if (horaSaidaBo.length == 2) {
					hora = Integer.parseInt(horaSaidaBo[0]);
					minuto = Integer.parseInt(horaSaidaBo[1]);
					horas = new java.sql.Time(hora, minuto, 00);
				} else if (horaSaidaBo.length == 3) {
					hora = Integer.parseInt(horaSaidaBo[0]);
					minuto = Integer.parseInt(horaSaidaBo[1]);
					segundo = Integer.parseInt(horaSaidaBo[2]);
					horas = new java.sql.Time(hora, minuto, segundo);
				}

				ci.setHoraSaideBlocoOperatorio(horas);

				String horaEntSala = (String) horaEntradaSala.getText();
				String[] horaEntSa = horaEntSala.split(":");
				if (horaEntSa.length == 2) {
					hora = Integer.parseInt(horaEntSa[0]);
					minuto = Integer.parseInt(horaEntSa[1]);
					horas = new java.sql.Time(hora, minuto, 00);
				} else if (horaEntSa.length == 3) {
					hora = Integer.parseInt(horaEntSa[0]);
					minuto = Integer.parseInt(horaEntSa[1]);
					segundo = Integer.parseInt(horaEntSa[2]);
					horas = new java.sql.Time(hora, minuto, segundo);
				}

				ci.setHoraEntradaSala(horas);

				String horaSaiSala = (String) horaSaidaSala.getText();
				String[] horaSaiSa = horaSaiSala.split(":");
				if (horaEntSa.length == 2) {
					hora = Integer.parseInt(horaEntSa[0]);
					minuto = Integer.parseInt(horaEntSa[1]);
					horas = new java.sql.Time(hora, minuto, 00);
				} else if (horaEntSa.length == 3) {
					hora = Integer.parseInt(horaEntSa[0]);
					minuto = Integer.parseInt(horaEntSa[1]);
					segundo = Integer.parseInt(horaEntSa[2]);
					horas = new java.sql.Time(hora, minuto, segundo);
				}

				ci.setHoraSaidaSala(horas);
				
				ci.setId(idCirurgia);
				try {
					new atualizarCirurgia().execute(ci);
				} catch (Exception rn) {
					Log.i("webservice", rn.getMessage());
=======

				ci.setDestinoDoente(destinoDoente.getSelectedItem().toString());
				ci.setCirurgia(cirurgia.getText().toString());
				ci.setInfoRelevante(informacoesRelevantes.getText().toString());
				// String datad = (String) data.getText();
				// // String[] dataF = datad.split("-");
				// // int dia = Integer.parseInt(dataF[0]);
				// // int mes = Integer.parseInt(dataF[1]);
				// // int ano = Integer.parseInt(dataF[2]);
				// int hora;
				// int minuto;
				// int segundo;
				// java.sql.Time horas = null;
				// //
				// // java.util.Date dataFinal = new Date(dia, mes, ano);
				// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// Date inputDate = null;
				// try {
				// inputDate = dateFormat.parse(datad);
				// } catch (java.text.ParseException e) {
				// e.printStackTrace();
				// }
				ci.setData(data.getText().toString());

				// String hor = (String) horaCirurgia.getText();
				// String[] horCirurgia = hor.split(":");
				// if (horCirurgia.length == 2) {
				// hora = Integer.parseInt(horCirurgia[0]);
				// minuto = Integer.parseInt(horCirurgia[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horCirurgia.length == 3) {
				// hora = Integer.parseInt(horCirurgia[0]);
				// minuto = Integer.parseInt(horCirurgia[1]);
				// segundo = Integer.parseInt(horCirurgia[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHora(horaCirurgia.getText().toString());

				// String horaChamU = (String) horaChamadaUtente.getText();
				// String[] horaChamada = horaChamU.split(":");
				// if (horaChamada.length == 2) {
				// hora = Integer.parseInt(horaChamada[0]);
				// minuto = Integer.parseInt(horaChamada[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaChamada.length == 3) {
				// hora = Integer.parseInt(horaChamada[0]);
				// minuto = Integer.parseInt(horaChamada[1]);
				// segundo = Integer.parseInt(horaChamada[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraChamadaUtente(horaChamadaUtente.getText().toString());

				// String horaEntB = (String) horaEntradaBO.getText();
				// String[] horaEntBo = horaEntB.split(":");
				// if (horaEntBo.length == 2) {
				// hora = Integer.parseInt(horaEntBo[0]);
				// minuto = Integer.parseInt(horaEntBo[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaEntBo.length == 3) {
				// hora = Integer.parseInt(horaEntBo[0]);
				// minuto = Integer.parseInt(horaEntBo[1]);
				// segundo = Integer.parseInt(horaEntBo[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraEntradaBlocoOperatorio(horaEntradaBO.getText()
						.toString());

				// String horaSaidaB = (String) horaSaidaBO.getText();
				// String[] horaSaidaBo = horaSaidaB.split(":");
				// if (horaSaidaBo.length == 2) {
				// hora = Integer.parseInt(horaSaidaBo[0]);
				// minuto = Integer.parseInt(horaSaidaBo[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaSaidaBo.length == 3) {
				// hora = Integer.parseInt(horaSaidaBo[0]);
				// minuto = Integer.parseInt(horaSaidaBo[1]);
				// segundo = Integer.parseInt(horaSaidaBo[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraSaideBlocoOperatorio(horaSaidaBO.getText().toString());
				//
				// String horaEntSala = (String) horaEntradaSala.getText();
				// String[] horaEntSa = horaEntSala.split(":");
				// if (horaEntSa.length == 2) {
				// hora = Integer.parseInt(horaEntSa[0]);
				// minuto = Integer.parseInt(horaEntSa[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaEntSa.length == 3) {
				// hora = Integer.parseInt(horaEntSa[0]);
				// minuto = Integer.parseInt(horaEntSa[1]);
				// segundo = Integer.parseInt(horaEntSa[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraEntradaSala(horaEntradaSala.getText().toString());

				// String horaSaiaSala = (String) horaSaidaSala.getText();
				// String[] horaSaidaSa = horaSaiaSala.split(":");
				// if (horaSaidaSa.length == 2) {
				// hora = Integer.parseInt(horaSaidaSa[0]);
				// minuto = Integer.parseInt(horaSaidaSa[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaSaidaSa.length == 3) {
				// hora = Integer.parseInt(horaSaidaSa[0]);
				// minuto = Integer.parseInt(horaSaidaSa[1]);
				// segundo = Integer.parseInt(horaSaidaSa[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraSaidaSala(horaSaidaSala.getText().toString());

				// String horaIAnestesia = (String)
				// horaInicioAnestesia.getText();
				// String[] horaIAnest = horaIAnestesia.split(":");
				// if (horaIAnest.length == 2) {
				// hora = Integer.parseInt(horaIAnest[0]);
				// minuto = Integer.parseInt(horaIAnest[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaIAnest.length == 3) {
				// hora = Integer.parseInt(horaIAnest[0]);
				// minuto = Integer.parseInt(horaIAnest[1]);
				// segundo = Integer.parseInt(horaIAnest[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraInicioAnestesia(horaInicioAnestesia.getText()
						.toString());

				// String horaFAnestesia = (String) horaFimAnestesia.getText();
				// String[] horaFAnest = horaFAnestesia.split(":");
				// if (horaFAnest.length == 2) {
				// hora = Integer.parseInt(horaFAnest[0]);
				// minuto = Integer.parseInt(horaFAnest[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaFAnest.length == 3) {
				// hora = Integer.parseInt(horaFAnest[0]);
				// minuto = Integer.parseInt(horaFAnest[1]);
				// segundo = Integer.parseInt(horaFAnest[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraFimAnestesia(horaFimAnestesia.getText().toString());
				//
				// String horaICirurgia = (String) horaInicioCirurgia.getText();
				// String[] horaICir = horaICirurgia.split(":");
				// if (horaICir.length == 2) {
				// hora = Integer.parseInt(horaICir[0]);
				// minuto = Integer.parseInt(horaICir[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaICir.length == 3) {
				// hora = Integer.parseInt(horaICir[0]);
				// minuto = Integer.parseInt(horaICir[1]);
				// segundo = Integer.parseInt(horaICir[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraInicioCirurgia(horaInicioCirurgia.getText()
						.toString());

				// String horaFCirurgia = (String) horaFimCirurgia.getText();
				// String[] horaFCir = horaFCirurgia.split(":");
				// if (horaFCir.length == 2) {
				// hora = Integer.parseInt(horaFCir[0]);
				// minuto = Integer.parseInt(horaFCir[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaICir.length == 3) {
				// hora = Integer.parseInt(horaFCir[0]);
				// minuto = Integer.parseInt(horaFCir[1]);
				// segundo = Integer.parseInt(horaFCir[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraFimCirurgia(horaFimCirurgia.getText().toString());

				// String horaEntRecobro = (String)
				// horaEntradaRecobro.getText();
				// String[] horaEntRec = horaEntRecobro.split(":");
				// if (horaEntRec.length == 2) {
				// hora = Integer.parseInt(horaEntRec[0]);
				// minuto = Integer.parseInt(horaEntRec[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaEntRec.length == 3) {
				// hora = Integer.parseInt(horaEntRec[0]);
				// minuto = Integer.parseInt(horaEntRec[1]);
				// segundo = Integer.parseInt(horaEntRec[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraEntradaRecobro(horaEntradaRecobro.getText()
						.toString());

				// String horaSaidRecobro = (String) horaSaidaRecobro.getText();
				// String[] horaSaiRec = horaSaidRecobro.split(":");
				// if (horaSaiRec.length == 2) {
				// hora = Integer.parseInt(horaSaiRec[0]);
				// minuto = Integer.parseInt(horaSaiRec[1]);
				// horas = new java.sql.Time(hora, minuto, 00);
				// } else if (horaSaiRec.length == 3) {
				// hora = Integer.parseInt(horaSaiRec[0]);
				// minuto = Integer.parseInt(horaSaiRec[1]);
				// segundo = Integer.parseInt(horaSaiRec[2]);
				// horas = new java.sql.Time(hora, minuto, segundo);
				// }

				ci.setHoraFimRecobro(horaSaidaRecobro.getText().toString());

				ci.setId(idCirurgia);
				BlocoComSala blocoCS = (BlocoComSala) sala.getSelectedItem();
				ci.setIdSala(blocoCS.getSala().getId());
				cir = ci;
				try {
					new atualizarCirurgia().execute(ci);
				} catch (Exception rn) {
>>>>>>> FETCH_HEAD
				}
			}
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
								data.setText(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
							}
						}, mYear, mMonth, mDay);
				dpd.show();

			}
		});

		horaCirurgia.setOnClickListener(new OnClickListener() {

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
								horaCirurgia.setText(hourOfDay + ":" + minute
										+ ":00");

							}

						}, mHour, mMinute, true);

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
<<<<<<< HEAD
										+ minute);
=======
										+ minute + ":00");
>>>>>>> FETCH_HEAD

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
<<<<<<< HEAD
								horaEntradaBO.setText(hourOfDay + ":" + minute);
=======
								horaEntradaBO.setText(hourOfDay + ":" + minute
										+ ":00");
>>>>>>> FETCH_HEAD

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
<<<<<<< HEAD
								horaSaidaBO.setText(hourOfDay + ":" + minute);
=======
								horaSaidaBO.setText(hourOfDay + ":" + minute
										+ ":00");
>>>>>>> FETCH_HEAD

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
										+ minute + ":00");

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
<<<<<<< HEAD
								horaSaidaSala.setText(hourOfDay + ":" + minute);
=======
								horaSaidaSala.setText(hourOfDay + ":" + minute
										+ ":00");
>>>>>>> FETCH_HEAD

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
										+ minute + ":00");

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
										+ minute + ":00");

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
										+ minute + ":00");

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
										+ minute + ":00");

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
										+ minute + ":00");

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
<<<<<<< HEAD
										+ minute);
=======
										+ minute + ":00");
>>>>>>> FETCH_HEAD

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});

	}

	/**/
	private int spinnerDaMeATuaPosicaoBloco(ArrayAdapter<BlocoComSala> adapter,
			int salaId) {
		int valor = -1;
		for (int i = 0; i < adapter.getCount(); i++) {
			BlocoComSala pro = (BlocoComSala) adapter.getItem(i);
			if (pro.getSala().getId() == salaId)
				valor = i;
		}
		return valor;
	}

	/**/
	private void preencherAtividade(Cirurgia c) {

		data.setText(c.getData().toString());
		horaCirurgia.setText(c.getHora().toString());
		horaChamadaUtente.setText(c.getHoraChamadaUtente().toString());

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
		informacoesRelevantes.setText(c.getInfoRelevante());
		cirurgia.setText(c.getCirurgia().toString());

		// Spinner
		// int gh = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
		// c.getIdSala());
		sala.setSelection(5);

		int a = spinnerDaMeATuaPosicao(tipoCirurgia.getAdapter(),
				c.getTipoCirurgia());
		tipoCirurgia.setSelection(a);

		a = spinnerDaMeATuaPosicao(especialidadeCirurgica.getAdapter(),
				c.getEspecialidade());
		especialidadeCirurgica.setSelection(a);

		a = spinnerDaMeATuaPosicao(lateralidade.getAdapter(),
				c.getLateralidade());
		lateralidade.setSelection(a);

		a = spinnerDaMeATuaPosicao(classificacaoASA.getAdapter(),
				c.getClassifASA());
		classificacaoASA.setSelection(a);

		a = spinnerDaMeATuaPosicao(destinoDoente.getAdapter(),
				c.getDestinoDoente());
		destinoDoente.setSelection(a);

	}

	private int spinnerDaMeATuaPosicao(SpinnerAdapter adapter, String p) {
		int valor = -1;
		for (int i = 0; i < adapter.getCount(); i++) {
			String pro = (String) adapter.getItem(i);
			if (pro.equals(p))
				valor = i;
		}
		return valor;
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

<<<<<<< HEAD
	private class getBlocoOperatorios extends
			AsyncTask<String, Void, ArrayList<BlocoOperatorio>> {

		@Override
		protected ArrayList<BlocoOperatorio> doInBackground(String... params) {
			ArrayList<BlocoOperatorio> lista = null;

			try {
				lista = WebServiceUtils.getAllBloco(token);

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<BlocoOperatorio> lista) {
			if (lista != null) {
				adaptadorBloco = new ArrayAdapter<>(getBaseContext(),
						android.R.layout.simple_list_item_1, lista);
				bloco.setAdapter(adaptadorBloco);
			} else {
				Toast.makeText(getApplicationContext(),
						"Get Bloco unsuccessful...", Toast.LENGTH_LONG).show();

			}
		}
	}

	private class atualizarCirurgia extends AsyncTask<Cirurgia, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Cirurgia... params) {
			Boolean adicionou = false;

			try {
				adicionou = WebServiceUtils.updateCirurgia(params[0],
						idCirurgia, token);

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

=======
	// private class getBlocoOperatorios extends
	// AsyncTask<String, Void, ArrayList<BlocoOperatorio>> {
	//
	// @Override
	// protected ArrayList<BlocoOperatorio> doInBackground(String... params) {
	// ArrayList<BlocoOperatorio> lista = null;
	//
	// try {
	// lista = WebServiceUtils.getAllBloco(token);
	//
	// } catch (IOException | RestClientException | ParseException
	// | JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return lista;
	// }
	//
	// @Override
	// protected void onPostExecute(ArrayList<BlocoOperatorio> lista) {
	// if (lista != null) {
	// adaptadorBloco = new ArrayAdapter<>(getBaseContext(),
	// android.R.layout.simple_list_item_1, lista);
	// bloco.setAdapter(adaptadorBloco);
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Get Bloco unsuccessful...", Toast.LENGTH_LONG).show();
	//
	// }
	// }
	// }

	private class atualizarCirurgia extends AsyncTask<Cirurgia, Void, Boolean> {

		@Override
		protected void onPreExecute() {

			ringProgressDialog = new ProgressDialog(DadosCirurgia.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Aguarde...");
			ringProgressDialog.setMessage("A carregar Dados...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(false);

			ringProgressDialog.show();
		};
		
		@Override
		protected Boolean doInBackground(Cirurgia... params) {
			Boolean adicionou = false;

			try {
				adicionou = WebServiceUtils.updateCirurgia(params[0],
						idCirurgia, token);

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
			if (result) {
				HomeActivity.setCirurgia(cir);

				Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
						.show();
				finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Erro Atualizar Cirurgia - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
			}
			ringProgressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	private class getBlocosComSala extends
			AsyncTask<String, Void, ArrayList<BlocoComSala>> {
		@Override
		protected void onPreExecute() {

			ringProgressDialog = new ProgressDialog(DadosCirurgia.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Aguarde...");
			ringProgressDialog.setMessage("A carregar Dados...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(false);

			ringProgressDialog.show();
		};

		@Override
		protected ArrayList<BlocoComSala> doInBackground(String... params) {
			ArrayList<BlocoComSala> lista = null;

			try {
				lista = WebServiceUtils.getAllBlocosComSala(token);

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<BlocoComSala> lista) {
			if (lista != null) {
				adaptadorBlococomSala = new ArrayAdapter<BlocoComSala>(
						getBaseContext(), android.R.layout.simple_list_item_1,
						lista);
				adaptadorBlococomSala.sort(new Comparator<BlocoComSala>() {

					@Override
					public int compare(BlocoComSala lhs, BlocoComSala rhs) {
						return lhs
								.getNomeBlocoOperatorio()
								.toLowerCase()
								.compareTo(
										rhs.getNomeBlocoOperatorio()
												.toLowerCase());
					}
				});
				sala.setAdapter(adaptadorBlococomSala);
				int a = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
						HomeActivity.getCirurgia().getIdSala());
				sala.setSelection(a);

				ringProgressDialog.dismiss();

			}
			else
			{
				Toast.makeText(getApplicationContext(), "Erro Get Blocos Com Sala - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
			}
		}
>>>>>>> FETCH_HEAD
	}
}
