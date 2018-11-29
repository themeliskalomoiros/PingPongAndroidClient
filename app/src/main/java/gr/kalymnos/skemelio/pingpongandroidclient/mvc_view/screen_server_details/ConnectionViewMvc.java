package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_server_details;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ViewMvc;

public interface ConnectionViewMvc extends ViewMvc {

    void setHostInputText(String host);

    void setPortInputText(int port);

    interface OnConnectClickListener {
        void onConnectClicked(String host, int port);
    }

    void setOnConnectClickListener(OnConnectClickListener listener);
}
