import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class clientePrueba {
    private static boolean jugando = true;

    public static void actualizarTablero(int[][] tablero) {
        for (int i = 0; i < 17; i++) {
            System.out.print("\n");
            for (int j = 0; j < 15; j++) {
                System.out.print(tablero[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            interfazServidor stub = (interfazServidor) registry.lookup("interfazServidor");

            System.out.println("\nRMI INFORMATION\n" + stub + "\n");

            System.out.println("Menú");
            System.out.println("1. Nuevo juego");
            System.out.println("2. Unirse a un juego."); // Pendiente

            String peticion;

            try (Terminal terminal = TerminalBuilder.builder().build()) {
                LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
                peticion = reader.readLine();
            } catch (UserInterruptException e) {
                peticion = ""; // Tratar interrupciones del usuario
            }

            if (peticion.equals("1")) {
                System.out.println("Numero de jugadores en la sala");
                Integer nSala;
                try (Terminal terminal = TerminalBuilder.builder().build()) {
                    LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
                    nSala = Integer.parseInt(reader.readLine());
                } catch (UserInterruptException | NumberFormatException e) {
                    nSala = 0;
                }
                stub.nuevaPartida(nSala);
            }

            System.out.println("Escribe el nombre de usuario: ");
            String id;

            try (Terminal terminal = TerminalBuilder.builder().build()) {
                LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
                id = reader.readLine();
            } catch (UserInterruptException e) {
                id = ""; // Tratar interrupciones del usuario
            }

            stub.nuevoJugador(id);

            boolean partidaLista = false;
            while (!partidaLista) {
                partidaLista = stub.partidaLista();
            }

            while (jugando) {
                int[][] tablero = stub.obtenerTablero();
                actualizarTablero(tablero);
                System.out.print("\n");
                System.out.println("Movimiento: Arriba=w, Izquierda=a, Abajo=s, Derecha=d");
                System.out.print("\n");

                String mov;

                try (Terminal terminal = TerminalBuilder.builder().build()) {
                    LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
                    mov = reader.readLine();
                } catch (UserInterruptException e) {
                    mov = ""; // Tratar interrupciones del usuario
                }

                stub.movimiento(id, mov);
                actualizarTablero(tablero);
                System.out.print("\n");
            }

        } catch (Exception e) {
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
