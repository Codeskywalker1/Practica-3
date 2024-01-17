public class jugador{
   private String id;
   private String nombre;
   private posicion pos = new posicion();
 
   public jugador(String id, String nombre, posicion pos){
        this.id = id;
        this.nombre = nombre;
        this.pos = pos;
    }
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public posicion getPos() {
        return pos;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPos(posicion pos) {
        this.pos = pos;
    }
}



