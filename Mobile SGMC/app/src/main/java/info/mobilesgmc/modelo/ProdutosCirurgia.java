package info.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 15/01/2015.
 */
public class ProdutosCirurgia {


    private int id;
    private String nomeProduto ;
    private int quantidade;
    private String tipoProduto;
    private Boolean preparado;
    private int idCirurgia;
    private int idProduto;
    private Boolean utilizado;

    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }

    public ProdutosCirurgia(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Boolean getPreparado() {
        return preparado;
    }

    public void setPreparado(Boolean preparado) {
        this.preparado = preparado;
    }

    public int getIdCirurgia() {
        return idCirurgia;
    }

    public void setIdCirurgia(int idCirurgia) {
        this.idCirurgia = idCirurgia;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }


    @Override
    public String toString() {
        return  nomeProduto +
                " Quantidade=" + quantidade;
    }
}
