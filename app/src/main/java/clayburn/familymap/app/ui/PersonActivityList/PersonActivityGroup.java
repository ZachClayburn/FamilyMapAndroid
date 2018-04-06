package clayburn.familymap.app.ui.PersonActivityList;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by zach on 4/5/18.
 */

public class PersonActivityGroup extends ExpandableGroup<ExpandableListItem> {


    public PersonActivityGroup(String title, List<ExpandableListItem> items) {
        super(title, items);
    }

    protected PersonActivityGroup(Parcel in) {
        super(in);
    }
}
