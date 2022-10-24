package pspT2.ejemplos.ejercicioCarreraDeCaballos;

class Caballo implements Runnable {
    private int pasos;
    private String nombre;

    public Caballo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        while (pasos < 100) {
            pasos += (int) (Math.random() * 5 + 1);
            System.out.println(nombre + " ha dado " + pasos + " pasos.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nombre + " ha llegado a la meta.");
    }
}
public class CarreraDeCaballos {

    //programa que lance 5 hilos de caballos y que cada uno de ellos avance de forma aleatoria
    //entre 1 y 5 pasos, y que se detenga cuando alcance 100 pasos.
    public static void main(String[] args) {
        Thread[] caballos = new Thread[5];
        for (int i = 0; i < caballos.length; i++) {
            caballos[i] = new Thread(new Caballo("Caballo " + (i + 1)));
            caballos[i].start();
        }
    }


}
