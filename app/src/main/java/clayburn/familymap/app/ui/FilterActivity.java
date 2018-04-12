package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import clayburn.familymap.app.R;


public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "FilterActivity";

    private RecyclerView mRecyclerView;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, FilterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mRecyclerView = findViewById(R.id.filter_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class FilterListHolder extends RecyclerView.ViewHolder{

        public FilterListHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.filter_list_holder_layout,parent,false));
        }
    }

    private class FilterListAdapter extends RecyclerView.Adapter<FilterListHolder>{
        @NonNull
        @Override
        public FilterListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(FilterActivity.this);

            return new FilterListHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FilterListHolder holder, int position) {
            //TODO Finish this using the info from https://stackoverflow.com/questions/37194653/fragment-replacing-in-recyclerview-item
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    /**
     * A class that will hold the data for an individual filter switch in the list
     */
    public class Filter {

        private String mFilterName;

    }
}
