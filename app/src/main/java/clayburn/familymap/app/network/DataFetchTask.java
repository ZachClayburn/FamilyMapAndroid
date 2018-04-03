package clayburn.familymap.app.network;

import android.os.AsyncTask;

import clayburn.familymap.ServiceResponses.AllEventResponse;
import clayburn.familymap.ServiceResponses.AllPersonResponse;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;
import clayburn.familymap.model.Model;

/**
 * Created by zach on 3/22/18.
 */

public class DataFetchTask extends AsyncTask<String,Void,String>{

    /**
     * An interface that must be implemented by any class that is going to create a DataFetchTask
     */
    public interface DataFetchCaller {

        /**
         * This method will be called by the DataFetchTask on the object that created it when the
         * transaction with the server is completed Successfully.
         */
        void onDataFetchSuccess();

        /**
         * This method will be called by the DataFetchTask on the object that created it if the
         * transaction with the server fails
         * @param message A message describing the error
         */
        void onDataFetchFailure(String message);
    }

    private static final String SUCCESS_MESSAGE = "TRANSACTION COMPLETED SUCCESSFULLY";

    private DataFetchCaller mCaller;
    private String mServerHost;
    private int mPort;

    /**
     * Creates a new DataFetchTask to log into the server
     * @param caller A reference to the object that is creating the DataFetchTask. It will use this to
     *              call functions to update the caller
     */
    public DataFetchTask(DataFetchTask.DataFetchCaller caller, String serverHost, int port){
        mCaller = caller;
        mServerHost = serverHost;
        mPort = port;
    }


    @Override
    protected String doInBackground(String... strings) {

        String authToken = strings[0];

        Proxy proxy = new Proxy(mServerHost, mPort);
        ServiceResponse response = proxy.getPersons(authToken);
        if (response.getClass() == ErrorResponse.class){
            return ((ErrorResponse) response).getMessage();
        }

        AllPersonResponse personResponse = (AllPersonResponse) response;

        response = proxy.getEvents(authToken);
        if (response.getClass() == ErrorResponse.class){
            return ((ErrorResponse) response).getMessage();
        }

        AllEventResponse eventResponse = (AllEventResponse) response;

        Model.get().populateModel(
                personResponse.getData(),
                eventResponse.getData()
        );

        return SUCCESS_MESSAGE;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals(SUCCESS_MESSAGE)){
            mCaller.onDataFetchSuccess();
        } else {
            mCaller.onDataFetchFailure(result);
        }
    }
}
