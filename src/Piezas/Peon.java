package Piezas;

import Tablero.*;

public class Peon extends Pieza
{
    private boolean primerTurno;
    public Peon(int bando, TipoPieza tipoPieza)
    {
        super(bando, tipoPieza);
        primerTurno = true;
    }

    public Peon(int bando, TipoPieza tipoPieza, int i, int j)
    {
        super(bando, tipoPieza, i, j);
        primerTurno = true;
    }

    public void calcularMovimientosB(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];

        //System.out.println("Movimientos de la pieza en " + posicion[0] + "|" + posicion[1]);
        //calcular los movimientos del peon
        if(i > 0 && casillas.getCasillas()[i-1][j] == null)
        {
            listaMovimientos.add(new Movimiento(i-1, j));
        }

        if(primerTurno == true)
        {
            if(casillas.getCasillas()[i-2][j] == null)
            {
                listaMovimientos.add(new Movimiento(i-2, j));
                primerTurno = false;
            }
        }
            
        if(clavada.estado != true)
        {
            if(i > 0 && j > 0 && casillas.getCasillas()[i-1][j-1] != null && casillas.getCasillas()[i-1][j-1].getBando() != this.bando)
            {
                listaMovimientos.add(new Movimiento(i-1, j-1));
            }
            if(i > 0 && j < 7 && casillas.getCasillas()[i-1][j+1] != null && casillas.getCasillas()[i-1][j+1].getBando() != this.bando)
            {
                listaMovimientos.add(new Movimiento(i-1, j+1));
            }
        }
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];
        //System.out.println("Movimientos de la pieza en " + posicion[0] + "|" + posicion[1]);
        //calcular los movimientos del peon
        if(i < 7 && casillas.getCasillas()[i+1][j] == null)
        {
            listaMovimientos.add(new Movimiento(i+1, j));
        }

        if(primerTurno == true)
        {
            if(casillas.getCasillas()[i+2][j] == null)
            {
                listaMovimientos.add(new Movimiento(i+2, j));
                primerTurno = false;
            }
        }

        if(clavada.estado != true)
        {
            if(i < 7 && j > 0 && casillas.getCasillas()[i+1][j-1] != null && casillas.getCasillas()[i-1][j-1].getBando() != this.bando)
            {
                listaMovimientos.add(new Movimiento(i+1, j-1));
            }
            if(i < 7 && j < 7 && casillas.getCasillas()[i+1][j+1] != null && casillas.getCasillas()[i-1][j+1].getBando() != this.bando)
            {
                listaMovimientos.add(new Movimiento(i+1, j+1));
            }
        }
    }

    public void coronar()
    {
        //codigo para coronar peon
        if(bando == 0)
        {
            if(posicion[0] == 0)
            {
                //Seleccionar nueva pieza
                
            }
        }
        else
        {
            if(posicion[0] == 7)
            {
                //Seleccionar nueva pieza

            }
        }
    }

    protected void reiniciarDirecciones()
    {
        
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "pB"; //peon blanco
        }
        return "pN"; //peon negro
    }
}