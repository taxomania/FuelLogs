package uk.co.taxomania.apps.fuellog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public final class LogDetailActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            LogDetailFragment fragment = LogDetailFragment.newInstance((Log) getIntent()
                    .getParcelableExtra(LogDetailFragment.ARG_LOG));
            getFragmentManager().beginTransaction().add(R.id.log_detail_container, fragment)
                    .commit();
        } // if
    } // onCreate(Bundle)

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigateUpTo(new Intent(this, LogListActivity.class));
            return true;
        }  // if
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected(MenuItem)
} // class LogDetailActivity
