package gr.kalymnos.skemelio.pingpongandroidclient.mvc_model;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller.MainActivity;

public class ClientThread extends Thread {
    private static final String TAG = "SKEMELIO";

    private ClientHandler handler;

    private static final int TIMEOUT = 500;
    public static final int INVALID_PORT = -1;
    private Socket socket;
    private String host;
    private int port;

    public ClientThread(String host, int port, Handler.Callback callback) {
        this.host = host;
        this.port = port;
        socket = new Socket();
        handler = new ClientHandler(callback);
    }

    @Override
    public void run() {
        BufferedReader in = null;
        String fromServer = null;
        try {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((fromServer = in.readLine()).equals(MainActivity.PING)) {
                handler.sendReceivedPingMsg(fromServer);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while Client was running.", e);
            handler.sendConnectionErrorMsg();
        }
    }

    public void pong() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            new Thread(() -> out.write(MainActivity.PONG)).start();
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining output stream from socket.", e);
        }
    }
}
