package Piezas;

import Reglas.DireccionRayo;
import Reglas.TipoMovimiento;
import Tablero.Movimiento;
import Tablero.Tablero;

public class Caballo extends Pieza
{
    public Caballo(int bando)
    {
        super(bando);
        tipoPieza = TipoPieza.CABALLO;
    }

    public Caballo(int bando, int i, int j)
    {
        super(bando, i, j);
        tipoPieza = TipoPieza.CABALLO;
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    public void calcularMovimientosB(Tablero casillas)
    {
        //System.out.println("CABALLO CLAVADO ESTADO: " + clavada.estado);
        // Si el caballo está clavado, no se puede mover
        if(clavada.estado == true)
        {
            return;
        }
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        for(movi = 1; movi <= 2; movi++)
        {
            for(movj = 2; movj >= 1; movj--)
            {
                if(movi == movj)
                {
                    continue;
                }

                //Abajo y derecha (+i +j)
                if(i+movi <= 7 && j+movj <= 7)
                {
                    if(casillas.getCasillas()[i+movi][j+movj] == null)
                    {
                        listaMovimientos.add(new Movimiento(i+movi, j+movj, TipoMovimiento.NORMAL));
                    }
                    else if(casillas.getCasillas()[i+movi][j+movj].bando != this.bando)
                    {
                        if(casillas.getCasillas()[i+movi][j+movj].getTipoPieza() == TipoPieza.REY)
                        {
                            //Encuentra al rey enemigo, activar jaque
                            darJaque(casillas.getCasillas()[i+movi][j+movj], DireccionRayo.NINGUNO, this);
                        }
                        else
                        {  
                            //No fue el rey, agregar el movimiento
                            listaMovimientos.add(new Movimiento(i+movi, j+movj, TipoMovimiento.CAPTURA));
                        }
                    }
                }

                //Abajo e izquierda (+i -j)
                if(i+movi <= 7 && j-movj >= 0)
                {
                    if(casillas.getCasillas()[i+movi][j-movj] == null)
                    {
                        listaMovimientos.add(new Movimiento(i+movi, j-movj, TipoMovimiento.NORMAL));
                    }
                    else if(casillas.getCasillas()[i+movi][j-movj].bando != this.bando)
                    {
                        if(casillas.getCasillas()[i+movi][j-movj].getTipoPieza() == TipoPieza.REY)
                        {
                            //Encuentra al rey enemigo, activar jaque
                            darJaque(casillas.getCasillas()[i+movi][j-movj], DireccionRayo.NINGUNO, this);
                        }
                        else
                        {
                            //No fue el rey, agregar el movimiento
                            listaMovimientos.add(new Movimiento(i+movi, j-movj, TipoMovimiento.CAPTURA));
                        }
                    }
                }

                //Arriba e izquierda (-i -j)
                if(i-movi >= 0 && j-movj >= 0)
                {
                    if(casillas.getCasillas()[i-movi][j-movj] == null)
                    {
                        listaMovimientos.add(new Movimiento(i-movi, j-movj, TipoMovimiento.NORMAL));
                    }
                    else if(casillas.getCasillas()[i-movi][j-movj].bando != this.bando)
                    {
                        if(casillas.getCasillas()[i-movi][j-movj].getTipoPieza() == TipoPieza.REY)
                        {
                            //Encuentra al rey enemigo, activar jaque
                            darJaque(casillas.getCasillas()[i-movi][j-movj], DireccionRayo.NINGUNO, this);
                        }
                        else
                        {
                            //No fue el rey, agregar el movimiento
                            listaMovimientos.add(new Movimiento(i-movi, j-movj, TipoMovimiento.CAPTURA));
                        }
                    }
                }

                //Arriba y derecha (-i +j)
                if(i-movi >= 0 && j+movj <= 7)
                {
                    if(casillas.getCasillas()[i-movi][j+movj] == null)
                    {
                        listaMovimientos.add(new Movimiento(i-movi, j+movj, TipoMovimiento.NORMAL));
                    }
                    else if(casillas.getCasillas()[i-movi][j+movj].bando != this.bando)
                    {
                        if(casillas.getCasillas()[i-movi][j+movj].getTipoPieza() == TipoPieza.REY)
                        {
                            //Encuentra al rey enemigo, activar jaque
                            darJaque(casillas.getCasillas()[i-movi][j+movj], DireccionRayo.NINGUNO, this);
                        }
                        else
                        {
                            //No fue el rey, agregar el movimiento
                            listaMovimientos.add(new Movimiento(i-movi, j+movj, TipoMovimiento.CAPTURA));
                        }
                    }
                }
            }
        }
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    protected void reiniciarDirecciones(){}

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "cB"; //alfil blanco
        }
        return "cN"; //alfil negro
    } 
}
