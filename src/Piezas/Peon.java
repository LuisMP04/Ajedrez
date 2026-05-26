package Piezas;

import Reglas.DireccionRayo;
import Reglas.TipoMovimiento;
import Tablero.*;

public class Peon extends Pieza
{
    private boolean primerTurno;
    private DireccionRayo[] direcciones = new DireccionRayo[3];

    public Peon(int bando)
    {
        super(bando);
        tipoPieza = TipoPieza.PEON;
        primerTurno = true;
    }

    public Peon(int bando, int i, int j)
    {
        super(bando, i, j);
        tipoPieza = TipoPieza.PEON;
        primerTurno = true;
    }

    public void calcularMovimientosB(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];

        reiniciarDirecciones();

        if(clavada != null && clavada.estado == true)
        {
            //calcular el rayo
            if(clavada.rayo == DireccionRayo.ABAJO || clavada.rayo == DireccionRayo.ARRIBA)
            {
                direcciones[1] = null;
                direcciones[2] = null;
            }
            else if(clavada.rayo == DireccionRayo.ARRIBA_DERECHA || clavada.rayo == DireccionRayo.ABAJO_IZQUIERDA)
            {
                direcciones[0] = null;
                direcciones[2] = null;
            }
            else if(clavada.rayo == DireccionRayo.ABAJO_DERECHA || clavada.rayo == DireccionRayo.ARRIBA_IZQUIERDA)
            {
                direcciones[0] = null;
                direcciones[1] = null;
            }
        }

        //System.out.println("Movimientos de la pieza en " + posicion[0] + "|" + posicion[1]);
        //calcular los movimientos del peon
        if(direcciones[0] != null)
        {
            if(i > 0 && casillas.getCasillas()[i-1][j] == null)
            {
                listaMovimientos.add(new Movimiento(i-1, j, TipoMovimiento.NORMAL));

                if(primerTurno == true)
                {
                    if(casillas.getCasillas()[i-2][j] == null)
                    {
                        listaMovimientos.add(new Movimiento(i-2, j, TipoMovimiento.NORMAL));
                        //primerTurno = false;
                    }
                }
            }

        }
           
        //Arriba izquierda
        if(direcciones[2] != null)
        {
            if(i > 0 && j > 0 && casillas.getCasillas()[i-1][j-1] != null)
            {
                if(casillas.getCasillas()[i-1][j-1].getBando() != this.bando)
                {
                    if(casillas.getCasillas()[i-1][j-1].getTipoPieza() == TipoPieza.REY)
                    {
                        darJaque(casillas.getCasillas()[i-1][j-1], DireccionRayo.NINGUNO, this);
                    }
                    else
                    {    
                        listaMovimientos.add(new Movimiento(i-1, j-1, TipoMovimiento.CAPTURA));
                    }
                }
            }
        }

        //Arriba derecha
        if(direcciones[1] != null)
        {
            if(i > 0 && j < 7 && casillas.getCasillas()[i-1][j+1] != null)
            {
                if(casillas.getCasillas()[i-1][j+1].getBando() != this.bando)
                {
                    if(casillas.getCasillas()[i-1][j+1].getTipoPieza() == TipoPieza.REY)
                    {
                        darJaque(casillas.getCasillas()[i-1][j+1], DireccionRayo.NINGUNO, this);
                    }
                    else
                    {    
                        listaMovimientos.add(new Movimiento(i-1, j+1, TipoMovimiento.CAPTURA));
                    }
                }
            }
        }
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];

        reiniciarDirecciones();

        if(clavada != null && clavada.estado == true)
        {
            //calcular el rayo
            if(clavada.rayo == DireccionRayo.ABAJO || clavada.rayo == DireccionRayo.ARRIBA)
            {
                direcciones[1] = null;
                direcciones[2] = null;
            }
            else if(clavada.rayo == DireccionRayo.ARRIBA_DERECHA || clavada.rayo == DireccionRayo.ABAJO_IZQUIERDA)
            {
                direcciones[0] = null;
                direcciones[2] = null;
            }
            else if(clavada.rayo == DireccionRayo.ABAJO_DERECHA || clavada.rayo == DireccionRayo.ARRIBA_IZQUIERDA)
            {
                direcciones[0] = null;
                direcciones[1] = null;
            }
        }

        //System.out.println("Movimientos de la pieza en " + posicion[0] + "|" + posicion[1]);
        //calcular los movimientos del peon

        if(direcciones[0] != null)
        {
            if(i < 7 && casillas.getCasillas()[i+1][j] == null)
            {
                listaMovimientos.add(new Movimiento(i+1, j, TipoMovimiento.NORMAL));

                if(primerTurno == true)
                {
                    if(casillas.getCasillas()[i+2][j] == null)
                    {
                        listaMovimientos.add(new Movimiento(i+2, j, TipoMovimiento.NORMAL));
                        //primerTurno = false;
                    }
                }
            }
        }

        //Abajo a la izquierda
        if(direcciones[2] != null)
        {
            if(i < 7 && j > 0 && casillas.getCasillas()[i+1][j-1] != null)
            {
                if(casillas.getCasillas()[i+1][j-1].getBando() != this.bando)
                {
                    if(casillas.getCasillas()[i+1][j-1].getTipoPieza() == TipoPieza.REY)
                    {
                        darJaque(casillas.getCasillas()[i+1][j-1], DireccionRayo.NINGUNO, this);
                    }
                    else
                    {
                        listaMovimientos.add(new Movimiento(i+1, j-1, TipoMovimiento.CAPTURA));
                    }
                }
            }
        }

        if(direcciones[1] != null)
        {
            if(i < 7 && j < 7 && casillas.getCasillas()[i+1][j+1] != null)
            {
                if(casillas.getCasillas()[i+1][j+1].getBando() != this.bando)
                {
                    if(casillas.getCasillas()[i+1][j+1].getTipoPieza() == TipoPieza.REY)
                    {
                        darJaque(casillas.getCasillas()[i+1][j+1], DireccionRayo.NINGUNO, this);
                    }
                    else
                    {

                        listaMovimientos.add(new Movimiento(i+1, j+1, TipoMovimiento.CAPTURA));
                    }
                }
            }
        }
    }

    protected void reiniciarDirecciones()
    {
        if(bando == 0)
        {
            direcciones[0] = DireccionRayo.ARRIBA;  //Hacia el frente
            direcciones[1] = DireccionRayo.ARRIBA_DERECHA;  //Derecha
            direcciones[2] = DireccionRayo.ARRIBA_IZQUIERDA;  //Izquierda
        }
        else
        {
            direcciones[0] = DireccionRayo.ABAJO;  //Hacia el frente
            direcciones[1] = DireccionRayo.ABAJO_DERECHA;  //Derecha
            direcciones[2] = DireccionRayo.ABAJO_IZQUIERDA;  //Izquierda
        }
    }

    @Override
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
            primerTurno = false;
        }
        else
        {
            System.out.println("Movimiento a " + (8 - posX) + "|" + (char)(posY+65) + " no valido");
        }
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "♙"; //peon blanco
        }
        return "♟"; //peon negro
    }
}