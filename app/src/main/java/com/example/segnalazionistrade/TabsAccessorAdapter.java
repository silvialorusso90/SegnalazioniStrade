package com.example.segnalazionistrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.segnalazionistrade.chat.ChatFragment;
import com.example.segnalazionistrade.mappa.MapFragment;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: MapFragment mapFragment = new MapFragment();
                return mapFragment;

            case 1: ChatFragment chatFragment = new ChatFragment();
                return chatFragment;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Mappa";

            case 1: return "Chat";

            default:
                return null;

        }

    }
}
