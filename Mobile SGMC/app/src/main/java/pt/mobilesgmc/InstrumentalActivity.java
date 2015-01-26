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
import android.widget.NumberPicker;
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
import pt.mobilesgmc.modelo.ProdutosCirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class InstrumentalActivity extends Activity {


    private String token;
    private int idCirurgia;
    private ProgressDialog ringProgressDialog;
    private ArrayList<ProdutosCirurgia> arrayProdutos = new ArrayList<ProdutosCirurgia>();
    private ArrayAdapter<ProdutosCirurgia> adaptadorProdutosCirurgia;
    private ListView listaInstrumentosUtilizados;
    private ArrayAdapter<Produtos> adaptadorInstrumentos;
    private  ListView listaInstrumentos;
    private  Dialog dialogoInstrumentos;
    private AlertDialog.Builder builder;
    private ProdutosCirurgia p;
    private NumberPicker np;
    private Produtos l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrumental);

        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        idCirurgia = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("idCirurgia", "0"));


        listaInstrumentosUtilizados = (ListView) findViewById(R.id.listView_InstrumentosUtilizados);
        builder = new AlertDialog.Builder(InstrumentalActivity.this);

        if(idCirurgia!=0){
            new getProdutosCirurgia().execute();
        }

        listaInstrumentosUtilizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProdutosCirurgia pc = (ProdutosCirurgia) listaInstrumentosUtilizados.getItemAtPosition(position);
                pc.setUtilizado(!pc.getUtilizado());
            }
        });

        listaInstrumentosUtilizados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                p = (ProdutosCirurgia) listaInstrumentosUtilizados.getItemAtPosition(position);


                builder.setIcon(R.drawable.ic_launcher);


                builder.setTitle("Editar / Apagar ?");
                builder.setMessage(p.getNomeProduto())
                        .setCancelable(false)
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(InstrumentalActivity.this);

                                alert.setTitle("Escolha a quantidade: ");

                                np = new NumberPicker(InstrumentalActivity.this);
                                String[] nums = new String[100];
                                for (int i = 0; i < nums.length; i++)
                                    nums[i] = Integer.toString(i);

                                np.setMinValue(0);
                                np.setMaxValue(nums.length - 1);
                                np.setWrapSelectorWheel(false);
                                np.setDisplayedValues(nums);
                                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                              //  np.setValue(p.getQuantidade() + 1);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int valor = np.getValue();
                                        ((ProdutosCirurgia) adaptadorProdutosCirurgia.getItem(position)).setQuantidade(valor);
                                        adaptadorProdutosCirurgia = new ArrayAdapter<ProdutosCirurgia>(
                                                getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                                                arrayProdutos);

                                        listaInstrumentosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                        listaInstrumentosUtilizados.setAdapter(adaptadorProdutosCirurgia);

                                        for (int i = 0; i < arrayProdutos.size(); i++) {
                                            if (arrayProdutos.get(i).getUtilizado() == true) {
                                                listaInstrumentosUtilizados.setItemChecked(i, true);
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
                                listaInstrumentosUtilizados.setAdapter(adaptadorProdutosCirurgia);
                                for (int j = 0; j < arrayProdutos.size(); j++) {
                                    if (arrayProdutos.get(j).getUtilizado() == true) {
                                        listaInstrumentosUtilizados.setItemChecked(j, true);
                                    }

                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            }     });
    }


    public void listenerProcurarProduto()
    {
        new getProdutos().execute();
        dialogoInstrumentos = new Dialog(InstrumentalActivity.this);

        // tell the Dialog to use the dialog.xml as it's layout
        // description
        dialogoInstrumentos.setContentView(R.layout.dialog_procuracirurgias);
        dialogoInstrumentos.setTitle("Escolha o Instrumento Utilizado:");
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


                l = (Produtos) listaInstrumentos.getItemAtPosition(arg2);

                // listaP.add(l);

                builder.setIcon(R.drawable.ic_launcher);


                builder.setTitle("Escolha a Quantidade:");
                builder.setMessage("Quantidade")
                        .setCancelable(false);
                       /* .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {*/
                AlertDialog.Builder alert = new AlertDialog.Builder(InstrumentalActivity.this);

                alert.setTitle("Escolha a quantidade: ");

                np = new NumberPicker(InstrumentalActivity.this);
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


                        listaInstrumentosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listaInstrumentosUtilizados.setAdapter(adaptadorProdutosCirurgia);

                        for (int i = 0; i < arrayProdutos.size(); i++) {
                            if (arrayProdutos.get(i).getUtilizado() == true) {
                                listaInstrumentosUtilizados.setItemChecked(i, true);
                            }
                        }

                        dialogoInstrumentos.dismiss();
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




        dialogoInstrumentos.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });


    }


    public void listenerGuardarInstrumentos(){


        new adicionarInstrumentos().execute(arrayProdutos);

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
        if (id == R.id.action_addInstrumento) {
            listenerProcurarProduto();
            return true;
        }

        if (id == R.id.action_saveInstrumento) {
            listenerGuardarInstrumentos();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ArrayList<ProdutosCirurgia>> {
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
                    if (lista.get(i).getTipoProduto().equals("I")) {
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
                    listaInstrumentosUtilizados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listaInstrumentosUtilizados.setAdapter(adaptadorProdutosCirurgia);


                    for (int j = 0; j < arrayProdutos.size(); j++) {
                        if (arrayProdutos.get(j).getUtilizado() == true) {
                            listaInstrumentosUtilizados.setItemChecked(j, true);
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
                ArrayList<Produtos> listaProdutos = new ArrayList<Produtos>();
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

                if(arrayProdutos.size()>0){

                    for(int i=0; i<arrayProdutos.size(); i++){
                        listaProdutos = removeElementoDaLista(listaProdutos, arrayProdutos.get(i));
                    }
                }

                if(listaProdutos.size()>0){
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
                dialogoInstrumentos.show();}
                else
                    ringProgressDialog.dismiss();


            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro Get Utente", Toast.LENGTH_SHORT).show();
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






    private class adicionarInstrumentos extends
            AsyncTask<ArrayList<ProdutosCirurgia>, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(InstrumentalActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Instrumentos...");

//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }

        ;

        @Override
        protected Boolean doInBackground(ArrayList<ProdutosCirurgia>... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.adicionarInstrumentos(params[0],idCirurgia, token);
            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String a = (result ? "Instrumentos Adicionados com Sucesso!"
                    : "Instrumentos Não Adicionados!");

            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
                    .show();

            ringProgressDialog.dismiss();


        }

    }



}
