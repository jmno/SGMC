package pt.mobilesgmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;
import com.shamanland.fab.ShowHideOnScroll;

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
import pt.mobilesgmc.modelo.Horas;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;

public class DadosCirurgia extends Activity {

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
    FlyOutContainer root;
    private ScrollView scrollDadosCirurgia;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private Spinner bloco;
    private ArrayAdapter<BlocoOperatorio> adaptadorBloco;
    private String token;
    private int idCirurgia;
    private Cirurgia cir;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cirurgia);
        setTitle("Editar: '" + HomeActivity.getCirurgia().getCirurgia() + "'");


        // idEquipa = Integer.parseInt(PreferenceManager
        // .getDefaultSharedPreferences(getApplicationContext())
        // .getString("icdEquipa", "-1"));


        /*
        ///* FAB BUTTON */



/* FAB But*/

        // new getBlocoOperatorios().execute();
        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        idCirurgia = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("idCirurgia", "0"));
        dialog = new Dialog(DadosCirurgia.this);

        data = (TextView) findViewById(R.id.TextViewData);
        horaChamadaUtente = (TextView) findViewById(R.id.editTextHoraChamadaUtente);
        especialidadeCirurgica = (Spinner) findViewById(R.id.spinnerEspecialidade);
        tipoCirurgia = (Spinner) findViewById(R.id.spinnerTipoCirurgia);
        sala = (Spinner) findViewById(R.id.spinnerSala);
        lateralidade = (Spinner) findViewById(R.id.spinnerLateralidade);
        classificacaoASA = (Spinner) findViewById(R.id.spinnerASA);
        horaCirurgia = (TextView) findViewById(R.id.TextViewHoraCirurgia);
        horaEntradaBO = (TextView) findViewById(R.id.editTextHoraEntradaBO);
        horaSaidaBO = (TextView) findViewById(R.id.editTextHoraSaidaBO);
        horaEntradaSala = (TextView) findViewById(R.id.editTextHoraEntradaSala);
        horaSaidaSala = (TextView) findViewById(R.id.editTextHoraSaidaSala);
        horaInicioAnestesia = (TextView) findViewById(R.id.editTextHoraInicioAnestesia);
        horaFimAnestesia = (TextView) findViewById(R.id.editTextHoraFimAnestesia);
        horaInicioCirurgia = (TextView) findViewById(R.id.editTextHoraInicioCirurgia);
        horaFimCirurgia = (TextView) findViewById(R.id.editTextHoraFimCirurgia);
        horaEntradaRecobro = (TextView) findViewById(R.id.editTextHoraEntradaRecobro);
        horaSaidaRecobro = (TextView) findViewById(R.id.editTextHoraSaidaRecobro);
        destinoDoente = (Spinner) findViewById(R.id.spinnerDestinoDoente);
        informacoesRelevantes = (EditText) findViewById(R.id.editTextInformacoes);
        cirurgia = (EditText) findViewById(R.id.editTextCirurgia);
        itemsHora= new ArrayList<String>();
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

        adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,itemsHora){
            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        items = new ArrayList<Horas>();
        adapter = new AdaptadorHoras(this, items);

        //carregaOsListeners();
        preencherAtividade(HomeActivity.getCirurgia());
        new getBlocosComSala().execute(token);




        // 1. pass context and data to the custom adapter

        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listView_DadosCirurgia_horas);

        // 3. setListAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                texto = (TextView) view.findViewById(R.id.value);
                Toast.makeText(getApplicationContext(), texto.getText().toString(), Toast.LENGTH_SHORT).show();

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
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

        View fab = (View) findViewById(R.id.fab);
