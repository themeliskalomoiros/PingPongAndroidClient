package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import gr.kalymnos.skemelio.pingpongandroidclient.R;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvcImp;

import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientThread.INVALID_PORT;

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
        if (!TextUtils.isEmpty(host) && port != INVALID_PORT) {
            
        } else {
            Snackbar.make(viewMvc.getRootView(), "Fill in all the fields.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSendClicked() {
        Toast.makeText(this, "Send button clicked", Toast.LENGTH_SHORT).show();
    }
}
