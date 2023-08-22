import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private List<ChatClientInterface> clients;

    public ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public void registerClient(ChatClientInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("Cliente registrado.");
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        System.out.println(message);
        for (ChatClientInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099);
            registry.rebind("ChatServer", chatServer);
            System.out.println("Servidor de Chat pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
