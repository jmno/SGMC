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

import info.mobilesgmc.modelo.ListaProdutosComProdutos;
import info.mobilesgmc.modelo.ListasProdutosCirurgia;
import info.mobilesgmc.modelo.Produtos;
import info.mobilesgmc.modelo.ProdutosCirurgia;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.mobilesgmc.produtosDaCirurgia.AdapterProdutosCirurgia;
import info.nicolau.mobilegsmc.R;

public class ListaProdutosActivity extends Fragment {


    private String token;
    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<ListaProdutosComProdutos> adaptadorLista;
    private ListView listas;
    private Dialog dialogoListas;
    private ArrayAdapter<ProdutosCirurgia> adaptadorProdutosFinal;
    public static ListView listaProdutosFinal;
    private ProdutosCirurgia p;
    private AlertDialog.Builder builder;
    private NumberPicker np;
    private ArrayAdapter<Produtos> adaptadorProduto;
    private ListView listaApresentaProduto;
    private Dialog dialogoProdutos;
    public static AdapterProdutosCirurgia adaptadorProdutos;
    private Produtos l;
    private boolean adicionou;

    public ListaProdutosActivity newInstance(String text){
        ListaProdutosActivity mFragment = new ListaProdutosActivity();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_lista_produtos, container, false);
        token = HomeActivity.getToken();



        builder = new AlertDialog.Builder(getActivity());
        listaProdutosFinal = (ListView) rootView.findViewById(R.id.listView_Produtos_Cirurgia);

        if(HomeActivity.getListaProdutos()==null) {
            HomeActivity.setListaProdutos(new ListasProdutosCirurgia());


            if (HomeActivity.getCirurgia().getId() != 0) {
                new getProdutosCirurgia().execute();
            }
        }
        else
        {
            adaptadorProdutos = new AdapterProdutosCirurgia(getActivity(),HomeActivity.getListaProdutos().getListas());


            adaptadorProdutos.sort(new Comparator<ProdutosCirurgia>() {

                @Override
                public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                    return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                }
            });

            // adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getActivity().getBaseContext(),
            //       android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

