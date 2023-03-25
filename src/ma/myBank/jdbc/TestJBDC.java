package ma.myBank.jdbc;

import lombok.*;
import ma.myBank.modéle.Crédit;

import java.sql.*;

public class TestJBDC {

    public static void main(String[] args) {
        var url ="jdbc:mysql://localhost:3306/bankati" ;
        var login ="root";
        var pass ="";
        var driver ="com.mysql.cj.jdbc.Driver";
        Connection connection = null ;
        Statement statement = null;
        PreparedStatement sc = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultMetaDta = null;

        try {
            Class.forName(driver);
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection(url, login, pass);
            System.out.println("Database connected");

            //Statement
            sc=connection.prepareStatement("select * from credit ");
            resultSet = sc.executeQuery();
            while (resultSet.next()){
                var id = resultSet.getLong("id");
                var cp = resultSet.getDouble("capital");
                var nbrMois = resultSet.getInt("nbr_mois");
                var taux = resultSet.getDouble("taux_mensialité");
                var demandeur = resultSet.getString("demandeur");
                var mensualité = resultSet.getDouble("mensualité");

                Crédit credit = new Crédit(id,cp,nbrMois,taux,demandeur,mensualité);
                resultMetaDta = resultSet.getMetaData();

            }




        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        } catch (SQLException e) {
            System.out.println("Database not connected");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Database not closed");
                }
            }

        }
    }
}