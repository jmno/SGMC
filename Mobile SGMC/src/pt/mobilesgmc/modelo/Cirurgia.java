package info.mobilesgmc.modelo;

public class Cirurgia {

	private int id;
	private String especialidade;
	private String cirurgia;
	private String data;
	private String hora;
	private String tipoCirurgia;
	private String lateralidade;
	private String classifASA;
	private String horaChamadaUtente;
	private String horaEntradaBO;
	private String horaSaidaBO;
	private String horaEntradaSala;
	private String horaSaidaSala;
	private String horaInicioAnestesia;
	private String horaFimAnestesia;
	private String horaInicioCirurgia;
	private String horaFimCirurgia;
	private String horaEntradaRecobro;
	private String horaSaidaRecobro;
	private String destinoDoente;
	private String infoRelevante;
	private int idSala;
	private int idUtente;
	private int idEquipa;

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


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
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

	public String getClassifASA() {
		return classifASA;
	}

	public void setClassifASA(String classificacaoASA) {
		this.classifASA = classificacaoASA;
	}

	public String getHoraChamadaUtente() {
		return horaChamadaUtente;
	}

	public void setHoraChamadaUtente(String horaChamadaUtente) {
		this.horaChamadaUtente = horaChamadaUtente;
	}

	public String getHoraEntradaBlocoOperatorio() {
		return horaEntradaBO;
	}

	public void setHoraEntradaBlocoOperatorio(String horaEntradaBlocoOperatorio) {
		this.horaEntradaBO = horaEntradaBlocoOperatorio;
	}

	public String getHoraSaideBlocoOperatorio() {
		return horaSaidaBO;
	}

	public void setHoraSaideBlocoOperatorio(String horaSaideBlocoOperatorio) {
		this.horaSaidaBO = horaSaideBlocoOperatorio;
	}

	public String getHoraEntradaSala() {
		return horaEntradaSala;
	}

	public void setHoraEntradaSala(String horaEntradaSala) {
		this.horaEntradaSala = horaEntradaSala;
	}

	public String getHoraSaidaSala() {
		return horaSaidaSala;
	}

	public void setHoraSaidaSala(String horaSaidaSala) {
		this.horaSaidaSala = horaSaidaSala;
	}

	public String getHoraInicioAnestesia() {
		return horaInicioAnestesia;
	}

	public void setHoraInicioAnestesia(String horaInicioAnestesia) {
		this.horaInicioAnestesia = horaInicioAnestesia;
	}

	public String getHoraFimAnestesia() {
		return horaFimAnestesia;
	}

	public void setHoraFimAnestesia(String horaFimAnestesia) {
		this.horaFimAnestesia = horaFimAnestesia;
	}

	public String getHoraInicioCirurgia() {
		return horaInicioCirurgia;
	}

	public void setHoraInicioCirurgia(String horaInicioCirurgia) {
		this.horaInicioCirurgia = horaInicioCirurgia;
	}

	public String getHoraFimCirurgia() {
		return horaFimCirurgia;
	}

	public void setHoraFimCirurgia(String horaFimCirurgia) {
		this.horaFimCirurgia = horaFimCirurgia;
	}

	public String getHoraEntradaRecobro() {
		return horaEntradaRecobro;
	}

	public void setHoraEntradaRecobro(String horaEntradaRecobro) {
		this.horaEntradaRecobro = horaEntradaRecobro;
	}

	public String getHoraFimRecobro() {
		return horaSaidaRecobro;
	}

	public void setHoraFimRecobro(String horaFimRecobro) {
		this.horaSaidaRecobro = horaFimRecobro;
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
		return "Id:" + id +  " Cirurgia: " + getCirurgia() + " Data: " + data.toString() +"   " +horaEntradaBO;
	}

	public int getIdEquipa() {
		return idEquipa;
	}

	public void setIdEquipa(int idEquipa) {
		this.idEquipa = idEquipa;
	}

	public String getCirurgia() {
		return cirurgia;
	}

	public void setCirurgia(String cirurgia) {
		this.cirurgia = cirurgia;
	}
}
