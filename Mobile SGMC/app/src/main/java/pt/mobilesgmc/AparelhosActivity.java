package pt.mobilesgmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import pt.mobilesgmc.modelo.ListaProdutosComProdutos;
import pt.mobilesgmc.modelo.Produtos;
import pt.mobilesgmc.modelo.ProdutosCirurgiaComProdutos;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;


public class AparelhosActivity extends Activity {

    private Button btnEscolheAparelhos;
    private String token;
    private Dialog dialogoAparelhos;
    private ListView listaAparelhos;
    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<Produtos> adaptadorAparelhos;
    private ArrayAdapter<Produtos> adaptadorAparelhosUtilizados;
    private  ListView listaAparelhosUtilizados;
    private  LinkedList<Produtos> listaP = new LinkedList<Produtos>();
    private AlertDialog.Builder builder;
    private Produtos p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelhos);

        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        btnEscolheAparelhos = (Button) findViewById(R.id.btnEscolhaAparelhos);
        listaAparelhosUtilizados = (ListView) findViewById(R.id.listView_AparelhosUtilizados);
        builder = new AlertDialog.Builder(AparelhosActivity.this);
        listaAparelhosUtilizados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                p= (Produtos) listaAparelhosUtilizados.getItemAtPosition(position);

                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("Pretende Apagar?");
                builder.setMessage(p.getNome())
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adaptadorAparelhosUtilizados.remove(p);
                                listaAparelhosUtilizados.setAdapter(adaptadorAparelhosUtilizados);
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
        btnEscolheAparelhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getProdutos().execute();
                dialogoAparelhos = new Dialog(AparelhosActivity.this);

                // tell the Dialog to use the dialog.xml as it's layout
                // description
                dialogoAparelhos.setContentView(R.layout.dialog_procuracirurgias);
                dialogoAparelhos.setTitle("Escolha o Aparelho Utilizado:");
                dialogoAparelhos
                        .getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText nomeEditText = (EditText) dialogoAparelhos
                        .findViewById(R.id.editText_escolhaCirurgia);
                nomeEditText.setHint("Nome Aparelho ..");


                nomeEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub
                        adaptadorAparelhos.getFilter().filter(s);
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
                listaAparelhos = (ListView) dialogoAparelhos
                        .findViewById(R.id.listView_cirurgias);

                listaAparelhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {



                        Produtos l = (Produtos) listaAparelhos.getItemAtPosition(arg2);

                        listaP.add(l);

                        adaptadorAparelhosUtilizados = new ArrayAdapter<Produtos>(getBaseContext(),
                                android.R.layout.simple_list_item_1,
                                listaP);

                        listaAparelhosUtilizados.setAdapter(adaptadorAparelhosUtilizados);

                        dialogoAparelhos.dismiss();
                    }
                });

                dialogoAparelhos.setOnDismissListener(new DialogInterface.OnDismissListener() {

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
        getMenuInflater().inflate(R.menu.menu_aparelhos, menu);
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

            ringProgressDialog = new ProgressDialog(AparelhosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A Procurar Produtos...");

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
                  if(lista.get(i).getTipo().equals("A")) {
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

                adaptadorAparelhos = new ArrayAdapter<Produtos>(getBaseContext(),
                        android.R.layout.simple_list_item_1, listaProdutos);
                adaptadorAparelhos.sort(new Comparator<Produtos>() {

                    @Override
                    public int compare(Produtos lhs, Produtos rhs) {
                        return ("" + lhs.getNome().toUpperCase()).compareTo(("" + rhs.getNome()).toUpperCase());
                    }
                });

                listaAparelhos.setAdapter(adaptadorAparelhos );
                ringProgressDialog.dismiss();
                dialogoAparelhos.show();


            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro Get Utente", Toast.LENGTH_SHORT).show();
                finish();

            }
        }}
}
