package packTest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import packWork.Admin;
import packWork.AgentDeTurism;
import packWork.Client;
import packWork.Locatie;
import packWork.Perioada;
import packWork.Permisiuni;
import packWork.Rezervare;
import packWork.ServerAgentie;
import packWork.ServerLogIn;
import packWork.TipPlata;
import packWork.TipTransport;
import packWork.Transport;

public class testFlow {
    private ServerAgentie serverAgentie;

    public testFlow() {
        serverAgentie = new ServerAgentie(new ServerLogIn());
    }

    private void pop() {
        ServerAgentie.addClient(new Client("1", "basilius.jakob@gmail.com", "basjak321#$", "Basilius", "Jakob", "90732423412", "M", "Estonia, Talinn", 32));
        ServerAgentie.addClient(new Client("2", "livia.rahela@gmail.com", "livrah123#$", "Rahela", "Livia", "40755744933", "F", "Romania, Bucuresti", 200));
        ServerAgentie.addClient(new Client("3", "vlad.sorin@gmail.com", "vladsor621#$", "Sorin", "Vlad", "40755744938", "M", "Romania, Cluj", 22));
        ServerAgentie.addAdmin(new Admin("4", "costel.rafael@gmail.com", "cosraf999@#$%", "Rafael", "Costel", "40755744930", "123456789541", Permisiuni.nivel2));
        ServerAgentie.addAdmin(new Admin("5", "albert.sorin@gmail.com", "albsor00%$##", "Albert", "Sorin", "40755744931", "123456789540", Permisiuni.nivel3));
        ServerAgentie.addAgentDeTurism(new AgentDeTurism("6", "marilena.claudia@gmail.com", "marcla678*&#", "Zamfir", "Marilena-Claudia", "40755744930", new String[] { "Romana", "Engleza", "Franceza" }, "Romania, Bucuresti, Str. Costi", "zamf.cl@gmail.com", "40766366766"));

        ServerAgentie.addUser("1", "basilius.jakob@gmail.com", "basjak321#$", "Basilius");
        ServerAgentie.addUser("2", "livia.rahela@gmail.com", "livrah123#$", "Rahela");
        ServerAgentie.addUser("3", "vlad.sorin@gmail.com", "vladsor621#$", "Sorin");
        ServerAgentie.addUser("4", "costel.rafael@gmail.com", "cosraf999@#$%", "A Rafael");
        ServerAgentie.addUser("5", "albert.sorin@gmail.com", "albsor00%$##", "A Albert");
        ServerAgentie.addUser("6", "marilena.claudia@gmail.com", "marcla678*&#", "A Zamfir");

        ServerAgentie.addRezervare(new Rezervare("1", "-", new Transport("1", "TransportRO", TipTransport.autocar), new Locatie("Franta, 75015 Paris, 12 Bis Rue Desaix", "Franta", "ile-de-France"), TipPlata.card, false, 15000, 15000, new Perioada(LocalDate.of(2023, 7, 13), LocalDate.of(2023, 7, 15))));
        ServerAgentie.addRezervare(new Rezervare("2", "-", new Transport("2", "RapidTrans", TipTransport.autocar), new Locatie("Romania, Maramures, 437035 Nanesti", "Romania", "Maramures"), TipPlata.card, false, 9999, 9999, new Perioada(LocalDate.of(2023, 6, 9), LocalDate.of(2023, 7, 13))));
        ServerAgentie.addRezervare(new Rezervare("3", "-", new Transport("5", "Flix", TipTransport.tren), new Locatie("China, 1000020 Beijing, No. 7 E. 3rd Ring Rd Middle", "China", "Chaoyang"), TipPlata.card, false, 15699, 15699, new Perioada(LocalDate.of(2023, 11, 22), LocalDate.of(2023, 7, 27))));

    }

