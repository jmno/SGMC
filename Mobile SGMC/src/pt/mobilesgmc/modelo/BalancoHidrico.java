package pt.mobilesgmc.modelo;

public class BalancoHidrico {
	
	private int id;
	private String hora;
	private String soroterapia;
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
	public String getSoroterapia() {
		return soroterapia;
	}
	public void setSoroterapia(String soroterapia) {
		this.soroterapia = soroterapia;
	}
	public int getIdIntraOperatorio() {
		return idIntraOperatorio;
	}
	public void setIdIntraOperatorio(int idIntraOperatorio) {
		this.idIntraOperatorio = idIntraOperatorio;
	}
	
	public BalancoHidrico(){}

}
