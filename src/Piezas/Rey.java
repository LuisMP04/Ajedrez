package Piezas;

import Reglas.Jaqueable;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.ArrayList;

public class Rey extends Pieza implements Jaqueable
{
    private ArrayList<Movimiento> movPeligrosos = new ArrayList<>();
    private ArrayList<Movimiento> casillasBloqueadas = new ArrayList<>();
    private int jaque = 0;  //0 = no jaque, 1 = jaque

    public Rey(int bando, TipoPieza tipoPieza)
    {   
        super(bando, tipoPieza);
    }

    public Rey(int bando, TipoPieza tipoPieza, int i, int j)
    {
        this.bando = bando;
        posicion[0] = i;
        posicion[1] = j;
        this.tipoPieza = tipoPieza;
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
            System.out.println("El rey está en jaque");
            jaque = 1;
        }
    }

    @Override
    public void desactivarJaque()
    {
        if(jaque != 0)
        {
            System.out.println("El rey no está en jaque");
            jaque = 0;
        }
    }

    //fase 1 del preprocesamiento
    public void calcularAreaPeligrosa(Tablero casillas, Movimiento movimiento)
    {
        //asumiendo que el rey está completamente solo, siempre tendrá un área (zona) en la que cualquier pieza podría atacarlo
        //int i = posicion[0], j = posicion[1];
        int movi = movimiento.i, movj = movimiento.j;

        //calcular todas las casillas en las que podría haber una pieza que la ataque
        /*  heuristica:
                - Arriba
                - Arriba y derecha
                - Derecha
                - Abajo y derecha
                - Abajo
                - Abajo e izquierda
                - Izquierda
                - Arriba e izquierda
                - Todos los movimientos del caballo
        */

        int incI, incJ;
        //Arriba (-i)
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi - incI >= 0)
            {
                movPeligrosos.add(new Movimiento(movi-incI, movj));
            }
        }
        //Arriba y derecha (-i +j)
        incJ = 1;
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi - incI >= 0 && movj + incJ <= 7)
            {
                movPeligrosos.add(new Movimiento(movi-incI, movj+incJ));
            }
            incJ += 1;
        }
        //Derecha (+j)
        for(incJ = 1; incJ <= 7; incJ++)
        {
            if(movj + incJ <= 7)
            {
                movPeligrosos.add(new Movimiento(movi, movj+incJ));
            }
        }
        //Abajo y derecha (+i +j)
        incJ = 1;
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi + incI <= 7 && movj + incJ <= 7)
            {
                movPeligrosos.add(new Movimiento(movi+incI, movj+incJ));
            }
            incJ += 1;
        }
        //Abajo (+i)
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi + incI <= 7)
            {
                movPeligrosos.add(new Movimiento(movi+incI, movj));
            }
        }
        //Abajo e izquierda (+i -j)
        incJ = 1;
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi + incI <= 7 && movj - incJ >= 0)
            {
                movPeligrosos.add(new Movimiento(movi+incI, movj-incJ));
            }
            incJ += 1;
        }
        //Izquierda (-j)
        for(incJ = 1; incJ <= 7; incJ++)
        {
            if(movj - incJ >= 0)
            {
                movPeligrosos.add(new Movimiento(movi, movj-incJ));
            }
        }
        //Arriba e izquierda (-i -j)
        incJ = 1;
        for(incI = 1; incI <= 7; incI++)
        {
            if(movi - incI >= 0 && movj - incJ >= 0)
            {
                movPeligrosos.add(new Movimiento(movi-incI, movj-incJ));
            }
            incJ += 1;
        }

        /*----Movimientos del caballo----*/
        //Arriba-2 y derecha (--i +j)
        if(movi - 2 >= 0 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj+1));
        }
        //Arriba y derecha+2 (-i ++j)
        if(movi - 1 >= 0 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj+2));
        }
        //Abajo y derecha+2 (+i ++j)
        if(movi + 1 <= 7 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj+2));
        }
        //Abajo+2 y derecha (++i +j)
        if(movi + 2 <= 7 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj+1));
        }
        //Abajo+2 e izquierda (++i -j)
        if(movi + 2 <= 7 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj-1));
        }
        //Abajo e izquierda-2 (+i --j)
        if(movi + 1 <= 7 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj-2));
        }
        //Arriba e izquierda-2 (-i --j)
        if(movi - 1 >= 0 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj-2));
        }
        //Arriba+2 e izquierda (--i -j)
        if(movi - 2 >= 0 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj-1));
        }
        /*
        //Mas movimientos, dios mio que pedo con el caballo
        //Arriba+3 e izquierda-1 (---i -j)
        if(movi - 3 >= 0 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-3, movj-1));
        }
        //Arriba+1 e izquierda-3 (-i ---j)
        if(movi - 1 >= 0 && movj - 3 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj-3));
        }
        //Abajo-1 e izquierda-3 (+i ---j)
        if(movi + 1 <= 7 && movj - 3 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj-3));
        }
        //Abajo-3 e izquierda-1 (+++i -j)
        if(movi + 3 <= 7 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+3, movj-1));
        }
        //Abajo-3 y derecha+1 (+++i +j)
        if(movi + 3 <= 7 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+3, movj+1));
        }
        //Abajo-1 y derecha+3 (+i +++j)
        if(movi + 1 <= 7 && movj + 3 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj+3));
        }
        //Arriba+1 y derecha+3 (-i +++j)
        if(movi - 1 >= 0 && movj + 3 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj+3));
        }
        //Arriba+3 y derecha+1 (---i +j)
        if(movi - 3 >= 0 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-3, movj+1));
        }
        //AUN MAS MOVIMIENTOS QUE PODRIAN NEGAR JUGADAS
        //MALDITO CABALLO
        //Arriba+3 e izquierda-2 (---i --j)
        if(movi - 3 >= 0 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-3, movj-2));
        }
        //Arriba+2 e izquierda-3 (--i ---j)
        if(movi - 2 >= 0 && movj - 3 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj-3));
        }
        //Abajo-2 e izquierda-3 (++i ---j)
        if(movi + 2 <= 7 && movj - 3 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj-3));
        }
        //Abajo-3 e izquierda-2 (+++i --j)
        if(movi + 3 <= 7 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+3, movj-2));
        }
        //Abajo+3 y derecha+2 (+++i ++j)
        if(movi+3 <= 7 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+3, movj+2));
        }
        //Abajo+2 y derecha+3 (++i +++j)
        if(movi+2 <= 7 && movj + 3 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj+3));
        }
        //Arriba+2 y derecha+3 (--i +++j)
        if(movi-2 >= 0 && movj + 3 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj+3));
        }
        //Arriba+3 y derecha+2 (---i ++j)
        if(movi-3 >= 0 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-3, movj+2));
        }
        */
    }

    public boolean casillasAtacadas(Tablero casillas, int i, int j)
    {
        int movi = i, movj = j;
        Pieza[][] pieza = casillas.getCasillas();
        //int[] banderaDirecciones = new int[8];

        //calcular todas las casillas en las que podría haber una pieza que la ataque
        /*  heuristica:
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

        // Peones
        if(this.bando == 0)
        {
            //Arriba y derecha
            if(movi - 1 >= 0 && movj + j <= 7 && 
                pieza[movi-1][movj+1] != null &&
                pieza[movi-1][movj+1].getBando() != this.bando &&
                pieza[movi-1][movj+1].getTipoPieza() == TipoPieza.PEON)
            {
                System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA DERECHA");
                return false;
            }
            //Arriba e izquierda
            if(movi - 1 >= 0 && movj - j >= 0 && 
                pieza[movi-1][movj-1] != null &&
                pieza[movi-1][movj-1].getBando() != this.bando &&
                pieza[movi-1][movj-1].getTipoPieza() == TipoPieza.PEON)
            {
                System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA IZQUIERDA");
                return false;
            }
        }
        else
        {
            //Abajo y derecha
            if(movi + 1 <= 7 && movj + j <= 7 && 
                pieza[movi+1][movj+1] != null &&
                pieza[movi+1][movj+1].getBando() != this.bando &&
                pieza[movi+1][movj+1].getTipoPieza() == TipoPieza.PEON)
            {
                System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA DERECHA");
                return false;
            }
            //Abajo e izquierda
            if(movi + 1 <= 7 && movj - j >= 0 && 
                pieza[movi+1][movj-1] != null &&
                pieza[movi+1][movj-1].getBando() != this.bando &&
                pieza[movi+1][movj-1].getTipoPieza() == TipoPieza.PEON)
            {
                System.out.println("DEBUG: PEON NEGRO ENCONTRADO ARRIBA A LA IZQUIERDA");
                return false;
            }
        }

        /*for(int bD = 0; i < 7; i++)
        {
            banderaDirecciones[bD] = 0;
        }*/

        int incJ;
        for(int incI = 1; incI <= 7; incI++)
        {
            incJ = incI;
            //Arriba
            if(movi - incI >= 0 && pieza[movi-incI][movj] != null && 
                                    pieza[movi-incI][movj].getBando() != this.bando &&
                                    (pieza[movi-incI][movj].getTipoPieza() == TipoPieza.TORRE ||
                                    pieza[movi-incI][movj].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA ARRIBA");
                return false;
            }
            //Abajo
            if(movi + incI <= 7 && pieza[movi+incI][movj] != null && 
                                    pieza[movi+incI][movj].getBando() != this.bando &&
                                    (pieza[movi+incI][movj].getTipoPieza() == TipoPieza.TORRE ||
                                    pieza[movi+incI][movj].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA ABAJO");
                return false;
            }
            //Izquierda
            if(movj - incJ >= 0 && pieza[movi][movj-incJ] != null && 
                                    pieza[movi][movj-incJ].getBando() != this.bando &&
                                    (pieza[movi][movj-incJ].getTipoPieza() == TipoPieza.TORRE ||
                                    pieza[movi][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA LA IZQUIERDA");
                return false;
            }
            //Derecha
            if(movj + incJ <= 7 && pieza[movi][movj+incJ] != null && 
                                    pieza[movi][movj+incJ].getBando() != this.bando &&
                                    (pieza[movi][movj+incJ].getTipoPieza() == TipoPieza.TORRE ||
                                    pieza[movi][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O TORRE ENCONTRADA HACIA LA DERECHA");
                return false;
            }
            //Arriba y derecha
            if(movi - incI >= 0 && movj + incJ <= 7 && pieza[movi-incI][movj+incJ] != null && 
                                    pieza[movi-incI][movj+incJ].getBando() != this.bando &&
                                    (pieza[movi-incI][movj+incJ].getTipoPieza() == TipoPieza.ALFIL ||
                                    pieza[movi-incI][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ARRIBA-DERECHA");
                return false;
            }
            //Abajo y derecha
            if(movi + incI <= 7 && movj + incJ <= 7 && pieza[movi+incI][movj+incJ] != null && 
                                    pieza[movi+incI][movj+incJ].getBando() != this.bando &&
                                    (pieza[movi+incI][movj+incJ].getTipoPieza() == TipoPieza.ALFIL ||
                                    pieza[movi+incI][movj+incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ABAJO-DERECHA");
                return false;
            }
            //Arriba e izquierda
            if(movi - incI >= 0 && movj - incJ >= 0 && pieza[movi-incI][movj-incJ] != null &&
                                    pieza[movi-incI][movj-incJ].getBando() != this.bando &&
                                    (pieza[movi-incI][movj-incJ].getTipoPieza() == TipoPieza.ALFIL ||
                                    pieza[movi-incI][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ARRIBA-IZQUIERDA");
                return false;
            }
            //Abajo e izquierda
            if(movi + incI <= 7 && movj - incJ >= 0 && pieza[movi+incI][movj-incJ] != null &&
                                    pieza[movi+incI][movj-incJ].getBando() != this.bando &&
                                    (pieza[movi+incI][movj-incJ].getTipoPieza() == TipoPieza.ALFIL ||
                                    pieza[movi+incI][movj-incJ].getTipoPieza() == TipoPieza.DAMA))
            {
                System.out.println("DEBUG: DAMA O ALFIL ENCONTRADA HACIA ABAJO-IZQUIERDA");
                return false;
            }
        }

        /*----Movimientos del caballo----*/
        //Arriba-2 y derecha (--i +j)
        if(movi - 2 >= 0 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj+1));
        }
        //Arriba y derecha+2 (-i ++j)
        if(movi - 1 >= 0 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj+2));
        }
        //Abajo y derecha+2 (+i ++j)
        if(movi + 1 <= 7 && movj + 2 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj+2));
        }
        //Abajo+2 y derecha (++i +j)
        if(movi + 2 <= 7 && movj + 1 <= 7)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj+1));
        }
        //Abajo+2 e izquierda (++i -j)
        if(movi + 2 <= 7 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+2, movj-1));
        }
        //Abajo e izquierda-2 (+i --j)
        if(movi + 1 <= 7 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi+1, movj-2));
        }
        //Arriba e izquierda-2 (-i --j)
        if(movi - 1 >= 0 && movj - 2 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-1, movj-2));
        }
        //Arriba+2 e izquierda (--i -j)
        if(movi - 2 >= 0 && movj - 1 >= 0)
        {
            movPeligrosos.add(new Movimiento(movi-2, movj-1));
        }

        return true;
    }

    /*--- Nota ---*/
    //primera parte del pre procesamiento hecha
    //falta verificar los tipos de piezas para calcular las casillas en las que
    //las piezas están clavadas (del mismo bando)
    //o las piezas no pueden ser comidas (del bando contrario)
    //una vez hecho eso, se desabilitan las casillas clavadas
    //y sigue el procesamiento (calcularMovimientos)

    public void mostrarAreaPeligrosa()
    {
        System.out.println("Area peligrosa de la pieza " + this + " en " + (8 - posicion[0]) + "|" + (char)(posicion[1]+65));
        for(Movimiento m : movPeligrosos)
        {
            System.out.println(m);
        }
    }

    @Override
    public void actualizarMovimientos(Tablero casillas)
    {
        calcularMovmientosB(casillas);
    }

    public void calcularMovmientosB(Tablero casillas)
    {
        System.out.println("La pieza es blanca");
        int i = posicion[0];
        int j = posicion[1];
        
        //Abajo a la derecha (+i +j) y abajo
        if((i+1 <= 7 && j+1 <= 7) &&
            casillas.getCasillas()[i+1][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j+1)));
            //listaMovimientos.add(new Movimiento((i+1), (j)));
        }
        else if((i+1 <= 7 && j+1 <= 7) &&
            casillas.getCasillas()[i+1][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j+1)));
            //listaMovimientos.add(new Movimiento((i+1), (j)));

        }

        //Abajo a la izquierda (+i -j) e izquierda
        if((i+1 <= 7 && j-1 >= 0) &&
            casillas.getCasillas()[i+1][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j-1)));
            //listaMovimientos.add(new Movimiento((i), (j-1)));
        }
        else if((i+1 <= 7 && j-1 >= 0) &&
        casillas.getCasillas()[i+1][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j-1)));
            //listaMovimientos.add(new Movimiento((i), (j-1)));
        }
        
        //Arriba a la izquierda (-i -j) y arriba
        if((i-1 >= 0 && j-1 >= 0) &&
            casillas.getCasillas()[i-1][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j-1)));
            //listaMovimientos.add(new Movimiento((i-1), (j)));
        }
        else if((i-1 >= 0 && j-1 >= 0) &&
        casillas.getCasillas()[i-1][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j-1)));
            //listaMovimientos.add(new Movimiento((i-1), (j)));
        }

        //Arriba a la derecha (-i +j) y derecha
        if((i-1 >= 0 && j+1 <= 7) &&
            casillas.getCasillas()[i-1][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j+1)));
            //listaMovimientos.add(new Movimiento((i), (j+1)));
        }
        else if((i-1 >= 0 && j+1 <= 7) &&
        casillas.getCasillas()[i-1][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j+1)));
            //listaMovimientos.add(new Movimiento((i), (j+1)));
        }

        //Arriba (-i)
        if(i-1 >= 0 &&
            casillas.getCasillas()[i-1][j] == null)
        {
            listaMovimientos.add(new Movimiento((i-1), (j)));
        }
        else if(i-1 >= 0 &&
            casillas.getCasillas()[i-1][j].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i-1), (j)));
        }

        //Abajo (+i)
        if(i+1 <= 7 &&
            casillas.getCasillas()[i+1][j] == null)
        {
            listaMovimientos.add(new Movimiento((i+1), (j)));
        }
        else if(i+1 <= 7 &&
            casillas.getCasillas()[i+1][j].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i+1), (j)));
        }

        //Derecha (+j)
        if(j+1 <= 7 &&
            casillas.getCasillas()[i][j+1] == null)
        {
            listaMovimientos.add(new Movimiento((i), (j+1)));
        }
        else if(j+1 <= 7 &&
            casillas.getCasillas()[i][j+1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i), (j+1)));
        }

        //Izquierda (-j)
        if(j-1 >= 0 &&
            casillas.getCasillas()[i][j-1] == null)
        {
            listaMovimientos.add(new Movimiento((i), (j-1)));
        }
        else if(j-1 >= 0 &&
            casillas.getCasillas()[i][j-1].getBando() != this.bando)
        {
            listaMovimientos.add(new Movimiento((i), (j-1)));
        }
    }

    public void calcularMovmientosN(Tablero casillas){}

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
