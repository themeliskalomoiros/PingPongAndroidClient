package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;

public interface ConnectionViewMvc extends ToolbaredViewMvc {

    interface OnConnectToServerClickListener {
        void onConnectToServerClicked(String host, int port);
    }

    void setOnConnectToServerClickListener(OnConnectToServerClickListener listener);

    void showProgressBar();

    void hideProgressBar();
}
