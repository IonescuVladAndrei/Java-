package testare;

import login.ServerLogin; 

public class ExDiagDeActiv2 {
    static public void main(String[] args) {
        ServerLogin serverConturi = new ServerLogin();
        serverConturi.addUser("1", "velma.agata@gmail.com", "A123456$$", "Velma");
        serverConturi.addUser("2", "deidre.isaura@gmail.com", "B123456$$", "Isaura23");
        serverConturi.addUser("3", "arnstein.byron@gmail.com", "C123456$$", "Byron");
        serverConturi.addUser("4", "jo.vibius@gmail.com", "D123456$$", "Vibius");
        serverConturi.addUser("5", "madai.sioned@gmail.com", "E123456$$", "Sioned");

        String raspuns;
        
        //caz 1
        //se introduce numele si parola, iar acestea sunt corecte
        String[] setParole1 = {"A123456$$"};
        String[] setNume1 = {"Velma"};
        String[] temp = {"-1"};
        raspuns = serverConturi.logIn(setNume1, setParole1, false, temp);
        System.out.println("---Exemplul de mai sus a fost pentru: " + raspuns + "---\n");
        
        //caz 2
        //se introduc numele si parola de mai multe ori pana cand aceastea sunt acceptate
        String[] setParole2 = {"password1", "password2", "password3", "B123456$$"};
        String[] setNume2 = {"Isaura", "Isaura23", "Isaura23", "Isaura23"};
        raspuns = serverConturi.logIn(setNume2, setParole2,false, temp);
        System.out.println("---Exemplul de mai sus a fost pentru: " + raspuns + "---\n");

        //caz 3 
        // se introduc combinatii de mail si parola incorecte, apoi se reseteaza parola
        // prima parola introdusa respecta nivelul de securitate
        String[] setParole3 = {"C123458$$", "C123458$$", "C123458$$", "B123456$$"};
        String[] setNume3 = {"Byron", "Byron", "Byron", "Byron"};
        String[] parolaNoua1 = {"Z123456A$%"};
        raspuns = serverConturi.logIn(setNume3, setParole3,true, parolaNoua1);
        System.out.println("---Exemplul de mai sus a fost pentru: " + raspuns + "---\n");

         //caz 4
        // se introduc combinatii de mail si parola incorecte, apoi se reseteaza parola
        // se introduc mai multe parole pana cand se respecta nivelul de securitate
        String[] setParole4 = {"LC123458$$", "LC123458$$", "LC123458$$", "LB123456$$"};
        String[] setNume4 = {"Vibius", "Vibius", "Vibius", "Vibius"};
        String[] parolaNoua2 = {"123456789", "@#$%^&*()0", "123456A$%"};
        raspuns = serverConturi.logIn(setNume4, setParole4,true, parolaNoua2);
        System.out.println("---Exemplul de mai sus a fost pentru: " + raspuns + "---\n");

        serverConturi.afisare();
        //se observa faptul ca pentru cazul 3 si cazul 4, parolele noi sunt memorate in hashmap, nu doar acceptate
    }

}
