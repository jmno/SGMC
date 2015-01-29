package pt.mobilesgmc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import pt.mobilesgmc.modelo.AdaptadorHoras;
import pt.mobilesgmc.modelo.BlocoComSala;
import pt.mobilesgmc.modelo.BlocoOperatorio;
import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.EspecialidadeTipoCirurgia;
import pt.mobilesgmc.modelo.Horas;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.TipoCirurgia;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class DadosCirurgia extends Fragment {

    private Spinner especialidadeCirurgica;
    private TextView data;
    private Spinner tipoCirurgia;
    private EditText cirurgia;
    private Spinner sala;
    private Spinner lateralidade;
    private Spinner classificacaoASA;
    private TextView horaCirurgia;
    private TextView horaChamadaUtente;
    private TextView horaEntradaBO;
    private TextView horaSaidaBO;
    private TextView horaEntradaSala;
    private TextView horaSaidaSala;
    private TextView horaInicioAnestesia;
    private TextView horaFimAnestesia;
    private TextView horaInicioCirurgia;
    private TextView horaFimCirurgia;
    private TextView horaEntradaRecobro;
    private TextView horaSaidaRecobro;
    private Spinner destinoDoente;
    private EditText informacoesRelevantes;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private Spinner bloco;
    private ArrayAdapter<BlocoOperatorio> adaptadorBloco;
    private String token;
    private int idCirurgia;
    private ArrayAdapter<BlocoComSala> adaptadorBlococomSala;
    ProgressDialog ringProgressDialog = null;
    private TextView texto;
    private Dialog dialog;
    private ArrayList<Horas> items;
    private AdaptadorHoras adapter;
    public String horaAdicionada;
    public ListView lista;
    public String h;
    ArrayList<String> itemsHora;
    ArrayAdapter<String> adaptador;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private ImageView btn_AddHoras;
    private ListView listView;
    private ArrayAdapter<EspecialidadeTipoCirurgia> adaptadorEspecialidade;
    private int posicaoTipo = 0;
    private int idUtente = 0;
    private Menu menu;

    public DadosCirurgia newInstance(String text){
        DadosCirurgia mFragment = new DadosCirurgia();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_dados_cirurgia, container, false);
        token = HomeActivity.getToken();

        /*
        if(HomeActivity.getCirurgia()!=null)
        if(HomeActivity.getCirurgia().getCirurgia()!=null) {
            getActivity().setTitle("Editar"+ HomeActivity.getCirurgia().getCirurgia());
            idCirurgia = HomeActivity.getCirurgia().getId();
        }

        if(HomeActivity.getIdUtenteAAssociar()!=0)
            idUtente = HomeActivity.getIdUtenteAAssociar();
        else
            getActivity().setTitle("Editar Cirurgia:");
*/
        dialog = new Dialog(getActivity());
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollDadosCirurgia);
        //   linearLayout = (LinearLayout) findViewById(R.id.linearLayout_DadosCirurgia_Horas);

        data = (TextView) rootView.findViewById(R.id.TextViewData);
        //horaChamadaUtente = (TextView) findViewById(R.id.editTextHoraChamadaUtente);
        especialidadeCirurgica = (Spinner) rootView.findViewById(R.id.spinnerEspecialidade);
        tipoCirurgia = (Spinner) rootView.findViewById(R.id.spinnerTipoCirurgia);
        sala = (Spinner) rootView.findViewById(R.id.spinnerSala);
        lateralidade = (Spinner) rootView.findViewById(R.id.spinnerLateralidade);
        classificacaoASA = (Spinner) rootView.findViewById(R.id.spinnerASA);
        horaCirurgia = (TextView) rootView.findViewById(R.id.TextViewHoraCirurgia);
