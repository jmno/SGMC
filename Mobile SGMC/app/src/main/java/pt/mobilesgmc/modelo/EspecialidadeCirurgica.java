package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 23/01/2015.
 */
public class EspecialidadeCirurgica {

    private int id;
    private String especialidade;

    public EspecialidadeCirurgica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return especialidade + '\'' ;
    }
}
