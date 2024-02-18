package service;
import entities.StatutDons;


import java.util.List;

public interface IServiceStatutDons {
    List<StatutDons> readStatutsDons();
    void addStatutDons(int idDon, String etatStatutDons);
    void deleteStatutDons(int id);
    void updateStatutDons(int id, String newEtatStatutDons);
}