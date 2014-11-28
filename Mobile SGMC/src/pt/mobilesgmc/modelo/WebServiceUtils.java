package pt.mobilesgmc.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Telephony.Sms.Conversations;
import android.text.method.DateTimeKeyListener;
import pt.mobilesgmc.Login;
import pt.mobilesgmc.modelo.*;

public class WebServiceUtils {

	public static String URL = "https://sgmc.apphb.com/Service1.svc/REST/";

	public static LinkedList<ProfissonalSaude> listaProfissionaisSaude = new LinkedList<ProfissonalSaude>();
	public static LinkedList<Tipo> listaTipos = new LinkedList<Tipo>();
	public static LinkedList<Utente> listaUtentes = new LinkedList<>();

	public static ArrayList<ProfissonalSaude> getAllProfissionalSaude(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<ProfissonalSaude> profissionaisSaude = null;

		HttpGet request = new HttpGet(URL + "getProfissionalSaude?token="+token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
		HttpClient client = new DefaultHttpClient();

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			profissionaisSaude = new ArrayList<ProfissonalSaude>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				ProfissonalSaude p = new ProfissonalSaude();
				p.setCc(o.getString("cc"));
				p.setId(Integer.parseInt(o.getString("id")));
				p.setNome(o.getString("nome"));
				profissionaisSaude.add(p);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return profissionaisSaude;

	}

	public static Boolean adicionarProfissionalSaude(
			ProfissonalSaude profissional) throws ClientProtocolException,
			IOException, ParseException, JSONException, RestClientException {
		Boolean adicionou = false;

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cc", profissional.getCc());
			jsonObject.put("id", 1);

			jsonObject.put("idTipo", profissional.getIdTipo());

			jsonObject.put("nome", profissional.getNome());

			HttpPost httpPost = new HttpPost(URL + "addProfissionalSaude");
			StringEntity se = new StringEntity(jsonObject.toString());

			se.setContentType("text/json");
			httpPost.setEntity(se);
			HttpClient httpClient = new DefaultHttpClient();
			BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
					.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String string = EntityUtils.toString(entity);
				adicionou = Boolean.valueOf(string);
			} else {
				throw new RestClientException(
						"HTTP Response with invalid status code"
								+ httpResponse.getStatusLine().getStatusCode()
								+ ".");

			}


		return adicionou;
	}

	public static ArrayList<Tipo> getAllTipo() throws ClientProtocolException,
			IOException, ParseException, JSONException, RestClientException {
		ArrayList<Tipo> listaTipos = null;

		HttpGet request = new HttpGet(URL + "getAllTipos");
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
		HttpClient client = new DefaultHttpClient();

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			listaTipos = new ArrayList<Tipo>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Tipo p = new Tipo();
				p.setDescricao(o.getString("descricao"));
				p.setId(Integer.parseInt(o.getString("id")));
				listaTipos.add(p);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return listaTipos;
	}
	public static ArrayList<ProfissonalSaude> getAllProfissionalSaudeByIdTipo(int idTipo, String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<ProfissonalSaude> profissionaisSaude = null;

		HttpGet request = new HttpGet(URL + "getProfissionaisByIdTipo" + "?idTipo=" + idTipo+"&token="+token);
		
		request.setHeader("Accept", "Application/JSON");
		HttpClient client = new DefaultHttpClient();

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			profissionaisSaude = new ArrayList<ProfissonalSaude>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				ProfissonalSaude p = new ProfissonalSaude();
				p.setCc(o.getString("cc"));
				p.setId(Integer.parseInt(o.getString("id")));
				p.setNome(o.getString("nome"));
				profissionaisSaude.add(p);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return profissionaisSaude;

	}
	
	public static ArrayList<Utente> getAllUtentes()
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<Utente> utentes = null;
		Date data = null;
		HttpGet request = new HttpGet(URL + "getAllUtentes");
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
		HttpClient client = new DefaultHttpClient();

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			utentes = new ArrayList<Utente>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
			
				Utente u = new Utente();
				u.setId(Integer.parseInt(o.getString("id")));
				u.setNome(o.getString("nomeUtente"));
				u.setNumProcesso(Integer.parseInt(o.getString("numProcesso")));
				String dataNas = o.getString("dataNascimento");
				data = JsonDateToDate(dataNas);
				
				u.setDataNascimento(data);
				//u.setDataNascimento(Date.parse(o.getString("dataNascimento")));
				u.setSubsistema(o.getString("subsistema"));
				u.setAlergias(o.getString("alergias"));
				u.setPatologias(o.getString("patologias"));
				u.setAntecedentesCirurgicos(o.getString("antecendentesCirurgicos"));
			
				utentes.add(u);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return utentes;

	}
	
	 public static Date JsonDateToDate(String jsonDate)
	  {
	    //  "/Date(1321867151710+0100)/"
	    int idx1 = jsonDate.indexOf("(");
	    int idx2 = jsonDate.indexOf(")") - 5;
	    String s = jsonDate.substring(idx1+1, idx2);
	    long l = Long.valueOf(s);
	    return new Date(l);
	  }

	 public static String logIn(String username, String password)
				throws ClientProtocolException, IOException, RestClientException,
				ParseException, JSONException {
		 	String token = "";
		 	
			HttpPost httpPost = new HttpPost(URL + "login?username="+username + "&password="+password);
			
			HttpClient httpClient = new DefaultHttpClient();
			BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
					.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				token = EntityUtils.toString(entity);
				
			}
			else {
				throw new RestClientException(
						"HTTP Response with invalid status code "
								+ httpResponse.getStatusLine().getStatusCode()
								+ ".");
			}

			return token;

		}
	

}
