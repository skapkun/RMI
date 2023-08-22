import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {
    void registerClient(ChatClientInterface client) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
}
