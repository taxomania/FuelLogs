package uk.co.taxomania.apps.fuellog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class LogDetailFragment extends Fragment {
    static final String ARG_ITEM_ID = "item_id";

    Log mItem;

    static LogDetailFragment newInstance(final Log log) {
        final LogDetailFragment f = new LogDetailFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_ID, log);
        f.setArguments(args);
        return f;
    }

    public LogDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getParcelable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_detail, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.log_detail)).setText(mItem.getMileage());
        }
        return rootView;
    }
}
