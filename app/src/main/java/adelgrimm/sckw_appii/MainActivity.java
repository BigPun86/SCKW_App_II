package adelgrimm.sckw_appii;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends AppCompatActivity {


    private CharSequence mTitle;
//    String[] titlesArray, descArray;

    private String[] titlesForAktive, textForAktive, titlesForVerein, textForVerein, titlesForJunioren, textForJunioren;

    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(drawerToggle);

        mTitle = getTitle();

        // Set the menu icon instead of the launcher icon.
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        titlesForAktive = i.getStringArrayExtra(SplashScreen.TITLES_AKTIVE);
        textForAktive = i.getStringArrayExtra(SplashScreen.DESCRIPTIONS_AKTIVE);
        titlesForVerein = i.getStringArrayExtra(SplashScreen.TITLES_VEREIN);
        textForVerein = i.getStringArrayExtra(SplashScreen.DESCRIPTIONS_VEREIN);
        titlesForJunioren = i.getStringArrayExtra(SplashScreen.TITLES_JUNIOREN);
        textForJunioren = i.getStringArrayExtra(SplashScreen.DESCRIPTIONS_JUNIOREN);
        setTitlesForAktive(titlesForAktive);
        setTextForAktive(textForAktive);
        setTitlesForVerein(titlesForVerein);
        setTextForVerein(textForVerein);
        setTitlesForJunioren(titlesForJunioren);
        setTextForJunioren(textForJunioren);

//        AktivenNewsFragment fragment = new AktivenNewsFragment();
//        Bundle b = new Bundle();
//        b.putStringArray(SplashScreen.TITLES, titlesArray);
//        b.putStringArray(SplashScreen.DESCRIPTIONS, descArray);
//        fragment.setArguments(b);


        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        // update the main content by replacing fragments
//        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragment = new PlaceholderFragment().newInstance(1);
                break;
            case R.id.nav_news_fragment:
                fragment = new NewsFragment().newInstance(2);
                break;
            case R.id.nav_liveTicker_fragment:
                fragment = new PlaceholderFragment().newInstance(3);
                break;
            case R.id.nav_impressum_fragment:
                fragment = new PlaceholderFragment().newInstance(4);
                break;
            case R.id.nav_whatsHot_fragment:
                fragment = new PlaceholderFragment().newInstance(5);
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public String[] getTitlesForAktive() {
        return titlesForAktive;
    }

    public void setTitlesForAktive(String[] titlesForAktive) {
        this.titlesForAktive = titlesForAktive;
    }

    public String[] getTextForAktive() {
        return textForAktive;
    }

    public void setTextForAktive(String[] textForAktive) {
        this.textForAktive = textForAktive;
    }

    public String[] getTitlesForVerein() {
        return titlesForVerein;
    }

    public void setTitlesForVerein(String[] titlesForVerein) {
        this.titlesForVerein = titlesForVerein;
    }

    public String[] getTextForVerein() {
        return textForVerein;
    }

    public void setTextForVerein(String[] textForVerein) {
        this.textForVerein = textForVerein;
    }

    public String[] getTitlesForJunioren() {
        return titlesForJunioren;
    }

    public void setTitlesForJunioren(String[] titlesForJunioren) {
        this.titlesForJunioren = titlesForJunioren;
    }

    public String[] getTextForJunioren() {
        return textForJunioren;
    }

    public void setTextForJunioren(String[] textForJunioren) {
        this.textForJunioren = textForJunioren;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
