package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class ProdutosDaLista {


    private Produtos produto;
    private int quantidade;

    public ProdutosDaLista(){}

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade() {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return
                 produto +
                "- Quantidade:" + quantidade
                ;
    }
}
