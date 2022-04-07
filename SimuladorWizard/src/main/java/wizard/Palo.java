package wizard;


/* Enumeracion para los posibles palos*/
public enum Palo {
    ROJO,
    VERDE,
    AZUL,
    AMARILLO;
    /**
     * toString() de los palos.
     */
    public String toString(){
        switch(this){
            case ROJO:      return "ROJO";
            case VERDE:     return "VERDE";
            case AZUL:      return "AZUL";
            case AMARILLO:  return "AMARILLO";
            default:    return "";
        }
    }

    /**
     * Nos regresa un palo dependiendo de la cadena suminstrada.
     * @param s Cadena de la que se desea el palo.
     * @return Palo que corresponde a la cadena.
     */
    public Palo getPalo(String s){
        switch (s){
            case "ROJO":        return ROJO;
            case "VERDE":       return VERDE;
            case "AZUL":        return AZUL;
            case "AMARILLO":    return AMARILLO;
            default: return null;
       }
    }

    /**
     * Regresa el color de fondo del palo correspondiente
     * @return una cadena ANSI del color de fondo
     */
    public String getBackgorundtColor(){
        switch(this){
            case ROJO:      return "\u001B[31m";
            case VERDE:     return "\u001B[32m";
            case AZUL:      return "\u001B[36m";
            case AMARILLO:  return "\u001B[33m";
            default:    return "";
        }
    }

    public static Palo getPaloRandom(){
        double i = Math.random();
        if(i < .25){
            return Palo.AMARILLO;
        } else if(i < .50){
            return Palo.AZUL;
        } else if(i < .75){
            return Palo.ROJO;
        } else {
            return Palo.VERDE;
        }
    }
}
