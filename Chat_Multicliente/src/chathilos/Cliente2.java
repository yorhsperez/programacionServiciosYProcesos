package chathilos;

import java.awt.*;

public class Cliente2 {
 //VA A SER UN NUEVO CLIENTE CHAT

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClienteChat window = new ClienteChat();
                    window.frmClienteChat.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
