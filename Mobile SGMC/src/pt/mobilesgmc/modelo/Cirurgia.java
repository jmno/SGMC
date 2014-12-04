package pt.mobilesgmc.modelo;

import java.util.Date;

import java.sql.*;

public class Cirurgia {

	private int id;
	private String especialidade;
	private String cirurgia;
	private Date data;
	private Time hora;
	private String tipoCirurgia;
	private String lateralidade;
	private String classificacaoASA;
	private Time horaChamadaUtente;
	private Time horaEntradaBlocoOperatorio;
	private Time horaSaideBlocoOperatorio;
	private Time horaEntradaSala;
	private Time horaSaidaSala;
	private Time horaInicioAnestesia;
	private Time horaFimAnestesia;
	private Time horaInicioCirurgia;
	private Time horaFimCirurgia;
	private Time horaEntradaRecobro;
	private Time horaFimRecobro;
	private String destinoDoente;
	private String infoRelevante;
	private int idSala;
	private int idUtente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getCirurgia() {
		return cirurgia;
	}

	public void setCirurgia(String cirurgia) {
		this.cirurgia = cirurgia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getTipoCirurgia() {
		return tipoCirurgia;
	}

	public void setTipoCirurgia(String tipoCirurgia) {
		this.tipoCirurgia = tipoCirurgia;
	}

	public String getLateralidade() {
		return lateralidade;
	}

	public void setLateralidade(String lateralidade) {
		this.lateralidade = lateralidade;
	}

	public String getClassificacaoASA() {
		return classificacaoASA;
	}

	public void setClassificacaoASA(String classificacaoASA) {
		this.classificacaoASA = classificacaoASA;
	}

	public Time getHoraChamadaUtente() {
		return horaChamadaUtente;
	}

	public void setHoraChamadaUtente(Time horaChamadaUtente) {
		this.horaChamadaUtente = horaChamadaUtente;
	}

	public Time getHoraEntradaBlocoOperatorio() {
		return horaEntradaBlocoOperatorio;
	}

	public void setHoraEntradaBlocoOperatorio(Time horaEntradaBlocoOperatorio) {
		this.horaEntradaBlocoOperatorio = horaEntradaBlocoOperatorio;
	}

	public Time getHoraSaideBlocoOperatorio() {
		return horaSaideBlocoOperatorio;
	}

	public void setHoraSaideBlocoOperatorio(Time horaSaideBlocoOperatorio) {
		this.horaSaideBlocoOperatorio = horaSaideBlocoOperatorio;
	}

	public Time getHoraEntradaSala() {
		return horaEntradaSala;
	}

	public void setHoraEntradaSala(Time horaEntradaSala) {
		this.horaEntradaSala = horaEntradaSala;
	}

	public Time getHoraSaidaSala() {
		return horaSaidaSala;
	}

	public void setHoraSaidaSala(Time horaSaidaSala) {
		this.horaSaidaSala = horaSaidaSala;
	}

	public Time getHoraInicioAnestesia() {
		return horaInicioAnestesia;
	}

	public void setHoraInicioAnestesia(Time horaInicioAnestesia) {
		this.horaInicioAnestesia = horaInicioAnestesia;
	}

	public Time getHoraFimAnestesia() {
		return horaFimAnestesia;
	}

	public void setHoraFimAnestesia(Time horaFimAnestesia) {
		this.horaFimAnestesia = horaFimAnestesia;
	}

	public Time getHoraInicioCirurgia() {
		return horaInicioCirurgia;
	}

	public void setHoraInicioCirurgia(Time horaInicioCirurgia) {
		this.horaInicioCirurgia = horaInicioCirurgia;
	}

	public Time getHoraFimCirurgia() {
		return horaFimCirurgia;
	}

	public void setHoraFimCirurgia(Time horaFimCirurgia) {
		this.horaFimCirurgia = horaFimCirurgia;
	}

	public Time getHoraEntradaRecobro() {
		return horaEntradaRecobro;
	}

	public void setHoraEntradaRecobro(Time horaEntradaRecobro) {
		this.horaEntradaRecobro = horaEntradaRecobro;
	}

	public Time getHoraFimRecobro() {
		return horaFimRecobro;
	}

	public void setHoraFimRecobro(Time horaFimRecobro) {
		this.horaFimRecobro = horaFimRecobro;
	}

	public String getDestinoDoente() {
		return destinoDoente;
	}

	public void setDestinoDoente(String destinoDoente) {
		this.destinoDoente = destinoDoente;
	}

	public String getInfoRelevante() {
		return infoRelevante;
	}

	public void setInfoRelevante(String infoRelevante) {
		this.infoRelevante = infoRelevante;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	
	public Cirurgia ()
	{
		
	}
	

	@Override
	public String toString() {
		return "Id:" + id +  " Cirurgia: " + cirurgia + " Data: " + data.toString();
	}
}
