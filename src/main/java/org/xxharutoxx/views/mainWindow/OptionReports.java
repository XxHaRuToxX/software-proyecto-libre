package org.xxharutoxx.views.mainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptionReports {
    private JButton reportesEntradasButton;
    private JButton reportesSalidasButton;
    private JPanel panelOptionsReports;
    private JTabbedPane tabpanel;
    private ReportsInputs reportsInputs;
    private ReportsOutputs reportsOutputs;

    public OptionReports(final JTabbedPane tabpanel) {
        this.tabpanel=tabpanel;
        initComponents();
        reportesEntradasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabpanel.indexOfComponent(reportsInputs.getPanelPrincipal())==-1){
                    tabpanel.add(reportsInputs.getTitle(),reportsInputs.getPanelPrincipal());
                }
                tabpanel.setSelectedComponent(reportsInputs.getPanelPrincipal());
            }
        });
        reportesSalidasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabpanel.indexOfComponent(reportsOutputs.getPanelPrincipal())==-1){
                    tabpanel.add(reportsOutputs.getTitle(),reportsOutputs.getPanelPrincipal());
                }
                tabpanel.setSelectedComponent(reportsOutputs.getPanelPrincipal());
            }
        });
        reportesSalidasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

    }
    private void initComponents(){
        reportsInputs=new ReportsInputs();
        reportsOutputs=new ReportsOutputs();
    }
    public JPanel getPanelPrincipal(){
        return panelOptionsReports;
    }
}
