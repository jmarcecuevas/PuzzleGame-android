package com.example.marce.luckypuzzle.ui.recyclerViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;

import java.util.ArrayList;

/**
 * Created by marce on 17/04/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>{

    private SettingsCallback listener;
    private ArrayList<String> mData;

    public SettingsAdapter(ArrayList<String> mData,SettingsCallback listener){
        this.mData=mData;
        this.listener=listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.text.setText(mData.get(position));
        holder.switcher.setChecked(true);
        holder.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.switcher.setChecked(isChecked);
                if(position==0)
                    listener.onMusicSwitcherChanged(isChecked);
                else if(position==1)
                    listener.onSoundEffectsSwitcherChanged(isChecked);
                else if(position==2)
                    listener.onVibrationSwitcherChanged(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private SwitchCompat switcher;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.text);
            switcher= (SwitchCompat) itemView.findViewById(R.id.switcher);
        }
    }

    public interface SettingsCallback{
        void onMusicSwitcherChanged(boolean isChecked);
        void onVibrationSwitcherChanged(boolean isChecked);
        void onSoundEffectsSwitcherChanged(boolean isChecked);
        void onLogoutPressed();
    }
}
