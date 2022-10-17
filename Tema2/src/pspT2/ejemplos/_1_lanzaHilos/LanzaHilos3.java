package pspT2.ejemplos._1_lanzaHilos;

import java.util.ArrayList;

public class LanzaHilos3 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ArrayList<Thread> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add(new Thread(new Hilo2("Hilo" + (i + 1))));
        }


        for (Thread a : arrayList) {
            a.start();
        }
        try {

            for (Thread t : arrayList) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Proceso interrumpido");
        }
        System.out.println("Proceso principal terminado");
    }

}
