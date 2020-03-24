package com.example.segnalazionistrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.segnalazionistrade.chats.ChatsFragment;
import com.example.segnalazionistrade.map.MapFragment;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: MapFragment mapFragment = new MapFragment();
                return mapFragment;

            case 1: ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            /*case 2: ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;

             */

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
            case 0: return "Maps";

            case 1: return "Chats";

            //case 2: return "Contacts";

            default:
                return null;

        }

    }
}
