package pt.mobilesgmc.modelo;

public class MedicacaoAdministrada {
	
	@Override
	public String toString() {
		return "MedicacaoAdministrada [id=" + id + ", hora=" + hora
				+ ", farmaco=" + farmaco + ", idIntraOperatorio="
				+ idIntraOperatorio + "]";
	}

	private int id;
	private String hora;
	private String farmaco;
	private int idIntraOperatorio;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getFarmaco() {
		return farmaco;
	}
	public void setFarmaco(String farmaco) {
		this.farmaco = farmaco;
	}
	public int getIdIntraOperatorio() {
		return idIntraOperatorio;
	}
	public void setIdIntraOperatorio(int idIntraOperatorio) {
		this.idIntraOperatorio = idIntraOperatorio;
	}
	
	public MedicacaoAdministrada(){}

}
