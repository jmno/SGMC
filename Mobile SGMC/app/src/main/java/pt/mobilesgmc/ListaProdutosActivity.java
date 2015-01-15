package pt.mobilesgmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.EquipaComJuncao;
import pt.mobilesgmc.modelo.ListaProdutosComJuncao;
import pt.mobilesgmc.modelo.ListaProdutosComProdutos;
import pt.mobilesgmc.modelo.ListaProdutosComProdutosCirurgia;
import pt.mobilesgmc.modelo.Produtos;
import pt.mobilesgmc.modelo.ProdutosCirurgia;
import pt.mobilesgmc.modelo.ProdutosCirurgiaComProdutos;
import pt.mobilesgmc.modelo.ProdutosDaLista;
import pt.mobilesgmc.modelo.ProfissonalSaude;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.Utente;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class ListaProdutosActivity extends Activity {


    private ProgressDialog ringProgressDialog;
    private String token;
    private ArrayAdapter<ListaProdutosComProdutos> adaptadorLista;
    private ListView listasProdutos;
    private Dialog dialogoListas;
    private Button btnPesquisar;
    private int idCirurgia;
    private ListView listaProdutosFinal;
    private ArrayAdapter<ProdutosCirurgiaComProdutos> adaptadorProdutos;
    private TextView textNomeEquipa;
    private TextView textTipoCirurgia;
    private TextView textEspecialidade;
    private Button btnGuardar;
    private Boolean adicionou = false;
    private  ArrayAdapter<ProdutosDaLista> adpatorProdutosFinal = null;
    private ArrayAdapter<ProdutosCirurgiaComProdutos> adaptadorProdutosExistentes;
    private Button btnProcurarProdutos;
    private Dialog dialogoProdutos;
    private ListView listaProdutosExistentes;
    private ProdutosCirurgiaComProdutos p;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_produtos);
        listasProdutos = (ListView) findViewById(R.id.listView_cirurgias);
        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        idCirurgia = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("idCirurgia", "0"));
        btnGuardar = (Button) findViewById(R.id.btnGuardarLista);
        textNomeEquipa = (TextView) findViewById(R.id.textview_NomeListaFinal);
        textTipoCirurgia = (TextView) findViewById(R.id.textview_TipoCirurgiaFinal);
        textEspecialidade = (TextView) findViewById(R.id.textview_EspecialidadeFinal);
        listaProdutosFinal = (ListView) findViewById(R.id.listView_Produtos_Cirurgia);
        btnPesquisar = (Button) findViewById(R.id.btnEscolhaLista);
        btnProcurarProdutos = (Button) findViewById(R.id.btnProcurarProdutos);

        btnProcurarProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getProdutos().execute();

                dialogoProdutos = new Dialog(ListaProdutosActivity.this);

                // tell the Dialog to use the dialog.xml as it's layout
                // description
                dialogoProdutos.setContentView(R.layout.dialog_procuracirurgias);
                dialogoProdutos.setTitle("Escolha o Produto:");
                dialogoProdutos
                        .getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText nomeEditText = (EditText) dialogoProdutos
                        .findViewById(R.id.editText_escolhaCirurgia);
                nomeEditText.setHint("Nome Produto ..");


                nomeEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub
                        adaptadorProdutosExistentes.getFilter().filter(s);
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
                listaProdutosExistentes = (ListView) dialogoProdutos
                        .findViewById(R.id.listView_cirurgias);

                listaProdutosExistentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {

                        ProdutosCirurgiaComProdutos p;
                        p= (ProdutosCirurgiaComProdutos) listaProdutosExistentes.getItemAtPosition(arg2);
                        adaptadorProdutosExistentes.add(p);
                        listaProdutosFinal.setAdapter(adaptadorProdutosExistentes);
                        dialogoProdutos.dismiss();
                    }
                });

                dialogoProdutos.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                    }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Toast.makeText(getApplicationContext(),toString(),Toast.LENGTH_SHORT).show();



            }
        });


        builder = new AlertDialog.Builder(ListaProdutosActivity.this);
        listaProdutosFinal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /*ProdutosDaLista p;
                p = (ProdutosDaLista)listaProdutosFinal.getItemAtPosition(position);
                adpatorProdutosFinal.remove(p);
                listaProdutosFinal.setAdapter(adpatorProdutosFinal);*/

                p= (ProdutosCirurgiaComProdutos) listaProdutosFinal.getItemAtPosition(position);

                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("Pretende Apagar?");
                builder.setMessage(p.getNomeProduto())
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adaptadorProdutos.remove(p);
                                listaProdutosFinal.setAdapter(adaptadorProdutos);
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
        } );


        //if(idCirurgia!=0){
        try {
            new getProdutosCirurgia().execute();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

       // }

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getListas().execute();


                dialogoListas = new Dialog(ListaProdutosActivity.this);

                // tell the Dialog to use the dialog.xml as it's layout
                // description
                dialogoListas.setContentView(R.layout.dialog_procuracirurgias);
                dialogoListas.setTitle("Escolha a Lista:");
                dialogoListas
                        .getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText nomeEditText = (EditText) dialogoListas
                        .findViewById(R.id.editText_escolhaCirurgia);
                nomeEditText.setHint("Nome Lista ..");

                nomeEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub
                        adaptadorLista.getFilter().filter(s);
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
                listasProdutos = (ListView) dialogoListas
                        .findViewById(R.id.listView_cirurgias);

                listasProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {

                        ListaProdutosComProdutos l = (ListaProdutosComProdutos) listasProdutos.getItemAtPosition(arg2);


                        textNomeEquipa.setText(l.getLista().getNome().toString());


                        textTipoCirurgia.setText(l.getLista().getTipoCirurgia().toString());


                        textEspecialidade.setText(l.getLista().getEspecialidade().toString());


                        LinkedList<ProdutosCirurgiaComProdutos> listaP = new LinkedList<ProdutosCirurgiaComProdutos>();
                           for (int i=0; i<l.getProdutos().getProduto().size();i++){
                               ProdutosCirurgiaComProdutos p = new ProdutosCirurgiaComProdutos();
                               p.setQuantidade(l.getProdutos().getProduto().get(i).getQuantidade());
                               p.setNomeProduto(l.getProdutos().getProduto().get(i).getProduto().getNome());
                               p.setPreparado(false);
                               listaP.add(p);
                           }

                        adaptadorProdutos = new ArrayAdapter<ProdutosCirurgiaComProdutos>(getBaseContext(),
                                android.R.layout.simple_list_item_multiple_choice,
                                listaP);
                        listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listaProdutosFinal.setAdapter(adaptadorProdutos);
                       /* adpatorProdutosFinal = new ArrayAdapter<ProdutosDaLista>(
                                getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                                ( l.getProdutos().getProduto()));
                        listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listaProdutosFinal.setAdapter(adpatorProdutosFinal);
*/
                        dialogoListas.dismiss();
                    }
                });

                dialogoListas.setOnDismissListener(new DialogInterface.OnDismissListener() {

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
        getMenuInflater().inflate(R.menu.menu_lista_produtos, menu);
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

    private class getListas extends
            AsyncTask<String, Void, ArrayList<ListaProdutosComProdutos>> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A verificar Equipas...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };
        @Override
        protected ArrayList<ListaProdutosComProdutos> doInBackground(String... params) {
            ArrayList<ListaProdutosComProdutos> lista = null;

            try {
                lista = WebServiceUtils.getAllListas(token);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<ListaProdutosComProdutos> lista) {
            if (lista != null) {
                adaptadorLista = new ArrayAdapter<ListaProdutosComProdutos>(
                        getBaseContext(), android.R.layout.simple_list_item_1,
                        lista);

                adaptadorLista.sort(new Comparator<ListaProdutosComProdutos>() {

                    @Override
                    public int compare(ListaProdutosComProdutos lhs, ListaProdutosComProdutos rhs) {
                        return ("" + lhs.getLista().getNome().toUpperCase()).compareTo(("" + rhs.getLista().getNome()).toUpperCase());
                    }
                });

                listasProdutos.setAdapter(adaptadorLista);
                ringProgressDialog.dismiss();
                dialogoListas.show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erro Get Listas - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }

        }
    }

   /* private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ArrayList<ProdutosCirurgiaComProdutos>> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A verificar Produtos...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };
        @Override
        protected ArrayList<ProdutosCirurgiaComProdutos> doInBackground(Integer... params) {
            ArrayList<ProdutosCirurgiaComProdutos> lista = null;

            try {
                lista = WebServiceUtils.getProdutosByIdCirurgia(token,1);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<ProdutosCirurgiaComProdutos> lista) {
            if (lista != null) {
                adaptadorProdutos= new ArrayAdapter<ProdutosCirurgiaComProdutos>(
                        getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                        lista);
                listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaProdutosFinal.setAdapter(adaptadorProdutos);
               for(int i=0; i<lista.size();i++){
                   if(lista.get(i).getPreparado()==true)
                   {
                       listaProdutosFinal.setItemChecked(i, true);
                   }
               }
                ringProgressDialog.dismiss();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erro Get Produtos - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }

        }
    }*/

    private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ListaProdutosComProdutosCirurgia> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A verificar Produtos...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };
        @Override
        protected ListaProdutosComProdutosCirurgia doInBackground(Integer... params) {
            ListaProdutosComProdutosCirurgia lista = null;

            try {
                lista = WebServiceUtils.getProdutosByIdCirurgia(token,2);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ListaProdutosComProdutosCirurgia lista) {
            if (lista != null) {
                if(lista.getListaProdutosCirurgia()!=null){
                //for(int i=0; i<lista.size();i++){
                    /*O que fiz*/
                   /* LinkedList<ProdutosDaLista> l = new LinkedList<ProdutosDaLista>();
                    for(int i =0; i<lista.getListaProdutosCirurgia().size();i++){
                        ProdutosDaLista prod = new ProdutosDaLista();
                        Produtos p = new Produtos();
                        p.setNome(lista.getListaProdutosCirurgia().get(i).getNomeProduto());

                        prod.setProduto(p);
                        prod.setQuantidade(lista.getListaProdutosCirurgia().get(i).getQuantidade());
                        l.add(prod);
                    }

                    adpatorProdutosFinal= new ArrayAdapter<ProdutosDaLista>(getBaseContext(),
                            android.R.layout.simple_list_item_multiple_choice,l);*/

                    adaptadorProdutos= new ArrayAdapter<ProdutosCirurgiaComProdutos>(
                            getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                            lista.getListaProdutosCirurgia());


                textEspecialidade.setText(lista.getListaPredefinida().getEspecialidade());
                textNomeEquipa.setText(lista.getListaPredefinida().getNome());
                textTipoCirurgia.setText(lista.getListaPredefinida().getTipoCirurgia());



                listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listaProdutosFinal.setAdapter(adaptadorProdutos);

                for (int i=0; i<lista.getListaProdutosCirurgia().size(); i++){
                    if(lista.getListaProdutosCirurgia().get(i).getPreparado()==true){
                        listaProdutosFinal.setItemChecked(i, true);
                    }
                }}
                ringProgressDialog.dismiss();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erro Get Produtos - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class adicionarProdutosCirurgia extends
            AsyncTask<ProdutosCirurgia, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Produtos...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        };

        @Override
        protected Boolean doInBackground(ProdutosCirurgia... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.adicionarProdutosDaCirurgia(params[0],token);
            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String a = (result ? "Produtos Adicionados com Sucesso!"
                    : "Produtos Não Adicionados!");

            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
                    .show();
            adicionou = true;
            ringProgressDialog.dismiss();
        }

    }

    private class getProdutos extends
            AsyncTask<Integer, Void, ArrayList<Produtos>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
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
                LinkedList<ProdutosCirurgiaComProdutos> l = new LinkedList<ProdutosCirurgiaComProdutos>();
                for(int i=0; i<lista.size();i++){
                    ProdutosCirurgiaComProdutos p = new ProdutosCirurgiaComProdutos();
                    p.setNomeProduto(lista.get(i).getNome());
                    p.setQuantidade(lista.get(i).getIdQuantidadeProdutosStock());
                    p.setPreparado(false);
                    l.add(p);
                }
                //adaptadorProdutosExistentes = new ArrayAdapter<Produtos>(getBaseContext(),
                 //       android.R.layout.simple_list_item_1, lista);

                adaptadorProdutosExistentes = new ArrayAdapter<ProdutosCirurgiaComProdutos>(getBaseContext(),
                       android.R.layout.simple_list_item_1, l);
                adaptadorProdutosExistentes.sort(new Comparator<ProdutosCirurgiaComProdutos>() {

                    @Override
                    public int compare(ProdutosCirurgiaComProdutos lhs, ProdutosCirurgiaComProdutos rhs) {
                        return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                    }
                });
                
                listaProdutosExistentes.setAdapter(adaptadorProdutosExistentes);
                ringProgressDialog.dismiss();
                dialogoProdutos.show();


            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Erro Get Utente",Toast.LENGTH_SHORT).show();
                finish();

            }
        }


    }}
