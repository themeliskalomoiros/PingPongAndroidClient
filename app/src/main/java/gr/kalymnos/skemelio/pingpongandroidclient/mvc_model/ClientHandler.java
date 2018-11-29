package gr.kalymnos.skemelio.pingpongandroidclient.mvc_model;

import android.os.Handler;
import android.os.Message;

/*
 *  This handler will update the UI thread about Client's messages.
 * */

public class ClientHandler extends Handler {

    public static final int RECEIVED_PING = 110;
    public static final int CONNECTION_ERROR = 101;
    public static final int SEND_PONG = 111;

    public ClientHandler(Callback callback) {
        super(callback);
    }

    public void sendConnectionErrorMsg() {
        Message msg = obtainMessage();
        msg.what = CONNECTION_ERROR;
        sendMessage(msg);
    }

    public void sendReceivedPingMsg(String fromServer) {
        Message msg = obtainMessage();
        msg.what = RECEIVED_PING;
        msg.obj = fromServer;
        sendMessage(msg);
    }
}
