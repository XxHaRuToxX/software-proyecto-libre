package org.xxharutoxx.views.mainWindow;

import org.xxharutoxx.models.Users;
import org.xxharutoxx.views.Login;
import org.xxharutoxx.views.NewUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {
    private JPanel panelPrincipal;
    private JLabel btnUsers;
    private JLabel btnCloseSession;
    private JButton btnStart;
    private JButton btnSales;
    private JButton btnEntry;
    private JTabbedPane tabbedPane;
    private JSplitPane jSplitPane;
    private JPanel jpanelCenter;
    private JLabel lblNombreUsuario;
    private JButton btnReports;
    private Dimension dimension;
    private OptionStart optionStart;
    private OptionOutput optionOutput;
    private OptionInput optionInput;
    private OptionReports optionReports;


    public Principal(Users priv){
        lblNombreUsuario.setText(priv.getFullNames());
        btnUsers.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCloseSession.setCursor(new Cursor(Cursor.HAND_CURSOR));
        initComponent();
        if(priv.getRol().equals("Asistente")){
//            optionOutput.getBtnRemoveOutput().setVisible(false);
            btnUsers.setVisible(false);
            btnCloseSession.setVisible(true);
            optionInput.getBtnRegisterProduct().setVisible(false);

        }else{
            btnUsers.setVisible(true);
            btnCloseSession.setVisible(true);
//            optionOutput.getBtnRemoveOutput().setVisible(true);
            optionInput.getBtnRegisterProduct().setVisible(true);
        }
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSplitPane.setLeftComponent(optionStart.getPanelPrincipal());
            }
        });
        btnSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSplitPane.setLeftComponent(optionOutput.getPanelPrincipal());
            }
        });

        btnEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSplitPane.setLeftComponent(optionInput.getPanelPrincipal());
            }
        });

        btnCloseSession.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeSession();
            }
        });
        btnUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NewUser newUser=new NewUser();
                newUser.setVisible(true);
            }
        });
        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSplitPane.setLeftComponent(optionReports.getPanelPrincipal());
            }
        });
    }
    public void initComponent(){
        dimension=new Dimension();
        dimension.width=1060;
        dimension.height=600;
        setContentPane(panelPrincipal);
        setSize(1060,600);
        setMinimumSize(dimension);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        optionStart=new OptionStart(tabbedPane);
        optionOutput =new OptionOutput(tabbedPane);
        optionInput=new OptionInput(tabbedPane);
        optionReports=new OptionReports(tabbedPane);

        jSplitPane.setLeftComponent(optionStart.getPanelPrincipal());
        tabbedPane.add(optionStart.getPanelFromWelcome().getTitle(),optionStart.getPanelFromWelcome().getPanelWelcome());
    }

    public void loadLoginPanel1(){
        Login log=new Login();
        log.setVisible(true);
    }
    public void closeSession(){
        Login p=new Login();
        p.setVisible(true);
        dispose();
    }
    public void nonDuplicatePanel(){
        if (jSplitPane.getComponentCount()==1){
            jSplitPane.setLeftComponent(null);
        }
    }

}
