package Piezas;

import Tablero.Movimiento;
import Tablero.Tablero;

public class Alfil extends Pieza
{
    public Alfil(int bando, TipoPieza tipoPieza)
    {
        super(bando, tipoPieza);
    }

    public Alfil(int bando, TipoPieza tipoPieza, int i, int j)
    {
        this.bando = bando;
        posicion[0] = i;
        posicion[1] = j;
        this.tipoPieza = tipoPieza;
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovmientosB(casillas);
    }

    public void calcularMovmientosB(Tablero casillas)
    {
        System.out.println("La pieza es blanca");
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        /*  
            Heuristica en diagonal:   
            1.- Arriba a la derecha     
            2.- Abajo a la derecha      
            3.- Abajo a la izquierda    
            4.- Arriba a la izquierda   
        */

        //Arriba a la derecha
        movi -= 1;
        movj += 1;
        //System.out.println("DEBUG: ARRIBA A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movi > -1 && movj < 8)
        {
            if(movi < 0 && movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba a la derecha");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] == null)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
            }
            else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
                break;
            }
            movi -= 1;
            movj += 1;
        }
        
        //Abajo a la derecha
        movi = i+1;
        movj = j+1;
        //System.out.println("DEBUG: ABAJO A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movi < 8 && movj < 8)
        {
            if(movi > 7 && movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo a la derecha");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] == null)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
            }
            else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
                break;
            }
            movi += 1;
            movj += 1;
        }

        //Abajo a la izquierda
        movi = i+1;
        movj = j-1;
        //System.out.println("DEBUG: ABAJO A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movi < 8 && movj > -1)
        {
            if(movi > 7 && movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo a la izquierda");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] == null)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
            }
            else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
                break;
            }
            movi += 1;
            movj -= 1;
        }

        //Arriba a la izquierda
        movi = i-1;
        movj = j-1;
        //System.out.println("DEBUG: ARRIBA A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movi > -1 && movj > -1)
        {
            if(movi < 0 && movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba a la izquierda");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] == null)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
            }
            else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, movj));
                break;
            }
            movi -= 1;
            movj -= 1;
        }
    }

    public void calcularMovmientosN(Tablero casillas){}

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "aB"; //alfil blanco
        }
        return "aN"; //alfil negro
    } 
}