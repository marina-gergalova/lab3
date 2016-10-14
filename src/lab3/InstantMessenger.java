package lab3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer-kc3e on 13.10.16.
 */
public class InstantMessenger {
    private String sender;

    private List<MessageListener> listeners = new ArrayList<>();


    public void addMessageListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeMessageListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void sendMessage(String senderName, String destinationAddress,
                            String message) throws UnknownHostException, IOException {
// Создаем сокет для соединения
        final Socket socket = new Socket(destinationAddress, MainFrame.SERVER_PORT);
// Открываем поток вывода данных
        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
// Записываем в поток имя
        out.writeUTF(senderName);
// Записываем в поток сообщение
        out.writeUTF(message);
// Закрываем сокет
        socket.close();
// Помещаем сообщения в текстовую область вывода

    }

    ;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private void notifyListeners(String sender, String message) {
        synchronized (listeners) {
            for (MessageListener listener : listeners) {
                listener.messageReceived(sender, message);
            }
        }
    }

    private void startServer() {

    }
}