            listaProdutosFinal.setAdapter(adaptadorProdutos);
        }

        setHasOptionsMenu(true);
        return  rootView;
    }

    public static void atualizaProdutos()
    {
        listaProdutosFinal.invalidate();
        adaptadorProdutos.notifyDataSetChanged();
    }


    public void listenerBotaoEscolhaListaMateriais() {
        new getListas().execute();


        dialogoListas = new Dialog(getActivity());

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
        listas = (ListView) dialogoListas
                .findViewById(R.id.listView_cirurgias);

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {

                /** ver isto   */
                HomeActivity.setListaProdutos(new ListasProdutosCirurgia());

                ListaProdutosComProdutos l = (ListaProdutosComProdutos) listas.getItemAtPosition(arg2);

                ProdutosCirurgia produto ;


                for(int i=0; i<l.getProdutos().getProduto().size();i++){
                    produto = new ProdutosCirurgia();

                    produto.setIdCirurgia(HomeActivity.getCirurgia().getId());
                    produto.setId(0);
                    produto.setNomeProduto(l.getProdutos().getProduto().get(i).getProduto().getNome());
                    produto.setTipoProduto(l.getProdutos().getProduto().get(i).getProduto().getTipo());
                    produto.setUtilizado(false);
                    produto.setIdProduto(l.getProdutos().getProduto().get(i).getProduto().getId());
                    produto.setPreparado(false);
                    produto.setQuantidade(l.getProdutos().getProduto().get(i).getQuantidade());

                    if(HomeActivity.getListaProdutos().getListaAparelhos()==null)
                        HomeActivity.getListaProdutos().setListaAparelhos(new ArrayList<ProdutosCirurgia>());


                    if(HomeActivity.getListaProdutos().getListaInstrumentos()==null)
                        HomeActivity.getListaProdutos().setListaInstrumentos(new ArrayList<ProdutosCirurgia>());

                    if(HomeActivity.getListaProdutos().getListaMateriais()==null)
                        HomeActivity.getListaProdutos().setListaMateriais(new ArrayList<ProdutosCirurgia>());

                    if(produto.getTipoProduto().toLowerCase().equals("a"))
                    HomeActivity.getListaProdutos().getListaAparelhos().add(produto);

                    if(produto.getTipoProduto().toLowerCase().equals("i"))
                        HomeActivity.getListaProdutos().getListaInstrumentos().add(produto);

                    if(produto.getTipoProduto().toLowerCase().equals("m"))
                        HomeActivity.getListaProdutos().getListaMateriais().add(produto);

                }

                adaptadorProdutos = new AdapterProdutosCirurgia(getActivity(),HomeActivity.getListaProdutos().getListas());


                adaptadorProdutos.sort(new Comparator<ProdutosCirurgia>() {

                    @Override
                    public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                        return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                    }
                });

                // adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getActivity().getBaseContext(),
                //       android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

                listaProdutosFinal.setAdapter(adaptadorProdutos);
                dialogoListas.dismiss();
            }
        });

        dialogoListas.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public void listenerBotaoAdicionarProduto(){

        new getProdutos().execute();
        dialogoProdutos= new Dialog(getActivity());

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
                adaptadorProduto.getFilter().filter(s);
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
        listaApresentaProduto = (ListView) dialogoProdutos
                .findViewById(R.id.listView_cirurgias);

        listaApresentaProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {


                l = (Produtos) listaApresentaProduto.getItemAtPosition(arg2);

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
                        if(produtoC.getTipoProduto().toLowerCase().equals("a"))
                            HomeActivity.getListaProdutos().getListaAparelhos().add(produtoC);

                        if(produtoC.getTipoProduto().toLowerCase().equals("i"))
                            HomeActivity.getListaProdutos().getListaInstrumentos().add(produtoC);

                        if(produtoC.getTipoProduto().toLowerCase().equals("m"))
                            HomeActivity.getListaProdutos().getListaAparelhos().add(produtoC);

                        adaptadorProdutos = new AdapterProdutosCirurgia(getActivity(),HomeActivity.getListaProdutos().getListas());
                        listaProdutosFinal.setAdapter(adaptadorProdutos);
                        //VIR AQUI VER SE É PRECISO ATUALIZAR O ADAPTADOR;
                        dialogoProdutos.dismiss();
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




        dialogoProdutos.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

    }

    public void listenerBotaoGuardarLista(){


        new adicionarProdutosCirurgia().execute(HomeActivity.getListaProdutos());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_lista_produtos, menu);


        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_searchLista) {
            listenerBotaoEscolhaListaMateriais();
            return true;
        }

        if (id == R.id.action_addProduto) {
            listenerBotaoAdicionarProduto();
            return true;
        }

        if (id == R.id.action_saveLista) {
            listenerBotaoGuardarLista();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class getListas extends
            AsyncTask<String, Void, ArrayList<ListaProdutosComProdutos>> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle(getActivity().getString(R.string.aguarde));
            ringProgressDialog.setMessage("A verificar Listas...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }

        ;

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
                        getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
                        lista);

                adaptadorLista.sort(new Comparator<ListaProdutosComProdutos>() {

                    @Override
                    public int compare(ListaProdutosComProdutos lhs, ListaProdutosComProdutos rhs) {
                        return ("" + lhs.getLista().getNome().toUpperCase()).compareTo(("" + rhs.getLista().getNome()).toUpperCase());
                    }
                });

                listas.setAdapter(adaptadorLista);
                ringProgressDialog.dismiss();
                dialogoListas.show();
            } else {
                ringProgressDialog.dismiss();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Listas - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }

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
                if(HomeActivity.getListaProdutos().getSize()>0) {
                    adaptadorProdutos = new AdapterProdutosCirurgia(getActivity(),HomeActivity.getListaProdutos().getListas());


                    adaptadorProdutos.sort(new Comparator<ProdutosCirurgia>() {

                        @Override
                        public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                            return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                        }
                    });

                   // adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getActivity().getBaseContext(),
                     //       android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

                    listaProdutosFinal.setAdapter(adaptadorProdutos);



                }

                ringProgressDialog.dismiss();



            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Produtos da Cirurgia", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);

            }
        }}



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
                        Produtos p = new Produtos();
                        p.setTipo(lista.get(i).getTipo());
                        p.setNome(lista.get(i).getNome());
                        p.setCodigo(lista.get(i).getCodigo());
                        p.setId(lista.get(i).getId());
                        p.setIdQuantidadeProdutosStock(lista.get(i).getIdQuantidadeProdutosStock());
                        p.setReferencia(lista.get(i).getReferencia());

                        listaProdutos.add(p);


                }

                if(HomeActivity.getListaProdutos().getSize()>0){

                    for(int i=0; i<HomeActivity.getListaProdutos().getListas().size(); i++){
                        listaProdutos = removeElementoDaLista(listaProdutos, HomeActivity.getListaProdutos().getListas().get(i));
                    }
                }





                if(listaProdutos.size()>0){
                    adaptadorProduto = new ArrayAdapter<Produtos>(getActivity().getBaseContext(),
                            android.R.layout.simple_list_item_1, listaProdutos);
                    adaptadorProduto.sort(new Comparator<Produtos>() {

                        @Override
                        public int compare(Produtos lhs, Produtos rhs) {
                            return ("" + lhs.getNome().toUpperCase()).compareTo(("" + rhs.getNome()).toUpperCase());
                        }
                    });

                    listaApresentaProduto.setAdapter(adaptadorProduto );
                    ringProgressDialog.dismiss();
                    dialogoProdutos.show();}

                else
                    ringProgressDialog.dismiss();
            } else {
                ringProgressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Erro Get Produtos", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);

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


    private class adicionarProdutosCirurgia extends
            AsyncTask<ListasProdutosCirurgia, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle(getActivity().getString(R.string.aguarde));
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
            adicionou = true;
            ringProgressDialog.dismiss();

          //  createPDF();

        }

    }



}
