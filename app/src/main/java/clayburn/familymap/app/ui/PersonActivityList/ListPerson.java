package clayburn.familymap.app.ui.PersonActivityList;

import android.os.Parcel;
import android.util.Log;

/**
 * Created by zach on 4/6/18.
 */

public class ListPerson implements ExpandableListItem {

    public static final int PARENT = 0;
    public static final int SPOUSE = 1;
    public static final int CHILD = 2;

    private static final String TAG = "lIST_PERSON";

    private String mPersonID;
    private int mRelation;

    /**
     * Creates a ListPerson from the given personID.
     * @param personID The personID of the Person to be added to the list
     * @param relation The relationship the Person has to the Person who owns the given list
     */
    public ListPerson(String personID, int relation) {
        if(relation == PARENT || relation == SPOUSE || relation == CHILD) {
            mPersonID = personID;
            mRelation = relation;
        } else {
            String er = "Bad Data passed into ListPerson(String, int)";
            Log.e(TAG,er);
            throw new RuntimeException(er);
        }
    }

    protected ListPerson(Parcel in){
        mPersonID = in.readString();
        mRelation = in.readInt();
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
        dest.writeString(mPersonID);
        dest.writeInt(mRelation);
    }

    public static final Creator<ListPerson> CREATOR = new Creator<ListPerson>() {
        @Override
        public ListPerson createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public ListPerson[] newArray(int size) {
            return new ListPerson[0];
        }
    };
}
