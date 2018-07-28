package info.dropbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import info.Fragments.DropBoxContentFragment;

public class DropBoxContentActivity extends AppCompatActivity {

    DropBoxContentFragment mDropBoxContentFragment;
    int backButtonCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_box_content);

        mDropBoxContentFragment = new DropBoxContentFragment();
        loadDropBoxContentFragment();
    }

    private void loadDropBoxContentFragment() {

        getSupportFragmentManager().beginTransaction().replace(R.id.dropboxcontent_fragment, mDropBoxContentFragment).
                addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            backButtonCount = 0;
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
