package pt.mobilesgmc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import pt.mobilesgmc.modelo.Produtos;
import pt.mobilesgmc.modelo.ProdutosCirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;


public class AparelhosActivity extends ActionBarActivity {

    private ArrayList<ProdutosCirurgia> arrayProdutos = new ArrayList<ProdutosCirurgia>();

    private String token;
    private Dialog dialogoAparelhos;
    private ListView listaAparelhos;
    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<Produtos> adaptadorAparelhos;
    private ArrayAdapter<Produtos> adaptadorAparelhosUtilizados;
    private  ListView listaAparelhosUtilizados;
    private  LinkedList<Produtos> listaP = new LinkedList<Produtos>();
    private AlertDialog.Builder builder;
    private ProdutosCirurgia p;
    private int idCirurgia;
    private ArrayAdapter<ProdutosCirurgia> adaptadorProdutosCirurgia;
    private NumberPicker np;
    private Produtos l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelhos);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        idCirurgia = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("idCirurgia", "0"));

        listaAparelhosUtilizados = (ListView) findViewById(R.id.listView_AparelhosUtilizados);
        builder = new AlertDialog.Builder(AparelhosActivity.this);

        if(idCirurgia!=0) {
            new getProdutosCirurgia().execute();
        }

        listaAparelhosUtilizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProdutosCirurgia pc = (ProdutosCirurgia) listaAparelhosUtilizados.getItemAtPosition(position);
                pc.setUtilizado(!pc.getUtilizado());

            }
        });


        listaAparelhosUtilizados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                p = (ProdutosCirurgia) listaAparelhosUtilizados.getItemAtPosition(position);


                builder.setIcon(R.drawable.ic_launcher);


                builder.setTitle("Editar / Apagar ?");
                builder.setMessage(p.getNomeProduto())
                        .setCancelable(false)
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(AparelhosActivity.this);

                                alert.setTitle("Escolha a quantidade: ");

                                np = new NumberPicker(AparelhosActivity.this);
                                String[] nums = new String[100];
                                for (int i = 0; i < nums.length; i++)
                                    nums[i] = Integer.toString(i);

                                np.setMinValue(0);
                                np.setMaxValue(nums.length - 1);
                                np.setWrapSelectorWheel(false);
                                np.setDisplayedValues(nums);
                                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                             //   np.setValue(p.getQuantidade() + 1);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int valor = np.getValue() ;
                                        ((ProdutosCirurgia) adaptadorProdutosCirurgia.getItem(position)).setQuantidade(valor);
                                        adaptadorProdutosCirurgia = new ArrayAdapter<ProdutosCirurgia>(
                                                getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                                                arrayProdutos);

                                        listaAparelhosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                        listaAparelhosUtilizados.setAdapter(adaptadorProdutosCirurgia);

                                        for (int i = 0; i < arrayProdutos.size(); i++) {
                                            if (arrayProdutos.get(i).getUtilizado() == true) {
                                                listaAparelhosUtilizados.setItemChecked(i, true);
                                            }
                                        }
                                        //  p.setQuantidade(Integer.parseInt(np.getValue()));
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel.
                                    }
                                });

                                alert.setView(np);
                                alert.show();
                            }
                        })
                        .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adaptadorProdutosCirurgia.remove(p);
                                listaAparelhosUtilizados.setAdapter(adaptadorProdutosCirurgia);
                                for (int j = 0; j < arrayProdutos.size(); j++) {
                                    if (arrayProdutos.get(j).getUtilizado() == true) {
                                        listaAparelhosUtilizados.setItemChecked(j, true);
                                    }

                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            }     });



    }

    public void listenerGuardar(){


        new adicionarAparelhos().execute(arrayProdutos);

    }


    public void listenerBotaoProcurarProduto(){
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


                l = (Produtos) listaAparelhos.getItemAtPosition(arg2);

                // listaP.add(l);

                builder.setIcon(R.drawable.ic_launcher);


                builder.setTitle("Escolha a Quantidade:");
                builder.setMessage("Quantidade")
                        .setCancelable(false);
                       /* .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {*/
                AlertDialog.Builder alert = new AlertDialog.Builder(AparelhosActivity.this);

                alert.setTitle("Escolha a quantidade: ");

                np = new NumberPicker(AparelhosActivity.this);
                String[] nums = new String[100];
                for (int i = 0; i < nums.length; i++)
                    nums[i] = Integer.toString(i);

                np.setMinValue(0);
                np.setMaxValue(nums.length - 1);
                np.setWrapSelectorWheel(false);
                np.setDisplayedValues(nums);
                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int valor = (np.getValue());
                        ProdutosCirurgia produtoC = new ProdutosCirurgia();
                        produtoC.setNomeProduto(l.getNome());
                        produtoC.setIdCirurgia(idCirurgia);
                        produtoC.setPreparado(false);
                        produtoC.setIdProduto(l.getId());
                        produtoC.setQuantidade(valor);
                        produtoC.setTipoProduto(l.getTipo());
                        produtoC.setUtilizado(true);
                        arrayProdutos.add(produtoC);
                        adaptadorProdutosCirurgia = new ArrayAdapter<ProdutosCirurgia>(getBaseContext(),
                                android.R.layout.simple_list_item_multiple_choice,
                                arrayProdutos);


                        listaAparelhosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listaAparelhosUtilizados.setAdapter(adaptadorProdutosCirurgia);

                        for (int i = 0; i < arrayProdutos.size(); i++) {
                            if (arrayProdutos.get(i).getUtilizado() == true) {
                                listaAparelhosUtilizados.setItemChecked(i, true);
                            }
                        }

                        dialogoAparelhos.dismiss();
                        //  p.setQuantidade(Integer.parseInt(np.getValue()));
                    }
                });

               /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel.
                    }*/
                // });

                alert.setView(np);
                alert.show();

            }
        });


       /* AlertDialog alert = builder.create();
        alert.show();*/




        dialogoAparelhos.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

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
        if (id == R.id.action_addAparelho) {
            listenerBotaoProcurarProduto();
            return true;
        }

        if (id == R.id.action_saveAparelho) {
            listenerGuardar();
            return true;
        }
        if(id == android.R.id.home)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("Pretende Retroceder sem guardar?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
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
                ArrayList<Produtos> listaProdutos = new ArrayList<Produtos>();
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

                if(arrayProdutos.size()>0){

                    for(int i=0; i<arrayProdutos.size(); i++){
                        listaProdutos = removeElementoDaLista(listaProdutos, arrayProdutos.get(i));
                    }
                }





                if(listaProdutos.size()>0){
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
                dialogoAparelhos.show();}

                else
                    ringProgressDialog.dismiss();
            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro Get Produtos", Toast.LENGTH_SHORT).show();
                finish();

            }
        }
        public ArrayList<Produtos> removeElementoDaLista(ArrayList<Produtos> lista, ProdutosCirurgia p)
        {
            ArrayList<Produtos> listaAux = new ArrayList<Produtos>();

            for (int o = 0; o<lista.size(); o++)
            {
                if(!lista.get(o).getNome().toLowerCase().equals(p.getNomeProduto().toLowerCase()))
                    listaAux.add(lista.get(o));
            }

            return listaAux;

        }

    }

    private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ArrayList<ProdutosCirurgia>> {
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
        protected ArrayList<ProdutosCirurgia> doInBackground(Integer... params) {
            ArrayList<ProdutosCirurgia> lista = null;

            try {
                lista = WebServiceUtils.getProdutosCirurgia(token, idCirurgia);
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }

        protected void onPostExecute(ArrayList<ProdutosCirurgia> lista) {
            if (lista != null) {


                for(int i=0; i<lista.size(); i++) {
                    if (lista.get(i).getTipoProduto().equals("A")) {
                        arrayProdutos.add(lista.get(i));
                    }
                }

                if(arrayProdutos.size()>0) {
                    adaptadorProdutosCirurgia = new ArrayAdapter<ProdutosCirurgia>(getBaseContext(),
                            android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

                    adaptadorProdutosCirurgia.sort(new Comparator<ProdutosCirurgia>() {

                        @Override
                        public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                            return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                        }
                    });
                    listaAparelhosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listaAparelhosUtilizados.setAdapter(adaptadorProdutosCirurgia);


                    for (int j = 0; j < arrayProdutos.size(); j++) {
                        if (arrayProdutos.get(j).getUtilizado() == true) {
                            listaAparelhosUtilizados.setItemChecked(j, true);
                        }

                    }


                }

                ringProgressDialog.dismiss();



            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro Get Produtos da Cirurgia", Toast.LENGTH_SHORT).show();
                finish();

            }
        }}

    private class adicionarAparelhos extends
            AsyncTask<ArrayList<ProdutosCirurgia>, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(AparelhosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Aparelhos...");

//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }

        ;

        @Override
        protected Boolean doInBackground(ArrayList<ProdutosCirurgia>... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.adicionarAparelhos(params[0], idCirurgia, token);
            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String a = (result ? "Aparelhos Adicionados com Sucesso!"
                    : "Aparelhos Não Adicionados!");

            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
                    .show();

            ringProgressDialog.dismiss();


        }

    }



}
