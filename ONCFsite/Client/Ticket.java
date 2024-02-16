package ONCFsite.Client;

public class Ticket {

    private String numeroTicket;
    private String nomTrain;
    private String dateDepart;
    private String dateArrivee;
    private String gareDepart; // Nouvelle propriété pour la gare de départ
    private String gareArrivee; // Nouvelle propriété pour la gare d'arrivée
    private String classe;
    private double prix;
    private double reduction; // Nouvelle propriété pour la réduction

    public Ticket(String numeroTicket, String nomTrain, String dateDepart, String dateArrivee,
                  String gareDepart, String gareArrivee, String classe, double prix, double reduction) {
        this.numeroTicket = numeroTicket;
        this.nomTrain = nomTrain;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;
        this.classe = classe;
        this.prix = prix;
        this.reduction = reduction;
    }

    // Méthodes getters (comme avant)
    public String getNumeroTicket() {
        return numeroTicket;
    }

    public String getNomTrain() {
        return nomTrain;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public String getGareDepart() {
        return gareDepart;
    }

    public String getGareArrivee() {
        return gareArrivee;
    }

    public String getClasse() {
        return classe;
    }

    public double getPrix() {
        return prix;
    }

    public double getReduction() {
        return reduction;
    }

    // Méthode pour calculer le prix après réduction
    public double calculerPrixApresReduction() {
        return prix * (1 - reduction);
    }
}

