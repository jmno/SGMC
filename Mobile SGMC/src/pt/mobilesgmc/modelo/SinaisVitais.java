package pt.mobilesgmc.modelo;

public class SinaisVitais {

	private int id;
	private String hora;
	private int ta;
	private int fc;
	private double spo2;
	private double temp;
	public int idintraOperatorio;
	
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
	public int getTa() {
		return ta;
	}
	public void setTa(int ta) {
		this.ta = ta;
	}
	public int getFc() {
		return fc;
	}
	public void setFc(int fc) {
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
	public int getIdintraOperatorio() {
		return idintraOperatorio;
	}
	public void setIdintraOperatorio(int idintraOperatorio) {
		this.idintraOperatorio = idintraOperatorio;
	}
	
	public SinaisVitais(){}
}
