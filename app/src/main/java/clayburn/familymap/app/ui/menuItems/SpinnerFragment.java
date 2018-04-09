package clayburn.familymap.app.ui.menuItems;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
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
        mOptionSpinner.setOnItemSelectedListener(this);
    }

    public void setOptionSpinnerListener(@ArrayRes int contentListsResID, MenuSpinnerAction action){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                contentListsResID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOptionSpinner.setAdapter(adapter);
        //TODO Set the value to the current selection
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
        CharSequence selection = (CharSequence) parent.getItemAtPosition(position);
        Log.d(TAG, "onItemSelected: " + selection + " selected");
        if (mAction != null) {
            mAction.saveSelection(selection.toString());
            optionChanged();
            return;
        }
        RuntimeException e = new RuntimeException("A callback was never set");
        Log.e(TAG, "onItemSelected: mAction was never set!",e);
        throw e;
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

    public interface MenuSpinnerAction{
        void saveSelection(String selection);
    }
}
