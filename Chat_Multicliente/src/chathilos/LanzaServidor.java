package chathilos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LanzaServidor implements Runnable {
    final public ServidorChat interfaz;

    public LanzaServidor(ServidorChat interfaz) {
        this.interfaz = interfaz;
    }

    ArrayList<HiloConexion> conexiones = new ArrayList<HiloConexion>();

    @Override
    public void run() {
        ServerSocket serverSocket;
        final int PUERTO = 9876;
        try {
            serverSocket = new ServerSocket(PUERTO);
            interfaz.escribirTexto("### Servidor iniciado");
            while (true) {
                //Se crea un hilo para atender a cada cliente que se conecte
                //new Thread(new AtiendeCliente(serverSocket.accept(), interfaz)).start();
                Socket conexion;
                conexion = serverSocket.accept();
                interfaz.escribirTexto("--- Petición de conexión recibida");
                HiloConexion hc = new HiloConexion(this, conexion);
                conexiones.add(hc);
                new Thread(hc).start();

            }
        } catch (IOException e) {
            System.out.println("Se ha producido algun error y no se ha iniciado el servidor");
            return;
        }

    }

    public void enviarMsg(String msg) {
        interfaz.escribirTexto("Envio msg " + msg);
        for (HiloConexion hc : conexiones) {
                hc.enviar(msg);
        }
    }

}