//        horaEntradaBO = (TextView) findViewById(R.id.editTextHoraEntradaBO);
//        horaSaidaBO = (TextView) findViewById(R.id.editTextHoraSaidaBO);
//        horaEntradaSala = (TextView) findViewById(R.id.editTextHoraEntradaSala);
//        horaSaidaSala = (TextView) findViewById(R.id.editTextHoraSaidaSala);
//        horaInicioAnestesia = (TextView) findViewById(R.id.editTextHoraInicioAnestesia);
//        horaFimAnestesia = (TextView) findViewById(R.id.editTextHoraFimAnestesia);
//        horaInicioCirurgia = (TextView) findViewById(R.id.editTextHoraInicioCirurgia);
//        horaFimCirurgia = (TextView) findViewById(R.id.editTextHoraFimCirurgia);
//        horaEntradaRecobro = (TextView) findViewById(R.id.editTextHoraEntradaRecobro);
//        horaSaidaRecobro = (TextView) findViewById(R.id.editTextHoraSaidaRecobro);
        destinoDoente = (Spinner) rootView.findViewById(R.id.spinnerDestinoDoente);
        informacoesRelevantes = (EditText) rootView.findViewById(R.id.editTextInformacoes);
        cirurgia = (EditText) rootView.findViewById(R.id.editTextCirurgia);
        itemsHora = new ArrayList<String>();
        itemsHora.add("Hora Chamada do Utente");
        itemsHora.add("Hora Entrada BO");
        itemsHora.add("Hora Saida BO");
        itemsHora.add("Hora Entrada Sala");
        itemsHora.add("Hora Saida Sala");
        itemsHora.add("Hora Inicio Anestesia");
        itemsHora.add("Hora Fim Anestesia");
        itemsHora.add("Hora Inicio Cirurgia");
        itemsHora.add("Hora Fim Cirurgia");
        itemsHora.add("Hora Entrada Recobro");
        itemsHora.add("Hora Saida Recobro");

        adaptador = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, itemsHora) {
            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        items = new ArrayList<Horas>();
        adapter = new AdaptadorHoras(getActivity(), items);
        listView = (ListView) rootView.findViewById(R.id.listView_DadosCirurgia_horas);

        carregaOsListeners();

        especialidadeCirurgica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atualizaTipoCirurgia(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new getEspecialidades().execute();



        // 1. pass context and data to the custom adapter

        // 2. Get ListView from activity_main.xml

        // 3. setListAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                texto = (TextView) view.findViewById(R.id.value);
                Toast.makeText(getActivity().getApplicationContext(), texto.getText().toString(), Toast.LENGTH_SHORT).show();
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                texto.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }

                        }, mHour, mMinute, true);

                dpd.show();


            }
        });

        btn_AddHoras = (ImageView) rootView.findViewById(R.id.imageView_DadosCirurgia_AddHora);
        btn_AddHoras.setClickable(true);
        btn_AddHoras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsHora.size()>0)
                    listenerButtonAddHora();

            }
        });


        setListViewHeightBasedOnChildren(listView);
        scrollView.requestLayout();
        scrollView.invalidate();
        setHasOptionsMenu(true);

        return rootView;
    }




/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                result= result.replace("\"", "");
                idUtente = Integer.parseInt(result);
                Toast.makeText(getApplicationContext(), "Utente selecionado. Não se esqueça de guardar", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Associação Cancelada",Toast.LENGTH_SHORT).show();
            }
        }
    } */
