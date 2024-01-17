import java.util.Scanner;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class cliente{
    private cliente(){}

    public static void actualizarTablero(int[][] tablero){
        for(int i=0; i<17; i++){
            System.out.print("\n");
            for(int j=0;j<15;j++){
                System.out.print(tablero[i][j]);
            }
        }
    }

    public static void main(String[] args){

        String host = (args.length < 1) ? null : args[0];
        boolean jugando = true;
        String mov;

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            interfazServidor stub = (interfazServidor) registry.lookup("interfazServidor");

            System.out.println("\nRMI INFORMATION\n" + stub + "\n");

            System.out.println("Menú");
            System.out.println("1. Nuevo juego");
            System.out.println("2. Unirse a un juego."); //Pendiente
            //System.out.println("3. Salir");
            Scanner entrada = new Scanner(System.in);
            String peticion = entrada.next();
            
            if ( peticion.equals("1") ){
                System.out.println("Numero de jugadores en la sala");
                Integer nSala = entrada.nextInt();
                stub.nuevaPartida(nSala);
            } 

            System.out.println("Escribe el nombre de usuario: ");
            String id = entrada.next();
            stub.nuevoJugador(id);
            
            boolean partida_lista = false;
            while(!partida_lista){
                partida_lista= stub.partidaLista();
            }
            
            while(jugando){
                int[][] tablero = stub.obtenerTablero();
                actualizarTablero(tablero);
                System.out.print("\n");
                System.out.println("Movimiento: Arriba=w, Izquierda=a, Abajo=s, Derecha=d");
                System.out.print("\n");
                mov= entrada.next();
                stub.movimiento(id, mov);
                actualizarTablero(tablero);
                System.out.print("\n");
            }

        }catch(Exception e){
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }
    
    }
}
