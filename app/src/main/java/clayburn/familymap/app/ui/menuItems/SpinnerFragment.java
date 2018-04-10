package clayburn.familymap.app.ui.menuItems;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import clayburn.familymap.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpinnerFragment extends MenuFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SpinnerFragment";

    protected Spinner mOptionSpinner;
    private MenuSpinnerAction mAction;

    public SpinnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_spinner, container, false);
        getOptionViews(v);
        return v;
    }

    @Override
    protected void getOptionViews(View v) {
        super.getOptionViews(v);
        mOptionSpinner = v.findViewById(R.id.option_spinner);
    }

    public void setOptionSpinnerList(@ArrayRes int contentListsResID, int contentSelection){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                contentListsResID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOptionSpinner.setAdapter(adapter);
        mOptionSpinner.setSelected(false);
        mOptionSpinner.setSelection(contentSelection,true);
        mOptionSpinner.setOnItemSelectedListener(this);
    }

    public void setOptionSpinnerAction(MenuSpinnerAction action){
        mAction = action;
    }



    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected: " + position + " selected");
        if (mAction != null) {
            mAction.saveSelection(position);
            optionChanged();
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "onNothingSelected called");
    }

    /**
     * An interface that is implemented to change the behavior of the menu spinner, allowing it to
     * save different options.
     */
    public interface MenuSpinnerAction{

        /**
         * Save the selection of the spinner
         * @param position The position of the current selection
         */
        void saveSelection(Integer position);
    }
}
