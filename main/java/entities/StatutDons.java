package entities;

import java.util.Date;

public class StatutDons {
    private int id;
    private Date dateChangement;
    private String etatStatutDons;

    public StatutDons(int id, Date dateChangement, String etatStatutDons) {
        this.id = id;

        this.dateChangement = dateChangement;
        this.etatStatutDons = etatStatutDons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Date getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(Date dateChangement) {
        this.dateChangement = dateChangement;
    }

    public String getEtatStatutDons() {
        return etatStatutDons;
    }

    public void setEtatStatutDons(String etatStatutDons) {
        this.etatStatutDons = etatStatutDons;
    }

    @Override
    public String toString() {
        return "StatutDons{" +
                "id=" + id +

                ", dateChangement=" + dateChangement +
                ", etatStatutDons='" + etatStatutDons + '\'' +
                '}';
    }
}
