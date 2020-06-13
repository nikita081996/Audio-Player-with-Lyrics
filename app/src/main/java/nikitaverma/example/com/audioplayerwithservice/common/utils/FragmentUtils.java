package nikitaverma.example.com.audioplayerwithservice.common.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
