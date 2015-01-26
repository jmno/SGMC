package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 23/01/2015.
 */
public class TipoCirurgia {

    private int id;
    private String tipo;
    private int idEspecialidade;


    public TipoCirurgia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(int idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    @Override
    public String toString() {
        return tipo ;
    }
}
