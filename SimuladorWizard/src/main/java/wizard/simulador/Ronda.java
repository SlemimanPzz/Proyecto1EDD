package wizard.simulador;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import wizard.Carta;
import wizard.Jugador;
import wizard.Lista;
import wizard.Palo;

public class Ronda {
    private Lista<Jugador> jugadores;
    private int jugadoresTotales;
    private int nRonda;
    private Carta[] baraja;
    private Carta lider;
    private Palo triunfo;
    private int cartaBaraja;
    private Jugador barajeador;

    public Ronda(Lista<Jugador> jugadores, int jugadoresTotales, int nRonda, Carta[] baraja){
        this.jugadores = jugadores;
        this.jugadoresTotales = jugadoresTotales;
        this.nRonda = nRonda;
        this.baraja = baraja;
        cartaBaraja = 0;
    }


    public void repartirCartas(){
        for(Jugador j : jugadores){
            for(int i = 0; i < nRonda;i++){
                j.addCarta(baraja[cartaBaraja]);
                cartaBaraja++;
            }
        }
    }

    private void defPaloTriunfo(){
        try {
            Carta cartaDef = baraja[cartaBaraja];
            if(cartaDef.getValor() == 14){
                System.out.printf("EL jugador %s escoje el Palo del Trinfo:\n 1.ROJO\n 2.VERDE\n 3.AZUL\n 3.AMARILLO\n", jugadores.toString());
                BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    switch(Integer.parseInt(in.readLine())){
                        case 1:     triunfo = Palo.ROJO; break;
                        case 2:     triunfo = Palo.VERDE; break;
                        case 3:     triunfo = Palo.AZUL; break;
                        case 4:     triunfo = Palo.AMARILLO; break;
                        default:    System.out.printf("Error al recibir opcion, el palo sera %s", (triunfo = Palo.getPaloRandom()).toString());
                    }
                    in.close();
                } catch (Exception e) {
                    System.out.printf("Error al recibir opcion, el palo sera %s", (triunfo = Palo.getPaloRandom()).toString());
                }
            } else if(cartaDef.getValor() == 0){
                triunfo = null;
            } else {
                triunfo = cartaDef.getPalo();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            triunfo = null;
        }
    }

    private void apuestaJugadores(){
        for(Jugador j : jugadores){
            BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    System.out.printf("Jugador %s, haz tu apuesta:\n", j.toString());
                    while(true){
                        try {
                            j.setApuesta(Integer.parseInt(in.readLine()));
                        } catch (NumberFormatException e) {
                            System.out.println("Ingresa un numero.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Hubo un error al leer las apuestas, seran 0 esta ronda.");
                }
        }
    }

    

}