public void atualizaTipoCirurgia(int position)
{
    EspecialidadeTipoCirurgia e = (EspecialidadeTipoCirurgia)especialidadeCirurgica.getItemAtPosition(position);

    ArrayAdapter<TipoCirurgia> arrayAdapterTipoCirurgia;
    arrayAdapterTipoCirurgia = new ArrayAdapter<TipoCirurgia>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, e.getTipoCirurgia());

    arrayAdapterTipoCirurgia.sort(new Comparator<TipoCirurgia>() {

        @Override
        public int compare(TipoCirurgia lhs, TipoCirurgia rhs) {
            return lhs
                    .getTipo()
                    .toLowerCase()
                    .compareTo(
                            rhs.getTipo()
                                    .toLowerCase());
        }
    });
    tipoCirurgia.setAdapter(arrayAdapterTipoCirurgia);
    tipoCirurgia.setSelection(posicaoTipo);
}

    public void listenerButtonAddHora() {

        // tell the Dialog to use the dialog.xml as it's layout
        // description
        dialog.setContentView(R.layout.dialog_escolhahoras);
        dialog.setTitle("Escolha o Tipo de Hora:");
        dialog.getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        lista = (ListView) dialog.findViewById(R.id.listView_Dialog_EscolhaHoras);


        lista.setAdapter(adaptador);
        lista = (ListView) dialog
                .findViewById(R.id.listView_Dialog_EscolhaHoras);

        lista
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {

                        h = (String) lista
                                .getItemAtPosition(arg2);
                        novoTimePicker();


                        dialog.dismiss();
                    }
                });


        dialog.show();

    }


    public void novoTimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Date Picker Dialog
        TimePickerDialog dpd = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        horaAdicionada = (hourOfDay + ":" + minute
                                + ":00");

