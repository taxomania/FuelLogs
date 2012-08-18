package uk.co.taxomania.apps.fuellog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class LogDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            LogDetailFragment fragment = LogDetailFragment.newInstance((Log)getIntent().getParcelableExtra(
                    LogDetailFragment.ARG_ITEM_ID));
            getFragmentManager().beginTransaction().add(R.id.log_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigateUpTo(new Intent(this, LogListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
