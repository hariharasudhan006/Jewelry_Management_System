package jewelry_management_system.dashboard;

import jewelry_management_system.login.ChangePassword;
import jewelry_management_system.login.ChangeUserName;
import jewelry_management_system.login.Login;
import jewelry_management_system.session.Session;
import jewelry_management_system.session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAccountPopup {
    private final JPopupMenu menu;
    private final JMenuItem changePassword;
    private final JMenuItem changeUsername;
    private final JMenuItem logout;
    private Session session;
    private final JFrame frame;
    private final Point point;

    public MyAccountPopup(JFrame frm, Point location){
        session = SessionManager.getCurrentSession();
        menu = new JPopupMenu(session.getUsername());
        changePassword = new JMenuItem("Change password");
        changeUsername = new JMenuItem("Change Username");
        logout = new JMenuItem("Logout");
        frame = frm;
        point = location;
        initComponents();
    }

    public void initComponents(){
        changePassword.addActionListener(event -> onChangePasswordSelected());
        changeUsername.addActionListener(event -> onChangeUsernameSelected());
        logout.addActionListener(event -> onLogoutSelected());
        menu.add(session.getUsername());
        menu.add(changePassword);
        menu.add(changeUsername);
        menu.add(logout);
        menu.setPopupSize(160, 170);
    }

    public void show(){
        menu.show(frame, point.x+20, point.y+95);
        menu.setVisible(true);
    }

    private void onChangePasswordSelected(){
        System.out.println("Change Password selected");
        new ChangePassword(frame, true);
    }

    private void onChangeUsernameSelected(){
        System.out.println("Change username selected");
    }

    private void onLogoutSelected(){
        SessionManager.purgeCurrentSession();
        new Login().setVisible(true);
        frame.dispose();
    }
}
