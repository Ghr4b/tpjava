import java.util.Scanner;
public class RobotLivraison extends RobotConnecte {
    public String colisActuel,destination;
    public boolean enlivraison;
    static final int ENERGIE_LIVRAISON=15;
    static final int ENERGIE_CHARGEMENT=5;
    public RobotLivraison(String id, int x, int y){
        super(x, y, id);
        colisActuel="0";
        enlivraison=false;
        destination=null;
    }
    @Override
    public boolean effectuerTache() throws RobotException{
        Scanner scan = new Scanner(System.in);
        verifierMaintenance();
        if(!enMarche){
            throw new RobotException("Le robot doit être démarré pour effectuer une tâche");
        }
        if(enlivraison){
            System.out.println("donner les coordonnées");
            int X=scan.nextInt();
            int Y=scan.nextInt();
            fairelivraison(X,Y);
        }
        else {
            System.out.println("voulez vous charger un nouveau colis");
            String reponse = scan.nextLine();
            switch (reponse.toLowerCase()){
                case "oui":
                    int energieNecessaire = this.ENERGIE_LIVRAISON + this.ENERGIE_CHARGEMENT;
                    verifierEnergie(energieNecessaire);
                    // a implementer
                    chargercolis("dest","colis");
                    break;
                case "non":
                    ajouterHistorique("En attente de colis");
                    break;
                default:
                    System.out.println("error");break;
            }
        }
        return false;
    }
    @Override
    public void deplacer(int X,int Y) throws RobotException{
        int x1= X-this.x;
        int y1= Y-this.y;
        double distance= Math.sqrt(Math.pow(x1, 2)+Math.pow(y1, 2));
        if(distance>100){
            throw new RobotException("distance trop long");
        }
        verifierMaintenance();
        double energieNecessaire  =  distance * 0.3;
        verifierEnergie(Math.round((float)energieNecessaire));
        this.heuresUtilisation+=(int)distance/10;

    }
    public void chargercolis(String destination, String colis) throws RobotException{
        if(enlivraison || this.colisActuel.equals("0")){
            throw new RobotException("Robot en livraison");
        }
        verifierEnergie(this.ENERGIE_CHARGEMENT);
        this.destination=destination;
        this.colisActuel=colis;
        consommerEnergie(this.ENERGIE_CHARGEMENT);
        ajouterHistorique("Colis "+ colis + " de destination "+destination+" chargée");
    }
    public void fairelivraison(int Destx,int Desty) throws RobotException{
        if(this.colisActuel.equals("0")){
            throw new RobotException("Pas de colis chargée");
        }
        this.enlivraison=true;
        deplacer(Destx,Desty);
        this.colisActuel="0";
        this.enlivraison=false;
        ajouterHistorique("Livraison terminée a"+destination);
        this.destination=null;
    }

}
