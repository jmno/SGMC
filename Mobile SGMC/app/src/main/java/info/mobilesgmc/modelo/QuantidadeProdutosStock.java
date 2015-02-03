package info.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 15/01/2015.
 */
public class QuantidadeProdutosStock {


    private int id;
    private int qntExistente ;
   private int qntStock ;

    public QuantidadeProdutosStock() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQntExistente() {
        return qntExistente;
    }

    public void setQntExistente(int qntExistente) {
        this.qntExistente = qntExistente;
    }

    public int getQntStock() {
        return qntStock;
    }

    public void setQntStock(int qntStock) {
        this.qntStock = qntStock;
    }

    @Override
    public String toString() {
        return "QuantidadeProdutosStock{" +
                "id=" + id +
                ", qntExistente=" + qntExistente +
                ", qntStock=" + qntStock +
                '}';
    }
}
