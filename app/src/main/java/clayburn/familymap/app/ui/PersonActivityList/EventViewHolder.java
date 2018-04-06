package clayburn.familymap.app.ui.PersonActivityList;

import android.view.View;
import android.widget.TextView;

import clayburn.familymap.app.R;
import clayburn.familymap.model.ExpandableListItem;
import clayburn.familymap.model.ListEvent;
import clayburn.familymap.model.Model;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by zach on 4/5/18.
 */

public class EventViewHolder extends ChildViewHolder {

    private TextView mEventDetails;

    private String mEventID;

    public EventViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create new PersonActivity with mEventID and itemView.getContext()
            }
        });
        mEventDetails = itemView.findViewById(R.id.event_list_view_details);
    }

    public void onBind(ExpandableListItem item){
        mEventID = ((ListEvent) item).getEventID();
        mEventDetails.setText(Model.get().getEventInfo(mEventID));
    }



}
