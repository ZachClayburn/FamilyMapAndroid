package clayburn.familymap.app.ui.PersonActivityList;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import clayburn.familymap.app.R;
import clayburn.familymap.model.ExpandableListItem;
import clayburn.familymap.model.ListEvent;
import clayburn.familymap.model.Model;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class EventViewHolder extends ChildViewHolder {

    private static final String TAG = "EVENT_VIEW_HOLDER";

    private TextView mEventDetails;

    private String mEventID;

    public EventViewHolder(View itemView) {
        super(itemView);
        Log.d(TAG, "EventViewHolder constructor called");
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create new EventActivity with mEventID and itemView.getContext()
            }
        });
        mEventDetails = itemView.findViewById(R.id.event_list_view_details);
    }

    public void onBind(ExpandableListItem item){
        Log.d(TAG,"EventViewHolder.onBind called");
        mEventID = ((ListEvent) item).getEventID();
        mEventDetails.setText(Model.get().getEventInfo(mEventID));
    }



}
