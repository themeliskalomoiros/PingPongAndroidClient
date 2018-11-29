package gr.kalymnos.skemelio.pingpongandroidclient.mvc_controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import gr.kalymnos.skemelio.pingpongandroidclient.R;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main.MainScreenViewMvcImp;

public class MainActivity extends AppCompatActivity {

    private ToolbaredViewMvc viewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
    }

    private void setupUi() {
        viewMvc = new MainScreenViewMvcImp(LayoutInflater.from(this),null);
        viewMvc.bindToolbarTitle("Connection Phase");
        setSupportActionBar(viewMvc.getToolbar());
        setContentView(viewMvc.getRootView());
    }
}
