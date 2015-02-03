package info.mobilesgmc.modelo;

public class BalancoHidrico {

	private int id;
	private String hora;
	private double valorAdministracaoSangue;
    private double valorEliminacao;
    private double valorTotal;
	private int idIntraOperatorio;

	public BalancoHidrico(){}

    public BalancoHidrico(double valorAdminSangue, double valorElim)
    {
        this.valorAdministracaoSangue = valorAdminSangue;
        this.valorEliminacao = valorElim;
        this.valorTotal = (valorAdministracaoSangue-valorEliminacao);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getValorAdministracaoSangue() {
        return valorAdministracaoSangue;
    }

    public void setValorAdministracaoSangue(double valorAdministracaoSangue) {
        this.valorAdministracaoSangue = valorAdministracaoSangue;
        this.valorTotal = this.valorAdministracaoSangue-this.valorEliminacao;
    }

    public double getValorEliminacao() {
        return valorEliminacao;
    }

    public void setValorEliminacao(double valorEliminacao) {
        this.valorEliminacao = valorEliminacao;
        this.valorTotal = this.valorAdministracaoSangue-this.valorEliminacao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdIntraOperatorio() {
        return idIntraOperatorio;
    }

    public void setIdIntraOperatorio(int idIntraOperatorio) {
        this.idIntraOperatorio = idIntraOperatorio;
    }

    @Override
    public String toString() {
        return "BalancoHidrico{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", valorAdministracaoSangue=" + valorAdministracaoSangue +
                ", valorEliminacao=" + valorEliminacao +
                ", valorTotal=" + valorTotal +
                ", idIntraOperatorio=" + idIntraOperatorio +
                '}';
    }
}
