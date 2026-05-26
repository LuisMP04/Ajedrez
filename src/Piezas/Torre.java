package Piezas;

import Reglas.DireccionRayo;
import Reglas.TipoMovimiento;
import Tablero.Movimiento;
import Tablero.Tablero;

public class Torre extends Pieza
{
    private DireccionRayo[] direcciones = new DireccionRayo[4];
    private boolean puedeEnrocar = true;

    public Torre(int bando)
    {
        super(bando);
        tipoPieza = TipoPieza.TORRE;
        reiniciarDirecciones();
    }

    public Torre(int bando, int i, int j)
    {
        super(bando, i, j);
        tipoPieza = TipoPieza.TORRE;
        reiniciarDirecciones();
    }

    public boolean getPuedeEnrocar()
    {
        return puedeEnrocar;
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    public void calcularMovimientosB(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        reiniciarDirecciones();

        if(clavada != null && clavada.estado == true)
        {
            //calcular el rayo hacia el frente y hacia atrás
            if(clavada.rayo == DireccionRayo.ARRIBA || clavada.rayo == DireccionRayo.ABAJO)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[2] = null;
                direcciones[3] = null;
            }
            else if(clavada.rayo == DireccionRayo.DERECHA || clavada.rayo == DireccionRayo.IZQUIERDA)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[0] = null;
                direcciones[1] = null;
            }
        }
        
        //System.out.println("Direcciones validas:");
        DireccionRayo dr;
        for(int xd = 0; xd < 4; xd++)
        {
            dr = direcciones[xd];
            if(dr != null)
            {
                //System.out.println(dr);
            }
        }

        /*  
            Heuristica en horizontal:   
            1.- Arriba    
            2.- A la derecha      
            3.- A la izquierda 
            4.- Abajo   
        */
       
