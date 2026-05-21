package Piezas;

import Tablero.Movimiento;
import Tablero.Tablero;

public class Caballo extends Pieza
{
    public Caballo(int bando, TipoPieza tipoPieza)
    {
        super(bando, tipoPieza);
    }

    public Caballo(int bando, TipoPieza tipoPieza, int i, int j)
    {
        super(bando, tipoPieza, i, j);
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    public void calcularMovimientosB(Tablero casillas)
    {
        // Si el caballo está clavado, no se puede mover
        if(clavada.estado == true)
        {
            return;
        }
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        //j = 2;
        for(movi = 1; movi <= 2; movi++)
        {
            for(movj = 2; movj >= 1; movj--)
            {
                if(movi == movj)
                {
                    continue;
                }

                //Abajo y derecha (+i +j)
                if((i+movi <= 7 && j+movj <= 7) &&
                    casillas.getCasillas()[i+movi][j+movj] == null)
                {
                    listaMovimientos.add(new Movimiento(i+movi, j+movj));
                }
                else if((i+movi <= 7 && j+movj <= 7) &&
                        casillas.getCasillas()[i+movi][j+movj] != null && casillas.getCasillas()[i+movi][j+movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(i+movi, j+movj));
                }

                //Abajo e izquierda (+i -j)
                if((i+movi <= 7 && j-movj >= 0) &&
                    casillas.getCasillas()[i+movi][j-movj] == null)
                {
                    listaMovimientos.add(new Movimiento(i+movi, j-movj));
                }
                else if((i+movi <= 7 && j-movj >= 0) &&
                        casillas.getCasillas()[i+movi][j-movj] != null && casillas.getCasillas()[i+movi][j-movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(i+movi, j-movj));
                }

                //Arriba e izquierda (-i -j)
                if((i-movi >= 0 && j-movj >= 0) &&
                    casillas.getCasillas()[i-movi][j-movj] == null)
                {
                    listaMovimientos.add(new Movimiento(i-movi, j-movj));
                }
                else if((i-movi >= 0 && j-movj >= 0) &&
                        casillas.getCasillas()[i-movi][j-movj] != null && casillas.getCasillas()[i-movi][j-movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(i-movi, j-movj));
                }

                //Arriba y derecha (-i +j)
                if((i-movi >= 0 && j+movj <= 7) &&
                    casillas.getCasillas()[i-movi][j+movj] == null)
                {
                    listaMovimientos.add(new Movimiento(i-movi, j+movj));
                }
                else if((i-movi >= 0 && j+movj <= 7) &&
                        casillas.getCasillas()[i-movi][j+movj] != null && casillas.getCasillas()[i-movi][j+movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(i-movi, j+movj));
                }
            }
        }
    }

    public void calcularMovimientosN(Tablero casillas){}

    protected void reiniciarDirecciones()
    {
        
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "cB"; //alfil blanco
        }
        return "cN"; //alfil negro
    } 
}
