package pt.mobilesgmc;

import java.util.Calendar;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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
	private int mYear, mMonth, mDay, mHour, mMinute;

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

		data = (TextView) findViewById(R.id.TextViewData);
		horaChamadaUtente = (TextView) findViewById(R.id.editTextHoraChamadaUtente);
		especialidadeCirurgica = (Spinner) findViewById(R.id.spinnerEspecialidade);
		tipoCirurgia = (Spinner) findViewById(R.id.spinnerTipoCirurgia);
		sala = (Spinner) findViewById(R.id.spinnerSala);
		lateralidade = (Spinner) findViewById(R.id.spinnerLateralidade);
		classificacaoASA =(Spinner) findViewById(R.id.spinnerASA);
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
										+ minute);

							}
						}, mHour, mMinute, true);

				dpd.show();

			}
		});

	}

	private void preencherAtividade(Cirurgia c) {
		
		data.setText(c.getData().getDay()+"/"+c.getData().getMonth()+"/"+c.getData().getYear());
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
}
