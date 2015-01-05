package pt.mobilesgmc.modelo;

public class DadosIntraoperatorio {
	
	private int id;
	private String tipoAnestesia;
	private int tet;
	private int ml;
	private double calibreAgulha;
	private String tipoAcessoVenoso;
	private double calibreAcessoVenoso;
	private String localizacaoAcessoVenoso;
	private String posicaoOperatoria;
	private String alivioZonaPressao;
	private String localAlivioZonaPressao;
	private String mantaTermica;
	private String localMantaTermica;
	private double pressaoGarrotePneumatico;
	private String localizacaoGarrotePneumatico;
	private String horaInicioGarrotePneum;
	private String horaFimGarrotePneum;
	private String placaEletrodo;
	private String localizacaoPlacaEletrodo;
	private String labPecaBiopsia;
	private String descricaoPecaBiopsia;
	private int idCirurgia;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipoAnestesia() {
		return tipoAnestesia;
	}
	public void setTipoAnestesia(String tipoAnestesia) {
		this.tipoAnestesia = tipoAnestesia;
	}
	public int getTet() {
		return tet;
	}
	public void setTet(int tet) {
		this.tet = tet;
	}
	public int getMl() {
		return ml;
	}
	public void setMl(int ml) {
		this.ml = ml;
	}
	public double getCalibreAgulha() {
		return calibreAgulha;
	}
	public void setCalibreAgulha(double calibreAgulha) {
		this.calibreAgulha = calibreAgulha;
	}
	public String getTipoAcessovenoso() {
		return tipoAcessoVenoso;
	}
	public void setTipoAcessovenoso(String tipoAcessovenoso) {
		this.tipoAcessoVenoso = tipoAcessovenoso;
	}
	public double getCalibreAcessoVenoso() {
		return calibreAcessoVenoso;
	}
	public void setCalibreAcessoVenoso(double calibreAcessoVenoso) {
		this.calibreAcessoVenoso = calibreAcessoVenoso;
	}
	public String getLocalizacaoAcessoVenoso() {
		return localizacaoAcessoVenoso;
	}
	public void setLocalizacaoAcessoVenoso(String localizacaoAcessoVenoso) {
		this.localizacaoAcessoVenoso = localizacaoAcessoVenoso;
	}
	public String getPosicaoOperatoria() {
		return posicaoOperatoria;
	}
	public void setPosicaoOperatoria(String posicaoOperatoria) {
		this.posicaoOperatoria = posicaoOperatoria;
	}
	public String getAlivioZonaPressao() {
		return alivioZonaPressao;
	}
	public void setAlivioZonaPressao(String alivioZonaPressao) {
		this.alivioZonaPressao = alivioZonaPressao;
	}
	public String getLocalAlivioZonaPressao() {
		return localAlivioZonaPressao;
	}
	public void setLocalAlivioZonaPressao(String localAlivioZonaPressao) {
		this.localAlivioZonaPressao = localAlivioZonaPressao;
	}
	public String getMantatermica() {
		return mantaTermica;
	}
	public void setMantatermica(String mantatermica) {
		this.mantaTermica = mantatermica;
	}
	public String getLocalMantaTermica() {
		return localMantaTermica;
	}
	public void setLocalMantaTermica(String localMantaTermica) {
		this.localMantaTermica = localMantaTermica;
	}
	public double getPressaoGarrotePneumatico() {
		return pressaoGarrotePneumatico;
	}
	public void setPressaoGarrotePneumatico(double pressaoGarrotePneumatico) {
		this.pressaoGarrotePneumatico = pressaoGarrotePneumatico;
	}
	public String getLocalizacaoGarrotePneum() {
		return localizacaoGarrotePneumatico;
	}
	public void setLocalizacaoGarrotePneum(String localizacaoGarrotePneum) {
		this.localizacaoGarrotePneumatico = localizacaoGarrotePneum;
	}
	public String getHoraInicioGarrotePneum() {
		return horaInicioGarrotePneum;
	}
	public void setHoraInicioGarrotePneum(String horaInicioGarrotePneum) {
		this.horaInicioGarrotePneum = horaInicioGarrotePneum;
	}
	public String getHoraFimGarrotePneum() {
		return horaFimGarrotePneum;
	}
	public void setHoraFimGarrotePneum(String horaFimGarrotePneum) {
		this.horaFimGarrotePneum = horaFimGarrotePneum;
	}
	public String getPlacaEletrodo() {
		return placaEletrodo;
	}
	public void setPlacaEletrodo(String placaEletrodo) {
		this.placaEletrodo = placaEletrodo;
	}
	public String getLocalizacaoPlacaEletrodo() {
		return localizacaoPlacaEletrodo;
	}
	public void setLocalizacaoPlacaEletrodo(String localizacaoPlacaEletrodo) {
		this.localizacaoPlacaEletrodo = localizacaoPlacaEletrodo;
	}
	public String getLabPecaBiopsia() {
		return labPecaBiopsia;
	}
	public void setLabPecaBiopsia(String labPecaBiopsia) {
		this.labPecaBiopsia = labPecaBiopsia;
	}
	public String getDescricaoPecaBiopsia() {
		return descricaoPecaBiopsia;
	}
	public void setDescricaoPecaBiopsia(String descricaoPecaBiopsia) {
		this.descricaoPecaBiopsia = descricaoPecaBiopsia;
	}
	public int getIdCirurgia() {
		return idCirurgia;
	}
	public void setIdCirurgia(int idCirurgia) {
		this.idCirurgia = idCirurgia;
	}
	
	public DadosIntraoperatorio(){}
	
	@Override
	public String toString() {
		return "DadosIntraoperatorio [id=" + id + ", tipoAnestesia="
				+ tipoAnestesia + ", tet=" + tet + ", ml=" + ml
				+ ", calibreAgulha=" + calibreAgulha + ", tipoAcessovenoso="
				+ tipoAcessoVenoso + ", calibreAcessoVenoso="
				+ calibreAcessoVenoso + ", localizacaoAcessoVenoso="
				+ localizacaoAcessoVenoso + ", posicaoOperatoria="
				+ posicaoOperatoria + ", alivioZonaPressao="
				+ alivioZonaPressao + ", localAlivioZonaPressao="
				+ localAlivioZonaPressao + ", mantatermica=" + mantaTermica
				+ ", localMantaTermica=" + localMantaTermica
				+ ", pressaoGarrotePneumatico=" + pressaoGarrotePneumatico
				+ ", localizacaoGarrotePneum=" + localizacaoGarrotePneumatico
				+ ", horaInicioGarrotePneum=" + horaInicioGarrotePneum
				+ ", horaFimGarrotePneum=" + horaFimGarrotePneum
				+ ", placaEletrodo=" + placaEletrodo
				+ ", localizacaoPlacaEletrodo=" + localizacaoPlacaEletrodo
				+ ", labPecaBiopsia=" + labPecaBiopsia
				+ ", descricaoPecaBiopsia=" + descricaoPecaBiopsia
				+ ", idCirurgia=" + idCirurgia + "]";
	}


}
