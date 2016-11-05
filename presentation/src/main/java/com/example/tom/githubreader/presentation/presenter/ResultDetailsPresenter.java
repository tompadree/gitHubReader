package com.example.tom.githubreader.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.domain.Result;
import com.example.domain.exception.DefaultErrorBundle;
import com.example.domain.exception.ErrorBundle;
import com.example.domain.interactor.DefaultSubscriber;
import com.example.domain.interactor.UseCase;
import com.example.tom.githubreader.presentation.exception.ErrorMessageFactory;
import com.example.tom.githubreader.presentation.mapper.ResultModelDataMapper;
import com.example.tom.githubreader.presentation.model.ResultModel;
import com.example.tom.githubreader.presentation.view.ResultDetailsView;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Tom on 1.11.2016..
 */

public class ResultDetailsPresenter implements Presenter {

    private ResultDetailsView viewDetailsView;

    private final UseCase getResultDetailsUseCase;
    private final ResultModelDataMapper resultModelDataMapper;

    @Inject
    public ResultDetailsPresenter(@Named("repoDetails") UseCase getResultDetailsUseCase,
                                  ResultModelDataMapper resultModelDataMapper) {
        this.getResultDetailsUseCase = getResultDetailsUseCase;
        this.resultModelDataMapper = resultModelDataMapper;
    }

    public void setView(@NonNull ResultDetailsView view) {
        this.viewDetailsView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.getResultDetailsUseCase.unsubscribe();
        this.viewDetailsView = null;
    }

    /**
     * Initializes the presenter by start retrieving result details.
     */
    public void initialize() {
        this.loadResultDetails();
    }

    /**
     * Loads result details.
     */
    private void loadResultDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getResultDetails();
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);

    }

    private void showResultDetailsInView(Result result) throws Exception {
        final ResultModel resultModel = this.resultModelDataMapper.transform(result);
        this.viewDetailsView.renderResult(resultModel);
    }

    private void getResultDetails() {
        this.getResultDetailsUseCase.execute(new ResultDetailsSubscriber(),"");
    }

    @RxLogSubscriber
    private final class ResultDetailsSubscriber extends DefaultSubscriber<Result> {

        @Override public void onCompleted() {
            ResultDetailsPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            e.printStackTrace();
            ResultDetailsPresenter.this.hideViewLoading();

            ResultDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ResultDetailsPresenter.this.showViewRetry();
        }

        @Override public void onNext(Result result) {
            try {
                ResultDetailsPresenter.this.showResultDetailsInView(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
