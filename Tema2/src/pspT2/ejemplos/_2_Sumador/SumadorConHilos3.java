package pspT2.ejemplos._2_Sumador;

class Acumulador {
    long acumulador = 0;

     synchronized public void acumular(long l) {
        acumulador += l;
    }

    public long getAcumulador() {
        return acumulador;
    }
}

class HiloSumador3 implements Runnable {

    int n1, n2;
    Acumulador acu;

    public HiloSumador3(int n1, int n2, Acumulador a) {
        this.n1 = n1;
        this.n2 = n2;
        this.acu = a;
    }

    @Override
    public void run() {
        long suma = 0;
        for (int i = n1; i <= n2; i++) {

            suma += i;
        }
        acu.acumular(suma);
    }
}

public class SumadorConHilos3 {

    static Acumulador suma;
    static int MAX_HILOS = 200;

    public static void main(String[] args) {
        long TInicio, TFin, tiempo;
        TInicio = System.currentTimeMillis();
        suma = new Acumulador();

        long n1 = 1;
        long n2 = 100000;
		long paso = (n2 - n1) / MAX_HILOS;



        Thread[] hilos = new Thread[MAX_HILOS];
        for (int i = 0; i < MAX_HILOS; i++) {
            System.out.println("Lanzando hilo"+ (i+1) +"desde " + n1 + " a " + (n1 + paso));
            hilos[i] = new Thread(new HiloSumador3((int) n1, (int) (n1 + paso), suma));
            hilos[i].setName("hilo" + (i + 1));
            hilos[i].start();
            n1 = n1 + paso+1;

        }


        try {
            for (int i = 0; i < MAX_HILOS; i++) {
				hilos[i].join();
            }


        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Suma:" + suma.getAcumulador());

        TFin = System.currentTimeMillis();
        tiempo = TFin - TInicio;

        System.out.println("\nTiempo de ejecución en milisegundos: " + tiempo);

    }
}
