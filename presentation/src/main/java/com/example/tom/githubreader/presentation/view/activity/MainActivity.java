package com.example.tom.githubreader.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tom.githubreader.presentation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

//  @Bind(R.id.btn_LoadData) Button btn_LoadData;
//  @Bind(R.id.editTextSearchRepo) EditText editText;

  private String searchRepo = "",page="0";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

  }

  /**
   * Goes to the user list screen.
   */
//  @OnClick(R.id.btn_LoadData)
//  void navigateToUserList() {
////    searchRepo = String.valueOf(this.editText.getText());
////    this.navigator.navigateToResultList(this,searchRepo);
//  }
}
