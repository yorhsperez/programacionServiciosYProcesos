package chathilos;

import javax.swing.*;
import java.awt.*;

public class ServidorChat {
    private JFrame frame;
    private JTextArea info;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServidorChat window = new ServidorChat();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**
     * Create the application.
     */
    public ServidorChat() {
        initialize();
    }
    /**
     * Initialize the contents of the frame.
     */
    public void initialize(){
        frame = new JFrame();
        frame.setBounds(100,100,450,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        info = new JTextArea();
        frame.getContentPane().add(getInfo(), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Servidor de chat");
        panel.add(lblNewLabel);
        //En la inicialización del servidor se crea un hilo que se encarga de escuchar
        //si hay algún cliente conectado
        new Thread(new LanzaServidor(this)).start();
    }
    public JTextArea getInfo() {
        return info;
    }
    //Metodo para que se pueda realizar esta modificación desde otro hilo
    public void escribirTexto(String s){
        info.append(s+"\n");
    }


}