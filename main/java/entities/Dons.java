package entities;

import javafx.beans.property.*;

import java.util.Date;

public class Dons {
    private final IntegerProperty idDons;
    private final IntegerProperty idUser;
    private final IntegerProperty nbPoints;
    private final ObjectProperty<Date> date_ajout;

    public Dons() {
        this.idDons = new SimpleIntegerProperty();
        this.idUser = new SimpleIntegerProperty();
        this.nbPoints = new SimpleIntegerProperty();
        this.date_ajout = new SimpleObjectProperty<>();
    }

    public Dons(int idDons, int idUser, int nbPoints, Date date_ajout) {
        this.idDons = new SimpleIntegerProperty(idDons);
        this.idUser = new SimpleIntegerProperty(idUser);
        this.nbPoints = new SimpleIntegerProperty(nbPoints);
        this.date_ajout = new SimpleObjectProperty<>(date_ajout);
    }

    // Méthodes d'accès aux propriétés
    public IntegerProperty idDonsProperty() {
        return idDons;
    }

    public int getIdDons() {
        return idDons.get();
    }

    public void setIdDons(int idDons) {
        this.idDons.set(idDons);
    }

    public IntegerProperty idUserProperty() {
        return idUser;
    }

    public int getIdUser() {
        return idUser.get();
    }

    public void setIdUser(int idUser) {
        this.idUser.set(idUser);
    }

    public IntegerProperty nbPointsProperty() {
        return nbPoints;
    }

    public int getNbPoints() {
        return nbPoints.get();
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints.set(nbPoints);
    }

    public ObjectProperty<Date> date_ajoutProperty() {
        return date_ajout;
    }

    public Date getDate_ajout() {
        return date_ajout.get();
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout.set(date_ajout);
    }
}
