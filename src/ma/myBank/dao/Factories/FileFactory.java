package ma.myBank.dao.Factories;

import ma.myBank.dao.IDao;
import ma.myBank.modéle.Crédit;

public class FileFactory extends Factory{
    @Override
    public IDao<Crédit, Long> getCréditDao() {
        return null;
    }
}
