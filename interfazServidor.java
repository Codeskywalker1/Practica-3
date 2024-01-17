import java.rmi.RemoteException;
import java.rmi.Remote;
public interface interfazServidor extends Remote {

    public void nuevaPartida(Integer nInregrantes) throws RemoteException;

    public void nuevoJugador(String nombre) throws RemoteException;

    public boolean partidaLista() throws RemoteException;

    public void movimiento(String id, String pos) throws RemoteException;

    public estado obtenerEstado() throws RemoteException;

    public int[][] obtenerTablero() throws RemoteException;
    
}

