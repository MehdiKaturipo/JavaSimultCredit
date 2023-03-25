package ma.myBank.dao.daoMySQL;

import java.sql.*;

public class Utilitaire {

    public static PreparedStatement initPS(Connection CNX, String SQL, boolean geneatekey, Object... Columns) throws SQLException{

        PreparedStatement PS= null;
            PS    = CNX.prepareStatement(SQL, geneatekey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < Columns.length; i++) {
            PS.setObject(i+1, Columns[i]);
        }
        return PS;
    }
    public static void closeDAOObjects(ResultSet RS){
        try {
            if (RS != null) {
                RS.close();
            }
        } catch (SQLException e) {
            System.out.println("Probleme while closing ResultSet : (" + e.getMessage() + ")");
        }
    }
    public static void closeDAOObjects(PreparedStatement PS){
        try {
            if (PS != null) {
                PS.close();
            }
        } catch (SQLException e) {
            System.out.println("Probleme while closing Statement : (" + e.getMessage() + ")");
        }
    }
    public static void closeDAOObjects(Connection CNX){
        try {
            if (CNX != null) {
                CNX.close();
            }
        } catch (SQLException e) {
            System.out.println("Probleme while closing Connection : (" + e.getMessage() + ")");
        }
    }
    public static void closeDAOObjects(ResultSet RS, PreparedStatement PS, Connection CNX){
        closeDAOObjects(RS);
        closeDAOObjects(PS);
        closeDAOObjects(CNX);
    }
    public static void closeDAOObjects(ResultSet RS,PreparedStatement PS ){
        closeDAOObjects(PS);
        closeDAOObjects(RS);
    }
}
