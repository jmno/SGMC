package pt.mobilesgmc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;
import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;

public class HomeActivity extends NavigationLiveo implements NavigationLiveoListener {

	private ArrayAdapter<Cirurgia> adaptadorCirurgias;
	private Dialog dialog;
	public static ListView listaCirurgias;
	public static EditText texto_cirurgia;
	public static TextView textoCirurgiaAUsar;
	private static Cirurgia cirurgia;
	ProgressDialog ringProgressDialog = null;
    private static String token;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onInt(Bundle savedInstanceState) {
        //Creation of the list items is here

        // set listener {required}
        this.setNavigationListener(this);

        // name of the list items
        List<String> mListNameItem = new ArrayList<>();
        mListNameItem.add(0, "Home");
        mListNameItem.add(1, "Equipa Cirurgica");
        mListNameItem.add(2, "Ficha Utente");
        mListNameItem.add(3, "Dados Cirurgia");
        mListNameItem.add(4, "Dados IntraOperatório"); //This item will be a subHeader
        mListNameItem.add(5, "Listas Materiais");
        mListNameItem.add(6, "Aparelhos Utilizados");
        mListNameItem.add(7, "Instrumental Utilizado");
        mListNameItem.add(8, "Material Utilizado");

        // icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, 0);
        mListIconItem.add(1, 0); //Item no icon set 0
        mListIconItem.add(2, 0); //Item no icon set 0
        mListIconItem.add(3, 0);
        mListIconItem.add(4, 0); //When the item is a subHeader the value of the icon 0
        mListIconItem.add(5, 0);
        mListIconItem.add(6, 0);
        //mListIconItem.add(6, R.drawable.ic_report_black_24dp);
        mListIconItem.add(7, 0);
        mListIconItem.add(8, 0);

        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer("Logout", R.drawable.ic_action_attach);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);

        setTitle("Ecrã Principal");
        token = PreferenceManager.getDefaultSharedPreferences(this).getString(
                "token", "defaultStringIfNothingFound");

        texto_cirurgia = (EditText) findViewById(R.id.editText_escolhaCirurgia);


        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),
                        DadosINtraOperatorioActivity.class);
                startActivity(i);
            }
        });

        textoCirurgiaAUsar = (TextView) findViewById(R.id.textViewCirurgia);




        Button btnAdd = (Button) findViewById(R.id.btnEscolhaCirurgia);
        btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new getAllCirurgias().execute();

                dialog = new Dialog(HomeActivity.this);

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
                                                getApplicationContext())
                                        .edit()
                                        .putString("idCirurgia",
                                                String.valueOf(c.getId()))
                                        .commit();
                                PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getApplicationContext()).edit()
                                        .putInt("idUtente", c.getIdUtente())
                                        .commit();
                                PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getApplicationContext())
                                        .edit()
                                        .putString("idEquipa",
                                                String.valueOf(c.getIdEquipa()))
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
                                        getApplicationContext()).getString(
                                        "idCirurgia",
                                        "defaultStringIfNothingFound");
                        textoCirurgiaAUsar.setText(cirurgia);
                    }
                });

               /* Intent i = new Intent(getApplicationContext(), ListaProdutosActivity.class);
                startActivity(i);*/

                /* Intent i = new Intent(getApplicationContext(), AparelhosActivity.class);
                startActivity(i);*/

               /*  Intent i = new Intent(getApplicationContext(), InstrumentalActivity.class);
                startActivity(i); */
            }
        });
    }

