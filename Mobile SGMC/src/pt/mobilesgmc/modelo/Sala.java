package pt.mobilesgmc.modelo;

public class Sala {

	private int id;
	private String nome;
	private int idBloco;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdBloco() {
		return idBloco;
	}
	public void setIdBloco(int idBloco) {
		this.idBloco = idBloco;
	}
	@Override
	public String toString() {
		return  nome ;
	}
	
	
}
