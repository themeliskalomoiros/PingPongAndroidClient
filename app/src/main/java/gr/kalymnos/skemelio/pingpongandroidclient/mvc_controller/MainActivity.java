package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.IOException;

import gr.kalymnos.skemelio.pingpongandroidclient.R;
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
        implements ConnectionFragment.OnConnectClickListener, PingPongFragment.OnSendClickListener, Handler.Callback, MediaPlayer.OnPreparedListener {

    public static final String PING = "PING";
    public static final String PONG = "PONG";
    private static final String TAG = "PANATHA";

    private MediaPlayer player;
    private boolean isPlayerPrepared;

    private MainScreenViewMvc viewMvc;
    private ClientThread client;

    private String cachedHost;
    private int cachedPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        initializeMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.shutdown();
        }

        if (player != null) {
            player.release();
        }
    }

    @Override
    public void onConnectClicked(String host, int port) {
        if (!TextUtils.isEmpty(host) && port != INVALID_PORT) {
            if (client == null) {
                viewMvc.showProgressBar();
                client = new ClientThread(cachedHost = host, cachedPort = port, this);
                client.start();
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
    public void onPrepared(MediaPlayer mediaPlayer) {
        isPlayerPrepared = true;
        Log.d(TAG, "Player prepared");
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case RECEIVED_PING:
                if (isCurrentFragmentPingPongFragment()) {
                    PingPongFragment fragment = (PingPongFragment) getSupportFragmentManager().findFragmentById(viewMvc.getFragmentContainerId());
                    fragment.showPing();
                    playSound();
                }
                break;
            case SENT_PONG:
                if (isCurrentFragmentPingPongFragment()) {
                    PingPongFragment fragment = (PingPongFragment) getSupportFragmentManager().findFragmentById(viewMvc.getFragmentContainerId());
                    fragment.showPong();
                    playSound();
                }
                break;
            case CONNECTION_SUCCESS:
                getSupportFragmentManager().beginTransaction()
                        .replace(viewMvc.getFragmentContainerId(), new PingPongFragment())
                        .commit();
                viewMvc.hideProgressBar();
                viewMvc.bindToolbarTitle("Ping pong");
                break;
            case CONNECTION_ERROR:
                client = null;
                viewMvc.hideProgressBar();
                Snackbar.make(viewMvc.getRootView(), "Could not connect to serve", Snackbar.LENGTH_LONG).show();
                break;

            case END_OF_CONNECTION:
                client = null;
                ConnectionFragment connectionFragment = new ConnectionFragment();
                if (!TextUtils.isEmpty(cachedHost) && cachedPort != INVALID_PORT) {
                    Bundle args = new Bundle();
                    args.putString(EXTRA_HOST, cachedHost);
                    args.putInt(EXTRA_PORT, cachedPort);
                    connectionFragment.setArguments(args);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(viewMvc.getFragmentContainerId(), connectionFragment)
                        .commit();
                break;
        }
        return true;
    }

    private void playSound() {
        if (isPlayerPrepared) {
            if (player.isPlaying())
                player.pause();
            player.start();
        }
    }

    private boolean isCurrentFragmentPingPongFragment() {
        return getSupportFragmentManager().findFragmentById(viewMvc.getFragmentContainerId()) instanceof PingPongFragment;
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // Solution here https://stackoverflow.com/questions/4896223/how-to-get-an-uri-of-an-image-resource-in-android
        Uri soundFile = Uri.parse("android.resource://gr.kalymnos.skemelio.pingpongandroidclient/" + R.raw.metal_ping);
        try {
            player.setDataSource(this, soundFile);
            player.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, "Error when setting datasourse and prepareAsync", e);
        }
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
}
