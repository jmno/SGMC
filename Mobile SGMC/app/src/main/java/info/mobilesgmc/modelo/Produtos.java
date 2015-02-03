package info.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class Produtos {

    private int id;
    private String nome;
    private String tipo;
    private String referencia;
    private String codigo;
    private int idQuantidadeProdutosStock;

    public Produtos(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdQuantidadeProdutosStock() {
        return idQuantidadeProdutosStock;
    }

    public void setIdQuantidadeProdutosStock(int idQuantidadeProdutosStock) {
        this.idQuantidadeProdutosStock = idQuantidadeProdutosStock;
    }

    @Override
    public String toString() {
        return nome;
    }
}
