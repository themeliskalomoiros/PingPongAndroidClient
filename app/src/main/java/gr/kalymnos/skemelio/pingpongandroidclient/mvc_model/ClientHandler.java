package gr.kalymnos.skemelio.pingpongandroidclient.mvc_model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/*
 *  This handler will update the UI thread about Client's messages.
 * */

public class ClientHandler extends Handler {

    public ClientHandler(Callback callback) {
        super(callback);
    }

    public void sendConnectionErrorMsg() {
        Message msg = obtainMessage();
        msg.what = ClientThread.CONNECTION_ERROR;
        sendMessage(msg);
    }

    public void sendReceivedPingMsg(String fromServer) {
        Message msg = obtainMessage();
        msg.what = ClientThread.RECEIVED_PING;
        msg.obj = fromServer;
        sendMessage(msg);
    }
}
