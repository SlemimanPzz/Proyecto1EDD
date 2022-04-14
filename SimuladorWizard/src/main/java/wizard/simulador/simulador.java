package wizard.simulador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import wizard.Carta;
import wizard.Jugador;
import wizard.Lista;
import wizard.Palo;

/**
 * Simulador por terminal del Juego Wizard.
 *
 */
public class simulador {

    /* Rondas totales que se jugaran */
    static int rondasTotales;
    /* Jugadores totales que tendra el juego */
    static int jugadoresTotales;
    /** Palo del triunfo */
    static Palo triunfo;
    /**Palo lider */
    static Palo lider;
    /**Buffered Reader para uso general */
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    /** La lista de jugadores */
    static Lista<Jugador> jugadores = new Lista<>();
    /** Modo rapido de la aplicacion*/
    static boolean fastMode;


    /** Crea una pausa en la ejecucion para cuando no hay fast mode */
    private static void esperaTexto(){
        if(fastMode) return;
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {}
    }

    /**
     * Crea una baraja con cartas. Utilizando arreglos y {@link Carta}.
     * @return Regresa un arreglo de cartas, una baraja.
     */
    private static Carta[] creaBaraja(){
        Carta[]  baraja = new Carta[60];
        int n = 0;
        for(Palo palo : Palo.values()){
            for(int i = 0; i < 15; i++){
                baraja[n] = new Carta(i, palo); 
                n++;
            }
        }
        return baraja;
    }

    /**
     * Empezamos el juego de manera correcta, aseguramos que los numeros de jugadores
     * sea el correcto.
     */
    private static void iniciaJuego(){
        System.out.println("WIZARD");
        System.out.println();
        esperaTexto();
        System.out.println("¡Vamos a Jugar!");
        System.out.println();
        esperaTexto();
        System.out.println("Seleciona el numero de jugadores(3 - 6):");
        while(true){
            int i = 0;
            try {
                i = Integer.parseInt(bf.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Se necesita un numero.");
                continue;
            } catch (IOException e) {
                System.out.println("Error :(");
                esperaTexto();
                System.out.println("Cerrando aplicacion.");
                System.exit(1);
            }
            if(i <= 6 && i >= 3){
                jugadoresTotales = i;
                break;
            } else{
                System.out.println("Numero de jugadores no valido.");
                esperaTexto();
                System.out.println("Se necesitan de 3 a 6 jugadores.");
            }
        }
        System.out.printf("Seran entonces %d jugadores\n", jugadoresTotales);
        esperaTexto();
    }


    /**
     * Configura el juego en el caso de de usarse el Fast Mode.
     */
    private static void configuraJuegoFastMode(){
        switch(jugadoresTotales){
            case 3:     rondasTotales = 20; break;
            case 4:     rondasTotales = 15; break;
            case 5:     rondasTotales = 12; break;
            case 6:     rondasTotales = 10; break;
            default:
                System.out.println("El numero de jugadores no fue correcto. Se salda del juego.");
                System.exit(0);
        }
        System.out.printf("Al ser %d jugadores se jugaran %d rondas\n",jugadoresTotales, rondasTotales);
        System.out.println("Ahora vamos a generar los jugadores.");

        int i = 1;

        while(i <= jugadoresTotales){
            System.out.printf("Ahora el jugador %d\n", i);
            jugadores.agregaFinal(new Jugador(i, ""));
            System.out.printf("Jugador %d  agregado.\n", i);
            i++;
            

        }
    }

    /**
     * Configuro todo el juego, agrega los jugadores con sus nombres e ID.
     * Y confugura el numero de rondas dependiende de los jugadores
     */
    private static void configuraJuego() {
        switch(jugadoresTotales){
            case 3:     rondasTotales = 20; break;
            case 4:     rondasTotales = 15; break;
            case 5:     rondasTotales = 12; break;
            case 6:     rondasTotales = 10; break;
            default:
                System.out.println("El numero de jugadores no fue correcto. Se salda del juego.");
                System.exit(0);
        }
        esperaTexto();
        System.out.printf("Al ser %d jugadores se jugaran %d rondas\n",jugadoresTotales, rondasTotales);
        esperaTexto();
        System.out.println("Ahora vamos a generar los jugadores.");
        esperaTexto();
        int i = 1;
        while(i <= jugadoresTotales){
            System.out.printf("Ahora el jugador %d\n", i);
            esperaTexto();
            System.out.printf("Jugador %d escoje tu nombre: ", i);

            String name;

            /** While donde el jugador escogera su nombres */
            while(true){
                try {
                    name = bf.readLine();
                } catch (IOException e) {
                    System.out.println("Hubo un error. Su nombre sera:");
                    System.out.printf("%s\n", Integer.toString(i));
                    name = Integer.toString(i);
                    break;
                }
                System.out.printf("Tu nombre sera %s.\n", name);
                esperaTexto();
                System.out.println("¿Seguro que quieres este nombre?\n 1. Si\n 2. No");
                esperaTexto();
                try {
                    if(Integer.parseInt(bf.readLine()) == 1){
                        break;
                    } else{
                        System.out.println("Vuelve a escojer tu nombre.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Hubo un error, vuelve a escojer nombre");
                    continue;
                } catch (IOException e) {
                    System.out.println("Hubo un error, vuelve a escojer nombre");
                    continue;}
            }

            jugadores.agregaFinal(new Jugador(i, name));
            esperaTexto();
            System.out.printf("Jugador %d: %s agregado.\n", i, name);
            i++;
            

        }

    }

    /**
     * Impreme el uso del programa y cierra la aplicacion.
     */
    private static void uso(){
        System.out.println("Utilizar la bandera [-f|-n] para determinar la velocidad que se presenta el texto.");
        System.exit(1);
    }

    public static void main(String[] args){
        if(args.length > 1 )
            uso();

        if(args.length == 1){
            String bandera = args[0];
            if(bandera.equals("-f")){
                fastMode = true;
                System.out.println("Fast Mode seleccionado.");
            }else if(bandera.equals("-n")){
                fastMode = false;
                System.out.println("Normal Mode seleccinado.");
            } else{
                uso();
            }
        }

        iniciaJuego();
        if(fastMode)
            configuraJuegoFastMode();
        else{
            configuraJuego();
        }
        esperaTexto();
        System.out.println();
        System.out.println("Ahora a empezar a jugar");
        System.out.println();
        esperaTexto();
        Ronda rondas = new Ronda(jugadores, jugadoresTotales, creaBaraja(), fastMode);
        


        for(int i = 0; i < rondasTotales; i++)
            rondas.juegaRonda();

        rondas.finaliza();


        }
        


        
    }
