package clayburn.familymap.app.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import clayburn.familymap.app.R;
import clayburn.familymap.model.Event;
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

    // TODO: Rename and change types of parameters
    private String mSelectedEventID;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private ConstraintLayout mInfoLayout;
    private boolean mInfoLayoutHidden;

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
    public interface OnFragmentInteractionListener {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventID Parameter 1.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.family_map);
//        MapView mapView = v.findViewById(R.id.family_map);
//        mapView.getMapAsync(this);
        mapFragment.getMapAsync(this);
        mInfoLayout = v.findViewById(R.id.info_layout);
        mInfoLayoutHidden = true;

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

        drawMapContents();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mInfoLayoutHidden){
                    raiseInfoLayout();
                } else {
                    lowerInfoLayout();
                }
            }
        });//TODO Figure out map onClickListener

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private void drawMapContents(){
        //TODO Add filter functionality

        for(String eventID : Model.get().getEventIDSet()){

            Event event = Model.get().getEvents().get(eventID);
            LatLng position = Model.get().getEventPosition(eventID);
            float color = Model.get().getEventColor(eventID);
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(color);
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(icon)
            ).setTag(eventID);
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

    private void raiseInfoLayout(){
        float start = mInfoLayout.getTop();
        float end = start - mInfoLayout.getHeight();

        ObjectAnimator.ofFloat(mInfoLayout,"y",start,end)
                .setDuration(300)
                .start();
        mInfoLayoutHidden = !mInfoLayoutHidden;
    }
}