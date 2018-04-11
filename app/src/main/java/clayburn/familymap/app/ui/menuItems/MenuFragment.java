package clayburn.familymap.app.ui.menuItems;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import clayburn.familymap.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MenuFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    protected AppCompatTextView mOptionName;
    protected TextView mOptionDetail;
    protected ConstraintLayout mLayout;

    protected String mSource;

    /**
     * The interface for actions that will be performed on click within the fragment.
     */
    public interface OnClickAction{

        /**
         * Perform the action needed when a user clicks on the fragment
         */
        void clickAction();
    }

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        getOptionViews(v);
        return v;
    }

    protected void getOptionViews(View v) {
        mOptionName = v.findViewById(R.id.menu_fragment_option_name);
        mOptionDetail = v.findViewById(R.id.menu_fragment_option_detail);
        mLayout = v.findViewById(R.id.menu_fragment_layout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Set the string of the OptionName TextView
     * @param text A CharSequence that contains the Name of the option that this MenuFragment will
     *             control
     */
    public void setOptionName(CharSequence text){
        mOptionName.setText(text);
    }

    /**
     * Set the string of the OptionName TextView
     * @param resid The resource id for the string resource that is the Name of the option that this
     *             MenuFragment will control
     */
    public void setOptionName(int resid){
        mOptionName.setText(resid);
    }


    /**
     * Set the string of the OptionName TextView
     * @param text A CharSequence that contains a detailed description of the option that this
     *             MenuFragment will control
     */
    public void setOptionDetail(CharSequence text){
        mOptionDetail.setText(text);
    }

    /**
     * Set the string of the OptionDetail TextView
     * @param resid The resource id for the string resource that is a detailed description of the
     *             option that this MenuFragment will control
     */
    public void setOptionDetail(int resid){
        mOptionDetail.setText(resid);
    }

    /**
     * Set the action to be performed upon the fragment being clicked.
     * @param action The OnClickAction that will listen to clicks on the view.
     */
    public void setClickAction(OnClickAction action){
        mLayout.setOnClickListener(v ->{
            action.clickAction();
            optionChanged(mSource);
        });
    }

    /**
     * Set the source string that will be passed back to the containing activity that implements
     * {@link OnFragmentInteractionListener} through
     * {@link OnFragmentInteractionListener#onOptionChanged(String)}
     * @param source The source string that will allow the parent activity or Fragment to
     *               distinguish between different interactions
     */
    public void setSource(String source) {
        mSource = source;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void optionChanged(String source){
        mListener.onOptionChanged(source);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onOptionChanged(@Nullable String source);
    }

}
