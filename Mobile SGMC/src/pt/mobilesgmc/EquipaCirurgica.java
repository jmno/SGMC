package pt.mobilesgmc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.EquipaComJuncao;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.ProfissionalDaCirurgia;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.Tipo;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.mobilegsmc.R;

//import pt.mobilesgmc.modelo.Notifications;

public class EquipaCirurgica extends Activity implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = -8374033655851874766L;
	private int idTipo;
	private ArrayAdapter<Tipo> adaptadorTipo;
	private ArrayAdapter<ProfissonalSaude> adaptadorCirurgiao;
	private ArrayAdapter<ProfissonalSaude> adaptador1ajudante;
	private ArrayAdapter<ProfissonalSaude> adaptador2ajudante;
	private ArrayAdapter<ProfissonalSaude> adaptador3ajudante;
	private ArrayAdapter<ProfissonalSaude> adaptadorEnfermeiro;
	private ArrayAdapter<EquipaComJuncao> adaptadorEquipa;
	private Spinner spinnerTipo;
	private String token;
	private Dialog dialog;
	private Dialog dialogoEquipas;
	FlyOutContainer root;
	private Spinner spinnerAssistente;
	private Spinner spinnerCirurgiao;
	private Spinner spinnerPrimAjudante;
	private Spinner spinnerSegundoAjudante;
	private Spinner spinnerTerceiroAjudante;
	private Spinner spinnerAnestesista;
	private Spinner spinnerEnfermeiroinstrumentista;
	private Spinner spinnerEnfermeiroCiruculante;
	private Spinner spinnerEnfermeiroAnestesia;
	private Spinner spinnerEquipa;
	private EditText editNomeEquipa;
	private String nomeEquipa;
	private int idCirurgia;
	public static ListView listaEquipas;
	public EquipaComJuncao equipaCirurgica;
	public int idEquipa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipa_cirurgica);

		// new getProfissionaisSaude().execute();
		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");
		atualizaAGui();

		idCirurgia = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext())
				.getString("idCirurgia", "0"));
		idEquipa = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext())
				.getString("idEquipa", "-1"));
		
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_equipa_cirurgica, null);
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;
		int margin = (80 * (int) dpWidth) / 100;
		root.setMargin(margin);
		this.setContentView(root);

//		idEquipa = Integer.parseInt(PreferenceManager
//				.getDefaultSharedPreferences(getApplicationContext())
//				.getString("idEquipa", "-1"));

		root.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeTop() {
				// Toast.makeText(SampleActivity.this, "top",
				// Toast.LENGTH_SHORT).show();
			}

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

			public void onSwipeBottom() {
				// Toast.makeText(SampleActivity.this, "bottom",
				// Toast.LENGTH_SHORT).show();
			}

			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}

		});
		editNomeEquipa = (EditText) findViewById(R.id.edit_text_NomeEquipa);

<<<<<<< HEAD
		TextView btnEquipa = (TextView) root.findViewById(R.id.textViewMenuEquipaCirurgica);

		//VOLTAR AQUI !!!!!!!
		TextView btnUtentes = (TextView) root.findViewById(R.id.textViewMenuUtentes);
=======
	
		TextView btnUtentes = (TextView) findViewById(R.id.textViewMenuUtentes);
>>>>>>> FETCH_HEAD
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent utentes = new Intent(getBaseContext(),
						UtentesActivity.class);
				toggleMenu(findViewById(R.layout.activity_utentes));
				startActivity(utentes);
<<<<<<< HEAD
			}
		});
		
		TextView btnDadosCirurgia = (TextView) root.findViewById(R.id.textViewMenuDadosCirurgia);
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (HomeActivity.getCirurgia() != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosCirurgia.class);
					toggleMenu(findViewById(R.layout.activity_dados_cirurgia));

					startActivity(dados);
				} else {
					Log.i("sgmc", "Não tem cirurgia escolhida");
					root.toggleMenu();
				}
