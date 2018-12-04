package com.music.arts.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.music.arts.R;
import com.music.arts.api.ApiClient;
import com.music.arts.api.ApiStores;
import com.music.arts.mvp.presenter.activity.HomeBasePresenter;
import com.music.arts.mvp.view.activity.HomeBaseView;
import com.music.arts.player.AudioPlaybackService;
import com.music.arts.player.ServiceConnectionState;
import com.music.arts.preference.MMAPrefs;
import com.music.arts.ui.customview.MMATextView;
import com.music.arts.ui.customview.search.ToolbarSearchView;
import com.music.arts.ui.customview.search.onSearchListener;
import com.music.arts.ui.customview.search.onSimpleSearchActionsListener;
import com.music.arts.ui.fragment.DashboardBaseFragment;
import com.music.arts.ui.fragment.EditMusicFragment;
import com.music.arts.ui.fragment.MusicDetailsFragment;
import com.music.arts.ui.fragment.ProfileFragment;
import com.music.arts.ui.fragment.UploadMusicFragment;
import com.music.arts.ui.fragment.UserProfileFragment;
import com.music.arts.utils.DPermissionManager;
import com.music.arts.utils.DViewUtils;
import com.music.arts.utils.MMAUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by bipradip on 31-07-2017.
 */

public class HomeBaseActivity extends AppCompatActivity implements View.OnClickListener, HomeBaseView, NavigationView.OnNavigationItemSelectedListener, onSearchListener, onSimpleSearchActionsListener {

    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CoordinatorLayout base_coordinator_layout;
    private MMATextView tv_toolbar_title, tv_friend_status, tv_profile_name;
    private NavigationView navigationView;
    private CircleImageView civ_profile_pic;

    //Toolbar Action Icons
    private AppCompatImageView iv_toolbar_search_icon, iv_toolbar_paint_save_icon, iv_toolbar_paint_share_icon, iv_toolbar_paint_music_icon, iv_toolbar_add_friend_icon, iv_header_mma_logo;
    private LinearLayout ll_toolbar_paint_action;

    //To Handle Back press event
    private static long back_pressed;

    //Presenter
    private HomeBasePresenter homeBasePresenter;

    //Callback Listeners
    private WeakReference<OnSearchActionListener> searchActionListener;
    private WeakReference<OnMusicPaintActionListener> musicPaintActionListener;
    private WeakReference<IMusicService> iMusicService;

    //For Toolbar Search View
    private boolean mSearchViewAdded = false;
    private ToolbarSearchView mSearchView;
    private WindowManager mWindowManager;
    private DPermissionManager dPermissionManager;

    //Toolbar Changing Status
    private int toolbarStatus;

