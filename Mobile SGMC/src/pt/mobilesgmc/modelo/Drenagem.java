package pt.mobilesgmc.modelo;

public class Drenagem {

	private int id;
	private String hora;
	private String drenagem;
	private String caracteristicas;
	private int idEliminacao;
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
	public String getDrenagem() {
		return drenagem;
	}
	public void setDrenagem(String drenagem) {
		this.drenagem = drenagem;
	}
	public String getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public int getIdEliminacao() {
		return idEliminacao;
	}
	public void setIdEliminacao(int idEliminacao) {
		this.idEliminacao = idEliminacao;
	}
	
	public Drenagem(){}
}
