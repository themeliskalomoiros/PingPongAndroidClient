package gr.kalymnos.skemelio.pingpongandroidclient.mvc_model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Key;

public class ClientThread extends Thread {
    private static final String TAG = "SKEMELIO";

    public static final String PING = "PING";
    public static final String PONG = "PONG";
    public static final int CONNECTION_ERROR = 101;
    public static final int RECEIVED_PING = 110;
    public static final int SEND_PONG = 111;

    private ClientHandler handler;

    private static final int TIMEOUT = 500;
    public static final int INVALID_PORT = -1;
    private Socket socket;
    private String host;
    private int port;

    public ClientThread(String host, int port) {
        this.host = host;
        this.port = port;
        socket = new Socket();
    }

    @Override
    public void run() {
        BufferedReader in = null;
        String fromServer = null;
        try {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((fromServer = in.readLine()).equals(PING)) {
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
            new Thread(() -> out.write(PONG)).start();
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining output stream from socket.", e);
        }
    }
}
