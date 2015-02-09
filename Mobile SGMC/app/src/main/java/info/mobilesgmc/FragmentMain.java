
package info.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Comparator;

import info.mobilesgmc.modelo.Cirurgia;
import info.mobilesgmc.modelo.ProdutosCirurgia;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;


public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private ArrayAdapter<Cirurgia> adaptadorCirurgias;
    private Dialog dialog;
    public static ListView listaCirurgias;
    ProgressDialog ringProgressDialog = null;
    private ImageView btn_gravarSom;
    private Button btn_comecarParar;
    private Boolean isChronometer;
    private String nomeFicheiro;
    private LinearLayout layoutHomeDados;
    private TextView textView_Cirurgia;
    private TextView textView_Equipa;
    private TextView textView_Cirurgiao;
    private TextView textView_Utente;
    private TextView textView_HoraInicioCirurgia;
    private TextView textView_HoraFimCirurgia;

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


        btn_gravarSom = (ImageView) rootView.findViewById(R.id.imageView_recordSound);

        btn_gravarSom.setClickable(true);
        btn_gravarSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


         btn_comecarParar = (Button) rootView.findViewById(R.id.button_ComecarAcabarCirurgia);



        btn_gravarSom.setVisibility(View.GONE);
        btn_comecarParar.setText("Começar Cirurgia");
        btn_comecarParar.setVisibility(View.INVISIBLE);


        btn_comecarParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                String hora = mYear+"-"+mMonth+"-"+mDay+" "+mHour+":"+mMinute;
                if(verificaCondicoesComecar())
                {
                    if(isForStart())
                    {
                        HomeActivity.getCirurgia().setHoraInicioCirurgia(hora);
                        new atualizarCirurgia().execute(HomeActivity.getCirurgia());
                    }

                    else
                    {
                        HomeActivity.getCirurgia().setHoraFimCirurgia(hora);
                        new atualizarCirurgia().execute(HomeActivity.getCirurgia());
                        btn_comecarParar.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),"Tem de selecionar Utente \n Equipa Cirúrgica",Toast.LENGTH_SHORT).show();

                }
                atualizanomes();
            }
        });
        layoutHomeDados = (LinearLayout) rootView.findViewById(R.id.linearLayout_dadosHome);
        textView_Cirurgia = (TextView) rootView.findViewById(R.id.textView_HomeDados_Cirurgia);
        textView_Equipa = (TextView) rootView.findViewById(R.id.textView_HomeDados_Equipa);
        textView_Cirurgiao = (TextView) rootView.findViewById(R.id.textView_HomeDados_Cirurgiao);
        textView_Utente = (TextView) rootView.findViewById(R.id.textView_HomeDados_Utente);
        textView_HoraInicioCirurgia = (TextView) rootView.findViewById(R.id.textView_HomeDados_HoraInicio);
        textView_HoraFimCirurgia = (TextView) rootView.findViewById(R.id.textView_HomeDados_FimCirurgia);

        layoutHomeDados.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
		return rootView;


	}
    public boolean isForStart()
    {
        boolean resultado=false;

        if(HomeActivity.getCirurgia().getHoraInicioCirurgia()!=null)
            resultado=false;
        else
            resultado = true;

        return resultado;
    }

    public void atualizanomes()
    {
        btn_comecarParar.setVisibility(View.VISIBLE);
        btn_comecarParar.setText("Começar");
        layoutHomeDados.setVisibility(View.VISIBLE);
        if(HomeActivity.getCirurgia()!=null) {
            textView_Cirurgia.setText(HomeActivity.getCirurgia().getCirurgia());
            if(HomeActivity.getCirurgia().getHoraInicioCirurgia()!=null){
                textView_HoraInicioCirurgia.setText(HomeActivity.getCirurgia().getHoraInicioCirurgia());
                btn_comecarParar.setText("Finalizar");
            }
            if(HomeActivity.getCirurgia().getHoraFimCirurgia()!=null){
                textView_HoraFimCirurgia.setText(HomeActivity.getCirurgia().getHoraFimCirurgia());
                btn_comecarParar.setVisibility(View.INVISIBLE);

            }
        }
        if(HomeActivity.getEquipa()!=null) {
            textView_Equipa.setText(HomeActivity.getEquipa().getNomeEquipa());
            String text = "";
            if(HomeActivity.getEquipa().getListaProfissionais()!=null){
                if(HomeActivity.getEquipa().getListaProfissionais().size()>0)
                {
                    for(int i=0; i<HomeActivity.getEquipa().getListaProfissionais().size();i++)
                    {
                        if(HomeActivity.getEquipa().getListaProfissionais().get(i).getTipo().toLowerCase().equals("c"))
                            text = HomeActivity.getEquipa().getListaProfissionais().get(i).getProfissional().getNome();
                    }
                }
            }

            textView_Cirurgiao.setText(text);
        }

        if(HomeActivity.getUtente()!=null)
        textView_Utente.setText(HomeActivity.getUtente().getNome());



    }
    public void limpaDados()
    {
        layoutHomeDados.setVisibility(View.INVISIBLE);
        textView_Cirurgia.setText("");
        textView_Equipa.setText("");
        textView_Cirurgiao.setText("");
        textView_Utente.setText("");
        textView_HoraInicioCirurgia.setText("");
        textView_HoraFimCirurgia.setText("");
        btn_comecarParar.setVisibility(View.INVISIBLE);

    }

    public boolean verificaCondicoesComecar()
    {
        boolean resultado=false;

        if(HomeActivity.getCirurgia()!=null)
            if(HomeActivity.getCirurgia().getIdUtente()!=0)
                resultado = true;
            else if (HomeActivity.getCirurgia().getHoraInicioCirurgia()==null)
                resultado = true;
        else
        resultado = false;


        return resultado;
    }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if(HomeActivity.getCirurgia()!=null || HomeActivity.getUtente() != null || HomeActivity.getEquipa()!=null)
        atualizanomes();
		super.onActivityCreated(savedInstanceState);

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
        int id = item.getItemId();
		switch (id) {
            case R.id.action_searchCirurgia:
                action_searchCirurgias();
                break;

            case R.id.action_CancelSurgery:
                HomeActivity.setCirurgia(null);
                HomeActivity.setEquipa(null);
                HomeActivity.setListaProdutos(null);
                HomeActivity.setUtente(null);
                limpaDados();
                break;

            case R.id.action_ExportarSurgery:
                if(HomeActivity.getCirurgia()!=null)
                    gerarReport();
                break;
        }
		return true;
	}	

   public void action_searchCirurgias()
   {
       new getAllCirurgias().execute();

       dialog = new Dialog(getView().getContext());
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
               atualizanomes();
           }
       });
   }




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
            } catch (ParseException | IOException | JSONException
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

    public void gerarReport()
    {
        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);

            nomeFicheiro = "Report_id=" + HomeActivity.getCirurgia().getId()+"_date="+(HomeActivity.getCirurgia().getData()!=null?HomeActivity.getCirurgia().getData():"NULL")+".pdf";
            File file = new File(dir,nomeFicheiro);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            /* Create Paragraph and Set Font */
            Paragraph p1 = new Paragraph("REPORT SGMC", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);

            Paragraph enter = new Paragraph(" ");
            doc.add(enter);
            doc.add(enter);

            ///CIRURGIA
            p1 = new Paragraph("Dados Cirurgia", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            doc.add(p1);
            doc.add(enter);
            p1 = new Paragraph("Cirurgia: " + (HomeActivity.getCirurgia().getCirurgia()!=null?HomeActivity.getCirurgia().getCirurgia():""), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
            p1 = new Paragraph("Data: " + (HomeActivity.getCirurgia().getData()!=null?HomeActivity.getCirurgia().getData():""), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
            p1 = new Paragraph("Hora: " + (HomeActivity.getCirurgia().getHora()!=null?HomeActivity.getCirurgia().getHora():""), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
            doc.add(enter);
            doc.add(enter);

            ///UTENTE
            p1 = new Paragraph("Dados Utente", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            doc.add(p1);
            doc.add(enter);
            p1 = new Paragraph("Numero Processo: " +HomeActivity.getCirurgia().getIdUtente(), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
            /*
            p1 = new Paragraph(": " + (HomeActivity.getCirurgia().getData()!=null?HomeActivity.getCirurgia().getData():""), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
            p1 = new Paragraph("Hora: " + (HomeActivity.getCirurgia().getHora()!=null?HomeActivity.getCirurgia().getHora():""), FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
            doc.add(p1);
              */
            doc.add(enter);
            doc.add(enter);

            ///EQUIPA CIRURGICA
            p1 = new Paragraph("Equipa Cirurgica", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            doc.add(p1);
            doc.add(enter);
            doc.add(enter);


            //DADOS LISTAS
            p1 = new Paragraph("Produtos", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            doc.add(p1);
            doc.add(enter);
            doc.add(enter);


            if(HomeActivity.getListaProdutos()!=null)
            {
                p1 = new Paragraph("Aparelhos", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                doc.add(p1);
                if(HomeActivity.getListaProdutos().getListaAparelhos().size()>0)
                for(int i=0; i<HomeActivity.getListaProdutos().getListaAparelhos().size(); i++)
                {
                    ProdutosCirurgia p = HomeActivity.getListaProdutos().getListaAparelhos().get(i);
                    String a = "*" + p.getNomeProduto() + " Quantidade : " + p.getQuantidade();
                    p1 = new Paragraph(a,FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                    doc.add(p1);
                    p1 = new Paragraph("Utilizado:" + (p.getUtilizado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                    doc.add(p1);
                    p1 = new Paragraph("Preparado:" + (p.getPreparado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                    doc.add(p1);
                    doc.add(enter);
                }
                doc.add(enter);

                p1 = new Paragraph("Instrumentos", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                doc.add(p1);
                if(HomeActivity.getListaProdutos().getListaInstrumentos().size()>0)
                    for(int i=0; i<HomeActivity.getListaProdutos().getListaInstrumentos().size(); i++)
                    {
                        ProdutosCirurgia p = HomeActivity.getListaProdutos().getListaInstrumentos().get(i);
                        String a = "*" + p.getNomeProduto() + " Quantidade : " + p.getQuantidade();
                        p1 = new Paragraph(a,FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);
                        p1 = new Paragraph("Utilizado:" + (p.getUtilizado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);
                        p1 = new Paragraph("Preparado:" + (p.getPreparado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);

                    }
                doc.add(enter);

                p1 = new Paragraph("Materiais", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                doc.add(p1);
                if(HomeActivity.getListaProdutos().getListaMateriais().size()>0)
                    for(int i=0; i<HomeActivity.getListaProdutos().getListaMateriais().size(); i++)
                    {
                        ProdutosCirurgia p = HomeActivity.getListaProdutos().getListaMateriais().get(i);
                        String a = "*" + p.getNomeProduto() + " Quantidade : " + p.getQuantidade();
                        p1 = new Paragraph(a,FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);
                        p1 = new Paragraph("Utilizado:" + (p.getUtilizado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);
                        p1 = new Paragraph("Preparado:" + (p.getPreparado()?"Sim":"Não"),FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.NORMAL));
                        doc.add(p1);

                    }
                doc.add(enter);


            }




            doc.add(enter);

            p1 = new Paragraph("FIM", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);

            Toast.makeText(getActivity().getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();

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

        File file = new File(path,nomeFicheiro);

        intent.setDataAndType( Uri.fromFile(file), "application/pdf" );
        startActivity(intent);
    }


    private class atualizarCirurgia extends AsyncTask<Cirurgia, Void, Boolean> {

        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle((getActivity().getString(R.string.aguarde)));
            ringProgressDialog.setMessage(getActivity().getString(R.string.guardar_dados));

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        }

        ;

        @Override
        protected Boolean doInBackground(Cirurgia... params) {
            Boolean adicionou = false;

            try {
                adicionou = WebServiceUtils.updateCirurgia(params[0],
                        HomeActivity.getCirurgia().getId(), HomeActivity.getToken());

            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String a = (result ? "Cirurgia Alterada com Sucesso!"
                    : "Cirurgia Não Alterada!");

            ringProgressDialog.dismiss();
            super.onPostExecute(result);
        }

    }



}
