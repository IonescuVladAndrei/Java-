package testare;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import login.ServerLogin;

class ServerLogInTest {
	@Test
	void testLogIn() {
		ServerLogin serverConturi = new ServerLogin();
        serverConturi.addUser("1", "velma.agata@gmail.com", "A123456$$", "Velma");
        serverConturi.addUser("2", "deidre.isaura@gmail.com", "B123456$$", "Isaura23");
        serverConturi.addUser("3", "arnstein.byron@gmail.com", "C123456$$", "Byron");
        serverConturi.addUser("4", "jo.vibius@gmail.com", "D123456$$", "Vibius");
        serverConturi.addUser("5", "madai.sioned@gmail.com", "E123456$$", "Sioned");
        
        //caz 1
        //se introduce numele si parola, iar acestea sunt corecte
        String[] setParole1 = {"A123456$$"};
        String[] setNume1 = {"Velma"};
        String[] temp = {"-1"};
        assertTrue("Acces: o incercare" == serverConturi.logIn(setNume1, setParole1, false, temp));
        
        //caz 2
        //se introduc numele si parola de mai multe ori pana cand aceastea sunt acceptate
        String[] setParole2 = {"password1", "password2", "password3", "B123456$$"};
        String[] setNume2 = {"Isaura", "Isaura23", "Isaura23", "Isaura23"};
        assertTrue("Acces: incarcari multiple fara resetare parola" == serverConturi.logIn(setNume2, setParole2,false, temp));
	
        //caz 3
        // se introduc combinatii de mail si parola incorecte, apoi se reseteaza parola
        // prima parola introdusa respecta nivelul de securitate
        String[] setParole3 = {"C123458$$", "C123458$$", "C123458$$", "B123456$$"};
        String[] setNume3 = {"Byron", "Byron", "Byron", "Byron"};
        String[] parolaNoua1 = {"Z123456A$%"};
        assertTrue("Acces: incarcari multiple si o resetare cu succes" == serverConturi.logIn(setNume3, setParole3,true, parolaNoua1));
        
        //caz 4
        // se introduc combinatii de mail si parola incorecte, apoi se reseteaza parola
        // se introduc mai multe parole pana cand se respecta nivelul de securitate
        String[] setParole4 = {"LC123458$$", "LC123458$$", "LC123458$$", "LB123456$$"};
        String[] setNume4 = {"Vibius", "Vibius", "Vibius", "Vibius"};
        String[] parolaNoua2 = {"123456789", "@#$%^&*()0", "123456A$%"};
        assertTrue("Acces: incarcari multiple si resetari multiple, ultima fiind cu succes" == serverConturi.logIn(setNume4, setParole4,true, parolaNoua2));
	}
	
	//aceleasi cazuri ca mai sus, doar ca apelam pentru fiecare caz cate o metoda
	
	@Test
	void testIncercSimpl() {
		ServerLogin serverConturi = new ServerLogin();
		assertTrue(serverConturi.incercSimpl(1));
		
		ServerLogin serverConturi_2 = new ServerLogin();
		assertTrue(!serverConturi_2.incercSimpl(2));
	}

	@Test
	void testIncercMultip() {
		ServerLogin serverConturi = new ServerLogin();
		assertTrue(serverConturi.incercMultip(1));
		
		ServerLogin serverConturi_2 = new ServerLogin();
		assertTrue(!serverConturi_2.incercMultip(2));
	}

	@Test
	void testIncercMultip_incercSimpl() {
		ServerLogin serverConturi = new ServerLogin();
		assertTrue(serverConturi.incercMultip_incercSimpl(1));
		
		ServerLogin serverConturi_2 = new ServerLogin();
		assertTrue(!serverConturi_2.incercMultip_incercSimpl(2));
	}

	@Test
	void testIncercMultip_incercMultip() {
		ServerLogin serverConturi = new ServerLogin();
		assertTrue(serverConturi.incercMultip_incercMultip(1));
		
		ServerLogin serverConturi_2 = new ServerLogin();
		assertTrue(!serverConturi_2.incercMultip_incercMultip(2));
	}
}
