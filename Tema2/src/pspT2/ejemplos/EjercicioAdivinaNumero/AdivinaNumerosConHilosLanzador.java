package pspT2.ejemplos.EjercicioAdivinaNumero;



import java.util.concurrent.ThreadLocalRandom;


class Random{
     public static int random;
     synchronized public int getRandom(){
        random = ThreadLocalRandom.current().nextInt(1,1000000);
        return random;
    }
}
class AdivinaNumerosConHilos implements Runnable {
    int numero;
    static int adivinado=0;
    String nombre;
    AdivinaNumerosConHilos(String nombre){
        this.nombre=nombre;
    }



        @Override
        public void run () {
        //El hilo intenta adivinar el numero
            Random random = new Random();
        int numeroAdivinado = ThreadLocalRandom.current().nextInt(1, 1000000);
        int contador = 0;

            System.out.println("Hola soy "+nombre);

            while(true){
                if (numeroAdivinado==random.getRandom()){
                    adivinado++;
                    break;

                }else {
                    contador++;


                }

            }

           // if para saber cual es el primer hilo que hizo break;
            if (adivinado==1){
                System.out.println("He acertado!! soy "+nombre+" y he necesitado "+contador+" intentos");
            }else{
                System.out.println("Ohhh, no he ganado ("+nombre+")"+" lo he intentado "+contador+" intentos");
            }






        }



}


public class AdivinaNumerosConHilosLanzador {
    static Random random;
    static int MAX_HILOS = 10;
    public static void main(String[] args) {
        long TInicio, TFin, tiempo;

        TInicio = System.currentTimeMillis();
        Integer numero;
        random= new Random();
        numero = random.getRandom();


        System.out.println("El numero a adivinar es: " + numero);

        Thread[] hilos = new Thread[MAX_HILOS];
        for (int i = 0; i < MAX_HILOS; i++) {
            hilos[i] = new Thread(new AdivinaNumerosConHilos("hilo "+(i+1)));
            hilos[i].setName("hilo " + (i + 1));
            hilos[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TFin = System.currentTimeMillis();
        tiempo = TFin - TInicio;

        System.out.println("\nTiempo de ejecución en milisegundos: " + tiempo);
    }
}


