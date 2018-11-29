package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import gr.kalymnos.skemelio.pingpongandroidclient.R;

import static gr.kalymnos.skemelio.pingpongandroidclient.mvc_model.ClientThread.INVALID_PORT;

public class ConnectionViewMvcImp implements ConnectionViewMvc {

    private View root;
    private ProgressBar progressBar;
    private FloatingActionButton connect;
    private EditText inputHost, inputPort;

    public ConnectionViewMvcImp(LayoutInflater inflater, ViewGroup container) {
        initializeViews(inflater, container);
    }

    @Override
    public void setHostInputText(String host) {
        inputHost.setText(host);
    }

    @Override
    public void setPortInputText(int port) {
        inputHost.setText(String.valueOf(port));
    }

    @Override
    public void setOnConnectClickListener(OnConnectClickListener listener) {
        connect.setOnClickListener(view -> {
            if (listener != null) {
                listener.onConnectClicked(getHost(), getPort());
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

    private String getHostInput() {
        if (!TextUtils.isEmpty(inputHost.getText())) {
            inputHost.getText().toString();
        }
        return null;
    }

    private String getHost() {
        return inputHost.getText().toString();
    }

    private int getPort() {
        try {
            return Integer.parseInt(inputPort.getText().toString());
        } catch (NumberFormatException e) {
            return INVALID_PORT;
        }
    }

    private void initializeViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_connection, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        connect = root.findViewById(R.id.connect_fab);
        inputHost = root.findViewById(R.id.input_host);
        inputPort = root.findViewById(R.id.input_port);
    }
}
