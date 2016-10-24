package lab3.Frames;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by developer-kc3e on 19.10.16.
 */
public class ChatFrame extends JFrame {
    private static final int WIDTH = 465;
    private static final int HEIGHT = 460;
    private JTextArea message;
    private JTextArea dialog;
    private Map<String, List<String>> historyMessagess = new HashMap<>();

    public ChatFrame(String name, String iPAddress, int activePortServer) {
        super("Chat");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);


        message = new JTextArea(5, 35);
        message.setLineWrap(true);
        message.append("Напишите свое сообщение");
        message.setMaximumSize(message.getPreferredSize());

        final ActiveUserComponent activeUserComponent = new ActiveUserComponent(iPAddress, activePortServer);
        activeUserComponent.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        activeUserComponent.setBorder(null);
        activeUserComponent.addSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String userName = activeUserComponent.getSelectedUser();
                dialog.setText("");
                if (historyMessagess.get(userName) != null) {
                        for (int i = 0; i < historyMessagess.get(userName).size(); i++) {
                            dialog.append(historyMessagess.get(userName).get(i) + "\n");
                        }
                } else {
                    createNewUser(userName, activeUserComponent);
                }
            }
        });

        dialog = new JTextArea(20, 35);
        dialog.setLineWrap(true);
        dialog.append("");
        dialog.setMaximumSize(dialog.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(dialog);
        scrollPane.setViewportView(dialog);

        JButton buttonSend = new JButton("Send Message");

        final ChatFrame frame = this;
        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {


                String userTo = activeUserComponent.getSelectedUser();
                String userFrom = name;
                String messageText = message.getText();
                String adressServer = iPAddress;
                int portServer = activePortServer;

                if (!userFrom.equals(userTo)) {
                    Socket socket = null;
                    try {
                        socket = new Socket(adressServer, portServer);
                        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
                        socketOut.writeInt(2);
                        socketOut.writeUTF(userTo);
                        socketOut.writeUTF(userFrom);
                        socketOut.writeUTF(messageText);

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
                } else {
                    if (!hasUserList(userTo)) {
                        historyMessagess.put(userTo, createNewUser(messageText, activeUserComponent));
                        dialog.append(messageText + "\n");
                        message.setText("");
                    } else {
                        historyMessagess.put(userTo, addMessageHistory(userTo, messageText));
                        dialog.append(messageText + "\n");
                        message.setText("");
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

    private boolean hasUserList(String userTo)
    {
        boolean hasUser = false;
        for (String userName : historyMessagess.keySet()) {
            if (userTo.equals(userName)) {
                hasUser = true;
            } else
                hasUser = false;
        }
        return hasUser;
    }

    private ArrayList<String> createNewUser(String messageText, ActiveUserComponent activeUserComponent) {
        ArrayList<String> history = new ArrayList<>();
        history.add(0, messageText);
        return history;
    }

    private ArrayList<String> addMessageHistory(String userTo, String messageText) {
        ArrayList<String> history = new ArrayList<>(historyMessagess.get(userTo));
        history.add(messageText);
        return history;
    }
}
