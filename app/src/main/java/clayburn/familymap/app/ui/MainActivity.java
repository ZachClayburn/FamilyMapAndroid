package clayburn.familymap.app.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Model;

public class MainActivity extends AppCompatActivity implements
        LoginRegisterFragment.LoginRegisterContext,
        MapFragment.OnFragmentInteractionListener {

    Fragment mFragment;
    FragmentManager mFragmentManager;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager  = getSupportFragmentManager();

        mFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_holder);

        if (mFragment == null) {
            if (Model.get().getUserPersonID() == null) {
                mFragment = new LoginRegisterFragment();

            } else {
                mFragment = MapFragment.newInstance(null);
            }
            mFragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_holder, mFragment)
                    .commit();
        }
    }

    @Override
    public void onLoginCompleted() {
        mFragment = MapFragment.newInstance(null);
        try {
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_fragment_holder, mFragment)
                    .commit();
        } catch (Exception e){
            Log.e(TAG,e.getLocalizedMessage(),e);
        }
    }

    /**
     * Force the MainActivity to restart
     */
    @Override
    public void restart() {
        mFragmentManager.beginTransaction()
                .remove(mFragment)
                .commit();
        recreate();
    }
}
