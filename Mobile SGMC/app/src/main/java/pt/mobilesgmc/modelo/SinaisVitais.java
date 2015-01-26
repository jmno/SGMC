package pt.mobilesgmc.modelo;

public class SinaisVitais {

    private int id;
	private String hora;
    private double taMin;
    private double taMax;
    private double fc;
	private double spo2;
	private double temp;
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

    public double getTaMin() {
        return taMin;
    }

    public void setTaMin(double taMin) {
        this.taMin = taMin;
    }

    public double getTaMax() {
        return taMax;
    }

    public void setTaMax(double taMax) {
        this.taMax = taMax;
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

    @Override
    public String toString() {
        return "SinaisVitais{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", taMin=" + taMin +
                ", taMax=" + taMax +
                ", fc=" + fc +
                ", spo2=" + spo2 +
                ", temp=" + temp +
                ", idIntraOperatorio=" + idIntraOperatorio +
                '}';
    }
}
