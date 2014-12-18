package pt.mobilesgmc.modelo;

import java.util.LinkedList;

public class DadosIntraoperatorioFinal {

	private DadosIntraoperatorio dados;
	private LinkedList<SinaisVitais> listaSinais;
	private LinkedList<MedicacaoAdministrada> listaMedicacao;
	private LinkedList<BalancoHidrico> listaBalanco;
	private LinkedList<Drenagem> listaDrenagemVesical;
	private LinkedList<Drenagem> listaDrenagemNasogastrica;
	private LinkedList<Eliminacao> listaEliminacao;
	private AdministracaoSangue administracao;
	public DadosIntraoperatorio getDados() {
		return dados;
	}
	public void setDados(DadosIntraoperatorio dados) {
		this.dados = dados;
	}
	public LinkedList<SinaisVitais> getListaSinais() {
		return listaSinais;
	}
	public void setListaSinais(LinkedList<SinaisVitais> listaSinais) {
		this.listaSinais = listaSinais;
	}
	public LinkedList<MedicacaoAdministrada> getListaMedicacao() {
		return listaMedicacao;
	}
	public void setListaMedicacao(LinkedList<MedicacaoAdministrada> listaMedicacao) {
		this.listaMedicacao = listaMedicacao;
	}
	public LinkedList<BalancoHidrico> getListaBalanco() {
		return listaBalanco;
	}
	public void setListaBalanco(LinkedList<BalancoHidrico> listaBalanco) {
		this.listaBalanco = listaBalanco;
	}
	public LinkedList<Drenagem> getListaDrenagemVesical() {
		return listaDrenagemVesical;
	}
	public void setListaDrenagemVesical(LinkedList<Drenagem> listaDrenagemVesical) {
		this.listaDrenagemVesical = listaDrenagemVesical;
	}
	public LinkedList<Drenagem> getListaDrenagemNasogastrica() {
		return listaDrenagemNasogastrica;
	}
	public void setListaDrenagemNasogastrica(
			LinkedList<Drenagem> listaDrenagemNasogastrica) {
		this.listaDrenagemNasogastrica = listaDrenagemNasogastrica;
	}
	public LinkedList<Eliminacao> getListaEliminacao() {
		return listaEliminacao;
	}
	public void setListaEliminacao(LinkedList<Eliminacao> listaEliminacao) {
		this.listaEliminacao = listaEliminacao;
	}
	public AdministracaoSangue getAdministracao() {
		return administracao;
	}
	public void setAdministracao(AdministracaoSangue administracao) {
		this.administracao = administracao;
	}
	
	public DadosIntraoperatorioFinal(){}
}
