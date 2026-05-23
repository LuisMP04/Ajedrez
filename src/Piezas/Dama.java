package Piezas;

import Reglas.DireccionRayo;
import Tablero.Movimiento;
import Tablero.Tablero;

public class Dama extends Pieza
{
    private DireccionRayo[] direcciones = new DireccionRayo[8];

    public Dama(int bando)
    {
        super(bando);
        tipoPieza = TipoPieza.DAMA;
        reiniciarDirecciones();
    }

    public Dama(int bando, int i, int j)
    {
        super(bando, i, j);
        tipoPieza = TipoPieza.DAMA;
        reiniciarDirecciones();
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    @Override
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
                //anular la busqueda de los otros rayos
                direcciones[2] = null;
                direcciones[3] = null;
                direcciones[4] = null;
                direcciones[5] = null;
                direcciones[6] = null;
                direcciones[7] = null;
            }
            else if(clavada.rayo == DireccionRayo.DERECHA || clavada.rayo == DireccionRayo.IZQUIERDA)
            {
                //anular la busqueda de los otros rayos
                direcciones[0] = null;
                direcciones[1] = null;
                direcciones[4] = null;
                direcciones[5] = null;
                direcciones[6] = null;
                direcciones[7] = null;
            }
            else if(clavada.rayo == DireccionRayo.ARRIBA_DERECHA || clavada.rayo == DireccionRayo.ABAJO_IZQUIERDA)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[5] = null;
                direcciones[6] = null;
                direcciones[0] = null;
                direcciones[1] = null;
                direcciones[2] = null;
                direcciones[3] = null;
            }
            else if(clavada.rayo == DireccionRayo.ARRIBA_IZQUIERDA || clavada.rayo == DireccionRayo.ABAJO_DERECHA)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[4] = null;
                direcciones[7] = null;
                direcciones[0] = null;
                direcciones[1] = null;
                direcciones[2] = null;
                direcciones[3] = null;
            }
        }

        //System.out.println("Direcciones validas:");
        DireccionRayo dr;
        for(int xd = 0; xd < 8; xd++)
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

                    listaMovimientos.add(new Movimiento(movi, j));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, j));
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

                    listaMovimientos.add(new Movimiento(i, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(i, movj));
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

                    listaMovimientos.add(new Movimiento(i, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(i, movj));
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
                        darJaque(casillas.getCasillas()[movi][j], DireccionRayo.ARRIBA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, j));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, j));
                }

                movi += 1;
            }
        }

        movi = i;
        movj = j;

        /*  
            Heuristica en diagonal:   
            1.- Arriba a la derecha     
            2.- Abajo a la derecha      
            3.- Abajo a la izquierda    
            4.- Arriba a la izquierda   
        */

        //Arriba a la derecha
        if(direcciones[4] != null)
        {
            movi -= 1;
            movj += 1;
            //System.out.println("DEBUG: ARRIBA A LA DERECHA | i: " + movi + ", j: " + movj);
            while(movi > -1 && movj < 8)
            {
                if(movi < 0 && movj > 7)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles arriba a la derecha");
                    break;
                }

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando)
                    {
                        break;
                    }
                    
                    if(casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][movj], DireccionRayo.ARRIBA_DERECHA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
    
                movi -= 1;
                movj += 1;
            }
        }
        
        //Abajo a la derecha
        if(direcciones[5] != null)
        {
            movi = i+1;
            movj = j+1;
            //System.out.println("DEBUG: ABAJO A LA DERECHA | i: " + movi + ", j: " + movj);
            while(movi < 8 && movj < 8)
            {
                if(movi > 7 && movj > 7)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles abajo a la derecha");
                    break;
                }

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando)
                    {
                        break;
                    }
                    
                    if(casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][movj], DireccionRayo.ABAJO_DERECHA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }

                movi += 1;
                movj += 1;
            }
        }

        //Abajo a la izquierda
        if(direcciones[7] != null)
        {
            movi = i+1;
            movj = j-1;
            //System.out.println("DEBUG: ABAJO A LA IZQUIERDA | i: " + movi + ", j: " + movj);
            while(movi < 8 && movj > -1)
            {
                if(movi > 7 && movj < 0)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles abajo a la izquierda");
                    break;
                }

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando)
                    {
                        break;
                    }
                    
                    if(casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][movj], DireccionRayo.ABAJO_IZQUIERDA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
                movi += 1;
                movj -= 1;
            }
        }

        //Arriba a la izquierda
        if(direcciones[6] != null)
        {
            movi = i-1;
            movj = j-1;
            //System.out.println("DEBUG: ARRIBA A LA IZQUIERDA | i: " + movi + ", j: " + movj);
            while(movi > -1 && movj > -1)
            {
                if(movi < 0 && movj < 0)
                {
                    System.out.println("DEBUG: No hay movimientos disponibles arriba a la izquierda");
                    break;
                }

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando)
                    {
                        break;
                    }
                    
                    if(casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        //Encuentra al rey enemigo, activar jaque
                        darJaque(casillas.getCasillas()[movi][movj], DireccionRayo.ARRIBA_IZQUIERDA, this);
                        break;
                    }

                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                else
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }

                movi -= 1;
                movj -= 1;
            }
        }
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    public void clavarPiezaEnemiga(Tablero casillas)
    {
        // Busqueda de rayos en diagonal
        int i = posicion[0];
        int j = posicion[1];
        int movi = i, movj = j;

        /*  
            Heuristica en diagonal:   
            1.- Arriba a la derecha     
            2.- Abajo a la derecha      
            3.- Abajo a la izquierda    
            4.- Arriba a la izquierda   
        */

        //Arriba a la derecha
        movi -= 1;
        movj += 1;
        //System.out.println("DEBUG: ARRIBA A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movi > -1 && movj < 8)
        {
            if(movi < 0 && movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba a la derecha");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                // Seguir buscando en esa dirección en busca del rey enemigo
                busquedaReyEnemigo(casillas, movi, movj, -1, 1);
                break;
            }
            movi -= 1;
            movj += 1;
        }
    
        //Abajo a la derecha
        movi = i+1;
        movj = j+1;
        //System.out.println("DEBUG: ABAJO A LA DERECHA | i: " + movi + ", j: " + movj);
        while(movi < 8 && movj < 8)
        {
            if(movi > 7 && movj > 7)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo a la derecha");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, movi, movj, 1, 1);
                break;
            }
            movi += 1;
            movj += 1;
        } 

        //Abajo a la izquierda
        movi = i+1;
        movj = j-1;
        //System.out.println("DEBUG: ABAJO A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movi < 8 && movj > -1)
        {
            if(movi > 7 && movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles abajo a la izquierda");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, movi, movj, 1, -1);
                break;
            }
            movi += 1;
            movj -= 1;
        }

        //Arriba a la izquierda
        movi = i-1;
        movj = j-1;
        //System.out.println("DEBUG: ARRIBA A LA IZQUIERDA | i: " + movi + ", j: " + movj);
        while(movi > -1 && movj > -1)
        {
            if(movi < 0 && movj < 0)
            {
                System.out.println("DEBUG: No hay movimientos disponibles arriba a la izquierda");
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando == this.bando)
            {
                break;
            }

            if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
            {
                busquedaReyEnemigo(casillas, movi, movj, -1, -1);
                break;
            }
            movi -= 1;
            movj -= 1;
        }

        // Busqueda de rayos en línea recta
        i = posicion[0];
        j = posicion[1];
        movi = i;
        movj = j;

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

        // Rayos en diagonal
        if(direccionI == -1 && direccionJ == 1)
        {
            // direccion arriba a la derecha
            dr = DireccionRayo.ARRIBA_DERECHA;
        }
        else if(direccionI == 1 && direccionJ == 1)
        {
            // direccion abajo a la derecha
            dr = DireccionRayo.ABAJO_DERECHA;
        }
        else if(direccionI == -1 && direccionJ == -1)
        {
            // direccion arriba a la izquierda
            dr = DireccionRayo.ARRIBA_IZQUIERDA;
        }
        else if(direccionI == 1 && direccionJ == -1)
        {
            // direccion abajo a la izquierda
            dr = DireccionRayo.ABAJO_IZQUIERDA;
        }
        // Rayos en línea recta
        else if(direccionI == -1 && direccionJ == 0)
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

    @Override
    protected void reiniciarDirecciones()
    {
        // Línea recta
        direcciones[0] = DireccionRayo.ARRIBA;
        direcciones[1] = DireccionRayo.ABAJO;
        direcciones[2] = DireccionRayo.DERECHA;
        direcciones[3] = DireccionRayo.IZQUIERDA;

        // Diagonales
        direcciones[4] = DireccionRayo.ARRIBA_DERECHA;
        direcciones[5] = DireccionRayo.ABAJO_DERECHA;
        direcciones[6] = DireccionRayo.ARRIBA_IZQUIERDA;
        direcciones[7] = DireccionRayo.ABAJO_IZQUIERDA;
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "dB"; //dama blanca
        }
        return "dN"; //dama negra
    }
}