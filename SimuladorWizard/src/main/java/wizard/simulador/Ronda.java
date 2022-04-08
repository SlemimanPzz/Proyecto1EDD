package wizard.simulador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import wizard.Carta;
import wizard.Jugador;
import wizard.Lista;
import wizard.Palo;

public class Ronda {
    /** La lista de jugadores */
    private Lista<Jugador> jugadores;

    /** Numero de jugadors totales */
    private int jugadoresTotales;

    /** Iteracion de la ronda actual */
    private int nRonda;

    /** Baraja entera del juego */
    private Carta[] baraja;

    /**Palo obligador por truco */
    private Palo lider;
    private boolean yaHayLider;

    /** Carta por ronda mas valiso */
    private Palo triunfo;
    private boolean hayTriunfo;

    /** Carta actual libre de la baraja */
    private int cartaBaraja;

    /** Ultimo jugador que barajeo */
    private Jugador barajeador;

    /**Ultimo ganador de un truco */
    private Jugador ultimoGanador;

    /** Las cartas jugados por el jugador */
    private Lista<Carta> mesa;

    

    private void Truco(int i){
        System.out.println();
        System.out.println("------------------------------------");
        System.out.printf("Truco %d", i);
    
        JueganJugadores();
        Carta gano = Ganador();
        System.out.printf("Gano la carta %s del jugador %s", gano.toString(), gano.getDueño().toString());
        gano.getDueño().trucoGanado();
        ultimoGanador = gano.getDueño();
        estableceOrden(ultimoGanador);

    }

