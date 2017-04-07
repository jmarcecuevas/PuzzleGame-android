package com.example.marce.luckypuzzle.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends LuckyActivity {

    @BindView(R.id.photo)CircleImageView photo;
    @BindView(R.id.greeting)TextView greeting;
    @BindView(R.id.logout)Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        greeting.setText("Welcome " + getIntent().getStringExtra("userName"));
        Picasso.with(this).load(getIntent().getStringExtra("imageURL")).into(photo);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {

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
}
