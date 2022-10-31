package ejercicios;

public class Prueba {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int numeroRandom = (int) (Math.random() * 10);
        Herramienta[] bancoHerramientas = new Herramienta[10];
        for (int i = 0; i < bancoHerramientas.length; i++) {
            bancoHerramientas[i] = new Herramienta();
            System.out.println(bancoHerramientas[i].getId());
        }

        //creamos 5 hilos alumnos
        Thread[] alumnos = new Thread[5];
        //cada hilo coge dos bancoHerramientas al azar
        for (int i = 0; i < alumnos.length; i++) {
            alumnos[i] = new Thread(new Alumno(bancoHerramientas[numeroRandom], bancoHerramientas[numeroRandom] , "Alumno " + (i+1)));
            alumnos[i].start();
        }

        //for con join
        for (int i = 0; i < alumnos.length; i++) {
            try {
                alumnos[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
