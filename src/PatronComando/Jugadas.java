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

    //Comandos
    public void actualizarMovimientos()
    {
        Pieza[][] piezas = casillas.getCasillas();
        
        int j;|
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {
                    piezas[i][j].actualizarMovimientos(casillas);
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
