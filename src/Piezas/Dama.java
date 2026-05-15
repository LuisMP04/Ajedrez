package Piezas;

import Tablero.Movimiento;
import Tablero.Tablero;

public class Dama extends Pieza
{
    public Dama(int bando, TipoPieza tipoPieza)
    {
        super(bando, tipoPieza);
    }

    public Dama(int bando, TipoPieza tipoPieza, int i, int j)
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

    @Override
    public void calcularMovmientosB(Tablero casillas)
    {
        System.out.println("La pieza es blanca");
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        /*  
            Heuristica en horizontal:   
            1.- Arriba    
            2.- A la derecha      
            3.- A la izquierda 
            4.- Abajo   
        */

        //Arriba
        movi -= 1;
        //System.out.println("DEBUG: ARRIBA | i: " + movi + ", j: " + movj);
        while(movi > -1)
        {
            if(movi < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba");
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][j] == null)
            {
                listaMovimientos.add(new Movimiento(movi, j));
            }
            else if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, j));
                break;
            }
            movi -= 1;
        }
        
        //A la derecha
        movj = j+1;
        //System.out.println("DEBUG: A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movj < 8)
        {
            if(movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles a la derecha");
                break;
            }

            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[i][movj] == null)
            {
                listaMovimientos.add(new Movimiento(i, movj));
            }
            else if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(i, movj));
                break;
            }
            movj += 1;
        }

        //A la izquierda
        movj = j-1;
        //System.out.println("DEBUG: A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movj > -1)
        {
            if(movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles a la izquierda");
                break;
            }
            
            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[i][movj] == null)
            {
                listaMovimientos.add(new Movimiento(i, movj));
            }
            else if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(i, movj));
                break;
            }
            movj -= 1;
        }

        //Abajo
        movi = i+1;
        //System.out.println("DEBUG: ABAJO | i: " + movi + ", j: " + movj);
        while(movi < 8)
        {
            if(movi > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo");
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][j] == null)
            {
                listaMovimientos.add(new Movimiento(movi, j));
            }
            else if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando != this.bando)
            {
                listaMovimientos.add(new Movimiento(movi, j));
                break;
            }
            movi += 1;
        }

        /*  
            Heuristica en diagonal:   
            1.- Arriba a la derecha     
            2.- Abajo a la derecha      
            3.- Abajo a la izquierda    
            4.- Arriba a la izquierda   
        */

        //Arriba a la derecha
        movi = i-1;
        movj = j+1;
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
            return "dB"; //dama blanca
        }
        return "dN"; //dama negra
    }
}