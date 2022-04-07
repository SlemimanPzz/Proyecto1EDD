package wizard;

public class Jugador {
    private int id;
    private String name;
    private Lista<Carta> mano;
    private int puntuacion;
    private int apuesta;

    public Jugador(int id, String name){
        this.id = id;
        this.name = name;
        puntuacion = 0;
        apuesta = 0;
        mano = new Lista<>();
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }
    public void setMano(Lista<Carta> mano) {
        this.mano = mano;
    }
    public void addCarta(Carta carta){
        mano.agregaFinal(carta);
    }
    public void addPuntuacion(int puntuacion) {
        this.puntuacion += puntuacion;
    }

    public int getApuesta() {
        return apuesta;
    }
    public int getId() {
        return id;
    }
    public Lista<Carta> getMano() {
        return mano;
    }
    public String getName() {
        return name;
    }
    public int getPuntuacion() {
        return puntuacion;
    }

    
}
