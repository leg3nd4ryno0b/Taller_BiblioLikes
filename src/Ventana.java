import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JTextField txtBuscarCodigo;
    private JButton btnBuscarCodigo;
    private JTextField txtBuscarTexto;
    private JButton btnBuscarTexto;
    private JLabel lblCoincidencias;
    private JList lstResultados;
    private JLabel lblCodigo;
    private JTextField txtEditarTitulo;
    private JTextField txtEditarAutor;
    private JComboBox cboEditarGenero;
    private JSpinner spiEditarAnio;
    private JLabel lblMeGusta;
    private JButton btnGuardar;
    private JButton btnMeGusta;
    private JList lstRanking;
    private JButton btnActualizar;
    private CatalogoLibros catalogo=new CatalogoLibros();
    private int codigoSeleccionado=-1;
    private String consulta="";

    public Ventana() {
        spiAnio.setModel(new SpinnerNumberModel(Year.now().getValue(),1,Year.now().getValue(),1));
        spiEditarAnio.setModel(new SpinnerNumberModel(Year.now().getValue(),1,Year.now().getValue(),1));
        lblTotal.setText("Total de libros: "+catalogo.size());
        mostrarRanking();
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
        btnBuscarCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consulta="";
                DefaultListModel dlm=new DefaultListModel();
                lstResultados.setModel(dlm);
                lblCoincidencias.setText("Coincidencias: 0");
                limpiar();
                try {
                    int codigo=Integer.parseInt(txtBuscarCodigo.getText().trim());
                    int indice=catalogo.buscarIndicePorCodigo(codigo);
                    if(indice==-1){
                        JOptionPane.showMessageDialog(null,"Libro no encontrado");
                    }else{
                        dlm.addElement(catalogo.obtener().get(indice));
                        lblCoincidencias.setText("Coincidencias: 1");
                        lstResultados.setSelectedIndex(0);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"El codigo debe ser un numero entero");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
        btnBuscarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtBuscarTexto.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Ingrese un texto para buscar");
                }else{
                    try {
                        consulta=txtBuscarTexto.getText();
                        mostrarResultados();
                        if(lstResultados.getModel().getSize()==0)
                            JOptionPane.showMessageDialog(null,"No hay coincidencias");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
            }
        });
        lstResultados.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(lstResultados.getSelectedValue()==null){
                    limpiar();
                }else{
                    mostrar((Libro) lstResultados.getSelectedValue());
                }
            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(codigoSeleccionado==-1){
                    JOptionPane.showMessageDialog(null,"Seleccione un libro");
                }else{
                    try {
                        catalogo.editarLibro(codigoSeleccionado,
                                txtEditarTitulo.getText(),
                                txtEditarAutor.getText(),
                                cboEditarGenero.getSelectedItem().toString(),
                                Integer.parseInt(spiEditarAnio.getValue().toString()));
                        JOptionPane.showMessageDialog(null,"Libro actualizado");
                        refrescarResultados();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
            }
        });
        btnMeGusta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(codigoSeleccionado==-1){
                    JOptionPane.showMessageDialog(null,"Seleccione un libro");
                }else{
                    try {
                        catalogo.darMeGusta(codigoSeleccionado);
                        Libro aux=catalogo.obtener().get(catalogo.buscarIndicePorCodigo(codigoSeleccionado));
                        lblMeGusta.setText("Me gusta: "+aux.getMeGusta());
                        lstResultados.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
            }
        });
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRanking();
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lblTotal.setText("Total de libros: "+catalogo.size());
                try {
                    refrescarResultados();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
                mostrarRanking();
            }
        });
    }

    private void mostrarResultados() throws Exception {
        DefaultListModel dlm=new DefaultListModel();
        dlm.addAll(catalogo.buscarCoincidencias(consulta));
        lstResultados.setModel(dlm);
        lblCoincidencias.setText("Coincidencias: "+dlm.size());
        limpiar();
    }

    private void refrescarResultados() throws Exception {
        int codigo=codigoSeleccionado;
        if(!consulta.isEmpty()){
            mostrarResultados();
            for(int i=0;i<lstResultados.getModel().getSize();i++){
                Libro aux=(Libro) lstResultados.getModel().getElementAt(i);
                if(aux.getCodigo()==codigo)
                    lstResultados.setSelectedIndex(i);
            }
        }else if(codigo!=-1){
            lstResultados.repaint();
            mostrar(catalogo.obtener().get(catalogo.buscarIndicePorCodigo(codigo)));
        }
    }

    private void mostrar(Libro libro){
        codigoSeleccionado=libro.getCodigo();
        lblCodigo.setText("Codigo: "+libro.getCodigo());
        txtEditarTitulo.setText(libro.getTitulo());
        txtEditarAutor.setText(libro.getAutor());
        cboEditarGenero.setSelectedItem(libro.getGenero());
        spiEditarAnio.setValue(libro.getAnio());
        lblMeGusta.setText("Me gusta: "+libro.getMeGusta());
    }

    private void limpiar(){
        codigoSeleccionado=-1;
        lblCodigo.setText("Codigo: ");
        txtEditarTitulo.setText("");
        txtEditarAutor.setText("");
        cboEditarGenero.setSelectedIndex(0);
        spiEditarAnio.setValue(Year.now().getValue());
        lblMeGusta.setText("Me gusta: ");
    }

    private void mostrarRanking(){
        DefaultListModel dlm=new DefaultListModel();
        dlm.addAll(catalogo.obtenerRanking());
        lstRanking.setModel(dlm);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BiblioLikes");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setVisible(true);
    }
}
