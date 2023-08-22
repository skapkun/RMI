import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class RetroChatClientGUI extends JFrame implements ChatClientInterface {
    private ChatServerInterface chatServer;
    private JTextArea chatArea;
    private JTextField inputField;

    public RetroChatClientGUI(ChatServerInterface server) {
        this.chatServer = server;

        setTitle("Chat Retrô");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                sendMessage(message);
                inputField.setText("");
            }
        });

        setVisible(true);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        chatArea.append(message + "\n");
    }

    private void sendMessage(String message) {
        try {
            chatServer.broadcastMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ChatServerInterface chatServer = (ChatServerInterface) java.rmi.registry.LocateRegistry.getRegistry().lookup("ChatServer");
            RetroChatClientGUI clientGUI = new RetroChatClientGUI(chatServer);
            chatServer.registerClient(clientGUI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
