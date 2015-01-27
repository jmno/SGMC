package pt.mobilesgmc;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilegsmc.R;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;

public class HomeActivity extends Activity {

	FlyOutContainer root;
	String token;
	private ArrayAdapter<Cirurgia> adaptadorCirurgias;
	private Dialog dialog;
	public static ListView listaCirurgias;
	public static EditText texto_cirurgia;
	public static TextView textoCirurgiaAUsar;
	private static Cirurgia cirurgia;
	ProgressDialog ringProgressDialog = null;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button buttonSpeech;



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        //View v =  findViewById(R.layout.activity_home);

        setTitle("Ecrã Principal");
		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_home, null);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;
		int margin = ((80 * (int) dpWidth) / 100)-50;
		root.setMargin(margin);
		this.setContentView(root);

		root.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeTop() {
				// Toast.makeText(SampleActivity.this, "top",
				// Toast.LENGTH_SHORT).show();
			}

			public void onSwipeRight() {
				String estado = root.getState().toString();
				if (estado.equals("CLOSED"))
					toggleMenu(findViewById(R.layout.activity_home));
				// Toast.makeText(SampleActivity.this, "right",
				// Toast.LENGTH_SHORT).show();
			}

			public void onSwipeLeft() {
				String estado = root.getState().toString();
				if (estado.equals("OPEN"))
                    toggleMenu(findViewById(R.layout.activity_home));
			}

			public void onSwipeBottom() {
				// Toast.makeText(SampleActivity.this, "bottom",
				// Toast.LENGTH_SHORT).show();
			}

			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}

		});
		texto_cirurgia = (EditText) root
				.findViewById(R.id.editText_escolhaCirurgia);

		setListenersMenus();

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

		textoCirurgiaAUsar = (TextView) root
				.findViewById(R.id.textViewCirurgia);




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

        buttonSpeech = (Button) findViewById(R.id.button1);
        buttonSpeech.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
	}

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
        });*/

		TextView btnEquipa = (TextView) root
				.findViewById(R.id.textViewMenuEquipaCirurgica);
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
                Intent dados = new Intent(getBaseContext(),
                        DadosCirurgia.class);
                root.toggleMenu();
				if (cirurgia == null) {
                    cirurgia = new Cirurgia();
                    cirurgia.setId(0);
                }

                startActivity(dados);
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
                 /*   Intent dados = new Intent(getBaseContext(),
                            DadosINtraOperatorioActivity.class);
                    //toggleMenu(findViewById(this));
                    startActivity(dados);*/
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
	}


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.i("Speech", result.get(0));
                }
                break;
            }

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

    @Override
    protected void onResume() {
        if(cirurgia!=null)
            textoCirurgiaAUsar.setText(cirurgia.getId()+"");
        super.onResume();

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

}
