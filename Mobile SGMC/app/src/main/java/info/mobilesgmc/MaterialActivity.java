package info.mobilesgmc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import info.mobilesgmc.materiaisCirurgia.AdapterMateriaisCirurgia;
import info.mobilesgmc.modelo.ListasProdutosCirurgia;
import info.mobilesgmc.modelo.Produtos;
import info.mobilesgmc.modelo.ProdutosCirurgia;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;


public class MaterialActivity extends Fragment {

    private ArrayList<ProdutosCirurgia> arrayProdutos = new ArrayList<ProdutosCirurgia>();

    private String token;
    private Dialog dialogoAparelhos;
    private ListView listaAparelhos;
    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<Produtos> adaptadorAparelhos;
    public static ListView listaMateriaisUtilizados;
    private AlertDialog.Builder builder;
    private ProdutosCirurgia p;
    private NumberPicker np;
    private Produtos l;
    public static AdapterMateriaisCirurgia adaptador;


    public MaterialActivity newInstance(String text){
        MaterialActivity mFragment = new MaterialActivity();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_aparelhos, container, false);
        token = HomeActivity.getToken();

        listaMateriaisUtilizados = (ListView) rootView.findViewById(R.id.listView_AparelhosUtilizados);
        builder = new AlertDialog.Builder(getActivity());

        if(HomeActivity.getListaProdutos()==null) {
            HomeActivity.setListaProdutos(new ListasProdutosCirurgia());


            if (HomeActivity.getCirurgia().getId() != 0) {
                new getProdutosCirurgia().execute();
            }
        }
        else
        {
            adaptador = new AdapterMateriaisCirurgia(getActivity(),HomeActivity.getListaProdutos().getListaMateriais());


            adaptador.sort(new Comparator<ProdutosCirurgia>() {

                @Override
                public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                    return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                }
            });

            // adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getActivity().getBaseContext(),
            //       android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

            listaMateriaisUtilizados.setAdapter(adaptador);
        }


        setHasOptionsMenu(true);
        return rootView;
    }


    public static void atualizaProdutos()
    {
        listaMateriaisUtilizados.invalidate();
        adaptador.notifyDataSetChanged();
    }


    public void listenerGuardar(){


        new adicionarAparelhos().execute(HomeActivity.getListaProdutos());

    }


    public void listenerBotaoProcurarProduto(){
        new getProdutos().execute();
        dialogoAparelhos = new Dialog(getActivity());

        // tell the Dialog to use the dialog.xml as it's layout
        // description
        dialogoAparelhos.setContentView(R.layout.dialog_procuracirurgias);
        dialogoAparelhos.setTitle("Escolha o Material Utilizado:");
        dialogoAparelhos
                .getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final EditText nomeEditText = (EditText) dialogoAparelhos
                .findViewById(R.id.editText_escolhaCirurgia);
        nomeEditText.setHint("Nome Material ..");


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
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Escolha a quantidade: ");

                np = new NumberPicker(getActivity());
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
                        produtoC.setIdCirurgia(HomeActivity.getCirurgia().getId());
                        produtoC.setPreparado(false);
                        produtoC.setIdProduto(l.getId());
                        produtoC.setQuantidade(valor);
                        produtoC.setTipoProduto(l.getTipo());
                        produtoC.setUtilizado(true);
                        HomeActivity.getListaProdutos().getListaMateriais().add(produtoC);
                        adaptador = new AdapterMateriaisCirurgia(getActivity(),HomeActivity.getListaProdutos().getListaMateriais());

                        listaMateriaisUtilizados.setAdapter(adaptador);


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
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_aparelhos, menu);


        super.onCreateOptionsMenu(menu, inflater);

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


        return super.onOptionsItemSelected(item);
    }

    private class getProdutos extends
            AsyncTask<Integer, Void, ArrayList<Produtos>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
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
                    if(lista.get(i).getTipo().equals("M")) {
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

                if(HomeActivity.getListaProdutos().getListaMateriais().size()>0){

                    for(int i=0; i<HomeActivity.getListaProdutos().getListaMateriais().size(); i++){
                        listaProdutos = removeElementoDaLista(listaProdutos, HomeActivity.getListaProdutos().getListaMateriais().get(i));
                    }
                }





                if(listaProdutos.size()>0){
                    adaptadorAparelhos = new ArrayAdapter<Produtos>(getActivity().getBaseContext(),
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
                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Produtos", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0, true);

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

    private class adicionarAparelhos extends
            AsyncTask<ListasProdutosCirurgia, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Produtos...");

//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }

        ;

        @Override
        protected Boolean doInBackground(ListasProdutosCirurgia... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.adicionarProdutosDaCirurgia(params[0], token);
            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            Toast.makeText(getActivity().getApplicationContext(), (result ? "Produtos Adicionados com Sucesso!"
                    : "Produtos Não Adicionados!"), Toast.LENGTH_LONG)
                    .show();

            if(result)
            {
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0, true);
            }


            ringProgressDialog.dismiss();

            //  createPDF();

        }

    }
    private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ListasProdutosCirurgia> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A Procurar Produtos...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        };

        @Override
        protected ListasProdutosCirurgia doInBackground(Integer... params) {
            ListasProdutosCirurgia lista = null;

            try {
                lista = WebServiceUtils.getProdutosCirurgia(token, HomeActivity.getCirurgia().getId());
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }
            return lista;
        }

        protected void onPostExecute(ListasProdutosCirurgia lista) {
            if (lista != null) {

                HomeActivity.setListaProdutos(lista);
                if(HomeActivity.getListaProdutos().getListaMateriais().size()>0) {
                    adaptador = new AdapterMateriaisCirurgia(getActivity(),HomeActivity.getListaProdutos().getListaMateriais());


                    adaptador.sort(new Comparator<ProdutosCirurgia>() {

                        @Override
                        public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                            return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                        }
                    });

                    // adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getActivity().getBaseContext(),
                    //       android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

                    listaMateriaisUtilizados.setAdapter(adaptador);



                }

                ringProgressDialog.dismiss();



            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Produtos da Cirurgia", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);

            }
        }}




}
