package com.example.marce.luckypuzzle.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerHomeComponent;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpActivityComponent;
import com.example.marce.luckypuzzle.di.component.HomeComponent;
import com.example.marce.luckypuzzle.di.module.HomeActivityModule;
import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.presenter.HomePresenterImp;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends LuckyActivity implements HomeView {
    private HomeComponent homeComponent;
    @Inject HomePresenterImp mPresenter;
    @BindView(R.id.photo)CircleImageView photo;
    @BindView(R.id.greeting)TextView greeting;
    @BindView(R.id.logout)Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter.checkSession();

        greeting.setText("Welcome " + getIntent().getStringExtra("userName"));
        if(getIntent().getStringExtra("imageURL")!=null)
            Picasso.with(this).load(getIntent().getStringExtra("imageURL")).into(photo);
        else
            photo.setImageBitmap((Bitmap)getIntent().getParcelableExtra("photo"));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {
        homeComponent= DaggerHomeComponent.builder()
                .luckyGameComponent(appComponent)
                .homeActivityModule(new HomeActivityModule(this)).build();
        homeComponent.inject(this);
    }

    @Override
    protected ActivityComponent getComponent() {
        return null;
    }

    @OnClick({R.id.logout})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.logout:
            break;
        }
    }

    @Override
    public void goToSignUpActivity() {
        startActivity(new Intent(HomeActivity.this,SignUpActivity.class));
        finish();
    }
}