=======
				finish();
			}
		});
		
		TextView btnDadosCirurgia = (TextView) findViewById(R.id.textViewMenuDadosCirurgia);
		btnDadosCirurgia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (HomeActivity.getCirurgia() != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosCirurgia.class);
					toggleMenu(findViewById(R.layout.activity_dados_cirurgia));

					startActivity(dados);
					finish();
				} else {
					Log.i("sgmc", "N�o tem cirurgia escolhida");
					root.toggleMenu();
				}
				
			}
		});
		
		TextView btnDadosIntraOperatorio = (TextView) findViewById(R.id.textViewMenuDadosIntraOperatorio);
		btnDadosIntraOperatorio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (HomeActivity.getCirurgia() != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosINtraOperatorioActivity.class);
					toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
					startActivity(dados);
					finish();
				} else {
					Log.i("sgmc", "N�o tem cirurgia escolhida");
					root.toggleMenu();
				}
				
>>>>>>> FETCH_HEAD
			}
		});
		


		Button btnAdd = (Button) findViewById(R.id.btn_AdicionarProfissional);
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new getTipo().execute();
				dialog = new Dialog(EquipaCirurgica.this);

				// tell the Dialog to use the dialog.xml as it's layout
				// description
				dialog.setContentView(R.layout.dialog_novoprofissional);
				dialog.setTitle("Defina o Novo Profissional:");
				dialog.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
						if (!nomeEditText.getText().equals("")) {
							ProfissonalSaude p = new ProfissonalSaude();

							p.setNome(nomeEditText.getText().toString());

							p.setCc(ccEditText.getText().toString());
							Tipo ti = (Tipo) spinnerTipo.getSelectedItem();
							p.setIdTipo(ti.getId());
							try {
								new adicionarProfissionalSaude().execute(p);
								atualizaAGui();
							} catch (Exception e) {

							}
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

			}
		});

		Button btnGuardarEquipa = (Button) findViewById(R.id.btn_GuardarEquipaProfissionais);

		btnGuardarEquipa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<ProfissionalDaCirurgia> lista = new LinkedList<>();
				ProfissonalSaude teste = new ProfissonalSaude();
				ProfissionalDaCirurgia profissional;

				if (spinnerCirurgiao.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerCirurgiao
							.getItemAtPosition(spinnerCirurgiao
									.getSelectedItemPosition());

					profissional = new ProfissionalDaCirurgia(teste, "C");

					lista.add(profissional);
				}

				if (spinnerPrimAjudante.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerPrimAjudante
							.getItemAtPosition(spinnerPrimAjudante
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "1A");
					lista.add(profissional);
				}

				if (spinnerSegundoAjudante.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerSegundoAjudante
							.getItemAtPosition(spinnerSegundoAjudante
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "2A");
					lista.add(profissional);
				}

				if (spinnerTerceiroAjudante.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerTerceiroAjudante
							.getItemAtPosition(spinnerTerceiroAjudante
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "3A");
					lista.add(profissional);
				}

				if (spinnerAnestesista.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerAnestesista
							.getItemAtPosition(spinnerAnestesista
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "A");
					lista.add(profissional);
				}

				if (spinnerEnfermeiroinstrumentista.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerEnfermeiroinstrumentista
							.getItemAtPosition(spinnerEnfermeiroinstrumentista
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "EI");
					lista.add(profissional);
				}

				if (spinnerEnfermeiroCiruculante.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerEnfermeiroCiruculante
							.getItemAtPosition(spinnerEnfermeiroCiruculante
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "EC");
					lista.add(profissional);
				}

				if (spinnerEnfermeiroAnestesia.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerEnfermeiroAnestesia
							.getItemAtPosition(spinnerEnfermeiroAnestesia
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "EA");
					lista.add(profissional);
				}

				if (spinnerAssistente.getSelectedItemPosition() != 0) {
					teste = (ProfissonalSaude) spinnerAssistente
							.getItemAtPosition(spinnerAssistente
									.getSelectedItemPosition());
					profissional = new ProfissionalDaCirurgia(teste, "AO");

					lista.add(profissional);
				}

				nomeEquipa = editNomeEquipa.getText().toString();
				if (!lista.isEmpty())
					new adicionarEquipa().execute(lista);

			}
		});

		Button btnProcurarCirurgias = (Button) root
				.findViewById(R.id.btn_PesquisarEquipas);
		btnProcurarCirurgias.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/****************/
				new getEquipas().execute(token);
				// new getAllCirurgias().execute();

				dialogoEquipas = new Dialog(EquipaCirurgica.this);

				// tell the Dialog to use the dialog.xml as it's layout
				// description
				dialogoEquipas.setContentView(R.layout.dialog_procurarequipas);
				dialogoEquipas.setTitle("Escolha a Equipa:");
				dialogoEquipas
						.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				final EditText nomeEditText = (EditText) dialogoEquipas
						.findViewById(R.id.editText_escolhaEquipa);
				listaEquipas = (ListView) dialogoEquipas
						.findViewById(R.id.listView_equipas);

				listaEquipas.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						equipaCirurgica = (EquipaComJuncao) listaEquipas
								.getItemAtPosition(arg2);
						preencheSpinnersComEquipa(equipaCirurgica);
						editNomeEquipa.setText(equipaCirurgica.getNomeEquipa());
						dialogoEquipas.dismiss();
					}
				});

				dialogoEquipas.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {

					}
				});

				/****************/

				// spinnerEquipa.setOnItemSelectedListener(new
				// OnItemSelectedListener()
				// {
				//
				//
				//
				// @Override
				// public void onItemSelected(AdapterView<?> arg0, View arg1,
				// int arg2, long arg3) {
				// EquipaComJuncao equipa = (EquipaComJuncao)
				// spinnerEquipa.getItemAtPosition(spinnerEquipa.getSelectedItemPosition());
				// //
				//
				//
				// }
				//
				// @Override
				// public void onNothingSelected(AdapterView<?> arg0) {
				// // TODO Auto-generated method stub
				//
				// }
				// });

			}

			

			
		});
		try{
		Log.i("equipa",HomeActivity.getCirurgia().toString() + " idEquipa: " + HomeActivity.getCirurgia().getIdEquipa());
		if (HomeActivity.getCirurgia().getIdEquipa() != 0) {
			try{
			new getEquipaByID().execute(HomeActivity.getCirurgia().getIdEquipa());
			}
			catch(Exception e)
			{
				Log.i("Webservice:", "ERROR - " + e.getMessage());
			}
		}
		else{
			
<<<<<<< HEAD
			Log.i("equipa","N�o foi encontrada Equipa");}
=======
			Log.i("equipa","N�o foi encontrada Equipa");}
		}
		catch (Exception e)
		{
			Log.i("erro", "Equipa N�o Selecionada");
		}
