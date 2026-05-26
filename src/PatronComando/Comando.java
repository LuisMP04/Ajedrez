package PatronComando;

import Tablero.Tablero;

public interface Comando 
{
    //execute()
    public void actualizarMovimientos();
    public void reiniciarMovimientos();
    public void reiniciarClavadas();
    public void reiniciarCantidadAtacantes();

    //Verificaciones
    public void verificarCoronacion();
    public boolean verificarTablas();
    public void verificarJaque();
    public void verificarEnroque();
    public boolean verificarAhogado();
    public boolean verificarMate();
}
