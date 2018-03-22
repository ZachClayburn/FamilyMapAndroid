package clayburn.familymap.app.network;

import android.os.AsyncTask;

import clayburn.familymap.ServiceRequests.RegisterRequest;
import clayburn.familymap.ServiceResponses.ServiceResponse;

/**
 * Created by zachc_000 on 3/22/2018.
 */

public class RegisterTask extends AsyncTask<LoginRegisterParams,Void,ServiceResponse> {

    /**
     * An interface that must be implemented by any class that is going to create a LogInTask
     */
    public interface RegisterCaller{

        /**
         * This method will be called by the RegisterTask on the object that created it when the
         * transaction with the server is complete. The response will be the response from the
         * server, or an error generated by the RegisterTask itself.
         * @param response Either a RegisterResponse from the server containing an authorization
         *                token, or an ErrorResponse containing an error message.
         */
        void onRegisterComplete(ServiceResponse response);
    }

    private RegisterCaller mCaller;

    /**
     * Creates a new RegisterTask to log into the server
     * @param caller A reference to the object that is creating the RegisterTask. It will use this
     *              to call functions to update the caller
     */
    public RegisterTask(RegisterCaller caller){
        mCaller = caller;
    }

    @Override
    protected ServiceResponse doInBackground(LoginRegisterParams... loginRegisterParams) {

        LoginRegisterParams params = loginRegisterParams[0];

        String serverHost = params.getServerHost();
        int serverPort = params.getServerPort();
        String userName = params.getUserName();
        String password = params.getPassword();
        String firstName = params.getFirstName();
        String lastName = params.getLastName();
        String email = params.getEmail();
        String gender = params.getGender();

        Proxy proxy = new Proxy(serverHost,serverPort);
        RegisterRequest request = new RegisterRequest(
                userName,
                password,
                email,
                firstName,
                lastName,
                gender
        );

        return proxy.register(request);
    }

    @Override
    protected void onPostExecute(ServiceResponse response) {
        mCaller.onRegisterComplete(response);
    }
}