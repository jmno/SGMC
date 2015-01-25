package pt.mobilesgmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.w3c.dom.Text;

import pt.mobilesgmc.modelo.Cirurgia;
import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.mobilegsmc.R;
import com.example.mobilegsmc.R.menu;

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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		token = PreferenceManager.getDefaultSharedPreferences(this).getString(
				"token", "defaultStringIfNothingFound");

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_home, null);
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;
		int margin = (80 * (int) dpWidth) / 100;
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

<<<<<<< HEAD
		TextView btnEquipa = (TextView) root.findViewById(R.id.textViewMenuEquipaCirurgica);
		btnEquipa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent equipa = new Intent(getBaseContext(),
						EquipaCirurgica.class);
				toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));

				startActivity(equipa);

			}
		});
		TextView btnDados = (TextView) root.findViewById(R.id.textViewMenuDadosCirurgia);
		btnDados.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cirurgia != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosCirurgia.class);
					toggleMenu(findViewById(R.layout.activity_dados_cirurgia));

					startActivity(dados);
				} else {
					Log.i("sgmc", "NÃ£o tem cirurgia escolhida");
					root.toggleMenu();
				}
			}
		});
		TextView btnUtentes = (TextView) root.findViewById(R.id.textViewMenuUtentes);
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent utentes = new Intent(getBaseContext(),
						UtentesActivity.class);
				toggleMenu(findViewById(R.layout.activity_utentes));
				startActivity(utentes);
			}
		});
=======
		setListenersMenus();
>>>>>>> FETCH_HEAD
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(), DadosINtraOperatorioActivity.class);
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
				listaCirurgias = (ListView) dialog
						.findViewById(R.id.listView_cirurgias);

				listaCirurgias
						.setOnItemClickListener(new OnItemClickListener() {

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

				dialog.setOnDismissListener(new OnDismissListener() {

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
			}
		});

	}
	
	public void setListenersMenus(){
		
		TextView btnEquipa = (TextView) root.findViewById(R.id.textViewMenuEquipaCirurgica);
		btnEquipa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent equipa = new Intent(getBaseContext(),
						EquipaCirurgica.class);
				toggleMenu(findViewById(R.layout.activity_equipa_cirurgica));

				startActivity(equipa);

			}
		});
		TextView btnDados = (TextView) root.findViewById(R.id.textViewMenuDadosCirurgia);
		btnDados.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cirurgia != null) {
					Intent dados = new Intent(getBaseContext(),
							DadosCirurgia.class);
					toggleMenu(findViewById(R.layout.activity_dados_cirurgia));

					startActivity(dados);
				} else {
					Log.i("sgmc", "Não tem cirurgia escolhida");
					root.toggleMenu();
				}
			}
		});
		TextView btnUtentes = (TextView) root.findViewById(R.id.textViewMenuUtentes);
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent utentes = new Intent(getBaseContext(),
						UtentesActivity.class);
				toggleMenu(findViewById(R.layout.activity_utentes));
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
					toggleMenu(findViewById(R.layout.activity_dados_intra_operatorio));
					startActivity(dados);
					
				} else {
					Log.i("sgmc", "Não tem cirurgia escolhida");
					root.toggleMenu();
				}
				
			}
		});
		TextView btnSair = (TextView) findViewById(R.id.textViewMenuSair);
		btnSair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();}
		});
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
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		// super.onBackPressed(); // optional depending on your needs
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

			// ringProgressDialog = ProgressDialog.show(Login.this,
			// "Please wait ...", "Loging in...", true);
			ringProgressDialog.setCancelable(false);

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
						return (""+lhs.getId())
								.compareTo((""+rhs.getId()));
					}
				});
				listaCirurgias.setAdapter(adaptadorCirurgias);
				ringProgressDialog.dismiss();
				dialog.show();
			}
		}

	}

}
