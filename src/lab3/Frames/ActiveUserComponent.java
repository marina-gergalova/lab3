package lab3.Frames;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class ActiveUserComponent extends JScrollPane {

    private JList<String> activeUsersComponent = new JList<>();

    public ActiveUserComponent(String iPAddress, int activePortServer) {
        super(new JPanel());
        String addressServer = iPAddress;
        int portServer = activePortServer;

        String[] activeUsers = getActiveUsers(addressServer, portServer);
        activeUsersComponent.setListData(activeUsers);
        activeUsersComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((JPanel) this.getViewport().getView()).add(activeUsersComponent);
        activeUsersComponent.setSelectedIndex(0);
    }

    public String getSelectedUser() {
        return activeUsersComponent.getSelectedValue();
    }

    private String[] getActiveUsers(String adressServer, int portServer) {
        Socket socket = null;

        try {
            socket = new Socket(adressServer, portServer);
            DataInputStream socketIn = new DataInputStream(socket.getInputStream());
            DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
            socketOut.writeInt(4);
            int countUsers = socketIn.readInt();
            if (countUsers != 0) {
                String[] activeUsersOnServer = new String[countUsers];
                for (int i = 0; i < countUsers; i++) {
                    activeUsersOnServer[i] = socketIn.readUTF();
                }
                return activeUsersOnServer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public void addSelectionListener(ListSelectionListener listener) {
        activeUsersComponent.addListSelectionListener(listener);
    }
}
