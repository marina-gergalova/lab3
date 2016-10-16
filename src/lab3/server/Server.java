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
    private static final Map<String, String> registredUsers = new HashMap(){{
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
                    String name = inSocket.readUTF();
                    String password = inSocket.readUTF();

                    if (action == firstAction) {
                        if (registredUsers.get(name) != null) {
                            if (registredUsers.get(name).equals(password)) {
                                outSocket.writeUTF(userFound);
                            }
                        } else
                            outSocket.writeUTF(USER_NOT_FOUND);
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
