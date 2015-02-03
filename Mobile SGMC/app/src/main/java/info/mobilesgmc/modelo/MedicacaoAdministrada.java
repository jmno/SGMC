package info.mobilesgmc.modelo;

public class MedicacaoAdministrada {

	private int id;
	private String hora;
	private String farmaco;
    private Double dose;
    private String via;
	private int idIntraOperatorio;

	public MedicacaoAdministrada(){}

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

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getIdIntraOperatorio() {
        return idIntraOperatorio;
    }

    public void setIdIntraOperatorio(int idIntraOperatorio) {
        this.idIntraOperatorio = idIntraOperatorio;
    }


    @Override
    public String toString() {
        return "MedicacaoAdministrada{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", farmaco='" + farmaco + '\'' +
                ", dose=" + dose +
                ", via='" + via + '\'' +
                ", idIntraOperatorio=" + idIntraOperatorio +
                '}';
    }
}
