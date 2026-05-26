package Motor;

import PatronComando.Jugadas;
import Piezas.*;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.ArrayList;

public class Motor 
{
    private Tablero tablero;
    private Jugadas comandos;

    public Motor()
    {
        tablero = new Tablero();
        comandos = new Jugadas(tablero);
        comandos.ejecutar();
    }

    public void turno()
    {
        comandos.ejecutar();
    }

    public Pieza getPieza(int fila, int col)
    {
        return tablero.getPieza(fila, col);
    }

    public void mover(int inicioFila, int inicioCol, int destinoFila, int destinoCol)
    {
        Pieza pieza = getPieza(inicioFila, inicioCol);
        pieza.mover(destinoFila, destinoCol, tablero);
    }

    public void ejecutarMovimiento(int i1, int j1, int i2, int j2)
    {
        mover(i1, j1, i2, j2);
        turno();
    }

    public ArrayList<Movimiento> getMovimientos(int fila, int col)
    {
        Pieza p = tablero.getPieza(fila, col);
        if(p == null) return new ArrayList<>();
        return p.getListaMovimientos();
    }

    public boolean terminarPartida()
    {
        return comandos.getFinPartida();
    }
}
