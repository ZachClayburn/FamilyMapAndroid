package clayburn.familymap.app.ui.menuItems;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import clayburn.familymap.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpinnerSwitchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SpinnerSwitchFragment extends SpinnerFragment {

    private Switch mOptionSwitch;

    public SpinnerSwitchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spinner_switch, container, false);
        getOptionViews(v);
        return v;
    }

    @Override
    public void setOptionSpinnerList(@ArrayRes int contentListsResID, int contentSelection) {
        super.setOptionSpinnerList(contentListsResID, contentSelection);
    }

    @Override
    public void setOptionSpinnerAction(MenuSpinnerAction action) {
        super.setOptionSpinnerAction(action);
    }

    @Override
    protected void getOptionViews(View v) {
        super.getOptionViews(v);
        mOptionSwitch = v.findViewById(R.id.option_switch);
    }

    public void setOptionSwitchListener(CompoundButton.OnCheckedChangeListener listener) {
        mOptionSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onCheckedChanged(buttonView,isChecked);
            optionChanged();
        });
    }
}
