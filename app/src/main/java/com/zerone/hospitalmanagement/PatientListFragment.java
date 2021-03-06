package com.zerone.hospitalmanagement;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.zerone.hospitalmanagement.Database.DoctorDataSource;
import com.zerone.hospitalmanagement.Database.PatientDataSource;
import com.zerone.hospitalmanagement.Model.PatientModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private Spinner doctorNameByList;
    private ImageView emptyList;
    private RecyclerView recyclerView;
    private List<PatientModel> patientModelList = new ArrayList<>();
    private PatientDataSource patientDataSource;
    private PatientAdapter patientAdapter;

    private FragmentController fragmentController;
    private Context context;
    private UserPreference userPreference;

    private String[] doctorName;

    public PatientListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentController = (FragmentController) getActivity();
        userPreference = new UserPreference(context);

        doctorNameByList = view.findViewById(R.id.doctorNameByList);
        recyclerView = view.findViewById(R.id.patientListView);
        emptyList = view.findViewById(R.id.emptyList);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("My Patient");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.logout){
                    if (userPreference.getLoginStatus()){
                        userPreference.setLoginStatus(false);
                    }
                    fragmentController.gotoHomeFragment();
                }
                return true;
            }
        });

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);


        String name = (String) getArguments().getString("loginDoctor");

        if (name.equals("admin")){
            DoctorDataSource doctorDataSource = new DoctorDataSource(getContext());
            doctorName = new String[doctorDataSource.getAlldoctorInfoCollectList().size()];
            for (int i = 0; i < doctorDataSource.getAlldoctorInfoCollectList().size(); i++) {
                doctorName[i] = doctorDataSource.getAlldoctorInfoCollectList().get(i).getDoctorName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,doctorName);
            doctorNameByList.setAdapter(adapter);
            doctorNameByList.setOnItemSelectedListener(this);
        } else {
            doctorNameByList.setVisibility(View.INVISIBLE);
            patientDataSource = new PatientDataSource(getContext());
            patientModelList = patientDataSource.getAllPatient(name);
            patientAdapter = new PatientAdapter(patientModelList, getContext());
            recyclerView.setAdapter(patientAdapter);

            if (!patientDataSource.getAllPatient(name).isEmpty()){
                emptyList.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        patientDataSource = new PatientDataSource(getContext());
        patientModelList = patientDataSource.getAllPatient(doctorName[position]);
        patientAdapter = new PatientAdapter(patientModelList, getContext());
        recyclerView.setAdapter(patientAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
