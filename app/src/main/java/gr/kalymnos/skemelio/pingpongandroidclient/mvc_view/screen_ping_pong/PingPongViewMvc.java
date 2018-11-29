package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_ping_pong;

import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ViewMvc;

public interface PingPongViewMvc extends ViewMvc {

    interface OnSendClickListener {
        void onSendClicked();
    }

    void setOnSendClickListener(OnSendClickListener listener);

    void showPing();

    void showPong();

    void animateBall();
}
