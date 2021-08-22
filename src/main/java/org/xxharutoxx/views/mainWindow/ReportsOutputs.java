package org.xxharutoxx.views.mainWindow;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.xxharutoxx.models.OutputProduct;
import org.xxharutoxx.models.Products;
import org.xxharutoxx.models.ReceptionStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportsOutputs {
    private JTable tablaRegistrosSalida;
    private JComboBox CBBProducts;
    private JTextField txtFecha;
    private JLabel LBLGenerateReport;
    private JPanel PanelReportsOutputs;
    private final String title="Reporte de salidas";
    private DefaultTableModel modelTableCancelOutput;
    private final String[] columnsTableCancelOutput = {"ID SALIDA", "ID DESC", "ID PRODUCTO", "NOMBRES", "PRODUCTO", "CATEGORÍA", "MARCA", "CANTIDAD", "FECHA"};
    private List<String[]> getOutputProducts = OutputProduct.getOutputProduct();
    private LocalDate fechaActual=LocalDate.now();
    private DateTimeFormatter formatoFecha1= DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ReportsOutputs() {
        initComponents();
        txtFecha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarElemento(txtFecha.getText(),tablaRegistrosSalida);
            }
        });
        CBBProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CBBProducts.getSelectedItem().toString().equals("Seleccione producto")){
                    listOutputProducts();
                }else {
                    filtrarPorProducto(CBBProducts.getSelectedItem().toString());
                }
            }
        });
        LBLGenerateReport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String ruta1=System.getProperty("user.home");
                String directoryName =ruta1+"/Documents/Registros";

                File directory = new File(directoryName);
                if (!directory.exists()){
                    directory.mkdirs();
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                Document document= new Document();
                try {
                    String ruta=System.getProperty("user.home");
                    PdfWriter.getInstance(document,new FileOutputStream(ruta +"/Documents/Registros/Reportes Salidas "+formatoFecha1.format(fechaActual)+".pdf"));
                    document.open();
                    Font f=new Font(Font.FontFamily.TIMES_ROMAN,25.0f,Font.BOLD, BaseColor.BLACK);
                    Paragraph titulo=new Paragraph("REPORTE DE SALIDAS",f);
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    Paragraph titulo1=new Paragraph("\n");
                    Paragraph titulo2=new Paragraph(" \n");
                    document.add(titulo);
                    document.add(titulo1);
                    document.add(titulo2);
                    PdfPTable table= new PdfPTable(3);
                    table.addCell("FECHA");
                    table.addCell("PRODUCTO");
                    table.addCell("CANTIDAD");
                    for(int i=0;i<tablaRegistrosSalida.getRowCount();i++){
                        table.addCell(tablaRegistrosSalida.getModel().getValueAt(i,8).toString());
                        table.addCell(tablaRegistrosSalida.getModel().getValueAt(i,3).toString());
                        table.addCell(tablaRegistrosSalida.getModel().getValueAt(i,7).toString());
                    }
                    document.add(table);
                    document.close();
                    JOptionPane.showMessageDialog(null,"reporte creado");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"cierre el pdf");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void filtrarElemento(String consult, JTable jTableSearch) {
        DefaultTableModel dm;
        dm = (DefaultTableModel) jTableSearch.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        jTableSearch.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(consult));
    }

    private void placeHolderText(){
        PromptSupport.setPrompt("Ejemplo: "+fechaActual,txtFecha);
    }
    public JPanel getPanelPrincipal(){
        return PanelReportsOutputs;
    }
    public String getTitle(){
        return title;
    }

    private void listNameProducts(){
        List<Products> Productos= Products.getProducts();
        for(Products p:Productos){
            CBBProducts.addItem(p.getName_product());
        }
    }
    private void initComponents(){
        listNameProducts();
        listOutputProducts();
        placeHolderText();
    }
    public void listOutputProducts() {
        getOutputProducts=null;
        getOutputProducts = OutputProduct.getOutputProduct();
        tablaRegistrosSalida.setModel(new DefaultTableModel(null, columnsTableCancelOutput));
        modelTableCancelOutput = (DefaultTableModel) tablaRegistrosSalida.getModel();
        Object[] objectProduct = new Object[9];
        for (String[] output : getOutputProducts) {
            objectProduct[0] = output[0];
            objectProduct[1] = output[1];
            objectProduct[2] = output[2];
            objectProduct[3] = output[3];
            objectProduct[4] = output[4];
            objectProduct[5] = output[5];
            objectProduct[6] = output[6];
            objectProduct[7] = output[7];
            objectProduct[8] = output[8];
            modelTableCancelOutput.addRow(objectProduct);
        }
        tablaRegistrosSalida.setModel(modelTableCancelOutput);
    }
//    private void listReports() {
//        registros=null;
//        registros= ReceptionStock.getReceptionStocks();
//        tablaRegistrosEntradas.setModel(new DefaultTableModel(null, columnas));
//        modeloReportesEntradas = (DefaultTableModel) tablaRegistrosEntradas.getModel();
//        Object[] objectProduct = new Object[4];
//        for (ReceptionStock output : registros) {
//            objectProduct[0] = output.getId_receptionStock();
//            objectProduct[1] = output.getDate_entry();
//            objectProduct[2] = output.getQuantity();
//            objectProduct[3] = output.getName_product();
//            modeloReportesEntradas.addRow(objectProduct);
//        }
//        tablaRegistrosEntradas.setModel(modeloReportesEntradas);
//    }
    private void filtrarPorProducto(String producto){
        modelTableCancelOutput.getDataVector().removeAllElements();
        modelTableCancelOutput.fireTableDataChanged();
        for(String[] output:getOutputProducts){
            if(output[4].equals(producto)){
                Object[] objectProduct = new Object[9];
                objectProduct[0] = output[0];
                objectProduct[1] = output[1];
                objectProduct[2] = output[2];
                objectProduct[3] = output[3];
                objectProduct[4] = output[4];
                objectProduct[5] = output[5];
                objectProduct[6] = output[6];
                objectProduct[7] = output[7];
                objectProduct[8] = output[8];
                modelTableCancelOutput.addRow(objectProduct);
            }
            tablaRegistrosSalida.setModel(modelTableCancelOutput);
        }
    }

}
