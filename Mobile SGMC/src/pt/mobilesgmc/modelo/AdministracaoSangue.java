package pt.mobilesgmc.modelo;

public class AdministracaoSangue {

	@Override
	public String toString() {
		return "AdministracaoSangue [id=" + id + ", horaInicioTransf="
				+ horaInicioTransfusao + ", taInicioTransfusao="
				+ taInicioTransfusao + ", fcInicioTransfusao="
				+ fcInicioTransfusao + ", spo2InicioTransfusao="
				+ spo2InicioTransfusao + ", hora15minAposTransf="
				+ hora15MinAposTransfusao + ", ta15minAposTransfusao="
				+ ta15MinAposTransfusao + ", fc15minAposTransfusao="
				+ fc15MinAposTransfusao + ", spo215minAposTransfusao="
				+ spo215MinAposTransfusao + ", horaFimTransf=" + horaFimTransfusao
				+ ", taFimTransfusao=" + taFimTransfusao + ", fcFimTransfusao="
				+ fcFimTransfusao + ", spo2FimTransfusao=" + spo2FimTransfusao
				+ ", idIntraOperatorio=" + idIntraOperatorio + "]";
	}


	private int id;
	private String horaInicioTransfusao;
	private double taInicioTransfusao;
	private double fcInicioTransfusao;
	private double spo2InicioTransfusao;
	private String hora15MinAposTransfusao;
	private double ta15MinAposTransfusao;
	private double fc15MinAposTransfusao;
	private double spo215MinAposTransfusao;
	private String horaFimTransfusao;
	private double taFimTransfusao;
	private double fcFimTransfusao;
	private double spo2FimTransfusao;
	private int idIntraOperatorio;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHoraInicioTransf() {
		return horaInicioTransfusao;
	}
	public void setHoraInicioTransf(String horaInicioTransf) {
		this.horaInicioTransfusao = horaInicioTransf;
	}
	public double getTaInicioTransfusao() {
		return taInicioTransfusao;
	}
	public void setTaInicioTransfusao(double taInicioTransfusao) {
		this.taInicioTransfusao = taInicioTransfusao;
	}
	public double getFcInicioTransfusao() {
		return fcInicioTransfusao;
	}
	public void setFcInicioTransfusao(double fcInicioTransfusao) {
		this.fcInicioTransfusao = fcInicioTransfusao;
	}
	public double getSpo2InicioTransfusao() {
		return spo2InicioTransfusao;
	}
	public void setSpo2InicioTransfusao(double spo2InicioTransfusao) {
		this.spo2InicioTransfusao = spo2InicioTransfusao;
	}
	public String getHora15minAposTransf() {
		return hora15MinAposTransfusao;
	}
	public void setHora15minAposTransf(String hora15minAposTransf) {
		this.hora15MinAposTransfusao = hora15minAposTransf;
	}
	public double getTa15minAposTransfusao() {
		return ta15MinAposTransfusao;
	}
	public void setTa15minAposTransfusao(double ta15minAposTransfusao) {
		this.ta15MinAposTransfusao = ta15minAposTransfusao;
	}
	public double getFc15minAposTransfusao() {
		return fc15MinAposTransfusao;
	}
	public void setFc15minAposTransfusao(double fc15minAposTransfusao) {
		this.fc15MinAposTransfusao = fc15minAposTransfusao;
	}
	public double getSpo215minAposTransfusao() {
		return spo215MinAposTransfusao;
	}
	public void setSpo215minAposTransfusao(double spo215minAposTransfusao) {
		this.spo215MinAposTransfusao = spo215minAposTransfusao;
	}
	public String getHoraFimTransf() {
		return horaFimTransfusao;
	}
	public void setHoraFimTransf(String horaFimTransf) {
		this.horaFimTransfusao = horaFimTransf;
	}
	public double getTaFimTransfusao() {
		return taFimTransfusao;
	}
	public void setTaFimTransfusao(double taFimTransfusao) {
		this.taFimTransfusao = taFimTransfusao;
	}
	public double getFcFimTransfusao() {
		return fcFimTransfusao;
	}
	public void setFcFimTransfusao(double fcFimTransfusao) {
		this.fcFimTransfusao = fcFimTransfusao;
	}
	public double getSpo2FimTransfusao() {
		return spo2FimTransfusao;
	}
	public void setSpo2FimTransfusao(double spo2FimTransfusao) {
		this.spo2FimTransfusao = spo2FimTransfusao;
	}
	public int getIdIntraOperatorio() {
		return idIntraOperatorio;
	}
	public void setIdIntraOperatorio(int idIntraOperatorio) {
		this.idIntraOperatorio = idIntraOperatorio;
	}
	
	
	public AdministracaoSangue(){}
	
}
