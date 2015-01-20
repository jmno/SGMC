package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 20/01/2015.
 */
public class PecaBiopsia {

    private int id;
   private String descricao;
   private String laboratotio;
   private int idIntra;

    public PecaBiopsia(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLaboratotio() {
        return laboratotio;
    }

    public void setLaboratotio(String laboratotio) {
        this.laboratotio = laboratotio;
    }

    public int getIdIntra() {
        return idIntra;
    }

    public void setIdIntra(int idIntra) {
        this.idIntra = idIntra;
    }

    @Override
    public String toString() {
        return "PecaBiopsia{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", laboratotio='" + laboratotio + '\'' +
                ", idIntra=" + idIntra +
                '}';
    }
}
