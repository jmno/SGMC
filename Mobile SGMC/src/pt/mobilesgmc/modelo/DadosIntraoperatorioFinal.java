package info.mobilesgmc.modelo;

import java.util.LinkedList;

public class DadosIntraoperatorioFinal {

	private DadosIntraoperatorio dados;
	private LinkedList<SinaisVitais> listaSinais;
	private LinkedList<MedicacaoAdministrada> listaMedicacao;
	private LinkedList<BalancoHidrico> listaBalancos;
	private LinkedList<Drenagem> listaDrenagemVesical;
	private LinkedList<Drenagem> listaDrenagemNasogastrica;
	private LinkedList<Eliminacao> listaEliminacao;
	private AdministracaoSangue adminSangue;

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

	public void setListaMedicacao(
			LinkedList<MedicacaoAdministrada> listaMedicacao) {
		this.listaMedicacao = listaMedicacao;
	}

	public LinkedList<BalancoHidrico> getListaBalanco() {
		return listaBalancos;
	}

	public void setListaBalanco(LinkedList<BalancoHidrico> listaBalanco) {
		this.listaBalancos = listaBalanco;
	}

	public LinkedList<Drenagem> getListaDrenagemVesical() {
		return listaDrenagemVesical;
	}

	public void setListaDrenagemVesical(
			LinkedList<Drenagem> listaDrenagemVesical) {
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
		return adminSangue;
	}

	public void setAdministracao(AdministracaoSangue administracao) {
		this.adminSangue = administracao;
	}

	public DadosIntraoperatorioFinal() {
	}

	@Override
	public String toString() {

		return dados.toString() + "\nLista de Sinais:" + listaSinais.toString()
				+ "\nLista de Balanco:" + listaBalancos.toString()
				+ "\nLista de Medicacao:" + listaMedicacao.toString()
				+ "\nLista de Eliminacao:" + listaEliminacao.toString()
				+ "\nLista de Drenagem Vesical:" + listaDrenagemVesical
				+ "\nLista de Drenagem Nasogastrica:"
				+ listaDrenagemNasogastrica + "\nAdministracao de Sangue:";
	}
}
