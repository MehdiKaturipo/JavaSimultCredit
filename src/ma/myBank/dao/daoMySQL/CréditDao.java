package ma.myBank.dao.daoMySQL;

import ma.myBank.dao.IDao;
import ma.myBank.dao.daoException.DAOException;
import ma.myBank.modéle.Crédit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CréditDao implements IDao<Crédit,Long> {

    private Connection connection;
    public CréditDao(Connection connection) {
        this.connection= connection;
    }

    private  static Crédit map(ResultSet resultSet) throws SQLException {
        Crédit credit = new Crédit();
        credit.setId(resultSet.getLong("id"));
        credit.setCapitale_Emprunté(resultSet.getDouble("capital"));
        credit.setNombreDeMois(resultSet.getInt("nbr_mois"));
        credit.setTaux_Mensuel(resultSet.getDouble("taux_mensialité"));
        credit.setDemandeur(resultSet.getString("demandeur"));
        credit.setMensualité(resultSet.getDouble("mensualité"));
        return credit;
    }
    private static final String COLUMN = "id,capital,nbr_mois,taux_mensialité,demandeur,mensualité";
    private static final String SELECTBYID = "SELECT * FROM credit WHERE id = ?";
    public Crédit findById(Long id){
        Crédit crédit = null;
        ResultSet RS = null;
        PreparedStatement PS = null;
        try {
              PS= Utilitaire.initPS(connection,SELECTBYID,false,id);
              RS= PS.executeQuery();
              crédit = map(RS);
            System.out.println("[SQL] : " + SELECTBYID + " " + id);}
        catch (SQLException ex) {throw new DAOException("Problem win find client Method "+ex.getMessage() );}
        finally {
            Utilitaire.closeDAOObjects(RS,PS);}
        return crédit;
    }

    private static final String SELECT_ALL = "SELECT"+COLUMN+" * FROM crédit";
    public List<Crédit> findAll(){
        List<Crédit> crédits =new ArrayList<>();
        ResultSet RS = null;
        PreparedStatement PS = null;
        try {
            PS= Utilitaire.initPS(connection,SELECT_ALL,false);
            RS= PS.executeQuery();
            System.out.println("[SQL] : " + SELECT_ALL);
            while (RS.next()){
               Crédit crédit = map(RS);
                crédits.add(map(RS));
            }
        }catch (SQLException ex) {throw new DAOException("Problem win find client Method "+ex.getMessage() );}
        finally {Utilitaire.closeDAOObjects(RS,PS);}
        return crédits;
    }
    public static String DELETE = "DELETE FROM credit WHERE id = ?";
    public Boolean delete(Crédit crédit){return deleteById(crédit.getId());}
    public Boolean deleteById(Long id){
        int status = 0;
        PreparedStatement PS = null;
        try {
            PS= Utilitaire.initPS(connection,DELETE,false,id);
            status =PS.executeUpdate();

          if (status == 0) throw new DAOException("suppression echoué!!! 0 ligne de la table crédit");
          else {
                System.out.println("suppression réussi !!! 1 ligne de la table crédit");
                System.out.println("[SQL] : DELETE FROM credit WHERE id = " + id);
          }
        }catch (SQLException ex) {throw new DAOException("Problem dans la Method delete"+ex.getMessage() );}
        finally {Utilitaire.closeDAOObjects(PS);}
        return true;
    }
    public static  final String INSERT_COLUMN =  "(capital,nbr_mois,taux_mensialité,demandeur,mensualité )";
    public static final String INSERT = "INSERT INTO crédit "+INSERT_COLUMN+" VALUES (?,?,?,?,?)";

    public Crédit save(Crédit crédit) {
        int status = 0;
        PreparedStatement PS = null;
        ResultSet autoGeneratedValues = null;
        try {
            PS= Utilitaire.initPS(connection,INSERT,true,crédit.getCapitale_Emprunté(),
                                                                  crédit.getNombreDeMois(),
                                                                  crédit.getTaux_Mensuel(),
                                                                  crédit.getDemandeur(),
                                                                  crédit.getMensualité());
            status =PS.executeUpdate();
            if (status == 0) throw new DAOException("insertion echoué!!! 0 ligne de la table crédit");
            autoGeneratedValues = PS.getGeneratedKeys();
            if (autoGeneratedValues.next()) {
                crédit.setId(autoGeneratedValues.getLong(1));
                System.out.println("[SQL] : INSERT INTO crédit VALUES (" +
                        crédit.getId() + "," +
                        crédit.getCapitale_Emprunté() + "," +
                        crédit.getNombreDeMois() + "," +
                        crédit.getTaux_Mensuel() + "," +
                        crédit.getDemandeur() + "," +
                        crédit.getMensualité() + ")");
                System.out.println("Enregistrement réussi !!! id auto-generé retourné avec succès");
            } else {
                throw new DAOException("insertion echoué!!!pas id auto-generé retourné avec succès");
            }
        }catch (SQLException ex) {throw new DAOException("Problem dans la Method save"+ex.getMessage() );}
        finally {Utilitaire.closeDAOObjects(autoGeneratedValues,PS);}
        return crédit;
    }

    public static final String UPDATE_COLUMN = "capital = ?, nbr_mois = ?, taux_mensialité = ?, demandeur = ?, mensualité = ? ";
    public static final String UPDATE = "UPDATE crédit SET "+UPDATE_COLUMN+" WHERE id = ?";
    public Crédit update(Crédit crédit) {
        int status = 0;
        PreparedStatement PS = null;
        try {
            PS= Utilitaire.initPS(connection,UPDATE,false,crédit.getCapitale_Emprunté(),
                                                                  crédit.getNombreDeMois(),
                                                                  crédit.getTaux_Mensuel(),
                                                                  crédit.getDemandeur(),
                                                                  crédit.getMensualité(),
                                                                  crédit.getId());
            status =PS.executeUpdate();
            if (status == 0) throw new DAOException("mise à jour echoué!!! 0 ligne de la table crédit");
            else {
                System.out.println("[SQL] : UPDATE crédit SET " +
                        "capital = " + crédit.getCapitale_Emprunté() + "," +
                        "nbr_mois = " + crédit.getNombreDeMois() + "," +
                        "taux_mensialité = " + crédit.getTaux_Mensuel() + "," +
                        "demandeur = " + crédit.getDemandeur() + "," +
                        "mensualité = " + crédit.getMensualité() + " WHERE id = " + crédit.getId());
                System.out.println("mise à jour réussi !!! 1 ligne modifiée de la table crédit");
            }
        }catch (SQLException ex) {throw new DAOException("Problem dans la Method update"+ex.getMessage() );}
        finally {Utilitaire.closeDAOObjects(PS);}
        return crédit;
    }
}