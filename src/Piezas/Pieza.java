package Piezas;

import Reglas.Clavada;
import Reglas.DireccionRayo;
import Reglas.Jaqueable;
import Tablero.*;
import java.util.ArrayList;

public abstract class Pieza 
{
    protected int[] posicion = new int[2];
    protected ArrayList<Movimiento> listaMovimientos;
    protected int bando; //0 = blanco, 1 = negro;
    protected TipoPieza tipoPieza;
    protected Clavada clavada;

    public Pieza(int bando)
    {
        //reiniciarDirecciones();
        posicion[0] = 0;
        posicion[1] = 0;
        listaMovimientos = new ArrayList<>();
        this.bando = bando;
        this.tipoPieza = null;
        clavada = new Clavada(posicion[0], posicion[1]);
    }

    public Pieza(int bando, int i, int j)
    {
        //reiniciarDirecciones();
        posicion[0] = i;
        posicion[1] = j;
        listaMovimientos = new ArrayList<>();
        this.bando = bando;
        this.tipoPieza = null;
        clavada = new Clavada(posicion[0], posicion[1]);
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

    public abstract void calcularMovimientosB(Tablero casillas);
    public abstract void calcularMovimientosN(Tablero casillas);
    public void actualizarMovimientos(Tablero casillas)
    {
        if(listaMovimientos.isEmpty() && bando == 0)
        {
            calcularMovimientosB(casillas);
        }
        else if(listaMovimientos.isEmpty() && bando == 1)
        {
            calcularMovimientosN(casillas);
        }
    }

    public void reiniciarMovimientos()
    {
        listaMovimientos.clear();
        reiniciarDirecciones();
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

    public Clavada getClavada()
    {
        return clavada;
    }

    public void darJaque(Pieza rey, DireccionRayo rayo)
    {
        //Primero comprobar si la pieza es un rey, solamente para asegurar
        if(rey.getTipoPieza() == TipoPieza.REY && rey.getBando() != this.bando)
        {
            rey.getClavada().estado = true;
            rey.getClavada().rayo = rayo;    
        }
    }

    protected abstract void reiniciarDirecciones();
}