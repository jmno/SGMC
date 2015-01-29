package pt.mobilesgmc;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class DadosINtraOperatorioActivity extends Fragment {

	String token;
	ProgressDialog ringProgressDialog = null;
    private Spinner spinner_tipoAnestesia;
    private EditText editText_TET;
    private EditText editText_ML;
    private EditText editText_AgulhaCalibre;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;


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

    public DadosINtraOperatorioActivity newInstance(String text){
        DadosINtraOperatorioActivity mFragment = new DadosINtraOperatorioActivity();
        Bundle mBundle = new Bundle();
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_dados_intra_operatorio, container, false);
        token = HomeActivity.getToken();


        spinner_tipoAnestesia = (Spinner) rootView.findViewById(R.id.spinner_DadosIntra_TipoAnestesia);
        editText_TET = (EditText) rootView.findViewById(R.id.editText_DadosIntra_TET);
        editText_ML = (EditText) rootView.findViewById(R.id.editText_DadosIntra_ML);
        editText_AgulhaCalibre = (EditText) rootView.findViewById(R.id.editText_DadosIntra_AgulhaPLCalibre);



        new verificaIntraOperatorio().execute();

        setHasOptionsMenu(true);
        return rootView;
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
        listView_AcessosVenosos = (ListView) getView().findViewById(R.id.listView_DadosIntra_AcessosVenosos);

        if(HomeActivity.getDadosFinal().getListaAcessoVenoso()==null)
            HomeActivity.getDadosFinal().setListaAcessoVenoso(new ArrayList<AcessoVenoso>());
        adapterAcessosVenosos = new AdapterAcessosVenosos(getActivity(),HomeActivity.getDadosFinal().getListaAcessoVenoso());
        listView_AcessosVenosos.setAdapter(adapterAcessosVenosos);
        listView_AcessosVenosos.setScrollContainer(false);
        listView_AcessosVenosos.setVisibility(View.GONE);
        setListViewHeightBasedOnChildren(listView_AcessosVenosos);


        adicionarAcessosVenosos = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_addAcessoVenoso);
        adicionarAcessosVenosos.setClickable(true);
        adicionarAcessosVenosos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcessoVenoso a = new AcessoVenoso();
                a.setLocalizacaoAcessoVenoso("");
                a.setCalibreAcessoVenoso(0);
                a.setTipoAcessoVenoso("");
                a.setIdIntra(HomeActivity.getDadosFinal().getDados().getId());
                adapterAcessosVenosos.add(a);
                setListViewHeightBasedOnChildren(listView_AcessosVenosos);
            }
        });

        if(HomeActivity.getDadosFinal().getListaAcessoVenoso().size()!=0)
            adicionarAcessosVenosos.setVisibility(View.GONE);

        expandCollapseAcessosVenosos = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_expandCollapseAcessosVenosos);
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
        listView_SinaisVitais = (ListView) getView().findViewById(R.id.listView_DadosIntra_SinaisVitais);

        if(HomeActivity.getDadosFinal().getListaSinais()==null)
            HomeActivity.getDadosFinal().setListaSinais(new ArrayList<SinaisVitais>());
        setListViewHeightBasedOnChildren(listView_SinaisVitais);
        adapterSinaisVitais = new AdapterSinaisVitais(getActivity(),HomeActivity.getDadosFinal().getListaSinais());
        listView_SinaisVitais.setAdapter(adapterSinaisVitais);
        listView_SinaisVitais.setScrollContainer(false);
        listView_SinaisVitais.setVisibility(View.GONE);
        adicionarSinalVital = (ImageView)  getView().findViewById(R.id.imageView_DadosIntra_AddSinalVital);
        if(HomeActivity.getDadosFinal().getListaSinais().size()!=0)
            adicionarSinalVital.setVisibility(View.GONE);
        expandCollapseSinaisVitais = (ImageView)  getView().findViewById(R.id.imageView_DadosIntra_ShowHideSinaisVitais);
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
                s.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
                adapterSinaisVitais.add(s);
                setListViewHeightBasedOnChildren(listView_SinaisVitais);

            }
        });

    }

    public void medicacaoAdministrada()
    {
        listView_MedicacaoAdministrada = (ListView) getView().findViewById(R.id.listView_DadosIntra_MedicacaoAdmin);



        if(HomeActivity.getDadosFinal().getListaMedicacao()==null)
            HomeActivity.getDadosFinal().setListaMedicacao(new ArrayList<MedicacaoAdministrada>());
        setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);
        adapterMedicacaoAdministrada = new AdapterMedicacaoAdministrada(getActivity(),HomeActivity.getDadosFinal().getListaMedicacao());
        listView_MedicacaoAdministrada.setAdapter(adapterMedicacaoAdministrada);
        listView_MedicacaoAdministrada.setScrollContainer(false);
        listView_MedicacaoAdministrada.setVisibility(View.GONE);
        adicionarMedicacao = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_AddMedicacao);
        if(HomeActivity.getDadosFinal().getListaMedicacao().size()!=0)
            adicionarMedicacao.setVisibility(View.GONE);
        expandCollapseMedicacao = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandCollapseMedicacao);
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
                m.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
                adapterMedicacaoAdministrada.add(m);
                setListViewHeightBasedOnChildren(listView_MedicacaoAdministrada);

            }
        });
    }

    public void administracaoSangue()
    {

        layoutObsAdminSangue = (LinearLayout) getView().findViewById(R.id.linearLayout_DadosIntra_ObsAdminSangue);
        layoutObsAdminSangue.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        obsAdminSangue = (EditText) getView().findViewById(R.id.editText_DadosIntra_ObsAdminSangue);
        //


        listView_AdminSangue = (ListView) getView().findViewById(R.id.listView_DadosIntra_AdminSangue);



        if(HomeActivity.getDadosFinal().getAdminSangue()==null)
            HomeActivity.getDadosFinal().setAdminSangue(new ArrayList<AdministracaoSangue>());
        setListViewHeightBasedOnChildren(listView_AdminSangue);
        adapterAdministracaoSangue = new AdapterAdministracaoSangue(getActivity(),HomeActivity.getDadosFinal().getAdminSangue());
        listView_AdminSangue.setAdapter(adapterAdministracaoSangue);
        listView_AdminSangue.setScrollContainer(false);
        listView_AdminSangue.setVisibility(View.GONE);
        adicionarAdminSangue = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_AddAdminSangue);
        if(HomeActivity.getDadosFinal().getAdminSangue().size()!=0)
            adicionarAdminSangue.setVisibility(View.GONE);
        expandCollapseAdminSangue = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandeCollapseAdminSangue);
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
                s.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
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
        linearLayoutDrenagemVesical = (LinearLayout) getView().findViewById(R.id.linearLayout_DadosIntra_Vesical);
        linearLayoutDrenagemVesical.setVisibility(View.GONE);
        linearLayoutDrenagemNasogastrica = (LinearLayout) getView().findViewById(R.id.linearLayout_DadosIntra_Nasogastrica);
        linearLayoutDrenagemNasogastrica.setVisibility(View.GONE);
        expandCollapseEliminacao = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandeCollapseEliminacao);
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

        layoutDadosDrenagemVesical = (LinearLayout) getView().findViewById(R.id.linearLayout_DadosIntra_DadosVesical);
        layoutDadosDrenagemVesical.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        calibreDrenagemVesical = (EditText) getView().findViewById(R.id.editText_DadosIntra_calibreSondaVesical);
        spinner_tipoDrenagemVesical = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_tipoSondaVesical);
        //


        listView_DrenagemVesical = (ListView) getView().findViewById(R.id.listView_DadosIntra_dreanagemVesical);

        if(HomeActivity.getDadosFinal().getListaDrenagemVesical()==null)
            HomeActivity.getDadosFinal().setListaDrenagemVesical(new ArrayList<Drenagem>());

        setListViewHeightBasedOnChildren(listView_DrenagemVesical);
        adaptadorDrenagemVesical = new AdapterDrenagem(getActivity(),HomeActivity.getDadosFinal().getListaDrenagemVesical());
        listView_DrenagemVesical.setAdapter(adaptadorDrenagemVesical);
        listView_DrenagemVesical.setScrollContainer(false);
        listView_DrenagemVesical.setVisibility(View.GONE);
        adicionarDrenagemVesical = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_AddVesical);
        if(HomeActivity.getDadosFinal().getListaDrenagemVesical().size()!=0)
            adicionarDrenagemVesical.setVisibility(View.GONE);
        expandeCollapseDrenagemVesical = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandeCollapseVesical);
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



        layoutDadosDrenagemNasogastrica = (LinearLayout) getView().findViewById(R.id.linearLayout_DadosIntra_DadosNasogastrica);
        layoutDadosDrenagemNasogastrica.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        calibreDrenagemNasogastrica = (EditText) getView().findViewById(R.id.editText_DadosIntra_calibreSondaNasogastrica);
        spinner_tipoDrenagemNasogastrica = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_tipoSondaNasogastrica);
        //


        listView_DrenagemNasogastrica = (ListView) getView().findViewById(R.id.listView_DadosIntra_dreanagemNasogastrica);

        if(HomeActivity.getDadosFinal().getListaDrenagemNasogastrica()==null)
            HomeActivity.getDadosFinal().setListaDrenagemNasogastrica(new ArrayList<Drenagem>());

        setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);
        adaptadorDrenagemNasogastrica = new AdapterDrenagem(getActivity(),HomeActivity.getDadosFinal().getListaDrenagemNasogastrica());
        listView_DrenagemNasogastrica.setAdapter(adaptadorDrenagemNasogastrica);
        listView_DrenagemNasogastrica.setScrollContainer(false);
        listView_DrenagemNasogastrica.setVisibility(View.GONE);
        adicionarDrenagemNasogastrica = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_AddNasogastrica);
        if(HomeActivity.getDadosFinal().getListaDrenagemNasogastrica().size()!=0)
            adicionarDrenagemNasogastrica.setVisibility(View.GONE);
        expandeCollapseDrenagemNasogastrica = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandeCollapseNasogastrica);
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

        listView_BalancoHidrico = (ListView) getView().findViewById(R.id.listView_DadosIntra_BalancoHidrico);


        setListViewHeightBasedOnChildren(listView_DrenagemNasogastrica);

        if(HomeActivity.getDadosFinal().getListaBalancos()==null)
            HomeActivity.getDadosFinal().setListaBalancos(new ArrayList<BalancoHidrico>());

        adapterBalancoHidrico = new AdapterBalancoHidrico(getActivity(),HomeActivity.getDadosFinal().getListaBalancos());
        listView_BalancoHidrico.setAdapter(adapterBalancoHidrico);
        listView_BalancoHidrico.setScrollContainer(false);
        listView_BalancoHidrico.setVisibility(View.GONE);

        expandCollapseBalancoHidrico = (ImageView) getView().findViewById(R.id.imageView_DadosIntra_ExpandCollapseBalancoHidrico);
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

        spinner_posicaoOperatoria = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_PosicaoOperatoria);
        editText_alivioZonasPressao = (EditText) getView().findViewById(R.id.editText_DadosIntra_AlivioZonasPressao);
        spinner_localPosicaoOperatoria = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_LocalAlivioZonaPressao);
        radioButton_mantatermica_sim = (RadioButton) getView().findViewById(R.id.radioButton_MantaTermicaProtecaoSim);
        radioButton_mantatermica_nao = (RadioButton) getView().findViewById(R.id.radioButton_MantaTermicaProtecaoNao);
        spinner_localMantaTermica = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_localMantaTermica);
        linearLayout_mantaTermica = (LinearLayout) getView().findViewById(R.id.linearLayout_mantaTermica);

        radioButton_mantatermica_nao.setSelected(true);
        radioButton_mantatermica_nao.setChecked(true);

        linearLayout_mantaTermica.setVisibility(View.GONE);
        radioGroup_mantatermica = (RadioGroup) getView().findViewById(R.id.radioGroup);

        radioGroup_mantatermica.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radioButton_MantaTermicaProtecaoSim)
                    linearLayout_mantaTermica.setVisibility(View.VISIBLE);
                else
                    linearLayout_mantaTermica.setVisibility(View.GONE);

            }
        });


        radioButton_Garrote_sim = (RadioButton) getView().findViewById(R.id.radioButton_GarroteSim);
        radioButton_Garrote_nao = (RadioButton) getView().findViewById(R.id.radioButton_GarroteNao);
        radioGroup_Garrote = (RadioGroup) getView().findViewById(R.id.radioGroup_Garrote);
        editText_GarrotePressao = (EditText) getView().findViewById(R.id.editText_GarrotePressao);
        spinner_localizacaoGarrote = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_localGarrote);
        linearLayout_garrote = (LinearLayout) getView().findViewById(R.id.linearLayout_Garrote);
        textView_Garrote_horaInicio = (TextView) getView().findViewById(R.id.textView_GarroteHoraInicio);
        textView_Garrote_horaFim = (TextView) getView().findViewById(R.id.textView_GarroteHoraFim);

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
                TimePickerDialog dpd = new TimePickerDialog(getActivity(),
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
                TimePickerDialog dpd = new TimePickerDialog(getActivity(),
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



        radioButton_Electrodo_sim = (RadioButton) getView().findViewById(R.id.radioButton_Electrodo_Sim);
        radioButton_Electrodo_nao = (RadioButton) getView().findViewById(R.id.radioButton_Electrodo_Nao);
        radioGroup_Electrodo = (RadioGroup) getView().findViewById(R.id.radioGroup_electrodoNeutro);
        spinner_localizacaoElectrodo = (Spinner) getView().findViewById(R.id.spinner_DadosIntra_localizacaoElectrodo);
        linearLayout_electrodo = (LinearLayout) getView().findViewById(R.id.linearLayout_Electrodo);

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


        linearLayoutBiopsia = (LinearLayout) getView().findViewById(R.id.linearLayoutBiopsia);
        linearLayoutBiopsia.setVisibility(View.GONE);
        //VIR AQUI bUSCAR TEXTO
        nFrascosBiopsia = (EditText) getView().findViewById(R.id.editText_Biopsia_nFrascos);
        //


        listView_PecaBiopsia = (ListView) getView().findViewById(R.id.listViewBiopsia);
        setListViewHeightBasedOnChildren(listView_PecaBiopsia);


        if(HomeActivity.getDadosFinal().getListaPecaBiopsia()==null)
            HomeActivity.getDadosFinal().setListaPecaBiopsia(new ArrayList<PecaBiopsia>());

        adapterBiopsia = new AdapterBiopsia(getActivity(),HomeActivity.getDadosFinal().getListaPecaBiopsia());
        listView_PecaBiopsia.setAdapter(adapterBiopsia);
        listView_PecaBiopsia.setScrollContainer(false);
        listView_PecaBiopsia.setVisibility(View.GONE);
        addBiopsia = (ImageView) getView().findViewById(R.id.imageView_addBiopsia);
        if(HomeActivity.getDadosFinal().getListaPecaBiopsia().size()!=0)
            addBiopsia.setVisibility(View.GONE);
        expandCollapseBiopsia = (ImageView) getView().findViewById(R.id.imageView_expandCollapse_Biopsia);
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
                p.setIdIntra(HomeActivity.getDadosFinal().getDados().getId());
                adapterBiopsia.add(p);
                setListViewHeightBasedOnChildren(listView_PecaBiopsia);

            }
        });



    }


    public void preencherAtividade() {

        if(HomeActivity.getDadosFinal().getDados()!=null){
            if(HomeActivity.getDadosFinal().getDados().getTipoAnestesia()!=null)
                spinner_tipoAnestesia.setSelection(spinnerDaMeATuaPosicao(spinner_tipoAnestesia, HomeActivity.getDadosFinal().getDados().getTipoAnestesia()));

            editText_TET.setText(HomeActivity.getDadosFinal().getDados().getTet() + "");
            editText_ML.setText(HomeActivity.getDadosFinal().getDados().getMl() + "");
            editText_AgulhaCalibre.setText(HomeActivity.getDadosFinal().getDados().getCalibreAgulha() + "");

            if(HomeActivity.getDadosFinal().getDados().getObsAdminSangue()!=null)
                obsAdminSangue.setText(HomeActivity.getDadosFinal().getDados().getObsAdminSangue());

            if(HomeActivity.getDadosFinal().getDados().getPosicaoOperatoria()!=null)
                spinner_posicaoOperatoria.setSelection(spinnerDaMeATuaPosicao(spinner_posicaoOperatoria,HomeActivity.getDadosFinal().getDados().getPosicaoOperatoria()));


            if(HomeActivity.getDadosFinal().getDados().getAlivioZonapressao()!=null)
                editText_alivioZonasPressao.setText(HomeActivity.getDadosFinal().getDados().getAlivioZonapressao());

            if(HomeActivity.getDadosFinal().getDados().getLocalAlivioZonaPressao()!=null)
                spinner_localPosicaoOperatoria.setSelection(spinnerDaMeATuaPosicao(spinner_localPosicaoOperatoria,HomeActivity.getDadosFinal().getDados().getLocalAlivioZonaPressao()));

            if(HomeActivity.getDadosFinal().getDados().isMantaTermica())
            {
                radioButton_mantatermica_sim.setChecked(true);
                linearLayout_mantaTermica.setVisibility(View.VISIBLE);
                spinner_localMantaTermica.setSelection(spinnerDaMeATuaPosicao(spinner_localMantaTermica,HomeActivity.getDadosFinal().getDados().getLocalMantaTermica()));
            }

            if(HomeActivity.getDadosFinal().getDados().getPressaoGarrotePneumatico()>0.0)
            {
                radioButton_Garrote_sim.setChecked(true);
                linearLayout_garrote.setVisibility(View.VISIBLE);
                spinner_localizacaoGarrote.setSelection(spinnerDaMeATuaPosicao(spinner_localizacaoGarrote,HomeActivity.getDadosFinal().getDados().getLocalizacaoGarrotePneumatico()));
                editText_GarrotePressao.setText(HomeActivity.getDadosFinal().getDados().getPressaoGarrotePneumatico()+"");
                if(HomeActivity.getDadosFinal().getDados().getHoraInicioGarrotePneum()!=null)
                    textView_Garrote_horaInicio.setText(HomeActivity.getDadosFinal().getDados().getHoraInicioGarrotePneum());
                if(HomeActivity.getDadosFinal().getDados().getHoraFimGarrotePneum()!=null)
                    textView_Garrote_horaFim.setText(HomeActivity.getDadosFinal().getDados().getHoraFimGarrotePneum());
            }

            if(HomeActivity.getDadosFinal().getDados().isPlacaEletrodo())
            {
                radioButton_Electrodo_sim.setChecked(true);
                linearLayout_electrodo.setVisibility(View.VISIBLE);
                spinner_localizacaoElectrodo.setSelection(spinnerDaMeATuaPosicao(spinner_localizacaoElectrodo,HomeActivity.getDadosFinal().getDados().getLocalizacaoPlacaEletrodo()));
            }

            nFrascosBiopsia.setText(HomeActivity.getDadosFinal().getDados().getNumFrascosPecaBiopsia()+"");


        }

        if(HomeActivity.getDadosFinal().getListaEliminacao()==null)
            HomeActivity.getDadosFinal().setListaEliminacao(new ArrayList<Eliminacao>());
        if(HomeActivity.getDadosFinal().getListaEliminacao().size()>0)
        {
            for(int i=0; i<HomeActivity.getDadosFinal().getListaEliminacao().size(); i++)
            {
                if(HomeActivity.getDadosFinal().getListaEliminacao().get(i).getTipo().toLowerCase().equals("v"))
                {
                    calibreDrenagemVesical.setText(HomeActivity.getDadosFinal().getListaEliminacao().get(i).getCalibre()+"");
                    spinner_tipoDrenagemVesical.setSelection(spinnerDaMeATuaPosicao(spinner_tipoDrenagemVesical,HomeActivity.getDadosFinal().getListaEliminacao().get(i).getTipoSonda()));
                }
                else if(HomeActivity.getDadosFinal().getListaEliminacao().get(i).getTipo().toLowerCase().equals("n"))
                {
                    calibreDrenagemNasogastrica.setText(HomeActivity.getDadosFinal().getListaEliminacao().get(i).getCalibre()+"");
                    spinner_tipoDrenagemNasogastrica.setSelection(spinnerDaMeATuaPosicao(spinner_tipoDrenagemNasogastrica,HomeActivity.getDadosFinal().getListaEliminacao().get(i).getTipoSonda()));
                }
            }
        }








        Log.i("Acessos", HomeActivity.getDadosFinal().getListaAcessoVenoso().toString());



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
        HomeActivity.getDadosFinal().getListaBalancos().clear();

        for(int i=0; i<HomeActivity.getDadosFinal().getAdminSangue().size(); i++)
        {
            adicionaOuAlteraAdminSangue(HomeActivity.getDadosFinal().getAdminSangue().get(i));

        }

        for(int i=0; i<HomeActivity.getDadosFinal().getListaDrenagemNasogastrica().size(); i++)
        {
            adicionaOuAlteraDrenagemNasogastrica(HomeActivity.getDadosFinal().getListaDrenagemNasogastrica().get(i));

        }

        for(int i=0; i<HomeActivity.getDadosFinal().getListaDrenagemVesical().size(); i++)
        {
            adicionaOuAlteraDrenagemVesical(HomeActivity.getDadosFinal().getListaDrenagemVesical().get(i));

        }

        adapterBalancoHidrico.notifyDataSetChanged();


    }

    public static void adicionaOuAlteraAdminSangue(AdministracaoSangue a)
    {
        int resultado = existsAdminSangue(a);
        if(resultado!=-1){
            HomeActivity.getDadosFinal().getListaBalancos().get(resultado).setValorAdministracaoSangue(a.getValorAdministracao());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
            bal.setValorAdministracaoSangue(a.getValorAdministracao());
            bal.setHora(a.getHora());
            HomeActivity.getDadosFinal().getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }
    public static void adicionaOuAlteraDrenagemNasogastrica(Drenagem dre)
    {
        int resultado = existsDrenagemNasogastrica(dre);
        if(resultado!=-1){
            HomeActivity.getDadosFinal().getListaBalancos().get(resultado).setValorEliminacao(dre.getDrenagem());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
            bal.setValorEliminacao(dre.getDrenagem());
            bal.setHora(dre.getHora());
            HomeActivity.getDadosFinal().getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }
    public static void adicionaOuAlteraDrenagemVesical(Drenagem dre)
    {
        int resultado = existsDrenagemVesical(dre);
        if(resultado!=-1){
            HomeActivity.getDadosFinal().getListaBalancos().get(resultado).setValorEliminacao(dre.getDrenagem());
        }
        else
        {
            BalancoHidrico bal = new BalancoHidrico();
            bal.setIdIntraOperatorio(HomeActivity.getDadosFinal().getDados().getId());
            bal.setValorEliminacao(dre.getDrenagem());
            bal.setHora(dre.getHora());
            HomeActivity.getDadosFinal().getListaBalancos().add(bal);
            setListViewHeightBasedOnChildren(listView_BalancoHidrico);
        }

    }

    public static int existsAdminSangue(AdministracaoSangue ad)
    {
        int resultado = -1;
        if(HomeActivity.getDadosFinal().getListaBalancos().size()>0){
            for (int i=0;i<HomeActivity.getDadosFinal().getListaBalancos().size();i++){
                if(HomeActivity.getDadosFinal().getListaBalancos().get(i).getHora().equals(ad.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }
    public static int existsDrenagemNasogastrica(Drenagem dre)
    {
        int resultado = -1;
        if(HomeActivity.getDadosFinal().getListaDrenagemNasogastrica().size()>0){
            for (int i=0;i<HomeActivity.getDadosFinal().getListaBalancos().size();i++){
                if(HomeActivity.getDadosFinal().getListaBalancos().get(i).getHora().equals(dre.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }
    public static int existsDrenagemVesical(Drenagem dre)
    {
        int resultado = -1;
        if(HomeActivity.getDadosFinal().getListaDrenagemVesical().size()>0){
            for (int i=0;i<HomeActivity.getDadosFinal().getListaBalancos().size();i++){
                if(HomeActivity.getDadosFinal().getListaBalancos().get(i).getHora().equals(dre.getHora()))
                    resultado = i;
            }

        }
        return resultado;
    }

    public void guardarDadosIntraOperatorio() throws ExceptionLog {
        HomeActivity.getDadosFinal().getDados().setTipoAnestesia(spinner_tipoAnestesia.getSelectedItem().toString());
        HomeActivity.getDadosFinal().getDados().setTet(Integer.parseInt(editText_TET.getText().toString()));
        HomeActivity.getDadosFinal().getDados().setMl(Integer.parseInt(editText_ML.getText().toString()));
        HomeActivity.getDadosFinal().getDados().setCalibreAgulha(Double.parseDouble(editText_AgulhaCalibre.getText().toString()));
        HomeActivity.getDadosFinal().getDados().setObsAdminSangue(obsAdminSangue.getText().toString());

        HomeActivity.getDadosFinal().setListaEliminacao(new ArrayList<Eliminacao>());
        Eliminacao e = new Eliminacao();
        e.setIdEnfermagemIntra(HomeActivity.getDadosFinal().getDados().getId());
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
            HomeActivity.getDadosFinal().getListaEliminacao().add(e);
        }

        e = new Eliminacao();
        e.setIdEnfermagemIntra(HomeActivity.getDadosFinal().getDados().getId());
        e.setTipo("N");
        e.setTipoSonda(spinner_tipoDrenagemNasogastrica.getSelectedItem().toString());
        if(!isEmpty(calibreDrenagemNasogastrica)) {
            e.setCalibre(Double.parseDouble(calibreDrenagemNasogastrica.getText().toString()));
            HomeActivity.getDadosFinal().getListaEliminacao().add(e);
        }
        HomeActivity.getDadosFinal().getDados().setPosicaoOperatoria(spinner_posicaoOperatoria.getSelectedItem().toString());
        HomeActivity.getDadosFinal().getDados().setAlivioZonapressao(editText_alivioZonasPressao.getText().toString());
        HomeActivity.getDadosFinal().getDados().setLocalAlivioZonaPressao(spinner_localPosicaoOperatoria.getSelectedItem().toString());
        HomeActivity.getDadosFinal().getDados().setMantaTermica(radioButton_mantatermica_sim.isChecked());
        HomeActivity.getDadosFinal().getDados().setLocalMantaTermica(spinner_localMantaTermica.getSelectedItem().toString());
        if(!isEmpty(editText_GarrotePressao))
            HomeActivity.getDadosFinal().getDados().setPressaoGarrotePneumatico(Double.parseDouble(editText_GarrotePressao.getText().toString()));
        HomeActivity.getDadosFinal().getDados().setLocalizacaoGarrotePneumatico(spinner_localizacaoGarrote.getSelectedItem().toString());
        HomeActivity.getDadosFinal().getDados().setHoraInicioGarrotePneum(textView_Garrote_horaInicio.getText().toString());
        HomeActivity.getDadosFinal().getDados().setHoraFimGarrotePneum(textView_Garrote_horaFim.getText().toString());
        HomeActivity.getDadosFinal().getDados().setPlacaEletrodo(radioButton_Electrodo_sim.isChecked());
        HomeActivity.getDadosFinal().getDados().setLocalizacaoPlacaEletrodo(spinner_localizacaoElectrodo.getSelectedItem().toString());

        HomeActivity.getDadosFinal().getDados().setNumFrascosPecaBiopsia(Integer.parseInt(nFrascosBiopsia.getText().toString()));


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
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.dados_intra_operatorio, menu);


        super.onCreateOptionsMenu(menu, inflater);

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
					getActivity());
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
                HomeActivity.setDadosFinal(dados);
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
				Toast.makeText(getActivity(), "Erro Verificar IntraOperatorio - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
                ((HomeActivity) getActivity()).onItemClickNavigation(0,HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0, true);
			}

		}
	}

    private class guardarDadosIntraOperatorio extends
            AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(
                    getActivity());
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
                resultado = WebServiceUtils.guardarDadosIntraOperatorios(token,HomeActivity.getDadosFinal());

            } catch (IOException | RestClientException | ParseException
                    | JSONException | ExceptionLog e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if (resultado) {
                Toast.makeText(getActivity(), "Dados IntraOperatorio Salvos Com Sucesso", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
                ((HomeActivity) getActivity()).onItemClickNavigation(0, HomeActivity.getLayoutcontainerid());
                ((HomeActivity) getActivity()).setCheckedItemNavigation(0,true);


            }
            else
            {
                Toast.makeText(getActivity(), "Erro Guardar IntraOperatorio - Verifique a Internet e repita o Processo", Toast.LENGTH_SHORT).show();
                ringProgressDialog.dismiss();
            }

        }
    }

}
