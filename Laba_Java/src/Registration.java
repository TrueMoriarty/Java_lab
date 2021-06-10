import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Registration extends JFrame {
    Box box1,box2,box3, box4,box5,box6,mainBox;
    JButton button_ok,button_cancel, button_login;
    JTextField nameField,surnameField,yearField,nuberField,loginField,passwField;
    JLabel nameLabel,surnameLabel,yearLabel,nuberLabel,loginLabel,passwLabel;

    public Registration(String str){
        super(str);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        box1 = Box.createHorizontalBox();
        nameLabel = new JLabel("First Name");
        nameField = new JTextField(15);
        box1.add(nameLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(nameField);

        box2 = Box.createHorizontalBox();
        surnameLabel = new JLabel("Last Name");
        surnameField = new JTextField(15);
        box2.add(surnameLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(surnameField);

        box3 = Box.createHorizontalBox();
        yearLabel = new JLabel("Birth year");
        yearField = new JTextField(9);
        box3.add(yearLabel);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(yearField);

        box4 = Box.createHorizontalBox();
        loginLabel = new JLabel("Login");
        loginField = new JTextField(15);
        box4.add(loginLabel);
        box4.add(Box.createHorizontalStrut(6));
        box4.add(loginField);

        box5 = Box.createHorizontalBox();
        passwLabel = new JLabel("Password");
        passwField = new JTextField(15);
        box5.add(passwLabel);
        box5.add(Box.createHorizontalStrut(6));
        box5.add(passwField);

        box6 = Box.createHorizontalBox();
        button_ok = new JButton("Register");
        button_cancel = new JButton("Cancel");
        button_login = new JButton("Login");
        box6.add(button_cancel);
        box6.add(Box.createHorizontalStrut(12));
        box6.add(button_ok);
        box6.add(Box.createHorizontalStrut(12));
        box6.add(button_login);

        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box3);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box4);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box5);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box6);
        setContentPane(mainBox);

//add events
        button_ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register();
            }});

        button_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cancel();
            }});

        button_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OpenLogin();
            }});

        pack();
        show();
    }

    void Cancel() {
        dispose();
    }

    void Register() {
        //TODO
    }

    void OpenLogin() {
        new Login("Login");
        dispose();
    }
}
