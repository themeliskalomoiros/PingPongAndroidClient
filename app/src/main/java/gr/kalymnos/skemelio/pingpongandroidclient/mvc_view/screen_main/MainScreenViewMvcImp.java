package gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.screen_main;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import gr.kalymnos.skemelio.pingpongandroidclient.R;
import gr.kalymnos.skemelio.pingpongandroidclient.mvc_view.ToolbaredViewMvc;

public class MainScreenViewMvcImp implements MainScreenViewMvc {

    private View root;
    private Toolbar toolbar;
    private FrameLayout fragmentContainer;
    private ProgressBar progressBar;

    public MainScreenViewMvcImp(LayoutInflater inflater, ViewGroup container) {
        initializeViews(inflater, container);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void bindToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void bindToolbarSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    @Override
    public View getRootView() {
        return root;
    }

    private void initializeViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_main, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        fragmentContainer = root.findViewById(R.id.fragment_container);
        progressBar = root.findViewById(R.id.progressBar);
    }

    @Override
    public int getFragmentContainerId() {
        return fragmentContainer.getId();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
