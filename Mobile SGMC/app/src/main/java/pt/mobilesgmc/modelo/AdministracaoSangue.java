package pt.mobilesgmc.modelo;

public class AdministracaoSangue {
	private int id;
	private String hora;
	private double taMin;
	private double fc;
	private double spo2;
	private double taMax;
	private String tipo;
	private int idIntraOperatorio;

	public AdministracaoSangue(){}

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

    public double getTaMin() {
        return taMin;
    }

    public void setTaMin(double taMin) {
        this.taMin = taMin;
    }

    public double getFc() {
        return fc;
    }

    public void setFc(double fc) {
        this.fc = fc;
    }

    public double getSpo2() {
        return spo2;
    }

    public void setSpo2(double spo2) {
        this.spo2 = spo2;
    }

    public double getTaMax() {
        return taMax;
    }

    public void setTaMax(double taMax) {
        this.taMax = taMax;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdIntraOperatorio() {
        return idIntraOperatorio;
    }

    public void setIdIntraOperatorio(int idIntraOperatorio) {
        this.idIntraOperatorio = idIntraOperatorio;
    }

    @Override
    public String toString() {
        return "AdministracaoSangue{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", taMin=" + taMin +
                ", fc=" + fc +
                ", spo2=" + spo2 +
                ", taMax=" + taMax +
                ", tipo='" + tipo + '\'' +
                ", idIntraOperatorio=" + idIntraOperatorio +
                '}';
    }
}
