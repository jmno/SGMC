package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 15/01/2015.
 */
public class ProdutosCirurgiaComProdutos {

    private int id;
    private String nomeProduto;
    private int quantidade;
    private String tipoProduto;
    private Boolean preparado;
    private int idCirurgia;
    private int idProduto;
    private String codigoProduto;
    private String referenciaProduto;

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

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getReferenciaProduto() {
        return referenciaProduto;
    }

    public void setReferenciaProduto(String referenciaProduto) {
        this.referenciaProduto = referenciaProduto;
    }

    @Override
    public String toString() {
        return
                nomeProduto + "- Quantidade=" + quantidade;
    }
}
