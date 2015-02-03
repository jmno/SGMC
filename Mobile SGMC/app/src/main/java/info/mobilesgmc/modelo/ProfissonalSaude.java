package info.mobilesgmc.modelo;

import java.util.Comparator;

public class ProfissonalSaude implements Comparable<ProfissonalSaude>{

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
	
	 @Override
	    public int compareTo(ProfissonalSaude o) {
	        return Comparators.NAME.compare(this, o);
	    }

	public static class Comparators {

        public static Comparator<ProfissonalSaude> NAME = new Comparator<ProfissonalSaude>() {
            @Override
            public int compare(ProfissonalSaude o1, ProfissonalSaude o2) {
                return o1.nome.toLowerCase().compareTo(o2.nome.toLowerCase());
            }
        };
	   }
	

}
