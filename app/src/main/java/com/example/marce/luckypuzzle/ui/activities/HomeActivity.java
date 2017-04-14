package com.example.marce.luckypuzzle.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerHomeComponent;
import com.example.marce.luckypuzzle.di.component.HomeComponent;
import com.example.marce.luckypuzzle.di.module.HomeActivityModule;
import com.example.marce.luckypuzzle.presenter.HomePresenterImp;
import com.example.marce.luckypuzzle.ui.fragments.GameFragment;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends LuckyActivity implements HomeView {
    private HomeComponent homeComponent;
    @Inject HomePresenterImp mPresenter;
    @BindView(R.id.photo)CircleImageView photo;
    @BindView(R.id.board_container)FrameLayout boardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        /*if(getIntent().getStringExtra("imageURL")!=null)
            Picasso.with(this).load(getIntent().getStringExtra("imageURL")).into(photo);
        else{
            byte[] bytes = getIntent().getByteArrayExtra("photo");
            photoDecoded = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            photo.setImageBitmap(photoDecoded);
        }*/
        final Bitmap photoDecoded= BitmapFactory.decodeResource(getResources(),R.drawable.horse);
        final View container = findViewById(R.id.board_container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPresenter.scaleBitMap(photoDecoded,container);
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
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
        return homeComponent;
    }


    @Override
    public void goToSignUpActivity() {
        startActivity(new Intent(HomeActivity.this,SignUpActivity.class));
        finish();
    }

    @Override
    public void getScaledBitMap(Bitmap bitmap) {
        mPresenter.cutBitMapIntoPieces(bitmap,3,2);
    }

    @Override
    public void showBitmapIntoSquares(ArrayList<Bitmap> bitmaps) {
        //bitmaps[8]=BitmapFactory.decodeResource(getResources(),R.drawable.blank_brick);
        bitmaps.set(3 * 3 - 1,BitmapFactory.decodeResource(getResources(), R.drawable.blank_brick));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.board_container, GameFragment.newInstance(bitmaps))
                .commit();
    }
}
