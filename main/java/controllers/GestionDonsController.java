package controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import entities.User;
import service.DonsService;
import service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.DonsService;

import java.io.IOException;
import java.util.List;

public class GestionDonsController {

    private Stage primaryStage;
    private DonsService donsService; // Déclaration de la variable DonsService

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Méthode pour injecter le service de dons
    public void setDonsService(DonsService donsService) {
        this.donsService = donsService;
    }

    @FXML
    private void handleAjouterDon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDons.fxml"));
            Parent root = loader.load();
            AjouterDonsController ajouterDonsController = loader.getController();
            ajouterDonsController.initialize(); // Vous pouvez initialiser le contrôleur ici si nécessaire
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la vue pour ajouter un don.");
        }
    }

    @FXML
    private void handleAfficherDons() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDons.fxml"));
            Parent root = loader.load();
            AfficherDonsController afficherDonsController = loader.getController();
            afficherDonsController.initialize(); // Vous pouvez initialiser le contrôleur ici si nécessaire
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Afficher Dons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la vue pour afficher les dons.");
        }
    }
    @FXML
    private void handleSupprimerDon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerDons.fxml"));
            Parent root = loader.load();
            SupprimerDonsController supprimerDonsController = loader.getController();
            supprimerDonsController.initialize();// Injecter le service de dons dans le contrôleur
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Supprimer Don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la vue pour supprimer un don.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
