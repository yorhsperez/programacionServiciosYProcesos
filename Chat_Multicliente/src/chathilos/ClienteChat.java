package chathilos;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteChat {
    public JFrame frmClienteChat;
    private JTextField campoNick;
    private JTextField campoTexto;

    private Socket conexion;
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;
    private JEditorPane panelInfo;

    /**
     * Launch the application.
     */
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

    /**
     * Create the application.
     */
    public ClienteChat() {
        initialize();
        conectar();
        recibir();
        cerrar();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize(){
        frmClienteChat = new JFrame();
        frmClienteChat.setTitle("Cliente Chat");
        frmClienteChat.setBounds(100,100,450,300);
        frmClienteChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelInfo = new JEditorPane();
        //frmClienteChat.getContentPane().add(panelInfo, BorderLayout.CENTER);
        panelInfo.setEditable(false);
        frmClienteChat.getContentPane().add(panelInfo, BorderLayout.CENTER);
        panelInfo.setContentType("text/html");

        JPanel panel = new JPanel();
        frmClienteChat.getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(2, 1, 2, 2));

        JPanel panel_1 = new JPanel();
        panel_1.setForeground(Color.RED);
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        panel.add(panel_1);

        JLabel nick = new JLabel("Nick");
        panel_1.add(nick);

        campoNick = new JTextField();
        campoNick.setEditable(false);
        campoNick.setText("");
        panel_1.add(campoNick);
        campoNick.setColumns(10);

        JPanel panel_2 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        panel.add(panel_2);

        JLabel etiquetaTexto = new JLabel("Texto");
        panel_2.add(etiquetaTexto);

        campoTexto = new JTextField();
        panel_2.add(campoTexto);
        campoTexto.setColumns(20);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviar();
            }
        });
        panel_2.add(botonEnviar);

    }

    public void escribir(String s){
        escribirColor(s, Color.BLACK);

    }
    private void recibir(){
        new Thread(new HiloRecibirCliente(this)).start();

    }
    public BufferedReader getEntrada(){
        return flujoEntrada;
    }
    //Para poder escribir en colores
    private void escribirColor(String s, Color c){
        Document doc = this.panelInfo.getDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        try{
            doc.insertString(doc.getLength(), s + "\n", aset);
        }catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    private void conectar(){
        while (this.campoNick.getText().equals("")){
            this.campoNick.setText(JOptionPane.showInputDialog(null,"Introduce tu apodo:"));
        }
        conexion = new Socket();
        direccion = new InetSocketAddress("localhost", 9876);
        try{
            conexion.connect(direccion);
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            flujoSalida = new PrintWriter(conexion.getOutputStream(), true);
            escribirColor("Conexión establecida con el servidor", Color.BLUE);
            flujoSalida.println("CON "+this.campoNick.getText());
            flujoSalida.flush();
        }catch (IOException e){
            System.out.println("Se ha producido algun error en la conexión");
            escribirColor("Se ha producido algun error en la conexión", Color.RED);
            return;

        }
    }
    //El metodo enviar se llama cuando se pulsa el boton enviar,donde se envia el mensaje al servidor

    private void enviar(){
        flujoSalida.println("MSG "+this.campoNick.getText()+" : "+this.campoTexto.getText());
        flujoSalida.flush();
        this.campoTexto.setText("");
    }



    //Si el cliente cierra la ventana se desconecta del servidor
    public void cerrar(){
        this.frmClienteChat.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                flujoSalida.println("EXI "+campoNick.getText());
                flujoSalida.flush();
            }
        });
    }


}
