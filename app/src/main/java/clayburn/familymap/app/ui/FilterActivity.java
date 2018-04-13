package clayburn.familymap.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Model;


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
        List<Filter> filters = getFilters();
        FilterListAdapter adapter = new FilterListAdapter(filters);
        mRecyclerView.setAdapter(adapter);
    }

    private List<Filter> getFilters(){
        List<String> list = new ArrayList<>(Model.get().getEventTypes());
        return list.stream().map(Filter::new).collect(Collectors.toList());
    }

    private class FilterListHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView mFilterName;
        private TextView mFilterDetail;
        private Switch mSwitch;

        private Filter mFilter;

        FilterListHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_switch,parent,false));

            mFilterName = itemView.findViewById(R.id.menu_fragment_option_name);
            mFilterDetail = itemView.findViewById(R.id.menu_fragment_option_detail);
            mSwitch = itemView.findViewById(R.id.option_switch);
        }

        void onBind(Filter filter){
            mFilter = filter;
            mFilterName.setText(
                    getString(R.string.filter_name,filter.getFilterName())
            );
            mFilterDetail.setText(
                    getString(R.string.filter_details,filter.getFilterName())
            );

            mSwitch.setChecked(
                    Model.get().isEventDrawn(mFilter.getFilterName())
            );
            mSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                    Model.get().setEventFilter(mFilter.getFilterName(),isChecked)
            );
        }
    }

    private class FilterListAdapter extends RecyclerView.Adapter<FilterListHolder>{

        private List<Filter> mFilters;

        FilterListAdapter(List<Filter> filters){
            mFilters = filters;
        }

        @NonNull
        @Override
        public FilterListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(FilterActivity.this);
            return new FilterListHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FilterListHolder holder, int position) {
            holder.onBind(mFilters.get(position));
        }

        @Override
        public int getItemCount() {
            return mFilters.size();
        }
    }

    /**
     * A class that will hold the data for an individual filter switch in the list
     */
    public class Filter {

        public Filter(String filterName){
            mFilterName = filterName;
        }

        private String mFilterName;

        String getFilterName() {
            return mFilterName;
        }
    }
}
