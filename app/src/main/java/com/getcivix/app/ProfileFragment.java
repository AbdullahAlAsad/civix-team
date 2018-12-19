package com.getcivix.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.getcivix.app.Models.ReportModel;
import com.getcivix.app.Models.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //  user data
    private EditText mEmailInput;
    private EditText mUserNameInput;
    private EditText mUseGenderInput;
    private EditText mUserInterestInput;
    private Button mRegisterButton;

    // report data

  //  editTextEnterYourComment  buttonSubmitReport
    private EditText mComment;
    private Button mSubmitReportButton;


    private DatabaseReference mFirebaseDatabaseUser;
    private DatabaseReference mFirebaseDatabaseReport;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    //private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View mView =  inflater.inflate(R.layout.fragment_profile, null);
        mEmailInput = mView.findViewById(R.id.editTextEnterEmail);
        mUserNameInput = mView.findViewById(R.id.editTextEnterUsername);
        mUseGenderInput = mView.findViewById(R.id.editTextEnterGender);
        mUserInterestInput = mView.findViewById(R.id.editTextEnterInterests);
        mComment =  mView.findViewById(R.id.editTextEnterYourComment);
//      mProfilePictureInput = mView.findViewById(R.id.textViewAttachPicture);
        mRegisterButton = mView.findViewById(R.id.buttonRegisterUser);
        mSubmitReportButton = mView.findViewById(R.id.buttonSubmitReport);


        userId = StaticConstants.userID;




        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabaseUser = mFirebaseInstance.getReference("users");

        // get reference to 'eventReport' node
        mFirebaseDatabaseReport = mFirebaseInstance.getReference("eventReport");

        // Save / update the user
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mUserNameInput.getText().toString();
                String email = mEmailInput.getText().toString();
                String gender = mUseGenderInput.getText().toString();
                String interest = mUserInterestInput.getText().toString();
                Integer credibility = 0;
                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email,gender,interest,credibility);
                } else {
                    Log.d("Profile fragment", "User already exists");
                    updateUser(name, email,gender,interest,credibility);
                }
            }
        });

        // Save report
        mSubmitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = mComment.getText().toString();
                Long reportTime = System.currentTimeMillis();
                LatLng reportLocation = StaticConstants.reportLocation;
                String reporterId = userId;

                createReport(comment,reportTime,reportLocation,reporterId );

            }
        });

        return mView;
    }

    private void createReport(String comment, Long reportTime, LatLng reportLocation, String reporterId) {


            String reportId = mFirebaseDatabaseReport.push().getKey();

        String location;

        if(null != reportLocation){
            location  = reportLocation.toString();
        }else{
            location = "0,0";
        }



        ReportModel report = new ReportModel(comment, reportTime, location, reporterId);

        mFirebaseDatabaseReport.child(reportId).setValue(report);
    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(String userName, String email, String gender, String interest, Integer credibility) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabaseUser.push().getKey();
        }

        User user = new User(userName, email, gender, interest, credibility);

        mFirebaseDatabaseUser.child(userId).setValue(user);
    }

    private void updateUser(String userName, String email, String gender, String interest, Integer credibility) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(userName))
            mFirebaseDatabaseUser.child(userId).child("userName").setValue(userName);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabaseUser.child(userId).child("email").setValue(email);

        if (!TextUtils.isEmpty(gender))
            mFirebaseDatabaseUser.child(userId).child("gender").setValue(gender);

        if (!TextUtils.isEmpty(interest))
            mFirebaseDatabaseUser.child(userId).child("interest").setValue(interest);

            mFirebaseDatabaseUser.child(userId).child("credibility").setValue(credibility);
    }





}
