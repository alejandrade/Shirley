package gigamog.com.stalker.gigamog.com.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gigamog.com.stalker.R;
import gigamog.com.stalker.gigamog.com.services.StalkerService;

import android.Manifest;
import android.widget.TextView;

public class Permission extends Fragment  {


    @BindView(R.id.agreeButton) Button agreeButton;
    @BindView(R.id.permissionLabel) TextView permissionTextView;
    int permissionChecked = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.permission, container, false);
        ButterKnife.bind(this, view);
        //agreeButton = ButterKnife.findById(view, R.id.agreeButton);
       // agreeButton.setOnClickListener(this);

        permissonChecker();


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.agreeButton)
    public void clicker(View v) {

        permissonChecker();



    }



    public void permissonChecker(){



        switch (permissionChecked){
            case 0:
                readContactPermissionCheck();
                break;
            case 1:
                permissionTextView.setText("Permission Check Finished");
                Intent tent = new Intent(getActivity(), StalkerService.class);
                tent.putExtra("extraText","testing");
                getActivity().startService(tent);
                break;
        }
    }

    public void readContactPermissionCheck(){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            permissionChecked++;
        }

    }




}
