package pt.mobilesgmc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import pt.mobilesgmc.acessosVenosos.AdapterAcessosVenosos;
import pt.mobilesgmc.adminSangue.AdapterAdministracaoSangue;
import pt.mobilesgmc.balancoHidrico.AdapterBalancoHidrico;
import pt.mobilesgmc.biopsia.AdapterBiopsia;
import pt.mobilesgmc.dreanagem.AdapterDrenagem;
import pt.mobilesgmc.medicacaoAdministrada.AdapterMedicacaoAdministrada;
import pt.mobilesgmc.modelo.AcessoVenoso;
import pt.mobilesgmc.modelo.AdministracaoSangue;
import pt.mobilesgmc.modelo.BalancoHidrico;
import pt.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import pt.mobilesgmc.modelo.Drenagem;
import pt.mobilesgmc.modelo.Eliminacao;
import pt.mobilesgmc.modelo.ExceptionLog;
import pt.mobilesgmc.modelo.MedicacaoAdministrada;
import pt.mobilesgmc.modelo.PecaBiopsia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.SinaisVitais;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.sinaisVitais.AdapterSinaisVitais;

public class DadosINtraOperatorioActivity extends ActionBarActivity {

	String token;
	ProgressDialog ringProgressDialog = null;
    private Spinner spinner_tipoAnestesia;
    private EditText editText_TET;
    private EditText editText_ML;
    private EditText editText_AgulhaCalibre;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private Context contexto;
    private static DadosIntraoperatorioFinal dadosFinal;

    //Acessos Venosos
    private ListView listView_AcessosVenosos;
    private AdapterAcessosVenosos adapterAcessosVenosos;
    private ImageView adicionarAcessosVenosos;
    private ImageView expandCollapseAcessosVenosos;


    //Sinais Vitais
    private ListView listView_SinaisVitais;
    private AdapterSinaisVitais adapterSinaisVitais;
    private TextView horaSinalVital;
    private ImageView adicionarSinalVital;
    private ImageView expandCollapseSinaisVitais;

    //Sinais Vitais
    private ListView listView_MedicacaoAdministrada;
    private AdapterMedicacaoAdministrada adapterMedicacaoAdministrada;
    private ImageView adicionarMedicacao;
    private ImageView expandCollapseMedicacao;

    //Administracao Sangue
    private ListView listView_AdminSangue;
    private AdapterAdministracaoSangue adapterAdministracaoSangue;
    private TextView horaAdminSangue;
    private ImageView adicionarAdminSangue;
    private ImageView expandCollapseAdminSangue;
    private LinearLayout layoutObsAdminSangue;
    private EditText obsAdminSangue;

    //ELIMINACAO
    private ImageView expandCollapseEliminacao;
    private LinearLayout linearLayoutDrenagemVesical;
    private LinearLayout linearLayoutDrenagemNasogastrica;

    //DRENAGEM VESICAL
    private ListView listView_DrenagemVesical;
    private AdapterDrenagem adaptadorDrenagemVesical;
    private ImageView adicionarDrenagemVesical;
    private ImageView expandeCollapseDrenagemVesical;
    private LinearLayout layoutDadosDrenagemVesical;
    private EditText calibreDrenagemVesical;
    private Spinner spinner_tipoDrenagemVesical;

    //DRENAGEM NASOGASTRICA
    private ListView listView_DrenagemNasogastrica;
    private AdapterDrenagem adaptadorDrenagemNasogastrica;
    private ImageView adicionarDrenagemNasogastrica;
    private ImageView expandeCollapseDrenagemNasogastrica;
    private LinearLayout layoutDadosDrenagemNasogastrica;
    private EditText calibreDrenagemNasogastrica;
    private Spinner spinner_tipoDrenagemNasogastrica;

    //Sinais Vitais & BALANCO HIDRICO
    private static ListView listView_BalancoHidrico;
    private static AdapterBalancoHidrico adapterBalancoHidrico;
    private ImageView expandCollapseBalancoHidrico;


    //SEM NOME
    private Spinner spinner_posicaoOperatoria;
    private EditText editText_alivioZonasPressao;
    private Spinner spinner_localPosicaoOperatoria;
    private RadioButton radioButton_mantatermica_sim;
    private RadioButton radioButton_mantatermica_nao;
    private RadioGroup radioGroup_mantatermica;
    private Spinner spinner_localMantaTermica;
    private LinearLayout linearLayout_mantaTermica;
    private RadioButton radioButton_Garrote_sim;
    private RadioButton radioButton_Garrote_nao;
    private RadioGroup radioGroup_Garrote;
    private EditText editText_GarrotePressao;
    private Spinner spinner_localizacaoGarrote;
    private LinearLayout linearLayout_garrote;
    private TextView textView_Garrote_horaInicio;
    private TextView textView_Garrote_horaFim;
    private RadioButton radioButton_Electrodo_sim;
    private RadioButton radioButton_Electrodo_nao;
    private RadioGroup radioGroup_Electrodo;
    private Spinner spinner_localizacaoElectrodo;
    private LinearLayout linearLayout_electrodo;

    //PECA BIOPSIA
    private ListView listView_PecaBiopsia;
    private AdapterBiopsia adapterBiopsia;
    private ImageView expandCollapseBiopsia;
    private ImageView addBiopsia;
    private EditText nFrascosBiopsia;
    private LinearLayout linearLayoutBiopsia;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Intra");
		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

        spinner_tipoAnestesia = (Spinner) findViewById(R.id.spinner_DadosIntra_TipoAnestesia);
        editText_TET = (EditText) findViewById(R.id.editText_DadosIntra_TET);
        editText_ML = (EditText) findViewById(R.id.editText_DadosIntra_ML);
        editText_AgulhaCalibre = (EditText) findViewById(R.id.editText_DadosIntra_AgulhaPLCalibre);

