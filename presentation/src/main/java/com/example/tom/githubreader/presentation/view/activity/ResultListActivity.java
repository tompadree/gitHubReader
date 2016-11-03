package com.example.tom.githubreader.presentation.view.activity;
/**
 * Created by Tom on 1.11.2016..
 */


        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.Window;
        import android.widget.AbsListView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListAdapter;

        import com.example.tom.githubreader.presentation.R;
        import com.example.tom.githubreader.presentation.di.HasComponent;
        import com.example.tom.githubreader.presentation.di.components.DaggerResultComponent;
        import com.example.tom.githubreader.presentation.di.components.ResultComponent;
        import com.example.tom.githubreader.presentation.di.modules.ResultModule;
        import com.example.tom.githubreader.presentation.model.ResultModel;
        import com.example.tom.githubreader.presentation.view.fragment.ResultListFragment;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import butterknife.OnClick;

/**
 * Activity that shows a list of Users.
 */
public class ResultListActivity extends BaseActivity implements HasComponent<ResultComponent>, ResultListFragment.ResultListListener {

//    public static Intent getCallingIntent(Context context, String searchRepo) {
//
//        Intent callingIntent = new Intent(context, ResultListActivity.class);
//        callingIntent.putExtra("searchRepo", searchRepo);
//        return callingIntent;
//    }

    private String searchRepo="",page="0";
    private ResultComponent resultComponent;
    private ResultListFragment fragment;

    @Bind(R.id.btn_LoadData) Button btn_LoadData;
    @Bind(R.id.editTextSearchRepo) EditText editText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);

        fragment = new ResultListFragment();

//        this.initializeInjector();
//        if (savedInstanceState == null)
//            addFragment(R.id.fragmentContainer, new ResultListFragment());




//        if (savedInstanceState == null) {
//            addFragment(R.id.fragmentContainer, new ResultListFragment());
//        }
    }

    /**
     * Initializes this activity.
     */
//    private void initializeActivity(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            this.searchRepo = getIntent().getExtras().getString("searchRepo", "");
//            addFragment(R.id.fragmentContainer, new ResultListFragment());
//        } else {
//            this.searchRepo = savedInstanceState.getString("searchRepo");
//        }
//    }

    private void initializeInjector() {
        this.resultComponent = DaggerResultComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .resultModule(new ResultModule(this.searchRepo, this.page))
                .build();
    }

    @Override public ResultComponent getComponent() {
        return resultComponent;
    }

    @Override public void onResultClicked(ResultModel resultModel) {
        this.navigator.navigateToUserDetails(this, resultModel.getRepoName());
    }

    @Override public void newPageShow(String str){


        searchRepo = String.valueOf(this.editText.getText());
        this.page = str;
        replaceFragment(R.id.fragmentContainer, fragment);
        fragment = new ResultListFragment();
        this.initializeInjector();

    }



    @OnClick(R.id.btn_LoadData)
    void searchResult() {
        searchRepo = String.valueOf(this.editText.getText());

       // if (fragment != null)
            //new ResultListFragment().onDestroy();
       // addFragment(R.id.fragmentContainer, new ResultListFragment());
        replaceFragment(R.id.fragmentContainer, fragment);
        fragment = new ResultListFragment();
        //replaceFragment(R.id.fragmentContainer, new ResultListFragment());
        this.initializeInjector();
    }

}


