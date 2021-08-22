package org.xxharutoxx.views.mainWindow;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.xxharutoxx.models.OutputProduct;
import org.xxharutoxx.models.Products;
import org.xxharutoxx.models.ReceptionStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportsInputs extends JFrame{
    private JPanel PanelReportsImputs;
    private JTable tablaRegistrosEntradas;
    private JComboBox CBBProducts;
    private JLabel LBLGenerateReport;
    private LocalDate fechaActual=LocalDate.now();
    private DateTimeFormatter formatoFecha1= DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String title="Reportes entradas";
    private final String[] columnas = {"ID Recepcion Stock","FECHA","CANTIDAD","PRODUCTO"};
    private List<ReceptionStock> registros=ReceptionStock.getReceptionStocks();
    private DefaultTableModel modeloReportesEntradas;

    public ReportsInputs() {
        initComponents();
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
                    PdfWriter.getInstance(document,new FileOutputStream(ruta +"/Documents/Registros/Reportes Entradas "+formatoFecha1.format(fechaActual)+".pdf"));
                    document.open();
                    Font f=new Font(Font.FontFamily.TIMES_ROMAN,25.0f,Font.BOLD, BaseColor.BLACK);
                    Paragraph titulo=new Paragraph("REPORTE DE ENTRADAS",f);
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
                    for(int i=0;i<tablaRegistrosEntradas.getRowCount();i++){
                        table.addCell(tablaRegistrosEntradas.getModel().getValueAt(i,1).toString());
                        table.addCell(tablaRegistrosEntradas.getModel().getValueAt(i,3).toString());
                        table.addCell(tablaRegistrosEntradas.getModel().getValueAt(i,2).toString());
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
        CBBProducts.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String producto=String.valueOf(CBBProducts.getSelectedItem());
                if(producto.equals("Seleccione producto")){
                    listReports();
                }else {
                    filtrarPorProducto(producto);
                }
            }
        });
    }
    public JPanel getPanelPrincipal(){
        return PanelReportsImputs;
    }
    public String getTitle(){
        return title;
    }
    private void initComponents(){
        listNameProducts();
        listReports();
    }

    private void listReports() {
        registros=null;
        registros= ReceptionStock.getReceptionStocks();
        tablaRegistrosEntradas.setModel(new DefaultTableModel(null, columnas));
        modeloReportesEntradas = (DefaultTableModel) tablaRegistrosEntradas.getModel();
        Object[] objectProduct = new Object[4];
        for (ReceptionStock output : registros) {
            objectProduct[0] = output.getId_receptionStock();
            objectProduct[1] = output.getDate_entry();
            objectProduct[2] = output.getQuantity();
            objectProduct[3] = output.getName_product();
            modeloReportesEntradas.addRow(objectProduct);
        }
        tablaRegistrosEntradas.setModel(modeloReportesEntradas);
    }
    private void listNameProducts(){
        List<Products> Productos= Products.getProducts();
        for(Products p:Productos){
            CBBProducts.addItem(p.getName_product());
        }
    }


    private void filtrarPorProducto(String producto){
        modeloReportesEntradas.getDataVector().removeAllElements();
        modeloReportesEntradas.fireTableDataChanged();
        for(ReceptionStock p:registros){
            if(p.getName_product().equals(producto)){
                Object[] objectProduct = new Object[4];
                objectProduct[0] = p.getId_receptionStock();
                objectProduct[1] = p.getDate_entry();
                objectProduct[2] = p.getQuantity();
                objectProduct[3] = p.getName_product();
                modeloReportesEntradas.addRow(objectProduct);
            }
            tablaRegistrosEntradas.setModel(modeloReportesEntradas);
        }
    }

}
