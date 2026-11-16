import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;

public class Ventana {
    private JPanel principal;
    private JTabbedPane tabbedPane1;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JComboBox cboGenero;
    private JSpinner spiAnio;
    private JButton btnRegistrar;
    private JLabel lblTotal;
    private CatalogoLibros catalogo=new CatalogoLibros();

    public Ventana() {
        spiAnio.setModel(new SpinnerNumberModel(Year.now().getValue(),1,Year.now().getValue(),1));
        lblTotal.setText("Total de libros: "+catalogo.size());
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo=Integer.parseInt(txtCodigo.getText().trim());
                    String titulo=txtTitulo.getText().trim();
                    String autor=txtAutor.getText().trim();
                    String genero=cboGenero.getSelectedItem().toString();
                    int anio=Integer.parseInt(spiAnio.getValue().toString());
                    catalogo.registrar(new Libro(codigo,titulo,autor,genero,anio,0));
                    JOptionPane.showMessageDialog(null,"Libro registrado");
                    lblTotal.setText("Total de libros: "+catalogo.size());
                    txtCodigo.setText("");
                    txtTitulo.setText("");
                    txtAutor.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"El codigo debe ser un numero entero");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BiblioLikes");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setVisible(true);
    }
}
