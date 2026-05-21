package Reglas;

public class Clavada 
{
    public boolean estado;
    public int i;
    public int j;
    public DireccionRayo rayo = DireccionRayo.NINGUNO;

    public Clavada(int i, int j)
    {
        estado = false;
        this.i = i;
        this.j = j;
    }

    public void reiniciar()
    {
        estado = false;
        rayo = DireccionRayo.NINGUNO;
    }
}
