package ma.myBank.modéle;

import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor

public class Crédit {
    private Long id;
    private Double capitale_Emprunté;
    private Integer nombreDeMois;
    private Double taux_Mensuel;
    private String Demandeur;
    private Double mensualité;

    @Override
    public String toString() {
        var creditstr ="= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n";
        creditstr += "=> Crédit N°                                         :" + getId() +"\n";
        creditstr+= "=>  nom du demandeur de credit                        : " + getDemandeur() +"  \n ";
        creditstr += "---------------------------------------------------------------------------------\n";
        creditstr += "=> Capitale emprunté                                 : " + getCapitale_Emprunté() +" \n";
        creditstr += "=> Nombre de mois                                    : " + getNombreDeMois() +"\n";
        creditstr += "=> Taux mensuel                                      : " + getTaux_Mensuel() +"\n";
        creditstr += "--------------------------------------------------------------------------------\n";
        creditstr += "=> Mensualité                                        : "
                               + (getMensualité() == 0.0 ? "NON-CALCULE": getMensualité()+"DH/mois") +"\n";
        creditstr +="= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n";
        return  creditstr;

    }
}
