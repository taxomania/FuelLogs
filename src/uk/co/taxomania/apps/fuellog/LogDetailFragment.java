package uk.co.taxomania.apps.fuellog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class LogDetailFragment extends Fragment {
    static final String ARG_LOG = LogDetailFragment.class.getName() + ".log";

    private Log mItem;

    static LogDetailFragment newInstance(final Log log) {
        final LogDetailFragment f = new LogDetailFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_LOG, log);
        f.setArguments(args);
        return f;
    } // newInstance(Log)

    public LogDetailFragment() {
    } // LogDetailFragment()

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getArguments().getParcelable(ARG_LOG);
    } // onCreate(Bundle)

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_log_detail, container, false);
        if (mItem != null) {
            View tblView = rootView.findViewById(R.id.litres);
            ((TextView) tblView.findViewById(R.id.label)).setText("Litres");
            ((TextView) tblView.findViewById(R.id.value)).setText(mItem.getLitres() + "");

            tblView = rootView.findViewById(R.id.price);
            ((TextView) tblView.findViewById(R.id.label)).setText("Price");
            ((TextView) tblView.findViewById(R.id.value)).setText(mItem.getPrice() + "");

            tblView = rootView.findViewById(R.id.mileage);
            ((TextView) tblView.findViewById(R.id.label)).setText("Mileage");
            ((TextView) tblView.findViewById(R.id.value)).setText(mItem.getMileage() + "");
        } // if
        return rootView;
    } // onCreateView(LayoutInflater, ViewGroup, Bundle)
} // class LogDetailFragment
