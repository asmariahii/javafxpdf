package entities;

public class User {
    private int id;
    private int nbpoints;

    public User(int id, int nbpoints) {
        this.id = id;
        this.nbpoints = nbpoints;
    }

    public User(int nbpoints) {
        this.nbpoints = nbpoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPoints() {
        return nbpoints;
    }

    public void setNbPoints(int nbpoints) {
        this.nbpoints = nbpoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nbpoints=" + nbpoints +
                '}';
    }
}
