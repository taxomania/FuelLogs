package uk.co.taxomania.apps.fuellog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public final class LogListFragment extends ListFragment {
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callback mCallback;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    interface Callback {
        void onItemSelected(Log log);
    } // interface Callback

    public LogListFragment() {
    } // LogListFragment()

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<Log> list = new ArrayList<Log>(3);
        list.add(new Log(0, 0, 0));
        list.add(new Log(7.3, 0.9, 5));
        list.add(new Log(8.1, 0.89, 10));
        setListAdapter(new ArrayAdapter<Log>(getActivity(),
                android.R.layout.simple_list_item_activated_1, android.R.id.text1, list));
    } // onCreate(Bundle)

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        } // if
    } // onViewCreated(View, Bundle)

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (final ClassCastException e) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        } // catch
    } // onAttach(Activity)

    @Override
    public void onListItemClick(final ListView listView, final View view, final int position,
            final long id) {
        super.onListItemClick(listView, view, position, id);
        mCallback.onItemSelected((Log) listView.getItemAtPosition(position));
    } // onListItemClick(ListView, View, int, long)

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        } // if
    } // onSaveInstanceState(Bundle)

    public final void setActivateOnItemClick(final boolean activateOnItemClick) {
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    } // setActivateOnItemClick(boolean)

    private void setActivatedPosition(final int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        } // else

        mActivatedPosition = position;
    } // setActivatedPosition(int)
} // class LogListFragment
