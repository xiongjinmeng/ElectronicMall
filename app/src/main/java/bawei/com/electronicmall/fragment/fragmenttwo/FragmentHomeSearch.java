package bawei.com.electronicmall.fragment.fragmenttwo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bawei.com.electronicmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeSearch extends Fragment {


    public FragmentHomeSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_home_search, container, false);
    }

}
