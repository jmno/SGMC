package info.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class ListaProdutosComProdutos {

    private ListaProdutosPredefinida lista ;

    private ListaProdutosComJuncao produtos;

    public ListaProdutosComProdutos(){}

    public ListaProdutosPredefinida getLista() {
        return lista;
    }

    public void setLista(ListaProdutosPredefinida lista) {
        this.lista = lista;
    }

    public ListaProdutosComJuncao getProdutos() {
        return produtos;
    }

    public void setProdutos(ListaProdutosComJuncao produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return lista +"" ;
    }
}
