import PatronComando.Jugadas;
import Tablero.Tablero;

public class main 
{
    public static void main(String[] args) throws Exception 
    {
        Tablero tablero = new Tablero();
        Jugadas jugadas = new Jugadas(tablero);

        jugadas.ejecutar();
        tablero.getCasillas()[6][0].mostrarMovimientos();
        tablero.getCasillas()[7][0].mostrarMovimientos();
        tablero.getCasillas()[7][1].mostrarMovimientos();
        tablero.getCasillas()[7][2].mostrarMovimientos();
        tablero.getCasillas()[7][3].mostrarMovimientos();
        tablero.getCasillas()[7][4].mostrarMovimientos();
    }
}