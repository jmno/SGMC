package info.mobilesgmc.modelo;

public class Eliminacao {

	@Override
	public String toString() {
		return "Eliminacao [id=" + id + ", tipo=" + tipo + ", tipoSonda="
				+ tipoSonda + ", calibre=" + calibre + ", idIntraOperatorio="
				+ idEnfermagemIntra + "]";
	}


	private int id;
	private String tipo;
	private String tipoSonda;
	private double calibre;
	private int idEnfermagemIntra;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipoSonda() {
		return tipoSonda;
	}
	public void setTipoSonda(String tipoSonda) {
		this.tipoSonda = tipoSonda;
	}
	public double getCalibre() {
		return calibre;
	}
	public void setCalibre(double calibre) {
		this.calibre = calibre;
	}
	public int getIdIntraOperatorio() {
		return idEnfermagemIntra;
	}
	public void setIdIntraOperatorio(int idIntraOperatorio) {
		this.idEnfermagemIntra = idIntraOperatorio;
	}
	
	
	public Eliminacao(){}
}
