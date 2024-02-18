package org.example;

import entities.User;
import service.UserService;
import service.DonsService;
import service.StatutDonsService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Créer une instance de UserService et DonsService
        UserService userService = new UserService();
        DonsService donsService = new DonsService();
        StatutDonsService statutDonsService = new StatutDonsService();

        // Récupérer l'utilisateur avec l'ID 3
        int userId = 6; // ID de l'utilisateur
        User user = userService.getUserById(userId);

        if (user != null) {
            // Récupérer le nombre de points de l'utilisateur avant d'ajouter le don
            int pointsBeforeDon = user.getNbPoints();

            // Ajouter un don de 60 points pour cet utilisateur
            int donPoints = 60; // Nombre de points du don
            donsService.addDons(user, donPoints);

            // Ne pas oublier d'afficher la date d'ajout du don
            System.out.println("Don de " + donPoints + " points ajouté avec succès pour l'utilisateur avec l'ID " + userId + ".");

            // Calculer la nouvelle valeur des points de l'utilisateur après l'ajout du don
            int newPoints = pointsBeforeDon - donPoints;

            // Afficher les points de l'utilisateur après l'ajout du don
            System.out.println("Points de l'utilisateur après l'ajout du don : " + newPoints);

            // Mettre à jour le statut du don
            int donId = 6; // ID du don
            String newStatut = "en cours"; // Nouveau statut du don
            statutDonsService.updateStatutDons(donId, newStatut);
        } else {
            System.out.println("Utilisateur avec l'ID " + userId + " non trouvé.");
        }
    }
}
