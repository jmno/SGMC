package pt.mobilesgmc.modelo;

import java.util.LinkedList;

public class DadosIntraoperatorioFinal {

	private DadosIntraoperatorio dados;
	private LinkedList<SinaisVitais> listaSinais;
	private LinkedList<MedicacaoAdministrada> listaMedicacao;
	private LinkedList<BalancoHidrico> listaBalancos;
	private LinkedList<Drenagem> listaDrenagemVesical;
	private LinkedList<Drenagem> listaDrenagemNasogastrica;
	private LinkedList<Eliminacao> listaEliminacao;
    private LinkedList<AcessoVenoso> listaAcessoVenoso;
    private LinkedList<PecaBiopsia> listaPecaBiopsia;
	private LinkedList<AdministracaoSangue> adminSangue;

	public DadosIntraoperatorioFinal() {
	}

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

    public LinkedList<BalancoHidrico> getListaBalancos() {
        return listaBalancos;
    }

    public void setListaBalancos(LinkedList<BalancoHidrico> listaBalancos) {
        this.listaBalancos = listaBalancos;
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

    public void setListaDrenagemNasogastrica(LinkedList<Drenagem> listaDrenagemNasogastrica) {
        this.listaDrenagemNasogastrica = listaDrenagemNasogastrica;
    }

    public LinkedList<Eliminacao> getListaEliminacao() {
        return listaEliminacao;
    }

    public void setListaEliminacao(LinkedList<Eliminacao> listaEliminacao) {
        this.listaEliminacao = listaEliminacao;
    }

    public LinkedList<AcessoVenoso> getListaAcessoVenoso() {
        return listaAcessoVenoso;
    }

    public void setListaAcessoVenoso(LinkedList<AcessoVenoso> listaAcessoVenoso) {
        this.listaAcessoVenoso = listaAcessoVenoso;
    }

    public LinkedList<PecaBiopsia> getListaPecaBiopsia() {
        return listaPecaBiopsia;
    }

    public void setListaPecaBiopsia(LinkedList<PecaBiopsia> listaPecaBiopsia) {
        this.listaPecaBiopsia = listaPecaBiopsia;
    }

    public LinkedList<AdministracaoSangue> getAdminSangue() {
        return adminSangue;
    }

    public void setAdminSangue(LinkedList<AdministracaoSangue> adminSangue) {
        this.adminSangue = adminSangue;
    }

    @Override
    public String toString() {
        return "DadosIntraoperatorioFinal{" +
                "dados=" + dados +
                ", listaSinais=" + listaSinais +
                ", listaMedicacao=" + listaMedicacao +
                ", listaBalancos=" + listaBalancos +
                ", listaDrenagemVesical=" + listaDrenagemVesical +
                ", listaDrenagemNasogastrica=" + listaDrenagemNasogastrica +
                ", listaEliminacao=" + listaEliminacao +
                ", listaAcessoVenoso=" + listaAcessoVenoso +
                ", listaPecaBiopsia=" + listaPecaBiopsia +
                ", adminSangue=" + adminSangue +
                '}';
    }
}
