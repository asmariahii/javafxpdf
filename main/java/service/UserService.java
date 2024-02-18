package service;

import entities.User;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Connection conn;
    private PreparedStatement pst;

    public UserService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void addUser(User user) {
        try {
            String query = "INSERT INTO Users (nbpoints) VALUES (?)";

            pst = conn.prepareStatement(query);

            pst.setInt(1, user.getNbPoints());

            pst.executeUpdate();

            System.out.println("Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
        } finally {
            // Fermeture des ressources (PreparedStatement, ResultSet, etc.)
        }
    }

    public void deleteUser(int id) {
        try {
            String query = "DELETE FROM Users WHERE idUser = ?";

            pst = conn.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();

            System.out.println("Utilisateur supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
        } finally {
            // Fermeture des ressources (PreparedStatement, ResultSet, etc.)
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users";
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idUser");
                int nbpoints = rs.getInt("nbpoints");
                User user = new User(id, nbpoints);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
        } finally {
            // Fermeture des ressources (PreparedStatement, ResultSet, etc.)
        }
        return userList;
    }

    public User getUserById(int id) {
        User user = null;
        try {
            String query = "SELECT * FROM Users WHERE idUser = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int nbpoints = rs.getInt("nbpoints");
                user = new User(id, nbpoints);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur par ID: " + e.getMessage());
        } finally {
            // Fermeture des ressources (PreparedStatement, ResultSet, etc.)
        }
        return user;
    }

    public void updateUserPoints(int userId, int newNbPoints) {
        try {
            String query = "UPDATE Users SET nbpoints = ? WHERE idUser = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, newNbPoints);
            pst.setInt(2, userId);
            pst.executeUpdate();
            System.out.println("Points de l'utilisateur mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour des points de l'utilisateur: " + e.getMessage());
        } finally {
            // Fermeture des ressources (PreparedStatement, ResultSet, etc.)
        }
    }
    // Ajoutez d'autres méthodes au besoin...
}
