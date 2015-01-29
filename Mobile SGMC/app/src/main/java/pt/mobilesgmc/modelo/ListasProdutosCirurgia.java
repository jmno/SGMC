package pt.mobilesgmc.modelo;

import java.util.ArrayList;

/**
 * Created by Nicolau on 29/01/15.
 */
public class ListasProdutosCirurgia {

    private ArrayList<ProdutosCirurgia> listaAparelhos;
    private ArrayList<ProdutosCirurgia> listaInstrumentos;
    private ArrayList<ProdutosCirurgia> listaMateriais;


    public void ListaProdutosCirurgia()
    {
        this.listaAparelhos = new ArrayList<>();
        this.listaInstrumentos = new ArrayList<>();
        this.listaMateriais = new ArrayList<>();
    }

    public ArrayList<ProdutosCirurgia> getListaAparelhos() {
        return listaAparelhos;
    }

    public void setListaAparelhos(ArrayList<ProdutosCirurgia> listaAparelhos) {
        this.listaAparelhos = listaAparelhos;
    }

    public ArrayList<ProdutosCirurgia> getListaInstrumentos() {
        return listaInstrumentos;
    }

    public void setListaInstrumentos(ArrayList<ProdutosCirurgia> listaInstrumentos) {
        this.listaInstrumentos = listaInstrumentos;
    }

    public ArrayList<ProdutosCirurgia> getListaMateriais() {
        return listaMateriais;
    }

    public void setListaMateriais(ArrayList<ProdutosCirurgia> listaMateriais) {
        this.listaMateriais = listaMateriais;
    }

    public void setListas(ArrayList<ProdutosCirurgia> lista)
    {
        this.listaAparelhos = new ArrayList<>();
        this.listaMateriais = new ArrayList<>();
        this.listaInstrumentos = new ArrayList<>();
        for (int i=0; i<lista.size(); i++)
        {
            if(lista.get(i).getTipoProduto().toLowerCase().equals("a"))
                listaAparelhos.add(lista.get(i));
            else if (lista.get(i).getTipoProduto().toLowerCase().equals("i"))
                listaInstrumentos.add(lista.get(i));
            else if(lista.get(i).getTipoProduto().toLowerCase().equals("m"))
                listaMateriais.add(lista.get(i));
        }

    }

    public int getSize()
    {
        int tamanho =0;
        if(listaAparelhos!=null)
            tamanho += listaAparelhos.size();
        if(listaInstrumentos!=null)
            tamanho+=listaInstrumentos.size();
        if(listaMateriais!=null)
            tamanho+=listaMateriais.size();

        return tamanho;

    }

    public ArrayList<ProdutosCirurgia> getListas()
    {
        ArrayList<ProdutosCirurgia> lista = new ArrayList<>();
        if(listaAparelhos!=null)
            lista.addAll(listaAparelhos);
        if(listaInstrumentos!=null)
            lista.addAll(listaInstrumentos);
        if(listaMateriais!=null)
            lista.addAll(listaMateriais);

        return lista;
    }

}
