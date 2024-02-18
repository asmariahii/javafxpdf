package service;

import entities.Dons;
import entities.User;
import service.DonsService;

import java.util.Date;
import java.util.List;

public interface IServiceDons {
    void addDons(User user, int donPoints);
    void updateDonsPoints(int donsId, int newPoints);


    boolean supprimerDon(int donsId, int nbPoints);

    boolean donExists(int idDon);

    List<Dons> getAllDons();
}