/*
	public void setListenersMenus() {

       /* TextView btnLista = (TextView) root
                .findViewById(R.id.textViewMenuListasMateriais);
        btnLista.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cirurgia != null) {
                    Intent equipa = new Intent(getBaseContext(),
                            ListaProdutosActivity.class);
                    toggleMenu(findViewById(R.layout.activity_lista_produtos));

                    startActivity(equipa);
                }

            }
        });


		btnEquipa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cirurgia != null) {
				Intent equipa = new Intent(getBaseContext(),
						EquipaCirurgica.class);
			//	toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));

				startActivity(equipa);
				} else {
					Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
					Log.i("sgmc", "Não tem cirurgia escolhida");
					root.toggleMenu();
				}

			}
		});
		TextView btnDados = (TextView) root
				.findViewById(R.id.textViewMenuDadosCirurgia);
		btnDados.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cirurgia != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosCirurgia.class);
				//	toggleMenu(findViewById(R.layout.activity_dados_cirurgia));

					startActivity(dados);
				} else {
					Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
					Log.i("sgmc", "Não tem cirurgia escolhida");
					root.toggleMenu();
				}
			}
		});
		TextView btnUtentes = (TextView) root
				.findViewById(R.id.textViewMenuUtentes);
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent utentes = new Intent(getBaseContext(),
						UtentesActivity.class);
				//toggleMenu(findViewById(R.layout.activity_utentes));
				startActivity(utentes);
			}
		});

		TextView btnDadosIntraOperatorio = (TextView) findViewById(R.id.textViewMenuDadosIntraOperatorio);
		btnDadosIntraOperatorio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (HomeActivity.getCirurgia() != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosINtraOperatorioActivity.class);
				//	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
					startActivity(dados);
                    //Toast.makeText(getApplicationContext(),"Não perca a próxima versão, porque nós também não!",Toast.LENGTH_SHORT).show();
                    root.toggleMenu();

				} else {
					Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
					Log.i("sgmc", "Não tem cirurgia escolhida");
                    Intent dados = new Intent(getBaseContext(),
                            DadosINtraOperatorioActivity.class);
                    //toggleMenu(findViewById(this));
                    startActivity(dados);
					root.toggleMenu();
				}

			}
		});
		TextView btnSair = (TextView) findViewById(R.id.textViewMenuSair);
		btnSair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext())
						.edit().clear().commit();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});

        TextView btnListasMateriais = (TextView) findViewById(R.id.textViewMenuListasMateriais);
        btnListasMateriais.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.getCirurgia() != null) {
                    Intent listasMat = new Intent(getBaseContext(),
                            ListaProdutosActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                    //Toast.makeText(getApplicationContext(),"N„o perca a prÛxima vers„o, porque nÛs tambÈm n„o!",Toast.LENGTH_SHORT).show();
                    root.toggleMenu();

                } else {
                    Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
                    Log.i("sgmc", "Não tem cirurgia escolhida");
                    Intent listasMat = new Intent(getBaseContext(),
                            ListaProdutosActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                    root.toggleMenu();
                }
            }
        });
        TextView btnAparelhosUtilizados= (TextView) findViewById(R.id.textViewMenuAparelhosCirurgia);
        btnAparelhosUtilizados.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.getCirurgia() != null) {
                    Intent listasMat = new Intent(getBaseContext(),
                            AparelhosActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                    //Toast.makeText(getApplicationContext(),"N„o perca a prÛxima vers„o, porque nÛs tambÈm n„o!",Toast.LENGTH_SHORT).show();
                    root.toggleMenu();

                } else {
                    Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
                    Log.i("sgmc", "Não tem cirurgia escolhida");
                    Intent listasMat = new Intent(getBaseContext(),
                            AparelhosActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);

                }
            }
        });

        TextView btnInstrumentosUtilizados= (TextView) findViewById(R.id.textViewMenuInstrumentalCirurgia);
        btnInstrumentosUtilizados.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.getCirurgia() != null) {
                    Intent listasMat = new Intent(getBaseContext(),
                            InstrumentalActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                    //Toast.makeText(getApplicationContext(),"N„o perca a prÛxima vers„o, porque nÛs tambÈm n„o!",Toast.LENGTH_SHORT).show();
                    root.toggleMenu();

                } else {
                    Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
                    Log.i("sgmc", "Não tem cirurgia escolhida");
                    Intent listasMat = new Intent(getBaseContext(),
                            InstrumentalActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                }
            }
        });

        TextView btnMaterialUtilizado = (TextView) findViewById(R.id.textViewMenuMaterialCirurgia);
        btnMaterialUtilizado.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.getCirurgia() != null) {
                    Intent listasMat = new Intent(getBaseContext(),
                            MaterialActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                    //Toast.makeText(getApplicationContext(),"N„o perca a prÛxima vers„o, porque nÛs tambÈm n„o!",Toast.LENGTH_SHORT).show();
                    root.toggleMenu();

                } else {
                    Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
                    Log.i("sgmc", "Não tem cirurgia escolhida");
                    Intent listasMat = new Intent(getBaseContext(),
                            MaterialActivity.class);
                    //	toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
                    startActivity(listasMat);
                }
            }
        });
	}*/

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

	private class getAllCirurgias extends
			AsyncTask<Cirurgia, Void, ArrayList<Cirurgia>> {
		@Override
		protected void onPreExecute() {

			ringProgressDialog = new ProgressDialog(HomeActivity.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Aguarde...");
			ringProgressDialog.setMessage("A carregar Dados...");

			ringProgressDialog.setCancelable(true);
			ringProgressDialog.setOnCancelListener(new OnCancelListener() {

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
				lista = WebServiceUtils.getAllCirurgias(token);
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
						getBaseContext(), android.R.layout.simple_list_item_1,
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
						getApplicationContext(),
						"Erro Get Cirurgias - Verifique a Internet e repita o Processo",
						Toast.LENGTH_SHORT).show();
			}
		}

	}





    @Override //The "layoutContainerId" should be used in "beginTransaction (). Replace"
    public void onItemClickNavigation(int position, int layoutContainerId) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        switch (position)
        {
            case 0:

                break;
            case 2:
                if (cirurgia != null) {
                    Intent equipa = new Intent(getBaseContext(),
                            EquipaCirurgica.class);
                    //	toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));

                    startActivity(equipa);
                } else {
                    Toast.makeText(getApplicationContext(), "Tem de selecionar uma cirurgia primeiro", Toast.LENGTH_SHORT).show();
                    Log.i("sgmc", "Não tem cirurgia escolhida");
                }
                break;

            default:
                break;

        }
    }

    @Override
    public void onClickUserPhotoNavigation(View v) {}

    @Override
    public void onClickFooterItemNavigation(View v) {}

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

        //hide the menu when the navigation is opens
        switch (position) {
            case 0:


            case 1:

                break;
        }
    }
    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText("Rudson Lima");
        this.mUserEmail.setText("rudsonlive@gmail.com");
        this.mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.mUserBackground.setImageResource(R.drawable.ic_user);

        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);
        this.addCustomHeader(mCustomHeader); //This will add the new header and remove the default user header
    }
    public static String getToken(){return token;}
}
