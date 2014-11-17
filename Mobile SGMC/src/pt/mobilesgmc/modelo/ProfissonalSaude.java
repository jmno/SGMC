package pt.mobilesgmc.modelo;

public class ProfissonalSaude {

	private int id;
	private String nome;
	private String cc;
	private Tipo tipo;
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
		nome = this.nome;
		cc = this.cc;
	}
	public ProfissonalSaude(int id, String nome, String cc)
	{
		id = this.id;
		nome = this.nome;
		cc = this.cc;
	}
	public ProfissonalSaude(int id, String nome, String cc, Tipo tipo)
	{
		id = this.id;
		nome = this.nome;
		cc = this.cc;
		tipo = this.tipo;
	}
	
	@Override
	public String toString() {
		return nome ;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	

}
