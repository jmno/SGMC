package pt.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;


public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private ArrayAdapter<Cirurgia> adaptadorCirurgias;
    private Dialog dialog;
    public static ListView listaCirurgias;
    public static EditText texto_cirurgia;
    public static TextView textoCirurgiaAUsar;
    ProgressDialog ringProgressDialog = null;

	public FragmentMain newInstance(String text){
		FragmentMain mFragment = new FragmentMain();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        texto_cirurgia = (EditText) rootView
                .findViewById(R.id.editText_escolhaCirurgia);
        textoCirurgiaAUsar = (TextView) rootView
                .findViewById(R.id.textViewCirurgia);


        Button btnAdd = (Button) rootView.findViewById(R.id.btnEscolhaCirurgia);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new getAllCirurgias().execute();

                dialog = new Dialog(getView().getContext());

                // tell the Dialog to use the dialog.xml as it's layout
                // description
                dialog.setContentView(R.layout.dialog_procuracirurgias);
                dialog.setTitle("Escolha a Cirurgia:");
                dialog.getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText nomeEditText = (EditText) dialog
                        .findViewById(R.id.editText_escolhaCirurgia);

                nomeEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub
                        adaptadorCirurgias.getFilter().filter(s);
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
                listaCirurgias = (ListView) dialog
                        .findViewById(R.id.listView_cirurgias);

                listaCirurgias
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                                    View arg1, int arg2, long arg3) {

                                Cirurgia c = (Cirurgia) listaCirurgias
                                        .getItemAtPosition(arg2);
                                PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getView().getContext())
                                        .edit()
                                        .putString("idCirurgia",
                                                String.valueOf(c.getId()))
                                        .commit();
                                PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getView().getContext()).edit()
                                        .putInt("idUtente", c.getIdUtente())
                                        .commit();
                                PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getView().getContext())
                                        .edit()
                                        .putString("idEquipa",
                                                String.valueOf(c.getIdEquipa()))
                                        .commit();
                                HomeActivity.setCirurgia(c);
                                dialog.dismiss();
                            }
                        });


                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String cirurgia = PreferenceManager
                                .getDefaultSharedPreferences(
                                        getView().getContext()).getString(
                                        "idCirurgia",
                                        "defaultStringIfNothingFound");
                        textoCirurgiaAUsar.setText(cirurgia);
                    }
                });

            }
            });
		return rootView;


	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
        if(HomeActivity.getCirurgia()!=null)
            textoCirurgiaAUsar.setText(HomeActivity.getCirurgia().getId()+"");
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {


		}
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
           }
           return false;
       }
   };




    private class getAllCirurgias extends
            AsyncTask<Cirurgia, Void, ArrayList<Cirurgia>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados...");

            ringProgressDialog.setCancelable(true);
            ringProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
            ringProgressDialog.show();
        };

        @Override
        protected ArrayList<Cirurgia> doInBackground(Cirurgia... params) {
            ArrayList<Cirurgia> lista = null;
            try {
                lista = WebServiceUtils.getAllCirurgias(HomeActivity.getToken());
            } catch ( IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Cirurgia> lista) {
            if (lista != null) {
                adaptadorCirurgias = new ArrayAdapter<Cirurgia>(
                        getView().getContext(), android.R.layout.simple_list_item_1,
                        lista);
                adaptadorCirurgias.sort(new Comparator<Cirurgia>() {

                    @Override
                    public int compare(Cirurgia lhs, Cirurgia rhs) {
                        return ("" + lhs.getId()).compareTo(("" + rhs.getId()));
                    }
                });
                listaCirurgias.setAdapter(adaptadorCirurgias);
                ringProgressDialog.dismiss();
                dialog.show();
            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(
                        getView().getContext(),
                        "Erro Get Cirurgias - Verifique a Internet e repita o Processo",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
