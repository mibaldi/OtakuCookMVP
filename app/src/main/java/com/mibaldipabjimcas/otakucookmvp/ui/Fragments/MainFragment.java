package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class MainFragment extends BaseMVPFragment<MainPresenter,MainView>  implements MainView{
    private MainActivityComponent component;
    private Unbinder unbind;

    @BindView(R.id.mainImage)
    ImageView mainImage;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.mainRecipeName)
    TextView mainRecipeName;

    @BindView(R.id.mainRecipeAuthor)
    TextView mainRecipeAuthor;

    @BindView(R.id.random)
    Button randomButton;

    private ProgressDialog progressDialog;

    @Inject
    public MainFragment() {
        setRetainInstance(true);
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        presenter.init(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(MainActivityComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        unbind = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public MainPresenter createPresenter() {
        return component.mainPresenter();
    }

    @OnClick(R.id.mainLinearLayout)
    @Override
    public void openMainRecipe() {
        presenter.openRecipeDescription();
    }

    @Override
    public void showRecipeImage(String photo) {
        Glide.with(getActivity()).load(photo).placeholder(R.mipmap.ic_launcher).into(mainImage);
    }

    @Override
    public void showDefaultImage() {
        Glide.with(getActivity()).load(R.drawable.default_recipe).into(mainImage);
    }

    @OnClick(R.id.random)
    public void randomRecipe(){
        presenter.getRandomRecipe();
    }

    @Override
    public void showRatingBar(int score) {
        ratingBar.setRating(score);
    }

    @Override
    public void showRecipeName(String name) {
        mainRecipeName.setText(name);
    }

    @Override
    public void showRecipeAuthor(String author) {
        if(!author.isEmpty())
            mainRecipeAuthor.setText(getString(R.string.por,author));
    }

    @Override
    public void showRandomButton(Boolean b) {
        randomButton.setEnabled(b);
        if(b){
            randomButton.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.buy_button,null));
            randomButton.setTextColor(Color.WHITE);
        }else{
            randomButton.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_button,null));
        }

    }

    @Override
    public void showProgressDialog(int message) {
        progressDialog.setMessage(getString(message));
        progressDialog.show();
    }

    @Override
    public void cancelProgressDialog() {
        progressDialog.setCancelable(true);
        progressDialog.cancel();
    }


    @Override
    public void showNoConnectivity() {

    }
}
