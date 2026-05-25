package Piezas;

import Reglas.Jaqueable;
import Reglas.TipoMovimiento;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.Iterator;

public class Rey extends Pieza implements Jaqueable
{
    //private ArrayList<Movimiento> movPeligrosos = new ArrayList<>();
    //private ArrayList<Movimiento> casillasBloqueadas = new ArrayList<>();
    private int jaque = 0;  //0 = no jaque, 1 = jaque
    private boolean ahogado = false;
    private boolean mate = false;
    private int cantidadAtacantes = 0;
    private Pieza atacante = null;
    private boolean puedeEnrocar = true;

    public Rey(int bando)
    {   
        super(bando);
        tipoPieza = TipoPieza.REY;
        reiniciarDirecciones();
    }

    public Rey(int bando, int i, int j)
    {
        super(bando, i, j);
        tipoPieza = TipoPieza.REY;
        reiniciarDirecciones();
    }

    public int getCantidadAtacantes()
    {
        return cantidadAtacantes;
    }

    public void setAtacante(Pieza atacante)
    {
        this.atacante = atacante;
    }

    public boolean getPuedeEnrocar()
    {
        return puedeEnrocar;
    }

    public void setMovimientoEnroque(int i, int j, TipoMovimiento enroque)
    {
        listaMovimientos.add(new Movimiento(i, j, enroque));
    }

    public Pieza getPiezaAtacante()
    {
        return atacante;
    }

    public void aumentarAtacantes()
    {
        cantidadAtacantes += 1;
    }

    public void reiniciarCantidadAtacantes()
    {
        cantidadAtacantes = 0;
    }

    @Override
    public int getJaque()
    {
        return jaque;
    }

    @Override
    public void activarJaque()
    {
        if(jaque == 0)
        {
            //System.out.println("El rey está en jaque");
            jaque = 1;
        }
    }

    public boolean getAhogado()
    {
        return ahogado;
    }

    public void activarAhogado()
    {
        ahogado = true;
    }

    public void desactivarAhogado()
    {
        ahogado = false;
    }

    @Override
    public void desactivarJaque()
    {
        if(jaque != 0)
        {
            //System.out.println("El rey no está en jaque");
            jaque = 0;
        }
    }

    public boolean verificarCasilla(Tablero casillas, int i, int j)
    {
        return casillasAtacadas(casillas, i, j);
    }

