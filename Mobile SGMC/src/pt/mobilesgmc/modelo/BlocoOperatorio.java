package pt.mobilesgmc.modelo;

public class BlocoOperatorio {
	
	private int id;
	private String nomeBlocoOperatorio;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nomeBlocoOperatorio;
	}
	public void setNome(String nome) {
		this.nomeBlocoOperatorio = nome;
	}
	@Override
	public String toString() {
		return   nomeBlocoOperatorio ;
	}
	
	

}
