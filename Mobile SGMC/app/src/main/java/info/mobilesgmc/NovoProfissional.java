package info.mobilesgmc;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import info.mobilesgmc.modelo.ProfissonalSaude;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.Tipo;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NovoProfissional extends Fragment {


    private ProfissonalSaude profissional;
    ProgressDialog ringProgressDialog = null;
    private ArrayAdapter<Tipo> adaptadorTipo;
    private Spinner spinnerTipo;
    private EditText nome;
    private EditText cc;



    public NovoProfissional newInstance(String text){
        NovoProfissional mFragment = new NovoProfissional();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_novo_profissional , container, false);
        spinnerTipo = (Spinner) rootView.findViewById(R.id.spinner_TipoProf);
        nome = (EditText) rootView.findViewById(R.id.editText_NovoProf_Nome);
        cc = (EditText) rootView.findViewById(R.id.editText_NovoProf_CC);

        new getTipo().execute();

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dados_intra_operatorio, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // aqui
        int id = item.getItemId();
        if (id == R.id.action_saveIntra) {
            guardarProfissionalSaude();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarProfissionalSaude()
    {

        profissional = new ProfissonalSaude();
        profissional.setCc(cc.getText().toString());
        Tipo ti = (Tipo) spinnerTipo.getSelectedItem();
        profissional.setIdTipo(ti.getId());
        profissional.setNome(nome.getText().toString());


        new adicionarProfissionalSaude().execute(profissional);
    }

    private class adicionarProfissionalSaude extends
            AsyncTask<ProfissonalSaude, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Profissional...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };

        @Override
        protected Boolean doInBackground(ProfissonalSaude... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.adicionarProfissionalSaude(
                        params[0], HomeActivity.getToken());
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

            Toast.makeText(getActivity().getApplicationContext(), a, Toast.LENGTH_LONG)
                    .show();
            ringProgressDialog.dismiss();
            ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
            ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
        }

    }

    private class getTipo extends AsyncTask<String, Void, ArrayList<Tipo>> {

        @Override
        protected ArrayList<Tipo> doInBackground(String... params) {
            ArrayList<Tipo> lista = null;

            try {
                lista = WebServiceUtils.getAllTipo(HomeActivity.getToken());
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Tipo> lista) {
            if (lista != null) {
                adaptadorTipo = new ArrayAdapter<Tipo>(getActivity().getBaseContext(),
                        android.R.layout.simple_list_item_1, lista);
                spinnerTipo.setAdapter(adaptadorTipo);
                // new Notifications(getApplicationContext(),
                // "Connexão Efetuada com Sucesso!");
            } else
            {
                Toast.makeText(getActivity().getBaseContext(), "Erro Get Tipos - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
            }
        }

    }


}
