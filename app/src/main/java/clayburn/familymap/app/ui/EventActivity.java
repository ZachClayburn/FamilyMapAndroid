package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clayburn.familymap.app.R;

public class EventActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener {

    private static final String EVENT_ID = "clayburn.familymap.app.ui.event_id";

    private Fragment mMapFragment;

    private String mEventID;

    public static Intent newIntent(Context packageContext, String eventID){
        return new Intent(packageContext, EventActivity.class)
                .putExtra(EVENT_ID,eventID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(EVENT_ID)){
            mEventID = intent.getStringExtra(EVENT_ID);
        }

        FragmentManager manager = getSupportFragmentManager();

        mMapFragment = manager.findFragmentById(R.id.map_fragment);
        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance(mEventID);
            manager.beginTransaction()
                    .add(R.id.map_fragment,mMapFragment)
                    .commit();
        }
    }

    @Override
    public void restart() {
        //Do nothing, this is for the MainActivity. Ugly, but it works.
    }
}
