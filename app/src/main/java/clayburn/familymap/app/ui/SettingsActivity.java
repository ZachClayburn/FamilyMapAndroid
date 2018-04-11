package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import clayburn.familymap.app.R;
import clayburn.familymap.app.ui.menuItems.MenuFragment;
import clayburn.familymap.app.ui.menuItems.SpinnerFragment;
import clayburn.familymap.app.ui.menuItems.SpinnerSwitchFragment;

public class SettingsActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener{

    private static final String TAG = "SettingsActivity";
    private static final String OPTIONS_HAVE_CHANGED = "options_have_changed";

    private Fragment mLifeStoryFragment;
    private Fragment mFamilyTreeFragment;
    private Fragment mSpouseFragment;
    private Fragment mMapTypeFragment;
    private Fragment mSyncDataFragment;
    private Fragment mLogOutFragment;
    private FragmentManager mFM;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,SettingsActivity.class);
    }

    public static boolean checkDataHasChanged(Intent intent){

        return intent != null && intent.getBooleanExtra(OPTIONS_HAVE_CHANGED, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mFM = getSupportFragmentManager();

        mLifeStoryFragment = mFM.findFragmentById(R.id.life_story_fragment);
        mFamilyTreeFragment = mFM.findFragmentById(R.id.family_tree_fragment);
        mSpouseFragment = mFM.findFragmentById(R.id.spouse_fragment);
        mMapTypeFragment = mFM.findFragmentById(R.id.map_type_fragment);
        mSyncDataFragment = mFM.findFragmentById(R.id.sync_data_fragment);
        mLogOutFragment = mFM.findFragmentById(R.id.log_out_fragment);


        if (mLifeStoryFragment == null) {
            mLifeStoryFragment = new SpinnerSwitchFragment();
            mFM.beginTransaction()
                    .add(R.id.life_story_fragment,mLifeStoryFragment)
                    .commit();
/*
*/
        }
        if (mFamilyTreeFragment == null) {
            mFamilyTreeFragment = new SpinnerSwitchFragment();
            mFM.beginTransaction()
                    .add(R.id.family_tree_fragment, mFamilyTreeFragment)
                    .commit();

        }
        if (mSpouseFragment == null) {
            mSpouseFragment = new SpinnerSwitchFragment();
            mFM.beginTransaction()
                    .add(R.id.spouse_fragment,mSpouseFragment)
                    .commit();
        }
        if (mMapTypeFragment == null) {
            mMapTypeFragment = new SpinnerFragment();
            mFM.beginTransaction()
                    .add(R.id.map_type_fragment,mMapTypeFragment)
                    .commit();

        }
        if (mSyncDataFragment == null) {
            mSyncDataFragment = new MenuFragment();
            mFM.beginTransaction()
                    .add(R.id.sync_data_fragment,mSyncDataFragment)
                    .commit();
        }
        if (mLogOutFragment == null) {
            mLogOutFragment = new MenuFragment();
            mFM.beginTransaction()
                    .add(R.id.log_out_fragment,mLogOutFragment)
                    .commit();
        }


    }


    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed.  Be sure to always call through to
     * the super-class.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        //TODO Properly initialize these fields
        //TODO Implement methods on Model to save all of these settings
        SpinnerSwitchFragment ssFragment;
        SpinnerFragment sFragment;
        MenuFragment mFragment;

        //Life story options
        ssFragment = (SpinnerSwitchFragment) mLifeStoryFragment;
        ssFragment.setOptionName(R.string.option_name_life_story);
        ssFragment.setOptionDetail(R.string.option_detail_life_story);
        ssFragment.setOptionSwitchListener((buttonView, isChecked) -> {
            Boolean IsChecked = isChecked;
            Toast.makeText(SettingsActivity.this,IsChecked.toString(),Toast.LENGTH_SHORT).show();
        });
        ssFragment.setOptionSpinnerList(R.array.line_color_names, 0);

        ssFragment.setOptionSpinnerAction(position ->
                Toast.makeText(SettingsActivity.this,"Life story spinner",Toast.LENGTH_SHORT).show());

        //Family tree options
        ssFragment = (SpinnerSwitchFragment) mFamilyTreeFragment;
        ssFragment.setOptionName(R.string.option_name_family_tree);
        ssFragment.setOptionDetail(R.string.option_detail_family_tree);
        ssFragment.setOptionSwitchListener((buttonView, isChecked) -> {
            Boolean IsChecked = isChecked;
            Toast.makeText(SettingsActivity.this,IsChecked.toString(),Toast.LENGTH_SHORT).show();
        });
        ssFragment.setOptionSpinnerList(R.array.line_color_names, 0);

        ssFragment.setOptionSpinnerAction(position ->
                Toast.makeText(SettingsActivity.this,"Family tree spinner",Toast.LENGTH_SHORT).show());

        //Spouse options
        ssFragment = (SpinnerSwitchFragment) mSpouseFragment;
        ssFragment.setOptionName(R.string.option_name_spouse);
        ssFragment.setOptionDetail(R.string.option_detail_spouse);
        ssFragment.setOptionSwitchListener((buttonView, isChecked) -> {
            Boolean IsChecked = isChecked;
            Toast.makeText(SettingsActivity.this,IsChecked.toString(),Toast.LENGTH_SHORT).show();
        });
        ssFragment.setOptionSpinnerList(R.array.line_color_names, 0);

        ssFragment.setOptionSpinnerAction(position ->
                Toast.makeText(SettingsActivity.this,"Spouse spinner",Toast.LENGTH_SHORT).show());

        //Map Type options
        sFragment = (SpinnerFragment) mMapTypeFragment;
        sFragment.setOptionName(R.string.option_name_map_type);
        sFragment.setOptionDetail(R.string.option_detail_map_type);
        sFragment.setOptionSpinnerList(R.array.map_types, 0);

        sFragment.setOptionSpinnerAction(position ->
                Toast.makeText(SettingsActivity.this,"map type spinner",Toast.LENGTH_SHORT).show());

        //Re-sync options
        mFragment = (MenuFragment) mSyncDataFragment;
        mFragment.setOptionName(R.string.option_name_sync_data);
        mFragment.setOptionDetail(R.string.option_detail_sync_data);
        mFragment.setClickAction(() ->
                Toast.makeText(SettingsActivity.this,"Sync clicked",Toast.LENGTH_SHORT).show());

        //Log out options
        mFragment = (MenuFragment) mLogOutFragment;
        mFragment.setOptionName(R.string.option_name_log_out);
        mFragment.setOptionDetail(R.string.option_detail_log_out);
        mFragment.setClickAction(() ->
                Toast.makeText(SettingsActivity.this,"Log out clicked",Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onOptionChanged() {
        Intent data = new Intent();
        data.putExtra(OPTIONS_HAVE_CHANGED,true);
        setResult(RESULT_OK,data);
    }
}
