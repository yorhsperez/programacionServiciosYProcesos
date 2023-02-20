package chathilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloConexion implements Runnable{
    private Socket conexion;
    private LanzaServidor servidor;

    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    public HiloConexion(LanzaServidor servidor, Socket conexion) {
        this.servidor = servidor;
        this.conexion = conexion;
        //new Thread(this).start();

    }
    @Override
    public void run() {
        try{
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            flujoSalida = new PrintWriter(conexion.getOutputStream(), true);
            while (true) {
                String lectura = flujoEntrada.readLine();
                String comando = lectura.substring(0, 3);
                System.out.println("Lectura: " + lectura+ " comando: " + comando);
                if (comando.equals("MSG")) {
                    servidor.enviarMsg(lectura.substring(4));
                }
                if (comando.equals("CON")) {
                    servidor.interfaz.escribirTexto("Se ha conectado : " + lectura.substring(4));
                }
                if (comando.equals("EXI")) {
                    servidor.interfaz.escribirTexto("Se ha desconectado : " + lectura.substring(4));
                    break;
                }
            }

        }catch (IOException e){
            System.out.println("No se pudo crear algun flujo");
            return;
        }finally {
            try {
                flujoEntrada.close();
                flujoSalida.close();
                conexion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //El metodo enviar,envia por el flujo de salida el mensaje que recibe como parametro
    public void enviar(String msg) {
        flujoSalida.println(msg);

    }
}
