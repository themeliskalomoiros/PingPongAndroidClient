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
            String fromServer = null;
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            Log.d(TAG, "Socket connected");
            handler.sendConnectionSuccessMsg();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while ((fromServer = in.readLine()).equals(MainActivity.PING)) {
                Log.d(TAG, "Pinged!");
                handler.sendReceivedPingMsg(fromServer);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while Client was running.", e);
            handler.sendConnectionErrorMsg();
        } finally {
            closeStreams();
            handler.sendEndOfConnectionMsg();
        }
    }

    private void closeStreams() {
        if (in != null) {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close input stream", e);
            }
        }
    }

    public void pong() {
        new Thread(() -> {
            out.write(MainActivity.PONG);
            Log.d(TAG, "Wrote a pong");
        }).start();
    }
}