    private List<Call> calls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_base);

        dPermissionManager = DPermissionManager.getInstance();
        dPermissionManager.setActivity(this);

        initView();
        initSearchView();
        initNavigationDrawer();
        initClickListener();
        initPresenter();
        changeFragmentWithOutBackStack(DashboardBaseFragment.newInstance());
    }

    private void initPresenter() {
        homeBasePresenter = new HomeBasePresenter(this);
        if (MMAUtils.isAndroidM())
            homeBasePresenter.initPermissionCheck(this, dPermissionManager, base_coordinator_layout);
    }

    /**
     * Initialize views with their IDs
     */
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        base_coordinator_layout = (CoordinatorLayout) findViewById(R.id.base_coordinator_layout);
        tv_toolbar_title = (MMATextView) findViewById(R.id.tv_toolbar_title);
        iv_toolbar_search_icon = (AppCompatImageView) findViewById(R.id.iv_toolbar_search_icon);
        ll_toolbar_paint_action = (LinearLayout) findViewById(R.id.ll_toolbar_paint_action);
        iv_toolbar_paint_save_icon = (AppCompatImageView) findViewById(R.id.iv_toolbar_paint_save_icon);
        iv_toolbar_paint_share_icon = (AppCompatImageView) findViewById(R.id.iv_toolbar_paint_share_icon);
        iv_toolbar_paint_music_icon = (AppCompatImageView) findViewById(R.id.iv_toolbar_paint_music_icon);
        iv_toolbar_add_friend_icon = (AppCompatImageView) findViewById(R.id.iv_toolbar_add_friend_icon);
        tv_friend_status = (MMATextView) findViewById(R.id.tv_friend_status);
        iv_header_mma_logo = (AppCompatImageView) findViewById(R.id.iv_header_mma_logo);
    }

    private void initSearchView() {
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mSearchView = new ToolbarSearchView(this);
        mSearchView.setOnSearchListener(this);
        mSearchView.setSearchResultsListener(this);
        mSearchView.setHintText(getString(R.string.hint_txt_search));

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                if (!mSearchViewAdded && mWindowManager != null && !isFinishing()) {
                    mWindowManager.addView(mSearchView, ToolbarSearchView.getSearchViewLayoutParams(HomeBaseActivity.this));
                    mSearchViewAdded = true;
                }
            }
        });
    }

    /**
     * Navigation drawer initialization
     */
    public void initNavigationDrawer() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        tv_profile_name = (MMATextView) headerView.findViewById(R.id.tv_profile_name);
        civ_profile_pic = (CircleImageView) headerView.findViewById(R.id.civ_profile_pic);

        setHeaderViewValue();

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getCurrentFragment();
                if (fragment != null) {
                    if (fragment instanceof DashboardBaseFragment) {
                        ((DashboardBaseFragment) fragment).setCurrentNavItem(4);
                        closeDrawer();
                    }
                }
            }
        });

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawer_layout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void setHeaderViewValue() {

        tv_profile_name.setText(MMAPrefs.getName(this));
        Glide.with(this)
                .load(MMAPrefs.getProfileImage(this))
                .asBitmap()
                .placeholder(R.drawable.ic_profile_picture_no_image)
                .error(R.drawable.ic_profile_picture_no_image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new BitmapImageViewTarget(civ_profile_pic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        civ_profile_pic.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public void initClickListener() {
        iv_toolbar_search_icon.setOnClickListener(this);
        iv_toolbar_paint_save_icon.setOnClickListener(this);
        iv_toolbar_paint_share_icon.setOnClickListener(this);
        iv_toolbar_paint_music_icon.setOnClickListener(this);
        iv_toolbar_add_friend_icon.setOnClickListener(this);
    }


    /**
     * Drawer Enable Disable Method
     *
     * @param enable
     */
    public void enableDrawer(boolean enable) {
        if (enable) {
            if (drawer_layout != null) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mDrawerToggle.setDrawerIndicatorEnabled(enable);
                drawer_layout.setDrawerListener(mDrawerToggle);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (drawer_layout.isDrawerOpen(Gravity.LEFT))
                            closeDrawer();
                        else {
                            deselectAllNavigationItems();
                            drawer_layout.openDrawer(Gravity.LEFT);
                        }
                    }
                });
                mDrawerToggle.syncState();
            }
        } else {
            if (drawer_layout != null) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mDrawerToggle.setDrawerIndicatorEnabled(enable);
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    /**
     * Method to Close Drawer
     */
    private void closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers();
        }
    }

    /*
    *  Method to deselect all navigation items
    * */
    private void deselectAllNavigationItems() {
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    /**
     * Method used to set toolbar title
     *
     * @param toolbarTitle
     */
    public void setToolBarTitle(String toolbarTitle) {
        if (!TextUtils.isEmpty(toolbarTitle))
            if (tv_toolbar_title != null)
                tv_toolbar_title.setText(toolbarTitle);
    }

    /**
     * Method used to show/hide topbar MMA Logo
     *
     * @param isShowLogo
     */
    public void showHeaderMmaLogo(boolean isShowLogo) {
        iv_header_mma_logo.setVisibility(isShowLogo ? View.VISIBLE : View.GONE);
    }

    /**
     * Getter Setter used to get current status of toolbar
     *
     * @param toolbarStatus
     */
    public void setToolbarStatus(int toolbarStatus) {
        this.toolbarStatus = toolbarStatus;
    }

    public int getToolbarStatus() {
        return toolbarStatus;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.nav_item_logout))) {
            homeBasePresenter.openLogoutDialog(this, base_coordinator_layout);
        } else {
            homeBasePresenter.changeFragment(HomeBaseActivity.this, item.getItemId());
        }

        closeDrawer();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_search_icon:
                mSearchView.display();
                break;

            case R.id.iv_toolbar_paint_save_icon:
                if (musicPaintActionListener.get() != null)
                    musicPaintActionListener.get().onPaintSaveClicked();
                break;

            case R.id.iv_toolbar_paint_share_icon:
                if (musicPaintActionListener.get() != null)
                    musicPaintActionListener.get().onPaintShareClicked();
                break;

            case R.id.iv_toolbar_paint_music_icon:
                if (musicPaintActionListener.get() != null)
                    musicPaintActionListener.get().onPaintMusicClicked();
                break;

            case R.id.iv_toolbar_add_friend_icon:
                if (getCurrentFragment() instanceof UserProfileFragment) {
                    ((UserProfileFragment) getCurrentFragment()).addFriend();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getCurrentFragment();
        if (resultCode == RESULT_OK) {
            if (requestCode == getResources().getInteger(R.integer.request_edit_profile)) {
                if (fragment instanceof DashboardBaseFragment) {
                    if (((DashboardBaseFragment) fragment).getVisibleFragment() instanceof ProfileFragment && data != null) {
                        ((ProfileFragment) ((DashboardBaseFragment) fragment).getVisibleFragment()).onUpdate(data.getBooleanExtra(getString(R.string.profile_edit_status), false));
                    }
                }
            } else if (requestCode == getResources().getInteger(R.integer.request_pick_music)) {
                if (data != null) {
                    if (fragment instanceof UploadMusicFragment)
                        ((UploadMusicFragment) fragment).onUpdate(data);
                    else if (fragment instanceof EditMusicFragment)
                        ((EditMusicFragment) fragment).onUpdate(data);
                }
            }
        } else if (resultCode == getResources().getInteger(R.integer.request_code_take_photo)) {
            String imagePath = data.getStringExtra(getString(R.string.intent_key_camera_or_gallery_image_path));
            if (imagePath.contains(getString(R.string.external_files))) {
                imagePath = imagePath.replace(getString(R.string.external_files), getString(R.string.storage_path));
            }
            if (musicPaintActionListener.get() != null)
                musicPaintActionListener.get().onCapturedBackgroundImage(imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dPermissionManager.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void changeActivity(Class<?> _class) {
        Intent configureTestIntent = new Intent(HomeBaseActivity.this, _class);
        startActivity(configureTestIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void addFragment(Fragment fragment, boolean isFadeInEntrance) {
        String fragment_name = fragment.getClass().getSimpleName();
        Fragment mFragment = getSupportFragmentManager().findFragmentByTag(fragment_name);
        if (mFragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (isFadeInEntrance)
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            else
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.fl_fragment_container, fragment, fragment_name);
            fragmentTransaction.addToBackStack(fragment_name);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void changeFragment(Fragment fragment) {
        if (fragment != null) {
            String fragment_name = fragment.getClass().getSimpleName();
            Fragment mFragment = getSupportFragmentManager().findFragmentByTag(fragment_name);

            if (mFragment == null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fl_fragment_container, fragment, fragment_name);
                fragmentTransaction.addToBackStack(fragment_name);
                fragmentTransaction.commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        }
    }

    @Override
    public void changeFragmentWithOutBackStack(Fragment fragment) {
        String fragment_name = fragment.getClass().getSimpleName();
        Fragment mFragment = getSupportFragmentManager().findFragmentByTag(fragment_name);
        if (mFragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fl_fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void popFragment() {
        DViewUtils.hideSoftKeyboard(this);
        getSupportFragmentManager().popBackStack();
    }

    private Fragment getCurrentFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container);
        return fragment;
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fm = getSupportFragmentManager();
        int entryCount = fm.getBackStackEntryCount();
        if (entryCount > 1) {
            popFragment();
        } else {
            if (back_pressed + 1500 > System.currentTimeMillis()) {
                homeBasePresenter.unbindService(this);
                finish();
                super.onBackPressed();
            } else {
                MMAUtils.showSnackBar(HomeBaseActivity.this, base_coordinator_layout, getString(R.string.press_again_to_exit), false);
            }
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (getCurrentFragment() instanceof MusicDetailsFragment)
            ((MusicDetailsFragment) getCurrentFragment()).onDispatchKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }

    /**
     * ==================================
     * Toolbar Action Icon Enable/Disable
     * ==================================
     */

    //Method used to show search icon
    public void showSearch(boolean isShowSearch) {
        iv_toolbar_search_icon.setVisibility(isShowSearch ? View.VISIBLE : View.GONE);
    }

    //Method used to show music paint action icons
    public void showMusicPaintActionMenus(boolean isShowActionMenu) {
        ll_toolbar_paint_action.setVisibility(isShowActionMenu ? View.VISIBLE : View.GONE);
    }

    //Method used to show add friend icons
    public void showAddFriendOption(boolean isAddFriend) {
        iv_toolbar_add_friend_icon.setVisibility(isAddFriend ? View.VISIBLE : View.GONE);
    }

    //Method used to show friend status
    public void showFriendStatus(boolean isFriendStatus) {
        tv_friend_status.setVisibility(isFriendStatus ? View.VISIBLE : View.GONE);
    }

    //Method used to hide already visible search bar
    public void hideSearchBar() {
        if (mSearchView.isSearchViewVisible())
            mSearchView.hide();
    }

    /**
     * ============================
     * Search View Callback
     * ============================
     */

    @Override
    public void onSearch(String query) {
        if (mSearchView.isSearchViewVisible()) {
            if (searchActionListener.get() != null)
                searchActionListener.get().onSearchClicked(query);
        }
    }

    @Override
    public void searchViewOpened() {

    }

    @Override
    public void searchViewClosed() {

    }

    @Override
    public void onCancelSearch() {
        if (searchActionListener.get() != null)
            searchActionListener.get().onSearchBack();
        mSearchView.hide();
    }

    @Override
    public void clearSearch() {
        if (searchActionListener.get() != null)
            searchActionListener.get().onSearchBack();
    }

    @Override
    public void onItemClicked(String item) {
    }

    @Override
    public void onScroll() {
    }

    @Override
    public void error(String localizedMessage) {
    }

    /**
     * =========================================
     * Required for API web Service
     * =========================================
     */
    public ApiStores apiStores() {
        return ApiClient.retrofit(HomeBaseActivity.this, getString(R.string.api_server_url)).create(ApiStores.class);
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    /**
     * =============================
     * Callback Listeners
     * =============================
     */

    //Required For Search Action
    public interface OnSearchActionListener {
        void onSearchClicked(String searchText);

        void onSearchBack();
    }

    public void setOnSearchActionListener(OnSearchActionListener _searchActionListener) {
        searchActionListener = new WeakReference<>(_searchActionListener);
    }

    //Required For Music Paint Action
    public interface OnMusicPaintActionListener {
        void onPaintSaveClicked();

        void onPaintShareClicked();

        void onPaintMusicClicked();

        void onCapturedBackgroundImage(String imagePath);
    }

    public void setOnMusicPaintActionListener(OnMusicPaintActionListener _musicPaintActionListener) {
        musicPaintActionListener = new WeakReference<>(_musicPaintActionListener);
    }

    //Require for Music play feature
    public interface IMusicService {
        void onMusicServiceConnected(AudioPlaybackService mPlaybackService);
    }

    public void setOnMusicServiceListener(IMusicService _iMusicService) {
        iMusicService = new WeakReference<>(_iMusicService);
    }


    /**
     * ====================================
     * Required for Music Player Service
     * ====================================
     */
    public void bindPlaybackService(Intent playerServiceIntent) {
        homeBasePresenter.bindService(this, playerServiceIntent);
    }

    public void unbindPlaybackService() {
        homeBasePresenter.unbindService(this);
    }

    public ServiceConnectionState getPlaybackServiceState() {
        return homeBasePresenter.getServiceState();
    }

    @Override
    public void onServiceConnected(AudioPlaybackService mPlaybackService) {
        if (iMusicService.get() != null)
            iMusicService.get().onMusicServiceConnected(mPlaybackService);
    }
}
