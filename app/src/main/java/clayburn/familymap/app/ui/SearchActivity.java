package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import clayburn.familymap.app.R;
import clayburn.familymap.app.ui.expandableRecyclerView.ExpandableListAdapter;
import clayburn.familymap.model.Model;

public class SearchActivity extends AppCompatActivity {

    private static String TAG = "SearchActivity";

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private ExpandableListAdapter mAdapter;

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext,SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = findViewById(R.id.search_activity_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                mAdapter = GetSearchResults(query);
                if (mRecyclerView.getAdapter() == null) {
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mRecyclerView.swapAdapter(mAdapter,true);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(() -> {
            if (mRecyclerView != null) {
                mRecyclerView.swapAdapter(null,true);
            }
            return false;
        });
    }

    private ExpandableListAdapter GetSearchResults(String query){
        return new ExpandableListAdapter(Model.get().search(query));
    }
}
