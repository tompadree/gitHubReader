/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tom.githubreader.presentation.presenter;

import android.support.annotation.NonNull;


import com.example.domain.Result;
import com.example.domain.exception.DefaultErrorBundle;
import com.example.domain.exception.ErrorBundle;
import com.example.domain.interactor.DefaultSubscriber;
import com.example.domain.interactor.UseCase;
import com.example.tom.githubreader.presentation.di.PerActivity;
import com.example.tom.githubreader.presentation.exception.ErrorMessageFactory;
import com.example.tom.githubreader.presentation.mapper.ResultModelDataMapper;
import com.example.tom.githubreader.presentation.model.ResultModel;
import com.example.tom.githubreader.presentation.view.ResultListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ResultListPresenter implements Presenter {

  private ResultListView viewListView;
  private String page;
  private final UseCase getResultListUseCase;
  private final ResultModelDataMapper resultModelDataMapper;

  @Inject
  public ResultListPresenter(@Named("resultList") UseCase getResultListUseCase,ResultModelDataMapper resultModelDataMapper) {
    this.getResultListUseCase = getResultListUseCase;
    this.resultModelDataMapper = resultModelDataMapper;
  }

  public void setView(@NonNull ResultListView view) {
    this.viewListView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getResultListUseCase.unsubscribe();
    this.viewListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the result list.
   */
  public void initialize() {
    this.loadResultList();
  }

  public void newPageShow(String page) {this.addResultList(page);}

  /**
   * Loads all result
   */
  private void loadResultList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getResultList();
  }

  private void addResultList(String page) {
    //this.hideViewRetry();
    //this.showViewLoading();
    this.page=page;
    this.getResultList();
  }

  public void onResultClicked(ResultModel resultModel) {
    this.viewListView.viewRepo(resultModel);
  }



  private void showViewLoading() {
    this.viewListView.showLoading();
  }

  private void hideViewLoading() {
    this.viewListView.hideLoading();
  }

  private void showViewRetry() {
    this.viewListView.showRetry();
  }

  private void hideViewRetry() {
    this.viewListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
        errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showResultCollectionInView(Collection<Result> resultCollection) {
    final Collection<ResultModel> resultModelsCollection = this.resultModelDataMapper.transform(resultCollection);
    this.viewListView.renderResultList(resultModelsCollection);
  }

  private void getResultList() {
    this.getResultListUseCase.execute(new ResultListSubscriber());
  }

  private final class ResultListSubscriber extends DefaultSubscriber<List<Result>> {

    @Override public void onCompleted() {
      ResultListPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      ResultListPresenter.this.hideViewLoading();
      ResultListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      ResultListPresenter.this.showViewRetry();
    }

    @Override public void onNext(List<Result> result) {
      ResultListPresenter.this.showResultCollectionInView(result);
    }
  }
}
