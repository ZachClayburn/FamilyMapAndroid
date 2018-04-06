package clayburn.familymap.model;

import android.os.Parcel;

/**
 * Created by zach on 4/5/18.
 */

public class ListEvent implements ExpandableListItem {

    private String mEventID;

    /**
     * Create a new ListEvent from a given eventID
     * @param eventID The eventID of the Event to be made into a ListEvent
     */
    public ListEvent(String eventID) {
        mEventID = eventID;
    }

    public String getEventID() {
        return mEventID;
    }

    protected ListEvent(Parcel in){
        mEventID = in.readString();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEventID);
    }

    public static final Creator<ListEvent> CREATOR = new Creator<ListEvent>() {
        @Override
        public ListEvent createFromParcel(Parcel source) {
            return new ListEvent(source);
        }

        @Override
        public ListEvent[] newArray(int size) {
            return new ListEvent[size];
        }
    };
}
