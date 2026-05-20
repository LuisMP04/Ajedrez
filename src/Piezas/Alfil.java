package Piezas;

import Reglas.DireccionRayo;
import Tablero.Movimiento;
import Tablero.Tablero;

public class Alfil extends Pieza
{
    private DireccionRayo[] direcciones = new DireccionRayo[4];

    public Alfil(int bando, TipoPieza tipoPieza)
    {
        super(bando, tipoPieza);
        reiniciarDirecciones();
    }

    public Alfil(int bando, TipoPieza tipoPieza, int i, int j)
    {
        super(bando, tipoPieza, i, j);
        // Inicialmente todas las direcciones estan habilitadas
        reiniciarDirecciones();
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
            if(clavada.rayo == DireccionRayo.ARRIBA_DERECHA || clavada.rayo == DireccionRayo.ABAJO_IZQUIERDA)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[1] = null;
                direcciones[2] = null;
            }
            else if(clavada.rayo == DireccionRayo.ARRIBA_IZQUIERDA || clavada.rayo == DireccionRayo.ABAJO_DERECHA)
            {
                //anular la busqueda de los otros dos rayos
                direcciones[0] = null;
                direcciones[3] = null;
            }
        }
        
        System.out.println("Direcciones validas:");
    
        for(DireccionRayo dr : direcciones)
        {
            if(dr != null)
            {
                //System.out.println(dr);
            }
        }

        /*  
            Heuristica en diagonal:   
            1.- Arriba a la derecha     
            2.- Abajo a la derecha      
            3.- Abajo a la izquierda    
            4.- Arriba a la izquierda   
        */

        if(direcciones[0] != null)
        {
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

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando || casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        break;
                    }
                }

                if(casillas.getCasillas()[movi][movj] == null)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
                else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                movi -= 1;
                movj += 1;
            }
        }
        
        if(direcciones[1] != null)
        {
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

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando || casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        break;
                    }
                }

                if(casillas.getCasillas()[movi][movj] == null)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
                else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                movi += 1;
                movj += 1;
            }
        }   

        if(direcciones[3] != null)
        {
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

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando || casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        break;
                    }
                }

                if(casillas.getCasillas()[movi][movj] == null)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
                else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                movi += 1;
                movj -= 1;
            }
        }

        if(direcciones[2] != null)
        {
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

                if(casillas.getCasillas()[movi][movj] != null)
                {
                    if(casillas.getCasillas()[movi][movj].bando == this.bando || casillas.getCasillas()[movi][movj].getTipoPieza() == TipoPieza.REY)
                    {
                        break;
                    }
                }

                if(casillas.getCasillas()[movi][movj] == null)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                }
                else if(casillas.getCasillas()[movi][movj] != null && casillas.getCasillas()[movi][movj].bando != this.bando)
                {
                    listaMovimientos.add(new Movimiento(movi, movj));
                    break;
                }
                movi -= 1;
                movj -= 1;
            }
        }
    }

    public void clavarPiezaEnemiga(Tablero casillas)
    {
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
    }

    // Metodo para buscar el rey enemigo en una direccion especifica
    private void busquedaReyEnemigo(Tablero casillas, int i, int j, int direccionI, int direccionJ)
    {
        int movi = i + direccionI, movj = j + direccionJ;

        DireccionRayo dr;

        
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

    public void calcularMovimientosN(Tablero casillas){}

    @Override
    protected void reiniciarDirecciones()
    {
        direcciones[0] = DireccionRayo.ARRIBA_DERECHA;
        direcciones[1] = DireccionRayo.ABAJO_DERECHA;
        direcciones[2] = DireccionRayo.ARRIBA_IZQUIERDA;
        direcciones[3] = DireccionRayo.ABAJO_IZQUIERDA;
    }

    @Override
    public String toString()
    {
        if(bando == 0)
        {
            return "aB"; //alfil blanco
        }
        return "aN"; //alfil negro
    } 
}