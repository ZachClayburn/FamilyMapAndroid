package clayburn.familymap.app.ui.PersonActivityList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by zach on 4/6/18.
 */

public class PersonViewHolder extends ChildViewHolder {

    private TextView mPersonName;
    private TextView mPersonRelaton;
    private ImageView mGenderIcon;

    public PersonViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create new PersonActivity
            }
        });
    }
}
