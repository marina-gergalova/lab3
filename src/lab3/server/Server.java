package lab3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by developer-kc3e on 14.10.16.
 */
public class Server {

    public static final int firstAction = 1;
    public static final int secondAction = 2;
    public static final int thirdAction = 3;
    public static final int fourthAction = 4;
    public static final String USER_NOT_FOUND = "User not found";
    public static final String userFound = "User found";
    public static final int PORT = 4488;

    private static final Map<String, String> registeredUsers = new HashMap<String, String>() {{
        put("Marina", "1q");
        put("Hleb", "1q");
    }};


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    DataInputStream inSocket = new DataInputStream(socket.getInputStream());
                    DataOutputStream outSocket = new DataOutputStream(socket.getOutputStream());
                    int action = inSocket.readInt();

                    if (action == firstAction) {
                        String name = inSocket.readUTF();
                        String password = inSocket.readUTF();
                        if (registeredUsers.get(name) != null) {
                            if (registeredUsers.get(name).equals(password)) {
                                outSocket.writeUTF(userFound);
                            } else
                                outSocket.writeUTF(USER_NOT_FOUND);
                        } else
                            outSocket.writeUTF(USER_NOT_FOUND);
                    }

                    if (action == secondAction) {
                        String userTo = inSocket.readUTF();
                        String userFrom = inSocket.readUTF();
                        String messageText = inSocket.readUTF();
                        if (userTo != null) {
                            outSocket.writeUTF(userFrom);
                            outSocket.writeUTF(userTo);
                            outSocket.writeUTF(messageText);
                        }
                    }

                    if (action == fourthAction) {
                        outSocket.writeInt(registeredUsers.size());
                        if (registeredUsers.size() != 0) {
                            for (String name : registeredUsers.keySet()) {
                                outSocket.writeUTF(name);
                            }
                        }
                    }
                } finally {
                    socket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }
}
