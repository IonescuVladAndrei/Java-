package Tema2;

import java.util.HashMap;
import java.util.Set;
import java.util.Objects;

public class ServerAgentie {
    private static ServerLogIn serverLoginMap;
    private static final HashMap<String, Client> clientMap = new HashMap<String, Client>();
    private static final HashMap<String, Admin> adminMap = new HashMap<String, Admin>();
    private static final HashMap<String, AgentDeTurism> agentDeTurismMap = new HashMap<String, AgentDeTurism>();
    private static final HashMap<String, Rezervare> rezervareMap = new HashMap<String, Rezervare>();
    private static final HashMap<String, Plata> plataMap = new HashMap<String, Plata>();

    public ServerAgentie(ServerLogIn serverLoginMap) {
        ServerAgentie.serverLoginMap = serverLoginMap;
    }

    public static void addPlataEfectuata(Plata plata) {
        ServerAgentie.plataMap.put(plata.getIdPlata(), plata);
    }

    public static void addClient(Client client) {
        ServerAgentie.clientMap.put(client.getID(), client);
    }

    public static void addUser(String idPers, String mail, String parola, String numeUtiliz) {
        ServerAgentie.serverLoginMap.addUser(idPers, mail, parola, numeUtiliz);
    }

    public static String checkMail(String mail) {
        return ServerAgentie.serverLoginMap.checkMail(mail);
    }

    public static String checkParola(String parola) {
        return ServerAgentie.serverLoginMap.checkParola(parola);
    }

    public static void addAdmin(Admin admin) {
        ServerAgentie.adminMap.put(admin.getID(), admin);
    }

    public static void addAgentDeTurism(AgentDeTurism agentieDeTurism) {
        ServerAgentie.agentDeTurismMap.put(agentieDeTurism.getID(), agentieDeTurism);
    }

    public static void addRezervare(Rezervare rezervare) {
        ServerAgentie.rezervareMap.put(rezervare.getIdRezerv(), rezervare);
    }
    
    public void deleteOfer(String adminId, String rezervareId) {
        Admin admin = ServerAgentie.adminMap.get(adminId);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(rezervareId);
        if (Objects.isNull(admin) || Objects.isNull(rezervare))
            System.out.println("Invalid action");
        else
            rezervareMap.remove(rezervareId);
    }
    
    public void oferExp(String adminId, String clientId, String rezervareID) {
        Admin admin = ServerAgentie.adminMap.get(adminId);
        Client client = ServerAgentie.clientMap.get(clientId);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(rezervareID);
        if (Objects.isNull(admin) || Objects.isNull(client) || Objects.isNull(rezervare))
            System.out.println("Invalid action");
        else
            System.out.println("\n++Mail oferta expirata trimis++\n\nBuna ziua " + client.getNume() + ",\n\nDin pacate, oferta selectata ID: " + rezervare.getIdRezerv() + ", locatie: " + rezervare.getLocatieAbsol() + "\nin perioada: " + rezervare.getDataIncep() + " - " + rezervare.getDataSfar() + " nu mai este valabila.\nNe pare rau pentru situatia neplacuta creata. Pentru oferte similare, vizitati sectiunea zamf.ro/" + rezervare.getTara() + "-" + rezervare.getJudet() +
                    "/\n\nToate cele bune,\n" + admin.getNume() + " " + admin.getPrenume() + "\n++++\n");

    }

    public void plataInv(String adminId, String clientId, String rezervareID) {
        Admin admin = ServerAgentie.adminMap.get(adminId);
        Client client = ServerAgentie.clientMap.get(clientId);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(rezervareID);
        AgentDeTurism agentDeTurism = ServerAgentie.agentDeTurismMap.get("6");
        if (Objects.isNull(admin) || Objects.isNull(client) || Objects.isNull(rezervare) || Objects.isNull(agentDeTurism))
            System.out.println("Invalid action");
        else {
            System.out.println("\n++Mail plata invalida trimis++\n\nBuna ziua " + client.getNume() + ",\n\nDin pacate, plata pentru oferta selectata ID: " + rezervare.getIdRezerv() + ", locatie: " + rezervare.getLocatieAbsol() + "\nin perioada: " + rezervare.getDataIncep() + " - " + rezervare.getDataSfar() + " nu este valida.\nPentru detalii, puteti telefona la relatii clienti: +" + agentDeTurism.getNrTelPublic());
            if (client.getLocatie().contains("Romania") && !agentDeTurism.isForeign())
                System.out.print("(tarif normal)");
            else
                System.out.print(" (tarif roaming)");
            System.out.print(" sau trimite email la adresa: " + agentDeTurism.getMailPublic() + "\n\nToate cele bune,\n" + admin.getNume() + " " + admin.getPrenume() + "\n++++\n");

        }

    }

