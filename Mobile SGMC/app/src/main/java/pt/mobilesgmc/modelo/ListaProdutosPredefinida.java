package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 14/01/2015.
 */
public class ListaProdutosPredefinida {

    private int id;
    private String nome;
    private  String especialidade;
    private String tipoCirurgia;

    public  ListaProdutosPredefinida(){}

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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTipoCirurgia() {
        return tipoCirurgia;
    }

    public void setTipoCirurgia(String tipoCirurgia) {
        this.tipoCirurgia = tipoCirurgia;
    }

    @Override
    public String toString() {
        return nome + " - Tipo Cirurgia: " + tipoCirurgia + " - Especialidade=" + especialidade;
    }
}
