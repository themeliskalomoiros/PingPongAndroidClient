package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import gr.kalymnos.skemelio.pingpongandroidclient.R;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvcImp;

public class MainActivity extends AppCompatActivity
        implements ConnectionFragment.OnConnectClickListener, PingPongFragment.OnSendClickListener {

    private MainScreenViewMvc viewMvc;

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

    }

    @Override
    public void onSendClicked() {

    }
}
