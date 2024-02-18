package service;

import entities.Dons;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;



public class DonsService implements IServiceDons {
    private Connection conn;
    private PreparedStatement pst;

    public DonsService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void addDons(User user, int donPoints) {
        try {
            // Obtenir la date actuelle
            Date dateAjout = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateAjoutString = sdf.format(dateAjout);

            // Ajouter le don avec la date actuelle dans la table Dons
            String insertQuery = "INSERT INTO Dons (idUser, nbpoints, date_ajout) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(insertQuery);
            pst.setInt(1, user.getId());
            pst.setInt(2, donPoints);
            pst.setString(3, dateAjoutString);
            pst.executeUpdate();

            System.out.println("Don ajouté avec succès.");

            // Soustraire les points du don de l'utilisateur
            UserService userService = new UserService();
            int remainingPoints = user.getNbPoints() - donPoints;
            userService.updateUserPoints(user.getId(), remainingPoints);

            // Afficher les points de l'utilisateur après l'ajout du don
            System.out.println("Points de l'utilisateur après l'ajout du don : " + remainingPoints);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du don: " + e.getMessage());
        } finally {
            closeStatement();
        }
    }




    @Override
    public boolean supprimerDon(int donsId, int nbPoints) {
        try {
            // Récupérer le nombre de points actuel du don
            int currentPoints = 0;
            String selectQuery = "SELECT nbpoints FROM Dons WHERE idDons = ?";
            try (PreparedStatement pstSelect = conn.prepareStatement(selectQuery)) {
                pstSelect.setInt(1, donsId);
                try (ResultSet rs = pstSelect.executeQuery()) {
                    if (rs.next()) {
                        currentPoints = rs.getInt("nbpoints");
                    } else {
                        System.out.println("Le don spécifié n'existe pas.");
                        return false; // Le don spécifié n'existe pas
                    }
                }
            }

            // Vérifier si le don a suffisamment de points à supprimer
            if (currentPoints >= nbPoints) {
                // Mettre à jour le nombre de points du don après la suppression
                int remainingPoints = currentPoints - nbPoints;
                String updateQuery = "UPDATE Dons SET nbpoints = ? WHERE idDons = ?";
                try (PreparedStatement pstUpdate = conn.prepareStatement(updateQuery)) {
                    pstUpdate.setInt(1, remainingPoints);
                    pstUpdate.setInt(2, donsId);
                    pstUpdate.executeUpdate();
                }
                System.out.println("Points supprimés avec succès du don.");
                return true; // Succès de la suppression des points du don
            } else {
                System.out.println("Le don spécifié ne contient pas suffisamment de points à supprimer.");
                return false; // Le don ne contient pas suffisamment de points à supprimer
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des points du don: " + e.getMessage());
            return false; // Échec de la suppression des points du don
        }
    }





    @Override
    public boolean donExists(int idDon) {
        String query = "SELECT COUNT(*) FROM Dons WHERE idDons = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, idDon);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du don : " + e.getMessage());
            return false;
        } finally {
            closeStatement();
        }
    }

    @Override
    public void updateDonsPoints(int donsId, int newPoints) {
        try {
            String query = "UPDATE Dons SET nbpoints = ? WHERE idDons = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, newPoints);
            pst.setInt(2, donsId);
            pst.executeUpdate();
            System.out.println("Points du don mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour des points du don : " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    private void closeStatement() {
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture du PreparedStatement: " + e.getMessage());
        }
    }
    public List<Dons> getAllDons() {
        List<Dons> donsList = new ArrayList<>();
        String query = "SELECT * FROM Dons";

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idDons");
                int userId = rs.getInt("idUser");
                int nbPoints = rs.getInt("nbpoints");
                Date dateAjout = rs.getTimestamp("date_ajout");

                Dons dons = new Dons(id, userId, nbPoints, dateAjout);
                donsList.add(dons);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des dons : " + e.getMessage());
        }

        return donsList;
    }
    public Dons getDonsById(int donsId) {
        String query = "SELECT * FROM Dons WHERE idDons = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, donsId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("idUser");
                int nbPoints = rs.getInt("nbpoints");
                Date dateAjout = rs.getTimestamp("date_ajout");
                return new Dons(donsId, userId, nbPoints, dateAjout);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du don par ID: " + e.getMessage());
        }
        return null;
    }


}