    // Función que retorna true si la casilla está atacada o false si no lo está
    private boolean casillasAtacadas(Tablero casillas, int i, int j)
    {
        int movi = i, movj = j;
        Pieza[][] pieza = casillas.getCasillas();
        //int[] banderaDirecciones = new int[8];

        //calcular todas las casillas en las que podría haber una pieza que la ataque
        /*  heuristica:
                * Rey enemigo
                - 1 casilla en todas las direcciones alrededor
                * Peones (depende del bando)
                    * Rey Blanco - Peon Negro
                    - Arriba y derecha (-i +j) y Arriba e izquierda (-i -j)
                    * Rey Negro - Peon Blanco 
                    - Abajo y derecha (+i -j) y Abajo e izquierda (+i -j)
                * Torres y dama
                - Arriba (-i) y Abajo (+i)
                - Izquierda (-j) y derecha (+j)
                * Alfiles y dama
                - Arriba y derecha (-i +j) y Abajo y derecha (+i +j)
                - Arriba e izquierda (-i -j) y Abajo e izquierda (+i -j)
                * Caballos
                - Todos los movimientos del caballo
        */

        //Rey enemigo
        //Arriba (-i)
        if(movi - 1 >= 0 && 
            pieza[movi-1][movj] != null &&
            pieza[movi-1][movj].getBando() != this.bando &&
            pieza[movi-1][movj].getTipoPieza() == TipoPieza.REY)
        {
            System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ARRIBA");
            return true;
        }

        //Abajo (+i)
        if(movi + 1 <= 7 && 
            pieza[movi+1][movj] != null &&
            pieza[movi+1][movj].getBando() != this.bando &&
            pieza[movi+1][movj].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ABAJO");
            return true;
        }

        //Derecha (+j)
        if(movj + 1 <= 7 && 
            pieza[movi][movj+1] != null &&
            pieza[movi][movj+1].getBando() != this.bando &&
            pieza[movi][movj+1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO A LA DERECHA");
            return true;
        }

        //Izquierda (-j)
        if(movj - 1>= 0 && 
            pieza[movi][movj-1] != null &&
            pieza[movi][movj-1].getBando() != this.bando &&
            pieza[movi][movj-1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO A LA IZQUIERDA");
            return true;
        }

        //Arriba y derecha (-i +j)
        if(movi - 1 >= 0 && movj + 1 <= 7 && 
            pieza[movi-1][movj+1] != null &&
            pieza[movi-1][movj+1].getBando() != this.bando &&
            pieza[movi-1][movj+1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ARRIBA A LA DERECHA");
            return true;
        }

        //Abajo y derecha (+i +j)
        if(movi + 1 <= 7 && movj + 1 <= 7 && 
            pieza[movi+1][movj+1] != null &&
            pieza[movi+1][movj+1].getBando() != this.bando &&
            pieza[movi+1][movj+1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ABAJO A LA DERECHA");
            return true;
        }
        
        //Arriba e izquierda (-i -j)
        if(movi - 1 >= 0 && movj - 1 >= 0 &&
            pieza[movi-1][movj-1] != null &&
            pieza[movi-1][movj-1].getBando() != this.bando &&
            pieza[movi-1][movj-1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ARRIBA A LA IZQUIERDA");
            return true;
        }

        //Abajo e izquierda (+i -j)
        if(movi + 1 <= 7 && movj - 1 >= 0 &&
            pieza[movi+1][movj-1] != null &&
            pieza[movi+1][movj-1].getBando() != this.bando &&
            pieza[movi+1][movj-1].getTipoPieza() == TipoPieza.REY)
        {
            //System.out.println("DEBUG: REY ENEMIGO ENCONTRADO ABAJO A LA IZQUIERDA");
            return true;
        }

        // Peones
        if(this.bando == 0)
        {
            //Arriba y derecha
            if(movi - 1 >= 0 && movj + 1 <= 7 && 
                pieza[movi-1][movj+1] != null &&
                pieza[movi-1][movj+1].getBando() != this.bando &&
                pieza[movi-1][movj+1].getTipoPieza() == TipoPieza.PEON)
            {
                //System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA DERECHA");
                return true;
            }
            //Arriba e izquierda
            if(movi - 1 >= 0 && movj - 1 >= 0 && 
                pieza[movi-1][movj-1] != null &&
                pieza[movi-1][movj-1].getBando() != this.bando &&
                pieza[movi-1][movj-1].getTipoPieza() == TipoPieza.PEON)
            {
                //System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA IZQUIERDA");
                return true;
            }
        }
        else
        {
            //Abajo y derecha
            if(movi + 1 <= 7 && movj + 1 <= 7 && 
                pieza[movi+1][movj+1] != null &&
                pieza[movi+1][movj+1].getBando() != this.bando &&
                pieza[movi+1][movj+1].getTipoPieza() == TipoPieza.PEON)
            {
                //System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA DERECHA");
                return true;
            }
            //Abajo e izquierda
            if(movi + 1 <= 7 && movj - 1 >= 0 && 
                pieza[movi+1][movj-1] != null &&
                pieza[movi+1][movj-1].getBando() != this.bando &&
                pieza[movi+1][movj-1].getTipoPieza() == TipoPieza.PEON)
            {
                //System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA IZQUIERDA");
                return true;
            }
        }

        //Buscar en cada rayo (cada dirección)
        if(busquedaArriba(casillas, i, j))
        {
            return true;
        }
        if(busquedaAbajo(casillas, i, j))
        {
            return true;
        }
        if(busquedaDerecha(casillas, i, j))
        {
            return true;
        }
        if(busquedaIzquierda(casillas, i, j))
        {
            return true;
        }
        if(busquedaArribaDerecha(casillas, i, j))
        {
            return true;
        }
        if(busquedaAbajoDerecha(casillas, i, j))
        {
            return true;
        }
        if(busquedaArribaIzquierda(casillas, i, j))
        {
            return true;
        }
        if(busquedaAbajoIzquierda(casillas, i, j))
        {
            return true;
        }


        /*----Movimientos del caballo----*/
        //Arriba-2 y derecha (--i +j)
        if(movi - 2 >= 0 && movj + 1 <= 7 && pieza[movi-2][movj+1] != null &&
                                             pieza[movi-2][movj+1].getBando() != this.bando &&
                                             pieza[movi-2][movj+1].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi-2, movj+1));
            return true;
        }
        //Arriba y derecha+2 (-i ++j)
        if(movi - 1 >= 0 && movj + 2 <= 7 && pieza[movi-1][movj+2] != null &&
                                             pieza[movi-1][movj+2].getBando() != this.bando &&
                                             pieza[movi-1][movj+2].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi-1, movj+2));
            return true;
        }
        //Abajo y derecha+2 (+i ++j)
        if(movi + 1 <= 7 && movj + 2 <= 7 && pieza[movi+1][movj+2] != null &&
                                             pieza[movi+1][movj+2].getBando() != this.bando &&
                                             pieza[movi+1][movj+2].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi+1, movj+2));
            return true;
        }
        //Abajo+2 y derecha (++i +j)
        if(movi + 2 <= 7 && movj + 1 <= 7 && pieza[movi+2][movj+1] != null &&
                                             pieza[movi+2][movj+1].getBando() != this.bando &&
                                             pieza[movi+2][movj+1].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi+2, movj+1));
            return true;
        }
        //Abajo+2 e izquierda (++i -j)
        if(movi + 2 <= 7 && movj - 1 >= 0 && pieza[movi+2][movj-1] != null &&
                                             pieza[movi+2][movj-1].getBando() != this.bando &&
                                             pieza[movi+2][movj-1].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi+2, movj-1));
            return true;
        }
        //Abajo e izquierda-2 (+i --j)
        if(movi + 1 <= 7 && movj - 2 >= 0 && pieza[movi+1][movj-2] != null &&
                                             pieza[movi+1][movj-2].getBando() != this.bando &&
                                             pieza[movi+1][movj-2].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi+1, movj-2));
            return true;
        }
        //Arriba e izquierda-2 (-i --j)
        if(movi - 1 >= 0 && movj - 2 >= 0 && pieza[movi-1][movj-2] != null &&
                                             pieza[movi-1][movj-2].getBando() != this.bando &&
                                             pieza[movi-1][movj-2].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi-1, movj-2));
            return true;
        }
        //Arriba+2 e izquierda (--i -j)
        if(movi - 2 >= 0 && movj - 1 >= 0 && pieza[movi-2][movj-1] != null &&
                                             pieza[movi-2][movj-1].getBando() != this.bando &&
                                             pieza[movi-2][movj-1].getTipoPieza() == TipoPieza.CABALLO)
        {
            //movPeligrosos.add(new Movimiento(movi-2, movj-1));
            return true;
        }

        return false;
    }

    public void invalidarCasillasAtacadas(Tablero casillas)
    {
        //verificar que la lista de movimientos no esté vacía
        if(listaMovimientos.isEmpty())
        {
            return;
        }

        Iterator<Movimiento> it = listaMovimientos.iterator();

        Movimiento m;
        //comprobar si cada movimiento está disponible
        while(it.hasNext())
        {
            m = it.next();
            //System.out.println("DEBUG: Comprobando en: [" + (8 - m.i) + "|" + (char)(m.j + 65) + "]");
            //si ese movimiento está atacado
            if(casillasAtacadas(casillas, m.i, m.j) == true)
            {
                //invalidarlo
                it.remove();
            }
        }
    }

    // Busqueda por rayos para movimientos ilegales
    //Arriba
    private boolean busquedaArriba(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI;

        for(incI = 1; incI <= 7; incI++)
        {
            if(movi - incI < 0)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }

            if(pieza[movi-incI][movj] != null)
            {
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi-incI][movj].getBando() != this.bando &&
                    (pieza[movi-incI][movj].getTipoPieza() == TipoPieza.TORRE ||
                    pieza[movi-incI][movj].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA ARRIBA");
                    return true;
                }

                if(pieza[movi-incI][movj] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza bloquea el rayo
                break;
            }
        }

        return false;
    }
    //Abajo
    private boolean busquedaAbajo(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI;

        for(incI = 1; incI <= 7; incI++)
        {
            if(movi + incI > 7)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            
            if(pieza[movi+incI][movj] != null)
            {

                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi+incI][movj].getBando() != this.bando &&
                    (pieza[movi+incI][movj].getTipoPieza() == TipoPieza.TORRE ||
                    pieza[movi+incI][movj].getTipoPieza() == TipoPieza.DAMA))
                {
            
                    //System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA ABAJO");
                    return true;
                }

                if(pieza[movi+incI][movj] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza bloquea el rayo
                break;
            }
        }

        return false;
    }
    //Derecha
    private boolean busquedaDerecha(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;

        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            if(movj + incJ > 7)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            if(pieza[movi][movj+incJ] != null)
            {
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi][movj+incJ].getBando() != this.bando &&
                    (pieza[movi][movj+incJ].getTipoPieza() == TipoPieza.TORRE ||
                    pieza[movi][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA LA DERECHA");
                    return true;
                }

                if(pieza[movi][movj+incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }

        return false;
    }
    //Izquierda
    private boolean busquedaIzquierda(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;

        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            //parar busqueda de ese rayo por salirse del rango
            if(movj - incJ < 0)
            {
                break;
            }

            if(pieza[movi][movj-incJ] != null)
            { 
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi][movj-incJ].getBando() != this.bando &&
                    (pieza[movi][movj-incJ].getTipoPieza() == TipoPieza.TORRE ||
                    pieza[movi][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA LA IZQUIERDA");
                    return true;
                }

                if(pieza[movi][movj-incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }

        return false;
    }
    //Arriba y derecha
    private boolean busquedaArribaDerecha(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;

        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            if(movi - incI < 0 || movj + incJ > 7)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            if(pieza[movi-incI][movj+incJ] != null)
            {   
                //si hay pieza enemiga y amenaza en este rayo                                 
                if(pieza[movi-incI][movj+incJ].getBando() != this.bando &&
                    (pieza[movi-incI][movj+incJ].getTipoPieza() == TipoPieza.ALFIL ||
                    pieza[movi-incI][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ARRIBA-DERECHA");
                    return true;
                }

                if(pieza[movi-incI][movj+incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }

        return false;
    }
    //Abajo y derecha
    private boolean busquedaAbajoDerecha(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;

        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            if(movi + incI > 7 || movj + incJ > 7)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            
            if(pieza[movi+incI][movj+incJ] != null)
            {
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi+incI][movj+incJ].getBando() != this.bando &&
                    (pieza[movi+incI][movj+incJ].getTipoPieza() == TipoPieza.ALFIL ||
                    pieza[movi+incI][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ABAJO-DERECHA");
                    return true;
                }

                if(pieza[movi+incI][movj+incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }

        return false;
    }
    //Arriba e izquierda
    private boolean busquedaArribaIzquierda(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;

        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            if(movi - incI < 0 || movj - incJ < 0)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            
            if(pieza[movi-incI][movj-incJ] != null)
            {
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi-incI][movj-incJ].getBando() != this.bando &&
                    (pieza[movi-incI][movj-incJ].getTipoPieza() == TipoPieza.ALFIL ||
                    pieza[movi-incI][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ARRIBA-IZQUIERDA");
                    return true;
                }

                if(pieza[movi-incI][movj-incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }

        return false;
    }
    //Abajo e izquierda
    private boolean busquedaAbajoIzquierda(Tablero casillas, int i, int j)
    {
        Pieza[][] pieza = casillas.getCasillas();
        int movi = i, movj = j;
        int incI, incJ;
        for(incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            if(movi + incI > 7 || movj - incJ < 0)
            {
                //parar busqueda de ese rayo por salirse del rango
                break;
            }
            if(pieza[movi+incI][movj-incJ] != null)
            {
                //si hay pieza enemiga y amenaza en este rayo
                if(pieza[movi+incI][movj-incJ].getBando() != this.bando &&
                    (pieza[movi+incI][movj-incJ].getTipoPieza() == TipoPieza.ALFIL ||
                    pieza[movi+incI][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
                {
                    //System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ABAJO-IZQUIERDA");
                    return true;
                }

                if(pieza[movi+incI][movj-incJ] == this)
                {
                    // No es una amenaza, es la misma pieza, por lo que sigue buscando mas profundo
                    continue;
                }

                //cualquier otra pieza para el rayo
                break;
            }
        }
    
        return false;
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

        //Abajo a la derecha (+i +j) y abajo
        if((i+1 <= 7 && j+1 <= 7) && 
            casillas.getCasillas()[i+1][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j+1), TipoMovimiento.NORMAL));
            //listaMovimientos.add(new Movimiento((i+1), (j)));
        }
        else if((i+1 <= 7 && j+1 <= 7) && 
            casillas.getCasillas()[i+1][j+1] != null &&
            casillas.getCasillas()[i+1][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j+1), TipoMovimiento.CAPTURA));
            //listaMovimientos.add(new Movimiento((i+1), (j)));

        }

        //Abajo a la izquierda (+i -j) e izquierda
        if((i+1 <= 7 && j-1 >= 0) &&
            casillas.getCasillas()[i+1][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j-1), TipoMovimiento.NORMAL));
            //listaMovimientos.add(new Movimiento((i), (j-1)));
        }
        else if((i+1 <= 7 && j-1 >= 0) &&
            casillas.getCasillas()[i+1][j-1] != null &&
            casillas.getCasillas()[i+1][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j-1), TipoMovimiento.CAPTURA));
            //listaMovimientos.add(new Movimiento((i), (j-1)));
        }
        
        //Arriba a la izquierda (-i -j) y arriba
        if((i-1 >= 0 && j-1 >= 0) &&
            casillas.getCasillas()[i-1][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j-1), TipoMovimiento.NORMAL));
            //listaMovimientos.add(new Movimiento((i-1), (j)));
        }
        else if((i-1 >= 0 && j-1 >= 0) &&
            casillas.getCasillas()[i-1][j-1] != null &&
            casillas.getCasillas()[i-1][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j-1), TipoMovimiento.CAPTURA));
            //listaMovimientos.add(new Movimiento((i-1), (j)));
        }

        //Arriba a la derecha (-i +j) y derecha
        if((i-1 >= 0 && j+1 <= 7) &&
            casillas.getCasillas()[i-1][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j+1), TipoMovimiento.NORMAL));
        }
        else if((i-1 >= 0 && j+1 <= 7) &&
            casillas.getCasillas()[i-1][j+1] != null &&
            casillas.getCasillas()[i-1][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j+1), TipoMovimiento.CAPTURA));
        }

        //Arriba (-i)
        if(i-1 >= 0 &&
            casillas.getCasillas()[i-1][j] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j), TipoMovimiento.NORMAL));
        }
        else if(i-1 >= 0 &&
            casillas.getCasillas()[i-1][j] != null &&
            casillas.getCasillas()[i-1][j].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j), TipoMovimiento.CAPTURA));
        }

        //Abajo (+i)
        if(i+1 <= 7 &&
            casillas.getCasillas()[i+1][j] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j), TipoMovimiento.NORMAL));
        }
        else if(i+1 <= 7 &&
            casillas.getCasillas()[i+1][j] != null &&
            casillas.getCasillas()[i+1][j].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j), TipoMovimiento.CAPTURA));
        }

        //Derecha (+j)
        if(j+1 <= 7 &&
            casillas.getCasillas()[i][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i), (j+1), TipoMovimiento.NORMAL));
        }
        else if(j+1 <= 7 &&
            casillas.getCasillas()[i][j+1] != null &&
            casillas.getCasillas()[i][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i), (j+1), TipoMovimiento.CAPTURA));
        }

        //Izquierda (-j)
        if(j-1 >= 0 &&
            casillas.getCasillas()[i][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i), (j-1), TipoMovimiento.NORMAL));
        }
        else if(j-1 >= 0 &&
            casillas.getCasillas()[i][j-1] != null &&
            casillas.getCasillas()[i][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i), (j-1), TipoMovimiento.CAPTURA));
        }

        invalidarCasillasAtacadas(casillas);
    }

    public void calcularMovimientosN(Tablero casillas)
    {
        calcularMovimientosB(casillas);
    }

    protected void reiniciarDirecciones(){}

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

            //Verificar si se hizo un enroque
            if(puedeEnrocar == true)
            {
                if(bando == 0)
                {
                    if(posX == 7 && posY == 1)      //Enroque largo
                    {
                        //buscar la torre
                        Torre torre = null;
                        if(casillas.getCasillas()[7][0] != null && casillas.getCasillas()[7][0].getTipoPieza() == TipoPieza.TORRE)
                        {
                            torre = (Torre) casillas.getCasillas()[7][0];
                        }

                        if(torre != null)
                        {
                            //Mover la torre
                            torre.forzarMovimiento(7, 2, casillas);
                        }
                    }
                    else if(posX == 7 && posY == 6) //Enroque corto
                    {
                        //buscar la torre
                        Torre torre = null;
                        if(casillas.getCasillas()[7][7] != null && casillas.getCasillas()[7][7].getTipoPieza() == TipoPieza.TORRE)
                        {
                            torre = (Torre) casillas.getCasillas()[7][7];
                        }

                        if(torre != null)
                        {
                            //Mover la torre
                            torre.forzarMovimiento(7, 5, casillas);
                        }
                    }
                }
                else
                {
                    if(posX == 0 && posY == 1)      //Enroque largo
                    {
                        //buscar la torre
                        Torre torre = null;
                        if(casillas.getCasillas()[0][0] != null && casillas.getCasillas()[0][0].getTipoPieza() == TipoPieza.TORRE)
                        {
                            torre = (Torre) casillas.getCasillas()[0][0];
                        }

                        if(torre != null)
                        {
                            //Mover la torre
                            torre.forzarMovimiento(0, 2, casillas);
                        }
                    }
                    else if(posX == 0 && posY == 6) //Enroque corto
                    {
                        //buscar la torre
                        Torre torre = null;
                        if(casillas.getCasillas()[0][7] != null && casillas.getCasillas()[0][7].getTipoPieza() == TipoPieza.TORRE)
                        {
                            torre = (Torre) casillas.getCasillas()[0][7];
                        }

                        if(torre != null)
                        {
                            //Mover la torre
                            torre.forzarMovimiento(0, 5, casillas);
                        }
                    }
                }
            }

            puedeEnrocar = false;
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
            return "rB"; //dama blanca
        }
        return "rN"; //dama negra
    }
}
