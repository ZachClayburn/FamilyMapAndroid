package clayburn.familymap.app.ui;


import android.content.Context;
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

import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familymap.ServiceResponses.RegisterResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;
import clayburn.familymap.app.R;
import clayburn.familymap.app.Utils;
import clayburn.familymap.app.network.DataFetchTask;
import clayburn.familymap.app.network.LogInTask;
import clayburn.familymap.app.network.LoginRegisterParams;
import clayburn.familymap.app.network.RegisterTask;
import clayburn.familymap.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginRegisterFragment
        extends Fragment
        implements
        LogInTask.LoginCaller,
        RegisterTask.RegisterCaller
        ,DataFetchTask.DataFetchCaller{

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

    private enum mEditTextNames{
        serverHost,
        serverPort,
        userName,
        password,
        firstName,
        lastName,
        email
    }

    private LoginRegisterContext mLoginRegisterContext;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Activities that contain this fragment must implement this interface
     */
    public interface LoginRegisterContext{

        /**
         * Called on the containing activity
         */
        void onLoginCompleted();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginRegisterContext) {
            mLoginRegisterContext = (LoginRegisterContext) context;
        } else {
            throw new RuntimeException(context.getClass() + " must implement LoginRegisterContext");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLoginRegisterContext = null;
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
        mServerHostEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.serverHost));

        mServerPortEditText = view.findViewById(R.id.server_port_edit_text);
        mServerPortEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.serverPort));

        mUserNameEditText = view.findViewById(R.id.user_name_edit_text);
        mUserNameEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.userName));

        mPasswordEditText = view.findViewById(R.id.password_edit_text);
        mPasswordEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.password));

        mFirstNameEditText = view.findViewById(R.id.first_name_edit_text);
        mFirstNameEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.firstName));

        mLastNameEditText = view.findViewById(R.id.last_name_edit_text);
        mLastNameEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.lastName));

        mEmailEditText = view.findViewById(R.id.email_edit_text);
        mEmailEditText.addTextChangedListener(new TextFieldListener(mEditTextNames.email));

        mGenderRadioGroup = view.findViewById(R.id.gender_radio_group);
        mGenderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            mGender = (checkedId == R.id.male_radio_button) ? "m" : "f";
            updateRegisterButtonState();
        });

        mRegisterButton = view.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(v -> {
            mIsWorking = true;
            Utils.closeKeyboard(requireActivity(),mServerHostEditText.getWindowToken());
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
            //Low Priority Add a working indicator
        });

        mLogInButton = view.findViewById(R.id.log_in_button);
        mLogInButton.setEnabled(false);
        mLogInButton.setOnClickListener(v -> {
            mIsWorking = true;
            Utils.closeKeyboard(requireActivity(),mServerHostEditText.getWindowToken());
            updateLoginButtonState();
            updateRegisterButtonState();
            LoginRegisterParams params = new LoginRegisterParams(
                    mServerHost,
                    mServerPort,
                    mUserName,
                    mPassword
            );
            new LogInTask(LoginRegisterFragment.this).execute(params);
            //Low Priority Add a working indicator
        });


        return view;
    }

    private class TextFieldListener implements TextWatcher{

        TextFieldListener(mEditTextNames fieldName){
            mFieldName = fieldName;
        }

        mEditTextNames mFieldName;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            switch (mFieldName){

                case serverHost:{
                    mServerHost = s.toString();
                } break;
                case serverPort:{
                    try {
                        mServerPort = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e){
                        mServerPort = 0;
                    }
                } break;
                case userName:{
                    mUserName = s.toString();
                } break;
                case password:{
                    mPassword = s.toString();
                } break;
                case firstName:{
                    mFirstName = s.toString();
                } break;
                case lastName:{
                    mLastName = s.toString();
                } break;
                case email:{
                    mEmail = s.toString();
                } break;
            }
            updateLoginButtonState();
            updateRegisterButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
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

            Model.get().setAuthToken(loginResponse.getAuthToken());
            Model.get().setUserPersonID(loginResponse.getPersonID());
            Model.get().setServerHost(mServerHost);
            Model.get().setServerPort(mServerPort);

            new DataFetchTask(this,mServerHost,mServerPort)
                    .execute(loginResponse.getAuthToken());
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

            Model.get().setAuthToken(registerResponse.getAuthToken());
            Model.get().setUserPersonID(registerResponse.getPersonID());

            new DataFetchTask(this,mServerHost,mServerPort)
                    .execute(registerResponse.getAuthToken());
        } catch (ClassCastException e){
            ErrorResponse errorResponse = (ErrorResponse) response;
            Toast.makeText(getContext(),errorResponse.getMessage(),Toast.LENGTH_LONG).show();
            mIsWorking = false;
            updateLoginButtonState();
            updateRegisterButtonState();
        }

    }

    @Override
    public void onDataFetchSuccess() {
        mLoginRegisterContext.onLoginCompleted();
    }

    @Override
    public void onDataFetchFailure(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
