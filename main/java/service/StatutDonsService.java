package service;

import entities.StatutDons;
import utils.DataSource;
import entities.Dons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatutDonsService implements IServiceStatutDons {
    private Connection conn;
    private PreparedStatement pst;

    public StatutDonsService() {
        conn = DataSource.getInstance().getCnx();
    }

    public List<StatutDons> readStatutsDons() {
        List<StatutDons> list = new ArrayList<>();
        String query = "SELECT statutDons.idStatutDons, statutDons.idDons, statutDons.etatStatutDons, statutDons.dateChangement " +
                "FROM statutDons " +
                "JOIN dons ON statutDons.idDons = dons.idDons";

        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idStatutDons");
                int idDon = rs.getInt("idDons");
                String etatStatutDons = rs.getString("etatStatutDons");
                java.util.Date dateChangement = rs.getDate("dateChangement");

                StatutDons statutDons = new StatutDons(id, dateChangement, etatStatutDons);
                list.add(statutDons);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture des statuts de don: " + e.getMessage());
        } finally {
            closeStatement();
        }

        return list;
    }


    public void addStatutDons(int idDon, String etatStatutDons) {
        // Vérifier si l'ID du don existe
        DonsService donsService = new DonsService();
        if (!donsService.donExists(idDon)) {
            System.out.println("Erreur : Le don avec l'ID " + idDon + " n'existe pas.");
            return;
        }

        String query = "INSERT INTO statutDons (idDons, dateChangement, etatStatutDons) VALUES (?, NOW(), ?)";

        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, idDon);
            pst.setString(2, etatStatutDons);

            pst.executeUpdate();

            System.out.println("Statut de don ajouté avec succès pour le don avec l'ID : " + idDon);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du statut de don: " + e.getMessage());
        } finally {
            closeStatement();
        }
    }


    public void deleteStatutDons(int id) {
        String query = "DELETE FROM statutDons WHERE idStatutDons = ?";

        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);

            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Statut de don supprimé avec succès.");
            } else {
                System.out.println("Aucun statut de don n'a été supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du statut de don: " + e.getMessage());
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
    public void updateStatutDons(int id, String newEtatStatutDons) {
        // Vérifier si le statut de don avec l'ID donné existe
        String checkQuery = "SELECT * FROM statutDons WHERE idStatutDons = ?";
        try {
            pst = conn.prepareStatement(checkQuery);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                System.out.println("Erreur : Aucun statut de don trouvé avec l'ID " + id);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du statut de don : " + e.getMessage());
        }

        // Vérifier si le nouvel état est valide
        if (!newEtatStatutDons.equals("en cours") && !newEtatStatutDons.equals("reçu")) {
            System.out.println("Erreur : État de statut de don invalide.");
            return;
        }

        // Mettre à jour l'état du statut de don
        String query = "UPDATE statutDons SET etatStatutDons = ? WHERE idStatutDons = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, newEtatStatutDons);
            pst.setInt(2, id);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Statut de don mis à jour avec succès.");
            } else {
                System.out.println("Aucun statut de don n'a été mis à jour.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du statut de don : " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

}
