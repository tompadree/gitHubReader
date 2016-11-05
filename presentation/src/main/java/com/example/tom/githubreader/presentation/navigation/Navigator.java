
package com.example.tom.githubreader.presentation.navigation;

import android.content.Context;
import android.content.Intent;


import com.example.tom.githubreader.presentation.view.activity.ResultDetailsActivity;
import com.example.tom.githubreader.presentation.view.activity.ResultListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the user list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
//  public void navigateToResultList(Context context, String searchRepo) {
//    if (context != null) {
//      Intent intentToLaunch = ResultListActivity.getCallingIntent(context, searchRepo);
//      context.startActivity(intentToLaunch);
//    }
//  }

  /**
   * Goes to the user details screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserDetails(Context context, String repoName) {
    if (context != null) {
      Intent intentToLaunch = ResultDetailsActivity.getCallingIntent(context, repoName);
      context.startActivity(intentToLaunch);
    }
  }
}
