package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientThread;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvcImp;

import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller.ConnectionFragment.EXTRA_HOST;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller.ConnectionFragment.EXTRA_PORT;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientHandler.CONNECTION_ERROR;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientHandler.CONNECTION_SUCCESS;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientHandler.END_OF_CONNECTION;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientHandler.RECEIVED_PING;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientHandler.SENT_PONG;
import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientThread.INVALID_PORT;

public class MainActivity extends AppCompatActivity
        implements ConnectionFragment.OnConnectClickListener, PingPongFragment.OnSendClickListener, Handler.Callback {

    public static final String PING = "PING";
    public static final String PONG = "PONG";

    private MainScreenViewMvc viewMvc;
    private ClientThread client;
    private PingPongFragment pingPongFragment;

    private String cachedHost;
    private int cachedPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
    }

    private void setupUi() {
        viewMvc = new MainScreenViewMvcImp(LayoutInflater.from(this), null);
        viewMvc.bindToolbarTitle("Connection Phase");
        setSupportActionBar(viewMvc.getToolbar());
        setContentView(viewMvc.getRootView());
        getSupportFragmentManager().beginTransaction()
                .replace(viewMvc.getFragmentContainerId(), new ConnectionFragment())
                .commit();
    }

    @Override
    public void onConnectClicked(String host, int port) {
        if (!TextUtils.isEmpty(host) && port != INVALID_PORT) {
            if (client == null) {
                client = new ClientThread(host, port, this);
                client.start();
                cachedHost = host;
                cachedPort = port;
            }
        } else {
            Snackbar.make(viewMvc.getRootView(), "Fill in all the fields.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSendClicked() {
        if (client != null)
            client.pong();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case RECEIVED_PING:
                if (pingPongFragment != null)
                    pingPongFragment.showPing();
                break;
            case SENT_PONG:
                if (pingPongFragment != null)
                    pingPongFragment.showPong();
                break;
            case CONNECTION_SUCCESS:
                getSupportFragmentManager().beginTransaction()
                        .replace(viewMvc.getFragmentContainerId(), pingPongFragment = new PingPongFragment())
                        .commit();
                break;
            case CONNECTION_ERROR:
                Snackbar.make(viewMvc.getRootView(), "Could not connect to serve", Snackbar.LENGTH_LONG).show();
                break;

            case END_OF_CONNECTION:
                ConnectionFragment connectionFragment = new ConnectionFragment();
                if (!TextUtils.isEmpty(cachedHost) && cachedPort != INVALID_PORT) {
                    Bundle args = new Bundle();
                    args.putString(EXTRA_HOST, cachedHost);
                    args.putInt(EXTRA_PORT, cachedPort);
                    connectionFragment.setArguments(args);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(viewMvc.getFragmentContainerId(), connectionFragment);
                break;
        }
        return true;
    }
}
