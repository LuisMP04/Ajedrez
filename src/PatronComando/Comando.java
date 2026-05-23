package PatronComando;

import Tablero.Tablero;

public interface Comando 
{
    //execute()
    public void actualizarMovimientos();
    public void reiniciarMovimientos();
    public void reiniciarClavadas();
}
