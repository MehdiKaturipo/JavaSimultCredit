package ma.myBank.métier;
import lombok.*;
import ma.myBank.dao.IDao;
import ma.myBank.modéle.Crédit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Data @AllArgsConstructor @NoArgsConstructor
@Service("métier")
public class MétierCrédit implements IMétierCrédit {
    @Autowired
    @Qualifier("dao")
    IDao<Crédit,Long> creditDao;

   @Override
    public Crédit calculerMensualité(Long idCrédit) throws Exception {
        var credit = creditDao.findById(idCrédit);
        if (credit == null) throw new Exception(" Id du crédit est incorrect Crédit introuvable :: [crédit non trouvé]");
        else {
         double taux = credit.getTaux_Mensuel() ;
         taux = taux / 1200;
         double capitale = credit.getCapitale_Emprunté();
         int    Nbr_mois = credit.getNombreDeMois();
         double mensualité = (capitale * taux )/ (1 - Math.pow(1 + taux, -1 *Nbr_mois));
         mensualité = Math.round(mensualité * 100) / 100;
            credit.setMensualité(mensualité);

        }
        return credit;
    }
}
