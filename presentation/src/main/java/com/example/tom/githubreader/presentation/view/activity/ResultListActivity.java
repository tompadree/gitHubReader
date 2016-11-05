package com.example.tom.githubreader.presentation.view.activity;
/**
 * Created by Tom on 1.11.2016..
 */


        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.EditText;

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

    private String searchRepo="",page="1";
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
    }


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

    @Override
    public void newPageShow(String page) {
    }

    @OnClick(R.id.btn_LoadData)
    void searchResult() {
        searchRepo = String.valueOf(this.editText.getText());
        replaceFragment(R.id.fragmentContainer, fragment);
        fragment = new ResultListFragment();
        this.initializeInjector();
    }

}


