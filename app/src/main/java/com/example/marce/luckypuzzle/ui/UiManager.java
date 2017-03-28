package com.example.marce.luckypuzzle.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by marce on 26/03/17.
 */

public class UiManager {
    private String currentFragmentName;
    private ChangeFragmentListener changeFragmentListener;
    private FragmentManager fragmentManager;

    public UiManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragmentToShow, Integer container, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(tag == null)
            tag = fragmentToShow.getClass().getName();
        changeFragmentListener.onReplaceFragment(ft, fragmentToShow, container, tag);
        ft.commit();
    }

    private void showFragment(FragmentTransaction ft, Fragment fragmentToShow, String tag, Boolean addToBackStack) {
        if(currentFragmentName != null) {
            Fragment currentFrag = fragmentManager.findFragmentByTag(currentFragmentName);
            hideFragment(ft, currentFrag);
        }

        //Check if fragment already exists. If so, use that fragment instead of a new one.
        Fragment frag = fragmentManager.findFragmentByTag(fragmentToShow.getClass().getName());
        if(frag != null)
            fragmentToShow = frag;

        if(tag == null)
            tag = fragmentToShow.getClass().getName();

        if(!fragmentToShow.isAdded())
            changeFragmentListener.onAddFragment(ft, fragmentToShow, tag);
        else
            changeFragmentListener.onShowFragment(ft, fragmentToShow);

        if(addToBackStack)
            ft.addToBackStack(null);

        ft.commit();

        currentFragmentName = tag;
    }

    /**
     * Show a fragment with no animation.
     * @param fragmentToShow = the fragment to be shown.
     */
    public void showFragment(Fragment fragmentToShow, String tag, Boolean addToBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        showFragment(ft, fragmentToShow, tag, addToBackStack);
    }

    /**
     * Show a fragment with custom enter and exit animations.
     * @param fragmentToShow = the fragment to be shown.
     * @param enterAnim = fragment enter animation.
     * @param exitAnim = fragment exit animation.
     */
    public void showFragment(Fragment fragmentToShow, Integer enterAnim, Integer exitAnim, String tag, Boolean addToBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        showFragment(ft, fragmentToShow, tag, addToBackStack);
    }

    private void hideFragment(FragmentTransaction ft, Fragment fragmentToHide) {
        changeFragmentListener.onHideFragment(ft, fragmentToHide);
    }


    /**
     * Hide fragment with no animation.
     * @param fragmentToHide
     */
    public void hideFragment(Fragment fragmentToHide) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft, fragmentToHide);
    }


    /**
     * Hide fragment with custon enter and exit animation.
     * @param fragmentToHide
     * @param enterAnim
     * @param exitAnim
     */
    public void hideFragment(Fragment fragmentToHide, Integer enterAnim, Integer exitAnim) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);

        hideFragment(ft, fragmentToHide);

    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }


    public interface ChangeFragmentListener {
        void onShowFragment(FragmentTransaction ft, Fragment fragmentToShow);
        void onHideFragment(FragmentTransaction ft, Fragment fragmentToHide);
        void onAddFragment(FragmentTransaction ft, Fragment fragmentToAdd, String tag);
        void onReplaceFragment(FragmentTransaction ft, Fragment fragmentToShow, Integer container, String tag);
    }

}
