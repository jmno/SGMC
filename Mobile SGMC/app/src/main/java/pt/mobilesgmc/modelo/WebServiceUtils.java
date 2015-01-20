package pt.mobilesgmc.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WebServiceUtils {

	public static String URL = "https://sgmc.apphb.com/Service1.svc/REST/";

	public static LinkedList<ProfissonalSaude> listaProfissionaisSaude = new LinkedList<ProfissonalSaude>();
	public static LinkedList<Tipo> listaTipos = new LinkedList<Tipo>();
	public static LinkedList<Utente> listaUtentes = new LinkedList<>();
	public static HttpClient client = new DefaultHttpClient();


	public static ArrayList<ProfissonalSaude> getAllProfissionalSaude(
			String token) throws ClientProtocolException, IOException,
			RestClientException, ParseException, JSONException {
		ArrayList<ProfissonalSaude> profissionaisSaude = null;

		HttpGet request = new HttpGet(URL + "getProfissionalSaude?token="
				+ token);
		request.setHeader("Accept", "Application/JSON");
		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			profissionaisSaude = new ArrayList<ProfissonalSaude>();
			Type collectionType = new TypeToken<ArrayList<ProfissonalSaude>>() {
			}.getType();
			profissionaisSaude = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

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
		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			cirurgias = new ArrayList<Cirurgia>();
			Type collectionType = new TypeToken<ArrayList<Cirurgia>>() {
			}.getType();
			cirurgias = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

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
		Gson g = new Gson();

		HttpPost httpPost = new HttpPost(URL + "addProfissionalSaude?token="
				+ token);
		StringEntity se = new StringEntity(g.toJson(profissional), "UTF-8");
		se.setContentType("text/json");
		se.setContentType("application/json;charset=UTF-8");

		httpPost.setEntity(se);
		BasicHttpResponse httpResponse = (BasicHttpResponse) client
				.execute(httpPost);

		if (isOk(httpResponse.getStatusLine().getStatusCode())) {
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

		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			listaTipos = new ArrayList<Tipo>();
			Type collectionType = new TypeToken<ArrayList<Tipo>>() {
			}.getType();
			listaTipos = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

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

		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			profissionaisSaude = new ArrayList<ProfissonalSaude>();
			Type collectionType = new TypeToken<ArrayList<ProfissonalSaude>>() {
			}.getType();
			profissionaisSaude = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

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

		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			utentes = new ArrayList<Utente>();
			Type collectionType = new TypeToken<ArrayList<Utente>>() {
			}.getType();
			utentes = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return utentes;

	}

	public static String logIn(String username, String password)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		String token = "";

		HttpPost httpPost = new HttpPost(URL + "login?username=" + username
				+ "&password=" + password);

        client = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) client
				.execute(httpPost);

		if (isOk(httpResponse.getStatusLine().getStatusCode())) {
			HttpEntity entity = httpResponse.getEntity();
			token = EntityUtils.toString(entity);

		}
        else if(httpResponse.getStatusLine().getStatusCode() == 500)
        {
            token = "ERRO";
        }else {
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

        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 15000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 15000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        client = new DefaultHttpClient(httpParameters);

        HttpGet request = new HttpGet(URL + "isLoggedIn?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {

			HttpEntity entity = basicHttpResponse.getEntity();
			resultado = Boolean.valueOf(EntityUtils.toString(entity));

		}
        else if(basicHttpResponse.getStatusLine().getStatusCode() == 500)
        {
            resultado = false;
        }
        else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return resultado;
	}

	public static Boolean adicionarEquipa(

	String nomeEquipa, int idCirurgia, String token)
			throws ClientProtocolException, IOException, ParseException,
			JSONException, RestClientException {

		Boolean adicionou = false;
		int idEquipa;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("idCirurgia", idCirurgia);
		jsonObject.put("nomeEquipa", nomeEquipa);

		HttpPost httpPost = new HttpPost(URL + "addEquipaCirurgica?token="
				+ token);
		StringEntity se = new StringEntity(jsonObject.toString(), "UTF-8");

		se.setContentType("application/json;charset=UTF-8");// text/plain;charset=UTF-8
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json;charset=UTF-8"));

		httpPost.setEntity(se);
		BasicHttpResponse httpResponse = (BasicHttpResponse) client
				.execute(httpPost);
		if (isOk(httpResponse.getStatusLine().getStatusCode())) {
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

	public static int getEquipaID(String nomeEquipa, String token)
			throws ParseException, IOException, RestClientException {
		int resultado = 0;
		HttpGet request = new HttpGet(URL + "getIdEquipaByNome?token=" + token
				+ "&nome=" + nomeEquipa.replaceAll("\\s","+"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
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

	public static Boolean adicionarJuncoes(List<ProfissionalDaCirurgia> lista,
			int idEquipa, String token) throws ClientProtocolException,
			IOException, RestClientException, JSONException {
		Boolean adicionou = false;
		HttpPost httpPost = new HttpPost(URL
				+ "addJuncaoEquipaCirurgica?token=" + token);
		for (ProfissionalDaCirurgia profissionalDaCirurgia : lista) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("idEquipa", idEquipa);
			jsonObject.put("idProfissional", profissionalDaCirurgia
					.getProfissional().getId());
			jsonObject
					.put("tipoProfissional", profissionalDaCirurgia.getTipo());

			StringEntity se = new StringEntity(jsonObject.toString(), "UTF-8");
			se.setContentType("application/json;charset=UTF-8");// text/plain;charset=UTF-8
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));

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

			}
		}

		return adicionou;

	}

	public static ArrayList<EquipaComJuncao> getAllEquipas(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<EquipaComJuncao> equipas = null;
		LinkedList<ProfissionalDaCirurgia> lista = null;
		LinkedList<ProfissonalSaude> listaProfissonalSaudes = null;
		HttpGet request = new HttpGet(URL + "getAllEquipas?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			equipas = new ArrayList<EquipaComJuncao>();
			Type collectionType = new TypeToken<ArrayList<EquipaComJuncao>>() {
			}.getType();
			equipas = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);

			// }
			// if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			// equipas = new ArrayList<EquipaComJuncao>();
			// listaProfissonalSaudes = new LinkedList<ProfissonalSaude>();
			// JSONArray array = new JSONArray(
			// EntityUtils.toString(basicHttpResponse.getEntity()));
			// for (int i = 0; i < array.length(); i++) {
			// lista = new LinkedList<ProfissionalDaCirurgia>();
			//
			// JSONObject o = array.getJSONObject(i);
			//
			// EquipaComJuncao equipa = new EquipaComJuncao();
			// equipa.setIdEquipa(Integer.parseInt(o.getString("idEquipa")));
			// equipa.setNomeEquipa(o.getString("nomeEquipa"));
			// JSONArray arrayComProfissionais = o
			// .getJSONArray("profissional");
			//
			// for (int j = 0; j < arrayComProfissionais.length(); j++) {
			// JSONObject novoProfissional = arrayComProfissionais
			// .getJSONObject(j);
			// // JSONObject objeto =
			// // novoArrayProfissionais.getJSONObject(j);
			// ProfissionalDaCirurgia proCir = new ProfissionalDaCirurgia();
			// JSONObject n = novoProfissional
			// .getJSONObject("profissional");
			//
			// proCir.setTipo(novoProfissional.getString("tipo"));
			// ProfissonalSaude pro = new ProfissonalSaude();
			// pro.setId(n.getInt("id"));
			// pro.setCc(n.getString("cc"));
			// pro.setIdTipo(n.getInt("idTipo"));
			// pro.setNome(n.getString("nome"));
			//
			// proCir.setProfissional(pro);
			// lista.add(proCir);
			//
			// }
			//
			// equipa.setListaProfissionais(lista);
			// equipas.add(equipa);
			// }
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return equipas;

	}

	public static EquipaComJuncao getEquipaByID(int idEquipa, String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		EquipaComJuncao equipa = null;
		LinkedList<ProfissionalDaCirurgia> lista = null;
		LinkedList<ProfissonalSaude> listaProfissonalSaudes = null;
		Date data = null;
		HttpGet request = new HttpGet(URL + "getEquipaJuncaoById?token="
				+ token + "&id=" + idEquipa);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {

			equipa = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					EquipaComJuncao.class);

		}
		//
		// if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
		// equipa = new EquipaComJuncao();
		// listaProfissonalSaudes = new LinkedList<ProfissonalSaude>();
		// JSONObject o = new JSONObject(
		// EntityUtils.toString(basicHttpResponse.getEntity()));
		//
		// lista = new LinkedList<ProfissionalDaCirurgia>();
		//
		// equipa.setIdEquipa(Integer.parseInt(o.getString("idEquipa")));
		// equipa.setNomeEquipa(o.getString("nomeEquipa"));
		// JSONArray arrayComProfissionais = o.getJSONArray("profissional");
		//
		// for (int j = 0; j < arrayComProfissionais.length(); j++) {
		// JSONObject novoProfissional = arrayComProfissionais
		// .getJSONObject(j);
		// ProfissionalDaCirurgia proCir = new ProfissionalDaCirurgia();
		// JSONObject n = novoProfissional.getJSONObject("profissional");
		//
		// proCir.setTipo(novoProfissional.getString("tipo"));
		// ProfissonalSaude pro = new ProfissonalSaude();
		// pro.setId(n.getInt("id"));
		// pro.setCc(n.getString("cc"));
		// pro.setIdTipo(n.getInt("idTipo"));
		// pro.setNome(n.getString("nome"));
		//
		// proCir.setProfissional(pro);
		// lista.add(proCir);
		//
		// }
		//
		// equipa.setListaProfissionais(lista);
		//

		else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return equipa;

	}

	public static ArrayList<BlocoOperatorio> getAllBloco(String token)
			throws ClientProtocolException, IOException, ParseException,
			JSONException, RestClientException {
		ArrayList<BlocoOperatorio> listaBlocos = null;

		HttpGet request = new HttpGet(URL + "getAllBlocoOperatorio?token="
				+ token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");
		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);

		if (basicHttpResponse.getStatusLine().getStatusCode() == 200) {
			listaBlocos = new ArrayList<BlocoOperatorio>();
			JSONArray array = new JSONArray(
					EntityUtils.toString(basicHttpResponse.getEntity()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				BlocoOperatorio p = new BlocoOperatorio();
				p.setNome(o.getString("nomeBlocoOperatorio"));
				p.setId(Integer.parseInt(o.getString("id")));
				listaBlocos.add(p);
			}
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return listaBlocos;
	}

	public static Boolean updateCirurgia(Cirurgia cirurgia, int idCirurgia,
			String token) throws ClientProtocolException, IOException,
			ParseException, JSONException, RestClientException {
		Boolean adicionou = false;
		Gson g = new Gson();

		HttpPost httpPost = new HttpPost(URL + "updateCirurgiaTotal?token="
				+ token + "&id=" + idCirurgia);
		StringEntity se = new StringEntity(g.toJson(cirurgia, Cirurgia.class),
				"UTF-8");

		se.setContentType("application/json;charset=UTF-8");// text/plain;charset=UTF-8

		se.setContentType("text/json");

		httpPost.setEntity(se);
		BasicHttpResponse httpResponse = (BasicHttpResponse) client
				.execute(httpPost);
		if (isOk(httpResponse.getStatusLine().getStatusCode())) {
			HttpEntity entity = httpResponse.getEntity();

			String string = EntityUtils.toString(entity);
			Log.i("error", string);

			adicionou = Boolean.valueOf(string);
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code"
							+ httpResponse.getStatusLine().getStatusCode()
							+ ".");

		}

		return adicionou;
	}

	public static ArrayList<BlocoComSala> getAllBlocosComSala(String token)
			throws ClientProtocolException, IOException, RestClientException,
			ParseException, JSONException {
		ArrayList<BlocoComSala> listaBlocosComSala = null;
		HttpGet request = new HttpGet(URL + "getBlocoComSala?token=" + token);
		// request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		// "application/json"));
		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			listaBlocosComSala = new ArrayList<BlocoComSala>();
			Type collectionType = new TypeToken<ArrayList<BlocoComSala>>() {
			}.getType();
			listaBlocosComSala = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					collectionType);
		} else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return listaBlocosComSala;

	}

	public static DadosIntraoperatorioFinal verificaIntraOperatorioID(
			String token, int idCirurgia) throws ClientProtocolException,
			IOException, RestClientException, ParseException, JSONException {
		DadosIntraoperatorio dadosIntraOperatorio = new DadosIntraoperatorio();
		LinkedList<SinaisVitais> listaSinaisVitais = new LinkedList<SinaisVitais>();
		LinkedList<MedicacaoAdministrada> listaMedicacaoAdministrada = new LinkedList<MedicacaoAdministrada>();
		LinkedList<BalancoHidrico> listaBalancoHidrico = new LinkedList<BalancoHidrico>();
		LinkedList<Eliminacao> listaEliminacao = new LinkedList<Eliminacao>();
		LinkedList<Drenagem> listaDrenagemVesical = new LinkedList<Drenagem>();
		LinkedList<Drenagem> listaDrenagemNasogastrica = new LinkedList<Drenagem>();
		AdministracaoSangue administracaoSangue = new AdministracaoSangue();

		DadosIntraoperatorioFinal dadosFinal = new DadosIntraoperatorioFinal();

		HttpPost request = new HttpPost(URL + "verificaIntraOperatorio?token="
				+ token + "&idCirurgia=" + idCirurgia);

		request.setHeader("Accept", "Application/JSON");

		BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
				.execute(request);
		

//		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
//
//			JSONObject o = new JSONObject(
//					EntityUtils.toString(basicHttpResponse.getEntity()));
//			Log.i("dados", o.toString(1));
//			if (!o.get("adminSangue").equals(JSONObject.NULL)) {
//
//				JSONObject adminJSON = o.getJSONObject("adminSangue");
//				Log.i("dados_adminSangue", adminJSON.toString(1));
//				administracaoSangue.setFc15minAposTransfusao(adminJSON
//						.getDouble("fc15MinAposTransfusao"));
//				administracaoSangue.setFcFimTransfusao(adminJSON
//						.getDouble("fcFimTransfusao"));
//				administracaoSangue.setFcInicioTransfusao(adminJSON
//						.getDouble("fcInicioTransfusao"));
//				administracaoSangue.setHora15minAposTransf(adminJSON
//						.getString("hora15MinAposTransfusao"));
//				administracaoSangue.setHoraFimTransf(adminJSON
//						.getString("horaFimTransfusao"));
//				administracaoSangue.setHoraInicioTransf(adminJSON
//						.getString("horaInicioTransfusao"));
//				administracaoSangue.setId(adminJSON.getInt("id"));
//				administracaoSangue.setIdIntraOperatorio(adminJSON
//						.getInt("idIntraOperatorio"));
//				administracaoSangue.setSpo215minAposTransfusao(adminJSON
//						.getDouble("spo215MinAposTransfusao"));
//				administracaoSangue.setSpo2FimTransfusao(adminJSON
//						.getDouble("spo2FimTransfusao"));
//				administracaoSangue.setSpo2InicioTransfusao(adminJSON
//						.getDouble("spo2InicioTransfusao"));
//				administracaoSangue.setTa15minAposTransfusao(adminJSON
//						.getInt("ta15MinAposTransfusao"));
//				administracaoSangue.setTaFimTransfusao(adminJSON
//						.getInt("taFimTransfusao"));
//				administracaoSangue.setTaInicioTransfusao(adminJSON
//						.getDouble("taInicioTransfusao"));
//				dadosFinal.setAdministracao(administracaoSangue);
//			} else {
//				dadosFinal.setAdministracao(administracaoSangue);
//			}
//
//			if (!o.get("dados").equals(JSONObject.NULL)) {
//				JSONObject dadosJSON = o.getJSONObject("dados");
//				Log.i("dados_dados", dadosJSON.toString(1));
//
//				dadosIntraOperatorio.setAlivioZonaPressao(dadosJSON
//						.getString("alivioZonapressao"));
//				dadosIntraOperatorio.setCalibreAcessoVenoso(dadosJSON
//						.getDouble("calibreAcessoVenoso"));
//				dadosIntraOperatorio.setCalibreAgulha(dadosJSON
//						.getDouble("calibreAgulha"));
//				dadosIntraOperatorio.setDescricaoPecaBiopsia(dadosJSON
//						.getString("descricaoPecaBiopsia"));
//				dadosIntraOperatorio.setHoraFimGarrotePneum(dadosJSON
//						.getString("horaFimGarrotePneum"));
//				dadosIntraOperatorio.setHoraInicioGarrotePneum(dadosJSON
//						.getString("horaInicioGarrotePneum"));
//				dadosIntraOperatorio.setId(dadosJSON.getInt("id"));
//				dadosIntraOperatorio.setIdCirurgia(dadosJSON
//						.getInt("idCirurgia"));
//				dadosIntraOperatorio.setLabPecaBiopsia(dadosJSON
//						.getString("labPecaBiopsia"));
//				dadosIntraOperatorio.setLocalAlivioZonaPressao(dadosJSON
//						.getString("localAlivioZonaPressao"));
//				dadosIntraOperatorio.setLocalMantaTermica(dadosJSON
//						.getString("localMantaTermica"));
//				dadosIntraOperatorio.setLocalizacaoAcessoVenoso(dadosJSON
//						.getString("localizacaoAcessoVenoso"));
//				dadosIntraOperatorio.setLocalizacaoGarrotePneum(dadosJSON
//						.getString("localizacaoGarrotePneumatico"));
//				dadosIntraOperatorio.setLocalizacaoPlacaEletrodo(dadosJSON
//						.getString("localizacaoPlacaEletrodo"));
//				dadosIntraOperatorio.setMantatermica(dadosJSON
//						.getString("mantaTermica"));
//				dadosIntraOperatorio.setMl(dadosJSON.getInt("ml"));
//				dadosIntraOperatorio.setPlacaEletrodo(dadosJSON
//						.getString("placaEletrodo"));
//				dadosIntraOperatorio.setPosicaoOperatoria(dadosJSON
//						.getString("posicaoOperatoria"));
//				dadosIntraOperatorio.setPressaoGarrotePneumatico(dadosJSON
//						.getDouble("pressaoGarrotePneumatico"));
//				dadosIntraOperatorio.setTet(dadosJSON.getInt("tet"));
//				dadosIntraOperatorio.setTipoAcessovenoso(dadosJSON
//						.getString("tipoAcessoVenoso"));
//				dadosIntraOperatorio.setTipoAnestesia(dadosJSON
//						.getString("tipoAnestesia"));
//
//				dadosFinal.setDados(dadosIntraOperatorio);
//			} else {
//				dadosFinal.setDados(dadosIntraOperatorio);
//			}
//			if (!(o.get("listaBalancos").equals(JSONObject.NULL))) {
//				JSONArray arrayListaBalanco = o.getJSONArray("listaBalancos");
//				Log.i("dados_listaBalancos", arrayListaBalanco.toString(1));
//
//				for (int j = 0; j < arrayListaBalanco.length(); j++) {
//					JSONObject balanco = arrayListaBalanco.getJSONObject(j);
//					BalancoHidrico balancohi = new BalancoHidrico();
//
//					balancohi.setHora(balanco.getString("hora"));
//					balancohi.setId(balanco.getInt("id"));
//					balancohi.setIdIntraOperatorio(balanco
//							.getInt("idIntraOperatorio"));
//					balancohi.setSoroterapia(balanco.getString("soroterapia"));
//					listaBalancoHidrico.add(balancohi);
//				}
//
//				dadosFinal.setListaBalanco(listaBalancoHidrico);
//
//			} else {
//				dadosFinal.setListaBalanco(listaBalancoHidrico);
//			}
//
//			if (!(o.get("listaEliminacao").equals(JSONObject.NULL))) {
//				JSONArray arrayListaEliminacao = o
//						.getJSONArray("listaEliminacao");
//				Log.i("dados_listaEliminacao", arrayListaEliminacao.toString(1));
//
//				for (int j = 0; j < arrayListaEliminacao.length(); j++) {
//					JSONObject eliminacao = arrayListaEliminacao
//							.getJSONObject(j);
//					Eliminacao elimina = new Eliminacao();
//
//					elimina.setCalibre(eliminacao.getDouble("calibre"));
//					elimina.setId(eliminacao.getInt("id"));
//					elimina.setIdIntraOperatorio(eliminacao
//							.getInt("idEnfermagemIntra"));
//					elimina.setTipo(eliminacao.getString("tipo"));
//					elimina.setTipoSonda(eliminacao.getString("tipoSonda"));
//
//					listaEliminacao.add(elimina);
//				}
//
//				dadosFinal.setListaEliminacao(listaEliminacao);
//			} else {
//				dadosFinal.setListaEliminacao(listaEliminacao);
//			}
//			if (!(o.get("listaDrenagemVesical").equals(JSONObject.NULL))) {
//				JSONArray arrayListaDrenagemVesical = o
//						.getJSONArray("listaDrenagemVesical");
//				Log.i("dados_listaDrenagemVesical",
//						arrayListaDrenagemVesical.toString(1));
//
//				for (int j = 0; j < arrayListaDrenagemVesical.length(); j++) {
//					JSONObject drenagemVesicalJSON = arrayListaDrenagemVesical
//							.getJSONObject(j);
//					Drenagem dreVesical = new Drenagem();
//
//					dreVesical.setCaracteristicas(drenagemVesicalJSON
//							.getString("caracteristicas"));
//					dreVesical.setDrenagem(drenagemVesicalJSON
//							.getString("hora"));
//					dreVesical.setId(drenagemVesicalJSON.getInt("id"));
//					dreVesical.setIdEliminacao(drenagemVesicalJSON
//							.getInt("idEliminacao"));
//					dreVesical.setHora(drenagemVesicalJSON.getString("hora"));
//
//					listaDrenagemVesical.add(dreVesical);
//				}
//
//				dadosFinal.setListaDrenagemVesical(listaDrenagemVesical);
//			} else {
//				dadosFinal.setListaDrenagemVesical(listaDrenagemVesical);
//			}
//
//			if (!(o.get("listaDrenagemNasogastrica").equals(JSONObject.NULL))) {
//				JSONArray arrayListaDrenagemNasogastrica = o
//						.getJSONArray("listaDrenagemNasogastrica");
//				Log.i("dados_listaDrenagemNasogastrica",
//						arrayListaDrenagemNasogastrica.toString(1));
//
//				for (int j = 0; j < arrayListaDrenagemNasogastrica.length(); j++) {
//					JSONObject drenagemNasogastricaJSON = arrayListaDrenagemNasogastrica
//							.getJSONObject(j);
//					Drenagem dreVesical = new Drenagem();
//
//					dreVesical.setCaracteristicas(drenagemNasogastricaJSON
//							.getString("caracteristicas"));
//					dreVesical.setDrenagem(drenagemNasogastricaJSON
//							.getString("hora"));
//					dreVesical.setId(drenagemNasogastricaJSON.getInt("id"));
//					dreVesical.setIdEliminacao(drenagemNasogastricaJSON
//							.getInt("idEliminacao"));
//					dreVesical.setHora(drenagemNasogastricaJSON
//							.getString("hora"));
//
//					listaDrenagemNasogastrica.add(dreVesical);
//				}
//
//				dadosFinal
//						.setListaDrenagemNasogastrica(listaDrenagemNasogastrica);
//			} else {
//				dadosFinal
//						.setListaDrenagemNasogastrica(listaDrenagemNasogastrica);
//			}
//
//			if (!(o.get("listaMedicacao").equals(JSONObject.NULL))) {
//				JSONArray arrayListaMedicacao = o
//						.getJSONArray("listaMedicacao");
//				Log.i("dados_listaMedicacao", arrayListaMedicacao.toString(1));
//
//				for (int j = 0; j < arrayListaMedicacao.length(); j++) {
//					JSONObject medicacaoJSON = arrayListaMedicacao
//							.getJSONObject(j);
//					MedicacaoAdministrada medicaAdministrada = new MedicacaoAdministrada();
//
//					medicaAdministrada.setFarmaco(medicacaoJSON
//							.getString("farmaco"));
//					medicaAdministrada.setHora(medicacaoJSON.getString("hora"));
//					medicaAdministrada.setId(medicacaoJSON.getInt("id"));
//					medicaAdministrada.setIdIntraOperatorio(medicacaoJSON
//							.getInt("idIntraOperatorio"));
//
//					listaMedicacaoAdministrada.add(medicaAdministrada);
//				}
//
//				dadosFinal.setListaMedicacao(listaMedicacaoAdministrada);
//			} else {
//				dadosFinal.setListaMedicacao(listaMedicacaoAdministrada);
//			}
//			if (!(o.get("listaSinais").equals(JSONObject.NULL))) {
//
//				JSONArray arrayListaSinais = o.getJSONArray("listaSinais");
//				Log.i("dados_listaSinais", arrayListaSinais.toString(1));
//
//				for (int j = 0; j < arrayListaSinais.length(); j++) {
//					JSONObject sinalJSON = arrayListaSinais.getJSONObject(j);
//					SinaisVitais sinal = new SinaisVitais();
//
//					sinal.setFc(sinalJSON.getInt("fc"));
//					sinal.setHora(sinalJSON.getString("hora"));
//					sinal.setId(sinalJSON.getInt("id"));
//					sinal.setIdintraOperatorio(sinalJSON
//							.getInt("idIntraOperatorio"));
//					sinal.setSpo2(sinalJSON.getDouble("spo2"));
//					sinal.setTa(sinalJSON.getInt("ta"));
//					sinal.setTemp(sinalJSON.getDouble("temp"));
//
//					listaSinaisVitais.add(sinal);
//				}
//
//				dadosFinal.setListaSinais(listaSinaisVitais);
//			} else {
//				dadosFinal.setListaSinais(listaSinaisVitais);
//			}
//
//		}
		Gson g = new Gson();

		if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
			dadosFinal = g.fromJson(
					EntityUtils.toString(basicHttpResponse.getEntity()),
					DadosIntraoperatorioFinal.class);}


		else {
			throw new RestClientException(
					"HTTP Response with invalid status code "
							+ basicHttpResponse.getStatusLine().getStatusCode()
							+ ".");
		}

		return dadosFinal;

	}


    public static Utente getUtenteByID(int idUtente, String token)
            throws ClientProtocolException, IOException, RestClientException,
            ParseException, JSONException {
        Utente utente = null;

        HttpGet request = new HttpGet(URL + "getUtenteById?token="
                + token + "&id=" + idUtente);
        // request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
        // "application/json"));
        request.setHeader("Accept", "Application/JSON");

        BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
                .execute(request);
        Gson g = new Gson();

        if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {

            utente = g.fromJson(
                    EntityUtils.toString(basicHttpResponse.getEntity()),
                    Utente.class);

        }

        else {
            throw new RestClientException(
                    "HTTP Response with invalid status code "
                            + basicHttpResponse.getStatusLine().getStatusCode()
                            + ".");
        }

        return utente;

    }


    public static ArrayList<ListaProdutosComProdutos> getAllListas(String token)
            throws ClientProtocolException, IOException, RestClientException,
            ParseException, JSONException {
        ArrayList<ListaProdutosComProdutos> lista = null;

        HttpGet request = new HttpGet(URL + "getListasProdutosComMateriais?token=" + token);
        // request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
        // "application/json"));
        request.setHeader("Accept", "Application/JSON");

        BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
                .execute(request);

        Gson g = new Gson();

        if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
            lista = new ArrayList<ListaProdutosComProdutos>();
            Type collectionType = new TypeToken<ArrayList<ListaProdutosComProdutos>>() {
            }.getType();
            lista = g.fromJson(
                    EntityUtils.toString(basicHttpResponse.getEntity()),
                    collectionType);

        } else {
            throw new RestClientException(
                    "HTTP Response with invalid status code "
                            + basicHttpResponse.getStatusLine().getStatusCode()
                            + ".");
        }

        return lista;

    }


    public static ListaProdutosComProdutosCirurgia getProdutosByIdCirurgia(String token, int idCirurgia)
            throws ClientProtocolException, IOException, RestClientException,
            ParseException, JSONException {
        ListaProdutosComProdutosCirurgia lista = null;

        HttpGet request = new HttpGet(URL + "getListaComProdutosCirurgia?token=" + token + "&idCirurgia=" + idCirurgia);
        // request.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
        // "application/json"));
        request.setHeader("Accept", "Application/JSON");

        BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
                .execute(request);

        Gson g = new Gson();

        if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
            lista = new ListaProdutosComProdutosCirurgia();
            Type collectionType = new TypeToken<ListaProdutosComProdutosCirurgia>() {
            }.getType();
            lista = g.fromJson(
                    EntityUtils.toString(basicHttpResponse.getEntity()),
                    collectionType);

        } else {
            throw new RestClientException(
                    "HTTP Response with invalid status code "
                            + basicHttpResponse.getStatusLine().getStatusCode()
                            + ".");
        }

        return lista;

    }

    public static Boolean adicionarProdutosDaCirurgia(
            ArrayList<ProdutosCirurgia> produtos, String token)
            throws ClientProtocolException, IOException, ParseException,
            JSONException, RestClientException {
        Boolean adicionou = false;
        Gson g = new Gson();

        HttpPost httpPost = new HttpPost(URL + "addAllProdutosCirurgia?token="
                + token);

        Type collectionType = new TypeToken<ArrayList<ProdutosCirurgia>>() {
        }.getType();

        StringEntity se = new StringEntity(g.toJson(produtos,collectionType), "UTF-8");
        se.setContentType("text/json");
        se.setContentType("application/json;charset=UTF-8");

        httpPost.setEntity(se);
        BasicHttpResponse httpResponse = (BasicHttpResponse) client
                .execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String string = EntityUtils.toString(entity);

        Log.i("adicionarProdutos",string);
        if (isOk(httpResponse.getStatusLine().getStatusCode())) {

            adicionou = Boolean.valueOf(string);
        } else {
            throw new RestClientException(
                    "HTTP Response with invalid status code"
                            + httpResponse.getStatusLine().getStatusCode()
                            + ".");

        }

        return adicionou;
    }

    public static ArrayList<Produtos> getAllProdutos(String token)
            throws ClientProtocolException, IOException, RestClientException,
            ParseException, JSONException {
        ArrayList<Produtos> produtos = null;

        HttpGet request = new HttpGet(URL + "getAllProdutos?token=" + token);

        request.setHeader("Accept", "Application/JSON");

        BasicHttpResponse basicHttpResponse = (BasicHttpResponse) client
                .execute(request);

        Gson g = new Gson();

        if (isOk(basicHttpResponse.getStatusLine().getStatusCode())) {
            produtos = new ArrayList<Produtos>();
            Type collectionType = new TypeToken<ArrayList<Produtos>>() {
            }.getType();
            produtos = g.fromJson(
                    EntityUtils.toString(basicHttpResponse.getEntity()),
                    collectionType);

        } else {
            throw new RestClientException(
                    "HTTP Response with invalid status code "
                            + basicHttpResponse.getStatusLine().getStatusCode()
                            + ".");
        }

        return produtos;

    }

    public static Boolean isOk(int statusCode) {
		Boolean resultado = false;

		switch (statusCode) {
		case 200:
			resultado = true;
			break;
		case 201:
			resultado = true;
			break;
		case 202:
			resultado = true;
			break;
		case 203:
			resultado = true;
			break;
		default:
			break;
		}

		return resultado;
	}


    public static Bitmap getImage(String sexo, String idPaciente)
            throws ClientProtocolException, IOException, RestClientException, JSONException {

        Bitmap imagem = null;

        URL img_value = null;


        img_value = new URL("http://www.nicolau.info/SGMC/"+sexo+"/" + idPaciente + ".jpg");

        try {
            imagem
                    = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
        } catch (Exception e) {
            Log.i("error", e.getMessage().toString());
        }
        return imagem;

    }

}
