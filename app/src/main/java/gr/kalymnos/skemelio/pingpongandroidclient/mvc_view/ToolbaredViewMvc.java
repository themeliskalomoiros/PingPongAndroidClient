package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view;

import android.support.v7.widget.Toolbar;

public interface ToolbaredViewMvc extends ViewMvc {
    Toolbar getToolbar();

    void bindToolbarTitle(String title);

    void bindToolbarSubtitle(String subtitle);
}
