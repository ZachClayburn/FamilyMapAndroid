package clayburn.familymap.app.ui.PersonActivityList;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import clayburn.familymap.app.R;
import clayburn.familymap.model.PersonActivityGroup;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by zach on 4/5/18.
 */

public class PersonActivityGroupHolder extends GroupViewHolder {

    private static final String TAG = "PERSON_ACT_GROUP_HOLDER";

    private TextView mGroupText;
    private ImageView mDropDownArrow;

    public PersonActivityGroupHolder(View itemView) {
        super(itemView);
        mGroupText = itemView.findViewById(R.id.expandable_group_text);
        mDropDownArrow = itemView.findViewById(R.id.drop_down_arrow);
    }

    public void onBind(ExpandableGroup group){

        switch (group.getTitle()){
            case PersonActivityGroup.EVENT_GROUP_TITLE:{
                mGroupText.setText(R.string.events_group_title);
            }break;
            case PersonActivityGroup.FAMILY_GROUP_TITLE:{
                mGroupText.setText(R.string.family_group_title);
            }break;
            default:{
                String er = "Unrecognized group category in PersonActivityGroupHolder.onBind";
                Log.e(TAG,er);
            }
        }
    }

}
