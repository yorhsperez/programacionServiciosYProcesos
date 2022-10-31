package ejercicios;

    //Estamos en un taller de practicas,donde estan trabajando 5 alumnos y en el que tenemos un banco de herramientas
    //para compartir entre todos los 5 alumnos.
    //Herramienta bancoHerramientas = new Herramienta();
    //los alumnos estan continuamente haciendo practicas, para las cuales eligen dos herramientas al azar y cuando las tienen
    //trabajan un tiempo aleatorio entre 2 y 3 segundos,y despues descansan un tiempo aleatorio entre 1 y 2 segundos despues de devolver las herramientas.
    //Para ello emprezamos con la clase Herramienta
class Herramienta{
    //Unicamente necesitamos un identificador para los mensajes
     private static int id=-1;

     Herramienta(){
         incrementarId();
     }


     //getter de Id
        public int getId() {
            return id;
        }
        //setter de Id
        public void setId(int id) {
            this.id = id;
        }


        public static void incrementarId() {
            id++;
        }

    }

class TallerDePracticas{
    //Metodo con interbloqueo para que un hilo no pueda acceder a un recurso mientras otro hilo lo esta usando
    public static boolean herramientaEnUso(Herramienta herramienta1,Herramienta herramienta2){

        boolean resultado = false;
        synchronized(herramienta1){
            synchronized(herramienta2){
                if (herramienta1.getId()!=herramienta2.getId()) {
                    resultado = true;
                }

            }
        }
        return resultado;

    }

    }

   class Alumno implements Runnable {


       private Herramienta herramienta1;
       private Herramienta herramienta2;
       private int tiempoTrabajando;
       private int tiempoDescansando;
       private String nombreDelHilo;


       Alumno(Herramienta herramienta11, Herramienta herramienta22, String nombreDelHilo) {

           this.herramienta1 = herramienta11;
           this.herramienta2 = herramienta22;
           this.nombreDelHilo = nombreDelHilo;
       }


       @Override
       public void run() {
           //El hilo esta continuamente ejecutando TallerDePracticas.herramientaEnUso
           //Escogen dos herramientas al azar y cuando las tienen trabajan un tiempo aleatorio entre 2 y 3 segundos
           //Despues de trabajar,el hilo descansa un tiempo aleatorio entre 1 y 2 segundos y devuelve las herramientas
           while (true) {
               if (TallerDePracticas.herramientaEnUso(herramienta1, herramienta2)) {
                   System.out.println("El alumno " + nombreDelHilo + " esta trabajando con las herramientas " + herramienta1.getId() + " y " + herramienta2.getId());
                   tiempoTrabajando = (int) (Math.random() * 2 + 2);
                   tiempoDescansando = (int) (Math.random() * 2 + 1);
                   try {
                       Thread.sleep(tiempoTrabajando * 1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("El alumno " + nombreDelHilo + " esta descansando");
                   try {
                       Thread.sleep(tiempoDescansando * 1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }


               }
           }

       }
   }








