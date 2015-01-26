package pt.mobilesgmc.modelo;

import java.util.ArrayList;

/**
 * Created by Carolina P Soares on 23/01/2015.
 */
public class EspecialidadeTipoCirurgia {

    private ArrayList<TipoCirurgia> tipoCirurgia ;
    private EspecialidadeCirurgica especialidade ;

    public EspecialidadeTipoCirurgia() {
    }

    public ArrayList<TipoCirurgia> getTipoCirurgia() {
        return tipoCirurgia;
    }

    public void setTipoCirurgia(ArrayList<TipoCirurgia> tipoCirurgia) {
        this.tipoCirurgia = tipoCirurgia;
    }

    public EspecialidadeCirurgica getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(EspecialidadeCirurgica especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return especialidade.getEspecialidade();
    }
}
