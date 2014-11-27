package pt.mobilesgmc.modelo;

import java.sql.Date;

import android.text.method.DateTimeKeyListener;

public class Utente {
	
	private int id;
	private String nome;
	private int numProcesso;
	private Date dataNascimento;
	private String subsistema;
	private String alergias;
	private String patologias;
	private String antecedentesCirurgicos;
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
	public int getNumProcesso() {
		return numProcesso;
	}
	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
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
		return antecedentesCirurgicos;
	}
	public void setAntecedentesCirurgicos(String antecedentesCirurgicos) {
		this.antecedentesCirurgicos = antecedentesCirurgicos;
	}
	
	public Utente (){}
	
	public Utente (int id, String nome, int numProc, Date dataNasc, 
			String subsistema, String alergias, String patologias, 
			String antecedentesCirurgicos)
	{
		this.id=id;
		this.nome= nome;
		this.numProcesso = numProc;
		this.dataNascimento=dataNasc;
		this.subsistema = subsistema;
		this.alergias = alergias;
		this.patologias = patologias;
		this.antecedentesCirurgicos = antecedentesCirurgicos;
	}
	@Override
	public String toString() {
		return numProcesso +" " + nome ;
	}
	
	
	
	
	
	

}
