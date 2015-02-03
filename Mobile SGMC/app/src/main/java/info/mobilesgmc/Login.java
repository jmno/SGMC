package info.mobilesgmc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.json.JSONException;

import java.io.IOException;

import info.mobilesgmc.modelo.ProfissonalSaude;
import info.mobilesgmc.modelo.RestClientException;
import info.mobilesgmc.modelo.WebServiceUtils;
import info.nicolau.mobilegsmc.R;

public class Login extends Activity {
	EditText txtUser;
	EditText txtPass;
	ProgressDialog ringProgressDialog = null;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("Login");
//		startActivity(new Intent(this, HomeActivity.class));

		
		txtUser = (EditText) findViewById(R.id.editTextUsername);
		txtPass = (EditText) findViewById(R.id.editTextPassword);
        String token = " ";
        try{
           token = PreferenceManager.getDefaultSharedPreferences(this).getString("token", "defaultStringIfNothingFound");
            Log.i("token", token);
        }
        catch (Exception e)
        {
            Log.i("Erro", e.getMessage().toString());
        }
        if(!token.equals("defaultStringIfNothingFound"))
		new IsLoggedIN().execute(token);

        else {
            txtUser.setText("");
            txtPass.setText("");
        }

		Button btn_Login = (Button) findViewById(R.id.btn_Login);
		btn_Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (txtUser.getText().toString().trim().length()>0) {
					if (txtPass.getText().toString().trim().length()>0){
						new LogInWeb().execute(txtUser.getText().toString(),
								txtPass.getText().toString());}
				}

			}
		});

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	private class LogInWeb extends AsyncTask<String, Void, String> {

	
		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(Login.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Por Favor Espere...");
			ringProgressDialog.setMessage("A entrar...");
			
			//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
			ringProgressDialog.setCancelable(true);
			ringProgressDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					
				}
			});
			ringProgressDialog.show();
		};
		@Override
		protected String doInBackground(String... params) {
			String token = "";

			try {
				token = WebServiceUtils.logIn(params[0], params[1]);
			} catch (IOException | RestClientException | ParseException
					| JSONException e) {
				e.printStackTrace();
			}

			return token;
		}

		@Override
		protected void onPostExecute(String token) {
			if (token != null) {
				if (!token.isEmpty()) {

					// new Notifications(getApplicationContext(),
					// "Connexão Efetuada com Sucesso!");
					token = token.replace("\"", "");
                    String erro = token.substring(0,4);
                    if(!erro.equals("ERRO")) {
                        if (!token.equals("ERRO")) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("token", token).commit();
                            ringProgressDialog.dismiss();
                            new GetDadosProfissional().execute(token);
                        } else {
                            ringProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Erro Utilizador/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        ringProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
                    }
				}
			} else {
                    ringProgressDialog.dismiss();
			}
		}
	}
    private class GetDadosProfissional extends AsyncTask<String, Void, ProfissonalSaude> {


        @Override
        protected void onPreExecute() {
            ringProgressDialog = new ProgressDialog(Login.this);
            ringProgressDialog.setIcon(R.drawable.ic_launcher);
            ringProgressDialog.setTitle("Por Favor Espere...");
            ringProgressDialog.setMessage("A verificar dados de utilizador...");

            //ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
            ringProgressDialog.setCancelable(true);
            ringProgressDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);

                }
            });
            ringProgressDialog.show();
        };
        @Override
        protected ProfissonalSaude doInBackground(String... params) {
            ProfissonalSaude pro = new ProfissonalSaude();

            try {
                pro = WebServiceUtils.getDadosProfissional(params[0]);
            } catch (IOException | RestClientException | ParseException
                    | JSONException e) {
                e.printStackTrace();
            }

            return pro;
        }

        @Override
        protected void onPostExecute(ProfissonalSaude pro) {
            if (pro != null) {
               PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("username", pro.getNome()).commit();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("cc", pro.getCc()+"").commit();

                ringProgressDialog.dismiss();
                            Intent home = new Intent(getBaseContext(),
                                    HomeActivity.class);
                            startActivity(home);

            } else {
                ringProgressDialog.dismiss();
            }
        }
    }
	private class IsLoggedIN extends AsyncTask<String, Void, Boolean> {

		
		@Override
		protected void onPreExecute() {
			ringProgressDialog = new ProgressDialog(Login.this);
			ringProgressDialog.setIcon(R.drawable.ic_launcher);
			ringProgressDialog.setTitle("Please wait...");
			ringProgressDialog.setMessage("Checking LogIn...");
			
			//ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...",	"Loging in...", true);
			ringProgressDialog.setCancelable(false);
			ringProgressDialog.show();
		};
		@Override
		protected Boolean doInBackground(String... params) {
			Boolean resultado= false;

			try {
                if(!params[0].toLowerCase().equals("erro no LogIn"))
				resultado = WebServiceUtils.isLoggedIn(params[0]);
			} catch (ParseException | IOException | RestClientException e) {
				e.printStackTrace();
			}

			return resultado;
		}

		@Override
		protected void onPostExecute(Boolean resultado) {
			if (resultado) {
				

					// new Notifications(getApplicationContext(),
					// "Connexão Efetuada com Sucesso!");
					  
					ringProgressDialog.dismiss();
					Intent equipa = new Intent(getBaseContext(),
							HomeActivity.class);
					startActivity(equipa);
			} else {
				Toast.makeText(getApplicationContext(), "Sessão expirada!", Toast.LENGTH_SHORT).show();
				PreferenceManager
				.getDefaultSharedPreferences(
						getApplicationContext())
				.edit().clear().commit();
				ringProgressDialog.dismiss();


			}
		}
	}
}
