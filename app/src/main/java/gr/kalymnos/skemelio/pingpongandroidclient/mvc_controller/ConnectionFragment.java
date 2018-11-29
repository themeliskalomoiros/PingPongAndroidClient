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

    public interface OnConnectClickListener {
        void onConnectClicked(String host, int port);
    }

    private ConnectionViewMvc viewMvc;
    private OnConnectClickListener callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMvc = new ConnectionViewMvcImp(inflater, container);
        viewMvc.setOnConnectToServerClickListener(this);
        return viewMvc.getRootView();
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
