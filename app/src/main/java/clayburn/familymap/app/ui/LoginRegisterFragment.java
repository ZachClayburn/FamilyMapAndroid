package clayburn.familymap.app.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import clayburn.familymap.ServiceRequests.RegisterRequest;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familymap.ServiceResponses.RegisterResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;
import clayburn.familymap.app.R;
import clayburn.familymap.app.network.LogInTask;
import clayburn.familymap.app.network.LoginRegisterParams;
import clayburn.familymap.app.network.RegisterTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginRegisterFragment extends Fragment
        implements LogInTask.LoginCaller, RegisterTask.RegisterCaller{
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

    private String mServerHost;
    private int mServerPort;
    private String mUserName;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mGender;
    private boolean mIsWorking;

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

        mIsWorking = false;

        mServerHostEditText = view.findViewById(R.id.server_host_edit_text);
        mServerHostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mServerHost = s.toString();
                updateLoginButtonState();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mServerPortEditText = view.findViewById(R.id.server_port_edit_text);
        mServerPortEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mServerPort = Integer.parseInt(s.toString());
                } catch (NumberFormatException e){
                    mServerPort = 0;
                }
                updateLoginButtonState();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUserNameEditText = view.findViewById(R.id.user_name_edit_text);
        mUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserName = s.toString();
                updateLoginButtonState();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordEditText = view.findViewById(R.id.password_edit_text);
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
                updateLoginButtonState();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFirstNameEditText = view.findViewById(R.id.first_name_edit_text);
        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFirstName = s.toString();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastNameEditText = view.findViewById(R.id.last_name_edit_text);
        mLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLastName = s.toString();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailEditText = view.findViewById(R.id.email_edit_text);
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmail = s.toString();
                updateRegisterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGenderRadioGroup = view.findViewById(R.id.gender_radio_group);
        mGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.male_radio_button){
                    mGender = "m";
                } else {
                    mGender = "f";
                }
                updateRegisterButtonState();
            }
        });

        mRegisterButton = view.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsWorking = true;
                updateLoginButtonState();
                updateRegisterButtonState();
                LoginRegisterParams params = new LoginRegisterParams(
                        mServerHost,
                        mServerPort,
                        mUserName,
                        mPassword,
                        mFirstName,
                        mLastName,
                        mEmail,
                        mGender
                );
                new RegisterTask(LoginRegisterFragment.this).execute(params);
                //TODO Add a working indicator
            }
        });

        mLogInButton = view.findViewById(R.id.log_in_button);
        mLogInButton.setEnabled(false);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsWorking = true;
                updateLoginButtonState();
                updateRegisterButtonState();
                LoginRegisterParams params = new LoginRegisterParams(
                        mServerHost,
                        mServerPort,
                        mUserName,
                        mPassword
                );
                new LogInTask(LoginRegisterFragment.this).execute(params);
                //TODO Add a working indicator
            }
        });


        return view;
    }

    private void updateLoginButtonState(){
        if (    mIsWorking ||
                TextUtils.isEmpty(mServerHost) ||
                mServerPort == 0 ||
                TextUtils.isEmpty(mUserName) ||
                TextUtils.isEmpty(mPassword)
                ){
            mLogInButton.setEnabled(false);
        } else {
            mLogInButton.setEnabled(true);
        }
    }

    private void updateRegisterButtonState(){
        if (    mIsWorking ||
                TextUtils.isEmpty(mServerHost) ||
                mServerPort == 0 ||
                TextUtils.isEmpty(mUserName) ||
                TextUtils.isEmpty(mPassword) ||
                TextUtils.isEmpty(mFirstName) ||
                TextUtils.isEmpty(mLastName) ||
                TextUtils.isEmpty(mEmail) ||
                TextUtils.isEmpty(mGender)
                ){
            mRegisterButton.setEnabled(false);
        } else {
            mRegisterButton.setEnabled(true);
        }
    }

    @Override
    public void onLogInComplete(ServiceResponse response) {
        try{
            LoginResponse loginResponse = (LoginResponse) response;

            //TODO After successful login, begin data fetch process
            Toast.makeText(getContext(),"SUCCESS!",Toast.LENGTH_LONG).show();
            mIsWorking = false;
            updateRegisterButtonState();
            updateLoginButtonState();
        } catch (ClassCastException e){
            ErrorResponse errorResponse = (ErrorResponse) response;
            Toast.makeText(getContext(),errorResponse.getMessage(),Toast.LENGTH_LONG).show();
            mIsWorking = false;
            updateLoginButtonState();
            updateRegisterButtonState();
        }
    }

    @Override
    public void onRegisterComplete(ServiceResponse response) {
        try{
            RegisterResponse registerResponse = (RegisterResponse) response;

            //TODO After successful login, begin data fetch process
            Toast.makeText(getContext(),"SUCCESS!",Toast.LENGTH_LONG).show();
            mIsWorking = false;
            updateRegisterButtonState();
            updateLoginButtonState();
        } catch (ClassCastException e){
            ErrorResponse errorResponse = (ErrorResponse) response;
            Toast.makeText(getContext(),errorResponse.getMessage(),Toast.LENGTH_LONG).show();
            mIsWorking = false;
            updateLoginButtonState();
            updateRegisterButtonState();
        }

    }
}
