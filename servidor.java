import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class servidor implements interfazServidor {
    public tablero mapa = new tablero();
    ArrayList<jugador> jugadores = new ArrayList<jugador>();
    ArrayList<posicion> bombas = new ArrayList<posicion>();
    Integer contador = 0;
    Integer nIntegrantes = 0;

    public void nuevaPartida(Integer nIntegrantes){
        this.nIntegrantes = nIntegrantes;
    }

    public void nuevoJugador(String nombre) {
        // Asignar una posición al nuevo jugador
        posicion posInicial = new posicion();
        posInicial.setPosX((int) (Math.random() * mapa.getAltura() + 1));
        posInicial.setPosY((int) (Math.random() * mapa.getAncho() + 1));
        jugador nuevo = new jugador(nombre, nombre, posInicial);
        jugadores.add(nuevo);
        contador += 1;
        mapa.getTablero()[posInicial.getPosX()][posInicial.getPosY()] = 1;
        System.out.println("posX" + posInicial.getPosX() + "posY" + posInicial.getPosY());
    }

    public boolean partidaLista() {
        // Devuelve verdadero solo cuando la sala de jugadores ya está llena
        return contador.equals(nIntegrantes);
    }

    public void movimiento(String id, String direccion) {
        for (int m = 0; m < jugadores.size(); m++) {
            if (id.equals(jugadores.get(m).getId())) {
                posicion aux = jugadores.get(m).getPos();
                if (direccion.equals("w")) {
                    moverJugador(m, aux.getPosX() - 1, aux.getPosY());
                } else if (direccion.equals("a")) {
                    moverJugador(m, aux.getPosX(), aux.getPosY() - 1);
                } else if (direccion.equals("s")) {
                    moverJugador(m, aux.getPosX() + 1, aux.getPosY());
                } else if (direccion.equals("d")) {
                    moverJugador(m, aux.getPosX(), aux.getPosY() + 1);
                }
            }
        }
    }

    public void moverJugador(int index, int nuevoX, int nuevoY) {
        if (esPosicionValida(nuevoX, nuevoY)) {
            mapa.getTablero()[jugadores.get(index).getPos().getPosX()][jugadores.get(index).getPos().getPosY()] = 0;
            jugadores.get(index).getPos().setPosX(nuevoX);
            jugadores.get(index).getPos().setPosY(nuevoY);
            mapa.getTablero()[nuevoX][nuevoY] = 1;
        }
    }

    public boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < mapa.getAncho() && y >= 0 && y < mapa.getAltura();
    }

    public estado obtenerEstado() {
        // Devuelve el estado de todos los jugadores
        return new estado(jugadores, bombas);
    }

    public int[][] obtenerTablero() {
        return mapa.getTablero();
    }



    public static void main(String args[]) {
        try {
            servidor obj = new servidor();
            interfazServidor stub = (interfazServidor) UnicastRemoteObject.exportObject(obj, 0);

            // Agrega el stub del objeto remoto al registro RMI
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("interfazServidor", stub);
            System.err.println("Servidor listo");
        } catch (Exception e) {
            System.err.println("Servidor exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

