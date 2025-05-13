import java.util.Vector;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public abstract class Robot {
    public String id;
    public int x,y,energie,heuresUtilisation;
    public boolean enMarche;
    private Vector<String> historiqueActions;
    public Robot(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
        //100% d'énergie et 0 heure d'utilisation lorsque le robot est crée
        this.energie = 100;
        this.heuresUtilisation = 0;
        historiqueActions = new Vector<>();
        ajouterHistorique("Robot crée");

    }
    public void ajouterHistorique(String action){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss").withLocale(Locale.FRENCH);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String date = currentDateTime.format(formatter);
        historiqueActions.add(date+" "+action);
    }
    public void verifierEnergie(int energieRequise) throws EnergieInsuffisanteException {
        if (energieRequise > energie){
            throw new EnergieInsuffisanteException();
        }
    }
    public void verifierMaintenance() throws MaintenanceRequiseException{
        if (heuresUtilisation > 100){
            throw new MaintenanceRequiseException();
        }
    }
    public void demarrer()throws RobotException{
        try {
            if (enMarche){
                throw new RobotException("Robot déjà démarré");
            }
            verifierEnergie(10);
            this.enMarche = true;
            ajouterHistorique("robot demarré");
        }
        catch (EnergieInsuffisanteException e) {
            ajouterHistorique("échec de demarrage");
            throw new RobotException("Batterie faible, veuillez recharger.");
        }
    }
    public void arreter() throws RobotException{
        if (!enMarche){
            throw new RobotException("Robot déjà arrêté");
        }
        this.enMarche = false;
        ajouterHistorique("robot arreté");

    }
    public void consommerEnergie(int quantite) throws EnergieInsuffisanteException {
        verifierEnergie(quantite);
        this.energie-=quantite;
    }
    public void recharger(int quantite) {
        this.energie+=quantite;
        if (energie > 100){
            energie = 100;
        }
    }
    public abstract void deplacer(int x, int y) throws RobotException;
    public abstract boolean effectuerTache() throws RobotException;
    public String getHistorique() {
        String historique = String.join(", ", historiqueActions);
        return historique;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ID : "+id+", Position : ("+x+","+y+"), Energie : "+energie+"%, Heures : "+heuresUtilisation+"]";
    }


}

