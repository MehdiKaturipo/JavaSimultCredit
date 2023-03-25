package ma.myBank;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

import ma.myBank.dao.IDao;
import ma.myBank.dao.daoVolatile.CréditDao;
import ma.myBank.modéle.Crédit;
import ma.myBank.métier.IMétierCrédit;
import ma.myBank.métier.MétierCrédit;
import ma.myBank.présentation.CreditControlleur;
import ma.myBank.présentation.ICréditControlleur;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimulateurDeCrédit_App {
    static Scanner  clavier= new Scanner(System.in);
    static ICréditControlleur créditControlleur ;
    private static boolean estUnEntier(String chaine) {
        try {
            Integer.parseInt(chaine);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void test1() {
        var dao = new CréditDao();
        var métier = new MétierCrédit();
       var controlleur = new CreditControlleur();

        métier.setCreditDao(dao);
        controlleur.setMétierCrédit(métier);
        String reponse = "";
        do {
            System.out.println("==> [Test 1] calcule de Mensualité de crédit <==\n ");
            try {
                String input = "";
                while (true) {
                    System.out.println("Entrer l'Id du crédit : ");
                    input = clavier.nextLine();
                    if (estUnEntier(input)) break;
                    System.err.println("Erreur : l'Id du crédit doit être un entier");
                }
                long id = Long.parseLong(input);
                controlleur.afficherMensualité(id);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
            System.out.println("Voulez vous quittez ? (oui/non) : ");
            reponse = clavier.nextLine();
        } while (!reponse.equalsIgnoreCase("oui"));

        System.out.println("Au revoir ^-^");

    }
    public  static void test2() throws Exception {
         /*IDao<Crédit,Long> dao = null;
         IMétierCrédit métier = null;
         ICréditControlleur créditControlleur = null;*/
        String daoClass;
        String serviceClass;
        String controlleurClass;

        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream config = classLoader.getResourceAsStream("ma/myBank/config.properties");

        if (config == null) {
            System.err.println("Erreur : fichier de configuration introuvable");
        } else {
            try {
                properties.load(config);
                daoClass = properties.getProperty("DAO");
                serviceClass = properties.getProperty("SERVICE");
                controlleurClass = properties.getProperty("CONTROLLER");
                config.close();
            } catch (IOException e) {
                throw new Exception("Erreur : impossible de charger le fichier de configuration");
            } finally {
                properties.clear();
            }

            try {
                Class cDao = Class.forName(daoClass);
                Class cMétier = Class.forName(serviceClass);
                Class cControlleur = Class.forName(controlleurClass);
                var dao = (IDao<Crédit, Long>) cDao.getDeclaredConstructor().newInstance();
                 var métier = (IMétierCrédit) cMétier.getDeclaredConstructor().newInstance();
                créditControlleur = (ICréditControlleur) cControlleur.getDeclaredConstructor().newInstance();

                // injection des dependances

                Method setDao = cMétier.getMethod("setCreditDao", IDao.class);
                setDao.invoke(métier, dao);
                Method setMétier = cControlleur.getMethod("setMétierCrédit", IMétierCrédit.class);
                setMétier.invoke(créditControlleur, métier);
                créditControlleur.afficherMensualité(3L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  static void test3() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("ma/myBank/spring-ioc.xml");
        créditControlleur = context.getBean(ICréditControlleur.class);
        créditControlleur.afficherMensualité(3L);
    }
    public  static void test4() throws Exception{
        ApplicationContext context = new AnnotationConfigApplicationContext("ma.myBank");
        créditControlleur = (ICréditControlleur) context.getBean(ICréditControlleur.class);
        créditControlleur.afficherMensualité(3L);
    }

        public static void main (String[] args) throws Exception {
            //test1();
            //test2();
           // test3();
            test4();
        }
    }
