package clayburn.familymap.app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginRegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private EditText mServerHostEditText;
    private EditText mServerPortEditText;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private RadioGroup mGenderRadioGroup;
    private Button mRegisterButton;
    private Button mLogInButton;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_register,
                container,
                false);

        mServerHostEditText = view.findViewById(R.id.server_host_edit_text);
        mServerPortEditText = view.findViewById(R.id.server_port_edit_text);
        mUserNameEditText = view.findViewById(R.id.user_name_edit_text);
        mPasswordEditText = view.findViewById(R.id.password_edit_text);
        mFirstNameEditText = view.findViewById(R.id.first_name_edit_text);
        mLastNameEditText = view.findViewById(R.id.last_name_edit_text);
        mEmailEditText = view.findViewById(R.id.email_edit_text);
        mGenderRadioGroup = view.findViewById(R.id.gender_radio_group);
        mRegisterButton = view.findViewById(R.id.register_button);
        mLogInButton = view.findViewById(R.id.log_in_button);

        return view;
    }


}
