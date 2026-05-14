package Piezas;

import Reglas.Jaqueable;
import Tablero.*;
import java.util.ArrayList;

public abstract class Pieza 
{
    protected int[] posicion = new int[2];
    protected ArrayList<Movimiento> listaMovimientos;
    protected int bando; //0 = blanco, 1 = negro;
    protected TipoPieza tipoPieza;

    public Pieza()
    {
        listaMovimientos = new ArrayList<>();
    }

    public Pieza(int bando, TipoPieza tipoPieza)
    {
        posicion[0] = 0;
        posicion[1] = 0;
        listaMovimientos = new ArrayList<>();
        this.bando = bando;
        this.tipoPieza = tipoPieza;
    }

    public Pieza(int bando, TipoPieza tipoPieza, int i, int j)
    {
        posicion[0] = 0;
        posicion[1] = 0;
        listaMovimientos = new ArrayList<>();
        this.bando = bando;
        this.tipoPieza = tipoPieza;
    }

    public int getBando()
    {
        return bando;
    }

    public int[] getPosiciones()
    {
        return posicion;
    }

    public void mover(int posX, int posY, Tablero casillas)
    {
        boolean valido = false;
        for(Movimiento m : listaMovimientos)
        {
            if(m.i == posX && m.j == posY)
            {
                valido = true;
                break;
            }
        }

        if(valido)
        {
            //Creo que lo que va a funcionar es hacer que los movimientos sí se calculen en cuanto se mueve la pieza
            //Para así poder poner en jaque al rey automaticamente
            //Ademas, las demas reglas como peon al paso o el enroque también irán en la carpeta de reglas, y serán una interfaz

            int i = posicion[0];
            int j = posicion[1];
            posicion[0] = posX;
            posicion[1] = posY;
            casillas.getCasillas()[posX][posY] = this;
            reiniciarMovimientos();
            casillas.getCasillas()[i][j] = null;
        }
        else
        {
            System.out.println("Movimiento a " + (8 - posX) + "|" + (char)(posY+65) + " no valido");
        }
    }

    public abstract void calcularMovmientosB(Tablero casillas);
    public abstract void calcularMovmientosN(Tablero casillas);
    public void actualizarMovimientos(Tablero casillas)
    {
        if(listaMovimientos.isEmpty() && bando == 0)
        {
            calcularMovmientosB(casillas);
        }
        else if(listaMovimientos.isEmpty() && bando == 1)
        {
            calcularMovmientosN(casillas);
        }
    }

    public void reiniciarMovimientos()
    {
        listaMovimientos.clear();
    }

    public void mostrarMovimientos()
    {
        System.out.println("Movimientos de la pieza " + this + " en " + (8 - posicion[0]) + "|" + (char)(posicion[1]+65));
        for(Movimiento m : listaMovimientos)
        {
            System.out.println(m);
        }
    }

    public TipoPieza getTipoPieza()
    {
        return tipoPieza;
    }

    public boolean verificarJaque(Tablero casillas)
    {
        for(Movimiento m : listaMovimientos)
        {
            if(casillas.getCasillas()[m.i][m.j] instanceof Jaqueable j &&
                casillas.getCasillas()[m.i][m.j].getBando() != this.bando)
            //if(casillas.getCasillas()[m.i][m.j].getTipoPieza() == TipoPieza.REY &&
            //    casillas.getCasillas()[m.i][m.j].getBando() != this.bando)
            {

                j.activarJaque();
                return true;
            }
        }
        return false;
    }
}