package info.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;
import info.mobilesgmc.modelo.Cirurgia;
import info.mobilesgmc.modelo.DadosIntraoperatorioFinal;
import info.mobilesgmc.modelo.EquipaComJuncao;
import info.mobilesgmc.modelo.ListasProdutosCirurgia;
import info.mobilesgmc.modelo.Utente;
import info.nicolau.mobilegsmc.R;

public class HomeActivity extends NavigationLiveo implements NavigationLiveoListener{

	private ArrayAdapter<Cirurgia> adaptadorCirurgias;
	private Dialog dialog;

	private static Cirurgia cirurgia;
    private static EquipaComJuncao equipa;
    private static DadosIntraoperatorioFinal dadosFinal;
    private static ListasProdutosCirurgia listaProdutos;
    private static Utente utente;
    private static List<Integer> mListIconItem;
	ProgressDialog ringProgressDialog = null;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button buttonSpeech;
    private Boolean isAdmin;

    private static List<String> mListNameItem;

    private static String token;
    private static Boolean isForResultUtentes = false;
    private static int layoutcontainerid;
    private static String username= "";
    private static String cc ="";
    private static int imageResource=0;
    private static boolean isStarted= false;
    private static ArrayList<Integer> listaNavegacao;




    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText(username);
        this.mUserEmail.setText(cc);
        this.mUserPhoto.setImageResource(imageResource);
        this.mUserBackground.setImageResource(R.drawable.ic_userbackground_2);

