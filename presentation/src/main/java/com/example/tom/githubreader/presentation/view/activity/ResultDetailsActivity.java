package com.example.tom.githubreader.presentation.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.tom.githubreader.presentation.R;
import com.example.tom.githubreader.presentation.di.HasComponent;
import com.example.tom.githubreader.presentation.di.components.DaggerResultComponent;
import com.example.tom.githubreader.presentation.di.components.ResultComponent;
import com.example.tom.githubreader.presentation.di.modules.ResultModule;
import com.example.tom.githubreader.presentation.view.fragment.ResultDetailsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity that shows details of a certain repo.
 */
public class ResultDetailsActivity extends BaseActivity implements HasComponent<ResultComponent> {

    private static final String INTENT_EXTRA_PARAM_RESULT_ID = "org.android10.INTENT_PARAM_USER_ID";
    private static final String INSTANCE_STATE_PARAM_RESULT_ID = "org.android10.STATE_PARAM_USER_ID";

    public static Intent getCallingIntent(Context context, String repoName) {
        Intent callingIntent = new Intent(context, ResultDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_RESULT_ID, repoName);
        return callingIntent;
    }
    @Bind(R.id.btn_LoadData)
    Button btn_LoadData;
    @Bind(R.id.editTextSearchRepo)
    EditText editText;
    private String repoName;
    private ResultComponent resultComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);

        btn_LoadData.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_RESULT_ID, this.repoName);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.repoName = getIntent().getExtras().getString(INTENT_EXTRA_PARAM_RESULT_ID, "");
            addFragment(R.id.fragmentContainer, new ResultDetailsFragment());
        } else {
            this.repoName = savedInstanceState.getString(INSTANCE_STATE_PARAM_RESULT_ID);
        }
    }

    private void initializeInjector() {
        this.resultComponent = DaggerResultComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .resultModule(new ResultModule(this.repoName,""))
                .build();
    }

    @Override public ResultComponent getComponent() {
        return resultComponent;
    }
}