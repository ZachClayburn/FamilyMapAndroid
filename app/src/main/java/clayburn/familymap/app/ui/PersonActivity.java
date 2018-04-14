package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import clayburn.familymap.app.R;
import clayburn.familymap.app.ui.expandableRecyclerView.ExpandableListAdapter;
import clayburn.familymap.model.ExpandableListItem;
import clayburn.familymap.model.Model;
import clayburn.familymap.model.ExpandingGroup;

public class PersonActivity extends AppCompatActivity {

    private static final String PERSON_ID = "clayburn.familymap.app.ui.person_id";

    private AppCompatTextView mPersonName;
    private ImageView mGenderIcon;
    private RecyclerView mRecyclerView;
    private ExpandableListAdapter mAdapter;

    private String mPersonID;

    public static Intent newIntent(Context packageContext, String personID){
        return new Intent(packageContext,PersonActivity.class)
                .putExtra(PERSON_ID,personID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(PERSON_ID)) {
            mPersonID = intent.getStringExtra(PERSON_ID);
        }

        mPersonName = findViewById(R.id.person_name);
        mPersonName.setText(
                Model.get().getPersonName(mPersonID)
        );

        mGenderIcon = findViewById(R.id.gender_icon);
        int iconID = Model.get().isMale(mPersonID) ?
                R.drawable.ic_android_male : R.drawable.ic_android_female;
        mGenderIcon.setImageResource(iconID);

        mRecyclerView = findViewById(R.id.person_activity_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new ExpandableListAdapter(prepareListContents());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
        outState.putString(PERSON_ID, mPersonID);
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
        mPersonID = savedInstanceState.getString(PERSON_ID);
    }

    private List<ExpandingGroup> prepareListContents(){
        List<ExpandingGroup> list = new ArrayList<>();

        List<ExpandableListItem> contentList;
        ExpandingGroup group;

        contentList = Model.get().getEventList(mPersonID);
        group = new ExpandingGroup(ExpandingGroup.EVENT_GROUP_TITLE,contentList);
        list.add(group);

        contentList = Model.get().getFamilyList(mPersonID);
        group = new ExpandingGroup(ExpandingGroup.FAMILY_GROUP_TITLE,contentList);
        list.add(group);

        return list;
    }
}
