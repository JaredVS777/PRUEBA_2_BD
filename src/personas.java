import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class personas {
    private JTextField codigoTextField;
    private JTextField cedulaTextField;
    private JTextField nombreTextField;
    private JTextField signoTextField;
    private JTextField fechaNacimientoTextField;
    private JButton buscarPorCodigoButton;
    private JButton buscarPorNombreButton;
    private JButton buscarPorSignoButton;
    private JButton actualizarButton;
    private JPanel formulario;
    private JButton limpiarFormularioButton;
    private JButton registrarButton;

    private Connection connection;

    public personas() {
        try {
            // Realizar la conexión a MySQL
            String url = "jdbc:mysql://localhost:3306/registro";
            String user = "root";
            String password = "vodafone2002";
            connection = DriverManager.getConnection(url, user, password);

            // Configurar los ActionListener para los botones
            buscarPorCodigoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buscarPorCodigo();
                }
            });

            buscarPorNombreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buscarPorNombre();
                }
            });

            buscarPorSignoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buscarPorSigno();
                }
            });

            actualizarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actualizarPersona();
                }
            });

            limpiarFormularioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarFormulario();
                }
            });

            registrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    registrarPersona();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos.");
        }
    }

    private void buscarPorCodigo() {
        String codigoBusqueda = codigoTextField.getText();
        try {
            String sql = "SELECT * FROM personas WHERE codigo=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, codigoBusqueda);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Persona persona = obtenerPersonaDesdeResultSet(result);
                mostrarResultado(persona);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una persona con ese código.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar la persona.");
        }
    }

    private void buscarPorNombre() {
        String nombreBusqueda = nombreTextField.getText();
        try {
            String sql = "SELECT * FROM personas WHERE nombre LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + nombreBusqueda + "%");

            ResultSet result = statement.executeQuery();
            List<Persona> personasEncontradas = new ArrayList<>();

            while (result.next()) {
                Persona persona = obtenerPersonaDesdeResultSet(result);
                personasEncontradas.add(persona);
            }

            if (!personasEncontradas.isEmpty()) {
                mostrarResultado(personasEncontradas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron personas con ese nombre.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar personas.");
        }
    }

    private void buscarPorSigno() {
        String signoBusqueda = signoTextField.getText();
        try {
            String sql = "SELECT * FROM personas WHERE signo=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, signoBusqueda);

            ResultSet result = statement.executeQuery();
            List<Persona> personasEncontradas = new ArrayList<>();

            while (result.next()) {
                Persona persona = obtenerPersonaDesdeResultSet(result);
                personasEncontradas.add(persona);
            }

            if (!personasEncontradas.isEmpty()) {
                mostrarResultado(personasEncontradas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron personas con ese signo.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar personas.");
        }
    }

    private Persona obtenerPersonaDesdeResultSet(ResultSet result) throws SQLException {
        String codigo = result.getString("codigo");
        String cedula = result.getString("cedula");
        String nombre = result.getString("nombre");
        String signo = result.getString("signo");
        String fechaNacimiento = result.getString("fecha_nacimiento");
        return new Persona(codigo, cedula, nombre, signo, fechaNacimiento);
    }

    private void mostrarResultado(Persona persona) {
        codigoTextField.setText(persona.getCodigo());
        cedulaTextField.setText(persona.getCedula());
        nombreTextField.setText(persona.getNombre());
        signoTextField.setText(persona.getSigno());
        fechaNacimientoTextField.setText(persona.getFechaNacimiento());
    }

    private void mostrarResultado(List<Persona> personas) {
        StringBuilder builder = new StringBuilder();
        for (Persona persona : personas) {
            builder.append("Código: ").append(persona.getCodigo())
                    .append(", Cédula: ").append(persona.getCedula())
                    .append(", Nombre: ").append(persona.getNombre())
                    .append(", Signo: ").append(persona.getSigno())
                    .append(", Fecha de Nacimiento: ").append(persona.getFechaNacimiento())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, "Personas encontradas:\n" + builder.toString());
    }

    private void actualizarPersona() {
        String codigo = codigoTextField.getText();
        String cedula = cedulaTextField.getText();
        String nombre = nombreTextField.getText();
        String signo = signoTextField.getText();
        String fechaNacimiento = fechaNacimientoTextField.getText();

        try {
            String sql = "UPDATE personas SET cedula=?, nombre=?, signo=?, fecha_nacimiento=? WHERE codigo=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cedula);
            statement.setString(2, nombre);
            statement.setString(3, signo);
            statement.setString(4, fechaNacimiento);
            statement.setString(5, codigo);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Persona actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una persona con ese código.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la persona.");
        }
    }

    private void limpiarFormulario() {
        codigoTextField.setText("");
        cedulaTextField.setText("");
        nombreTextField.setText("");
        signoTextField.setText("");
        fechaNacimientoTextField.setText("");
    }

    private void registrarPersona() {
        String codigo = codigoTextField.getText();
        String cedula = cedulaTextField.getText();
        String nombre = nombreTextField.getText();
        String signo = signoTextField.getText();
        String fechaNacimiento = fechaNacimientoTextField.getText();

        try {
            String sql = "INSERT INTO personas (codigo, cedula, nombre, signo, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, codigo);
            statement.setString(2, cedula);
            statement.setString(3, nombre);
            statement.setString(4, signo);
            statement.setString(5, fechaNacimiento);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Persona registrada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la persona.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar la persona.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Personas App");
                frame.setContentPane(new personas().formulario);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
