package pt.mobilesgmc.modelo;

import java.util.LinkedList;

/**
 * Created by Carolina P Soares on 15/01/2015.
 */
public class ListaProdutosComProdutosCirurgia {


    private ListaProdutosPredefinida listaPredefinida ;
    private LinkedList<ProdutosCirurgiaComProdutos> listaProdutosCirurgia ;

    public ListaProdutosComProdutosCirurgia(){}

    public ListaProdutosPredefinida getListaPredefinida() {
        return listaPredefinida;
    }

    public void setListaPredefinida(ListaProdutosPredefinida listaPredefinida) {
        this.listaPredefinida = listaPredefinida;
    }

    public LinkedList<ProdutosCirurgiaComProdutos> getListaProdutosCirurgia() {
        return listaProdutosCirurgia;
    }

    public void setListaProdutosCirurgia(LinkedList<ProdutosCirurgiaComProdutos> listaProdutosCirurgia) {
        this.listaProdutosCirurgia = listaProdutosCirurgia;
    }

    @Override
    public String toString() {
        return listaPredefinida +""+
                listaProdutosCirurgia +""
                ;
    }
}
