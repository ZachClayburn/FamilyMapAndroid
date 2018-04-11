package clayburn.familymap.app.ui.expandableRecyclerView;

import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import clayburn.familymap.app.R;
import clayburn.familymap.model.PersonActivityGroup;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

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

    @Override
    public void expand() {
        animateArrowOpen();
    }

    @Override
    public void collapse() {
        animateArrowClosed();
    }

    private void animateArrowOpen(){
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF,
                        0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mDropDownArrow.setAnimation(rotate);
    }

    private void animateArrowClosed(){
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF,
                        0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mDropDownArrow.setAnimation(rotate);
    }
}