>>>>>>> FETCH_HEAD
	}

	private int spinnerDaMeATuaPosicao(ArrayAdapter<ProfissonalSaude> adapter,
			ProfissonalSaude p) {
		int valor = -1;
		for (int i = 0; i < adapter.getCount(); i++) {
			ProfissonalSaude pro = (ProfissonalSaude) adapter.getItem(i);
			if (pro.getId() == p.getId())
				valor = i;
		}
		return valor;
	}

	private void atualizaAGui() {
		new getProfissionaisSaudeByTipo().execute("1", token);
		new getProfissionaisSaudeByTipo().execute("2", token);
	}
	
	
	public void reloadSpinners() {
		spinnerCirurgiao.setSelection(0);
		spinnerPrimAjudante.setSelection(0);
		spinnerSegundoAjudante.setSelection(0);
		spinnerTerceiroAjudante.setSelection(0);
		spinnerAnestesista.setSelection(0);
		spinnerAssistente.setSelection(0);
		spinnerEnfermeiroAnestesia.setSelection(0);
		spinnerEnfermeiroCiruculante.setSelection(0);
		spinnerEnfermeiroinstrumentista.setSelection(0);
	}

	public void preencheSpinnersComEquipa(EquipaComJuncao e) {
		EquipaComJuncao equipa = e;
		reloadSpinners();
		Toast.makeText(getApplicationContext(), equipa.getNomeEquipa(),
				Toast.LENGTH_SHORT).show();

		String nomeEquipa = equipa.getNomeEquipa();

		List<ProfissionalDaCirurgia> profissionalcirurgia = equipa
				.getListaProfissionais();

	

		// editNomeEquipa.setText(nomeEquipa);

		for (ProfissionalDaCirurgia prof : profissionalcirurgia) {

			String tipo = prof.getTipo();

			if (tipo.equals("C")) {

				spinnerCirurgiao.setSelection(spinnerDaMeATuaPosicao(
						adaptadorCirurgiao, prof.getProfissional()));
			}
			if (tipo.equals("1A")) {

				spinnerPrimAjudante
						.setSelection(spinnerDaMeATuaPosicao(
								adaptador1ajudante,
								prof.getProfissional()));
			}
			if (tipo.equals("2A")) {

				spinnerSegundoAjudante
						.setSelection(spinnerDaMeATuaPosicao(
								adaptador2ajudante,
								prof.getProfissional()));
			}
			if (tipo.equals("3A")) {

				spinnerTerceiroAjudante
						.setSelection(spinnerDaMeATuaPosicao(
								adaptador3ajudante,
								prof.getProfissional()));
			}
			if (tipo.equals("A")) {

				spinnerAnestesista.setSelection(spinnerDaMeATuaPosicao(
						adaptadorEnfermeiro, prof.getProfissional()));
			}
			if (tipo.equals("EI")) {

				spinnerEnfermeiroinstrumentista
						.setSelection(spinnerDaMeATuaPosicao(
								adaptadorEnfermeiro,
								prof.getProfissional()));
			}
			if (tipo.equals("EC")) {

				spinnerEnfermeiroCiruculante
						.setSelection(spinnerDaMeATuaPosicao(
								adaptadorEnfermeiro,
								prof.getProfissional()));
			}
			if (tipo.equals("EA")) {

				spinnerEnfermeiroAnestesia
						.setSelection(spinnerDaMeATuaPosicao(
								adaptadorEnfermeiro,
								prof.getProfissional()));
			}
			if (tipo.equals("AO")) {

				spinnerAssistente.setSelection(spinnerDaMeATuaPosicao(
						adaptadorEnfermeiro, prof.getProfissional()));
			}

		}

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

		// aqui
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
				adicionou = WebServiceUtils.adicionarProfissionalSaude(
						params[0], token);
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

	private class adicionarEquipa extends
			AsyncTask<List<ProfissionalDaCirurgia>, Void, Boolean> {
		
		@Override
		protected Boolean doInBackground(List<ProfissionalDaCirurgia>... params) {
			Boolean adicionou = false;
			String nomeEquipaFinal = nomeEquipa;
			try {
				int idEquipa = WebServiceUtils.getEquipaID(nomeEquipaFinal,
						token);
				if (idEquipa != -1) {
					nomeEquipaFinal = nomeEquipaFinal.concat("1");
					adicionou = WebServiceUtils.adicionarEquipa(
							nomeEquipaFinal, idCirurgia, token);
				} else {
					adicionou = WebServiceUtils.adicionarEquipa(
							nomeEquipaFinal, idCirurgia, token);
				}
				idEquipa = WebServiceUtils.getEquipaID(nomeEquipaFinal, token);
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("idEquipa",""+idEquipa).commit();
				HomeActivity.getCirurgia().setIdEquipa(idEquipa);
				adicionou = WebServiceUtils.adicionarJuncoes(params[0],
						idEquipa, token);
				if (adicionou) {
//					Intent main = new Intent(getBaseContext(),
//							HomeActivity.class);
//					startActivity(main);
					finish();
				}
			} catch (ParseException | IOException | JSONException
					| RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return adicionou;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String a = (result ? "Equipa Adicionado com Sucesso!"
					: "Equipa Não Adicionado!");
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
				lista = WebServiceUtils.getAllTipo(token);
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
				dialog.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Get Tipo unsuccessful...", Toast.LENGTH_LONG).show();

			}
		}

	}

	private class getProfissionaisSaudeByTipo extends
			AsyncTask<String, Void, ArrayList<ProfissonalSaude>> {
	
		@Override
		protected ArrayList<ProfissonalSaude> doInBackground(String... params) {
			ArrayList<ProfissonalSaude> lista = null;

			try {
				lista = WebServiceUtils.getAllProfissionalSaudeByIdTipo(
						Integer.parseInt(params[0]), token);
				idTipo = Integer.parseInt(params[0]);
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
		Collections.sort(lista, ProfissonalSaude.Comparators.NAME);
	
		switch (id) {
		case 1:
			adaptadorCirurgiao = new ArrayAdapter<ProfissonalSaude>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerCirurgiao = (Spinner) findViewById(R.id.spinner_Cirurgião);
			// adaptadorCirurgiao.sort(new Comparator<ProfissonalSaude>() {
			//
			// @Override
			// public int compare(ProfissonalSaude lhs, ProfissonalSaude rhs) {
			// return lhs.getNome().compareTo(rhs.getNome());
			// }
			// });
			spinnerCirurgiao.setAdapter(adaptadorCirurgiao);

			adaptador1ajudante = new ArrayAdapter<ProfissonalSaude>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerPrimAjudante = (Spinner) findViewById(R.id.spinner_1Ajudante);
			spinnerPrimAjudante.setAdapter(adaptador1ajudante);

			adaptador2ajudante = new ArrayAdapter<ProfissonalSaude>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerSegundoAjudante = (Spinner) findViewById(R.id.spinner_2Ajudante);
			spinnerSegundoAjudante.setAdapter(adaptador2ajudante);

			adaptador3ajudante = new ArrayAdapter<ProfissonalSaude>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerTerceiroAjudante = (Spinner) findViewById(R.id.spinner_3Ajudante);
			spinnerTerceiroAjudante.setAdapter(adaptador3ajudante);
		case 2:
			adaptadorEnfermeiro = new ArrayAdapter<ProfissonalSaude>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerAnestesista = (Spinner) findViewById(R.id.spinner_Anestesista);

			spinnerAnestesista.setAdapter(adaptadorEnfermeiro);
			spinnerAssistente = (Spinner) findViewById(R.id.spinner_Assistente_Operacional);
			spinnerAssistente.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroinstrumentista = (Spinner) findViewById(R.id.spinner_Enf_Instrumentista);
			spinnerEnfermeiroinstrumentista.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroCiruculante = (Spinner) findViewById(R.id.spinner_Enf_Circulante);
			spinnerEnfermeiroCiruculante.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroAnestesia = (Spinner) findViewById(R.id.spinner_Enf_de_Anestesia);
			spinnerEnfermeiroAnestesia.setAdapter(adaptadorEnfermeiro);
			break;

		}

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

	private class getEquipas extends
			AsyncTask<String, Void, ArrayList<EquipaComJuncao>> {
		
		@Override
		protected ArrayList<EquipaComJuncao> doInBackground(String... params) {
			ArrayList<EquipaComJuncao> lista = null;

			try {
				lista = WebServiceUtils.getAllEquipas(token);

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return lista;
		}

		@Override
		protected void onPostExecute(ArrayList<EquipaComJuncao> lista) {
			if (lista != null) {
				adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
						getBaseContext(), android.R.layout.simple_list_item_1,
						lista);
				adaptadorEquipa.sort(new Comparator<EquipaComJuncao>() {

					@Override
					public int compare(EquipaComJuncao lhs, EquipaComJuncao rhs) {
						return lhs.getNomeEquipa().toLowerCase()
								.compareTo(rhs.getNomeEquipa().toLowerCase());
					}
				});
				listaEquipas.setAdapter(adaptadorEquipa);
				dialogoEquipas.show();
			}

		}
	}

	private class getEquipaByID extends
			AsyncTask<Integer, Void, EquipaComJuncao> {
		
		@Override
		protected EquipaComJuncao doInBackground(Integer... params) {
			EquipaComJuncao equipa = null;

			try {
				equipa = WebServiceUtils.getEquipaByID(params[0], token);

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return equipa;
		}

		@Override
		protected void onPostExecute(EquipaComJuncao equipa) {
			if (equipa != null) {
				Log.i("equipa",equipa.toString());
				preencheSpinnersComEquipa(equipa);
				editNomeEquipa.setText(equipa.getNomeEquipa());
				
				// adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
				// getBaseContext(), android.R.layout.simple_list_item_1,
				// lista);
				// listaEquipas.setAdapter(adaptadorEquipa);
				// dialogoEquipas.show();
			}
		}
	}

}
