package Tablero;

import Reglas.TipoMovimiento;

public class Movimiento 
{
    public int i = 0;
    public int j = 0;
    public TipoMovimiento tipoMov;

    public Movimiento(int i, int j, TipoMovimiento tipoMov)
    {
        this.i = i;
        this.j = j;
        this.tipoMov = tipoMov;
    }

    @Override
    public String toString()
    {
        char columna = 65;
        columna += j;
        //return "[" + (8 - i) + "], [" + columna + "]";
        return "[" + (8 - i) + "], [" + columna + "] ó [" + i + "], [" + j + "]";
    }
}