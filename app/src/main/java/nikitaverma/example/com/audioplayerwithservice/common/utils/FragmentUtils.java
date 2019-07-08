package nikitaverma.example.com.audioplayerwithservice.common.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment class
 */
public class FragmentUtils {

    public static void getFragment(FragmentManager fm, Fragment fragment, int resource, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        if(fm != null){
            if (fm.findFragmentByTag(tag) == null) {
//                ft.add(resource, fragment);
//                ft.addToBackStack(tag);
                ft.replace(resource, fragment, tag);
                ft.commit();
            } else {
                ft.show(fragment);
                ft.commit();
            }
        }

    }

}
