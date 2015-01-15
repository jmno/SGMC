package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class JuncaoListaProdutos {

    private int id;
    private int idProduto;
    private int idListaPredefinida;
    private int quantidade;

    public JuncaoListaProdutos(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdListaPredefinida() {
        return idListaPredefinida;
    }

    public void setIdListaPredefinida(int idListaPredefinida) {
        this.idListaPredefinida = idListaPredefinida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


}
