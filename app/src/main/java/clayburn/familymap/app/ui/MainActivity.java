package clayburn.familymap.app.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clayburn.familymap.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.main_activity_fragment_holder);

        if (fragment == null) {
            fragment = new LoginRegisterFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_holder,fragment)
                    .commit();
        }
    }
}
