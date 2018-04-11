package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import clayburn.familymap.app.R;
import clayburn.familymap.app.ui.menuItems.MenuFragment;
import clayburn.familymap.app.ui.menuItems.SpinnerFragment;
import clayburn.familymap.app.ui.menuItems.SpinnerSwitchFragment;
import clayburn.familymap.model.Model;

import static clayburn.familymap.model.Model.LineName.*;

public class SettingsActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener{

    private static final String TAG = "SettingsActivity";
    private static final String LINE_OPTIONS_HAVE_CHANGED = "LINE_OPTIONS_HAVE_CHANGED";
    private static final String MAP_OPTIONS_HAVE_CHANGED ="MAP_OPTIONS_HAVE_CHANGED";
    private static final String DATA_WAS_SYNCED = "DATA_WAS_SYNCED";

    private Fragment mLifeStoryFragment;
    private Fragment mFamilyTreeFragment;
    private Fragment mSpouseFragment;
    private Fragment mMapTypeFragment;
    private Fragment mSyncDataFragment;
    private Fragment mLogOutFragment;
    private FragmentManager mFM;

    private boolean mLineOptionsHaveChanged = false;
    private boolean mMapOptionsHaveChanged = false;
    private boolean mDataWasSynced = false;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,SettingsActivity.class);
    }

    /**
     * Check if the options pertaining to lines were changed during the SettingsActivity
     * @param intent The intent returned as a result from the SettingsActivity
     * @return True if line option were changed, false otherwise
     */
    public static boolean lineOptionsHaveChanged(Intent intent){

        return intent != null
                && intent.getBooleanExtra(LINE_OPTIONS_HAVE_CHANGED, false);
    }

    /**
     * Check if the options pertaining to the map were changed during the SettingsActivity
     * @param intent The intent returned as a result from the SettingsActivity
     * @return True if map option were changed, false otherwise
     */
    public static boolean mapOptionsHaveChanged(Intent intent){

        return intent != null
                && intent.getBooleanExtra(MAP_OPTIONS_HAVE_CHANGED, false);
    }

    /**
     * Check if the data was synced during the SettingsActivity
     * @param intent The intent returned as a result from the SettingsActivity
     * @return True if data was synced, false otherwise
     */
    public static boolean dataWasSynced(Intent intent){

        return intent != null
                && intent.getBooleanExtra(DATA_WAS_SYNCED, false);
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
        Model model = Model.get();

        //Life story options
        ssFragment = (SpinnerSwitchFragment) mLifeStoryFragment;
        ssFragment.setOptionName(R.string.option_name_life_story);
        ssFragment.setOptionDetail(R.string.option_detail_life_story);
        ssFragment.setSource(LINE_OPTIONS_HAVE_CHANGED);

        ssFragment.setOptionSwitchState(model.isLineDrawn(lifeStoryLines));
        ssFragment.setOptionSwitchListener((buttonView, isChecked) ->
                model.setLineDrawn(lifeStoryLines,isChecked)
        );

        ssFragment.setOptionSpinnerList(
                R.array.line_color_names,
                model.getLineColorSelection(lifeStoryLines)
        );
        ssFragment.setOptionSpinnerAction(position ->
                model.setLineColorSelection(lifeStoryLines,position)
        );

        //Family tree options
        ssFragment = (SpinnerSwitchFragment) mFamilyTreeFragment;
        ssFragment.setOptionName(R.string.option_name_family_tree);
        ssFragment.setOptionDetail(R.string.option_detail_family_tree);
        ssFragment.setSource(LINE_OPTIONS_HAVE_CHANGED);

        ssFragment.setOptionSwitchState(model.isLineDrawn(familyTreeLines));
        ssFragment.setOptionSwitchListener((buttonView, isChecked) ->
                model.setLineDrawn(familyTreeLines,isChecked)
        );

        ssFragment.setOptionSpinnerList(
                R.array.line_color_names,
                model.getLineColorSelection(familyTreeLines)
        );
        ssFragment.setOptionSpinnerAction(position ->
                model.setLineColorSelection(familyTreeLines,position)
        );

        //Spouse options
        ssFragment = (SpinnerSwitchFragment) mSpouseFragment;
        ssFragment.setOptionName(R.string.option_name_spouse);
        ssFragment.setOptionDetail(R.string.option_detail_spouse);
        ssFragment.setSource(LINE_OPTIONS_HAVE_CHANGED);

        ssFragment.setOptionSwitchState(model.isLineDrawn(spouseLines));
        ssFragment.setOptionSwitchListener((buttonView, isChecked) ->
                model.setLineDrawn(spouseLines,isChecked)
        );

        ssFragment.setOptionSpinnerList(
                R.array.line_color_names,
                model.getLineColorSelection(spouseLines)
        );
        ssFragment.setOptionSpinnerAction(position ->
                model.setLineColorSelection(spouseLines, position)
        );

        //Map Type options
        sFragment = (SpinnerFragment) mMapTypeFragment;
        sFragment.setOptionName(R.string.option_name_map_type);
        sFragment.setOptionDetail(R.string.option_detail_map_type);
        sFragment.setSource(MAP_OPTIONS_HAVE_CHANGED);

        sFragment.setOptionSpinnerList(R.array.map_types, model.getCurrentMapTypeIndex());
        sFragment.setOptionSpinnerAction(model::setCurrentMapType);

        //Re-sync options
        mFragment = (MenuFragment) mSyncDataFragment;
        mFragment.setOptionName(R.string.option_name_sync_data);
        mFragment.setOptionDetail(R.string.option_detail_sync_data);
        mFragment.setSource(DATA_WAS_SYNCED);

        mFragment.setClickAction(() ->//TODO Set this up correctly
                Toast.makeText(SettingsActivity.this,"Sync clicked",Toast.LENGTH_SHORT).show());

        //Log out options
        mFragment = (MenuFragment) mLogOutFragment;
        mFragment.setOptionName(R.string.option_name_log_out);
        mFragment.setOptionDetail(R.string.option_detail_log_out);

        mFragment.setClickAction(() ->//TODO Set this up correctly
                Toast.makeText(SettingsActivity.this,"Log out clicked",Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onOptionChanged(@Nullable String source) {

        if (source == null) {
            source = "";
        }

        switch (source){
            case LINE_OPTIONS_HAVE_CHANGED: mLineOptionsHaveChanged = true; break;
            case MAP_OPTIONS_HAVE_CHANGED: mMapOptionsHaveChanged = true; break;
            case DATA_WAS_SYNCED: mDataWasSynced = true; break;
        }

        Intent data = new Intent();
        data.putExtra(LINE_OPTIONS_HAVE_CHANGED, mLineOptionsHaveChanged);
        data.putExtra(MAP_OPTIONS_HAVE_CHANGED,mMapOptionsHaveChanged);
        data.putExtra(DATA_WAS_SYNCED,mDataWasSynced);
        setResult(RESULT_OK,data);
    }
}
