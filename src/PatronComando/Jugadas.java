package PatronComando;

import Piezas.*;
import Reglas.DireccionRayo;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.Iterator;

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
        reiniciarCantidadAtacantes();
        reiniciarMovimientos();
        reiniciarClavadas();

        actualizarMovimientos();

        verificarJaque();
        verificarAhogado();

        casillas.mostrarTablero();
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
                    if(piezas[i][j].getTipoPieza() == TipoPieza.ALFIL || 
                        piezas[i][j].getTipoPieza() == TipoPieza.TORRE || 
                        piezas[i][j].getTipoPieza() == TipoPieza.DAMA)
                    {
                        piezas[i][j].clavarPiezaEnemiga(casillas);
                    }
                }
            }
        }

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

    private boolean ahogado(int bando)
    {
        Pieza[][] piezas = casillas.getCasillas();

        ArrayList<Pieza> vivas = new ArrayList<>();
        Rey rey = null;
        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {    
                    if(piezas[i][j].getBando() == bando)
                    {
                        //Obtener al rey
                        if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                        {
                            rey = (Rey) piezas[i][j];
                            rey.desactivarAhogado();
                        }
                        vivas.add(piezas[i][j]);
                    }
                }
            }
        }

        if(rey == null || rey.getJaque() == 1)
        {
            return false;
        }

        // Asumir que el rey está ahogado
        // Buscar todos los movimientos de las piezas blancas
        for(Pieza p : vivas)
        {
            if(!p.getListaMovimientos().isEmpty())
            {
                //Si alguna pieza blanca tiene movimientos, el rey aun no está ahogado
                rey.desactivarAhogado();
                return false;
            }
        }

        rey.activarAhogado();
        return true;
    }

    public void verificarAhogado()
    {
        if(ahogado(0))
        {
            System.out.println("Rey blanco ahogado");
        }

        if(ahogado(1))
        {
            System.out.println("Rey blanco ahogado");
        }
    }

    public void reiniciarCantidadAtacantes()
    {
        Pieza[][] piezas = casillas.getCasillas();

        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {    
                    //Obtener al rey
                    if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                    {
                        Rey rey = (Rey) piezas[i][j];
                        rey.reiniciarCantidadAtacantes();
                    }
                }
            }
        }
    }

    public void verificarJaque()
    {
        Pieza[][] piezas = casillas.getCasillas();
        //Buscar a los dos reyes
        Rey reyBlanco = null, reyNegro = null;
        ArrayList<Pieza> piezasBlancas = new ArrayList<>();
        ArrayList<Pieza> piezasNegras = new ArrayList<>();

        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null)
                {    
                    //Obtener al rey
                    if(piezas[i][j].getBando() == 0)
                    {
                        if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                        {
                            reyBlanco = (Rey) piezas[i][j];
                            continue;
                        }
                        
                        piezasBlancas.add(piezas[i][j]);
                    }
                    else
                    {
                        if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                        {
                            reyNegro = (Rey) piezas[i][j];
                            continue;
                        }
                        
                        piezasNegras.add(piezas[i][j]);
                    }
                }
            }
        }

        // Verificar un jaque
        // Si alguna pieza activó el estado de la clavada como true, el jaque se activa
        if(reyBlanco != null)
        {
            if(reyBlanco.getClavada().estado == true)
            {
                reyBlanco.activarJaque();
                filtradoDeMovimientos(reyBlanco, reyBlanco.getPiezaAtacante(), piezasBlancas);
            }
            else
            {
                reyBlanco.desactivarJaque();
            }
        }

        if(reyNegro != null)
        {
            if(reyNegro.getClavada().estado == true)
            {
                reyNegro.activarJaque();
                filtradoDeMovimientos(reyNegro, reyNegro.getPiezaAtacante(), piezasNegras);
            }
            else
            {
                reyNegro.desactivarJaque();
            }
        }

        // Verificar doble jaque
        if(reyBlanco != null && reyBlanco.getCantidadAtacantes() >= 2)
        {
            System.out.println("DOBLE JAQUE AL REY BLANCO");
            //reiniciar los movimientos de todas las demas piezas
            for(Pieza pzB : piezasBlancas)
            {
                pzB.reiniciarMovimientos();
            }
        }


        if(reyNegro != null && reyNegro.getCantidadAtacantes() >= 2)
        {
            System.out.println("DOBLE JAQUE AL REY NEGRO");
            //reiniciar los movimientos de todas las demas piezas
            for(Pieza pzN : piezasNegras)
            {
                pzN.reiniciarMovimientos();
            }
        }
    }

    private void filtradoDeMovimientos(Rey rey, Pieza atacante, ArrayList<Pieza> piezas)
    {
        if(rey.getJaque() != 1)
        {
            return;
        }

        System.out.println(rey.getClavada().rayo);

        int i, j;

        DireccionRayo dr = rey.getClavada().rayo;

        switch (dr) 
        {
            case DireccionRayo.ARRIBA:
                //Busca hacia abajo
                i = -1;
                j = 0;

                break;
            case DireccionRayo.ABAJO:
                //Busca hacia arriba
                i = 1;
                j = 0;
                
                break;
            case DireccionRayo.DERECHA:
                //Busca hacia la izquierda
                i = 0;
                j = -1;
                break;
            case DireccionRayo.IZQUIERDA:
                //Busca hacia la derecha
                i = 0;
                j = 1;
                break;
            case DireccionRayo.ARRIBA_DERECHA:
                //Busca hacia abajo a la izquierda
                i = 1;
                j = -1;
                break;
            case DireccionRayo.ABAJO_DERECHA:
                //Busca hacia arriba a la izquierda
                i = -1;
                j = -1;
                break;
            case DireccionRayo.ARRIBA_IZQUIERDA:
                //Busca hacia abajo a la derecha
                i = 1;
                j = 1;
                break;
            case DireccionRayo.ABAJO_IZQUIERDA:
                //Busca hacia arriba a la derecha
                i = -1;
                j = 1;
                break;
            case DireccionRayo.NINGUNO:
                i = 0;
                j = 0;
                break;
            default:
                throw new AssertionError();
        }

        ArrayList<Movimiento> movimientosValidos = new ArrayList<>();
        ArrayList<Movimiento> movimientosPieza;

        if(dr == DireccionRayo.NINGUNO)
        {
            movimientosValidos.add(
                new Movimiento(
                    atacante.getPosiciones()[0],
                    atacante.getPosiciones()[1]
                )
            );
        }
        else
        {
            movimientosValidos = busquedaRayo(rey.getPosiciones()[0], rey.getPosiciones()[1], i, j, atacante.getPosiciones()[0], atacante.getPosiciones()[1]);
        }

        // Filtrar todos
        for(Pieza p : piezas)
        {
            if(p.getTipoPieza() == TipoPieza.REY)
            {
                continue;
            }

            movimientosPieza = p.getListaMovimientos();

            Iterator<Movimiento> it = movimientosPieza.iterator();

            while(it.hasNext())
            {
                Movimiento m = it.next();

                if(!movimientoValido(m, movimientosValidos))
                {
                    //Si no está dentro de los movimientos validos, eliminar el movimiento de la pieza
                    it.remove();
                }
            }
        }
    }

    private boolean movimientoValido(Movimiento mov, ArrayList<Movimiento> validos)
    {
        for(Movimiento mv : validos)
        {
            if(mv.i == mov.i && mv.j == mov.j)
            {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Movimiento> busquedaRayo(int inicioI, int inicioJ, int direccionI, int direccionJ, int finalI, int finalJ)
    {
        ArrayList<Movimiento> movimientos = new ArrayList<>();

        while((inicioI != finalI) || (inicioJ != finalJ))
        {
            inicioI += direccionI;
            inicioJ += direccionJ;

            if((inicioI < 0 || inicioI > 7) ||
                (inicioJ < 0 || inicioJ > 7))
            {
                System.out.println("ERROR: Se salió de los limites");
                break;
            }

            movimientos.add(new Movimiento(inicioI, inicioJ));
        }

        return movimientos;
    }
}
