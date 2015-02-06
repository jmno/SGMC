package info.mobilesgmc.modelo;

import java.util.LinkedList;

public class EquipaComJuncao {
	
	private LinkedList<ProfissionalDaCirurgia> profissional;
	private String nomeEquipa;
	private int idEquipa;
	
	
	public LinkedList<ProfissionalDaCirurgia> getListaProfissionais() {
		return profissional;
	}
	public void setListaProfissionais(LinkedList<ProfissionalDaCirurgia> listaProfissionais) {
		this.profissional = listaProfissionais;
	}
	public String getNomeEquipa() {
		return (nomeEquipa==null || nomeEquipa.toLowerCase().equals(" "))?"Sem nome":nomeEquipa;
	}
	public void setNomeEquipa(String nomeEquipa) {
		this.nomeEquipa = nomeEquipa;
	}
	public int getIdEquipa() {
		return idEquipa;
	}
	public void setIdEquipa(int idEquipa) {
		this.idEquipa = idEquipa;
	}
	
	@Override
	public String toString() {
		return nomeEquipa;
	}

}
