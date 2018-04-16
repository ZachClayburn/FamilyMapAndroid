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
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Model;


public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "FilterActivity";
    private static final String FILTERS_HAVE_CHANGED =
            "clayburn.familymap.app.ui.PersonActivity.filters_have_changed";
    private static final String GENDER_FILTER_MALE =
            "clayburn.familymap.app.ui.SearchActivity.gender_filter_male";
    private static final String GENDER_FILTER_FEMALE =
            "clayburn.familymap.app.ui.SearchActivity.gender_filter_female";


    private RecyclerView mRecyclerView;

    private boolean mFiltersHaveChanged;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, FilterActivity.class);
    }

    /**
     * Check if the filters changed during the FilterActivity
     * @param data The Intent returned as a result from the FilterActivity
     * @return Tru if the
     */
    public static boolean filtersHaveChanged(Intent data){
        return data != null && data.getBooleanExtra(FILTERS_HAVE_CHANGED,false);
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
        List<String> list = new ArrayList<>();
        list.add(GENDER_FILTER_MALE);
        list.add(GENDER_FILTER_FEMALE);
        list.addAll(Model.get().getEventTypes());
        return list.stream().map(Filter::new).collect(Collectors.toList());
    }

    private void onFilterChanged(){
        Intent result = new Intent();
        result.putExtra(FILTERS_HAVE_CHANGED,true);
        setResult(RESULT_OK,result);
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

        void bindEventFilter(Filter filter){
            mFilter = filter;
            mFilterName.setText(
                    getString(R.string.filter_name,filter.getFilterName())
            );
            mFilterDetail.setText(
                    getString(R.string.filter_details,filter.getFilterName())
            );

            mSwitch.setChecked(Model.get().isEventDrawn(mFilter.getFilterName()));
            mSwitch.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        Model.get().setEventFilter(mFilter.getFilterName(), isChecked);
                        onFilterChanged();
                    }
            );
        }

        void bindGenderFilter(Filter filter, boolean isMale){
            mFilter = filter;
            String gender = isMale ?
                    getString(R.string.male_text) : getString(R.string.female_text);

            mFilterName.setText(
                    getString(R.string.filter_name,gender)
            );
            mFilterDetail.setText(
                    getString(R.string.filter_details,gender)
            );

            mSwitch.setChecked(Model.get().isEventDrawn(isMale));
            mSwitch.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        Model.get().setGenderFilter(isMale, isChecked);
                        onFilterChanged();
                    }
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
            Filter filter = mFilters.get(position);
            switch (filter.getFilterName()){
                case GENDER_FILTER_FEMALE:{
                    holder.bindGenderFilter(filter, false);
                }break;
                case GENDER_FILTER_MALE:{
                    holder.bindGenderFilter(filter, true);
                }break;
                default:{
                    holder.bindEventFilter(filter);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mFilters.size();
        }
    }

    private class Filter {

        Filter(String filterName){
            mFilterName = filterName;
        }

        private String mFilterName;

        String getFilterName() {
            return mFilterName;
        }
    }
}
