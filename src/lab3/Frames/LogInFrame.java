package lab3.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static lab3.server.Server.USER_NOT_FOUND;

/**
 * Created by developer-kc3e on 16.10.16.
 */
public class LogInFrame extends JFrame {

    private static final String FRAME_TITLE = "Registration";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 200;

    private JTextField name;
    private JTextField password;
    private JTextField iPAdress;
    private JTextField port;

    public LogInFrame() {

        super(FRAME_TITLE);
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        JLabel labelForName = new JLabel("name:");
        name = new JTextField("Marina", 9);
        name.setMaximumSize(name.getPreferredSize());
        JLabel labelForPassword = new JLabel("password:");
        password = new JTextField("1q", 10);
        password.setMaximumSize(password.getPreferredSize());
        JLabel labelForIPAdress = new JLabel("IP adress:");
        iPAdress = new JTextField("127.0.0.1", 10);
        iPAdress.setMaximumSize(iPAdress.getPreferredSize());
        JLabel labelForPort = new JLabel("port");
        port = new JTextField("4488", 7);
        port.setMaximumSize(port.getPreferredSize());
        JLabel labelForPoints = new JLabel(":");

        Box hboxVariablesN = Box.createHorizontalBox();
        hboxVariablesN.add(Box.createHorizontalGlue());
        hboxVariablesN.add(labelForName);
        hboxVariablesN.add(Box.createHorizontalStrut(10));
        hboxVariablesN.add(name);
        hboxVariablesN.add(Box.createHorizontalStrut(10));
        hboxVariablesN.add(Box.createHorizontalGlue());

        Box hboxVariablesP = Box.createHorizontalBox();
        hboxVariablesP.add(Box.createHorizontalGlue());
        hboxVariablesP.add(labelForPassword);
        hboxVariablesP.add(Box.createHorizontalStrut(10));
        hboxVariablesP.add(password);
        hboxVariablesP.add(Box.createHorizontalStrut(10));
        hboxVariablesP.add(Box.createHorizontalGlue());

        Box hboxVariablesIPPort = Box.createHorizontalBox();
        hboxVariablesIPPort.add(Box.createHorizontalGlue());
        hboxVariablesIPPort.add(labelForIPAdress);
        hboxVariablesIPPort.add(Box.createHorizontalStrut(10));
        hboxVariablesIPPort.add(labelForPort);
        hboxVariablesIPPort.add(Box.createHorizontalStrut(10));
        hboxVariablesIPPort.add(Box.createHorizontalGlue());

        Box hboxVariablesIPPortOut = Box.createHorizontalBox();
        hboxVariablesIPPortOut.add(Box.createHorizontalGlue());
        hboxVariablesIPPortOut.add(iPAdress);
        hboxVariablesIPPortOut.add(Box.createHorizontalStrut(5));
        hboxVariablesIPPortOut.add(labelForPoints);
        hboxVariablesIPPortOut.add(Box.createHorizontalStrut(5));
        hboxVariablesIPPortOut.add(port);
        hboxVariablesIPPortOut.add(Box.createHorizontalStrut(10));
        hboxVariablesIPPortOut.add(Box.createHorizontalGlue());

        Box hboxVariables = Box.createVerticalBox();
        hboxVariables.add(Box.createVerticalGlue());
        hboxVariables.add(hboxVariablesN);
        hboxVariables.add(hboxVariablesP);
        hboxVariables.add(Box.createVerticalGlue());

        JButton buttonLogIn = new JButton("Log In");
        final LogInFrame frame = this;
        buttonLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                String loginUser = name.getText();
                String passwordUser = password.getText();
                String adressServer = iPAdress.getText();
                int portServer = Integer.parseInt(port.getText());

                Socket socket = null;
                try {
                    socket = new Socket(adressServer, portServer);
                    DataInputStream socketIn = new DataInputStream(socket.getInputStream());
                    DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
                    socketOut.writeInt(1);
                    socketOut.writeUTF(loginUser);
                    socketOut.writeUTF(passwordUser);
                    if (socketIn.readUTF().equals(USER_NOT_FOUND)) {
                        name.setText("");
                        password.setText("");
                    } else {
                        frame.setVisible(false);
                        ChatFrame frameOfChat = new ChatFrame(loginUser, iPAdress.getText(), portServer);
                        frameOfChat.setVisible(true);
                        frame.dispose();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Box hboxButton = Box.createHorizontalBox();
        hboxButton.add(Box.createHorizontalGlue());
        hboxButton.add(buttonLogIn);
        hboxButton.add(Box.createHorizontalGlue());

        // Связать области воедино в компоновке BoxLayout
        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxVariables);
        contentBox.add(hboxVariablesIPPort);
        contentBox.add(hboxVariablesIPPortOut);
        contentBox.add(hboxButton);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        LogInFrame frame = new LogInFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
