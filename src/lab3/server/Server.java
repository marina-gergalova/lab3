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
    public static final String USER_NOT_FOUND = "User not found";
    public static final String userFound = "User found";
    public static final int PORT = 4488;
    private static final Map<String, String> registredUsers = new HashMap() {{
        put("Marina", "1q");
        put("Hleb", "1q");
    }};
    private static int countUser = registredUsers.size();
    public static final String[] userNames = new String[countUser];


    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 3; i++) {
            for (String e : registredUsers.keySet()) {
                userNames[i] = e;
                i++;
            }
        }

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
                        if (registredUsers.get(name) != null) {
                            if (registredUsers.get(name).equals(password)) {
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
                            if (messageText != null) {
                                outSocket.writeUTF(userFrom);
                                outSocket.writeUTF(userTo);
                                outSocket.writeUTF(messageText);
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
