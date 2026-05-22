package Tablero;

import Piezas.*;

public class Tablero 
{
    private Pieza[][] casillas = new Pieza[8][8];

    public Tablero()
    {
        //inicializar el tablero'
        //peones
        for(int j = 0; j < 8; j++)
        {
            casillas[1][j] = new Peon(1, 1, j);
            casillas[6][j] = new Peon(0, 6, j);
        }

        //alfiles
        casillas[0][2] = new Alfil(1, 0, 2);
        casillas[0][5] = new Alfil(1, 0, 5);
        casillas[7][2] = new Alfil(0, 7, 2);
        casillas[7][5] = new Alfil(0, 7, 5);

        //torres
        casillas[0][0] = new Torre(1, 0, 0);
        casillas[0][7] = new Torre(1, 0, 7);
        casillas[7][0] = new Torre(0, 7, 0);
        casillas[7][7] = new Torre(0, 7, 7);

        //damas
        casillas[0][3] = new Dama(1, 0, 3);
        casillas[7][3] = new Dama(0,  7, 3);

        //caballos
        casillas[0][1] = new Caballo(1, 0, 1);
        casillas[0][6] = new Caballo(1, 0, 6);
        casillas[7][1] = new Caballo(0, 7, 1);
        casillas[7][6] = new Caballo(0, 7, 6);

        //reyes
        casillas[0][4] = new Rey(1, 0, 4);
        casillas[7][4] = new Rey(0, 7, 4);
    }
    
    public Pieza[][] getCasillas()
    {
        return casillas;
    }

    public Pieza getPieza(int i, char j)
    {
        int columna = (int)j-65;
        return casillas[8-i][columna];
    }

    public void mostrarTablero()
    {
        System.out.print("|x|");
        int i, j;
        for(i = 0; i < 8; i++)
        {
            System.out.print("[ " + (char)(i+65) + "]");
        }
        System.out.println("|x|");

        for(i = 0; i < 8; i++)
        {
            System.out.print("|" + (8-i) + "|");
            for(j = 0; j < 8; j++)
            {
                if(casillas[i][j] == null)
                {
                    System.out.print("[**]");
                }
                else
                {
                    System.out.print("[" + casillas[i][j] + "]");
                }
            }
            System.out.println("|" + (8-i) + "|");
        }

        System.out.print("|x|");
        for(i = 0; i < 8; i++)
        {
            System.out.print("[ " + (char)(i+65) + "]");
        }
        System.out.println("|x|");

        System.out.println("\n");
    }
}