    public void mailConf(String adminId, String clientId, String rezervareID) {
        Admin admin = ServerAgentie.adminMap.get(adminId);
        Client client = ServerAgentie.clientMap.get(clientId);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(rezervareID);
        if (Objects.isNull(admin) || Objects.isNull(client) || Objects.isNull(rezervare))
            System.out.println("Invalid action");
        else
            System.out.println("\n++Mail rezervare confirmata trimis++\n\nBuna ziua " + client.getNume() + ",\n\nRezervarea cu umratoarele date ID: " + rezervare.getIdRezerv() + ", locatie: " + rezervare.getLocatieAbsol() + "\nin perioada: " + rezervare.getDataIncep() + " - " + rezervare.getDataSfar() + " (" + rezervare.getNrNopti() + "nopti) a fost confirmata.\nZamf va ureaza o vacanta placuta!");
        if (rezervare.isPassportNeededEU(client.getLocatie()))
            System.out.print("Nu va uitati pasaportul!");
        System.out.print("\n\nToate cele bune,\n" + admin.getNume() + " " + admin.getPrenume() + "\n++++\n");
    }

    public void printRezervari(int tip) {
        Set<String> keys = ServerAgentie.rezervareMap.keySet();
        System.out.println("\n---Catalog rezervari valabile---");
        for (String key : keys)
            if (tip == 1) {
                if (rezervareMap.get(key).getValabilitate()) { // doar ofertele valabile
                    System.out.println("\n  ++Oferta " + ServerAgentie.rezervareMap.get(key).getIdRezerv() + "++");
                    ServerAgentie.rezervareMap.get(key).afisare(2);
                }
            } else {
                System.out.println("\t--Oferta " + key + "--");
                ServerAgentie.rezervareMap.get(key).afisare(2);
            }
    }

    public int checkValab(String ofertaKey, String adminId) {
        if (Objects.isNull(ServerAgentie.adminMap.get(adminId)) || Objects.isNull(ServerAgentie.rezervareMap.get(ofertaKey))) { // numai un admin are acces la aceasta informatie sau oferta nu mai este in sistem
            System.out.println("Invalid action");
            return -1;
        } else {
            if (ServerAgentie.rezervareMap.get(ofertaKey).getValabilitate())
                return 1;
            else
                return 0;
        }
    }

    public int checkPlata(String ofertaKey, String adminId) {
        if (Objects.isNull(ServerAgentie.adminMap.get(adminId)) || Objects.isNull(ServerAgentie.rezervareMap.get(ofertaKey))) {
            System.out.println("Invalid action");
            return -1;
        } else {
            Set<String> keys = ServerAgentie.rezervareMap.keySet();
            for (String key : keys) {
                if (!Objects.isNull((ServerAgentie.plataMap.get(key))))
                    if (ServerAgentie.plataMap.get(key).getIdRezervare() == ofertaKey)
                        return 1;
            }
            return 0;
        }
    }

    public void efectuarePlata(String clientId, String rezervareID) {
        Client client = ServerAgentie.clientMap.get(clientId);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(rezervareID);
        int pctReducRamas = 0;
        if (Objects.isNull(client) || Objects.isNull(rezervare))
            System.out.println("Invalid action");
        else {
            pctReducRamas = rezervare.setPretCuReducere(client.getNrPctRed());
            ServerAgentie.addPlataEfectuata(new Plata(rezervareID, rezervareID, rezervare.getTipPlata(), rezervare.getPretCuReducere()));
            client.setNrPctRed(pctReducRamas);
        }
    }

    public void updateRezervare(String adminId, String ID_rez, String ID_pers, boolean valabilitate) {
        Client client = ServerAgentie.clientMap.get(ID_pers);
        Rezervare rezervare = ServerAgentie.rezervareMap.get(ID_rez);
        if (Objects.isNull(rezervare) || Objects.isNull(ServerAgentie.adminMap.get(adminId)) || Objects.isNull(client))
            System.out.println("Invalid action");
        else {
            rezervare.setValabilitate(valabilitate);
            rezervare.setClientId(ID_pers);
        }
    }

}