        /*
        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);
        this.addCustomHeader(mCustomHeader); //This will add the new header and remove the default user header*/
    }



    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void onInt(Bundle savedInstanceState) {
        //Creation of the list items is here
        // set listener {required}
        this.setNavigationListener(this);
        this.setDefaultStartPositionNavigation(0);


        // name of the list items
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, "Home");
        mListNameItem.add(1, "Ficha Utente");
        mListNameItem.add(2, "Equipa Cirurgica");
        mListNameItem.add(3, "Dados Cirurgia");
        mListNameItem.add(4, "Dados IntraOperatórios");
        mListNameItem.add(5, "Listas");
        mListNameItem.add(6, "Produtos");
        mListNameItem.add(7, "Aparelhos Utilizados");
        mListNameItem.add(8, "Instrumental Utilizado");
        mListNameItem.add(9, "Material Utilizado");
        mListNameItem.add(10, "Criar");
        mListNameItem.add(11, "Cirurgia");
        mListNameItem.add(12, "Profissional Saúde");

        // icons list items
        mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_home);
        mListIconItem.add(1, R.drawable.ic_action_user);
        mListIconItem.add(2, R.drawable.ic_medical_doctor);
        mListIconItem.add(3, R.drawable.ic_surgery);
        mListIconItem.add(4, R.drawable.heart288);
        mListIconItem.add(5, 0);
        mListIconItem.add(6, R.drawable.verification5);
        mListIconItem.add(7, R.drawable.verificationa);
        mListIconItem.add(8, R.drawable.verificationi);
        mListIconItem.add(9, R.drawable.verificationm);
        mListIconItem.add(10, 0);
        mListIconItem.add(11, R.drawable.surgery_add);
        mListIconItem.add(12, R.drawable.ic_medical_doctor_add);
        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(5);
        mListHeaderItem.add(10);
        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.logout, R.drawable.ic_exit_to_app);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
        listaNavegacao = new ArrayList<Integer>() ;
        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");
        cc = "CC: "+ PreferenceManager.getDefaultSharedPreferences(this).getString(
                "cc", " ");
        username ="Nome: "+ PreferenceManager.getDefaultSharedPreferences(this).getString(
                "username", "Sem Nome");
        if(cc.toLowerCase().equals("cc: 13827237"))
           imageResource = R.drawable.nico;
        else
            imageResource = R.drawable.ic_action_user;

        onUserInformation();

        isAdmin = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isAdmin",false);
    }


    @Override //The "layoutContainerId" should be used in "beginTransaction (). Replace"
    public void onItemClickNavigation(int position, int layoutContainerId) {

        layoutcontainerid = layoutContainerId;
        FragmentManager mFragmentManager = getSupportFragmentManager();


        switch (position)
        {
            case 0:
                listaNavegacao.add(0);
                Fragment mFragment = new FragmentMain().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                setTitle("Home");
                break;
            case 1:
                listaNavegacao.add(1);
                if(cirurgia != null && cirurgia.getIdUtente()==0)
                {
                    setIsForResultUtentes(true);
                }
                mFragment = new UtentesActivity().newInstance(mListNameItem.get(position));
                mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                setTitle("Utente");
                break;
            case 2:
                listaNavegacao.add(2);
                if(cirurgia == null) {
                    Toast.makeText(getApplicationContext(), "Escolha/Crie Cirurgia", Toast.LENGTH_SHORT).show();
                }
                else{
                    mFragment = new EquipaCirurgica().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId,mFragment).commit();
                    setTitle("Equipa Cirúrgica");
                }

                break;
            case 3:
                listaNavegacao.add(3);
                if(cirurgia == null) {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();
                }
                else {
                    mFragment = new DadosCirurgia().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                    setTitle("Dados Cirurgia");
                }
                break;
            case 4:
                listaNavegacao.add(4);
                if(cirurgia == null)
                {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mFragment = new DadosINtraOperatorioActivity().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                    setTitle("Dados Intra-Operatório");
                }
                break;
            case 6:
                listaNavegacao.add(6);
                if(cirurgia == null)
                {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mFragment = new ListaProdutosActivity().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                    setTitle("Lista Produtos");
                }
                break;
            case 7:
                listaNavegacao.add(7);
                if(cirurgia == null)
                {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mFragment = new AparelhosActivity().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                    setTitle("Lista Aparelhos");
                }
                break;
            case 8:
                listaNavegacao.add(8);
                if(cirurgia == null)
                {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mFragment = new InstrumentalActivity().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 9:
                listaNavegacao.add(9);
                if(cirurgia == null)
                {
                    Toast.makeText(getApplicationContext(), "Escolha ou Crie Cirurgia", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mFragment = new MaterialActivity().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                    setTitle("Lista Materiais");
                }
                break;
            case 11:
                listaNavegacao.add(11);
                setTitle("Nova Cirurgia");
                cirurgia = new Cirurgia();
                equipa = new EquipaComJuncao();
                mFragment = new DadosCirurgia().newInstance(mListNameItem.get(position));
                mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                break;

            case 12:
                listaNavegacao.add(12);
                if(isAdmin) {
                    setTitle("Novo Profissional Saúde");
                    mFragment = new NovoProfissional().newInstance(mListNameItem.get(position));
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                else
                    Toast.makeText(getApplicationContext(), "Apenas Administradores", Toast.LENGTH_SHORT).show();

                break;
        }


    }



    @Override
    public void onClickUserPhotoNavigation(View v)
    {
        Toast.makeText(this, username , Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClickFooterItemNavigation(View v)
    {
        PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .edit().clear().commit();
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

        //hide the menu when the navigation is opens
        switch (position) {
            case 0:

                break;

            case 1:
                break;


        }
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
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }


		return super.onOptionsItemSelected(item);
	}


    @Override
    public void onBackPressed()
    {
        if(listaNavegacao.size()>1){
            if(listaNavegacao.get(listaNavegacao.size()-1)==0){
                verificaSair();
            }
            else{
                int last= HomeActivity.getListaNavegacao().get(HomeActivity.getListaNavegacao().size()-2);
                listaNavegacao.remove(listaNavegacao.size()-1);
                onItemClickNavigation(last, HomeActivity.getLayoutcontainerid());
                setCheckedItemNavigation(last, true);
            }
        }
        else
        {
            verificaSair();
        }


    }
    public void verificaSair()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return;
        }
        else { Toast.makeText(getBaseContext(), "Pressione duas vezes para sair...", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }


	public static Cirurgia getCirurgia() {
		return cirurgia;
	}

	public static void setCirurgia(Cirurgia cirurgia) {
		HomeActivity.cirurgia = cirurgia;
	}

    public static String getToken(){return token;}

    public static Boolean getIsForResultUtentes() {
        return isForResultUtentes;
    }

    public static void setIsForResultUtentes(Boolean isForResultUtentes) {
        HomeActivity.isForResultUtentes = isForResultUtentes;
    }
    public static int getLayoutcontainerid() {
        return layoutcontainerid;
    }

    public static void setLayoutcontainerid(int layoutcontainerid) {
        HomeActivity.layoutcontainerid = layoutcontainerid;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        HomeActivity.username = username;
    }

    public static EquipaComJuncao getEquipa() {
        return equipa;
    }

    public static void setEquipa(EquipaComJuncao equipa) {
        HomeActivity.equipa = equipa;
    }

    public static String getCc() {
        return cc;
    }

    public static void setCc(String cc) {
        HomeActivity.cc = cc;
    }

    public static boolean isIsStarted() {
        return isStarted;
    }

    public static void setIsStarted(boolean isStarted) {
        HomeActivity.isStarted = isStarted;
    }

    public static DadosIntraoperatorioFinal getDadosFinal() {
        return dadosFinal;
    }

    public static void setDadosFinal(DadosIntraoperatorioFinal dadosFinal) {
        HomeActivity.dadosFinal = dadosFinal;
    }


    public static ListasProdutosCirurgia getListaProdutos() {
        return listaProdutos;
    }

    public static void setListaProdutos(ListasProdutosCirurgia listaProdutos) {
        HomeActivity.listaProdutos = listaProdutos;
    }

    public static ArrayList<Integer> getListaNavegacao() {
        return listaNavegacao;
    }

    public static void setListaNavegacao(ArrayList<Integer> listaNavegacao) {
        HomeActivity.listaNavegacao = listaNavegacao;
    }

    public static Utente getUtente() {
        return utente;
    }

    public static void setUtente(Utente utente) {
        HomeActivity.utente = utente;
    }
}
