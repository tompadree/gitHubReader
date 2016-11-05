package com.example.tom.githubreader.presentation.view;

import com.example.tom.githubreader.presentation.model.ResultModel;

import java.util.Collection;

/**
 * Created by Tom on 1.11.2016..
 */

public interface ResultListView extends LoadDataView{

    /**
     * Render a user list in the UI.
     *
     * @param resultModelCollection The collection of {@link ResultModel} that will be shown.
     */
    void renderResultList(Collection<ResultModel> resultModelCollection);

    /**
     * View a {@link ResultModel} profile/details.
     *
     * @param resultModel The result that will be shown.
     */
    void viewRepo(ResultModel resultModel);
}
