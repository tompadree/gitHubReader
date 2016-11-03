/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.tom.githubreader.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.example.tom.githubreader.presentation.R;
import com.example.tom.githubreader.presentation.di.components.ResultComponent;
import com.example.tom.githubreader.presentation.model.ResultModel;
import com.example.tom.githubreader.presentation.presenter.ResultListPresenter;
import com.example.tom.githubreader.presentation.view.ResultListView;
import com.example.tom.githubreader.presentation.view.adapter.ResultAdapter;
import com.example.tom.githubreader.presentation.view.adapter.ResultLayoutManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Users.
 */
public class ResultListFragment extends BaseFragment implements ResultListView {

  /**
   * Interface for listening user list events.
   */
  public interface ResultListListener {
    void onResultClicked(final ResultModel resultModel);
    void newPageShow(final String page);
  }

//  public interface ScrollListListener {
//    void newPageshow(final String page);
//  }

  @Inject
  ResultListPresenter resultListPresenter;
  @Inject
  ResultAdapter resultAdapter;

  @Bind(R.id.rv_users) RecyclerView rv_users;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  private ResultListListener resultListListener;
  private int page=0;

  public ResultListFragment() {
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ResultListListener) {
      this.resultListListener = (ResultListListener) activity;
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(ResultComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
    ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.resultListPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadResultList();
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.resultListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.resultListPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    rv_users.setAdapter(null);
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.resultListPresenter.destroy();
  }

  @Override public void onDetach() {
    super.onDetach();
    this.resultListListener = null;
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

  @Override public void renderResultList(Collection<ResultModel> resultModelCollection) {
    if (resultModelCollection != null) {
      this.resultAdapter.setResultCollection(resultModelCollection);
    }
  }

  @Override public void viewRepo(ResultModel resultModel) {
    if (this.resultListListener != null) {
      this.resultListListener.onResultClicked(resultModel);
      this.resultListListener.newPageShow(String.valueOf(this.page));
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return this.getActivity().getApplicationContext();
  }


  private void setupRecyclerView() {

    this.rv_users.addOnScrollListener(sc);
    this.resultAdapter.setOnItemClickListener(onItemClickListener);
    this.rv_users.setLayoutManager(new ResultLayoutManager(context()));
    this.rv_users.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context()).build());
    this.rv_users.setAdapter(resultAdapter);//

  }

  /**
   * Loads all result.
   */
  private void loadResultList() {
    this.resultListPresenter.initialize();
  }

  /**
   * Loads all result.
   */
  private void loadMoreResultList() {
    this.resultListPresenter.initialize();
  }

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    ResultListFragment.this.loadResultList();
  }

  private ResultAdapter.OnItemClickListener onItemClickListener =
      new ResultAdapter.OnItemClickListener() {
        @Override public void onUserItemClicked(ResultModel resultModel) {
          if (ResultListFragment.this.resultListPresenter != null && resultModel != null) {
            ResultListFragment.this.resultListPresenter.onResultClicked(resultModel);
          }
        }
      };

RecyclerView.OnScrollListener sc = new RecyclerView.OnScrollListener() {
  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    if(rv_users.computeVerticalScrollOffset()/200>20+page && ResultListFragment.this.resultListPresenter != null){
      ResultListFragment.this.resultListPresenter.newPageShow(String.valueOf((page+20)/10));
      page = rv_users.computeVerticalScrollOffset()/200;
    }


    Log.e("ONSCROLL",String.valueOf(rv_users.computeVerticalScrollOffset()/200));
  }
};


}
