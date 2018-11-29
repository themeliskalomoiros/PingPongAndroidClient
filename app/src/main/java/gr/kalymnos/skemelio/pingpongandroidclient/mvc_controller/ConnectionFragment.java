package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details.ConnectionViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details.ConnectionViewMvcImp;

public class ConnectionFragment extends Fragment implements ConnectionViewMvc.OnConnectClickListener {

    public static final String EXTRA_HOST = "extra host";
    public static final String EXTRA_PORT= "extra port";

    public interface OnConnectClickListener {
        void onConnectClicked(String host, int port);
    }

    private ConnectionViewMvc viewMvc;
    private OnConnectClickListener callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMvc = new ConnectionViewMvcImp(inflater, container);
        viewMvc.setOnConnectClickListener(this);
        if (argsContainHostAndPort()){
            viewMvc.setHostInputText(getArguments().getString(EXTRA_HOST));
            viewMvc.setPortInputText(getArguments().getInt(EXTRA_PORT));
        }
        return viewMvc.getRootView();
    }

    private boolean argsContainHostAndPort() {
        return getArguments()!=null && getArguments().containsKey(EXTRA_HOST) && getArguments().containsKey(EXTRA_PORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnConnectClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnConnectClickListener.class.getSimpleName());
        }
    }


    @Override
    public void onConnectClicked(String host, int port) {
        if (callback != null)
            callback.onConnectClicked(host, port);
    }
}
