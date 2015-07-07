package de.grimm.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adelgrimm.sckw_appii.MainActivity;
import adelgrimm.sckw_appii.R;

/**
 * Created by Adel on 06.07.2015.
 */
public class VereinNewsFragment extends Fragment implements AbsListView.OnItemClickListener {


    // TODO: For TheNews!!!!
//    private OnFragmentInteractionListener mListener;

    String[] titles, text;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MyListAdapter mAdapter;
    private List listItemList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VereinNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItemList = new ArrayList();
        if (((MainActivity) getActivity()).getTitlesForVerein() != null && ((MainActivity) getActivity()).getTextForVerein() != null) {
            titles = ((MainActivity) getActivity()).getTitlesForVerein();
            text = ((MainActivity) getActivity()).getTextForVerein();
            setupListView();
        } else {
            listItemList.add(new MyListItem("Loading...", "Swipe Down for Update"));
        }
    }

    private void setupListView() {
        for (int i = 0; i < titles.length; i++) {
            listItemList.add(new MyListItem(titles[i], (text[i]).substring(0, 75) + "..."));
        }
        mAdapter = new MyListAdapter(getActivity(), listItemList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyListItem item = (MyListItem) this.listItemList.get(position);
        Toast.makeText(getActivity(), item.getItemTitle() + " Clicked!", Toast.LENGTH_SHORT).show();
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }


}
