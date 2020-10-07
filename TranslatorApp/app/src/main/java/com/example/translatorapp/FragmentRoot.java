package com.example.translatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentRoot extends Fragment implements BackPressListener {
    @Override
    public boolean onBackPressed() {
        return new backPressHandler(this).onBackPressed();
    }
    //This method is used to perform the fragment transaction and replace the current fragment with the provided fragment 'f'
    public void launchFragment(Fragment f, String title, int id, Bundle extra){
        FragmentManager childManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childManager.beginTransaction();   //Begin the fragment change
        MasterActivity.appTitles[((MasterActivity)getActivity()).currentPage].push(title);  //Push the fragment title to the title stack for the tab
        ((MasterActivity)getActivity()).setTitle(title);  //Update the title immediately
        //if the bundle contained information: add it to the fragment
        if (extra != null && !extra.isEmpty())
            f.setArguments(extra);
        fragmentTransaction.replace(id, f);   //Replace listConstraintLayout with the new fragment
        fragmentTransaction.addToBackStack(null);   //Add the previous fragment to the stack so the back button works
        fragmentTransaction.commit();   //Complete the fragment transaction
    }
}