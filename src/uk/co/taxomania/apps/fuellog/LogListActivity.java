package uk.co.taxomania.apps.fuellog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LogListActivity extends Activity implements LogListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        if (findViewById(R.id.log_detail_container) != null) {
            mTwoPane = true;
            ((LogListFragment) getFragmentManager().findFragmentById(R.id.log_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Log id) {
        if (mTwoPane) {
            LogDetailFragment fragment = LogDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction().replace(R.id.log_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, LogDetailActivity.class);
            detailIntent.putExtra(LogDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
