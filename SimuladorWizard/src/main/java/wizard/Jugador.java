package wizard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.BufferOverflowException;

public class Jugador {
    private int id;
    private String name;
    private Lista<Carta> mano;
    private int puntuacion;
    private int apuesta;
    private int ganadasPorRonda;


    /**
     * Constructor de clase jugador.
     * @param id ID del jugador.
     * @param name Nombre del jugador.
     */
    public Jugador(int id, String name){
        this.id = id;
        this.name = name;
        puntuacion = 0;
        apuesta = 0;
        mano = new Lista<>();
    }

    /**
     * Establece una apuesta.
     * @param apuesta la apuesta realizada.
     */
    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    /**
     * Establece toda la mano del jugador.
     * @param mano
     */
    public void setMano(Lista<Carta> mano) {
        this.mano = mano;
    }

    /**
     * Agrega una carta a la mano del jugador.
     * @param carta Carta a agregar.
     */
    public void addCarta(Carta carta){
        mano.agregaFinal(carta);
    }


    /**
     * Limpia la mano del jugador a la vez que libera todas las cartas.
     */
    public void limpiaMano(){
        for(Carta carta : mano){
            carta.liberar();
        }
        mano.limpia();
    }

    /**
     * Agrega la puntacion a la total.
     * @param puntuacion Puntacion a agregar.
     */
    public void addPuntuacion(int puntuacion) {
        this.puntuacion += puntuacion;
    }

    /**
     * Obtiene la apuesta realizada.
     * @return
     */
    public int getApuesta() {
        return apuesta;
    }

    /**
     * Regresa el ID del jugador.
     * @return ID del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * Regresa la mano del jugador.
     * @return Mano del jugador.
     */
    public Lista<Carta> getMano() {
        return mano;
    }

    public Carta getCartaDeMano(int i){
        return mano.get(i);
    }

    /**
     * Regresa el nombre del jugador.
     * @return Nombre del jugador.
     */
    public String getName() {
        return name;
    }

    public int getGanadasPorRonda() {
        return ganadasPorRonda;
    }
    public void setGanadasPorRonda(int ganadasPorRonda) {
        this.ganadasPorRonda = ganadasPorRonda;
    }
    public void trucoGanado(){
        ganadasPorRonda++;
    }

    public void resetGanadasPorRonda(){
        this.ganadasPorRonda = 0;
    }

    /**
     * Regresa la puntacion actual del jugador.
     * @return Puntaicon del jugador.
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    @Override public boolean equals(Object obj){
        if(!(obj instanceof Jugador))
            return false;
        Jugador jugador = (Jugador)obj;
        return this.id == jugador.id && jugador.name.equals(this.name);
    }
    
    public void imprimeMano(){
        int i = 1;
        for(Carta carta : mano){
            if(carta.isDisponible()){
                System.out.printf("%d.- %s\t",i, carta.toString());
                i++;
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.id, this.name);
    }

}