    public String testare(int caz) throws InterruptedException {
        this.pop();
        if (caz == 1) {
            String inputMailFromClient = "basilius.jakob@gmail.com", inputPasswordFromClient = "basjak321#$", tempIdMail, tempIdPass, ofertaId = "1", adminId = "4";
            System.out.println("\n -> Primul caz de utilizare: Clientul introduce mail-ul si parola, ambele corecte din prima, cere o rezervare, insa nu mai este valabila.");
            tempIdMail = ServerAgentie.checkMail(inputMailFromClient);
            TimeUnit.MILLISECONDS.sleep(1500); // simlulam un delay pentru intelegere etape
            if (tempIdMail == "-1")
                return "mail incorect";
            tempIdPass = ServerAgentie.checkParola(inputPasswordFromClient);
            if (tempIdPass == "-1" || tempIdPass != tempIdMail) // parola incorecta sau parola este a altui client
                return "parola incorecta";
            System.out.println("\n -> Clientul s-a logat cu urmatoarele date: \"" + inputMailFromClient + "\", \"" + inputPasswordFromClient + "\" si urmeaza sa verifice catalogul cu oferte.");
            TimeUnit.MILLISECONDS.sleep(1500);
            serverAgentie.printRezervari(1);
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n -> Clientul este interesat de prima oferta");
            TimeUnit.MILLISECONDS.sleep(1500);
            
            serverAgentie.updateRezervare(adminId, ofertaId, "2", false);
            
            System.out.println("\n -> Oferta intre timp a expirat");
            TimeUnit.MILLISECONDS.sleep(1500);
            if (serverAgentie.checkValab(ofertaId, adminId) == 0) {
                serverAgentie.oferExp(adminId, "1", ofertaId);
                return "mail oferta expirata";

            } else if (serverAgentie.checkPlata(ofertaId, adminId) == 0) {
                serverAgentie.plataInv(adminId, "1", ofertaId);
                return "plata invalida";
            } else {
                serverAgentie.mailConf(adminId, "1", ofertaId);
                return "mail confirmare";
            }
        } else if (caz == 2) {
            String inputMailFromClient = "livia.rahela@gmail.com", inputPasswordFromClient[] = { "livi1234", "livrah123#$" }, tempIdMail, tempIdPass = "-1", ofertaId = "2", adminId = "5";
            int i;
            System.out.println("\n -> Al doilea caz de utilizare: Clientul introduce mail-ul si parola, email-ul este corect, dar parola o nimereste la a doua incercare, cere o rezervare, insa plata nu este valida.");
            tempIdMail = ServerAgentie.checkMail(inputMailFromClient);
            TimeUnit.MILLISECONDS.sleep(1500); // simlulam un delay pentru intelegere etape
            if (tempIdMail == "-1")
                return "mail incorect";
            for (i = 0; i < inputPasswordFromClient.length; i++) {
                tempIdPass = ServerAgentie.checkParola(inputPasswordFromClient[i]);
            }
            if (tempIdPass == "-1" || tempIdPass != tempIdMail) // parola incorecta sau 2 clienti cu aceleasi parole
                return "parola incorecta";
            System.out.println("\n -> Clientul s-a logat cu urmatoarele date: \"" + inputMailFromClient + "\", \"" + inputPasswordFromClient[inputPasswordFromClient.length - 1] + "\" si urmeaza sa verifice catalogul cu oferte.");
            TimeUnit.MILLISECONDS.sleep(1500);
            serverAgentie.printRezervari(1);
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n -> Clientul este interesat de a doua oferta");
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n -> Plata clientului nu a fost acceptata si deci nu se gaseste in sistem");
            TimeUnit.MILLISECONDS.sleep(1500);
            if (serverAgentie.checkValab(ofertaId, adminId) == 0) {
                serverAgentie.oferExp(adminId, "2", ofertaId);
                return "mail oferta expirata";
            } else if (serverAgentie.checkPlata(ofertaId, adminId) == 0) {
                serverAgentie.plataInv(adminId, "2", ofertaId);
                return "plata invalida";
            } else {
                serverAgentie.mailConf(adminId, "2", ofertaId);
                return "mail confirmare";
            }
        } else if (caz == 3) {
            String inputMailFromClient = "vlad.sorin@gmail.com", inputPasswordFromClient = "vladsor621#$", tempIdMail, tempIdPass, ofertaId = "3", adminId = "4";
            System.out.println("\n -> Al treilea caz de utilizare: Clientul introduce corect mail-ul si parola, cere o rezervare, plata este valalida si primeste un mail pentru confirmare.");
            tempIdMail = ServerAgentie.checkMail(inputMailFromClient);
            TimeUnit.MILLISECONDS.sleep(1500); // simlulam un delay pentru intelegere etape
            if (tempIdMail == "-1")
                return "mail incorect";
            tempIdPass = ServerAgentie.checkParola(inputPasswordFromClient);
            if (tempIdPass == "-1" || tempIdPass != tempIdMail) // parola incorecta sau parola este a altui client
                return "parola incorecta";
            System.out.println("\n -> Clientul s-a logat cu urmatoarele date: \"" + inputMailFromClient + "\", \"" + inputPasswordFromClient + "\" si urmeaza sa verifice catalogul cu oferte.");
            TimeUnit.MILLISECONDS.sleep(1500);
            serverAgentie.printRezervari(1);
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n -> Clientul este interesat de a treia oferta");
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n -> Plata clientului a fost acceptata si deci se gaseste in sistem");
            serverAgentie.efectuarePlata("3", "3");
            TimeUnit.MILLISECONDS.sleep(1500);
            if (serverAgentie.checkValab(ofertaId, adminId) == 0) {
                serverAgentie.oferExp(adminId, "3", ofertaId);
                return "mail oferta expirata";
            } else if (serverAgentie.checkPlata(ofertaId, adminId) == 0) {
                serverAgentie.plataInv(adminId, "3", ofertaId);
                return "plata invalida";
            } else {
                serverAgentie.updateRezervare(adminId, ofertaId, "3", false);
                serverAgentie.mailConf(adminId, "3", ofertaId);
                return "mail confirmare";
            }
        }

        return "ipostaza neasteptata";
    }

    public static void main(String[] args) throws InterruptedException{
        testFlow testFlow = new testFlow();
        System.out.println(testFlow.testare(1));
        //System.out.println(testFlow.testare(2));      //testare caz 2
        //System.out.println(testFlow.testare(3));      //testare caz 3
    }
}

