// Richard Padilla
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    public JTextField textUser;
    public JPasswordField passwordField;
    public JButton iniciarSesionButton;
    public JPanel loginsito;

    public Login() {
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/EXAMEN";
                String user = "root";
                String password = "1234";

                try (Connection conexionMYSQL = DriverManager.getConnection(url, user, password)) {
                    String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
                    PreparedStatement statement = conexionMYSQL.prepareStatement(query);

                    statement.setString(1, textUser.getText());
                    statement.setString(2, passwordField.getText());

                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Correcto! Bienvenid@ " + textUser.getText());

                        JFrame frame = new JFrame("Gestion De Calificaciones");
                        frame.setContentPane(new GestionCalificaciones().Gestionarsito);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(500, 300);
                        frame.setPreferredSize(new Dimension(900, 500));
                        frame.setLocationRelativeTo(null);
                        frame.pack();
                        frame.setVisible(true);

                        // Cerrar la ventana de login actual
                        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(loginsito);
                        loginFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error de conexión en la base de datos!");
                    ex.printStackTrace();
                }
            }
        });
    }

}
