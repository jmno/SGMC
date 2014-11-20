package pt.mobilesgmc.modelo;

public class Tipo {
	private int id;
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Tipo(int id, String descricao){

		id = this.id;
		descricao = this.descricao;
	}
	
	public Tipo()
	{
		
	}
	
	public Tipo(String descricao)
	{
		descricao = this.descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
	
}
