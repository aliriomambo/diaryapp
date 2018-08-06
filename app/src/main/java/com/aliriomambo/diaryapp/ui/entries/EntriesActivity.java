package com.aliriomambo.diaryapp.ui.entries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.Utils.ActivityUtils;
import com.aliriomambo.diaryapp.ui.credential.CredentialActivity;
import com.google.firebase.auth.FirebaseAuth;


public class EntriesActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);
        mAuth = FirebaseAuth.getInstance();
        mDrawerLayout = findViewById(R.id.drawer_layout_entries_activity);
        NavigationView navigationView = findViewById(R.id.nav_view_entries_activity);
        EntriesFragment entriesFragment = (EntriesFragment) getSupportFragmentManager().
                findFragmentById(R.id.frame_layout_entries_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }


        if (entriesFragment == null) {
            entriesFragment = EntriesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), entriesFragment, R.id.frame_layout_entries_activity);
        }

        new EntriesPresenter(this, entriesFragment, entriesFragment);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId() == R.id.nav_signout) {
                    if (mAuth.getCurrentUser() != null) {
                        mAuth.signOut();
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent startCredentialActivity = new Intent(EntriesActivity.this, CredentialActivity.class);
                        startActivity(startCredentialActivity);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not authenticated", Toast.LENGTH_SHORT).show();
                    }
                    mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
