

public class tablero{
    
    public Integer LimiteLargo = 17;
    public Integer LimiteAncho = 15;

    //Nota: Inicializamos nIntegrantes con lo que se recibe del client
    private int [][] mapa = new int [LimiteLargo][LimiteAncho];
    
    public tablero(){
        inicializarTablero();
    }

    public int[][] getTablero(){
        return mapa;
    }

    public void inicializarTablero(){
        System.out.println("Mapa iniciado");
        //Nota: rellenamos de 0 para ver el mapa 
        for(int i=0; i<LimiteLargo; i++){
            for(int j =0; j<LimiteAncho; j++){
                mapa[i][j] = 0;
            }
        }   
    }

    public Integer getLimiteAncho() {
        return LimiteAncho;
    }

    public Integer getLimiteLargo() {
        return LimiteLargo;
    }
}