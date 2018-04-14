package clayburn.familymap.app.ui.expandableRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.List;

import static android.view.LayoutInflater.from;

import clayburn.familymap.app.R;
import clayburn.familymap.model.ExpandableListItem;
import clayburn.familymap.model.ExpandingGroup;

public class ExpandableListAdapter
        extends MultiTypeExpandableRecyclerViewAdapter<ExpandingGroupHolder,ChildViewHolder> {

    public static final int PERSON_VIEW_TYPE = 3;
    public static final int EVENT_VIEW_TYPE = 4;

    private static final String TAG = "PERSON_ACTIVITY_ADAPTER";

    public ExpandableListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    /**
     * Called from {@link #onCreateViewHolder(ViewGroup, int)} when
     * the list item created is a group
     *
     * @param parent   the {@link ViewGroup} in the list for which a
     * {@link ExpandingGroupHolder}  is being created
     * @param viewType an int returned by {@link ExpandableRecyclerViewAdapter#getItemViewType(int)}
     * @return A {@link ExpandingGroupHolder} corresponding to the group list item with the
     * {@code ViewGroup} parent
     */
    @Override
    public ExpandingGroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"ExpandingGroupHolder.onCreateGroupViewHolder called");
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.expandable_group_view,parent,false);
        return new ExpandingGroupHolder(view);
    }

    /**
     * Called from {@link #onCreateViewHolder(ViewGroup, int)} when the list item created is a child
     *
     * @param parent   the {@link ViewGroup} in the list for which a {@link ChildViewHolder}
     *               is being created
     * @param viewType an int returned by {@link ExpandableRecyclerViewAdapter#getItemViewType(int)}
     * @return A {@link ChildViewHolder} corresponding to child list item with the
     * {@code ViewGroup} parent
     */
    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG,"ExpandingGroupHolder.onCreateChildViewHolder called");
        LayoutInflater inflater = from(parent.getContext());
        View view;

        switch (viewType) {
            case EVENT_VIEW_TYPE:
                view = inflater.inflate(R.layout.event_list_view, parent, false);
                return new EventViewHolder(view);
            case PERSON_VIEW_TYPE:
               view = inflater.inflate(R.layout.person_list_view, parent, false);
               return new PersonViewHolder(view);
            default:
                String er = "Illegal type in onCreateChildViewHolder";
                Log.e(TAG,er);
                throw new RuntimeException(er);
        }
    }

    /**
     * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item
     * bound to is a  child.
     * <p>
     * Bind data to the {@link ChildViewHolder} here.
     *
     * @param holder       The {@code CVH} to bind data to
     * @param flatPosition the flat position (raw index) in the list at which to bind the child
     * @param group        The {@link ExpandableGroup} that the the child list item belongs to
     * @param childIndex   the index of this child within it's {@link ExpandableGroup}
     */
    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {
        Log.d(TAG,"ExpandingGroupHolder.onBindChildViewHolder called");
        int viewType = getItemViewType(flatPosition);

        ExpandableListItem item = ((ExpandingGroup) group).getItems().get(childIndex);

        switch (viewType){
            case PERSON_VIEW_TYPE:{
                ((PersonViewHolder) holder).onBind(item);
            }break;
            case EVENT_VIEW_TYPE:{
                ((EventViewHolder) holder).onBind(item);
            }break;
        }
    }

    /**
     * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item bound to is a
     * group
     * <p>
     * Bind data to the {@link ExpandingGroupHolder} here.
     *
     * @param holder       The {@code GVH} to bind data to
     * @param flatPosition the flat position (raw index) in the list at which to bind the group
     * @param group        The {@link ExpandableGroup} to be used to bind data to this {@link
     * ChildViewHolder}
     */
    @Override
    public void onBindGroupViewHolder(ExpandingGroupHolder holder,
                                      int flatPosition, ExpandableGroup group) {
        Log.d(TAG,"ExpandingGroupHolder.onBindGroupViewHolder called");
        holder.onBind(group);
    }

    /**
     * Used to allow subclasses to have multiple view types for children
     *
     * @param position   the flat position in the list
     * @param group      the group that this child belongs to
     * @param childIndex the index of the child within the group
     * @return any int representing the viewType for a child within the {@code group} *EXCEPT*
     * for {@link ExpandableListPosition#CHILD} and {@link ExpandableListPosition#GROUP}.
     * <p>
     * If you do *not* override this method, the default viewType for a group is {@link
     * ExpandableListPosition#CHILD}
     * <p>
     * <p>
     * A subclass may use any number *EXCEPT* for {@link ExpandableListPosition#CHILD} and {@link
     * ExpandableListPosition#GROUP} as those are already being used by the adapter
     * </p>
     */
    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        Log.d(TAG,"ExpandingGroupHolder.getChildViewType called");
        switch (group.getTitle()){
            case ExpandingGroup.FAMILY_GROUP_TITLE:
                return PERSON_VIEW_TYPE;
            case ExpandingGroup.EVENT_GROUP_TITLE:
                return EVENT_VIEW_TYPE;
            case ExpandingGroup.PERSON_GROUP_TITLE:
                return PERSON_VIEW_TYPE;
            default:
                String er = "Illegal type in ExpandableListAdapter.getChildViewType";
                Log.e(TAG,er);
                throw new RuntimeException(er);

        }
    }

    /**
     * @param viewType the int corresponding to the viewType of a child of a {@code ExpandableGroup}
     * @return if a subclasses has *NOT* overridden {@code getChildViewType} than the viewType for
     * the child is defaulted to {@link ExpandableListPosition#CHILD}
     */
    @Override
    public boolean isChild(int viewType) {
        Log.d(TAG,"ExpandingGroupHolder.isChild called");
        return viewType == EVENT_VIEW_TYPE || viewType == PERSON_VIEW_TYPE;
    }
}
