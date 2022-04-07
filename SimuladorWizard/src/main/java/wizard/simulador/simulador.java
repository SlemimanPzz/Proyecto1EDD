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
    /** Baraja */
    static Carta[] baraja;
    /**Buffered Reader para uso general */
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    /** La lista de jugadores */
    static Lista<Jugador> jugadores = new Lista<>();

    private static void esperaTexto(){
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {}
    }

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
    public static void iniciaJuego(){
        System.out.println("WIZARD");
        esperaTexto();
        System.out.println("¡Vamos a Jugar!");
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
                System.out.println("Error =(");
                esperaTexto();
                System.out.println("Cerrando aplicacion.");
                System.exit(0);
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
    }

    /**
     * Configuro todo el juego, agrega los jugadores con sus nombres e ID.
     * Y confugura el numero de rondas dependiende de los jugadores
     */
    public static void configuraJuego() {
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
        esperaTexto();
        System.out.println("Ahora vamos a generar los jugadores.");
        esperaTexto();
        int i = 1;
        while(i <= jugadoresTotales){
            System.out.printf("Ahora el jugador %d\n", i);
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

    public static void main(String[] args){
        // iniciaJuego();
        // configuraJuego();
        baraja = creaBaraja();
        System.out.println(baraja.length);
        for(Carta x : baraja){
            System.out.println(x.toString());
        }
    }
}
