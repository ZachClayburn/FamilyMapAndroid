package clayburn.familymap.app.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Model;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECTED_EVENT = "SELECTED_EVENT";
    private static final String TAG = "MAP_FRAGMENT";
    private static final int PERSON_ACTIVITY_REQUEST_CODE = 0;
    private static final int SETTINGS_ACTIVITY_REQUEST_CODE = 1;
    private static final int FILTER_ACTIVITY_REQUEST_CODE = 2;

    private String mSelectedEventID;
    private boolean mInfoLayoutHidden;
    private ArrayList<Polyline> mPolyLines;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private ConstraintLayout mInfoLayout;
    private TextView mEventDetailPersonName;
    private TextView mEventDetailInformation;

    public MapFragment() {
        // Required empty public constructor
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
    interface OnFragmentInteractionListener {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventID Parameter 1.
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance(@Nullable String eventID) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        if (eventID != null) {
            args.putString(SELECTED_EVENT, eventID);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedEventID = getArguments().getString(SELECTED_EVENT);
            //TODO Make the Event from arguments selected
        }
        setHasOptionsMenu(true);//TODO Disable this from the EventActivity
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.family_map);
        mapFragment.getMapAsync(this);

        mEventDetailPersonName = v.findViewById(R.id.event_detail_person_name);
        mEventDetailInformation = v.findViewById(R.id.event_detail_information);

        mInfoLayout = v.findViewById(R.id.info_layout);
        mInfoLayout.setOnClickListener(v1 -> {
            String personID = Model
                    .get()
                    .getEvents()
                    .get(mSelectedEventID)
                    .getPersonID();
            startActivity(
                    PersonActivity.newIntent(getContext(),personID)
            );
        });
        mInfoLayoutHidden = true;

        mPolyLines = new ArrayList<>();

        return v;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(Model.get().getCurrentMapType());

        drawEventsOnMap();

        mMap.setOnMapClickListener(latLng -> {
            if (!mInfoLayoutHidden){
                lowerInfoLayout();
                mSelectedEventID = null;
            }
            clearLines();
        });

        mMap.setOnMarkerClickListener(this::selectEventMarker);
    }

    private void drawEventsOnMap(){

        for(String eventID : Model.get().getEventIDSet()){

            LatLng position = Model.get().getEventLocation(eventID);
            float color = Model.get().getEventColor(eventID);
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(color);
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(icon)
            ).setTag(eventID);
        }
    }

    private boolean selectEventMarker(Marker marker){
        if(mInfoLayoutHidden){
            raiseInfoLayout();
        }
        clearLines();
        mSelectedEventID = (String) marker.getTag();

        LatLng position = marker.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position));

        mEventDetailPersonName.setText(Model.get().getEventPersonName(mSelectedEventID));
        mEventDetailInformation.setText(Model.get().getEventInfo(mSelectedEventID));

        drawRelationLines();

        return true;
    }

    private void drawRelationLines(){
        Log.d(TAG,"drawRelationLines() Called");
        PolylineOptions spouseLine = Model.get().getSpouseLine(mSelectedEventID);
        if (spouseLine != null) {
            mPolyLines.add(mMap.addPolyline(spouseLine));
        }

        PolylineOptions lifeStoryLine = Model.get().getLifeStoryLine(mSelectedEventID);
        if (lifeStoryLine != null) {
            mPolyLines.add(mMap.addPolyline(lifeStoryLine));
        }

        PolylineOptions[] familyTreeLines = Model.get().getFamilyHistoryLines(mSelectedEventID);
        if (familyTreeLines != null) {
            for (PolylineOptions line : familyTreeLines){
                mPolyLines.add(mMap.addPolyline(line));
            }
        }
    }

    private void lowerInfoLayout(){
        float end = mInfoLayout.getTop();
        float start = end - mInfoLayout.getHeight();
        ObjectAnimator.ofFloat(mInfoLayout,"y", start, end)
                .setDuration(300)
                .start();
        mInfoLayoutHidden = !mInfoLayoutHidden;

    }

    private void clearLines(){

        for (Polyline polyline : mPolyLines){
            polyline.remove();
        }
        mPolyLines.clear();
    }

    private void raiseInfoLayout(){
        float start = mInfoLayout.getTop();
        float end = start - mInfoLayout.getHeight();

        ObjectAnimator.ofFloat(mInfoLayout,"y",start,end)
                .setDuration(300)
                .start();
        mInfoLayoutHidden = !mInfoLayoutHidden;
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater The inflater to inflate the menu.
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map,menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_activity_filter:{
                Intent intent = FilterActivity.newIntent(requireContext());
                startActivityForResult(intent,FILTER_ACTIVITY_REQUEST_CODE);
                return true;
            }
            case R.id.map_activity_settings:{
                Intent intent = SettingsActivity.newIntent(requireContext());
                startActivityForResult(intent,SETTINGS_ACTIVITY_REQUEST_CODE);
                return true;
            }
            case R.id.map_activity_search:{
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SETTINGS_ACTIVITY_REQUEST_CODE:{
                handleSettingsActivityResult(data);
            }
        }
    }

    private void handleSettingsActivityResult(Intent data){
        if (SettingsActivity.dataWasSynced(data)){
            clearLines();
            mMap.clear();
            drawEventsOnMap();
        }
        if (SettingsActivity.lineOptionsHaveChanged(data)){
            clearLines();
            if (mSelectedEventID != null) {
                drawRelationLines();
            }
        }
        if (SettingsActivity.mapOptionsHaveChanged(data)){
            mMap.setMapType(Model.get().getCurrentMapType());
        }
    }
}
