package com.example.marce.luckypuzzle.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerHomeComponent;
import com.example.marce.luckypuzzle.di.component.HomeComponent;
import com.example.marce.luckypuzzle.di.module.HomeActivityModule;
import com.example.marce.luckypuzzle.model.Square;
import com.example.marce.luckypuzzle.presenter.HomePresenterImp;
import com.example.marce.luckypuzzle.ui.custom.CustomDialog;
import com.example.marce.luckypuzzle.ui.fragments.GameFragment;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.example.marce.luckypuzzle.utils.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends LuckyActivity implements HomeView,GameFragment.GameCallback {
    private static final String FORMAT = "%02d:%02d:%02d";
    private HomeComponent homeComponent;
    @Inject HomePresenterImp mPresenter;
    @BindView(R.id.photo)CircleImageView photo;
    @BindView(R.id.board_container)FrameLayout boardContainer;
    @BindView(R.id.level)TextView level;
    @BindView(R.id.time)TextView time;
    @BindView(R.id.moves)TextView moves;
    @BindView(R.id.userName)TextView userNameTv;
    private long timeSpent;
    private ArrayList<Integer> imagesListId;
    private CountDownTimer timer;
    private int totalMoves=0;
    private ArrayList<Square> squares;
    private CustomDialog customDialog;
    private int imageId;
    private Bitmap pictureToPlay=null;
    private int spanCount;
    private String userName,uriProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentExtras();
        final View container = findViewById(R.id.board_container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPresenter.scaleBitMap(pictureToPlay,container);
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

    public void getIntentExtras(){
        if(getIntent().getStringExtra("uri")!=null) {
            Uri uri= Uri.parse(getIntent().getStringExtra("uri"));
            try {
                pictureToPlay= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            imageId= getIntent().getIntExtra("imageId",0);
            pictureToPlay= BitmapFactory.decodeResource(getResources(),imageId);
        }
        spanCount= getIntent().getIntExtra("spanCount",0);
        imagesListId=getIntent().getIntegerArrayListExtra("arrayImages");
        userName= getIntent().getStringExtra("userName");
        uriProfile= getIntent().getStringExtra("profileUri");
        Picasso.with(this).load(uriProfile).resize(128,128).into(photo);
        userNameTv.setText(userName);
    }

    @Override
    public HomeComponent getComponent() {
        return homeComponent;
    }

    @Override
    protected void init() {
        level.setText(getText(R.string.level)+" "+String.valueOf(spanCount-2));
    }

    public void startGame(){
       initTime();
        initMoves();
    }

    public void initTime(){
        timer=new CountDownTimer(60000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                time.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                timeSpent= 60 - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
            }
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    public void initMoves(){
        totalMoves=0;
        moves.setText(String.valueOf(totalMoves));
    }

    private void gameOver() {
        customDialog= new CustomDialog(this,R.style.Theme_Dialog_Translucent,R.layout.user_lost_dialog);
        Button retry=(Button)customDialog.findViewById(R.id.retry);
        TextView timeSpent=(TextView)customDialog.findViewById(R.id.timeSpent);
        TextView moves=(TextView)customDialog.findViewById(R.id.moves);
        moves.setText(moves.getText().toString()+ " " + String.valueOf(totalMoves));
        timeSpent.setText(timeSpent.getText().toString() + " " + 60+"s");
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
                getSupportFragmentManager().beginTransaction().replace(R.id.board_container,GameFragment.newInstance(squares,spanCount)).commit();
                customDialog.cancel();
            }
        });
        Button exit=(Button)customDialog.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }

    public void nextGame(){
        Random randomGenerator = new Random();
        int random= randomGenerator.nextInt(imagesListId.size());
        Bitmap newPhoto=BitmapFactory.decodeResource(getResources(),imagesListId.get(random));
        spanCount++;
        mPresenter.scaleBitMap(newPhoto,boardContainer);
    }

    @Override
    public void goToSignUpActivity() {
        startActivity(new Intent(HomeActivity.this,SignUpActivity.class));
        finish();
    }

    @Override
    public void getScaledBitMap(Bitmap bitmap) {
        mPresenter.cutBitMapIntoPieces(bitmap,spanCount,2);
    }

    @Override
    public void showBitmapIntoSquares(ArrayList<Bitmap> bitmaps) {
        squares= new ArrayList<>();
        for(int i=0;i<bitmaps.size();i++)
            squares.add(new Square(bitmaps.get(i),i));
        startGame();
        getSupportFragmentManager().beginTransaction().replace(R.id.board_container,GameFragment.newInstance(squares,spanCount)).commit();
    }

    @Override
    public void onUserWon() {
        timer.cancel();
        customDialog= new CustomDialog(this,R.style.Theme_Dialog_Translucent,R.layout.user_won_dialog);
        final Button next=(Button)customDialog.findViewById(R.id.next);
        TextView time= (TextView) customDialog.findViewById(R.id.timeSpent);
        TextView moves= (TextView)customDialog.findViewById(R.id.moves);
        time.setText(time.getText().toString()+" "+timeSpent+"s");
        moves.setText(moves.getText().toString()+ " " + totalMoves);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextGame();
                customDialog.cancel();
            }
        });
        Button exit=(Button)customDialog.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customDialog.show();
    }

    @Override
    public void onItemMoved() {
        totalMoves+=1;
        moves.setText(String.valueOf(totalMoves));
    }
}
