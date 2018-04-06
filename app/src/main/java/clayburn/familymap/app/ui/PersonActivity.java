package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Event;

public class PersonActivity extends AppCompatActivity {

    private static final String PERSON_ID = "clayburn.familyMap.app.ui.person_id";

    public static Intent newIntent(Context packageContext, String personID){
        Intent intent = new Intent(packageContext,PersonActivity.class);
        intent.putExtra(PERSON_ID,personID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
}
