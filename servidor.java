import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class servidor implements interfazServidor{
    //static tablero mapa;
    public tablero mapa = new tablero(); 
    ArrayList<jugador> jugadores = new ArrayList<jugador>();
    ArrayList<posicion> bombas = new ArrayList<posicion>();
    Integer contador = 0;
    Integer nIntegrantes = 0;

    public void nuevaPartida(Integer nIntegrantes){
        this.nIntegrantes = nIntegrantes;
    }

    public void nuevoJugador(String nombre){
        //Nota: se asigna una posicion al nuevo jugador 
        posicion posInicial = new posicion();
        posInicial.setPosX( (int)(Math.random()*mapa.getLimiteAncho()+1));
        posInicial.setPosY( (int)(Math.random()*mapa.getLimiteLargo()+1));
        jugador nuevo = new jugador(nombre, nombre, posInicial);
        jugadores.add(nuevo);
        contador+=1;
        mapa.getTablero()[posInicial.getPosX()][posInicial.getPosY()]= 1;
        System.out.println("posX" + posInicial.getPosX()+ "posY"+posInicial.getPosY());
    }

    public boolean partidaLista(){
        //Nota: regresa verdadero solo cuando la sala de jugadores ya esta llena
        if ( contador.equals(nIntegrantes) ){ 
            return true;
        }
        else {
            return false;
        }
    }

    public void movimiento(String id, String pos){
        
        for(int m=0; m<jugadores.size(); m++){
            if(id.equals(jugadores.get(m).getId())){
                posicion aux = jugadores.get(m).getPos();
                if(pos.equals("w")){
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 0;
                    jugadores.get(m).getPos().setPosX(aux.getPosX()- 1);
                    jugadores.get(m).getPos().setPosY(aux.getPosY());
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 1;
                } else if(pos.equals("a")){
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 0;
                    jugadores.get(m).getPos().setPosX(aux.getPosX());
                    jugadores.get(m).getPos().setPosY(aux.getPosY()-1);
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 1;
                } else if(pos.equals("s")){
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 0;
                    jugadores.get(m).getPos().setPosX(aux.getPosX()+ 1);
                    jugadores.get(m).getPos().setPosY(aux.getPosY());
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 1;
                } else if(pos.equals("d")){
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 0;
                    jugadores.get(m).getPos().setPosX(aux.getPosX());
                    jugadores.get(m).getPos().setPosY(aux.getPosY() + 1);
                    mapa.getTablero()[jugadores.get(m).getPos().getPosX()][jugadores.get(m).getPos().getPosY()]= 1;
                }
            }
        }
    }

    public estado obtenerEstado(){
        //Nota: regresa el estado de todos los jugadores
        return new estado(jugadores, bombas);
    }

    public int[][] obtenerTablero(){
        return mapa.getTablero();
    }

    public static void main(String args[]) {
        try {
            servidor obj = new servidor();
            interfazServidor stub = (interfazServidor) UnicastRemoteObject.exportObject(obj, 0);
            
            //tablero = new Tablero();
            // Agrega el stud del objeto remoto al registro RMI
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("interfazServidor", stub);
            System.err.println("Servidor listo");
        // FIN del try 
        } catch (Exception e) {
            System.err.println("Servidor exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
