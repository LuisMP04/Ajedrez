package Tablero;

public class Movimiento 
{
    public int i = 0;
    public int j = 0;

    public Movimiento(int i, int j)
    {
        this.i = i;
        this.j = j;
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