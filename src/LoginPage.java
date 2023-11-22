import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements ActionListener {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel PasswordLabel ;
    private static JPasswordField passwordText;
    private static JButton button;
    private static JLabel success;

    public static void main(String[] args){

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("User Name: ");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);
        userText = new JTextField();
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        PasswordLabel = new JLabel("Password: ");
        PasswordLabel.setBounds(10,50,80,25);
        panel.add(PasswordLabel);
        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10,80,80, 25);
        button.addActionListener(new LoginPage());
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        System.out.println("Button clicked");
        String user = userText.getText();
        String password = passwordText.getText();
    }
}
