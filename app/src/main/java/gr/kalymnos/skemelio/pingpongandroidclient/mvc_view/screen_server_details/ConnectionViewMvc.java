package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ViewMvc;

public interface ConnectionViewMvc extends ViewMvc {

    interface OnConnectClickListener {
        void onConnectClicked(String host, int port);
    }

    void setOnConnectToServerClickListener(OnConnectClickListener listener);

    void showProgressBar();

    void hideProgressBar();
}
