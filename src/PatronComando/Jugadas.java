package PatronComando;

import Piezas.Pieza;
import Tablero.Tablero;

public class Jugadas implements Comando
{
    //private ArrayList<Pieza> piezas = new ArrayList();
    private Tablero casillas;

    public Jugadas(Tablero casillas)
    {
        this.casillas = casillas;
    }

    public void ejecutar()
    {
        casillas.mostrarTablero();
        reiniciarMovimientos();
        verificarJaques();
        actualizarMovimientos();
        reiniciarClavadas();
    }

    //Comandos
    public void actualizarMovimientos()
    {
        Pieza[][] piezas = casillas.getCasillas();
        
        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {
                    //piezas[i][j].reiniciarMovimientos();
                    piezas[i][j].actualizarMovimientos(casillas);
                }
            }
        }
    }

    public void reiniciarMovimientos()
    {
        Pieza[][] piezas = casillas.getCasillas();

        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {
                    piezas[i][j].reiniciarMovimientos();
                }
            }
        }
    }

    public void reiniciarClavadas()
    {
        Pieza[][] piezas = casillas.getCasillas();

        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {
                    piezas[i][j].getClavada().reiniciar();
                }
            }
        }
    }

    public void verificarJaques()
    {
        Pieza[][] piezas = casillas.getCasillas();
        
        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {
                    piezas[i][j].verificarJaque(casillas);
                }
            }
        }
    }
}