    private void JueganJugadores(){

        System.out.println("Ahora se jugaran las cartas.");

        for(Jugador j : jugadores){

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            if(yaHayLider && hayTriunfo){
                System.out.printf(" Palo del triunfo: %s | Palo lider: %s\n", triunfo.toString(), lider.toString());
            } else if(hayTriunfo){
                System.out.printf("Palo del triunfo: %s | Aun no hay lider\n");
            } else if( yaHayLider){
                System.out.printf("No hay triunfo | Palo lider: %s\n");
            } else {
                System.out.println("Aun no hay lider aun ,no  hay triunfo.");
            }

            j.imprimeMano();

            System.out.printf("Jugador %s selecione la carta que desees jugar\n", j.toString());

            while(true){
                int i = 0;
                try {
                    i = Integer.parseInt(in.readLine()) - 1;
                    if(i >= j.getMano().getLongitud() || i < 0){
                        System.out.println("Ingresa un numero valido");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un numero valido");
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                Carta jugada = j.getCartaDeMano(i);

                if(jugada.getValor() == 14){
                    mesa.agregaFinal(jugada);
                    System.out.printf("Haz jugado %s\n", jugada.toString());
                    break;
                } else if(jugada.getValor() == 0){
                    mesa.agregaFinal(jugada);
                    System.out.printf("Haz jugado %s\n", jugada.toString());
                    break;
                }  else if(yaHayLider){
                    boolean tienes = false;
                    for(Carta c : j.getMano()){
                        if(c.getPalo() == lider) tienes = true;
                    }
                    if(tienes && jugada.getPalo() != lider){
                        System.out.println("Seleciona una carta del palo lider.");
                        continue;
                    } else {
                        mesa.agregaFinal(jugada);
                        System.out.printf("Haz jugado %s\n", jugada.toString());
                    break;
                    }
                } else {
                    mesa.agregaFinal(jugada);
                    lider = jugada.getPalo();
                    yaHayLider = true;
                    System.out.printf("Se ha establecido el palo lider como %s", lider.toString());
                    System.out.printf("Haz jugado %\ns", jugada.toString());
                    break;
                }

            }
        }
    }

    private Carta Ganador(){
        Carta ganadora = mesa.get(0);
        for(int i = 1; i < mesa.getLongitud(); i++){
            if(ganadora.getValor() == 14){
                break;
            }
            else if(ganadora.getPalo() == triunfo && ganadora.getValor() > mesa.get(i).getValor()){
                continue;
            }
            else if(ganadora.getPalo() == lider && ganadora.getValor() > mesa.get(i).getValor()){
                continue;
            } else if(mesa.get(i).getValor() != 0){
                ganadora = mesa.get(i);
            }
        }
        mesa.limpia();
        return ganadora;
    }

    public Ronda(Lista<Jugador> jugadores, int jugadoresTotales, Carta[] baraja){
        this.jugadores = jugadores;
        this.jugadoresTotales = jugadoresTotales;
        nRonda = 1;
        this.baraja = baraja;
        cartaBaraja = 0;
    }


    private void estableceOrden(Jugador jugador){
        while(jugadores.getPrimero().getId() != jugador.getId()){
            jugadores.agregaFinal(jugadores.eliminaPrimero());
        }
    }

    public void repartirCartas(){
        for(Jugador j : jugadores){
            for(int i = 0; i < nRonda;i++){
                j.addCarta(baraja[cartaBaraja]);
                baraja[cartaBaraja].setDueño(j);
                cartaBaraja++;
            }
        }
    }

    private void defPaloTriunfo(){
        try {
            Carta cartaDef = baraja[cartaBaraja];
            hayTriunfo = true;
            if(cartaDef.getValor() == 14){
                System.out.printf("EL jugador %s escoje el Palo del Trinfo:\n 1.ROJO\n 2.VERDE\n 3.AZUL\n 3.AMARILLO\n", jugadores.toString());
                BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    switch(Integer.parseInt(in.readLine())){
                        case 1:     triunfo = Palo.ROJO; break;
                        case 2:     triunfo = Palo.VERDE; break;
                        case 3:     triunfo = Palo.AZUL; break;
                        case 4:     triunfo = Palo.AMARILLO; break;
                        default:    System.out.printf("Error al recibir opcion, el palo sera %s\n", (triunfo = Palo.getPaloRandom()).toString());
                    }
                    in.close();
                } catch (Exception e) {
                    System.out.printf("Error al recibir opcion, el palo sera %s\n", (triunfo = Palo.getPaloRandom()).toString());
                }
            } else if(cartaDef.getValor() == 0){
                triunfo = null;
            } else {
                triunfo = cartaDef.getPalo();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            triunfo = null;
            hayTriunfo = false;
        }
    }

    private void apuestaJugadores(){
        for(Jugador j : jugadores){
            BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    System.out.printf("Jugador %d : %s, haz tu apuesta:\n", j.getId(), j.getName());
                    System.out.printf("Tus cartas:\n");
                    j.imprimeMano();
                    while(true){
                        try {
                            int i = Integer.parseInt(in.readLine());
                            if( i >= 0 && i <= nRonda){
                                j.setApuesta(i);
                                break;
                            } else {
                                System.out.printf("Ingresa un numero entre 0 y %d\n", nRonda);
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ingresa un numero.");
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Hubo un error al leer las apuestas, seran 0 esta ronda.");
                }
        }
    }

    private void barajear(Jugador jugador){
        if(jugador == null) barajeador = jugadores.getPrimero();
        barajeador = jugador;
        Random rand = new Random();
		
		for (int i = 0; i < baraja.length; i++) {
			int randomIndexToSwap = rand.nextInt(baraja.length);
			Carta temp = baraja[randomIndexToSwap];
			baraja[randomIndexToSwap] = baraja[i];
			baraja[i] = temp;
		}
    }

    public void juegaRonda(){

        barajear(jugadores.getPrimero());

        repartirCartas();

        defPaloTriunfo();

        apuestaJugadores();


        for(int i = 0; i < nRonda; i++){
            Truco(i+1);
        }

        System.out.printf("Ronda %d termindad\n", nRonda);



        for(Jugador j : jugadores){
            if(j.getApuesta() == j.getGanadasPorRonda()){
                int puntuacionDeRonda = j.getGanadasPorRonda() * 10;
                System.out.printf("Jugador %s has ganado esta ronda %d\n", j.toString(), puntuacionDeRonda);
                j.addPuntuacion(puntuacionDeRonda);
                j.resetGanadasPorRonda();
                System.out.printf("Tu puntuacion acutal es %d\n", j.getPuntuacion());
            } else{
                int puntuacionDeRonda = -10*(Math.abs(j.getApuesta() - j.getGanadasPorRonda()));
                System.out.printf("Jugador %s has ganado esta ronda %d\n", j.toString(), puntuacionDeRonda);
                j.addPuntuacion(puntuacionDeRonda);
                j.resetGanadasPorRonda();
                System.out.printf("Tu puntuacion acutal es %d\n", j.getPuntuacion());
            }
            j.limpiaMano();
        }

        for(Carta c : baraja){
            c.liberar();
        }

        nRonda++;




    }
}
