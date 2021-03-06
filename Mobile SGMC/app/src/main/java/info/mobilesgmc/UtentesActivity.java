package info.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import info.mobilesgmc.modelo.Cirurgia;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.Utente;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;

public class UtentesActivity extends Fragment {

    private TextView textView__idade;
    private TextView textView_nomeUtente;
    private TextView textView_dataNasc;
    private TextView textView_numProcesso;
    private TextView textView_subsistema;
    private TextView textView_alergia;
    private TextView textView_patologia;
    private TextView textView_antecedentes_cirugicos;
	private EditText inputSearch;
	private String token;
	private int idUtente = 0;
	ProgressDialog ringProgressDialog = null;
    private Dialog dialog;
    private  Bitmap imagemPaciente;
    private CircleImageView imagemProfile;
    private Utente u;
    private ArrayAdapter<Utente> adaptadorUtente;
    public static ListView listaUtentes;
    public boolean isForResult = false;
    private Menu menu;

    public UtentesActivity newInstance(String text){
        UtentesActivity mFragment = new UtentesActivity();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_utentes, container, false);
        token = HomeActivity.getToken();



            textView__idade = (TextView) rootView.findViewById(R.id.textView_Utentes_idade);
            textView_alergia = (TextView) rootView.findViewById(R.id.textView_Utentes_alergia);
            textView_antecedentes_cirugicos = (TextView) rootView.findViewById(R.id.textView_Profile_antecedentes);
            textView_dataNasc = (TextView) rootView.findViewById(R.id.textView_Utentes_dataNascimento);
            textView_nomeUtente = (TextView) rootView.findViewById(R.id.textView_Utentes_nome);
            textView_numProcesso = (TextView) rootView.findViewById(R.id.textView_Utentes_numProcesso);
            textView_patologia = (TextView) rootView.findViewById(R.id.textView_Utentes_patologia);
            textView_subsistema = (TextView) rootView.findViewById(R.id.textView_Utentes_subsistema);
            imagemProfile = (CircleImageView) rootView.findViewById(R.id.profile_image);

            if (HomeActivity.getCirurgia() != null) {
                idUtente = HomeActivity.getCirurgia().getIdUtente();
                if (idUtente != 0)
                    new getUtenteById().execute();
                else
                    abrirDialogoPesquisaUtente();


            } else {
                //setTitle("Escolha o Utente");

                abrirDialogoPesquisaUtente();
            }
            setHasOptionsMenu(true);

