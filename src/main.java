import PatronComando.Jugadas;
import Piezas.*;
import Tablero.Tablero;

public class main 
{
    public static void main(String[] args) throws Exception 
    {
        Tablero tablero = new Tablero();
        tablero.getCasillas()[5][3] = new Rey(0,TipoPieza.REY, 5, 3);
        tablero.getCasillas()[2][7] = new Rey(1, TipoPieza.REY, 2, 7);
        //tablero.getCasillas()[4][4] = new Alfil(0, TipoPieza.ALFIL, 4, 4);
        //tablero.getCasillas()[2][6] = new Alfil(1, TipoPieza.ALFIL, 2, 6);
        tablero.getCasillas()[2][5] = new Torre(1, TipoPieza.DAMA, 2, 5);
        tablero.getCasillas()[0][3] = new Torre(1, TipoPieza.TORRE, 0, 3);
        //tablero.getCasillas()[2][3] = new Torre(0, TipoPieza.TORRE, 2, 3);
        tablero.getCasillas()[2][3] = new Dama(0, TipoPieza.DAMA, 2, 3);
        
        Rey reyPruebas = (Rey)tablero.getCasillas()[5][3];
        Jugadas jugadas = new Jugadas(tablero);

        //jugadas.actualizarMovimientos();
        tablero.mostrarTablero();
        //tablero.getCasillas()[5][3].mostrarMovimientos();
        reyPruebas.invalidarCasillasAtacadas(tablero);
        //tablero.getCasillas()[5][3].mostrarMovimientos();

        //jugadas.reiniciarMovimientos();
        System.out.println("\n--- Calcular piezas clavadas---\n");
        jugadas.actualizarMovimientos();
        //tablero.getCasillas()[4][4].mostrarMovimientos();
        
        //Clavar la torre en 6G
        //Torre torrePruebas = (Torre) tablero.getCasillas()[2][3];
        Dama damaPruebas = (Dama) tablero.getCasillas()[2][3];
        //torrePruebas.clavarPiezaEnemiga(tablero);
        damaPruebas.clavarPiezaEnemiga(tablero);
        System.out.println();
        //Mostrar movimientos despues de ser clavada
        jugadas.reiniciarMovimientos();
        jugadas.actualizarMovimientos();
        System.out.println(tablero.getCasillas()[2][5].getClavada().estado);
        tablero.getCasillas()[2][5].mostrarMovimientos();
        
        Torre torrePruebas2 = (Torre) tablero.getCasillas()[0][3];
        torrePruebas2.clavarPiezaEnemiga(tablero);
        System.out.println();

        jugadas.reiniciarMovimientos();
        jugadas.actualizarMovimientos();
        System.out.println(tablero.getCasillas()[2][3].getClavada().estado);
        tablero.getCasillas()[2][3].mostrarMovimientos();

        /*System.out.println(tablero.getCasillas()[4][4].getClavada().estado);
        tablero.getCasillas()[4][4].mostrarMovimientos();

        Alfil alfilPruebas = (Alfil) tablero.getCasillas()[2][6];
        alfilPruebas.clavarPiezaEnemiga(tablero);

        jugadas.reiniciarMovimientos();
        jugadas.actualizarMovimientos();
        System.out.println(tablero.getCasillas()[4][4].getClavada().estado);
        tablero.getCasillas()[4][4].mostrarMovimientos();*/

        //tablero.getCasillas()[2][6].mostrarMovimientos();
        

        //tablero.getCasillas()[2][3].mostrarMovimientos();
    }
}