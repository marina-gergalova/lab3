package lab3.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by developer-kc3e on 19.10.16.
 */
public class ChatFrame extends JFrame {
    private static final int WIDTH = 465;
    private static final int HEIGHT = 460;
    private JTextArea message;
    private JTextArea dialog;

    public ChatFrame(String name, String iPAdress, int activePortServer) {
        super("Chat");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        dialog = new JTextArea(20, 35);
        dialog.setLineWrap(true);
        dialog.append("Диалог с пользователем");
        dialog.append("\n");
        dialog.setMaximumSize(dialog.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(dialog);
        scrollPane.setViewportView(dialog);

        message = new JTextArea(5, 35);
        message.setLineWrap(true);
        message.append("Напишите свое сообщение");
        message.setMaximumSize(message.getPreferredSize());

        final ActiveUserComponent activeUserComponent = new ActiveUserComponent();
        activeUserComponent.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        activeUserComponent.setBorder(null);

        JButton buttonSend = new JButton("Send Message");

        final ChatFrame frame = this;
        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                String userTo = activeUserComponent.getSelectedUser();
                String userFrom = name;
                String messageText = message.getText();
                String adressServer = iPAdress;
                int portServer = activePortServer;

                Socket socket = null;
                try {
                    socket = new Socket(adressServer, portServer);
                    DataInputStream socketIn = new DataInputStream(socket.getInputStream());
                    DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
                    socketOut.writeInt(2);
                    socketOut.writeUTF(userTo);
                    socketOut.writeUTF(userFrom);
                    socketOut.writeUTF(messageText);

                    messageText = socketIn.readUTF();
                    messageText += " to " + socketIn.readUTF() + ":";
                    messageText += " " + socketIn.readUTF();
                    dialog.append(messageText + "\n");
                    message.setText("");

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

        Box hboxLeft = Box.createVerticalBox();
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.add(scrollPane);
        hboxLeft.add(Box.createVerticalStrut(5));
        hboxLeft.add(message);
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.add(buttonSend);
        hboxLeft.add(Box.createVerticalGlue());
        hboxLeft.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        Box contentBox = Box.createHorizontalBox();
        contentBox.add(Box.createHorizontalGlue());
        contentBox.add(hboxLeft);
        contentBox.add(activeUserComponent);
        contentBox.add(Box.createHorizontalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }
}
