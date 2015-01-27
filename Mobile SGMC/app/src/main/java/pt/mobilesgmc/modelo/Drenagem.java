package pt.mobilesgmc.modelo;

public class Drenagem {

	private int id;
	private String hora;
	private double drenagem;
	private String caracteristicas;
	private int idEliminacao;

	public Drenagem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getDrenagem() {
        return drenagem;
    }

    public void setDrenagem(double drenagem) {
        this.drenagem = drenagem;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getIdEliminacao() {
        return idEliminacao;
    }

    public void setIdEliminacao(int idEliminacao) {
        this.idEliminacao = idEliminacao;
    }

    @Override
    public String toString() {
        return "Drenagem{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", drenagem='" + drenagem + '\'' +
                ", caracteristicas='" + caracteristicas + '\'' +
                ", idEliminacao=" + idEliminacao +
                '}';
    }
}
