package wizard;



/**
 * Clase de carta.
 */
public class Carta {

    /* Palo de la carta. */
    private Palo palo;
    /* Valor de la carta. */
    private int valor;
    /* Disponibilidad de la carta */
    private boolean disponible;
    /* Dueño de la carta acualmente */
    private Jugador dueño;


    /**
     * Contructor de la carta. si el valor es 0 entonces este rea un bufon, en cambio si es 14 es un mago.
     * @param valor Valor de la carta
     * @param palo Palo de la carta
     */
    public Carta(int valor, Palo palo){
        if(valor < 0 || valor > 14) throw new IllegalArgumentException("Valor no Valido");
        this.valor = valor;
        this.palo = palo;
        disponible = true;
    }

    /**
     * Regresa el palo de la carta.
     * @return palo de la carta.
     */
    public Palo getPalo(){return palo;}
    /**
     * Regresa el valor de la carta. 14 si es mago. 0 si es bufon.
     * @return valor de la carta.
     */
    public int getValor(){return valor;}
    /**
     * Nos dice si esta disponible para uso la carta.
     * @return true si esta disponible, false si no.
     */
    public boolean isDisponible(){ return disponible;}
    /**
     * Establece la disponibilidad como falsa.
     */
    public void usar(){ disponible = false;}
    /**
     * Establece la disonibilidad como verdadera. Asi como liberarla
     */
    public void liberar(){
        disponible = true;
        dueño = null;
    }

    /**
     * Establece el dueño.
     * @param dueño el nuevo dueño.
     */
    public void setDueño(Jugador dueño) {
        this.dueño = dueño;
    }

    /**
     * Regresa el dueño actual.
     * @return el dueño actual.
     */
    public Jugador getDueño() {
        return dueño;
    }


    /**
     * Generamos una representacion de la carta.
     * Mago si su valor es 14,bufon si es 0, y de
     * ahi depende de su palo y valor, aparte se le da color
     */
    public String toString(){
        if(valor == 14){
            return String.format("%s|MAGO|", "\u001B[37m");
        } else if(valor == 0){
            return String.format("%s|BUFON|", "\u001B[37m");
        } else {
            return String.format("%s|%d %s|%s", this.palo.getColor(), valor, palo.toString(), "\u001B[37m");
        }
    }
}