        return rootView;
    }



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager mFragmentManager = ((HomeActivity)getActivity()).getSupportFragmentManager();

		int id = item.getItemId();
		if (id == R.id.action_Utentes_Search) {
            abrirDialogoPesquisaUtente();
			return true;
		}

        if (id == R.id.action_UtentesAccept){
            HomeActivity.setUtente(u);
            HomeActivity.setIsForResultUtentes(false);
            HomeActivity.getCirurgia().setIdUtente(u.getId());
            int last= HomeActivity.getListaNavegacao().get(HomeActivity.getListaNavegacao().size()-2);
            HomeActivity.getListaNavegacao().remove(HomeActivity.getListaNavegacao().size()-1);
            ((HomeActivity) getActivity()).onItemClickNavigation(last,HomeActivity.getLayoutcontainerid());
            ((HomeActivity) getActivity()).setCheckedItemNavigation(last,true);
        }

        if (id == R.id.action_UtentesCancel){
            int last= HomeActivity.getListaNavegacao().get(HomeActivity.getListaNavegacao().size()-2);
            HomeActivity.getListaNavegacao().remove(HomeActivity.getListaNavegacao().size()-1);
            ((HomeActivity) getActivity()).onItemClickNavigation(last,HomeActivity.getLayoutcontainerid());
            ((HomeActivity) getActivity()).setCheckedItemNavigation(last,true);
        }
		return super.onOptionsItemSelected(item);
	}



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.utentes, menu);

        if(!HomeActivity.getIsForResultUtentes())
        {
            menu.findItem(R.id.action_UtentesAccept).setVisible(false).setEnabled(false);
            menu.findItem(R.id.action_UtentesCancel).setVisible(false).setEnabled(false);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void abrirDialogoPesquisaUtente()
    {
        new getUtentes().execute();

        dialog = new Dialog(getActivity());

        // tell the Dialog to use the dialog.xml as it's layout
        // description
        dialog.setContentView(R.layout.dialog_procuracirurgias);
        dialog.setTitle("Escolha o Utente:");
        dialog.getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final EditText nomeEditText = (EditText) dialog
                .findViewById(R.id.editText_escolhaCirurgia);
        nomeEditText.setHint("Ex: Nome Utente...");
        nomeEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub
                adaptadorUtente.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        listaUtentes = (ListView) dialog
                .findViewById(R.id.listView_cirurgias);

        listaUtentes
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {

                        Utente c = (Utente) listaUtentes
                                .getItemAtPosition(arg2);
                        u = c;
                        new GetImage().execute(u.getSexo());
                        dialog.dismiss();
                    }
                });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
    }

    public void preencherAtividade(Utente utente)
    {

            textView_nomeUtente.setText(utente.getNome());
            textView_numProcesso.setText(utente.getNumProcesso()+"");
            textView_dataNasc.setText(utente.getDataNascimento());
            textView_subsistema.setText(utente.getSubsistema());
            String al = "";
            if(utente.getAlergias()!=null) {
                String[] alergias = utente.getAlergias().split("!");
                for (int i =0; i<alergias.length;i++){
                    al += alergias[i] + "\n";

                }
                textView_alergia.setTextColor(Color.RED);
            }
            else {
                al="Não Tem";
            }
            textView_alergia.setText(al);
            String pat = "";
            if(utente.getPatologias()!=null) {
                String[] patologias = utente.getPatologias().split("!");
                for (int j =0; j<patologias.length;j++){
                    pat += patologias[j] + "\n";
                }
             }
            else{
                pat= "Não Tem";
            }
            textView_patologia.setText(pat);
            String antecedentes = "";
            if(utente.getCirurgias()!=null){
                if(utente.getCirurgias().size()>1){
                    for (int k=0; k<utente.getCirurgias().size(); k++){
                        Cirurgia c = (Cirurgia) utente.getCirurgias().get(k);
                        if(isbeforeNow(c))
                            antecedentes +="* "+c.toString() +"\n";
                    }
                }
                else
                    antecedentes ="Não Tem";

            }
            textView_antecedentes_cirugicos.setText(antecedentes);

            String idade = calculaIdade(utente.getDataNascimento());

                textView__idade.setText(idade);


        if(imagemPaciente!=null)
            imagemProfile.setImageBitmap(imagemPaciente);

    }


    public boolean isbeforeNow(Cirurgia c)
    {
        boolean isBeforeNow =false;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        DateTime dt1 = null;
        try {
            d1 = format.parse(c.getData());
            dt1 = new DateTime(d1);
            isBeforeNow = dt1.isBeforeNow();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return isBeforeNow;

    }

    public String calculaIdade(String data){


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        DateTime dt1 = null;
        String resultado = "";
        String  j = "";
        try{
            d1 = format.parse(data);
             dt1 = new DateTime(d1);
            Period period = new Period(dt1, new DateTime());
            int difYears = period.getYears();
            int difMonths = period.getMonths();
            int difDays = period.getDays();
            int difWeeks = period.getWeeks();

            if(difYears>0){
                if(difYears ==1)
                    resultado = difYears + "Ano";
                else
                resultado = difYears + " Anos";
            }

            else{
                if(difMonths ==1)
                    resultado = difMonths + "Mes";
                else if (difMonths >0){
                     resultado = difMonths + "Meses";
                }
                else{
                     if(difWeeks == 1)
                        resultado = difWeeks + " Semana";
                     else
                         resultado = difWeeks + " Semanas";
                }

            }



          j = period.getYears() + "anos " + period.getMonths()+" " + period.getWeeks()+ " " + period.getDays();
            //j=(Months.monthsBetween(dt1, new DateTime()).getMonths() % 52 + " months, ");
        }
        catch (Exception e){
            e.printStackTrace();
        }
       Log.i("HORAS", j + " agora: " + new DateTime());

        return resultado;

    }

	private class getUtentes extends
			AsyncTask<Integer, Void, ArrayList<Utente>> {
		@Override
		protected void onPreExecute() {

			ringProgressDialog = new ProgressDialog(getActivity());
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Aguarde...");
			ringProgressDialog.setMessage("A Procurar Utentes...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(false);

			ringProgressDialog.show();
		};

		@Override
		protected ArrayList<Utente> doInBackground(Integer... params) {
			ArrayList<Utente> lista = null;

			try {
				lista = WebServiceUtils.getAllUtentes(token);
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}
			return lista;
		}

		protected void onPostExecute(ArrayList<Utente> lista) {
			if (lista != null) {
				adaptadorUtente = new ArrayAdapter<Utente>(getActivity().getBaseContext(),
						android.R.layout.simple_list_item_1, lista);
				adaptadorUtente.sort(new Comparator<Utente>() {

					@Override
					public int compare(Utente lhs, Utente rhs) {
						return lhs.getNome().toLowerCase()
								.compareTo(rhs.getNome().toLowerCase());
					}
				});
				listaUtentes.setAdapter(adaptadorUtente);
                ringProgressDialog.dismiss();
                dialog.show();


			} else {
               ringProgressDialog.dismiss();
                Toast.makeText(getActivity().getBaseContext(),"Erro Get Utente",Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);

			}
		}


	}

    private class getUtenteById extends
            AsyncTask<Integer, Void, Utente> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados Utente...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        };

        @Override
        protected Utente doInBackground(Integer... params) {
            Utente lista = null;

            try {
                lista = WebServiceUtils.getUtenteByID(idUtente, token);
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }

        protected void onPostExecute(Utente lista) {
            if (lista != null) {

                if (idUtente != 0)
                u = lista;
                HomeActivity.setUtente(u);

                ringProgressDialog.dismiss();
                new GetImage().execute(u.getSexo());


            } else {
                Toast.makeText(getActivity().getBaseContext(),
                        "Get Utente unsuccessful...", Toast.LENGTH_LONG)
                        .show();
                ringProgressDialog.dismiss();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);


            }

        }


    }

    private class GetImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados Utente...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        };
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap imagem = null;

            try {
                imagem = WebServiceUtils.getImage(params[0],u.getId()+"");
            } catch (IOException | RestClientException
                    | JSONException e) {
                e.printStackTrace();
            }

            return imagem;
        }


        @Override
        protected void onPostExecute(Bitmap imagem) {
            if (imagem != null) {
                imagemPaciente = imagem;
                ringProgressDialog.dismiss();


            } else {
                ringProgressDialog.dismiss();

            }
            preencherAtividade(u);
        }
    }

}