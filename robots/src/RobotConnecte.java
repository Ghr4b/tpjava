public abstract class RobotConnecte extends Robot implements Connectable {
    public boolean connecte;
    public String reseauConnecte;
    public RobotConnecte(int x, int y, String id){
        super(x, y, id);
        connecte = false;
        reseauConnecte = null;
    }
    public void connecter(String reseau) throws RobotException{
        verifierEnergie(5);
        verifierMaintenance();
        this.reseauConnecte = reseau;
        this.connecte = true;
        consommerEnergie(5);
        ajouterHistorique("connecté au reseau "+reseau);
    }
    public void deconnecter(){
        this.reseauConnecte = null;
        this.connecte = false;
        ajouterHistorique("deconnecté");
    }
    public void envoyerDonnees(String donnees) throws RobotException{
        if(!connecte){
            throw new RobotException("robot pas connectée");
        }
        verifierMaintenance();
        verifierEnergie(3);
        consommerEnergie(3);
        ajouterHistorique("donnée envoyé: "+donnees);

    }
}
