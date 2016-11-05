
package com.example.tom.githubreader.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tom.githubreader.presentation.R;
import com.example.tom.githubreader.presentation.di.components.ResultComponent;
import com.example.tom.githubreader.presentation.model.ResultModel;
import com.example.tom.githubreader.presentation.presenter.ResultDetailsPresenter;
import com.example.tom.githubreader.presentation.view.ResultDetailsView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows details of a certain user.
 */
public class ResultDetailsFragment extends BaseFragment implements ResultDetailsView {

  @Inject
  ResultDetailsPresenter repoDetailsPresenter;

  @Bind(R.id.fragmentLayout) LinearLayout fragmentLayout;
  @Bind(R.id.tv_fullname) TextView tv_fullname;
  @Bind(R.id.tv_language) TextView tv_language;
  @Bind(R.id.tv_created) TextView tv_created;
  @Bind(R.id.tv_modified) TextView tv_modified;
  @Bind(R.id.tv_forks) TextView tv_forks;
  @Bind(R.id.tv_issues) TextView tv_issues;
  @Bind(R.id.tv_watchers) TextView tv_watchers;
  @Bind(R.id.tv_subscriptions) TextView tv_subscriptions;
  @Bind(R.id.tv_typeUser) TextView tv_typeUser;
  @Bind(R.id.tv_siteAdmin) TextView tv_siteAdmin;
  @Bind(R.id.tv_pull) TextView tv_pull;
  @Bind(R.id.tv_pulse) TextView tv_pulse;
  @Bind(R.id.language)LinearLayout _language;
  @Bind(R.id.created) LinearLayout _created;
  @Bind(R.id.modified) LinearLayout _modified;
  @Bind(R.id.forks) LinearLayout _forks;
  @Bind(R.id.issues) LinearLayout _issues;
  @Bind(R.id.watchers) LinearLayout _watchers;
  @Bind(R.id.subscriptions) LinearLayout _subscriptions;
  @Bind(R.id.typeUser) LinearLayout _typeUser;
  @Bind(R.id.siteAdmin) LinearLayout _siteAdmin;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  public ResultDetailsFragment() {
    setRetainInstance(true);
  }
  private String repoName;
  private SimpleDateFormat fmt, outputTime;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(ResultComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_repo_details, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.repoDetailsPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadRepoDetails();
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.repoDetailsPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.repoDetailsPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.repoDetailsPresenter.destroy();
  }

  @Override public void renderResult(ResultModel result) {

      if (result != null) {

        fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        outputTime = new SimpleDateFormat("H:mm  d.M.yyyy");

        this.fragmentLayout.setVisibility(View.VISIBLE);
        this.tv_fullname.setText(result.getRepoName());
        this.tv_language.setText(String.valueOf(result.getLanguage()));
        this._language.setOnClickListener(clicInfokListener);
        try {
          this.tv_created.setText(outputTime.format(fmt.parse(result.getCreatedAt())));
          this._created.setOnClickListener(clicInfokListener);
          this.tv_modified.setText(outputTime.format(fmt.parse(result.getModified())));
          this._modified.setOnClickListener(clicInfokListener);
        } catch (ParseException e) {
          e.printStackTrace();
        }

        this.tv_watchers.setText(String.valueOf(result.getWatchers()));
        this._watchers.setOnClickListener(clicInfokListener);
        this.tv_forks.setText(String.valueOf(result.getForks()));
        this._forks.setOnClickListener(clicInfokListener);
        this.tv_issues.setText(String.valueOf(result.getIssues()));
        this._issues.setOnClickListener(clicInfokListener);
        this.tv_subscriptions.setText(String.valueOf(result.getSubscribers()));
        this._subscriptions.setOnClickListener(clicInfokListener);
        this.tv_typeUser.setText(String.valueOf(result.getUserType()));
        this._typeUser.setOnClickListener(clicInfokListener);
        this.tv_siteAdmin.setText(String.valueOf(result.getSiteAdmin()));
        this._siteAdmin.setOnClickListener(clicInfokListener);
        this.tv_pull.setOnClickListener(clicInfokListener);
        this.tv_pulse.setOnClickListener(clicInfokListener);

        repoName = result.getRepoName();
      }


  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return getActivity().getApplicationContext();
  }

  /**
   * Loads all repositories.
   */
  private void loadRepoDetails() {
    if (this.repoDetailsPresenter != null) {
      this.repoDetailsPresenter.initialize();
    }
  }

  View.OnClickListener clicInfokListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      String url = "https://github.com/" + repoName;
      Intent browserIntent = new Intent(Intent.ACTION_VIEW);


      switch(v.getId()){
        case R.id.language:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.watchers:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.issues:
          browserIntent.setData(Uri.parse(url +"/issues"));
          startActivity(browserIntent);
          break;
        case R.id.forks:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.modified:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.subscriptions:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.created:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.typeUser:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.siteAdmin:
          browserIntent.setData(Uri.parse(url));
          startActivity(browserIntent);
          break;
        case R.id.tv_pull:
          browserIntent.setData(Uri.parse(url+"/pulls"));
          startActivity(browserIntent);
          break;
        case R.id.tv_pulse:
          browserIntent.setData(Uri.parse(url+"/pulse"));
          startActivity(browserIntent);
          break;
        default:
          break;
      }


    }
  };

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    ResultDetailsFragment.this.loadRepoDetails();
  }
}
