package pt.mobilesgmc;

import java.io.IOException;

import org.apache.http.ParseException;
import org.json.JSONException;

import pt.mobilesgmc.modelo.RestClientException;
import pt.mobilesgmc.modelo.WebServiceUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobilegsmc.R;

public class Login extends Activity {
	EditText txtUser;
	EditText txtPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		txtUser = (EditText) findViewById(R.id.editTextUsername);
		txtPass = (EditText) findViewById(R.id.editTextPassword);

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
		getMenuInflater().inflate(R.menu.login, menu);
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
					
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("token", token).commit();  

					Intent equipa = new Intent(getBaseContext(),
							HomeActivity.class);
					startActivity(equipa);
				}
			} else {

			}
		}
	}
}