// NOTE invoke this method after setting new values!
// NOTE standard method of ImageView
//        listView.setOnTouchListener(new ShowHideOnScroll(fab));
        listView.setOnTouchListener(new ShowHideOnScroll(fab, R.anim.shake, R.anim.shake));

        fab.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       listenerFAB();
                                   }
                               }
        );
    }

    public void listenerFAB()
    {

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




    public void novoTimePicker()
    {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Date Picker Dialog
        TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        horaAdicionada=(hourOfDay + ":" + minute
                                + ":00");
                        Horas hor = new Horas(h,horaAdicionada);
                        adapter.add(hor);
                        itemsHora.remove(h);

                    }

                }, mHour, mMinute, true);

        dpd.show();

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
                DatePickerDialog dpd = new DatePickerDialog(DadosCirurgia.this,
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
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
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
        horaChamadaUtente.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaChamadaUtente.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }

                        }, mHour, mMinute, true);

                dpd.show();

            }
        });

        horaEntradaBO.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaEntradaBO.setText(hourOfDay + ":" + minute
                                        + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaSaidaBO.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaSaidaBO.setText(hourOfDay + ":" + minute
                                        + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaEntradaSala.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaEntradaSala.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaSaidaSala.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaSaidaSala.setText(hourOfDay + ":" + minute
                                        + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });

        horaInicioAnestesia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaInicioAnestesia.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaFimAnestesia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaFimAnestesia.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaInicioCirurgia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaInicioCirurgia.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });

        horaFimCirurgia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaFimCirurgia.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });
        horaEntradaRecobro.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaEntradaRecobro.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });

        horaSaidaRecobro.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond = c.get(Calendar.SECOND);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(DadosCirurgia.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                horaSaidaRecobro.setText(hourOfDay + ":"
                                        + minute + ":00");

                            }
                        }, mHour, mMinute, true);

                dpd.show();

            }
        });

    }

    private void insereHoras(Cirurgia c)
    {
        if(c.getHoraChamadaUtente()!=null){
            Horas h = new Horas("Hora Chamada do Utente",c.getHoraChamadaUtente());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraEntradaBlocoOperatorio()!=null){
            Horas h = new Horas("Hora Entrada BO",c.getHoraEntradaBlocoOperatorio());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraSaideBlocoOperatorio()!=null){
            Horas h = new Horas("Hora Saida BO",c.getHoraSaideBlocoOperatorio());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraEntradaSala()!=null){
            Horas h = new Horas("Hora Entrada Sala",c.getHoraEntradaSala());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraSaidaSala()!=null){
            Horas h = new Horas("Hora Saida Sala",c.getHoraSaidaSala());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraInicioAnestesia()!=null){
            Horas h = new Horas("Hora Inicio Anestesia",c.getHoraInicioAnestesia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraFimAnestesia()!=null){
            Horas h = new Horas("Hora Fim Anestesia",c.getHoraFimAnestesia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraInicioCirurgia()!=null){
            Horas h = new Horas("Hora Inicio Cirurgia",c.getHoraInicioCirurgia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraFimCirurgia()!=null){
            Horas h = new Horas("Hora Fim Cirurgia",c.getHoraFimCirurgia());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

        if(c.getHoraEntradaRecobro()!=null){
            Horas h = new Horas("Hora Entrada Recobro",c.getHoraEntradaRecobro());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }


        if(c.getHoraFimRecobro()!=null){
            Horas h = new Horas("Hora Saida Recobro",c.getHoraFimRecobro());
            adapter.add(h);
            itemsHora.remove(h.getTitle());
        }

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
    private void preencherAtividade(Cirurgia c) {

        insereHoras(c);
        data.setText(c.getData().toString());
//        if(!(c.getHora()==null))
//            horaCirurgia.setText(c.getHora().toString());
//
//        if(!(c.getHoraChamadaUtente()==null))
//            horaChamadaUtente.setText(c.getHoraChamadaUtente().toString());
//
//        if(!(c.getHoraEntradaBlocoOperatorio()==null))
//            horaEntradaBO.setText(c.getHoraEntradaBlocoOperatorio().toString());
//
//        if(!(c.getHoraSaideBlocoOperatorio()==null))
//        horaSaidaBO.setText(c.getHoraSaideBlocoOperatorio().toString());
//
//        if(!(c.getHoraEntradaSala()==null))
//        horaEntradaSala.setText(c.getHoraEntradaSala().toString());
//
//        if(!(c.getHoraSaidaSala()==null))
//        horaSaidaSala.setText(c.getHoraSaidaSala().toString());
//
//        if(!(c.getHoraInicioAnestesia()==null))
//        horaInicioAnestesia.setText(c.getHoraInicioAnestesia().toString());
//
//        if(!(c.getHoraFimAnestesia()==null))
//        horaFimAnestesia.setText(c.getHoraFimAnestesia().toString());
//
//        if(!(c.getHoraInicioCirurgia()==null))
//        horaInicioCirurgia.setText(c.getHoraInicioCirurgia().toString());
//
//        if(!(c.getHoraFimCirurgia()==null))
//        horaFimCirurgia.setText(c.getHoraFimCirurgia().toString());
//
//        if(!(c.getHoraEntradaRecobro()==null))
//        horaEntradaRecobro.setText(c.getHoraEntradaRecobro().toString());
//
//        if(!(c.getHoraFimRecobro()==null))
//        horaSaidaRecobro.setText(c.getHoraFimRecobro().toString());

        informacoesRelevantes.setText(c.getInfoRelevante());
        cirurgia.setText(c.getCirurgia().toString());

        // Spinner
        // int gh = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
        // c.getIdSala());
        sala.setSelection(5);

        int a = spinnerDaMeATuaPosicao(tipoCirurgia.getAdapter(),
                c.getTipoCirurgia());
        tipoCirurgia.setSelection(a);

        a = spinnerDaMeATuaPosicao(especialidadeCirurgica.getAdapter(),
                c.getEspecialidade());
        especialidadeCirurgica.setSelection(a);

        a = spinnerDaMeATuaPosicao(lateralidade.getAdapter(),
                c.getLateralidade());
        lateralidade.setSelection(a);

        a = spinnerDaMeATuaPosicao(classificacaoASA.getAdapter(),
                c.getClassifASA());
        classificacaoASA.setSelection(a);

        a = spinnerDaMeATuaPosicao(destinoDoente.getAdapter(),
                c.getDestinoDoente());
        destinoDoente.setSelection(a);

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

        final Cirurgia p = HomeActivity.getCirurgia();
        Cirurgia ci = new Cirurgia();
        ci.setEspecialidade(especialidadeCirurgica.getSelectedItem()
                .toString());
        ci.setTipoCirurgia(tipoCirurgia.getSelectedItem().toString());
        ci.setIdSala(p.getIdSala());
        ci.setIdUtente(p.getIdUtente());
        ci.setIdEquipa(p.getIdEquipa());
        ci.setLateralidade(lateralidade.getSelectedItem().toString());
        ci.setClassifASA(classificacaoASA.getSelectedItem()
                .toString());

        ci.setDestinoDoente(destinoDoente.getSelectedItem().toString());
        ci.setCirurgia(cirurgia.getText().toString());
        ci.setInfoRelevante(informacoesRelevantes.getText().toString());
        // String datad = (String) data.getText();
        // // String[] dataF = datad.split("-");
        // // int dia = Integer.parseInt(dataF[0]);
        // // int mes = Integer.parseInt(dataF[1]);
        // // int ano = Integer.parseInt(dataF[2]);
        // int hora;
        // int minuto;
        // int segundo;
        // java.sql.Time horas = null;
        // //
        // // java.util.Date dataFinal = new Date(dia, mes, ano);
        // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Date inputDate = null;
        // try {
        // inputDate = dateFormat.parse(datad);
        // } catch (java.text.ParseException e) {
        // e.printStackTrace();
        // }
        ci.setData(data.getText().toString());

        // String hor = (String) horaCirurgia.getText();
        // String[] horCirurgia = hor.split(":");
        // if (horCirurgia.length == 2) {
        // hora = Integer.parseInt(horCirurgia[0]);
        // minuto = Integer.parseInt(horCirurgia[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horCirurgia.length == 3) {
        // hora = Integer.parseInt(horCirurgia[0]);
        // minuto = Integer.parseInt(horCirurgia[1]);
        // segundo = Integer.parseInt(horCirurgia[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHora(horaCirurgia.getText().toString());

        // String horaChamU = (String) horaChamadaUtente.getText();
        // String[] horaChamada = horaChamU.split(":");
        // if (horaChamada.length == 2) {
        // hora = Integer.parseInt(horaChamada[0]);
        // minuto = Integer.parseInt(horaChamada[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaChamada.length == 3) {
        // hora = Integer.parseInt(horaChamada[0]);
        // minuto = Integer.parseInt(horaChamada[1]);
        // segundo = Integer.parseInt(horaChamada[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraChamadaUtente(horaChamadaUtente.getText().toString());

        // String horaEntB = (String) horaEntradaBO.getText();
        // String[] horaEntBo = horaEntB.split(":");
        // if (horaEntBo.length == 2) {
        // hora = Integer.parseInt(horaEntBo[0]);
        // minuto = Integer.parseInt(horaEntBo[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaEntBo.length == 3) {
        // hora = Integer.parseInt(horaEntBo[0]);
        // minuto = Integer.parseInt(horaEntBo[1]);
        // segundo = Integer.parseInt(horaEntBo[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraEntradaBlocoOperatorio(horaEntradaBO.getText()
                .toString());

        // String horaSaidaB = (String) horaSaidaBO.getText();
        // String[] horaSaidaBo = horaSaidaB.split(":");
        // if (horaSaidaBo.length == 2) {
        // hora = Integer.parseInt(horaSaidaBo[0]);
        // minuto = Integer.parseInt(horaSaidaBo[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaSaidaBo.length == 3) {
        // hora = Integer.parseInt(horaSaidaBo[0]);
        // minuto = Integer.parseInt(horaSaidaBo[1]);
        // segundo = Integer.parseInt(horaSaidaBo[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraSaideBlocoOperatorio(horaSaidaBO.getText().toString());
        //
        // String horaEntSala = (String) horaEntradaSala.getText();
        // String[] horaEntSa = horaEntSala.split(":");
        // if (horaEntSa.length == 2) {
        // hora = Integer.parseInt(horaEntSa[0]);
        // minuto = Integer.parseInt(horaEntSa[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaEntSa.length == 3) {
        // hora = Integer.parseInt(horaEntSa[0]);
        // minuto = Integer.parseInt(horaEntSa[1]);
        // segundo = Integer.parseInt(horaEntSa[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraEntradaSala(horaEntradaSala.getText().toString());

        // String horaSaiaSala = (String) horaSaidaSala.getText();
        // String[] horaSaidaSa = horaSaiaSala.split(":");
        // if (horaSaidaSa.length == 2) {
        // hora = Integer.parseInt(horaSaidaSa[0]);
        // minuto = Integer.parseInt(horaSaidaSa[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaSaidaSa.length == 3) {
        // hora = Integer.parseInt(horaSaidaSa[0]);
        // minuto = Integer.parseInt(horaSaidaSa[1]);
        // segundo = Integer.parseInt(horaSaidaSa[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraSaidaSala(horaSaidaSala.getText().toString());

        // String horaIAnestesia = (String)
        // horaInicioAnestesia.getText();
        // String[] horaIAnest = horaIAnestesia.split(":");
        // if (horaIAnest.length == 2) {
        // hora = Integer.parseInt(horaIAnest[0]);
        // minuto = Integer.parseInt(horaIAnest[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaIAnest.length == 3) {
        // hora = Integer.parseInt(horaIAnest[0]);
        // minuto = Integer.parseInt(horaIAnest[1]);
        // segundo = Integer.parseInt(horaIAnest[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraInicioAnestesia(horaInicioAnestesia.getText()
                .toString());

        // String horaFAnestesia = (String) horaFimAnestesia.getText();
        // String[] horaFAnest = horaFAnestesia.split(":");
        // if (horaFAnest.length == 2) {
        // hora = Integer.parseInt(horaFAnest[0]);
        // minuto = Integer.parseInt(horaFAnest[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaFAnest.length == 3) {
        // hora = Integer.parseInt(horaFAnest[0]);
        // minuto = Integer.parseInt(horaFAnest[1]);
        // segundo = Integer.parseInt(horaFAnest[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraFimAnestesia(horaFimAnestesia.getText().toString());
        //
        // String horaICirurgia = (String) horaInicioCirurgia.getText();
        // String[] horaICir = horaICirurgia.split(":");
        // if (horaICir.length == 2) {
        // hora = Integer.parseInt(horaICir[0]);
        // minuto = Integer.parseInt(horaICir[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaICir.length == 3) {
        // hora = Integer.parseInt(horaICir[0]);
        // minuto = Integer.parseInt(horaICir[1]);
        // segundo = Integer.parseInt(horaICir[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraInicioCirurgia(horaInicioCirurgia.getText()
                .toString());

        // String horaFCirurgia = (String) horaFimCirurgia.getText();
        // String[] horaFCir = horaFCirurgia.split(":");
        // if (horaFCir.length == 2) {
        // hora = Integer.parseInt(horaFCir[0]);
        // minuto = Integer.parseInt(horaFCir[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaICir.length == 3) {
        // hora = Integer.parseInt(horaFCir[0]);
        // minuto = Integer.parseInt(horaFCir[1]);
        // segundo = Integer.parseInt(horaFCir[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraFimCirurgia(horaFimCirurgia.getText().toString());

        // String horaEntRecobro = (String)
        // horaEntradaRecobro.getText();
        // String[] horaEntRec = horaEntRecobro.split(":");
        // if (horaEntRec.length == 2) {
        // hora = Integer.parseInt(horaEntRec[0]);
        // minuto = Integer.parseInt(horaEntRec[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaEntRec.length == 3) {
        // hora = Integer.parseInt(horaEntRec[0]);
        // minuto = Integer.parseInt(horaEntRec[1]);
        // segundo = Integer.parseInt(horaEntRec[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraEntradaRecobro(horaEntradaRecobro.getText()
                .toString());

        // String horaSaidRecobro = (String) horaSaidaRecobro.getText();
        // String[] horaSaiRec = horaSaidRecobro.split(":");
        // if (horaSaiRec.length == 2) {
        // hora = Integer.parseInt(horaSaiRec[0]);
        // minuto = Integer.parseInt(horaSaiRec[1]);
        // horas = new java.sql.Time(hora, minuto, 00);
        // } else if (horaSaiRec.length == 3) {
        // hora = Integer.parseInt(horaSaiRec[0]);
        // minuto = Integer.parseInt(horaSaiRec[1]);
        // segundo = Integer.parseInt(horaSaiRec[2]);
        // horas = new java.sql.Time(hora, minuto, segundo);
        // }

        ci.setHoraFimRecobro(horaSaidaRecobro.getText().toString());

        ci.setId(idCirurgia);
        BlocoComSala blocoCS = (BlocoComSala) sala.getSelectedItem();
        ci.setIdSala(blocoCS.getSala().getId());
        cir = ci;
        try {
            new atualizarCirurgia().execute(ci);
        } catch (Exception rn) {
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dados_cirurgia, menu);

        return true;
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
        return super.onOptionsItemSelected(item);
    }

    public void toggleMenu(View v) {
        this.root.toggleMenu();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String estado = root.getState().toString();
        if (estado.equals("OPEN"))
            this.root.toggleMenu();
        return super.onTouchEvent(event);
    }

    public void preencher(Cirurgia c) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage("Pretende Retroceder sem guardar?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


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

            ringProgressDialog = new ProgressDialog(DadosCirurgia.this);
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
                        idCirurgia, token);

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
                    : "Cirurgoa Não Alterada!");
            if (result) {
                HomeActivity.setCirurgia(cir);

                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG)
                        .show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Erro Atualizar Cirurgia - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
            }
            ringProgressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class getBlocosComSala extends
            AsyncTask<String, Void, ArrayList<BlocoComSala>> {
        @Override
        protected void onPreExecute() {

            ringProgressDialog = new ProgressDialog(DadosCirurgia.this);
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
                        getBaseContext(), android.R.layout.simple_list_item_1,
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
                int a = spinnerDaMeATuaPosicaoBloco(adaptadorBlococomSala,
                        HomeActivity.getCirurgia().getIdSala());
                sala.setSelection(a);

                ringProgressDialog.dismiss();

            } else {
                Toast.makeText(getApplicationContext(), "Erro Get Blocos Com Sala - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
