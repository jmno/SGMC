package pt.mobilesgmc.modelo;

/**
 * Created by Carolina P Soares on 20/01/2015.
 */
public class AcessoVenoso {


    private int id;
    private String tipoAcessoVenoso;
    private double calibreAcessoVenoso;
    private String localizacaoAcessoVenoso;
   private int idIntra;

    public AcessoVenoso(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoAcessoVenoso() {
        return tipoAcessoVenoso;
    }

    public void setTipoAcessoVenoso(String tipoAcessoVenoso) {
        this.tipoAcessoVenoso = tipoAcessoVenoso;
    }

    public double getCalibreAcessoVenoso() {
        return calibreAcessoVenoso;
    }

    public void setCalibreAcessoVenoso(double calibreAcessoVenoso) {
        this.calibreAcessoVenoso = calibreAcessoVenoso;
    }

    public String getLocalizacaoAcessoVenoso() {
        return localizacaoAcessoVenoso;
    }

    public void setLocalizacaoAcessoVenoso(String localizacaoAcessoVenoso) {
        this.localizacaoAcessoVenoso = localizacaoAcessoVenoso;
    }

    public int getIdIntra() {
        return idIntra;
    }

    public void setIdIntra(int idIntra) {
        this.idIntra = idIntra;
    }

    @Override
    public String toString() {
        return "AcessoVenoso{" +
                "id=" + id +
                ", tipoAcessoVenoso='" + tipoAcessoVenoso + '\'' +
                ", calibreAcessoVenoso=" + calibreAcessoVenoso +
                ", localizacaoAcessoVenoso='" + localizacaoAcessoVenoso + '\'' +
                ", idIntra=" + idIntra +
                '}';
    }
}
