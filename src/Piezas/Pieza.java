package Piezas;

import Reglas.Clavada;
import Reglas.DireccionRayo;
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

    public ArrayList<Movimiento> getListaMovimientos()
    {
        return listaMovimientos;
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
        if(bando == 0)
        {
            calcularMovimientosB(casillas);
        }
        else
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

    public Clavada getClavada()
    {
        return clavada;
    }

    public void darJaque(Pieza rey, DireccionRayo rayo, Pieza atacante)
    {
        //System.out.println("JAQUE DETECTADO");
        //Primero comprobar si la pieza es un rey, solamente para asegurar
        if(rey.getTipoPieza() == TipoPieza.REY && rey.getBando() != this.bando)
        {
            Rey piezaRey = (Rey) rey;
            piezaRey.getClavada().estado = true;
            piezaRey.getClavada().rayo = rayo;
            piezaRey.aumentarAtacantes();
            piezaRey.setAtacante(atacante);
        }
    }

    public void clavarPiezaEnemiga(Tablero casillas){}
    protected abstract void reiniciarDirecciones();
}