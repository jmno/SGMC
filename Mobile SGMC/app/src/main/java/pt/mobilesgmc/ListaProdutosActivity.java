package pt.mobilesgmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import pt.mobilesgmc.modelo.ListaProdutosComProdutos;
import pt.mobilesgmc.modelo.Produtos;
import pt.mobilesgmc.modelo.ProdutosCirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class ListaProdutosActivity extends Activity {


    private String token;
    private int idCirurgia;
    private ProgressDialog ringProgressDialog;
    private ArrayAdapter<ListaProdutosComProdutos> adaptadorLista;
    private ListView listas;
    private Dialog dialogoListas;
    private ArrayList<ProdutosCirurgia> arrayProdutos;
    private ArrayAdapter<ProdutosCirurgia> adaptadorProdutosFinal;
    private ListView listaProdutosFinal;
    private ProdutosCirurgia p;
    private AlertDialog.Builder builder;
    private NumberPicker np;
    private ArrayAdapter<Produtos> adaptadorProduto;
    private ListView listaApresentaProduto;
    private Dialog dialogoProdutos;
    private Produtos l;
    private boolean adicionou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_produtos);

        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        idCirurgia = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("idCirurgia", "0"));
        builder = new AlertDialog.Builder(ListaProdutosActivity.this);
        listaProdutosFinal = (ListView) findViewById(R.id.listView_Produtos_Cirurgia);

        arrayProdutos= new ArrayList<ProdutosCirurgia>();

        if(idCirurgia!=0){
            new getProdutosCirurgia().execute();
        }

        listaProdutosFinal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p = (ProdutosCirurgia) listaProdutosFinal.getItemAtPosition(position);
                p.setPreparado(!p.getPreparado());
            }
        });

        listaProdutosFinal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                p = (ProdutosCirurgia) listaProdutosFinal.getItemAtPosition(position);

                builder.setIcon(R.drawable.ic_launcher);


                builder.setTitle("Editar / Apagar ?");
                builder.setMessage(p.getNomeProduto())
                        .setCancelable(false)
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(ListaProdutosActivity.this);

                                alert.setTitle("Escolha a quantidade: ");

                                np = new NumberPicker(ListaProdutosActivity.this);
                                String[] nums = new String[100];
                                for (int i = 0; i < nums.length; i++)
                                    nums[i] = Integer.toString(i);

                                np.setMinValue(1);
                                np.setMaxValue(nums.length-1);
                                np.setWrapSelectorWheel(false);
                                np.setDisplayedValues(nums);
                                np.setValue(p.getQuantidade() + 1);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int valor = np.getValue() - 1;
                                        ((ProdutosCirurgia) adaptadorProdutosFinal.getItem(position)).setQuantidade(valor);

                                        //  p.setQuantidade(Integer.parseInt(np.getValue()));

                                        adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(
                                                getBaseContext(), android.R.layout.simple_list_item_multiple_choice,
                                                arrayProdutos);

                                        listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                        listaProdutosFinal.setAdapter(adaptadorProdutosFinal);

                                        for (int i = 0; i < arrayProdutos.size(); i++) {
                                            if (arrayProdutos.get(i).getPreparado() == true) {
                                                listaProdutosFinal.setItemChecked(i, true);
                                            }
                                        }
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
                                adaptadorProdutosFinal.remove(p);
                                listaProdutosFinal.setAdapter(adaptadorProdutosFinal);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });



    }



  

    public void listenerBotaoEscolhaListaMateriais() {
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
        listas = (ListView) dialogoListas
                .findViewById(R.id.listView_cirurgias);

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {

                /** ver isto   */
                arrayProdutos.clear();

                ListaProdutosComProdutos l = (ListaProdutosComProdutos) listas.getItemAtPosition(arg2);

                ProdutosCirurgia produto ;


                for(int i=0; i<l.getProdutos().getProduto().size();i++){
                    produto = new ProdutosCirurgia();

                    produto.setIdCirurgia(idCirurgia);
                    produto.setId(0);
                    produto.setNomeProduto(l.getProdutos().getProduto().get(i).getProduto().getNome());
                    produto.setTipoProduto(l.getProdutos().getProduto().get(i).getProduto().getTipo());
                    produto.setUtilizado(false);
                    produto.setIdProduto(l.getProdutos().getProduto().get(i).getProduto().getId());
                    produto.setPreparado(false);
                    produto.setQuantidade(l.getProdutos().getProduto().get(i).getQuantidade());



                    arrayProdutos.add(produto);
                }


                adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getBaseContext(),
                        android.R.layout.simple_list_item_multiple_choice,
                        arrayProdutos);
                listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaProdutosFinal.setAdapter(adaptadorProdutosFinal);
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

    public void listenerBotaoAdicionarProduto(){

        new getProdutos().execute();
        dialogoProdutos= new Dialog(ListaProdutosActivity.this);

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
                AlertDialog.Builder alert = new AlertDialog.Builder(ListaProdutosActivity.this);

                alert.setTitle("Escolha a quantidade: ");

                np = new NumberPicker(ListaProdutosActivity.this);
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
                        adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getBaseContext(),
                                android.R.layout.simple_list_item_multiple_choice,
                                arrayProdutos);


                        listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listaProdutosFinal.setAdapter(adaptadorProdutosFinal);

                        for (int i = 0; i < arrayProdutos.size(); i++) {
                            if (arrayProdutos.get(i).getPreparado() == true) {
                                listaProdutosFinal.setItemChecked(i, true);
                            }
                        }

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


        new adicionarProdutosCirurgia().execute(arrayProdutos);
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
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
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
                        getBaseContext(), android.R.layout.simple_list_item_1,
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
                finish();
                Toast.makeText(getApplicationContext(), "Erro Get Listas - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class getProdutosCirurgia extends
            AsyncTask<Integer, Void, ArrayList<ProdutosCirurgia>> {
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

                        arrayProdutos.add(lista.get(i));

                }

                if(arrayProdutos.size()>0) {
                    adaptadorProdutosFinal = new ArrayAdapter<ProdutosCirurgia>(getBaseContext(),
                            android.R.layout.simple_list_item_multiple_choice, arrayProdutos);

                    adaptadorProdutosFinal.sort(new Comparator<ProdutosCirurgia>() {

                        @Override
                        public int compare(ProdutosCirurgia lhs, ProdutosCirurgia rhs) {
                            return ("" + lhs.getNomeProduto().toUpperCase()).compareTo(("" + rhs.getNomeProduto()).toUpperCase());
                        }
                    });
                    listaProdutosFinal.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listaProdutosFinal.setAdapter(adaptadorProdutosFinal);


                    for (int j = 0; j < arrayProdutos.size(); j++) {
                        if (arrayProdutos.get(j).getPreparado() == true) {
                            listaProdutosFinal.setItemChecked(j, true);
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
                    adaptadorProduto = new ArrayAdapter<Produtos>(getBaseContext(),
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


    private class adicionarProdutosCirurgia extends
            AsyncTask<ArrayList<ProdutosCirurgia>, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(ListaProdutosActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog.setMessage("A Adicionar Produtos...");

//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }

        ;

        @Override
        protected Boolean doInBackground(ArrayList<ProdutosCirurgia>... params) {
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
            String a = (result ? "Produtos Adicionados com Sucesso!"
                    : "Produtos Não Adicionados!");

            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
                    .show();
            adicionou = true;
            ringProgressDialog.dismiss();

            createPDF();

        }

    }

    public void createPDF()
    {
        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir,"ListaMateriais_id:" + idCirurgia+".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            /* Create Paragraph and Set Font */
            Paragraph p1 = new Paragraph("Lista Materiais:  ", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p1);

            Paragraph enter = new Paragraph(" ");
            doc.add(enter);


            for(int i = 0; i<arrayProdutos.size(); i++)
            {
                Paragraph s = new Paragraph(arrayProdutos.get(i).toString(), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.UNDERLINE));
                s.setAlignment(Paragraph.ALIGN_LEFT);
                doc.add(s);
            }

            doc.add(enter);
            doc.add(enter);
            doc.add(enter);



            Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
            openPdf();
        }
    }

    void openPdf()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        File file = new File(path,"ListaMateriais_id:" + idCirurgia+".pdf");

        intent.setDataAndType( Uri.fromFile(file), "application/pdf" );
        startActivity(intent);
    }


}
