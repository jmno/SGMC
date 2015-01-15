package pt.mobilesgmc.sinaisVitais;

import java.util.ArrayList;

/**
 * Created by Nicolau on 13/01/15.
 */
public class ParentSinaisVitais {

    private String sinaisVitais;


    // ArrayList to store child objects
    private ArrayList<ChildSinaisVitais> children;


    public String getSinaisVitais() {
        return sinaisVitais;
    }

    public void setSinaisVitais(String sinaisVitais) {
        this.sinaisVitais = sinaisVitais;
    }

    // ArrayList to store child objects
    public ArrayList<ChildSinaisVitais> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildSinaisVitais> children) {
        this.children = children;
    }

}
