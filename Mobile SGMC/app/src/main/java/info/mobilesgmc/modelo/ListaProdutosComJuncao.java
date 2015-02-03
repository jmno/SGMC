package info.mobilesgmc.modelo;

import java.util.LinkedList;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class ListaProdutosComJuncao {


    private LinkedList<ProdutosDaLista> produto;
    private int idListaProdutosPredefinida;

    public  ListaProdutosComJuncao(){}

    public LinkedList<ProdutosDaLista> getProduto() {
        return produto;
    }

    public void setProduto(LinkedList<ProdutosDaLista> produto) {
        this.produto = produto;
    }

    public int getIdListaProdutosPredefinida() {
        return idListaProdutosPredefinida;
    }

    public void setIdListaProdutosPredefinida(int idListaProdutosPredefinida) {
        this.idListaProdutosPredefinida = idListaProdutosPredefinida;
    }

    @Override
    public String toString() {
        return "ListaProdutosComJuncao{" +
                "produto=" + produto +
                ", idListaProdutosPredefinida=" + idListaProdutosPredefinida +
                '}';
    }
}