        //Arriba
        if(direcciones[0] != null)
        {
            movi -= 1;
            //System.out.println("DEBUG: ARRIBA | i: " + movi + ", j: " + movj);
            while(movi > -1)
            {
                if(movi < 0)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles arriba");
                    break;
                }

                if(casillas.getCasillas()[movi][j] != null)
                {
                    if(casillas.getCasillas()[movi][j].bando == this.bando)
                    {
                        break;
                    }

                    if(casillas.getCasillas()[movi][j].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][j], DireccionRayo.ARRIBA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, j, TipoMovimiento.CAPTURA));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, j, TipoMovimiento.NORMAL));
                }

                movi -= 1;
            }
        }
        
        //A la derecha
        if(direcciones[2] != null)
        {
            movj = j+1;
            //System.out.println("DEBUG: A LA DERECHA | i: " + movi + ", j: " + movj);
            while(movj < 8)
            {
                if(movj > 7)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles a la derecha");
                    break;
                }

                if(casillas.getCasillas()[i][movj] != null)
                {
                    if(casillas.getCasillas()[i][movj].bando == this.bando)
                    {
                        break;
                    }

                    if(casillas.getCasillas()[i][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[i][movj], DireccionRayo.DERECHA, this);
                        break;
                    }


                    listaMovimientos.add(new Movimiento(i, movj, TipoMovimiento.CAPTURA));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(i, movj, TipoMovimiento.NORMAL));
                }

                movj += 1;
            }
        }

        //A la izquierda
        if(direcciones[3] != null)
        {
            movj = j-1;
            //System.out.println("DEBUG: A LA IZQUIERDA | i: " + movi + ", j: " + movj);
            while(movj > -1)
            {
                if(movj < 0)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles a la izquierda");
                    break;
                }
                
                if(casillas.getCasillas()[i][movj] != null)
                {
                    if(casillas.getCasillas()[i][movj].bando == this.bando)
                    {
                        break;
                    }

                    if(casillas.getCasillas()[i][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[i][movj], DireccionRayo.IZQUIERDA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(i, movj, TipoMovimiento.CAPTURA));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(i, movj, TipoMovimiento.NORMAL));
                }

                movj -= 1;
            }
        }

        //Abajo
        if(direcciones[1] != null)
        {
            movi = i+1;
            //System.out.println("DEBUG: ABAJO | i: " + movi + ", j: " + movj);
            while(movi < 8)
            {
                if(movi > 7)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles abajo");
                    break;
                }

                if(casillas.getCasillas()[movi][j] != null)
                {
                    if(casillas.getCasillas()[movi][j].bando == this.bando)
                    {
                        break;
                    }

                    if(casillas.getCasillas()[movi][j].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][j], DireccionRayo.ABAJO, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, j, TipoMovimiento.CAPTURA));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, j, TipoMovimiento.NORMAL));
                }

                movi += 1;
            }
        }
    }

    public void clavarPiezaEnemiga(Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        //Arriba
        movi -= 1;
        //System.out.println("DEBUG: ARRIBA | i: " + movi + ", j: " + movj);
        while(movi > -1)
        {
            if(movi < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba");
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, movi, j, -1, 0);
                break;
            }
            movi -= 1;
        }
        
        //A la derecha
        movj = j+1;
        //System.out.println("DEBUG: A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movj < 8)
        {
            if(movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles a la derecha");
                break;
            }

            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando == this.bando)
            {
                break;
            }
            
            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, i, movj, 0, 1);
                break;
            }
            movj += 1;
        }

        //A la izquierda
        movj = j-1;
        //System.out.println("DEBUG: A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movj > -1)
        {
            if(movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles a la izquierda");
                break;
            }
            
            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[i][movj] != null && casillas.getCasillas()[i][movj].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, i, movj, 0, -1);
                break;
            }
            movj -= 1;
        }

        //Abajo
        movi = i+1;
        //System.out.println("DEBUG: ABAJO | i: " + movi + ", j: " + movj);
        while(movi < 8)
        {
            if(movi > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo");
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][j] != null && casillas.getCasillas()[movi][j].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, movi, j, 1, 0);
                break;
            }
            movi += 1;
        }
    }

    // Metodo para buscar el rey enemigo en una direccion especifica
    private void busquedaReyEnemigo(Tablero casillas, int i, int j, int direccionI, int direccionJ)
    {
        int movi = i + direccionI, movj = j + direccionJ;

        DireccionRayo dr;

        
        if(direccionI == -1 && direccionJ == 0)
        {
            // direccion arriba
            dr = DireccionRayo.ARRIBA;
        }
        else if(direccionI == 1 && direccionJ == 0)
        {
            // direccion abajo
            dr = DireccionRayo.ABAJO;
        }
        else if(direccionI == 0 && direccionJ == 1)
        {
            // direccion derecha
            dr = DireccionRayo.DERECHA;
        }
        else if(direccionI == 0 && direccionJ == -1)
        {
            // direccion izquierda
            dr = DireccionRayo.IZQUIERDA;
        }
        else
        {
            return;
        }

        Pieza pieza;

        //Arriba
        while(movi >= 0 && movj <= 7 &&
                movi <= 7 && movj >= 0)
        {            
            pieza = casillas.getCasillas()[movi][movj];

            if(pieza != null)
            {
                if(pieza.bando != this.bando && pieza.getTipoPieza() == TipoPieza.REY)
                {
                    // Encuentra al rey  enemiga, por lo que la pieza inicial se clava
                    casillas.getCasillas()[i][j].getClavada().estado = true;
                    casillas.getCasillas()[i][j].getClavada().rayo = dr;
                }

                // Cualquier pieza que no sea el rey enemigo, para la busqueda
                break;
            }
            movi += direccionI;
            movj += direccionJ;
        }
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    protected void reiniciarDirecciones()
    {
        direcciones[0] = DireccionRayo.ARRIBA;
        direcciones[1] = DireccionRayo.ABAJO;
        direcciones[2] = DireccionRayo.DERECHA;
        direcciones[3] = DireccionRayo.IZQUIERDA;
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
            int i = posicion[0];
            int j = posicion[1];
            posicion[0] = posX;
            posicion[1] = posY;
            casillas.getCasillas()[posX][posY] = this;
            reiniciarMovimientos();
            casillas.getCasillas()[i][j] = null;
            puedeEnrocar = false;
        }
        else
        {
            System.out.println("Movimiento a " + (8 - posX) + "|" + (char)(posY+65) + " no valido");
        }
    }

    public void forzarMovimiento(int posI, int posJ, Tablero casillas)
    {
        int i = posicion[0];
        int j = posicion[1];
        posicion[0] = posI;
        posicion[1] = posJ;
        casillas.getCasillas()[posI][posJ] = this;
        reiniciarMovimientos();
        casillas.getCasillas()[i][j] = null;
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "♖"; //alfil blanco
        }
        return "♜"; //alfil negro
    }
}