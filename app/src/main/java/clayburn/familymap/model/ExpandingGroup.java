package clayburn.familymap.model;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by zach on 4/5/18.
 */

public class ExpandingGroup extends ExpandableGroup<ExpandableListItem> {


    public static final String FAMILY_GROUP_TITLE = "family";
    public static final String EVENT_GROUP_TITLE = "event";
    public static final String PERSON_GROUP_TITLE = "person";

    public ExpandingGroup(String title, List<ExpandableListItem> items) {
        super(title, items);
    }

    protected ExpandingGroup(Parcel in) {
        super(in);
    }
}
