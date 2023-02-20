package chathilos;

import java.io.IOException;

public class HiloRecibirCliente implements Runnable {
    private ClienteChat cliente;

    public HiloRecibirCliente(ClienteChat cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        while (true) {
            //Leer del flujo y escribir en el chat

            try {
                String lectura = cliente.getEntrada().readLine();
                cliente.escribir(lectura);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
