package clayburn.familymap.app.ui.expandableRecyclerView;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import clayburn.familymap.app.R;
import clayburn.familymap.app.ui.PersonActivity;
import clayburn.familymap.model.ExpandableListItem;
import clayburn.familymap.model.ListPerson;
import clayburn.familymap.model.Model;

public class PersonViewHolder extends ChildViewHolder {

    private AppCompatTextView mPersonName;
    private TextView mPersonRelation;
    private ImageView mGenderIcon;

    private String mPersonID;

    public PersonViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PersonActivity.newIntent(itemView.getContext(),mPersonID);
                itemView.getContext().startActivity(intent);
            }
        });
        mGenderIcon = itemView.findViewById(R.id.person_activity_gender_icon);
        mPersonName = itemView.findViewById(R.id.person_activity_list_person_name);
        mPersonRelation = itemView.findViewById(R.id.person_activity_list_relationship);
    }

    public void onBind(ExpandableListItem expandableListItem){

        ListPerson item = (ListPerson) expandableListItem;
        mPersonID = item.getPersonID();

        mPersonName.setText(Model.get().getPersonFullName(mPersonID));

        mGenderIcon.setImageResource(
                Model.get().isMale(mPersonID) ?
                        R.drawable.ic_android_male : R.drawable.ic_android_female
        );

        switch (item.getRelation()){
            case ListPerson.CHILD:{
                mPersonRelation.setText(R.string.relation_child);
            }break;
            case ListPerson.PARENT:{
                mPersonRelation.setText(R.string.relation_parent);
            }break;
            case ListPerson.SPOUSE:{
                mPersonRelation.setText(R.string.relation_spouse);
            }break;
        }
    }
}
