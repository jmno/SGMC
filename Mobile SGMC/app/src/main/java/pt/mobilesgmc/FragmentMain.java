
package pt.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.http.ParseException;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.ProdutosCirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;


public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private ArrayAdapter<Cirurgia> adaptadorCirurgias;
    private Dialog dialog;
    public static ListView listaCirurgias;
    public static TextView textoCirurgiaAUsar;
    ProgressDialog ringProgressDialog = null;
    private ImageView btn_gravarSom;
    private Button btn_comecarParar;
    private Chronometer chronometer;
    private Boolean isChronometer;
    private String nomeFicheiro;

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

        textoCirurgiaAUsar = (TextView) rootView
                .findViewById(R.id.textViewCirurgia);
        chronometer = (Chronometer) rootView.findViewById(R.id.textView_tempoDecorrido);
        chronometer.setText("00:00:00");

        btn_gravarSom = (ImageView) rootView.findViewById(R.id.imageView_recordSound);

        btn_gravarSom.setClickable(true);
        btn_gravarSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_comecarParar = (Button) rootView.findViewById(R.id.button_ComecarAcabarCirurgia);
        btn_comecarParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.getCirurgia()!=null)
                if(isChronometer) {
                    btn_comecarParar.setText("Começar Cirurgia");
                    stopCount();
                }
                else {
                    btn_comecarParar.setText("Terminar Cirurgia");
                    startCount();
                }
            }
        });



        btn_gravarSom.setVisibility(View.GONE);
        btn_comecarParar.setText("Começar Cirurgia");
        chronometer.setVisibility(View.GONE);
        if(HomeActivity.getCirurgia()!=null)
        verificaCount();

        setHasOptionsMenu(true);
		return rootView;


	}

    public void verificaCount()
    {
        if(HomeActivity.getCirurgia().getHoraInicioCirurgia()!=null)
        {
            restartCount();
            chronometer.setVisibility(View.VISIBLE);
            btn_gravarSom.setVisibility(View.VISIBLE);
        }
        else {
            chronometer.setVisibility(View.INVISIBLE);
            btn_gravarSom.setVisibility(View.INVISIBLE);
        }

    }
    public void restartCount()
    {
        String horaInicio = HomeActivity.getCirurgia().getHoraInicioCirurgia();
        LocalTime hInicio = new LocalTime( horaInicio );
        Period t = Period.fieldDifference(hInicio,LocalTime.now());
        chronometer.setText(t.getHours()+":"+t.getMinutes()+":"+t.getSeconds());
        startCount();

    }

    public void startCount()
    {
        btn_comecarParar.setText("Terminar Cirurgia");
        chronometer.setVisibility(View.VISIBLE);
        int stoppedMilliseconds = 0;
        isChronometer = true;
        String chronoText = chronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        chronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
        chronometer.start();
    }


    public void stopCount()
    {
        btn_comecarParar.setText("Começar Cirurgia");

        isChronometer = false;
        chronometer.stop();
        chronometer.setVisibility(View.GONE);
        chronometer.setText("00:00:00");
        showElapsedTime();
    }

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        Period p = new Period(elapsedMillis);
        String tempo  =p.getHours() +":" + p.getMinutes() + ":"+p.getSeconds();
        Log.i("Tempo Cirurgia", tempo);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if(HomeActivity.getCirurgia()!=null)
            textoCirurgiaAUsar.setText("Cirurgia : " + HomeActivity.getCirurgia().getId() +"\n" +HomeActivity.getCirurgia().getIdEquipa());
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
                search();
                break;

            case R.id.action_CancelSurgery:
                HomeActivity.setCirurgia(null);
                HomeActivity.setEquipa(null);
                HomeActivity.setListaProdutos(null);
                textoCirurgiaAUsar.setText("Nenhuma Cirurgia");
                break;

            case R.id.action_ExportarSurgery:
                if(HomeActivity.getCirurgia()!=null)
                    gerarReport();
                break;
        }
		return true;
	}	

   public void search()
   {
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

                       HomeActivity.setCirurgia(c);
                       verificaCount();
                       //((HomeActivity) getActivity()).setNewCounterValue(1,1);
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
               textoCirurgiaAUsar.setText("Cirurgia :" + cirurgia);
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

            p1 = new Paragraph("END OF REPORT", FontFactory.getFont(FontFactory.defaultEncoding, Font.DEFAULTSIZE, Font.BOLDITALIC));
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



}
