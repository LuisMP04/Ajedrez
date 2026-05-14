import PatronComando.Jugadas;
import Piezas.*;
import Tablero.Tablero;

public class main 
{
    public static void main(String[] args) throws Exception 
    {
        Tablero tablero = new Tablero();
        tablero.getCasillas()[3][3] = new Rey(0, 3, 3);
        Rey reyPruebas = (Rey)tablero.getCasillas()[3][3];
        Jugadas jugadas = new Jugadas(tablero);

        jugadas.actualizarMovimientos();
        tablero.mostrarTablero();
        tablero.getCasillas()[3][3].mostrarMovimientos();
        reyPruebas.calcularAreaPeligrosa(tablero);
        reyPruebas.mostrarAreaPeligrosa();
    }
}