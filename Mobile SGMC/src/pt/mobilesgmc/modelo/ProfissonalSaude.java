package pt.mobilesgmc.modelo;

public class ProfissonalSaude {

	private int id;
	private String nome;
	private String cc;
	private int idTipo;
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
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	
	public ProfissonalSaude()
	{
	}
	
	public ProfissonalSaude(String nome, String cc)
	{
		this.nome = nome;
		this.cc= cc;
	}
	public ProfissonalSaude(int id, String nome, String cc)
	{
		this.id = id;
		this.nome = nome;
		this.cc = cc;
	}
	public ProfissonalSaude(int id, String nome, String cc, int idTipo)
	{
		this.id = id;
		this.nome = nome;
		this.cc = cc;
		this.idTipo = idTipo;
	}
	
	@Override
	public String toString() {
		return nome ;
	}
	
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	

}
