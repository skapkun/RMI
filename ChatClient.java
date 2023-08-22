import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {
    private String username;

    protected ChatClient(String username) throws RemoteException {
        this.username = username;
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(username + ": " + message);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ChatClient pronto");
            return;
        }

        String username = args[0];

        try {
            ChatClient chatClient = new ChatClient(username);
            ChatServerInterface chatServer = (ChatServerInterface) java.rmi.registry.LocateRegistry.getRegistry().lookup("rmi://127.0.0.1/ChatServer");
            chatServer.registerClient(chatClient);
 
             
            chatServer.broadcastMessage(username + " entrou no chat!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
