// Richard Padilla
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GestionCalificaciones {
    public JTextField textCedula;
    public JTextField textNombre;
    public JTextField textCALIF1;
    public JTextField textCALIF2;
    public JTextField textCALIF3;
    public JTextField textCALIF4;
    public JTextField textCALIF5;
    public JButton guardarDatosButton;
    public JPanel Gestionarsito;
    public JButton verDatosButton;
    public JLabel VerDatos;

    public GestionCalificaciones() {
        guardarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textCedula.getText();
                String nombre = textNombre.getText();
                String calif1 = textCALIF1.getText();
                String calif2 = textCALIF2.getText();
                String calif3 = textCALIF3.getText();
                String calif4 = textCALIF4.getText();
                String calif5 = textCALIF5.getText();

                double[] calificaciones = new double[5];
                try {

                    calificaciones[0] = Double.parseDouble(calif1);
                    calificaciones[1] = Double.parseDouble(calif2);
                    calificaciones[2] = Double.parseDouble(calif3);
                    calificaciones[3] = Double.parseDouble(calif4);
                    calificaciones[4] = Double.parseDouble(calif5);

                    // validar calificaciones
                    for (double calif : calificaciones) {
                        if (calif < 0 || calif > 20) {
                            throw new NumberFormatException();
                        }
                    }

                    String url = "jdbc:mysql://localhost:3306/EXAMEN";
                    String user = "root";
                    String password = "1234";

                    try (Connection conexionMYSQL = DriverManager.getConnection(url, user, password)) {
                        String sql = "INSERT INTO estudiantes (cedula, nombre, nota1, nota2, nota3, nota4, nota5) VALUES (?,?,?,?,?,?,?)";
                        PreparedStatement statement = conexionMYSQL.prepareStatement(sql);
                        statement.setString(1, cedula);
                        statement.setString(2, nombre);
                        statement.setDouble(3, calificaciones[0]);
                        statement.setDouble(4, calificaciones[1]);
                        statement.setDouble(5, calificaciones[2]);
                        statement.setDouble(6, calificaciones[3]);
                        statement.setDouble(7, calificaciones[4]);
                        statement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "¡Datos Ingresados Correctamente!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la base de datos.");
                        ex.printStackTrace();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Las calificaciones deben ser números entre 0 y 20.");
                }
            }
        });


        verDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/EXAMEN";
                String user = "root";
                String password = "1234";

                try (Connection conexionMySQL = DriverManager.getConnection(url, user, password)) {
                    String query = "SELECT * FROM estudiantes";
                    PreparedStatement statement = conexionMySQL.prepareStatement(query);
                    ResultSet rs = statement.executeQuery();

                    // Esto es un tipo String que mostrara los resultados de mysql desde java
                    StringBuilder resultados = new StringBuilder("<html>");
                    while (rs.next()) {
                        double nota1 = rs.getDouble("nota1");
                        double nota2 = rs.getDouble("nota2");
                        double nota3 = rs.getDouble("nota3");
                        double nota4 = rs.getDouble("nota4");
                        double nota5 = rs.getDouble("nota5");
                        double promedio = (nota1 + nota2 + nota3 + nota4 + nota5) / 5;

                        resultados.append("ID: ").append(rs.getString("id"))
                                .append(",  Cedula: ").append(rs.getString("cedula"))
                                .append(",  Nombre: ").append(rs.getString("nombre"))
                                .append(",  Nota 1: ").append(nota1)
                                .append(",  Nota 2: ").append(nota2)
                                .append(",  Nota 3: ").append(nota3)
                                .append(",  Nota 4: ").append(nota4)
                                .append(",  Nota 5: ").append(nota5)
                                .append(",  PROMEDIO: ").append(promedio)
                                .append("<br>");
                    }
                    resultados.append("</html>");

                    // Mostrar los resultados
                    VerDatos.setText(resultados.toString());

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error en la base de datos!");
                }
            }
        });

    }
}
