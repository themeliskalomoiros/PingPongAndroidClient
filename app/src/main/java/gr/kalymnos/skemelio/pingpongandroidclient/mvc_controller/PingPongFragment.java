package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_ping_pong.PingPongViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_ping_pong.PingPongViewMvcImp;

public class PingPongFragment extends Fragment implements PingPongViewMvc.OnSendClickListener {

    public interface OnSendClickListener {
        void onSendClicked();
    }

    private PingPongViewMvc viewMvc;
    private OnSendClickListener callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMvc = new PingPongViewMvcImp(inflater, container);
        viewMvc.setOnSendClickListener(this);
        return viewMvc.getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnSendClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnSendClickListener.class.getSimpleName());
        }
    }


    @Override
    public void onSendClicked() {
        if (callback != null)
            callback.onSendClicked();
    }
}
