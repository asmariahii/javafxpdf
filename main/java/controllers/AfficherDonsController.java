package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entities.Dons;
import service.DonsService;

import java.text.SimpleDateFormat;
import java.util.List;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;


public class AfficherDonsController {

    @FXML
    private TableView<Dons> donsTable;

    @FXML
    private TableColumn<Dons, String> idColumn;

    @FXML
    private TableColumn<Dons, String> userIdColumn;

    @FXML
    private TableColumn<Dons, String> nbPointsColumn;

    @FXML
    private TableColumn<Dons, String> dateAjoutColumn;

    private DonsService donsService;

    public AfficherDonsController() {
        donsService = new DonsService();
    }

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getIdDons())));
        userIdColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getIdUser())));
        nbPointsColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNbPoints())));
        dateAjoutColumn.setCellValueFactory(data -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new SimpleStringProperty(dateFormat.format(data.getValue().getDate_ajout()));
        });

        loadDons();
    }
    @FXML
    private void importerDons() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les dons");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx"));
        File file = fileChooser.showSaveDialog(donsTable.getScene().getWindow());

        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Dons");

                // Ajoutez les en-têtes de colonnes
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID Don");
                headerRow.createCell(1).setCellValue("ID Utilisateur");
                headerRow.createCell(2).setCellValue("Nombre de points");
                headerRow.createCell(3).setCellValue("Date d'Ajout");

                // Ajoutez les données des dons à partir de la table
                List<Dons> donsList = donsTable.getItems();
                int rowNum = 1;
                for (Dons don : donsList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(don.getIdDons());
                    row.createCell(1).setCellValue(don.getIdUser());
                    row.createCell(2).setCellValue(don.getNbPoints());
                    row.createCell(3).setCellValue(don.getDate_ajout().toString()); // Adapter la conversion de la date selon votre format
                }

                workbook.write(fos);
                workbook.close();

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Les dons ont été exportés avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'exportation des dons : " + e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadDons() {
        List<Dons> donsList = donsService.getAllDons();
        donsTable.getItems().addAll(donsList);
    }
}