        contexto = this;


        new verificaIntraOperatorio().execute();

	}

    public void constroiAtividade()
    {
        acessosVenosos();
        sinaisVitais();
        medicacaoAdministrada();
        administracaoSangue();
        eliminacao();
        balancoHidrico();
        semNome();
        biopsia();
    }

    public void acessosVenosos()
    {
        listView_AcessosVenosos = (ListView) findViewById(R.id.listView_DadosIntra_AcessosVenosos);

        if(dadosFinal.getListaAcessoVenoso()==null)
            dadosFinal.setListaAcessoVenoso(new ArrayList<AcessoVenoso>());
        adapterAcessosVenosos = new AdapterAcessosVenosos(this,dadosFinal.getListaAcessoVenoso());
        listView_AcessosVenosos.setAdapter(adapterAcessosVenosos);
        listView_AcessosVenosos.setScrollContainer(false);
        listView_AcessosVenosos.setVisibility(View.GONE);
        setListViewHeightBasedOnChildren(listView_AcessosVenosos);


        adicionarAcessosVenosos = (ImageView) findViewById(R.id.imageView_DadosIntra_addAcessoVenoso);
        adicionarAcessosVenosos.setClickable(true);
        adicionarAcessosVenosos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcessoVenoso a = new AcessoVenoso();
                a.setLocalizacaoAcessoVenoso("");
                a.setCalibreAcessoVenoso(0);
                a.setTipoAcessoVenoso("");
                a.setIdIntra(dadosFinal.getDados().getId());
                adapterAcessosVenosos.add(a);
                setListViewHeightBasedOnChildren(listView_AcessosVenosos);
            }
        });

        if(dadosFinal.getListaAcessoVenoso().size()!=0)
            adicionarAcessosVenosos.setVisibility(View.GONE);

        expandCollapseAcessosVenosos = (ImageView) findViewById(R.id.imageView_DadosIntra_expandCollapseAcessosVenosos);
        expandCollapseAcessosVenosos.setClickable(true);
        expandCollapseAcessosVenosos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_AcessosVenosos.getVisibility() == View.VISIBLE){
                    expandCollapseAcessosVenosos.setBackgroundResource(R.drawable.ic_collapse);
                    listView_AcessosVenosos.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_AcessosVenosos);
                    adicionarAcessosVenosos.setVisibility(View.GONE);
                }
                else {
                    expandCollapseAcessosVenosos.setBackgroundResource(R.drawable.ic_expand);
                    listView_AcessosVenosos.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_AcessosVenosos);
                    adicionarAcessosVenosos.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void sinaisVitais()
    {
        listView_SinaisVitais = (ListView) findViewById(R.id.listView_DadosIntra_SinaisVitais);

        if(dadosFinal.getListaSinais()==null)
            dadosFinal.setListaSinais(new ArrayList<SinaisVitais>());
        setListViewHeightBasedOnChildren(listView_SinaisVitais);
        adapterSinaisVitais = new AdapterSinaisVitais(this,dadosFinal.getListaSinais());
        listView_SinaisVitais.setAdapter(adapterSinaisVitais);
        listView_SinaisVitais.setScrollContainer(false);
        listView_SinaisVitais.setVisibility(View.GONE);
        adicionarSinalVital = (ImageView) findViewById(R.id.imageView_DadosIntra_AddSinalVital);
        if(dadosFinal.getListaSinais().size()!=0)
            adicionarSinalVital.setVisibility(View.GONE);
        expandCollapseSinaisVitais = (ImageView) findViewById(R.id.imageView_DadosIntra_ShowHideSinaisVitais);
        expandCollapseSinaisVitais.setClickable(true);
        expandCollapseSinaisVitais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_SinaisVitais.getVisibility() == View.VISIBLE){
                    expandCollapseSinaisVitais.setBackgroundResource(R.drawable.ic_collapse);
                    listView_SinaisVitais.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_SinaisVitais);
                    adicionarSinalVital.setVisibility(View.GONE);
                }
                else {
                    expandCollapseSinaisVitais.setBackgroundResource(R.drawable.ic_expand);
                    listView_SinaisVitais.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_SinaisVitais);
                    adicionarSinalVital.setVisibility(View.VISIBLE);
                }
            }
        });

        adicionarSinalVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinaisVitais s = new SinaisVitais();
                s.setTemp(0);
                s.setSpo2(0);
                s.setTaMax(0);
                s.setTaMin(0);
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                String hr = (mHour + ":"
                        + mMinute + ":00");
                s.setHora(hr);
                s.setFc(0);
                s.setIdIntraOperatorio(dadosFinal.getDados().getId());
                adapterSinaisVitais.add(s);
                setListViewHeightBasedOnChildren(listView_SinaisVitais);

            }
        });

    }

    public void medicacaoAdministrada()
    {
        listView_MedicacaoAdministrada = (ListView) findViewById(R.id.listView_DadosIntra_MedicacaoAdmin);



        if(dadosFinal.getListaMedicacao()==null)
            dadosFinal.setListaMedicacao(new ArrayList<MedicacaoAdministrada>());
        setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);
        adapterMedicacaoAdministrada = new AdapterMedicacaoAdministrada(this,dadosFinal.getListaMedicacao());
        listView_MedicacaoAdministrada.setAdapter(adapterMedicacaoAdministrada);
        listView_MedicacaoAdministrada.setScrollContainer(false);
        listView_MedicacaoAdministrada.setVisibility(View.GONE);
        adicionarMedicacao = (ImageView) findViewById(R.id.imageView_DadosIntra_AddMedicacao);
        if(dadosFinal.getListaMedicacao().size()!=0)
            adicionarMedicacao.setVisibility(View.GONE);
        expandCollapseMedicacao = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandCollapseMedicacao);
        expandCollapseMedicacao.setClickable(true);
        expandCollapseMedicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_MedicacaoAdministrada.getVisibility() == View.VISIBLE){
                    expandCollapseMedicacao.setBackgroundResource(R.drawable.ic_collapse);
                    listView_MedicacaoAdministrada.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);
                    adicionarMedicacao.setVisibility(View.GONE);
                }
                else {
                    expandCollapseMedicacao.setBackgroundResource(R.drawable.ic_expand);
                    listView_MedicacaoAdministrada.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);
                    adicionarMedicacao.setVisibility(View.VISIBLE);
                }
            }
        });

        adicionarMedicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicacaoAdministrada m = new MedicacaoAdministrada();
                m.setFarmaco("");
                m.setVia("");
                m.setDose(0.0);
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                String hr = (mHour + ":"
                        + mMinute + ":00");
                m.setHora(hr);
                m.setIdIntraOperatorio(dadosFinal.getDados().getId());
                adapterMedicacaoAdministrada.add(m);
                setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);

            }
        });
    }

    public void administracaoSangue()
    {

        layoutObsAdminSangue = (LinearLayout) findViewById(R.id.linearLayout_DadosIntra_ObsAdminSangue);
        layoutObsAdminSangue.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        obsAdminSangue = (EditText) findViewById(R.id.editText_DadosIntra_ObsAdminSangue);
        //


        listView_AdminSangue = (ListView) findViewById(R.id.listView_DadosIntra_AdminSangue);



        if(dadosFinal.getAdminSangue()==null)
            dadosFinal.setAdminSangue(new ArrayList<AdministracaoSangue>());
        setListViewHeightBasedOnChildren(listView_AdminSangue);
        adapterAdministracaoSangue = new AdapterAdministracaoSangue(this,dadosFinal.getAdminSangue());
        listView_AdminSangue.setAdapter(adapterAdministracaoSangue);
        listView_AdminSangue.setScrollContainer(false);
        listView_AdminSangue.setVisibility(View.GONE);
        adicionarAdminSangue = (ImageView) findViewById(R.id.imageView_DadosIntra_AddAdminSangue);
        if(dadosFinal.getAdminSangue().size()!=0)
            adicionarAdminSangue.setVisibility(View.GONE);
        expandCollapseAdminSangue = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandeCollapseAdminSangue);
        expandCollapseAdminSangue.setClickable(true);
        expandCollapseAdminSangue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_AdminSangue.getVisibility() == View.VISIBLE){
                    expandCollapseAdminSangue.setBackgroundResource(R.drawable.ic_collapse);
                    listView_AdminSangue.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_AdminSangue);
                    adicionarAdminSangue.setVisibility(View.GONE);
                    layoutObsAdminSangue.setVisibility(View.GONE);
                }
                else {
                    expandCollapseAdminSangue.setBackgroundResource(R.drawable.ic_expand);
                    listView_AdminSangue.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_AdminSangue);
                    adicionarAdminSangue.setVisibility(View.VISIBLE);
                    layoutObsAdminSangue.setVisibility(View.VISIBLE);

                }
            }
        });

        adicionarAdminSangue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdministracaoSangue s = new AdministracaoSangue();
                s.setFc(0);
                s.setSpo2(0);
                s.setTaMax(0);
                s.setTaMin(0);
                s.setTipo(" ");
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                String hr = (mHour + ":"
                        + mMinute + ":00");
                s.setHora(hr);
                s.setFc(0);
                s.setIdIntraOperatorio(dadosFinal.getDados().getId());
                if(existsAdminSangue(s)==-1) {
                    adapterAdministracaoSangue.add(s);
                    setListViewHeightBasedOnChildren(listView_AdminSangue);
                    refreshBalancos();
                }

            }
        });



    }

    public void eliminacao()
    {
        linearLayoutDrenagemVesical = (LinearLayout) findViewById(R.id.linearLayout_DadosIntra_Vesical);
        linearLayoutDrenagemVesical.setVisibility(View.GONE);
        linearLayoutDrenagemNasogastrica = (LinearLayout) findViewById(R.id.linearLayout_DadosIntra_Nasogastrica);
        linearLayoutDrenagemNasogastrica.setVisibility(View.GONE);
        expandCollapseEliminacao = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandeCollapseEliminacao);
        expandCollapseEliminacao.setClickable(true);
        expandCollapseEliminacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutDrenagemVesical.getVisibility() == View.VISIBLE){
                    expandCollapseEliminacao.setBackgroundResource(R.drawable.ic_collapse);
                    linearLayoutDrenagemVesical.setVisibility(View.GONE);
                    linearLayoutDrenagemNasogastrica.setVisibility(View.GONE);
                }
                else {
                    expandCollapseEliminacao.setBackgroundResource(R.drawable.ic_expand);
                    linearLayoutDrenagemVesical.setVisibility(View.VISIBLE);
                    linearLayoutDrenagemNasogastrica.setVisibility(View.VISIBLE);
                }
            }
        });

        cataterismoVesical();
        cataterismoNasogastrico();
    }

    public void cataterismoVesical()
    {

        layoutDadosDrenagemVesical = (LinearLayout) findViewById(R.id.linearLayout_DadosIntra_DadosVesical);
        layoutDadosDrenagemVesical.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        calibreDrenagemVesical = (EditText) findViewById(R.id.editText_DadosIntra_calibreSondaVesical);
        spinner_tipoDrenagemVesical = (Spinner) findViewById(R.id.spinner_DadosIntra_tipoSondaVesical);
        //


        listView_DrenagemVesical = (ListView) findViewById(R.id.listView_DadosIntra_dreanagemVesical);

        if(dadosFinal.getListaDrenagemVesical()==null)
        dadosFinal.setListaDrenagemVesical(new ArrayList<Drenagem>());

        setListViewHeightBasedOnChildren(listView_DrenagemVesical);
        adaptadorDrenagemVesical = new AdapterDrenagem(this,dadosFinal.getListaDrenagemVesical());
        listView_DrenagemVesical.setAdapter(adaptadorDrenagemVesical);
        listView_DrenagemVesical.setScrollContainer(false);
        listView_DrenagemVesical.setVisibility(View.GONE);
        adicionarDrenagemVesical = (ImageView) findViewById(R.id.imageView_DadosIntra_AddVesical);
        if(dadosFinal.getListaDrenagemVesical().size()!=0)
            adicionarDrenagemVesical.setVisibility(View.GONE);
        expandeCollapseDrenagemVesical = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandeCollapseVesical);
        expandeCollapseDrenagemVesical.setClickable(true);
        expandeCollapseDrenagemVesical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_DrenagemVesical.getVisibility() == View.VISIBLE){
                    expandeCollapseDrenagemVesical.setBackgroundResource(R.drawable.ic_collapse);
                    listView_DrenagemVesical.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_DrenagemVesical);
                    adicionarDrenagemVesical.setVisibility(View.GONE);
                    layoutDadosDrenagemVesical.setVisibility(View.GONE);
                }
                else {
                    expandeCollapseDrenagemVesical.setBackgroundResource(R.drawable.ic_expand);
                    listView_DrenagemVesical.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_DrenagemVesical);
                    adicionarDrenagemVesical.setVisibility(View.VISIBLE);
                    layoutDadosDrenagemVesical.setVisibility(View.VISIBLE);

                }
            }
        });

        adicionarDrenagemVesical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drenagem s = new Drenagem();
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                String hr = (mHour + ":"
                        + mMinute + ":00");
                s.setHora(hr);
                s.setDrenagem(0);
                s.setCaracteristicas("");

                adaptadorDrenagemVesical.add(s);
                setListViewHeightBasedOnChildren(listView_DrenagemVesical);

            }
        });

    }

    public void cataterismoNasogastrico()
    {



        layoutDadosDrenagemNasogastrica = (LinearLayout) findViewById(R.id.linearLayout_DadosIntra_DadosNasogastrica);
        layoutDadosDrenagemNasogastrica.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        calibreDrenagemNasogastrica = (EditText) findViewById(R.id.editText_DadosIntra_calibreSondaNasogastrica);
        spinner_tipoDrenagemNasogastrica = (Spinner) findViewById(R.id.spinner_DadosIntra_tipoSondaNasogastrica);
        //


        listView_DrenagemNasogastrica = (ListView) findViewById(R.id.listView_DadosIntra_dreanagemNasogastrica);

        if(dadosFinal.getListaDrenagemNasogastrica()==null)
        dadosFinal.setListaDrenagemNasogastrica(new ArrayList<Drenagem>());

        setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);
        adaptadorDrenagemNasogastrica = new AdapterDrenagem(this,dadosFinal.getListaDrenagemNasogastrica());
        listView_DrenagemNasogastrica.setAdapter(adaptadorDrenagemNasogastrica);
        listView_DrenagemNasogastrica.setScrollContainer(false);
        listView_DrenagemNasogastrica.setVisibility(View.GONE);
        adicionarDrenagemNasogastrica = (ImageView) findViewById(R.id.imageView_DadosIntra_AddNasogastrica);
        if(dadosFinal.getListaDrenagemNasogastrica().size()!=0)
            adicionarDrenagemNasogastrica.setVisibility(View.GONE);
        expandeCollapseDrenagemNasogastrica = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandeCollapseNasogastrica);
        expandeCollapseDrenagemNasogastrica.setClickable(true);
        expandeCollapseDrenagemNasogastrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_DrenagemNasogastrica.getVisibility() == View.VISIBLE){
                    expandeCollapseDrenagemNasogastrica.setBackgroundResource(R.drawable.ic_collapse);
                    listView_DrenagemNasogastrica.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);
                    adicionarDrenagemNasogastrica.setVisibility(View.GONE);
                    layoutDadosDrenagemNasogastrica.setVisibility(View.GONE);
                }
                else {
                    expandeCollapseDrenagemNasogastrica.setBackgroundResource(R.drawable.ic_expand);
                    listView_DrenagemNasogastrica.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);
                    adicionarDrenagemNasogastrica.setVisibility(View.VISIBLE);
                    layoutDadosDrenagemNasogastrica.setVisibility(View.VISIBLE);

                }
            }
        });

        adicionarDrenagemNasogastrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drenagem s = new Drenagem();
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                String hr = (mHour + ":"
                        + mMinute + ":00");
                s.setHora(hr);
                s.setDrenagem(0);
                s.setCaracteristicas("");

                adaptadorDrenagemNasogastrica.add(s);
                setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);

            }
        });


    }

    public void balancoHidrico()
    {

        listView_BalancoHidrico = (ListView) findViewById(R.id.listView_DadosIntra_BalancoHidrico);


        setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);

        if(dadosFinal.getListaBalancos()==null)
            dadosFinal.setListaBalancos(new ArrayList<BalancoHidrico>());

        adapterBalancoHidrico = new AdapterBalancoHidrico(this,dadosFinal.getListaBalancos());
        listView_BalancoHidrico.setAdapter(adapterBalancoHidrico);
        listView_BalancoHidrico.setScrollContainer(false);
        listView_BalancoHidrico.setVisibility(View.GONE);

        expandCollapseBalancoHidrico = (ImageView) findViewById(R.id.imageView_DadosIntra_ExpandCollapseBalancoHidrico);
        expandCollapseBalancoHidrico.setClickable(true);
        expandCollapseBalancoHidrico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_BalancoHidrico.getVisibility() == View.VISIBLE){
                    expandCollapseBalancoHidrico.setBackgroundResource(R.drawable.ic_collapse);
                    listView_BalancoHidrico.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_BalancoHidrico);
                }
                else {
                    expandCollapseBalancoHidrico.setBackgroundResource(R.drawable.ic_expand);
                    listView_BalancoHidrico.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_BalancoHidrico);
                }
            }
        });


    }

    public void semNome()
    {

        spinner_posicaoOperatoria = (Spinner) findViewById(R.id.spinner_DadosIntra_PosicaoOperatoria);
        editText_alivioZonasPressao = (EditText) findViewById(R.id.editText_DadosIntra_AlivioZonasPressao);
        spinner_localPosicaoOperatoria = (Spinner) findViewById(R.id.spinner_DadosIntra_LocalAlivioZonaPressao);
        radioButton_mantatermica_sim = (RadioButton) findViewById(R.id.radioButton_MantaTermicaProtecaoSim);
        radioButton_mantatermica_nao = (RadioButton) findViewById(R.id.radioButton_MantaTermicaProtecaoNao);
        spinner_localMantaTermica = (Spinner) findViewById(R.id.spinner_DadosIntra_localMantaTermica);
        linearLayout_mantaTermica = (LinearLayout) findViewById(R.id.linearLayout_mantaTermica);

        radioButton_mantatermica_nao.setSelected(true);
        radioButton_mantatermica_nao.setChecked(true);

        linearLayout_mantaTermica.setVisibility(View.GONE);
        radioGroup_mantatermica = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup_mantatermica.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radioButton_MantaTermicaProtecaoSim)
                    linearLayout_mantaTermica.setVisibility(View.VISIBLE);
                else
                    linearLayout_mantaTermica.setVisibility(View.GONE);

            }
        });


        radioButton_Garrote_sim = (RadioButton) findViewById(R.id.radioButton_GarroteSim);
        radioButton_Garrote_nao = (RadioButton) findViewById(R.id.radioButton_GarroteNao);
        radioGroup_Garrote = (RadioGroup) findViewById(R.id.radioGroup_Garrote);
        editText_GarrotePressao = (EditText) findViewById(R.id.editText_GarrotePressao);
        spinner_localizacaoGarrote = (Spinner) findViewById(R.id.spinner_DadosIntra_localGarrote);
        linearLayout_garrote = (LinearLayout) findViewById(R.id.linearLayout_Garrote);
        textView_Garrote_horaInicio = (TextView) findViewById(R.id.textView_GarroteHoraInicio);
        textView_Garrote_horaFim = (TextView) findViewById(R.id.textView_GarroteHoraFim);

        radioButton_Garrote_nao.setSelected(true);
        radioButton_Garrote_nao.setChecked(true);
        linearLayout_garrote.setVisibility(View.GONE);

        radioGroup_Garrote.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton_GarroteSim)
                    linearLayout_garrote.setVisibility(View.VISIBLE);
                else
                    linearLayout_garrote.setVisibility(View.GONE);
            }
        });


        textView_Garrote_horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView_Garrote_horaInicio = (TextView) v;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(contexto,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                String hr = (hourOfDay + ":"
                                        + minute + ":00");
                                textView_Garrote_horaInicio.setText(hr);
                            }

                        }, mHour, mMinute, true);

                dpd.show();
            }
        });

        textView_Garrote_horaFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView_Garrote_horaFim = (TextView) v;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Date Picker Dialog
                TimePickerDialog dpd = new TimePickerDialog(contexto,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                String hr = (hourOfDay + ":"
                                        + minute + ":00");
                                textView_Garrote_horaFim.setText(hr);
                            }

                        }, mHour, mMinute, true);

                dpd.show();
            }
        });



        radioButton_Electrodo_sim = (RadioButton) findViewById(R.id.radioButton_Electrodo_Sim);
        radioButton_Electrodo_nao = (RadioButton) findViewById(R.id.radioButton_Electrodo_Nao);
        radioGroup_Electrodo = (RadioGroup) findViewById(R.id.radioGroup_electrodoNeutro);
        spinner_localizacaoElectrodo = (Spinner) findViewById(R.id.spinner_DadosIntra_localizacaoElectrodo);
        linearLayout_electrodo = (LinearLayout) findViewById(R.id.linearLayout_Electrodo);

        radioButton_Electrodo_nao.setSelected(true);
        radioButton_Electrodo_nao.setChecked(true);
        linearLayout_electrodo.setVisibility(View.GONE);

        radioGroup_Electrodo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton_Electrodo_Sim)
                    linearLayout_electrodo.setVisibility(View.VISIBLE);
                else
                    linearLayout_electrodo.setVisibility(View.GONE);
            }
        });


    }

    public void biopsia()
    {


        linearLayoutBiopsia = (LinearLayout) findViewById(R.id.linearLayoutBiopsia);
        linearLayoutBiopsia.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        nFrascosBiopsia = (EditText) findViewById(R.id.editText_Biopsia_nFrascos);
        //


        listView_PecaBiopsia = (ListView) findViewById(R.id.listViewBiopsia);
        setListViewHeightBasedOnChildren(listView_PecaBiopsia);


        if(dadosFinal.getListaPecaBiopsia()==null)
            dadosFinal.setListaPecaBiopsia(new ArrayList<PecaBiopsia>());

        adapterBiopsia = new AdapterBiopsia(this,dadosFinal.getListaPecaBiopsia());
        listView_PecaBiopsia.setAdapter(adapterBiopsia);
        listView_PecaBiopsia.setScrollContainer(false);
        listView_PecaBiopsia.setVisibility(View.GONE);
        addBiopsia = (ImageView) findViewById(R.id.imageView_addBiopsia);
        if(dadosFinal.getListaPecaBiopsia().size()!=0)
            addBiopsia.setVisibility(View.GONE);
        expandCollapseBiopsia = (ImageView) findViewById(R.id.imageView_expandCollapse_Biopsia);
        expandCollapseBiopsia.setClickable(true);
        expandCollapseBiopsia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_PecaBiopsia.getVisibility() == View.VISIBLE){
                    expandCollapseBiopsia.setBackgroundResource(R.drawable.ic_collapse);
                    listView_PecaBiopsia.setVisibility(View.GONE);
                    setListViewHeightBasedOnChildren(listView_PecaBiopsia);
                    addBiopsia.setVisibility(View.GONE);
                    linearLayoutBiopsia.setVisibility(View.GONE);
                }
                else {
                    expandCollapseBiopsia.setBackgroundResource(R.drawable.ic_expand);
                    listView_PecaBiopsia.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnChildren(listView_PecaBiopsia);
                    addBiopsia.setVisibility(View.VISIBLE);
                    linearLayoutBiopsia.setVisibility(View.VISIBLE);

                }
            }
        });

        addBiopsia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PecaBiopsia p = new PecaBiopsia();
                p.setDescricao("");
                p.setLaboratotio("");
                p.setIdIntra(dadosFinal.getDados().getId());
                adapterBiopsia.add(p);
                setListViewHeightBasedOnChildren(listView_PecaBiopsia);

            }
        });



    }


    public void preencherAtividade() {

        if(dadosFinal.getDados()!=null){
            if(dadosFinal.getDados().getTipoAnestesia()!=null)
                spinner_tipoAnestesia.setSelection(spinnerDaMeATuaPosicao(spinner_tipoAnestesia, dadosFinal.getDados().getTipoAnestesia()));

            editText_TET.setText(dadosFinal.getDados().getTet() + "");
            editText_ML.setText(dadosFinal.getDados().getMl() + "");
            editText_AgulhaCalibre.setText(dadosFinal.getDados().getCalibreAgulha() + "");

            if(dadosFinal.getDados().getObsAdminSangue()!=null)
                obsAdminSangue.setText(dadosFinal.getDados().getObsAdminSangue());

            if(dadosFinal.getDados().getPosicaoOperatoria()!=null)
                spinner_posicaoOperatoria.setSelection(spinnerDaMeATuaPosicao(spinner_posicaoOperatoria,dadosFinal.getDados().getPosicaoOperatoria()));


            if(dadosFinal.getDados().getAlivioZonapressao()!=null)
                editText_alivioZonasPressao.setText(dadosFinal.getDados().getAlivioZonapressao());

            if(dadosFinal.getDados().getLocalAlivioZonaPressao()!=null)
                spinner_localPosicaoOperatoria.setSelection(spinnerDaMeATuaPosicao(spinner_localPosicaoOperatoria,dadosFinal.getDados().getLocalAlivioZonaPressao()));

            if(dadosFinal.getDados().isMantaTermica())
            {
                radioButton_mantatermica_sim.setChecked(true);
                linearLayout_mantaTermica.setVisibility(View.VISIBLE);
                spinner_localMantaTermica.setSelection(spinnerDaMeATuaPosicao(spinner_localMantaTermica,dadosFinal.getDados().getLocalMantaTermica()));
            }

            if(dadosFinal.getDados().getPressaoGarrotePneumatico()>0.0)
            {
                radioButton_Garrote_sim.setChecked(true);
                linearLayout_garrote.setVisibility(View.VISIBLE);
                spinner_localizacaoGarrote.setSelection(spinnerDaMeATuaPosicao(spinner_localizacaoGarrote,dadosFinal.getDados().getLocalizacaoGarrotePneumatico()));
                editText_GarrotePressao.setText(dadosFinal.getDados().getPressaoGarrotePneumatico()+"");
                if(dadosFinal.getDados().getHoraInicioGarrotePneum()!=null)
                    textView_Garrote_horaInicio.setText(dadosFinal.getDados().getHoraInicioGarrotePneum());
                if(dadosFinal.getDados().getHoraFimGarrotePneum()!=null)
                    textView_Garrote_horaFim.setText(dadosFinal.getDados().getHoraFimGarrotePneum());
            }

            if(dadosFinal.getDados().isPlacaEletrodo())
            {
                radioButton_Electrodo_sim.setChecked(true);
                linearLayout_electrodo.setVisibility(View.VISIBLE);
                spinner_localizacaoElectrodo.setSelection(spinnerDaMeATuaPosicao(spinner_localizacaoElectrodo,dadosFinal.getDados().getLocalizacaoPlacaEletrodo()));
            }

            nFrascosBiopsia.setText(dadosFinal.getDados().getNumFrascosPecaBiopsia()+"");


        }

        if(dadosFinal.getListaEliminacao()==null)
            dadosFinal.setListaEliminacao(new ArrayList<Eliminacao>());
        if(dadosFinal.getListaEliminacao().size()>0)
        {
            for(int i=0; i<dadosFinal.getListaEliminacao().size(); i++)
            {
                if(dadosFinal.getListaEliminacao().get(i).getTipo().toLowerCase().equals("v"))
                {
                    calibreDrenagemVesical.setText(dadosFinal.getListaEliminacao().get(i).getCalibre()+"");
                    spinner_tipoDrenagemVesical.setSelection(spinnerDaMeATuaPosicao(spinner_tipoDrenagemVesical,dadosFinal.getListaEliminacao().get(i).getTipoSonda()));
                }
                else if(dadosFinal.getListaEliminacao().get(i).getTipo().toLowerCase().equals("n"))
                {
                    calibreDrenagemNasogastrica.setText(dadosFinal.getListaEliminacao().get(i).getCalibre()+"");
                    spinner_tipoDrenagemNasogastrica.setSelection(spinnerDaMeATuaPosicao(spinner_tipoDrenagemNasogastrica,dadosFinal.getListaEliminacao().get(i).getTipoSonda()));
                }
            }
        }








        Log.i("Acessos", dadosFinal.getListaAcessoVenoso().toString());



    }

    public int spinnerDaMeATuaPosicao(Spinner s, String valor)
    {
        int resultado = 0;
        if(valor !=null) {
            for (int i = 0; i < s.getAdapter().getCount(); i++) {
                if (s.getItemAtPosition(i).toString().toLowerCase().equals(valor.toLowerCase()))
                    resultado = i;
            }
        }

        return resultado;
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


    public static void refreshBalancos()
    {
        dadosFinal.getListaBalancos().clear();

        for(int i=0; i<dadosFinal.getAdminSangue().size(); i++)
        {
            adicionaOuAlteraAdminSangue(dadosFinal.getAdminSangue().get(i));

        }

        for(int i=0; i<dadosFinal.getListaDrenagemNasogastrica().size(); i++)
        {
            adicionaOuAlteraDrenagemNasogastrica(dadosFinal.getListaDrenagemNasogastrica().get(i));

        }

        for(int i=0; i<dadosFinal.getListaDrenagemVesical().size(); i++)
        {
            adicionaOuAlteraDrenagemVesical(dadosFinal.getListaDrenagemVesical().get(i));

        }

        adapterBalancoHidrico.notifyDataSetChanged();


    }

    public static void adicionaOuAlteraAdminSangue(AdministracaoSangue a)
    {
        int resultado = existsAdminSangue(a);
        if(resultado!=-1){
            dadosFinal.getListaBalancos().get(resultado).setValorAdministracaoSangue(a.getValorAdministracao());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(dadosFinal.getDados().getId());
            bal.setValorAdministracaoSangue(a.getValorAdministracao());
            bal.setHora(a.getHora());
            dadosFinal.getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }
    public static void adicionaOuAlteraDrenagemNasogastrica(Drenagem dre)
    {
        int resultado = existsDrenagemNasogastrica(dre);
        if(resultado!=-1){
            dadosFinal.getListaBalancos().get(resultado).setValorEliminacao(dre.getDrenagem());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(dadosFinal.getDados().getId());
            bal.setValorEliminacao(dre.getDrenagem());
            bal.setHora(dre.getHora());
            dadosFinal.getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }
    public static void adicionaOuAlteraDrenagemVesical(Drenagem dre)
    {
        int resultado = existsDrenagemVesical(dre);
        if(resultado!=-1){
            dadosFinal.getListaBalancos().get(resultado).setValorEliminacao(dre.getDrenagem());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(dadosFinal.getDados().getId());
            bal.setValorEliminacao(dre.getDrenagem());
            bal.setHora(dre.getHora());
            dadosFinal.getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }

    public static int existsAdminSangue(AdministracaoSangue ad)
    {
        int resultado = -1;
        if(dadosFinal.getListaBalancos().size()>0){
            for (int i=0;i<dadosFinal.getListaBalancos().size();i++){
                if(dadosFinal.getListaBalancos().get(i).getHora().equals(ad.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }
    public static int existsDrenagemNasogastrica(Drenagem dre)
    {
        int resultado = -1;
        if(dadosFinal.getListaDrenagemNasogastrica().size()>0){
            for (int i=0;i<dadosFinal.getListaBalancos().size();i++){
                if(dadosFinal.getListaBalancos().get(i).getHora().equals(dre.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }
    public static int existsDrenagemVesical(Drenagem dre)
    {
        int resultado = -1;
        if(dadosFinal.getListaDrenagemVesical().size()>0){
            for (int i=0;i<dadosFinal.getListaBalancos().size();i++){
                if(dadosFinal.getListaBalancos().get(i).getHora().equals(dre.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }

    public void guardarDadosIntraOperatorio() throws ExceptionLog {
        dadosFinal.getDados().setTipoAnestesia(spinner_tipoAnestesia.getSelectedItem().toString());
        dadosFinal.getDados().setTet(Integer.parseInt(editText_TET.getText().toString()));
        dadosFinal.getDados().setMl(Integer.parseInt(editText_ML.getText().toString()));
        dadosFinal.getDados().setCalibreAgulha(Double.parseDouble(editText_AgulhaCalibre.getText().toString()));
        dadosFinal.getDados().setObsAdminSangue(obsAdminSangue.getText().toString());

        dadosFinal.setListaEliminacao(new ArrayList<Eliminacao>());
        Eliminacao e = new Eliminacao();
        e.setIdEnfermagemIntra(dadosFinal.getDados().getId());
        e.setTipo("V");
        e.setTipoSonda(spinner_tipoDrenagemVesical.getSelectedItem().toString());

        if(!isEmpty(calibreDrenagemVesical))
        {
            try {
                e.setCalibre(Double.parseDouble(calibreDrenagemVesical.getText().toString()));
            }
            catch (Exception ex)
            {
                e.setCalibre(0);
                throw new ExceptionLog(ex.getMessage().toString());
            }
            dadosFinal.getListaEliminacao().add(e);
        }

        e = new Eliminacao();
        e.setIdEnfermagemIntra(dadosFinal.getDados().getId());
        e.setTipo("N");
        e.setTipoSonda(spinner_tipoDrenagemNasogastrica.getSelectedItem().toString());
        if(!isEmpty(calibreDrenagemNasogastrica)) {
            e.setCalibre(Double.parseDouble(calibreDrenagemNasogastrica.getText().toString()));
            dadosFinal.getListaEliminacao().add(e);
        }
        dadosFinal.getDados().setPosicaoOperatoria(spinner_posicaoOperatoria.getSelectedItem().toString());
        dadosFinal.getDados().setAlivioZonapressao(editText_alivioZonasPressao.getText().toString());
        dadosFinal.getDados().setLocalAlivioZonaPressao(spinner_localPosicaoOperatoria.getSelectedItem().toString());
        dadosFinal.getDados().setMantaTermica(radioButton_mantatermica_sim.isChecked());
        dadosFinal.getDados().setLocalMantaTermica(spinner_localMantaTermica.getSelectedItem().toString());
        if(!isEmpty(editText_GarrotePressao))
        dadosFinal.getDados().setPressaoGarrotePneumatico(Double.parseDouble(editText_GarrotePressao.getText().toString()));
        dadosFinal.getDados().setLocalizacaoGarrotePneumatico(spinner_localizacaoGarrote.getSelectedItem().toString());
        dadosFinal.getDados().setHoraInicioGarrotePneum(textView_Garrote_horaInicio.getText().toString());
        dadosFinal.getDados().setHoraFimGarrotePneum(textView_Garrote_horaFim.getText().toString());
        dadosFinal.getDados().setPlacaEletrodo(radioButton_Electrodo_sim.isChecked());
        dadosFinal.getDados().setLocalizacaoPlacaEletrodo(spinner_localizacaoElectrodo.getSelectedItem().toString());

        dadosFinal.getDados().setNumFrascosPecaBiopsia(Integer.parseInt(nFrascosBiopsia.getText().toString()));


      //  Log.i("DadosIntraFinal", dadosFinal.toString());

        new guardarDadosIntraOperatorio().execute();


    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dados_intra_operatorio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_saveIntra) {
            try {
                guardarDadosIntraOperatorio();
            } catch (ExceptionLog exceptionLog) {
                exceptionLog.printStackTrace();
            }
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





	private int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString().toLowerCase()
					.equals(myString.toLowerCase())) {
				index = i;
			}
		}
		return index;
	}

	private class verificaIntraOperatorio extends
			AsyncTask<String, Void, DadosIntraoperatorioFinal> {

		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(
					DadosINtraOperatorioActivity.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Please wait...");
			ringProgressDialog
					.setMessage("A verificar Dados Intra Operatórios...");

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(true);
			ringProgressDialog.show();
		};

		@Override
		protected DadosIntraoperatorioFinal doInBackground(String... params) {
			DadosIntraoperatorioFinal dados = new DadosIntraoperatorioFinal();

			try {
				dados = WebServiceUtils.verificaIntraOperatorioID(token,
						HomeActivity.getCirurgia().getId());

			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return dados;
		}

		@Override
		protected void onPostExecute(DadosIntraoperatorioFinal dados) {
			if (dados != null) {
                Log.i("Dados",dados.toString());
                dadosFinal = dados;
                constroiAtividade();
                preencherAtividade();
				ringProgressDialog.dismiss();



                        // dados.getAdministracao().getFc15minAposTransfusao()+"");
                        // adaptadorEquipa = new ArrayAdapter<EquipaComJuncao>(
                        // getBaseContext(), android.R.layout.simple_list_item_1,
                        // lista);
                        // adaptadorEquipa.sort(new Comparator<EquipaComJuncao>() {
                        //
                        // @Override
                        // public int compare(EquipaComJuncao lhs, EquipaComJuncao rhs)
                        // {
                        // return lhs.getNomeEquipa().toLowerCase()
                        // .compareTo(rhs.getNomeEquipa().toLowerCase());
                        // }
                        // });
                        // listaEquipas.setAdapter(adaptadorEquipa);
                        // dialogoEquipas.show();
            }
			else
			{
				Toast.makeText(getApplicationContext(), "Erro Verificar IntraOperatorio - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
                finish();
			}

		}
	}

    private class guardarDadosIntraOperatorio extends
            AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(
                    DadosINtraOperatorioActivity.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Please wait...");
            ringProgressDialog
                    .setMessage("A guardar Dados Intra Operatórios...");

            // ringProgressDialog = ProgressDialog.show(Login.this,
            // "Please wait ...", "Loging in...", true);
            ringProgressDialog.setCancelable(true);
            ringProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);

                }
            });
            ringProgressDialog.show();
        };

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean resultado = false;

            try {
                resultado = WebServiceUtils.guardarDadosIntraOperatorios(token,dadosFinal);

            } catch (IOException | RestClientException | ParseException
                    | JSONException | ExceptionLog e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if (resultado) {
                Toast.makeText(getApplicationContext(), "Dados IntraOperatorio Salvos Com Sucesso", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
                finish();



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erro Guardar IntraOperatorio - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
            }

        }
    }

}
