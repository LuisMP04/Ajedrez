package PatronComando;

import Piezas.*;
import Reglas.DireccionRayo;
import Reglas.TipoMovimiento;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.Iterator;

public class Jugadas implements Comando
{
    //private ArrayList<Pieza> piezas = new ArrayList();
    private Tablero casillas;
    private boolean finPartida = false;

    public Jugadas(Tablero casillas)
    {
        this.casillas = casillas;
    }

    public void ejecutar()
    {
        reiniciarCantidadAtacantes();
        reiniciarMovimientos();
        reiniciarClavadas();

        verificarCoronacion();

        actualizarMovimientos();

        finPartida |= verificarTablas();
        verificarJaque();
        verificarEnroque();
        finPartida |= verificarAhogado();
        finPartida |= verificarMate();

        casillas.mostrarTablero();
    }

    public boolean getFinPartida()
    {
        return finPartida;
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

    public boolean verificarAhogado()
    {
        if(ahogado(0))
        {
            System.out.println("Rey blanco ahogado");
            return true;
        }

        if(ahogado(1))
        {
            System.out.println("Rey negro ahogado");
            return true;
        }

        return false;
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

    public void verificarEnroque()
    {
        Pieza[][] piezas = casillas.getCasillas();
        //Buscar a los dos reyes y a las torres
        Rey reyBlanco = null, reyNegro = null;

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
                        }
                    }
                    else
                    {
                        if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                        {
                            reyNegro = (Rey) piezas[i][j];
                        }
                    }
                }
            }
        }

        Torre torreBlancaLargo = null;
        Torre torreBlancaCorto = null;
        Torre torreNegraLargo = null;
        Torre torreNegraCorto = null;

        if(casillas.getCasillas()[7][0] != null && casillas.getCasillas()[7][0].getTipoPieza() == TipoPieza.TORRE)
        {
            torreBlancaLargo = (Torre) piezas[7][0];
        }

        if(casillas.getCasillas()[7][7] != null && casillas.getCasillas()[7][7].getTipoPieza() == TipoPieza.TORRE)
        {
            torreBlancaCorto = (Torre) piezas[7][7];
        }

        if(casillas.getCasillas()[0][0] != null && casillas.getCasillas()[0][0].getTipoPieza() == TipoPieza.TORRE)
        {
            torreNegraLargo = (Torre) piezas[0][0];
        }

        if(casillas.getCasillas()[0][7] != null && casillas.getCasillas()[0][7].getTipoPieza() == TipoPieza.TORRE)
        {
            torreNegraCorto = (Torre) piezas[0][7];
        }

        if(torreBlancaLargo != null)
        {
            reyEnroqueLargo(reyBlanco, torreBlancaLargo);
        }

        if(torreBlancaCorto != null)
        {
            reyEnroqueCorto(reyBlanco, torreBlancaCorto);
        }

        if(torreNegraLargo != null)
        {
            reyEnroqueLargo(reyNegro, torreNegraLargo);
        }

        if(torreNegraCorto != null)
        {
            reyEnroqueCorto(reyNegro, torreNegraCorto);
        }
    }

    private void reyEnroqueLargo(Rey rey, Torre torre)
    {
        int i = rey.getPosiciones()[0];
        int j = rey.getPosiciones()[1];

        if(rey.getPuedeEnrocar() == false || 
            torre.getPuedeEnrocar() == false || 
            rey.getJaque() == 1)
        {
            //System.out.println("DEBUG: El rey " + rey.getBando() + " no puede enrocar");
            return;
        }

        //buscar en las tres casillas a la izquierda
        if(casillas.getCasillas()[i][j-1] == null &&
            casillas.getCasillas()[i][j-2] == null &&
            casillas.getCasillas()[i][j-3] == null &&
            !rey.verificarCasilla(casillas, i, j-1) &&
            !rey.verificarCasilla(casillas, i, j-2) &&
            !rey.verificarCasilla(casillas, i, j-3))
        {
            //Si en ninguna de las casillas estaria atacado
            rey.setMovimientoEnroque(i, j-3, TipoMovimiento.ENROQUE);
        }
    }

    private void reyEnroqueCorto(Rey rey, Torre torre)
    {
        int i = rey.getPosiciones()[0];
        int j = rey.getPosiciones()[1];

        if(rey.getPuedeEnrocar() == false || 
            torre.getPuedeEnrocar() == false || 
            rey.getJaque() == 1)
        {
            //System.out.println("DEBUG: El rey " + rey.getBando() + " no puede enrocar");
            return;
        }

        //buscar en las tres casillas a la izquierda
        if(casillas.getCasillas()[i][j+1] == null &&
            casillas.getCasillas()[i][j+2] == null &&
            !rey.verificarCasilla(casillas, i, j+1) &&
            !rey.verificarCasilla(casillas, i, j+2))
        {
            //Si en ninguna de las casillas estaria atacado
            rey.setMovimientoEnroque(i, j+2, TipoMovimiento.ENROQUE);
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
                    atacante.getPosiciones()[1],
                    TipoMovimiento.NORMAL
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

    public boolean verificarTablas()
    {
        Pieza[][] piezas = casillas.getCasillas();
        //Buscar alguna pieza diferente a los dos reyes, si no hay ninguna, significa que solo quedan los dos reyes, por lo tanto tablas
        int j;
        for(int i = 0; i < 8; i++)
        {
            for(j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null && piezas[i][j].getTipoPieza() != TipoPieza.REY)
                {
                    return false;
                }
            }
        }

        return true;
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

            movimientos.add(new Movimiento(inicioI, inicioJ, TipoMovimiento.NORMAL));
        }

        return movimientos;
    }

    public boolean verificarMate()
    {
        if(mate(0))
        {
            System.out.println("JAQUE MATE AL REY BLANCO");
            return true;
        }

        if(mate(1))
        {
            System.out.println("JAQUE MATE AL REY NEGRO");
            return true;
        }

        return false;
    }

    private boolean mate(int bando)
    {
        Pieza[][] piezas = casillas.getCasillas();

        Rey rey = null;
        ArrayList<Pieza> vivas = new ArrayList<>();

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(piezas[i][j] != null &&
                piezas[i][j].getBando() == bando)
                {
                    vivas.add(piezas[i][j]);

                    if(piezas[i][j].getTipoPieza() == TipoPieza.REY)
                    {
                        rey = (Rey)piezas[i][j];
                    }
                }
            }
        }

        // Si no está en jaque, no es mate
        if(rey == null || rey.getJaque() == 0)
        {
            return false;
        }

        // Si alguna pieza tiene movimientos, no es mate
        for(Pieza p : vivas)
        {
            if(!p.getListaMovimientos().isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public void verificarCoronacion()
    {
        //Como solo se puede coronar un peon en cada turno, buscar peones en fila 0 y 7
        Pieza[][] piezas = casillas.getCasillas();
        int j;
        for(j = 0; j < 8; j++)
        {
            //buscar peones blancos
            if(piezas[0][j] != null)
            {
                if(piezas[0][j].getTipoPieza() == TipoPieza.PEON && piezas[0][j].getBando() == 0)
                {
                    piezas[0][j] = new Dama(0, 0, j);
                }
            }
        }

        for(j = 0; j < 8; j++)
        {
            //buscar peones negros
            if(piezas[7][j] != null)
            {
                if(piezas[7][j].getTipoPieza() == TipoPieza.PEON && piezas[7][j].getBando() == 1)
                {
                    piezas[7][j] = new Dama(1, 7, j);
                }
            }
        }
    }
}