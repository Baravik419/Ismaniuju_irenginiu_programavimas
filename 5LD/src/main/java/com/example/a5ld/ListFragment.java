package com.example.a5ld;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    ListView listView;
    TextView textView;
    String[] fragmentListEntries1;
    String[] fragmentListEntries2;
    FragmentManager fragmentManager;
    Fragment withAFragment;
    Fragment withoutAFragment;

    String pasirinktaEilute;

    Bundle bundle;


    private static final String ARG_PARAM1 = "pasirinktaEilute";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentListEntries1 = getResources().getStringArray(R.array.fragmentListEntries1);
        fragmentListEntries2 = getResources().getStringArray(R.array.fragmentListEntries2);

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = view.findViewById(R.id.listView_fragmentList);
        textView = view.findViewById(R.id.textView_fragmentList);

        bundle = new Bundle();

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, fragmentListEntries2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {listView.setAdapter(listAdapter);}
        });

        fragmentManager = getActivity().getSupportFragmentManager();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("EB", "Pasirinktos eilutes pozicija: " + position + fragmentListEntries2[position]);
                pasirinktaEilute = fragmentListEntries2[position];
                Log.d("EB", "Pasirinkta eilute: " + pasirinktaEilute);

                bundle.putString("pasirinktaEilute", pasirinktaEilute);

                withAFragment = new WithAFragment();
                withoutAFragment = new WithoutAFragment();

                withAFragment.setArguments(bundle);
                withoutAFragment.setArguments(bundle);

                textView.setText(fragmentListEntries2[position]);

                if(pasirinktaEilute.toLowerCase().contains("a")){
                    if(fragmentManager != null){
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout1, withAFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }else{
                    if(fragmentManager != null){
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout1, withoutAFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return view;
    }
}