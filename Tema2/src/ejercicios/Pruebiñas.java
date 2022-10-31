package ejercicios;

public class Pruebiñas {
    public static void main(String[] args) {
      Herramienta[] bancoHerramientas1 = new Herramienta[10];

      for (int i = 0; i < bancoHerramientas1.length; i++) {
        bancoHerramientas1[i] = new Herramienta();
          System.out.println(bancoHerramientas1[i].getId());
      }



    }
}
