package uk.co.taxomania.apps.fuellog;

import java.util.Calendar;

import uk.co.taxomania.apps.fuellog.DataHelper.Logs;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public final class LogListFragment extends ListFragment {
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callback mCallback;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    interface Callback {
        void onItemSelected(long id);
    } // interface Callback

    public LogListFragment() {
    } // LogListFragment()

    private static final class SimpleAdapter extends SimpleCursorAdapter {
        public SimpleAdapter(final Context context) {
            super(context, android.R.layout.simple_list_item_activated_1, DataHelper.getInstance(
                    context).selectAll(), new String[] { },
                    new int[] { }, 0);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(Logs.TIME)));
            final String s = c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/"
                    + c.get(Calendar.YEAR);
            ((TextView) view.findViewById(android.R.id.text1)).setText(s);
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new SimpleAdapter(getActivity()));
        setHasOptionsMenu(true);
    } // onCreate(Bundle)

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.FIRST, "Add log").setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    } // onCreateOptionsMenu(Menu, MenuInflater)

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == Menu.FIRST) {
            final AddLogDialog d = new AddLogDialog(getActivity());

            d.setButton(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final DataHelper db = DataHelper.getInstance(getActivity());
                    db.insert(new Log(d.getLitres(), d.getPrice(), d.getMileage()));
                    final SimpleCursorAdapter c = ((SimpleCursorAdapter) LogListFragment.this
                            .getListAdapter());
                    c.changeCursor(db.selectAll());
                    c.notifyDataSetChanged();
                } // onClick(View)
            });
            d.show();
            return true;
        } // if
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected(MenuItem)

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
        mCallback.onItemSelected(listView.getItemIdAtPosition(position));
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
