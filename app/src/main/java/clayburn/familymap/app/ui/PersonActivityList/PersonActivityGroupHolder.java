package clayburn.familymap.app.ui.PersonActivityList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import clayburn.familymap.app.R;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by zach on 4/5/18.
 */

public class PersonActivityGroupHolder extends GroupViewHolder {

    private TextView mGroupText;
    private ImageView mDropDownArrow;

    public PersonActivityGroupHolder(View itemView) {
        super(itemView);
        mGroupText = itemView.findViewById(R.id.expandable_group_text);
        mDropDownArrow = itemView.findViewById(R.id.drop_down_arrow);
    }

    public void setGroupText(ExpandableGroup group){
        mGroupText.setText(group.getTitle());
    }

}
