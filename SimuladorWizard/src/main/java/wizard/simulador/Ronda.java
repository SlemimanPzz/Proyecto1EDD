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
    private int nRonda ;

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
    private Jugador ultimoGanadorTruco;

    /** Ultimo ganador ronda */
    private Jugador ultimoGanadorRonda;

    /** Las cartas jugados por el jugador */
    private Lista<Carta> mesa;

    /** Modo rapido */
    private static boolean fastMode;

    private void espera500(){
        if(fastMode) return;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Crea una pausa en la ejecucion para cuando no hay fast mode */
    private static void esperaTexto(){
        if(fastMode) return;
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {}
    }

    
    /**
     * Se juega un truco.
     * @param i el numero de truco.
     */
    private void Truco(int i){
        System.out.println("------------------------------------");
        System.out.printf("Truco %d\n", i);
        lider = null;
        yaHayLider = false;
        mesa.limpia();
    
        JueganJugadores();
        esperaTexto();
        Carta gano = Ganador();
        System.out.printf("Gano la carta %s del jugador %s\n", gano.toString(), gano.getDueño().toString());
        esperaTexto();
        gano.getDueño().trucoGanado();
        ultimoGanadorTruco = gano.getDueño();
        estableceOrden(ultimoGanadorTruco);

    }

    /** Todos lo jugadores juegan sus cartas con las correspondientes restricciones. */
    private void JueganJugadores(){

        System.out.println("Ahora se jugaran las cartas.");
        espera500();

        for(Jugador j : jugadores){

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            if(yaHayLider && hayTriunfo){
                System.out.printf(" Palo del triunfo: %s | Palo lider: %s\n", triunfo.toString(), lider.toString());
            } else if(hayTriunfo && !yaHayLider){
                System.out.printf("Palo del triunfo: %s | Aun no hay lider\n", triunfo.toString());
            } else if( yaHayLider && !hayTriunfo){
                System.out.printf("No hay triunfo | Palo lider: %s\n", lider.toString());
            } else {
                System.out.println("Aun no hay lider aun ,no  hay triunfo.");
            }
            esperaTexto();
            j.imprimeMano();

            System.out.printf("Jugador %s selecione la carta que desees jugar, tus cartas son las de arriba.\n", j.toString());

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
                espera500();
                Carta jugada = j.getCartaDeMano(i);
                

                if(jugada.getValor() == 14){
                    mesa.agregaFinal(jugada);
                    System.out.printf("Haz jugado %s\n", jugada.toString());
                    j.usaCarta(jugada);
                    break;
                } else if(jugada.getValor() == 0){
                    mesa.agregaFinal(jugada);
                    System.out.printf("Haz jugado %s\n", jugada.toString());
                    j.usaCarta(jugada);
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
                        j.usaCarta(jugada);
                        break;
                    }
                } else {
                    mesa.agregaFinal(jugada);
                    lider = jugada.getPalo();
                    yaHayLider = true;
                    System.out.printf("Se ha establecido el palo lider como %s\n", lider.toString());
                    System.out.printf("Haz jugado %s\n", jugada.toString());
                    j.usaCarta(jugada);
                    break;
                }
            }
            espera500();
        }
    }

    /**
     * Determina la carta ganadora de la mesa.
     * @return regresa la carta ganadora.
     */
    private Carta Ganador(){
        Carta ganadora = mesa.getPrimero();
        for(Carta c : mesa){

            if(c.getValor() == 14){
                ganadora = c;
                return ganadora;
            } if(hayTriunfo){
                if(c.getPalo() == triunfo){
                    if(ganadora.getPalo() != triunfo)
                        ganadora = c;
                    else{
                        if(c.getValor() > ganadora.getValor())
                            ganadora = c;
                    }
                }
            } if(yaHayLider){
                if(c.getPalo() == lider){
                    if(ganadora.getPalo() != lider){
                        ganadora = c;
                    } else {
                        if(c.getValor() > ganadora.getValor())
                            ganadora = c;
                    }
                }
            }

        }
        return ganadora;
    }

    /** Contructor de la Ronda */
    public Ronda(Lista<Jugador> jugadores, int jugadoresTotales, Carta[] baraja, Boolean fastMode){
        this.jugadores = jugadores;
        this.jugadoresTotales = jugadoresTotales;
        nRonda = 1;
        this.baraja = baraja;
        this.mesa = new Lista<>();
        ultimoGanadorRonda = jugadores.getPrimero();
        cartaBaraja = 0;
        Ronda.fastMode = fastMode;
    }

    /**
     * Establece el orden de los jugadores en base al jugador parametro.
     * @param jugador El jugador que debe ir primero.
     */
    private void estableceOrden(Jugador jugador){
        while(jugadores.getPrimero().getId() != jugador.getId()){
            jugadores.agregaFinal(jugadores.eliminaPrimero());
        }

        System.out.print("Ahora mismo el orden de jugadores es: ");
        for(Jugador j : jugadores){
            esperaTexto();
            if(j == jugadores.getUltimo()){
                System.out.printf("%s", j.toString());
                break;
            } 
            System.out.printf("%s -> ", j.toString());
        }
        System.out.println();
        espera500();
    }

    /** Reparte las cartas entre los jugadores. */
    public void repartirCartas(){

        System.out.println("Repariendo cartas...");

        espera500();

        for(Jugador j : jugadores){
            for(int i = 0; i < nRonda; i++){
                if(cartaBaraja == baraja.length) return;
                j.addCarta(baraja[cartaBaraja]);
                baraja[cartaBaraja].setDueño(j);
                cartaBaraja++;
            }
        }

        System.out.println("Cartas repartidas");
        esperaTexto();
    }

    /** Define el palo del triunfo */
    private void defPaloTriunfo(){
        try {
            System.out.println("Selecionando palo del triunfo...");
            esperaTexto();

            Carta cartaDef = baraja[cartaBaraja];
            hayTriunfo = true;
            if(cartaDef.getValor() == 14){
                System.out.printf("Al salir |MAGO| el %s escojera el palo del triunfo", barajeador.toString());
                esperaTexto();
                System.out.printf("El jugador %s escoje el Palo del Trinfo:\n 1.ROJO\n 2.VERDE\n 3.AZUL\n 4.AMARILLO\n", jugadores.toString());
                BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    switch(Integer.parseInt(in.readLine())){
                        case 1:     triunfo = Palo.ROJO; break;
                        case 2:     triunfo = Palo.VERDE; break;
                        case 3:     triunfo = Palo.AZUL; break;
                        case 4:     triunfo = Palo.AMARILLO; break;
                        default:    System.out.printf("Error al recibir opcion, el palo sera %s\n", (triunfo = Palo.getPaloRandom()).toString());
                    }
                    esperaTexto();
                    System.out.printf("Ingresaste el palo %s entonces ese es el palo de la ronda\n", triunfo.toString());
                    esperaTexto();
                } catch (Exception e) {
                    System.out.printf("Error al recibir opcion, el palo sera aleatoriamente: %s\n", (triunfo = Palo.getPaloRandom()).toString());
                }
            } else if(cartaDef.getValor() == 0){
                triunfo = null;
                hayTriunfo = false;
                System.out.println("Al salir un |BUFON| entocnes no habra triunfo.");
                esperaTexto();
            } else {
                triunfo = cartaDef.getPalo();
                System.out.printf("El palo del triunfo es %s de la carta %s\n", cartaDef.getPalo().toString(), cartaDef.toString());
                esperaTexto();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Al ya no haber cartas no habra triunfo.");
            esperaTexto();
            triunfo = null;
            hayTriunfo = false;
        }
    }

    /** Todos los jugadores apuestan */
    private void apuestaJugadores(){
        System.out.println("Ahora hay que apostar.");
        System.out.println();
        esperaTexto();
        for(Jugador j : jugadores){
            BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
                try {
                    System.out.printf("Jugador %d : %s, haz tu apuesta:\n", j.getId(), j.getName());
                    esperaTexto();
                    System.out.printf("Tus cartas:\n");
                    j.imprimeMano();
                    while(true){
                        try {
                            int i = Integer.parseInt(in.readLine());
                            if( i >= 0 && i <= nRonda){
                                System.out.printf("Haz apostado %d\n", i);
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
        espera500();
    }

    /** Barajea las cartas */
    private void barajear(Jugador jugador){
        if(jugador == null) barajeador = jugadores.getPrimero();
        barajeador = jugador;
        Random rand = new Random();

        System.out.println("Barajeando cartas...");
        espera500();

		
		for (int i = 0; i < baraja.length; i++) {
			int randomIndexToSwap = rand.nextInt(baraja.length);
			Carta temp = baraja[randomIndexToSwap];
			baraja[randomIndexToSwap] = baraja[i];
			baraja[i] = temp;
		}

        System.out.println("Cartas barajeadas");
        esperaTexto();
    }

    /** Jugamos una ronda dentro del juego. */
    public void juegaRonda(){

        System.out.println("====================================");
        System.out.printf("Ronda %d\n", nRonda);

        System.out.println();

        barajear(ultimoGanadorRonda);

        System.out.println();

        repartirCartas();

        System.out.println();

        defPaloTriunfo();

        System.out.println();

        apuestaJugadores();

        System.out.println();

        estableceOrden(ultimoGanadorRonda);

        for(int i = 0; i < nRonda; i++){
            Truco(i+1);
        }

        System.out.printf("Ronda %d terminada\n", nRonda);
        espera500();
        jugadores.mergeSort(((o1, o2) -> o1.getId() - o2.getId()));


        ultimoGanadorRonda = jugadores.getPrimero();
        for(Jugador j : jugadores){
            if(ultimoGanadorRonda.getGanadasPorRonda() < j.getGanadasPorRonda()){
                ultimoGanadorRonda = j;
            }
        }

        for(Jugador j : jugadores){
            if(j.getApuesta() == j.getGanadasPorRonda()){
                int puntuacionDeRonda = j.getGanadasPorRonda() * 10;
                System.out.printf("Bien, apostate correctamente, apostate y ganaste %s.\n", puntuacionDeRonda);
                esperaTexto();
                System.out.printf("Jugador %s has ganado esta ronda %d\n", j.toString(), puntuacionDeRonda);
                j.addPuntuacion(puntuacionDeRonda);
                j.resetGanadasPorRonda();
                System.out.printf("Tu puntuacion acutal es %d\n", j.getPuntuacion());
            } else{
                int puntuacionDeRonda = -10*(Math.abs(j.getApuesta() - j.getGanadasPorRonda()));
                System.out.printf("Haz apostado %d pero ganaste %d\n", j.getApuesta(), j.getGanadasPorRonda());
                esperaTexto();
                if(puntuacionDeRonda > 0)
                System.out.printf("Jugador %s has ganado en esta ronda %d\n", j.toString(), puntuacionDeRonda);
                else {
                    System.out.printf("Jugador %s has perdido en esta ronda %d\n", j.toString(), puntuacionDeRonda);
                }
                esperaTexto();
                j.addPuntuacion(puntuacionDeRonda);
                j.resetGanadasPorRonda();
                System.out.printf("Tu puntuacion acutal es %d\n", j.getPuntuacion());
            }
            j.limpiaMano();
            espera500();
        }

        for(Carta c : baraja){
            c.liberar();
        }

        nRonda++;

    }

    /** Finaliza las rondas */
    public void finaliza(){
        Lista<Jugador> ganadores = new Lista<>();
        int maxPuntuacion = 0;
        for(Jugador j : jugadores){
            if(maxPuntuacion < j.getPuntuacion())
                maxPuntuacion = j.getPuntuacion();
        }
        for(Jugador j : jugadores){
            if(j.getPuntuacion() == maxPuntuacion)
                ganadores.agregaFinal(j);
        }
        if(ganadores.getLongitud() == 1)
            System.out.printf("El ganador es %s\n", ganadores.getPrimero().toString());
        else{
            System.out.println("Hubo un empate.");
            for(Jugador j : jugadores){
                System.out.printf("Uno de los ganadores es %s\n", j.toString());
            }
        }
    }
}
