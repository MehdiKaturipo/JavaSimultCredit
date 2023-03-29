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
            System.out.println("Driver loaded for mysql succefully");
            connection = DriverManager.getConnection(url, login, pass);
            System.out.println("Database connected with Bankkati database");

            //Statement
            sc=connection.prepareStatement("select * from Crédit ");
            resultSet = sc.executeQuery();
            resultMetaDta = resultSet.getMetaData();

            // METHODE 1
//            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
//            while (resultSet.next()){
//                for (int i =1;i<=resultMetaDta.getColumnCount();i++)
//
//                    System.out.print("\t"+resultMetaDta.getColumnName(i).toUpperCase()+
//                            " : "+resultSet.getObject(i).toString() + "\t |");
//                System.out.println();
//                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
//
//            }
            // METHODE 2
            while (resultSet.next()){

                System.out.println("=========================================================");
                System.out.println("                        CREDITs                          ");
                System.out.println("=========================================================");
                System.out.println("#ID        :   "+ resultSet.getInt("id"));
                System.out.println("+OWNER     :   "+ resultSet.getString("Demandeur"));
                System.out.println("-AMOUNT    :   "+ resultSet.getDouble("capitale_Emprunté")+ " DHs");
                System.out.println("-DURATION  :   "+ resultSet.getInt("nombreDeMois")+" Months");
                System.out.println("-RATE      :   "+ resultSet.getDouble("taux_Mensuel")+" %");
                System.out.println("-DHs/Month :   "+ resultSet.getDouble("mensualité")+" DHs");
                System.out.println("=========================================================");

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
