import PatronComando.Jugadas;
import Piezas.*;
import Tablero.Tablero;
import java.util.Scanner;

public class main 
{
    public static void main(String[] args) throws Exception 
    {
        Scanner scanner = new Scanner(System.in);

        Tablero tablero = new Tablero();
        Jugadas jugadas = new Jugadas(tablero);

        /*tablero.getCasillas()[7][4] = new Rey(0, 7, 4);
        tablero.getCasillas()[1][1] = new Rey(1, 1, 1);
        Rey reyPruebas = (Rey) tablero.getCasillas()[7][4];*/

        // Prueba 1 - Pasada
        /*tablero.getCasillas()[6][4] = new Peon(0, 6, 4);
        tablero.getCasillas()[2][5] = new Peon(1, 2, 5);
        tablero.getCasillas()[0][4] = new Torre(1, 0, 4);*/

        // Prueba 2 - Pasada
        /*tablero.getCasillas()[7][5] = new Torre(1, 7, 5);
        tablero.getCasillas()[5][4] = new Caballo(0, 5, 4);*/

        // Prueba 3 - Pasada
        /*tablero.getCasillas()[0][0] = new Torre(1, 0, 0);
        tablero.getCasillas()[0][7] = new Torre(1, 0, 7);
        tablero.getCasillas()[7][0] = new Torre(0, 7, 0);
        tablero.getCasillas()[7][7] = new Torre(0, 7, 7);
        tablero.getCasillas()[5][5] = new Caballo(1, 5, 5);*/

        // Prueba 4 - Pasada
        /*tablero.getCasillas()[0][0] = new Torre(0, 0, 0);
        tablero.getCasillas()[0][7] = new Torre(0, 0, 7);
        tablero.getCasillas()[7][0] = new Torre(0, 7, 0);
        tablero.getCasillas()[7][7] = new Torre(0, 7, 7);
        tablero.getCasillas()[5][4] = new Torre(1, 5, 4);
        tablero.getCasillas()[5][6] = new Dama(1, 5, 6);*/

        // Prueba 5 - Pasada
        /*tablero.getCasillas()[6][4] = new Torre(0, 6, 4);
        tablero.getCasillas()[0][4] = new Torre(1, 0, 4);*/

        // Prueba 6 - Pasada
        /*tablero.getCasillas()[7][7] = new Rey(0, 7, 7);
        tablero.getCasillas()[5][7] = new Rey(1, 5, 7);
        tablero.getCasillas()[5][6] = new Dama(1, 5, 6);
        Rey reyPruebas = (Rey) tablero.getCasillas()[7][7];*/


        System.out.println("\n --- Simulación de juego --- \n");
        int i, j;
        String columna;

        while(true)
        {
            jugadas.ejecutar();

            //System.out.println("Estado del jaque del rey de pruebas: " + reyPruebas.getJaque());
            //System.out.println("Estado del ahogado del rey de pruebas: " + reyPruebas.getAhogado());

            System.out.print("Ingrese la pieza a mover (fila): ");
            i = scanner.nextInt();
            i = 8 - i;
            scanner.nextLine();

            System.out.print("Ingrese la pieza a mover (columna): ");
            columna = scanner.nextLine();

            j = (int) columna.charAt(0);
            j -= 65;

            if(i == -1 && columna.charAt(0) == 'X')
            {
                System.out.println("Adios");
                break;
            }

            Pieza pieza = tablero.getCasillas()[i][j];

            pieza.mostrarMovimientos();
            
            System.out.print("Ingrese la posición a mover (fila): ");
            i = scanner.nextInt();
            i = 8 - i;
            scanner.nextLine();

            System.out.print("Ingrese la posición a mover (columna): ");
            columna = scanner.nextLine();

            j = (int) columna.charAt(0);
            j -= 65;

            pieza.mover(i, j, tablero);
        }
    }
}