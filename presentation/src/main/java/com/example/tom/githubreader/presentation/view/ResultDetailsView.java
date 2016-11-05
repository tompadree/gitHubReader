package com.example.tom.githubreader.presentation.view;

/**
 * Created by Tom on 1.11.2016..
 */

import com.example.tom.githubreader.presentation.model.ResultModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a user profile.
 */
public interface ResultDetailsView extends LoadDataView {
    /**
     * Render a result in the UI.
     *
     * @param result The {@link ResultModel} that will be shown.
     */
    void renderResult(ResultModel result) throws Exception;
}
