package clayburn.familymap.app;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/**
 * A General utility class
 */
public class Utils {

    private static final String TAG = "FamilyMapUtils";

    public static void closeKeyboard(Context c, IBinder windowToken){
        Log.d(TAG, "closeKeyboard: called");
        InputMethodManager manager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(windowToken,0);
            return;
        }
        Log.d(TAG, "closeKeyboard: Keyboard not closed!");
    }

}
