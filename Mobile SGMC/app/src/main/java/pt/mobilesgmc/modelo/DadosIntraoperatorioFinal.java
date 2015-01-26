package pt.mobilesgmc.modelo;

import java.util.ArrayList;

public class DadosIntraoperatorioFinal {

	private DadosIntraoperatorio dados;
	private ArrayList<SinaisVitais> listaSinais;
	private ArrayList<MedicacaoAdministrada> listaMedicacao;
	private ArrayList<BalancoHidrico> listaBalancos;
	private ArrayList<Drenagem> listaDrenagemVesical;
	private ArrayList<Drenagem> listaDrenagemNasogastrica;
	private ArrayList<Eliminacao> listaEliminacao;
    private ArrayList<AcessoVenoso> listaAcessoVenoso;
    private ArrayList<PecaBiopsia> listaPecaBiopsia;
	private ArrayList<AdministracaoSangue> adminSangue;

	public DadosIntraoperatorioFinal() {
	}

    public DadosIntraoperatorio getDados() {
        return dados;
    }

    public void setDados(DadosIntraoperatorio dados) {
        this.dados = dados;
    }

    public ArrayList<SinaisVitais> getListaSinais() {
        return listaSinais;
    }

    public void setListaSinais(ArrayList<SinaisVitais> listaSinais) {
        this.listaSinais = listaSinais;
    }

    public ArrayList<MedicacaoAdministrada> getListaMedicacao() {
        return listaMedicacao;
    }

    public void setListaMedicacao(ArrayList<MedicacaoAdministrada> listaMedicacao) {
        this.listaMedicacao = listaMedicacao;
    }

    public ArrayList<BalancoHidrico> getListaBalancos() {
        return listaBalancos;
    }

    public void setListaBalancos(ArrayList<BalancoHidrico> listaBalancos) {
        this.listaBalancos = listaBalancos;
    }

    public ArrayList<Drenagem> getListaDrenagemVesical() {
        return listaDrenagemVesical;
    }

    public void setListaDrenagemVesical(ArrayList<Drenagem> listaDrenagemVesical) {
        this.listaDrenagemVesical = listaDrenagemVesical;
    }

    public ArrayList<Drenagem> getListaDrenagemNasogastrica() {
        return listaDrenagemNasogastrica;
    }

    public void setListaDrenagemNasogastrica(ArrayList<Drenagem> listaDrenagemNasogastrica) {
        this.listaDrenagemNasogastrica = listaDrenagemNasogastrica;
    }

    public ArrayList<Eliminacao> getListaEliminacao() {
        return listaEliminacao;
    }

    public void setListaEliminacao(ArrayList<Eliminacao> listaEliminacao) {
        this.listaEliminacao = listaEliminacao;
    }

    public ArrayList<AcessoVenoso> getListaAcessoVenoso() {
        return listaAcessoVenoso;
    }

    public void setListaAcessoVenoso(ArrayList<AcessoVenoso> listaAcessoVenoso) {
        this.listaAcessoVenoso = listaAcessoVenoso;
    }

    public ArrayList<PecaBiopsia> getListaPecaBiopsia() {
        return listaPecaBiopsia;
    }

    public void setListaPecaBiopsia(ArrayList<PecaBiopsia> listaPecaBiopsia) {
        this.listaPecaBiopsia = listaPecaBiopsia;
    }

    public ArrayList<AdministracaoSangue> getAdminSangue() {
        return adminSangue;
    }

    public void setAdminSangue(ArrayList<AdministracaoSangue> adminSangue) {
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
