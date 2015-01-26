package pt.mobilesgmc.modelo;

public class DadosIntraoperatorio {
	
	private int id;
	private String tipoAnestesia;
	private int tet;
	private int ml;
	private double calibreAgulha;
	private String posicaoOperatoria;
	private String alivioZonaPressao;
	private String localAlivioZonaPressao;
	private boolean mantaTermica;
	private String localMantaTermica;
	private double pressaoGarrotePneumatico;
	private String localizacaoGarrotePneumatico;
	private String horaInicioGarrotePneum;
	private String horaFimGarrotePneum;
	private boolean placaEletrodo;
	private String localizacaoPlacaEletrodo;
	private int numFrascosPecaBiopsia;
	private String obsAdminSangue;
	private int idCirurgia;

	public DadosIntraoperatorio(){}


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

    public boolean isMantaTermica() {
        return mantaTermica;
    }

    public void setMantaTermica(boolean mantaTermica) {
        this.mantaTermica = mantaTermica;
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

    public String getLocalizacaoGarrotePneumatico() {
        return localizacaoGarrotePneumatico;
    }

    public void setLocalizacaoGarrotePneumatico(String localizacaoGarrotePneumatico) {
        this.localizacaoGarrotePneumatico = localizacaoGarrotePneumatico;
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

    public boolean isPlacaEletrodo() {
        return placaEletrodo;
    }

    public void setPlacaEletrodo(boolean placaEletrodo) {
        this.placaEletrodo = placaEletrodo;
    }

    public String getLocalizacaoPlacaEletrodo() {
        return localizacaoPlacaEletrodo;
    }

    public void setLocalizacaoPlacaEletrodo(String localizacaoPlacaEletrodo) {
        this.localizacaoPlacaEletrodo = localizacaoPlacaEletrodo;
    }

    public int getNumFrascosPecaBiopsia() {
        return numFrascosPecaBiopsia;
    }

    public void setNumFrascosPecaBiopsia(int numFrascosPecaBiopsia) {
        this.numFrascosPecaBiopsia = numFrascosPecaBiopsia;
    }

    public String getObsAdminSangue() {
        return obsAdminSangue;
    }

    public void setObsAdminSangue(String obsAdminSangue) {
        this.obsAdminSangue = obsAdminSangue;
    }

    public int getIdCirurgia() {
        return idCirurgia;
    }

    public void setIdCirurgia(int idCirurgia) {
        this.idCirurgia = idCirurgia;
    }

    @Override
    public String toString() {
        return "DadosIntraoperatorio{" +
                "id=" + id +
                ", tipoAnestesia='" + tipoAnestesia + '\'' +
                ", tet=" + tet +
                ", ml=" + ml +
                ", calibreAgulha=" + calibreAgulha +
                ", posicaoOperatoria='" + posicaoOperatoria + '\'' +
                ", alivioZonaPressao='" + alivioZonaPressao + '\'' +
                ", localAlivioZonaPressao='" + localAlivioZonaPressao + '\'' +
                ", mantaTermica=" + mantaTermica +
                ", localMantaTermica='" + localMantaTermica + '\'' +
                ", pressaoGarrotePneumatico=" + pressaoGarrotePneumatico +
                ", localizacaoGarrotePneumatico='" + localizacaoGarrotePneumatico + '\'' +
                ", horaInicioGarrotePneum='" + horaInicioGarrotePneum + '\'' +
                ", horaFimGarrotePneum='" + horaFimGarrotePneum + '\'' +
                ", placaEletrodo=" + placaEletrodo +
                ", localizacaoPlacaEletrodo='" + localizacaoPlacaEletrodo + '\'' +
                ", numFrascosPecaBiopsia=" + numFrascosPecaBiopsia +
                ", obsAdminSangue='" + obsAdminSangue + '\'' +
                ", idCirurgia=" + idCirurgia +
                '}';
    }
}