//FAZER VERIFICAÇOES HORAS
                        adicionaHora(horaAdicionada);
                       // ViewGroup.LayoutParams params = listView.getLayoutParams();
                      //  params.height =
                      //  listView.setLayoutParams(new ViewGroup.LayoutParams().height=1);


                        //scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

                    }

                }, mHour, mMinute, true);

        dpd.show();

    }

    public void adicionaHora(String horaAdicionada)
    {

        Horas hor = new Horas(h, horaAdicionada);
        adapter.add(hor);
        Log.i("Adapter horas", "Adatpter com " + adapter.getCount() + " horas");
        itemsHora.remove(h);
        setListViewHeightBasedOnChildren(listView);
        scrollView.requestLayout();
        scrollView.invalidate();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public ArrayList<Horas> generateHoras() {
        ArrayList<Horas> items = new ArrayList<Horas>();
        items.add(new Horas("Hora Chamada do Utente", ":"));
        items.add(new Horas("Hora Entrada BO", ":"));
        items.add(new Horas("Hora Saida BO", ":"));
        items.add(new Horas("Hora Entrada Sala", ":"));
        items.add(new Horas("Hora Saida Sala", ":"));
        items.add(new Horas("Hora Inicio Anestesia", ":"));
        items.add(new Horas("Hora Fim Anestesia", ":"));
        items.add(new Horas("Hora Inicio Cirurgia", ":"));
        items.add(new Horas("Hora Fim Cirurgia", ":"));
        items.add(new Horas("Hora Entrada Recobro", ":"));
        items.add(new Horas("Hora Saida Recobro", ":"));
        return items;
    }

    private void carregaOsListeners() {

        data.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                data.setText(year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();



            }
        });

        horaCirurgia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaCirurgia.setText(hourOfDay + ":" + minute
                                        + ":00");

                            }

                        }, mHour, mMinute, true);

                dpd.show();

            }
        });


    }

    private void insereHoras(Cirurgia c) {
        if (c.getHoraChamadaUtente() != null) {
            Horas h = new Horas("Hora Chamada do Utente", c.getHoraChamadaUtente());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraEntradaBlocoOperatorio() != null) {
            Horas h = new Horas("Hora Entrada BO", c.getHoraEntradaBlocoOperatorio());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraSaideBlocoOperatorio() != null) {
            Horas h = new Horas("Hora Saida BO", c.getHoraSaideBlocoOperatorio());
            adapter.add(h);
            itemsHora.remove(h.getTitle());


        }

        if (c.getHoraEntradaSala() != null) {
            Horas h = new Horas("Hora Entrada Sala", c.getHoraEntradaSala());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraSaidaSala() != null) {
            Horas h = new Horas("Hora Saida Sala", c.getHoraSaidaSala());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraInicioAnestesia() != null) {
            Horas h = new Horas("Hora Inicio Anestesia", c.getHoraInicioAnestesia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraFimAnestesia() != null) {
            Horas h = new Horas("Hora Fim Anestesia", c.getHoraFimAnestesia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraInicioCirurgia() != null) {
            Horas h = new Horas("Hora Inicio Cirurgia", c.getHoraInicioCirurgia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraFimCirurgia() != null) {
            Horas h = new Horas("Hora Fim Cirurgia", c.getHoraFimCirurgia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }

        if (c.getHoraEntradaRecobro() != null) {
            Horas h = new Horas("Hora Entrada Recobro", c.getHoraEntradaRecobro());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }


        if (c.getHoraFimRecobro() != null) {
            Horas h = new Horas("Hora Saida Recobro", c.getHoraFimRecobro());
            adapter.add(h);
            itemsHora.remove(h.getTitle());

        }
        setListViewHeightBasedOnChildren(listView);
        scrollView.requestLayout();
        scrollView.invalidate();

    }

    /**/
    private int spinnerDaMeATuaPosicaoBloco(ArrayAdapter<BlocoComSala> adapter,
                                            int salaId) {
        int valor = -1;
        for (int i = 0; i < adapter.getCount(); i++) {
            BlocoComSala pro = (BlocoComSala) adapter.getItem(i);
            if (pro.getSala().getId() == salaId)
                valor = i;
        }
        return valor;
    }

    /**/

    private int spinnerDaMeATuaPosicaoTipoCirurgia(ArrayAdapter<EspecialidadeTipoCirurgia> adapter,int tipoId) {
        int valor = -1;
        for (int i = 0; i < adapter.getCount(); i++) {
            EspecialidadeTipoCirurgia pro = (EspecialidadeTipoCirurgia) adapter.getItem(i);
                  for(int j=0; j<pro.getTipoCirurgia().size(); j++){
                      if(pro.getTipoCirurgia().get(j).getId() == tipoId)
                          valor = i;
                  }
        }
        return valor;
    }
    private void preencherAtividade(Cirurgia c) {

        insereHoras(c);

        if(!(c.getData()==null))
        data.setText(c.getData().toString());
        if(!(c.getHora()==null))
            horaCirurgia.setText(c.getHora().toString());
//
        if(c.getInfoRelevante()!=null)
        informacoesRelevantes.setText(c.getInfoRelevante());

        if(c.getCirurgia()!=null)
        cirurgia.setText(c.getCirurgia().toString());

        // Spinner
         //int gh = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
       //  c.getIdSala());
        //sala.setSelection(gh);
        int a =0;

        if(c.getIdTipoCirurgia()!=0){
        int [] result= spinnerDaMeATuaPosicaoEspecialidadeETipo(especialidadeCirurgica.getAdapter(),c.getIdTipoCirurgia());
        especialidadeCirurgica.setSelection(result[0]);
            posicaoTipo = result[1];
        atualizaTipoCirurgia(result[0]);
        }


        if(c.getLateralidade()!=null) {
            a = spinnerDaMeATuaPosicao(lateralidade.getAdapter(),
                    c.getLateralidade());
            lateralidade.setSelection(a);
        }

        if(c.getClassifASA()!=null){
        a = spinnerDaMeATuaPosicao(classificacaoASA.getAdapter(),
                c.getClassifASA());
        classificacaoASA.setSelection(a);}

        if(c.getDestinoDoente()!=null) {
            a = spinnerDaMeATuaPosicao(destinoDoente.getAdapter(),
                    c.getDestinoDoente());
            destinoDoente.setSelection(a);
        }

    }

    private int[] spinnerDaMeATuaPosicaoEspecialidadeETipo(SpinnerAdapter adaptador, int idTipo)
    {
        int [] resultado = new int[2];

        for(int k=0; k<adaptador.getCount(); k++)
        {
            EspecialidadeTipoCirurgia a = (EspecialidadeTipoCirurgia) adaptador.getItem(k);
            for (int j=0;j<a.getTipoCirurgia().size(); j++)
            {
                if(a.getTipoCirurgia().get(j).getId()==idTipo) {
                    resultado[0] = k;
                    resultado[1] = j;
                }
            }
        }

        return resultado;

    }
    private int spinnerDaMeATuaPosicao(SpinnerAdapter adapter, String p) {
        int valor = -1;
        for (int i = 0; i < adapter.getCount(); i++) {
            String pro = (String) adapter.getItem(i);
            if (pro.equals(p))
                valor = i;
        }
        return valor;
    }

    public void guardarDadosCirurgia() {

        try {
            if(HomeActivity.getCirurgia().getId()!=0) {

                HomeActivity.getCirurgia().setIdTipoCirurgia(((TipoCirurgia) tipoCirurgia.getSelectedItem()).getId());
                HomeActivity.getCirurgia().setLateralidade(lateralidade.getSelectedItem().toString());
                HomeActivity.getCirurgia().setClassifASA(classificacaoASA.getSelectedItem()
                        .toString());

                HomeActivity.getCirurgia().setDestinoDoente(destinoDoente.getSelectedItem().toString());
                HomeActivity.getCirurgia().setCirurgia(cirurgia.getText().toString());
                HomeActivity.getCirurgia().setInfoRelevante(informacoesRelevantes.getText().toString());
                String da = data.getText().toString();
                da = da.replace("/","");
                da = da.replace(" ", "");
                if(da.length()>1)
                    HomeActivity.getCirurgia().setData(data.getText().toString());
                String ho = horaCirurgia.getText().toString();
                ho = ho.replace(":", "");
                ho = ho.replace(" ","");
                if(ho.length()>1)
                    HomeActivity.getCirurgia().setHora(horaCirurgia.getText().toString());


                TipoCirurgia t = (TipoCirurgia) tipoCirurgia.getSelectedItem();
                HomeActivity.getCirurgia().setIdTipoCirurgia(t.getId());


                preencherCirurgiaComHorasParaGuardar(adapter, HomeActivity.getCirurgia());


                BlocoComSala blocoCS = (BlocoComSala) sala.getSelectedItem();
                HomeActivity.getCirurgia().setIdSala(blocoCS.getSala().getId());

                //
                new atualizarCirurgia().execute(HomeActivity.getCirurgia());
            }
            else
                novaCirurgia();

        } catch (Exception rn) {
            novaCirurgia();
        }


    }
    public void novaCirurgia()
    {
        HomeActivity.getCirurgia().setIdTipoCirurgia(((TipoCirurgia) tipoCirurgia.getSelectedItem()).getId());
        HomeActivity.getCirurgia().setIdSala(((BlocoComSala) sala.getSelectedItem()).getSala().getId());
        if(idUtente!=0)
            HomeActivity.getCirurgia().setIdUtente(idUtente);
        //id utente cir.setIdUtente(xxxx); e idEquipa
        HomeActivity.getCirurgia().setLateralidade(lateralidade.getSelectedItem().toString());
        HomeActivity.getCirurgia().setClassifASA(classificacaoASA.getSelectedItem()
                .toString());

        HomeActivity.getCirurgia().setDestinoDoente(destinoDoente.getSelectedItem().toString());
        HomeActivity.getCirurgia().setCirurgia(cirurgia.getText().toString());
        HomeActivity.getCirurgia().setInfoRelevante(informacoesRelevantes.getText().toString());
        String da = data.getText().toString();
        da = da.replace("/","");
        da = da.replace(" ", "");
        if(da.length()>1)
            HomeActivity.getCirurgia().setData(data.getText().toString());
        String ho = horaCirurgia.getText().toString();
        ho = ho.replace(":", "");
        ho = ho.replace(" ","");
        if(ho.length()>1)
            HomeActivity.getCirurgia().setHora(horaCirurgia.getText().toString());

        TipoCirurgia t = (TipoCirurgia) tipoCirurgia.getSelectedItem();
        HomeActivity.getCirurgia().setIdTipoCirurgia(t.getId());
        preencherCirurgiaComHorasParaGuardar(adapter, HomeActivity.getCirurgia());

        new guardarCirurgia().execute(HomeActivity.getCirurgia());
    }

    public void preencherCirurgiaComHorasParaGuardar(ArrayAdapter<Horas> adap, Cirurgia c1){

        if(adap.getCount()>0){
            for(int i=0; i<adap.getCount(); i++){
                Horas h = (Horas) adap.getItem(i);
                if(h.getTitle().toLowerCase().equals("hora chamada do utente"))
                    c1.setHoraChamadaUtente(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora entrada bo"))
                    c1.setHoraEntradaBlocoOperatorio(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora saida bo"))
                    c1.setHoraSaideBlocoOperatorio(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora entrada sala"))
                    c1.setHoraEntradaSala(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora saida sala"))
                    c1.setHoraSaidaSala(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora inicio anestesia"))
                    c1.setHoraInicioAnestesia(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora fim anestesia"))
                    c1.setHoraFimAnestesia(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora inicio cirurgia"))
                c1.setHoraInicioCirurgia(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora fim cirurgia"))
                    c1.setHoraFimCirurgia(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora entrada recobro"))
                    c1.setHoraEntradaRecobro(h.getDescription());
                else if(h.getTitle().toLowerCase().equals("hora saida recobro"))
                    c1.setHoraFimRecobro(h.getDescription());
            }
        }


    }
    private void hideOption(int id)
    {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.dados_cirurgia, menu);

        this.menu = menu;
            if(HomeActivity.getCirurgia().getIdUtente()!=0){
                menu.findItem(R.id.action_associarCirurgia).setVisible(false).setEnabled(false);
            }
            else
                menu.findItem(R.id.action_associarCirurgia).setVisible(true).setEnabled(true);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_saveDadosCirurgia) {
            guardarDadosCirurgia();
            return true;
        }

        if(id == R.id.action_associarCirurgia){

            HomeActivity.setIsForResultUtentes(true);
            ((HomeActivity) getActivity()).onItemClickNavigation(1,HomeActivity.getLayoutcontainerid());
            ((HomeActivity) getActivity()).setCheckedItemNavigation(1,true);
            //Intent i = new Intent(this,getActivity().);
        }

        return super.onOptionsItemSelected(item);
    }




    public void preencher(Cirurgia c) {

    }

    /*
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage("Pretende Retroceder sem guardar?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/


    // private class getBlocoOperatorios extends
    // AsyncTask<String, Void, ArrayList<BlocoOperatorio>> {
    //
    // @Override
    // protected ArrayList<BlocoOperatorio> doInBackground(String... params) {
    // ArrayList<BlocoOperatorio> lista = null;
    //
    // try {
    // lista = WebServiceUtils.getAllBloco(token);
    //
    // } catch (IOException | RestClientException | ParseException
    // | JSONException e) {
    // e.printStackTrace();
    // }
    //
    // return lista;
    // }
    //
    // @Override
    // protected void onPostExecute(ArrayList<BlocoOperatorio> lista) {
    // if (lista != null) {
    // adaptadorBloco = new ArrayAdapter<>(getBaseContext(),
    // android.R.layout.simple_list_item_1, lista);
    // bloco.setAdapter(adaptadorBloco);
    // } else {
    // Toast.makeText(getApplicationContext(),
    // "Get Bloco unsuccessful...", Toast.LENGTH_LONG).show();
    //
    // }
    // }
    // }

    private class atualizarCirurgia extends AsyncTask<Cirurgia, Void, Boolean> {

        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados...");

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
                        HomeActivity.getCirurgia().getId(), token);

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
            if (result) {
                Toast.makeText(getActivity(), a, Toast.LENGTH_LONG)
                        .show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
            } else {
                Toast.makeText(getActivity(), "Erro Atualizar Cirurgia - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
            }
            ringProgressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
    private class guardarCirurgia extends AsyncTask<Cirurgia, Void, Integer> {

        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A guardar dados...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        }

        ;

        @Override
        protected Integer doInBackground(Cirurgia... params) {
            int adicionou = 0;

            try {
                adicionou = WebServiceUtils.guardarCirurgia(params[0], token);

            } catch (ParseException | IOException | JSONException
                    | RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return adicionou;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result!=0) {
                HomeActivity.getCirurgia().setId(result);
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0, true);
            } else {
                Toast.makeText(getActivity(), "Erro Guardar Cirurgia - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }
            ringProgressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class getBlocosComSala extends
            AsyncTask<String, Void, ArrayList<BlocoComSala>> {
        @Override
        protected void onPreExecute() {

            /*ringProgressDialog = new ProgressDialog(DadosCirurgia.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();*/
        }

        ;

        @Override
        protected ArrayList<BlocoComSala> doInBackground(String... params) {
            ArrayList<BlocoComSala> lista = null;

            try {
                lista = WebServiceUtils.getAllBlocosComSala(token);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<BlocoComSala> lista) {
            if (lista != null) {
                adaptadorBlococomSala = new ArrayAdapter<BlocoComSala>(
                        getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
                        lista);
                adaptadorBlococomSala.sort(new Comparator<BlocoComSala>() {

                    @Override
                    public int compare(BlocoComSala lhs, BlocoComSala rhs) {
                        return lhs
                                .getNomeBlocoOperatorio()
                                .toLowerCase()
                                .compareTo(
                                        rhs.getNomeBlocoOperatorio()
                                                .toLowerCase());
                    }
                });
                sala.setAdapter(adaptadorBlococomSala);

                if(HomeActivity.getCirurgia()!=null) {
                    int a = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
                            HomeActivity.getCirurgia().getIdSala());
                    sala.setSelection(a);
                    if (HomeActivity.getCirurgia().getId() != 0)
                        preencherAtividade(HomeActivity.getCirurgia());
                }
                ringProgressDialog.dismiss();

            } else {
                Toast.makeText(getActivity(), "Erro Get Blocos Com Sala - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0, true);
            }
        }
    }

    private class getEspecialidades extends
            AsyncTask<String, Void, ArrayList<EspecialidadeTipoCirurgia>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(getActivity());
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Aguarde...");
            ringProgressDialog.setMessage("A carregar Dados...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(false);

            ringProgressDialog.show();
        }

        ;

        @Override
        protected ArrayList<EspecialidadeTipoCirurgia> doInBackground(String... params) {
            ArrayList<EspecialidadeTipoCirurgia> lista = null;

            try {
                lista = WebServiceUtils.getAllEspecialidadesComTipos(token);

            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<EspecialidadeTipoCirurgia> lista) {
            if (lista != null) {
                adaptadorEspecialidade = new ArrayAdapter<EspecialidadeTipoCirurgia>(
                        getActivity().getBaseContext(), android.R.layout.simple_list_item_1,
                        lista);
                adaptadorEspecialidade.sort(new Comparator<EspecialidadeTipoCirurgia>() {

                    @Override
                    public int compare(EspecialidadeTipoCirurgia lhs, EspecialidadeTipoCirurgia rhs) {
                        return lhs
                                .getEspecialidade().getEspecialidade()
                                .toLowerCase()
                                .compareTo(
                                        rhs.getEspecialidade().getEspecialidade()
                                                .toLowerCase());
                    }
                });
                especialidadeCirurgica.setAdapter(adaptadorEspecialidade);





                new getBlocosComSala().execute(token);


              //  ringProgressDialog.dismiss();

            } else {
                Toast.makeText(getActivity(), "Erro Get Especialidades - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);
            }
        }
    }
}
