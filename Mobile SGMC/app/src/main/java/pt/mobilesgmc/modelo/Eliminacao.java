package pt.mobilesgmc.modelo;

public class Eliminacao {

	private int id;
	private String tipo;
	private String tipoSonda;
	private double calibre;
	private int idEnfermagemIntra;

	public Eliminacao(){}

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

    public int getIdEnfermagemIntra() {
        return idEnfermagemIntra;
    }

    public void setIdEnfermagemIntra(int idEnfermagemIntra) {
        this.idEnfermagemIntra = idEnfermagemIntra;
    }

    @Override
    public String toString() {
        return "Eliminacao{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", tipoSonda='" + tipoSonda + '\'' +
                ", calibre=" + calibre +
                ", idEnfermagemIntra=" + idEnfermagemIntra +
                '}';
    }
}
