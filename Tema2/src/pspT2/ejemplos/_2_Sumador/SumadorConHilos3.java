package pspT2.ejemplos._2_Sumador;

class Acumulador {
    long acumulador = 0;

    void acumular(long l) {
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
    static int MAX_HILOS = 10;

    public static void main(String[] args) {
        suma = new Acumulador();

        double n1 = 1.0;
        double n2 = 100000;
		double paso = (n2 - n1) / MAX_HILOS;
//		Thread t1=new Thread(new HiloSumador3(1,33333,suma));
//		Thread t2=new Thread(new HiloSumador3(33334,66666,suma));
//		Thread t3=new Thread(new HiloSumador3(66667,100000,suma));
//		t1.start();
//		t2.start();
//		t3.start();
        Thread[] hilos = new Thread[MAX_HILOS];
        for (int i = 0; i < MAX_HILOS; i++) {
            System.out.println("Lanzando hilo desde " + n1 + " a " + (n1 + paso));
            hilos[i] = new Thread(new HiloSumador3((int) n1, (int) (n1 + paso), suma));
            hilos[i].setName("hilo" + (i + 1));
            hilos[i].start();
            n1 = n1 + paso;

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

    }
}
