package ma.myBank.dao.Factories;

import ma.myBank.dao.IDao;
import ma.myBank.modéle.Crédit;

public abstract class Factory {
    public static final int MYSQL_DAO_FACTORY = 0;
    public static final int FILE_DAO_FACTORY = 1;
    public static final int VOLATILE_DAO_FACTORY = 2;


    public abstract IDao<Crédit, Long> getCréditDao();

  public static Factory getFactory(int type) {
    switch (type) {
      case MYSQL_DAO_FACTORY:
        return MySqlFactory.INSTANCE;
      case FILE_DAO_FACTORY:
        return new FileFactory();
      case VOLATILE_DAO_FACTORY:
        return new VolatileFactory();
      default:
        return null;
    }
  }
}
