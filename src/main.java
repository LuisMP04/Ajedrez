import PatronComando.Jugadas;
import Tablero.Tablero;

public class main 
{
    public static void main(String[] args) throws Exception 
    {
        Tablero tablero = new Tablero();
        Jugadas jugadas = new Jugadas(tablero);

        jugadas.ejecutar();

        tablero.getCasillas()[7][4].mostrarMovimientos();
    }
}