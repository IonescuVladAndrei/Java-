package packWork;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.Set;

public class ServerLogIn {
    protected static final HashMap<String, String> mailMap = new HashMap<String, String>();
    protected static final HashMap<String, String> parolaMap = new HashMap<String, String>();
    protected static final HashMap<String, String> numeUtilizMap = new HashMap<String, String>();

    public ServerLogIn() {
    }

    public void addUser(String idPers, String mail, String parola, String numeUtiliz) {
        mailMap.put(idPers, mail);
        parolaMap.put(idPers, parola);
        numeUtilizMap.put(idPers, numeUtiliz);
    }

    public String logIn(String[] numeUtiliz, String[] parola, boolean resetareParola, String[] parolaNoua) {
        String raspuns = "Acces: o incercare", checkNume, checkParola;
        System.out.println("Introduceti numele de utilizator si parola");

        int i;
        for (i = 0; i < numeUtiliz.length; i++) {
            if (i == 1)
                raspuns = "Acces: incarcari multiple fara resetare parola";
            checkNume = this.checkNumeUtiliz(numeUtiliz[i]);
            System.out.println("\tAti introdus in campul nume utilizator: " + numeUtiliz[i]);
            checkParola = this.checkParola(parola[i]);
            System.out.println("\tAti introdus in campul parola: " + parola[i]);
            if (checkNume == checkParola && checkParola != "-1") {
                System.out.println("User-ul si parola sunt corecte. Bun venit(access granted).\n");
                i = numeUtiliz.length;
            } else {
                System.out.println("User-ul si/sau parola sunt incorecte. Reintroduceti datele.\n");
                if (i == numeUtiliz.length - 1 && resetareParola) {
                    System.out.println("A fost selectata optiunea de resetare a parolei");
                    System.out.println("Email-ul cu link-ul pentru resetare parola va fi trimis la adresa de mail: " + this.getMail(checkNume));
                    for (int j = 0; j < parolaNoua.length; j++) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(1300); // simluare delay de la conexiunea cu serverul
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                        System.out.println("\tParola noua introdusa este: " + parolaNoua[j]);
                        if (this.checkParolaValida(parolaNoua[j])) {
                            System.out.println("\nParola acceptata.\n");
                            this.setParola(checkNume, parolaNoua[j]);
                            if (i == 0 && j == 0)
                                raspuns = "Acces: o incarcare si o resetare cu succes";
                            else if (i == 0 && j > 0)
                                raspuns = "Acces:  o incarcare si resetari multiple, ultima fiind cu succes";
                            else if (i > 0 && j == 0)
                                raspuns = "Acces: incarcari multiple si o resetare cu succes";
                            else
                                raspuns = "Acces: incarcari multiple si resetari multiple, ultima fiind cu succes";
                        } else
                            System.out.println("\nParola nu este suficient de sigura (cel putin doua numere, cel putin 2 caractere speciale (@, #, $, %, ^, &, *, (, ), ?, !)\nsi o lungime de cel putin 9 caractere)!");
                    }
                }
                if (i == numeUtiliz.length - 1 && this.checkNumeUtiliz(numeUtiliz[i]) == "-1")
                    return "Invalid";
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1300); // simluare delay de la conexiunea cu serverul
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return raspuns;
    }

    public String checkMail(String mail) {
        String idFound = "-1";
        for (String key : mailMap.keySet()) {
            if (mailMap.get(key) == mail)
                idFound = key;
        }
        return idFound;
    }

    public String checkParola(String parola) {
        String idFound = "-1";
        for (String key : parolaMap.keySet()) {
            if (parolaMap.get(key) == parola)
                idFound = key;
        }
        return idFound;
    }

    public String checkNumeUtiliz(String numeUtiliz) {
        String idFound = "-1";
        for (String key : numeUtilizMap.keySet()) {
            if (numeUtilizMap.get(key) == numeUtiliz)
                idFound = key;
        }
        return idFound;
    }

    // conditie verificare nivel de securitate parola
    // parola noua trebuie sa contina minim 9 caractere, dintre care minim 2 digits si minim 2 caractere speciale, si cel putin un caracter din a...z sau A...Z
    public boolean checkParolaValida(String parolaNoua) {
        char[] specialChar = { '@', '#', '$', '%', '^', '&', '*', '(', ')', '?', '!' };
        int i, j, nrOfSpecialChar = 0, nrOfDigits = 0, nrOfAllowedChar = 0;
        if (parolaNoua.length() < 9) // nu se respecta numarul minim de caractere impuse
            return false;
        else {
            for (i = 0; i < parolaNoua.length(); i++) {
                if (parolaNoua.charAt(i) > 47 && parolaNoua.charAt(i) < 58)
                    nrOfDigits++;
                else if (parolaNoua.charAt(i) > 64 && parolaNoua.charAt(i) < 91 || parolaNoua.charAt(i) > 96 && parolaNoua.charAt(i) < 123)
                    nrOfAllowedChar++;
                else {
                    for (j = 0; j < 11; j++)
                        if (parolaNoua.charAt(i) == specialChar[j])
                            nrOfSpecialChar++;
                }

            }
        }
        if (nrOfDigits + nrOfAllowedChar + nrOfSpecialChar != parolaNoua.length()) // avem cel putin un caracter precum: ș, ț, ă, î, etc
            return false;
        if (nrOfSpecialChar > 1 && nrOfDigits > 1 && nrOfAllowedChar > 0) // conditiile impuse sunt respectate
            return true;
        return false; // nu se respecta numarul minim de caractere impuse din una sau mai multe conditii 
    }

    public String getMail(String idPers) {
        return ServerLogIn.mailMap.get(idPers);
    }

    public String getParola(String idPers) {
        return ServerLogIn.parolaMap.get(idPers);
    }

    public void setParola(String idPers, String parola) {
        parolaMap.put(idPers, parola);
    }

    public void afisare() {
        Set<String> keys = mailMap.keySet();
        System.out.println("\n-----Server-----");
        for (String key : keys) {
            System.out.println("\n\n\tmail: " + mailMap.get(key) + "\n\tnumeUtilizator: " + numeUtilizMap.get(key) + "\n\tparola: " + parolaMap.get(key));
        }
    }
}
