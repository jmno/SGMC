package pt.mobilesgmc.modelo;

public class SinaisVitais {

    @Override
    public String toString() {
        return "SinaisVitais{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", tamin=" + tamin +
                ", tamax=" + tamax +
                ", fc=" + fc +
                ", spo2=" + spo2 +
                ", temp=" + temp +
                ", idIntraOperatorio=" + idIntraOperatorio +
                '}';
    }

    private int id;
	private String hora;
    private double tamin;
    private double tamax;
    private double fc;
	private double spo2;
	private double temp;
	public int idIntraOperatorio;

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

    public double getTamin() {
        return tamin;
    }

    public void setTamin(double tamin) {
        this.tamin = tamin;
    }

    public double getTamax() {
        return tamax;
    }

    public void setTamax(double tamax) {
        this.tamax = tamax;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getIdIntraOperatorio() {
        return idIntraOperatorio;
    }

    public void setIdIntraOperatorio(int idIntraOperatorio) {
        this.idIntraOperatorio = idIntraOperatorio;
    }

    public SinaisVitais(){}
}
