package pt.mobilesgmc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import pt.mobilesgmc.acessosVenosos.AdapterAcessosVenosos;
import pt.mobilesgmc.adminSangue.AdapterAdministracaoSangue;
import pt.mobilesgmc.dreanagem.AdapterDrenagem;
import pt.mobilesgmc.medicacaoAdministrada.AdapterMedicacaoAdministrada;
import pt.mobilesgmc.modelo.AcessoVenoso;
import pt.mobilesgmc.modelo.AdministracaoSangue;
import pt.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import pt.mobilesgmc.modelo.Drenagem;
import pt.mobilesgmc.modelo.MedicacaoAdministrada;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.SinaisVitais;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.sinaisVitais.AdapterSinaisVitais;

public class DadosINtraOperatorioActivity extends Activity {

	String token;
	ProgressDialog ringProgressDialog = null;
    private Spinner spinner_tipoAnestesia;
    private EditText editText_TET;
    private EditText editText_ML;
    private EditText editText_AgulhaCalibre;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

    //Acessos Venosos
    private ListView listView_AcessosVenosos;
    private ArrayList<AcessoVenoso> itemsAcessoVenoso;
    private AdapterAcessosVenosos adapterAcessosVenosos;
    private ImageView adicionarAcessosVenosos;
    private ImageView expandCollapseAcessosVenosos;


    //Sinais Vitais
    private ListView listView_SinaisVitais;
    private ArrayList<SinaisVitais> itemsSinaisVitais;
    private AdapterSinaisVitais adapterSinaisVitais;
    private TextView horaSinalVital;
    private ImageView adicionarSinalVital;
    private ImageView expandCollapseSinaisVitais;

    //Sinais Vitais
    private ListView listView_MedicacaoAdministrada;
    private ArrayList<MedicacaoAdministrada> itemsMedicacaoAdministrada;
    private AdapterMedicacaoAdministrada adapterMedicacaoAdministrada;
    private ImageView adicionarMedicacao;
    private ImageView expandCollapseMedicacao;

    //Administracao Sangue
    private ListView listView_AdminSangue;
    private ArrayList<AdministracaoSangue> itemsAdministracaoSangue;
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
    private ArrayList<Drenagem> itemsDrenagemVesical;
    private AdapterDrenagem adaptadorDrenagemVesical;
    private ImageView adicionarDrenagemVesical;
    private ImageView expandeCollapseDrenagemVesical;
    private LinearLayout layoutDadosDrenagemVesical;
    private EditText calibreDrenagemVesical;
    private Spinner spinner_tipoDrenagemVesical;

    //DRENAGEM NASOGASTRICA
    private ListView listView_DrenagemNasogastrica;
    private ArrayList<Drenagem> itemsDrenagemNasogastrica;
    private AdapterDrenagem adaptadorDrenagemNasogastrica;
    private ImageView adicionarDrenagemNasogastrica;
    private ImageView expandeCollapseDrenagemNasogastrica;
    private LinearLayout layoutDadosDrenagemNasogastrica;
    private EditText calibreDrenagemNasogastrica;
    private Spinner spinner_tipoDrenagemNasogastrica;



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");



        spinner_tipoAnestesia = (Spinner) findViewById(R.id.spinner_DadosIntra_TipoAnestesia);
        editText_TET = (EditText) findViewById(R.id.editText_DadosIntra_TET);
        editText_ML = (EditText) findViewById(R.id.editText_DadosIntra_ML);
        editText_AgulhaCalibre = (EditText) findViewById(R.id.editText_DadosIntra_AgulhaPLCalibre);

