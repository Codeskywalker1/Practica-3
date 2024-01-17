public class tablero {
    
    public Integer altura = 17;
    public Integer ancho = 15;

    private int[][] mapa = new int[altura][ancho]; //opcional final
    
    public tablero(){
        inicializarTablero();
    }

    public int[][] getTablero(){
        return mapa;
    }

    // Crear Tablero con 0
    public void inicializarTablero(){
        
        System.out.println("Mapa iniciado");
        for(int i = 0; i < altura; i++){
            for(int j = 0; j < ancho; j++){
                mapa[i][j] = 0;
            }
        }   
    }

    //Getters

    public Integer getAltura() {
        return ancho;
    }

    public Integer getAncho() {
        return altura;
    }
}