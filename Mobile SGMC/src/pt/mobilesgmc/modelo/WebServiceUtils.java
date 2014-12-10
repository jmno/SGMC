package pt.mobilesgmc.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceUtils {

	public static String URL = "https://sgmc.apphb.com/Service1.svc/REST/";

	public static LinkedList<ProfissonalSaude> listaProfissionaisSaude = new LinkedList<ProfissonalSaude>();
	public static LinkedList<Tipo> listaTipos = new LinkedList<Tipo>();
	public static LinkedList<Utente> listaUtentes = new LinkedList<>();
<<<<<<< HEAD
	public static HttpClient client = new DefaultHttpClient();
	public static ArrayList<ProfissonalSaude> getAllProfissionalSaude(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
=======

	public static ArrayList<ProfissonalSaude> getAllProfissionalSaude(
			String token) throws ClientProtocolException, IOException,
			RestClientException, ParseException, JSONException {
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec
		ArrayList<ProfissonalSaude> profissionaisSaude = null;

		HttpGet request = new HttpGet(URL + "getProfissionalSaude?token="
				+ token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
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

	public static ArrayList<Cirurgia> getAllCirurgias(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<Cirurgia> cirurgias = null;
		Date data = null;

		HttpGet request = new HttpGet(URL + "getAllCirurgias?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			cirurgias = new ArrayList<Cirurgia>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Cirurgia c = new Cirurgia();
				c.setId(Integer.parseInt(o.getString("id")));
				c.setEspecialidade(o.getString("especialidade"));
				c.setCirurgia(o.getString("cirurgia"));
				data = JsonDateToDate(o.getString("data"));
				c.setData(data);

				c.setHora(JsonTimeToTime(o.getString("hora")));
				c.setTipoCirurgia(o.getString("tipoCirurgia"));
				c.setLateralidade(o.getString("lateralidade"));
				c.setClassificacaoASA(o.getString("classifASA"));
				c.setHoraChamadaUtente(JsonTimeToTime(o
						.getString("horaChamadaUtente")));
				c.setHoraEntradaBlocoOperatorio(JsonTimeToTime(o
						.getString("horaEntradaBO")));
				c.setHoraSaideBlocoOperatorio(JsonTimeToTime(o
						.getString("horaSaidaBO")));
				c.setHoraEntradaSala(JsonTimeToTime(o
						.getString("horaEntradaSala")));
				c.setHoraSaidaSala(JsonTimeToTime(o.getString("horaSaidaSala")));
				c.setHoraInicioAnestesia(JsonTimeToTime(o
						.getString("horaInicioAnestesia")));
				c.setHoraFimAnestesia(JsonTimeToTime(o
						.getString("horaFimAnestesia")));
				c.setHoraInicioCirurgia(JsonTimeToTime(o
						.getString("horaInicioCirurgia")));
				c.setHoraFimCirurgia(JsonTimeToTime(o
						.getString("horaFimCirurgia")));
				c.setHoraEntradaRecobro(JsonTimeToTime(o
						.getString("horaEntradaRecobro")));
				c.setHoraFimRecobro(JsonTimeToTime(o
						.getString("horaSaidaRecobro")));
				c.setDestinoDoente(o.getString("destinoDoente"));
				c.setInfoRelevante(o.getString("infoRelevante"));
				c.setIdSala(Integer.parseInt(o.getString("idSala")));
				c.setIdUtente(Integer.parseInt(o.getString("idUtente")));

				cirurgias.add(c);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return cirurgias;

	}

	public static Boolean adicionarProfissionalSaude(
			ProfissonalSaude profissional, String token)
			throws ClientProtocolException, IOException, ParseException,
			JSONException, RestClientException {
		Boolean adicionou = false;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cc", profissional.getCc());
		jsonObject.put("id", 1);

		jsonObject.put("idTipo", profissional.getIdTipo());

		jsonObject.put("nome", profissional.getNome());

		HttpPost httpPost = new HttpPost(URL + "addProfissionalSaude?token="
				+ token);
		StringEntity se = new StringEntity(jsonObject.toString());

<<<<<<< HEAD
			se.setContentType("text/json");
			httpPost.setEntity(se);
			BasicHttpResponse httpResponse = (BasicHttpResponse) client
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
=======
		se.setContentType("text/json");
		httpPost.setEntity(se);
		HttpClient httpClient = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
				.execute(httpPost);
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec

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

	public static ArrayList<Tipo> getAllTipo(String token)
			throws ClientProtocolException, IOException, ParseException,
			JSONException, RestClientException {
		ArrayList<Tipo> listaTipos = null;

		HttpGet request = new HttpGet(URL + "getAllTipos?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
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

	public static ArrayList<ProfissonalSaude> getAllProfissionalSaudeByIdTipo(
			int idTipo, String token) throws ClientProtocolException,
			IOException, RestClientException, ParseException, JSONException {
		ArrayList<ProfissonalSaude> profissionaisSaude = null;

		HttpGet request = new HttpGet(URL + "getProfissionaisByIdTipo"
				+ "?idTipo=" + idTipo + "&token=" + token);

		request.setHeader("Accept", "Application/JSON");

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

	public static ArrayList<Utente> getAllUtentes(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<Utente> utentes = null;
		Date data = null;
		HttpGet request = new HttpGet(URL + "getAllUtentes?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

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
				// u.setDataNascimento(Date.parse(o.getString("dataNascimento")));
				u.setSubsistema(o.getString("subsistema"));
				u.setAlergias(o.getString("alergias"));
				u.setPatologias(o.getString("patologias"));
				u.setAntecedentesCirurgicos(o
						.getString("antecendentesCirurgicos"));

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

	public static Date JsonDateToDate(String jsonDate) {
		// "/Date(1321867151710+0100)/"
		int idx1 = jsonDate.indexOf("(");
		int idx2 = jsonDate.indexOf(")") - 5;
		String s = jsonDate.substring(idx1 + 1, idx2);
		long l = Long.valueOf(s);
		return new Date(l);
	}

	public static java.sql.Time JsonTimeToTime(String jsonTime) {
		int horas = 0;
		int minutos = 0;
		int segundos = 0;
		try {
			horas = Integer.valueOf(jsonTime.substring(2, 4));
			minutos = Integer.valueOf(jsonTime.substring(5, 7));
			segundos = Integer.valueOf(jsonTime.substring(8, 10));
		} catch (Exception e) {

		}
<<<<<<< HEAD
		 java.sql.Time t = new java.sql.Time(horas,minutos ,segundos );
		 
		 
		 return t;
	 }
	
	 
	

	 public static String logIn(String username, String password)
				throws ClientProtocolException, IOException, RestClientException,
				ParseException, JSONException {
		 	String token = "";
		 	
			HttpPost httpPost = new HttpPost(URL + "login?username="+username + "&password="+password);
			
			BasicHttpResponse httpResponse = (BasicHttpResponse) client
					.execute(httpPost);
=======
		java.sql.Time t = new java.sql.Time(horas, minutos, segundos);
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec

		return t;
	}

	public static String logIn(String username, String password)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		String token = "";

		HttpPost httpPost = new HttpPost(URL + "login?username=" + username
				+ "&password=" + password);

		HttpClient httpClient = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
				.execute(httpPost);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			token = EntityUtils.toString(entity);

		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ httpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return token;

	}

	public static Boolean isLoggedIn(String token)
			throws ClientProtocolException, IOException, RestClientException {
		Boolean resultado = false;

		HttpGet request = new HttpGet(URL + "isLoggedIn?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {

			HttpEntity entity = basicHttpResponse.getEntity();
			resultado = Boolean.valueOf(EntityUtils.toString(entity));

		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return resultado;
	}

	public static Boolean adicionarEquipa(

			
<<<<<<< HEAD
			//INT = ADICONAR EQUIPA
			//BOOL => FOREACH ELEMENTO LISTA ADICIONARA JUNCAO EQUIPA
			String nomeEquipa,int idCirurgia,String token) throws ClientProtocolException,
=======
			List<ProfissionalDaCirurgia> lista, String nomeEquipa,
			int idCirurgia, String token) throws ClientProtocolException,
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec
			IOException, ParseException, JSONException, RestClientException {

		Boolean adicionou = false;
		int idEquipa;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("idCirurgia", idCirurgia);
		jsonObject.put("nomeEquipa", nomeEquipa);

		HttpPost httpPost = new HttpPost(URL + "addEquipaCirurgica?token="
				+ token);
		StringEntity se = new StringEntity(jsonObject.toString());

		se.setContentType("text/json");
		httpPost.setEntity(se);
		HttpClient httpClient = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
				.execute(httpPost);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			Boolean string = Boolean.valueOf(EntityUtils.toString(entity));
			// idEquipa = Integer.valueOf(string);
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code"
							+ httpResponse.getStatusLine().getStatusCode()
							+ ".");

		}

		HttpGet request = new HttpGet(URL + "getIdEquipaByNome?token=" + token
				+ "&nome=" + nomeEquipa);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		HttpClient client = new DefaultHttpClient();

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			int i = Integer.parseInt(EntityUtils.toString(entity));
			idEquipa = i;
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code"
							+ httpResponse.getStatusLine().getStatusCode()
							+ ".");

		}

		httpPost = new HttpPost(URL + "addJuncaoEquipaCirurgica?token=" + token);
		for (ProfissionalDaCirurgia profissionalDaCirurgia : lista) {
			jsonObject = new JSONObject();
			jsonObject.put("idEquipa", idEquipa);
			jsonObject.put("idProfissional", profissionalDaCirurgia
					.getProfissional().getId());
			jsonObject
					.put("tipoProfissional", profissionalDaCirurgia.getTipo());

<<<<<<< HEAD
			HttpPost httpPost = new HttpPost(URL + "addEquipaCirurgica?token="+token);
			StringEntity se = new StringEntity(jsonObject.toString());
=======
			se = new StringEntity(jsonObject.toString());
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec

			se.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			httpPost.setEntity(se);
<<<<<<< HEAD
			BasicHttpResponse httpResponse = (BasicHttpResponse) client
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
	
	public static int getEquipaID(String nomeEquipa, String token) throws ParseException, IOException, RestClientException
	{
		int resultado = 0;
		
		HttpGet request = new HttpGet(URL + "getIdEquipaByNome?token=" + token+"&nome="+nomeEquipa);
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = basicHttpResponse.getEntity();
			String string = EntityUtils.toString(entity);
			resultado = Integer.valueOf(string);
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code"
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");

		}
		
		return resultado;
	}
	
	public static Boolean adicionarJuncoes(List<ProfissionalDaCirurgia> lista,int idEquipa, String token) throws ClientProtocolException, IOException, RestClientException, JSONException
	{
		Boolean adicionou = false;
		HttpPost httpPost = new HttpPost(URL + "addJuncaoEquipaCirurgica?token=" + token);
		for (ProfissionalDaCirurgia profissionalDaCirurgia : lista) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("idEquipa", idEquipa);
			jsonObject.put("idProfissional", profissionalDaCirurgia.getProfissional().getId());
			jsonObject.put("tipoProfissional", profissionalDaCirurgia.getTipo());
			
			StringEntity se = new StringEntity(jsonObject.toString());
			se.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
            
			httpPost.setEntity(se);
			BasicHttpResponse httpResponse = (BasicHttpResponse) client
					.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
=======
			httpClient = new DefaultHttpClient();
			httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec
				String string = EntityUtils.toString(entity);
				adicionou = Boolean.valueOf(string);
			} else {
				throw new RestClientException(
						"HTTP Response with invalid status code"
								+ httpResponse.getStatusLine().getStatusCode()
								+ ".");

			}
		}
<<<<<<< HEAD
		
=======

>>>>>>> 7b09f7b700c28750797dc36c9ed727ce6906b3ec
		return adicionou;
		

		
	}

	public static ArrayList<EquipaComJuncao> getAllEquipas(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<EquipaComJuncao> equipas = null;
		LinkedList<ProfissionalDaCirurgia> lista = null;
		LinkedList<ProfissonalSaude> listaProfissonalSaudes = null;
		Date data = null;
		HttpGet request = new HttpGet(URL + "getAllEquipas?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			equipas = new ArrayList<EquipaComJuncao>();
			listaProfissonalSaudes = new LinkedList<ProfissonalSaude>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				lista = new LinkedList<ProfissionalDaCirurgia>();

				JSONObject o = array.getJSONObject(i);

				EquipaComJuncao equipa = new EquipaComJuncao();
				equipa.setIdEquipa(Integer.parseInt(o.getString("idEquipa")));
				equipa.setNomeEquipa(o.getString("nomeEquipa"));
				JSONArray arrayComProfissionais = o
						.getJSONArray("profissional");

				for (int j = 0; j < arrayComProfissionais.length(); j++) {
					JSONObject novoProfissional = arrayComProfissionais
							.getJSONObject(j);
					// JSONObject objeto =
					// novoArrayProfissionais.getJSONObject(j);
					ProfissionalDaCirurgia proCir = new ProfissionalDaCirurgia();
					JSONObject n = novoProfissional
							.getJSONObject("profissional");

					proCir.setTipo(novoProfissional.getString("tipo"));
					ProfissonalSaude pro = new ProfissonalSaude();
					pro.setId(n.getInt("id"));
					pro.setCc(n.getString("cc"));
					pro.setIdTipo(n.getInt("idTipo"));
					pro.setNome(n.getString("nome"));

					proCir.setProfissional(pro);
					lista.add(proCir);

				}

				equipa.setListaProfissionais(lista);
				equipas.add(equipa);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return equipas;

	}

}
