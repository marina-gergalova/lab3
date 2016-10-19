package lab3.Frames;

import lab3.server.Server;
import javax.swing.*;

import java.awt.*;

import static lab3.server.Server.userNames;

/**
 * Created by developer-kc3e on 19.10.16.
 */
public class ActiveUserComponent extends JScrollPane{

    public static void main() {
        ActiveUserComponent frame = new ActiveUserComponent();

    }

    public ActiveUserComponent() {
        super(new JPanel());
        String[] activeUsers = new String[2];
        activeUsers[0]="Marina";
        activeUsers[1]="Hleb";
        JList<String> activeUsersComponent = new JList<>();
        activeUsersComponent.setListData(activeUsers);
        activeUsersComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((JPanel)this.getViewport().getView()).add(activeUsersComponent);

    }
}
