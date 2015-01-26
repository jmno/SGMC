package pt.mobilesgmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pt.mobilesgmc.modelo.Produtos;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class InstrumentalActivity extends Activity {

    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<Produtos> adaptadorInstrumentos;
    private ListView listaInstrumentos;
    private Dialog dialogoInstrumentos;
    private String token;
    private Button btnEscolheIntrumentos;
    private ListView listaInstrumentosUtilizados;
    private AlertDialog.Builder builder;
    private Produtos p;
    private LinkedList<Produtos> listaP= new LinkedList<Produtos>();
    private ListView listaAparelhosUtilizados;
    private ArrayAdapter<Produtos> adaptadorInstrumentosUtilizados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrumental);

        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        btnEscolheIntrumentos = (Button) findViewById(R.id.btnEscolhaInstrumentos);
        listaInstrumentosUtilizados = (ListView) findViewById(R.id.listView_InstrumentosUtilizados);
        builder = new AlertDialog.Builder(InstrumentalActivity.this);
        listaInstrumentosUtilizados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                p= (Produtos) listaInstrumentosUtilizados.getItemAtPosition(position);

                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("Pretende Apagar?");
                builder.setMessage(p.getNome())
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adaptadorInstrumentosUtilizados.remove(p);
                                listaInstrumentosUtilizados.setAdapter(adaptadorInstrumentosUtilizados);
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });
        btnEscolheIntrumentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getProdutos().execute();
                dialogoInstrumentos = new Dialog(InstrumentalActivity.this);

                // tell the Dialog to use the dialog.xml as it's layout
                // description
                dialogoInstrumentos.setContentView(R.layout.dialog_procuracirurgias);
                dialogoInstrumentos.setTitle("Escolha o Instrumental Utilizado:");
                dialogoInstrumentos
                        .getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText nomeEditText = (EditText) dialogoInstrumentos
                        .findViewById(R.id.editText_escolhaCirurgia);
                nomeEditText.setHint("Nome Instrumento ..");


                nomeEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub
                        adaptadorInstrumentos.getFilter().filter(s);
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
                listaInstrumentos = (ListView) dialogoInstrumentos
                        .findViewById(R.id.listView_cirurgias);

                listaInstrumentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {


                        Produtos l = (Produtos) listaInstrumentos.getItemAtPosition(arg2);

                        listaP.add(l);
                        adaptadorInstrumentosUtilizados = new ArrayAdapter<Produtos>(getBaseContext(),
                                android.R.layout.simple_list_item_1,
                                listaP);


                        listaInstrumentosUtilizados.setAdapter(adaptadorInstrumentosUtilizados);

                        dialogoInstrumentos.dismiss();
                    }
                });

                dialogoInstrumentos.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instrumental, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class getProdutos extends
            AsyncTask<Integer, Void, ArrayList<Produtos>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(InstrumentalActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A Procurar Instrumentos...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        };

        @Override
        protected ArrayList<Produtos> doInBackground(Integer... params) {
            ArrayList<Produtos> lista = null;

            try {
                lista = WebServiceUtils.getAllProdutos(token);
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }

        protected void onPostExecute(ArrayList<Produtos> lista) {
            if (lista != null) {
                LinkedList<Produtos> listaProdutos = new LinkedList<Produtos>();
                for(int i=0; i<lista.size();i++) {
                    if(lista.get(i).getTipo().equals("I")) {
                        Produtos p = new Produtos();
                        p.setTipo(lista.get(i).getTipo());
                        p.setNome(lista.get(i).getNome());
                        p.setCodigo(lista.get(i).getCodigo());
                        p.setId(lista.get(i).getId());
                        p.setIdQuantidadeProdutosStock(lista.get(i).getIdQuantidadeProdutosStock());
                        p.setReferencia(lista.get(i).getReferencia());

                        listaProdutos.add(p);
                    }

                }

                adaptadorInstrumentos = new ArrayAdapter<Produtos>(getBaseContext(),
                        android.R.layout.simple_list_item_1, listaProdutos);
                adaptadorInstrumentos.sort(new Comparator<Produtos>() {

                    @Override
                    public int compare(Produtos lhs, Produtos rhs) {
                        return ("" + lhs.getNome().toUpperCase()).compareTo(("" + rhs.getNome()).toUpperCase());
                    }
                });

                listaInstrumentos.setAdapter(adaptadorInstrumentos );
                ringProgressDialog.dismiss();
                dialogoInstrumentos.show();


            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro Get Utente", Toast.LENGTH_SHORT).show();
                finish();

            }
        }}
}
