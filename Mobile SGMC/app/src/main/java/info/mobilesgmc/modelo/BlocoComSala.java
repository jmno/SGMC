package info.mobilesgmc.modelo;

public class BlocoComSala {
	private Sala sala;
	private String nomeBlocoOperatorio;
	
	
	
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public String getNomeBlocoOperatorio() {
		return nomeBlocoOperatorio;
	}
	public void setNomeBlocoOperatorio(String nomeBlocoOperatorio) {
		this.nomeBlocoOperatorio = nomeBlocoOperatorio;
	}
	
	
	public String toString()
	{
		return nomeBlocoOperatorio + " - " + sala.getNome();
	}
}
