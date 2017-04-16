package com.example.marce.luckypuzzle.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.PictureGalleryAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.itemDecoration.SquareGridSpacingItemDecoration;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.GridLayoutAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.ItemTouchHelperAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.SimpleItemTouchHelperCallback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoosePictureActivity extends LuckyActivity
        implements NavigationView.OnNavigationItemSelectedListener,PictureGalleryAdapter.ItemListener {

    private RecyclerView mRecyclerView;
    private PictureGalleryAdapter adapter;
    private ArrayList<Integer> imageListId;
    @BindView(R.id.imagePreview)ImageView imagePreview;
    @BindView(R.id.textInsideImage)TextView gridSize;
    @BindView(R.id.seekBar)SeekBar seekBar;


    @Override
    protected int getLayout() {
        return R.layout.activity_choose_picture;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {

    }

    @Override
    protected ActivityComponent getComponent() {
        return null;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        imageListId = new ArrayList<Integer>();
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
        navigationView.setNavigationItemSelectedListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gridSize.setText(String.valueOf(progress)+"x"+String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

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
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(int position) {
        Picasso.with(this).load(imageListId.get(position)).resize(240,240).into(imagePreview);
    }
}
