package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import gr.kalymnos.skemelio.pingpongandroidclient.R;

public class ConnectionViewMvcImp implements ConnectionViewMvc {

    private View root;
    private ProgressBar progressBar;
    private FloatingActionButton connect;
    private EditText inputHost, inputPort;

    public ConnectionViewMvcImp(LayoutInflater inflater, ViewGroup container) {
        initializeViews(inflater, container);
    }

    private void initializeViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_connection, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        connect = root.findViewById(R.id.connect_fab);
        inputHost = root.findViewById(R.id.input_host);
        inputPort = root.findViewById(R.id.input_port);
    }

    @Override
    public void setOnConnectToServerClickListener(OnConnectClickListener listener) {
        connect.setOnClickListener(view -> {
            if (listener != null) {
                String host = inputHost.getText().toString();
                int port = Integer.parseInt(inputPort.getText().toString());
                listener.onConnectClicked(host, port);
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
