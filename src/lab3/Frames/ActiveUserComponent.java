package lab3.Frames;

import javax.swing.*;


public class ActiveUserComponent extends JScrollPane {

    private JList<String> activeUsersComponent = new JList<>();

    public ActiveUserComponent() {
        super(new JPanel());
        String[] activeUsers = new String[2];
        activeUsers[0] = "Marina";
        activeUsers[1] = "Hleb";
        activeUsersComponent.setListData(activeUsers);
        activeUsersComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((JPanel) this.getViewport().getView()).add(activeUsersComponent);
    }

    public String getSelectedUser() {
        return activeUsersComponent.getSelectedValue();
    }
}
