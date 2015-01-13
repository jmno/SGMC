package pt.mobilesgmc.modelo;

import java.util.LinkedList;

public class Utente {
	
	private int id;
	private String nomeUtente;
	private int numProcesso;
	private String dataNascimento;
	private String subsistema;
	private String alergias;
	private String patologias;
	private String antecendentesCirurgicos;
    private String sexo;

    public LinkedList<Cirurgia> getCirurgias() {
        return cirurgias;
    }

    public void setCirurgias(LinkedList<Cirurgia> cirurgias) {
        this.cirurgias = cirurgias;
    }

    private LinkedList<Cirurgia> cirurgias;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nomeUtente;
	}
	public void setNome(String nome) {
		this.nomeUtente = nome;
	}
	public int getNumProcesso() {
		return numProcesso;
	}
	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSubsistema() {
		return subsistema;
	}
	public void setSubsistema(String subsistema) {
		this.subsistema = subsistema;
	}
	public String getAlergias() {
		return alergias;
	}
	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}
	public String getPatologias() {
		return patologias;
	}
	public void setPatologias(String patologias) {
		this.patologias = patologias;
	}
	public String getAntecedentesCirurgicos() {
		return antecendentesCirurgicos;
	}
	public void setAntecedentesCirurgicos(String antecedentesCirurgicos) {
		this.antecendentesCirurgicos = antecedentesCirurgicos;
	}
	
	public Utente (){}
	
	public Utente (int id, String nome, int numProc, String dataNasc, 
			String subsistema, String alergias, String patologias, 
			String antecedentesCirurgicos)
	{
		this.id=id;
		this.nomeUtente= nome;
		this.numProcesso = numProc;
		this.dataNascimento=dataNasc;
		this.subsistema = subsistema;
		this.alergias = alergias;
		this.patologias = patologias;
		this.antecendentesCirurgicos = antecedentesCirurgicos;
	}
	@Override
	public String toString() {
		return  nomeUtente;
	}


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
