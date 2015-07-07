package adelgrimm.sckw_appii;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.grimm.view.AktivenNewsFragment;
import de.grimm.view.JuniorenNewsFragment;
import de.grimm.view.VereinNewsFragment;

/**
 * Created by Adel on 06.07.2015.
 */
public class NewsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public NewsFragment() {
    }

    public NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);

        mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        // Setup TabsContent Fragments
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Aktive"), AktivenNewsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Verein"), VereinNewsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("Junioren"), JuniorenNewsFragment.class, null);

        return rootView;
    }


}