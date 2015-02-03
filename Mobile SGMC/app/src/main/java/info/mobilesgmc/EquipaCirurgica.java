package info.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import info.mobilesgmc.modelo.EquipaComJuncao;
import info.mobilesgmc.modelo.ProfissionalDaCirurgia;
import info.mobilesgmc.modelo.ProfissonalSaude;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.Tipo;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;

//import pt.mobilesgmc.modelo.Notifications;

public class EquipaCirurgica extends Fragment implements Serializable {
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
	public static ListView listaEquipas;
	public EquipaComJuncao equipaCirurgica;
	ProgressDialog ringProgressDialog = null;
    private Boolean adicionou = false;


    public EquipaCirurgica newInstance(String text){
        EquipaCirurgica mFragment = new EquipaCirurgica();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_equipa_cirurgica, container, false);
        token = HomeActivity.getToken();

        atualizaAGui();



        editNomeEquipa = (EditText) rootView.findViewById(R.id.edit_text_NomeEquipa);

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

                Log.i("equipa","Não foi encontrada Equipa");}
        }
        catch (Exception e)
        {
            Log.i("erro", "Equipa Não Selecionada");
        }

        setHasOptionsMenu(true);
        return rootView;
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
	
	public void procurarEquipas(){
				new getEquipas().execute(token);
				// new getAllCirurgias().execute();

				dialogoEquipas = new Dialog(getActivity());

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
				nomeEditText.setHint("Nome Equipa Cirurgica ..");
				listaEquipas = (ListView) dialogoEquipas
						.findViewById(R.id.listView_equipas);

				listaEquipas.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						equipaCirurgica = (EquipaComJuncao) listaEquipas
								.getItemAtPosition(arg2);
                        HomeActivity.setEquipa(equipaCirurgica);
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

				nomeEditText.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						adaptadorEquipa.getFilter().filter(s);
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
				/****************/


			

	}


	
	public void guardarEquipa(){

				LinkedList<ProfissionalDaCirurgia> lista = new LinkedList<>();
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
                    Log.i("nada","nada");

                if(HomeActivity.getEquipa()==null)
                HomeActivity.setEquipa(new EquipaComJuncao());
                HomeActivity.getEquipa().setListaProfissionais(lista);
                HomeActivity.getEquipa().setNomeEquipa(nomeEquipa);
                new adicionarOuAtualizarEquipa().execute(HomeActivity.getEquipa());

		
	}
	public void preencheSpinnersComEquipa(EquipaComJuncao e) {
		EquipaComJuncao equipa = e;
		reloadSpinners();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.equipa_cirurgica, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		// aqui
		int id = item.getItemId();
		if (id == R.id.action_search) {
			procurarEquipas();
			return true;
		}

		if(id == R.id.action_saveEquipa){
			guardarEquipa();
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



	/*private class adicionarEquipa extends
            AsyncTask<List<ProfissionalDaCirurgia>, Void, Boolean> {
                @Override
                protected void onPreExecute() {
                    ringProgressDialog = new ProgressDialog(EquipaCirurgica.this);
                    ringProgressDialog.setIcon(R.drawable.ic_launcher);
                    ringProgressDialog.setTitle("Please wait...");
                    ringProgressDialog.setMessage("A Adicionar Equipa...");

                    //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
			ringProgressDialog.setCancelable(false);
			ringProgressDialog.show();
		};
		@Override
		protected Boolean doInBackground(List<ProfissionalDaCirurgia>... params) {
			Boolean adicionou = false;
			String nomeEquipaFinal = nomeEquipa.trim();
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
				
			} catch (ParseException | IOException | JSONException
					| RestClientException | UnknownError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return adicionou;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String a = (result ? "Equipa Adicionado com Sucesso!"
					: "Equipa Não Adicionada! Verifique Ligação");
			Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
					.show();
			if (result) {
//				Intent main = new Intent(getBaseContext(),
//						HomeActivity.class);
//				startActivity(main);
				ringProgressDialog.dismiss();
				finish();
			}
			super.onPostExecute(result);
		}

	}*/



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


			} else
			{
				Toast.makeText(getActivity().getApplicationContext(), "Erro Get Profissionais- Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
			}
			
		}
	}

	public void populateSpinners(ArrayList<ProfissonalSaude> lista, int id) {
		Collections.sort(lista, ProfissonalSaude.Comparators.NAME);
	
		switch (id) {
		case 1:
			adaptadorCirurgiao = new ArrayAdapter<ProfissonalSaude>(
                    getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerCirurgiao = (Spinner) getView().findViewById(R.id.spinner_Cirurgião);
			// adaptadorCirurgiao.sort(new Comparator<ProfissonalSaude>() {
			//
			// @Override
			// public int compare(ProfissonalSaude lhs, ProfissonalSaude rhs) {
			// return lhs.getNome().compareTo(rhs.getNome());
			// }
			// });
			spinnerCirurgiao.setAdapter(adaptadorCirurgiao);

			adaptador1ajudante = new ArrayAdapter<ProfissonalSaude>(
                    getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerPrimAjudante = (Spinner) getView().findViewById(R.id.spinner_1Ajudante);
			spinnerPrimAjudante.setAdapter(adaptador1ajudante);

			adaptador2ajudante = new ArrayAdapter<ProfissonalSaude>(
                    getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerSegundoAjudante = (Spinner) getView().findViewById(R.id.spinner_2Ajudante);
			spinnerSegundoAjudante.setAdapter(adaptador2ajudante);

			adaptador3ajudante = new ArrayAdapter<ProfissonalSaude>(
                    getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerTerceiroAjudante = (Spinner) getView().findViewById(R.id.spinner_3Ajudante);
			spinnerTerceiroAjudante.setAdapter(adaptador3ajudante);
            if(adicionou)
                preencheSpinnersComEquipa(HomeActivity.getEquipa());
            break;
		case 2:
			adaptadorEnfermeiro = new ArrayAdapter<ProfissonalSaude>(
                    getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
					lista);
			spinnerAnestesista = (Spinner) getView().findViewById(R.id.spinner_Anestesista);

			spinnerAnestesista.setAdapter(adaptadorEnfermeiro);
			spinnerAssistente = (Spinner) getView().findViewById(R.id.spinner_Assistente_Operacional);
			spinnerAssistente.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroinstrumentista = (Spinner) getView().findViewById(R.id.spinner_Enf_Instrumentista);
			spinnerEnfermeiroinstrumentista.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroCiruculante = (Spinner) getView().findViewById(R.id.spinner_Enf_Circulante);
			spinnerEnfermeiroCiruculante.setAdapter(adaptadorEnfermeiro);
			spinnerEnfermeiroAnestesia = (Spinner) getView().findViewById(R.id.spinner_Enf_de_Anestesia);
			spinnerEnfermeiroAnestesia.setAdapter(adaptadorEnfermeiro);
            if(adicionou)
                preencheSpinnersComEquipa(HomeActivity.getEquipa());
			break;

		}

	}

	


	private class getEquipas extends
			AsyncTask<String, Void, ArrayList<EquipaComJuncao>> {
		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(getActivity());
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Please wait...");
			ringProgressDialog.setMessage("A verificar Equipas...");
			
			//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
			ringProgressDialog.setCancelable(true);
            ringProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);

                }
            });

			ringProgressDialog.show();
		};
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
                        getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
						lista);
				adaptadorEquipa.sort(new Comparator<EquipaComJuncao>() {

					@Override
					public int compare(EquipaComJuncao lhs, EquipaComJuncao rhs) {
						return lhs.getNomeEquipa().toLowerCase()
								.compareTo(rhs.getNomeEquipa().toLowerCase());
					}
				});
				listaEquipas.setAdapter(adaptadorEquipa);
				ringProgressDialog.dismiss();
                dialogoEquipas.show();
			}
			else
			{
                ringProgressDialog.dismiss();

                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Equipas - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
			}

		}
	}

	private class getEquipaByID extends
			AsyncTask<Integer, Void, EquipaComJuncao> {
		
		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(getActivity());
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Please wait...");
			ringProgressDialog.setMessage("A verificar Equipa...");
			
			//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
			ringProgressDialog.setCancelable(false);
			ringProgressDialog.show();
		};
		
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
                HomeActivity.setEquipa(equipa);
				Log.i("equipa",equipa.toString());
				preencheSpinnersComEquipa(equipa);



				editNomeEquipa.setText(equipa.getNomeEquipa());
				ringProgressDialog.dismiss();
				// adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
				// getBaseContext(), android.R.layout.simple_list_item_1,
				// lista);
				// listaEquipas.setAdapter(adaptadorEquipa);
				// dialogoEquipas.show();
			}
			else
			{
				Toast.makeText(getActivity(), "Erro Get Equipa Com Junção - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
			}
		}
	}

    private class adicionarOuAtualizarEquipa extends
            AsyncTask<EquipaComJuncao, Void, Integer> {

        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A verificar Equipa...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };

        @Override
        protected Integer doInBackground(EquipaComJuncao... params) {
            int resultado = 0;

            try {
                resultado = WebServiceUtils.adicionarOuAtualizarEquipa(params[0], HomeActivity.getCirurgia().getId(), token);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(Integer resultado) {
            if (resultado != 0) {
                HomeActivity.getEquipa().setIdEquipa(resultado);
                HomeActivity.getCirurgia().setIdEquipa(resultado);
                ringProgressDialog.dismiss();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
                // adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
                // getBaseContext(), android.R.layout.simple_list_item_1,
                // lista);
                // listaEquipas.setAdapter(adaptadorEquipa);
                // dialogoEquipas.show();
            }
            else
            {
                Toast.makeText(getActivity(), "Erro Get Equipa Com Junção - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
            }
        }
    }

}
