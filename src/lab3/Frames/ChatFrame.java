package lab3.Frames;

import com.sun.org.apache.xpath.internal.operations.String;

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
 * Created by developer-kc3e on 19.10.16.
 */
public class ChatFrame extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JTextArea message;
    private JTextArea dialog;
    private JTextField port= new JTextField("4488");



    public ChatFrame() {
        super("Chat");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        dialog = new JTextArea(20, 35);
        dialog.append("Здесь будет отображаться диалог");
        dialog.setMaximumSize(dialog.getPreferredSize());

        message = new JTextArea(5, 35);
        message.append("Напишите свое сообщение");
        message.setMaximumSize(message.getPreferredSize());

        JButton buttonSend = new JButton("Send Message");
        /*
        final ChatFrame frame = this;
        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                java.lang.String dialogWith = dialog.getText();
                java.lang.String messageTo = message.getText();
              //  java.lang.String adressTo = iPAdress.getText();
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
                        ChatFrame frameOfChat = new ChatFrame();
                        frameOfChat.setVisible(true);
                        frame.dispose();
                        //name.setText(loginUser + 1);
                        //password.setText(passwordUser);
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
        */

        Box hboxLeft = Box.createVerticalBox();
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.add(dialog);
        hboxLeft.add(Box.createVerticalStrut(5));
        hboxLeft.add(message);
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.add(buttonSend);
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        ActiveUserComponent activeUserComponent = new ActiveUserComponent();
        activeUserComponent.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        activeUserComponent.setBorder(null);

        Box contentBox = Box.createHorizontalBox();
        contentBox.add(Box.createHorizontalGlue());
        contentBox.add(hboxLeft);contentBox.add(Box.createHorizontalGlue());
        contentBox.add(activeUserComponent);
        contentBox.add(Box.createHorizontalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }
}
