package com.example.marce.luckypuzzle.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;

import com.example.marce.luckypuzzle.di.component.ChooseActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerChooseActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpActivityComponent;
import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.ui.fragments.SignUpFragment;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.PictureGalleryAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.SettingsAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.itemDecoration.SquareGridSpacingItemDecoration;
import com.example.marce.luckypuzzle.utils.ImagePicker;
import com.example.marce.luckypuzzle.utils.SessionManager;
import com.example.marce.luckypuzzle.utils.SettingsManager;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChoosePictureActivity extends LuckyActivity
        implements NavigationView.OnNavigationItemSelectedListener,PictureGalleryAdapter.ItemListener {

    private static final int PICK_IMAGE_ID = 1;
    private RecyclerView mRecyclerView;
    private ChooseActivityComponent chooseComponent;
    private PictureGalleryAdapter adapter;
    private ArrayList<Integer> imageListId;
    private Bitmap photo;
    private boolean userImageSelected,ourImageSelected;
    private int imageId;
    private String uriString,uriPictureTaken;
    private Uri uri;
    private int spanCount=3;
    private String profileUri;
    private String userName;
    private SharedPreferences prefs;
    @Inject SessionManager session;
    @BindView(R.id.imagePreview)ImageView imagePreview;
    @BindView(R.id.textInsideImage)TextView gridSize;
    @BindView(R.id.seekBar)SeekBar seekBar;


    @Override
    protected int getLayout() {
        return R.layout.activity_choose_picture;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {
        chooseComponent = DaggerChooseActivityComponent.builder()
                .luckyGameComponent(appComponent).build();
        chooseComponent.inject(this);
    }

    @Override
    protected ActivityComponent getComponent() {
        return null;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        gridSize.setText(String.valueOf(spanCount)+"x"+String.valueOf(spanCount));

        if(!session.checkStatusLogin()){
            startActivity(new Intent(this,SignUpActivity.class));
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
        if(getIntent().getStringExtra("uri")!=null)
            uriString= getIntent().getStringExtra("uri");
        else
            uriString=getIntent().getStringExtra("imageURL");
        userName= getIntent().getStringExtra("userName");


        imageListId = new ArrayList<>();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("pic"))
                imageListId.add(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
        }

        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerGallery);
        adapter= new PictureGalleryAdapter(this,imageListId,this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SquareGridSpacingItemDecoration(this,R.dimen.brick_divider_width,3));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.choseAPicture);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        CircleImageView profileImage= (CircleImageView) hView.findViewById(R.id.profileImage);
        TextView user= (TextView) hView.findViewById(R.id.userName);
        user.setText(userName);
        Picasso.with(this).load(uriString).resize(128,128).into(profileImage);

        navigationView.setNavigationItemSelectedListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                spanCount=progress+3;
                gridSize.setText(String.valueOf(spanCount)+"x"+String.valueOf(spanCount));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.settings){
            Intent intent= new Intent(this,SettingsActivity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("uri",uriString);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }else if(id==R.id.ranking){
            Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT).show();
        }else if(id==R.id.myBest){
            Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT).show();
        }else if(id==R.id.signout){
            session.logoutUser();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(int position) {
        Picasso.with(this).load(imageListId.get(position)).resize(240,240).into(imagePreview);
        ourImageSelected=true;
        userImageSelected=false;
        imageId=imageListId.get(position);
    }

    @OnClick({R.id.browse,R.id.start,R.id.random})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.browse:
                showPictureDialog();
                break;
            case R.id.random:
                showRandomImage();
                break;
            case R.id.start:
                startGame();
                break;
        }
    }

    private void showRandomImage(){
        ourImageSelected=true;
        userImageSelected=false;
        Random randomGenerator = new Random();
        int random= randomGenerator.nextInt(imageListId.size());
        imageId=imageListId.get(random);
        Picasso.with(this).load(imageListId.get(random)).resize(240,240).into(imagePreview);
    }

    private void startGame() {
        if((userImageSelected||ourImageSelected)){
            Intent intent= new Intent(this,HomeActivity.class);
            if(userImageSelected)
                intent.putExtra("uri",uriPictureTaken);
            else if(ourImageSelected)
                intent.putExtra("imageId",imageId);
            intent.putExtra("spanCount",spanCount);
            intent.putExtra("arrayImages",imageListId);
            intent.putExtra("userName",userName);
            intent.putExtra("profileUri",uriString);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,R.string.chooseAPhoto,Toast.LENGTH_SHORT).show();
        }
    }

    private void showPictureDialog() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK){
            photo=ImagePicker.getImageFromResult(this,resultCode,data);
            uriPictureTaken= ImagePicker.getImageUri(this,photo).toString();
            Picasso.with(this).load(uriPictureTaken).resize(240,240).into(imagePreview);
            ourImageSelected=false;
            userImageSelected=true;
        }
    }
}
