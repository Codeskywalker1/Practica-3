import java.util.Scanner;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class cliente {
    private cliente() {}
    public static void actualizarTablero(int[][] tablero) {
        for (int i = 0; i < 17; i++) {
            System.out.print("\n");
            for (int j = 0; j < 15; j++) {
                switch (tablero[i][j]) {
                    case 0:
                        System.out.print("."); // Espacio vacío
                        break;
                    case 1:
                        System.out.print("P"); // Jugador
                        break;
                    case 2:
                        System.out.print("B"); // Bomba
                        break;
                    case 3:
                        System.out.print("#"); // Pared
                        break;
                    default:
                        System.out.print(tablero[i][j]); // Otros casos
                }
            }
        }
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        boolean jugando = true;
        String mov;

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            interfazServidor stub = (interfazServidor) registry.lookup("interfazServidor");

            System.out.println("\nRMI INFORMATION\n" + stub + "\n");

            System.out.println("Menú");
            System.out.println("1. Nuevo juego");
            System.out.println("2. Unirse a un juego.");

            Scanner entrada = new Scanner(System.in);
            String peticion = entrada.next();

            if (peticion.equals("1")) {
                System.out.println("Numero de jugadores en la sala");
                Integer nSala = entrada.nextInt();
                stub.nuevaPartida(nSala);
            }

            System.out.println("Escribe el nombre de usuario: ");
            String id = entrada.next();
            stub.nuevoJugador(id);

            boolean partidaLista = false;
            while (!partidaLista) {
                partidaLista = stub.partidaLista();
            }

            /*while (jugando) {
                int[][] tablero = stub.obtenerTablero();
                actualizarTablero(tablero);
                System.out.print("\n");
                System.out.println("Movimiento: Arriba=w, Izquierda=a, Abajo=s, Derecha=d");
                System.out.print("\n");
                mov = entrada.next();
                stub.movimiento(id, mov);
                actualizarTablero(tablero);
                System.out.print("\n");
            }*/

             // Hilo para actualizar el tablero cada segundo
            Thread actualizarTableroThread = new Thread(() -> {
                while (jugando) {
                    try {
                        // Limpia la consola (puede variar según el sistema operativo)
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        int[][] tablero = stub.obtenerTablero();
                        actualizarTablero(tablero);
                        System.out.print("\n");
                        System.out.println("Movimiento: Arriba=w, Izquierda=a, Abajo=s, Derecha=d");
                        System.out.print("\n");

                        Thread.sleep(1000);  // Espera 1 segundo antes de la próxima actualización
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            actualizarTableroThread.start();  // Inicia el hilo de actualización del tablero

            while (jugando) {
                mov = entrada.next();
                stub.movimiento(id, mov);
            }
            

        } catch (Exception e) {
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
