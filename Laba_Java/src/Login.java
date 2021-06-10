import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    Box box1,box2,box3,mainBox;
    JButton button_register,button_cancel, button_login;
    JTextField loginField,passwField;
    JLabel loginLabel,passwLabel;

    public Login(String str){

        super(str);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        box1 = Box.createHorizontalBox();
        loginLabel = new JLabel("Login");
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(loginField);

        box2 = Box.createHorizontalBox();
        passwLabel = new JLabel("Password");
        passwField = new JTextField(15);
        box2.add(passwLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(passwField);

        box3 = Box.createHorizontalBox();
        button_register = new JButton("Register");
        button_cancel = new JButton("Cancel");
        button_login = new JButton("Login");
        box3.add(button_cancel);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(button_login);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(button_register);

        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box3);
        setContentPane(mainBox);

//add events
        button_register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OpenRegister();
            }});

        button_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cancel();
            }});

        button_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    TryLogin();
                }
                catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                catch (FailedLoginException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }});

        pack();
        show();
    }

    public void TryLogin() throws NullPointerException, FailedLoginException {
        //TODO
        String login = loginField.getText();
        String password = passwField.getText();

        if(!login.equals("") && !password.equals("")) {
            if(login.equals("admin") && password.equals("admin")) {
                new MainForm(null);
                dispose();
            }
            else throw new FailedLoginException("Incorrect login or password");
        }
        else throw new NullPointerException("Login or password cannot be null");
    }

    public void OpenRegister() {
        new Registration("Registration");
        dispose();
    }

    public void Cancel() {
        dispose();
    }

    private class FailedLoginException extends Exception {
        public FailedLoginException(String errorMessage) {
            super(errorMessage);
        }
    }
}
