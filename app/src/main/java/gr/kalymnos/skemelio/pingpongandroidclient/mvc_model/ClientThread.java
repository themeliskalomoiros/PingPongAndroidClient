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

import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller.MainActivity.PING;

public class ClientThread extends Thread {
    private static final String TAG = "PANATHA";

    private ClientHandler handler;

    private static final int TIMEOUT = 500;
    public static final int INVALID_PORT = -1;

    private Socket socket;
    private String host;
    private int port;

    private PrintWriter out;
    private BufferedReader in;

    public ClientThread(String host, int port, Handler.Callback callback) {
        this.host = host;
        this.port = port;
        socket = new Socket();
        handler = new ClientHandler(callback);
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            Log.d(TAG, "Socket connected");
            handler.sendConnectionSuccessMsg();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String response;
            while ((response = in.readLine()).equals(PING)) {
                handler.sendReceivedPingMsg(response);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while Client was running.", e);
            handler.sendConnectionErrorMsg();
        } catch (NullPointerException e) {
            Log.d(TAG, "Server disconnected");
        } finally {
            shutdown();
        }
    }

    public void pong() {
        new Thread(() -> {
            out.println(MainActivity.PONG);
            handler.sendSentPongMsg();
        }).start();
    }

    public void shutdown() {
        if (socket != null) {
            try {
                socket.close();
                handler.sendEndOfConnectionMsg();
            } catch (IOException e) {
                Log.d(TAG, "Error while closing socket");
            }
        }
    }
}
