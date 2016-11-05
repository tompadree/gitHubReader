
package com.example.tom.githubreader.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;


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
  private String page="1";
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
    this.showViewLoading();
    this.page=page;
    this.getResultList();
  }

  public void onResultClicked(ResultModel resultModel) {
    this.viewListView.viewRepo(resultModel);
  }
  public void newPageShow(String page) {
    this.addResultList(page);}


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
    this.getResultListUseCase.execute(new ResultListSubscriber(),this.page);
  }

  private final class ResultListSubscriber extends DefaultSubscriber<List<Result>> {

    @Override public void onCompleted() {

      ResultListPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      e.printStackTrace();
      ResultListPresenter.this.hideViewLoading();
      ResultListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      ResultListPresenter.this.showViewRetry();
    }

    @Override public void onNext(List<Result> result) {
      if(result.isEmpty() || result==null)
        Log.e("EMPTY","EMPTY");
      ResultListPresenter.this.showResultCollectionInView(result);
    }
  }
}
