package ma.myBank.dao.daoVolatile;

import ma.myBank.dao.IDao;
import ma.myBank.modéle.Crédit;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component("dao")
public class CréditDao  implements IDao<Crédit,Long> {







    public static Set<Crédit> BDCredit(){
        return  new HashSet<Crédit>(
                Arrays.asList(
                        new Crédit(1L, 300000.0, 120, 2.5, "Mehdi", 0.0),
                        new Crédit(2L, 850000.0, 240, 2.5, "Amine", 0.0),
                        new Crédit(3L, 020000.0, 030, 1.5, "Anass", 0.0),
                        new Crédit(4L, 065000.0, 060, 2.0, "Fatima", 0.0)
                )
        );
    }

    public Crédit findById(Long id) {
        System.out.println("[DAO -DS volatile] trouver le credit n °  : " + id);
        return BDCredit()
                .stream()
                .filter(credit -> credit.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Crédit> findAll() {
        return null;
    }

    @Override
    public Crédit save(Crédit crédit) {
        return null;
    }

    @Override
    public Crédit update(Crédit crédit) {
        return null;
    }

    @Override
    public Boolean delete(Crédit crédit) {
        return null;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return null;
    }
}