        acessosVenosos();
        sinaisVitais();
        medicacaoAdministrada();
        administracaoSangue();
        eliminacao();
        //new verificaIntraOperatorio().execute();

	}

    public void acessosVenosos()
    {
        listView_AcessosVenosos = (ListView) findViewById(R.id.listView_DadosIntra_AcessosVenosos);
        itemsAcessoVenoso = new ArrayList<AcessoVenoso>();


        adapterAcessosVenosos = new AdapterAcessosVenosos(this,itemsAcessoVenoso);
        listView_AcessosVenosos.setAdapter(adapterAcessosVenosos);
        listView_AcessosVenosos.setScrollContainer(false);
        listView_AcessosVenosos.setVisibility(View.GONE);

        AcessoVenoso a = new AcessoVenoso();
        a.setLocalizacaoAcessoVenoso("");
        a.setCalibreAcessoVenoso(0);
        a.setTipoAcessoVenoso("");
        adapterAcessosVenosos.add(a);
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
                adapterAcessosVenosos.add(a);
                setListViewHeightBasedOnChildren(listView_AcessosVenosos);
            }
        });

        if(itemsAcessoVenoso.size()!=0)
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
        SinaisVitais s = new SinaisVitais();
        s.setFc(1.2);
        s.setHora("10:10");
        s.setSpo2(2);
        s.setTaMax(2.1);
        s.setTaMin(2.2);
        s.setTemp(36);
        SinaisVitais s1 = new SinaisVitais();
        s1.setFc(1.2);
        s1.setHora("10:10");
        s1.setSpo2(2);
        s1.setTaMax(2.1);
        s1.setTaMin(2.2);
        s1.setTemp(36);
        itemsSinaisVitais = new ArrayList<SinaisVitais>();
        itemsSinaisVitais.add(s);
        itemsSinaisVitais.add(s1);
        setListViewHeightBasedOnChildren(listView_SinaisVitais);
        adapterSinaisVitais = new AdapterSinaisVitais(this,itemsSinaisVitais);
        listView_SinaisVitais.setAdapter(adapterSinaisVitais);
        listView_SinaisVitais.setScrollContainer(false);
        listView_SinaisVitais.setVisibility(View.GONE);
        adicionarSinalVital = (ImageView) findViewById(R.id.imageView_DadosIntra_AddSinalVital);
        if(itemsSinaisVitais.size()!=0)
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
                adapterSinaisVitais.add(s);
                setListViewHeightBasedOnChildren(listView_SinaisVitais);

            }
        });

    }

    public void medicacaoAdministrada()
    {
        listView_MedicacaoAdministrada = (ListView) findViewById(R.id.listView_DadosIntra_MedicacaoAdmin);

        itemsMedicacaoAdministrada = new ArrayList<MedicacaoAdministrada>();

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
        itemsMedicacaoAdministrada.add(m);

        setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);
        adapterMedicacaoAdministrada = new AdapterMedicacaoAdministrada(this,itemsMedicacaoAdministrada);
        listView_MedicacaoAdministrada.setAdapter(adapterMedicacaoAdministrada);
        listView_MedicacaoAdministrada.setScrollContainer(false);
        listView_MedicacaoAdministrada.setVisibility(View.GONE);
        adicionarMedicacao = (ImageView) findViewById(R.id.imageView_DadosIntra_AddMedicacao);
        if(itemsMedicacaoAdministrada.size()!=0)
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
        AdministracaoSangue s = new AdministracaoSangue();
        s.setFc(1.2);
        s.setHora("10:10");
        s.setSpo2(2);
        s.setTaMax(2.1);
        s.setTaMin(2.2);
        s.setTipo("Sangue");
        AdministracaoSangue s1 = new AdministracaoSangue();
        s1.setFc(1.2);
        s1.setHora("10:10");
        s1.setSpo2(2);
        s1.setTaMax(2.1);
        s1.setTaMin(2.2);
        s1.setTipo("Hemácias");

        itemsAdministracaoSangue = new ArrayList<AdministracaoSangue>();
        itemsAdministracaoSangue.add(s);
        itemsAdministracaoSangue.add(s1);
        setListViewHeightBasedOnChildren(listView_AdminSangue);
        adapterAdministracaoSangue = new AdapterAdministracaoSangue(this,itemsAdministracaoSangue);
        listView_AdminSangue.setAdapter(adapterAdministracaoSangue);
        listView_AdminSangue.setScrollContainer(false);
        listView_AdminSangue.setVisibility(View.GONE);
        adicionarAdminSangue = (ImageView) findViewById(R.id.imageView_DadosIntra_AddAdminSangue);
        if(itemsAdministracaoSangue.size()!=0)
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
                adapterAdministracaoSangue.add(s);
                setListViewHeightBasedOnChildren(listView_AdminSangue);

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
        Drenagem s = new Drenagem();
        s.setHora("10:10");
        s.setDrenagem(" a ");
        s.setCaracteristicas("Anestesia geral");
        Drenagem s1 = new Drenagem();
        s1.setHora("10:10");
        s1.setDrenagem(" a ");
        s1.setCaracteristicas("Anestesia local");

        itemsDrenagemVesical = new ArrayList<Drenagem>();
        itemsDrenagemVesical.add(s);
        itemsDrenagemVesical.add(s1);
        setListViewHeightBasedOnChildren(listView_DrenagemVesical);
        adaptadorDrenagemVesical = new AdapterDrenagem(this,itemsDrenagemVesical);
        listView_DrenagemVesical.setAdapter(adaptadorDrenagemVesical);
        listView_DrenagemVesical.setScrollContainer(false);
        listView_DrenagemVesical.setVisibility(View.GONE);
        adicionarDrenagemVesical = (ImageView) findViewById(R.id.imageView_DadosIntra_AddVesical);
        if(itemsDrenagemVesical.size()!=0)
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
                s.setDrenagem("");
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
        Drenagem s = new Drenagem();
        s.setHora("10:10");
        s.setDrenagem(" a ");
        s.setCaracteristicas("Anestesia geral");
        Drenagem s1 = new Drenagem();
        s1.setHora("10:10");
        s1.setDrenagem(" a ");
        s1.setCaracteristicas("Anestesia local");

        itemsDrenagemNasogastrica = new ArrayList<Drenagem>();
        itemsDrenagemNasogastrica.add(s);
        itemsDrenagemNasogastrica.add(s1);
        setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);
        adaptadorDrenagemNasogastrica = new AdapterDrenagem(this,itemsDrenagemNasogastrica);
        listView_DrenagemNasogastrica.setAdapter(adaptadorDrenagemNasogastrica);
        listView_DrenagemNasogastrica.setScrollContainer(false);
        listView_DrenagemNasogastrica.setVisibility(View.GONE);
        adicionarDrenagemNasogastrica = (ImageView) findViewById(R.id.imageView_DadosIntra_AddNasogastrica);
        if(itemsDrenagemNasogastrica.size()!=0)
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
                s.setDrenagem("");
                s.setCaracteristicas("");
                adaptadorDrenagemNasogastrica.add(s);
                setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);

            }
        });


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




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

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

				ringProgressDialog.dismiss();
				//preencherAtividade(dados);

				// Log.i("Dados",
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
			}

		}
	}

}
