import java.util.ArrayList;

public class estado {
    private ArrayList<jugador> jugadores = new ArrayList<jugador>();
    private ArrayList<posicion> bombas = new ArrayList<posicion>();


    public estado(){};

    public estado(ArrayList<jugador> jugadores,ArrayList<posicion> bombas){
        this.jugadores = jugadores;
        this.bombas = bombas;
    };

    
    public ArrayList<posicion> getBombas() {
        return bombas;
    }

    public ArrayList<jugador> getJugadores() {
        return jugadores;
    }
}
