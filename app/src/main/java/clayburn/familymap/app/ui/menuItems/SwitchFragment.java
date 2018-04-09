package clayburn.familymap.app.ui.menuItems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import clayburn.familymap.app.R;

public class SwitchFragment extends MenuFragment {

    private Switch mOptionSwitch;

    public SwitchFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_switch, container, false);
        getOptionViews(v);
        return v;
    }

    @Override
    protected void getOptionViews(View v) {
        super.getOptionViews(v);
        mOptionSwitch = v.findViewById(R.id.option_switch);
    }

    public void setOptionSwitchListenter(CompoundButton.OnCheckedChangeListener listener){
        mOptionSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            listener.onCheckedChanged(buttonView,isChecked);
            optionChanged();
        }));
    }
}
