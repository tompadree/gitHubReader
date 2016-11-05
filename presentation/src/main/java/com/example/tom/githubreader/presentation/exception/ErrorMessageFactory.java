
package com.example.tom.githubreader.presentation.exception;

import android.content.Context;
import android.util.Log;

import com.example.data.exception.ApiLimitException;
import com.example.data.exception.NetworkConnectionException;
import com.example.data.exception.ResultNotFoundException;
import com.example.data.exception.StringNotFoundException;
import com.example.tom.githubreader.presentation.R;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    //empty
  }

  /**
   * Creates a String representing an error message.
   *
   * @param context Context needed to retrieve string resources.
   * @param exception An exception used as a condition to retrieve the correct error message.
   * @return {@link String} an error message.
   */
  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.exception_message_generic);


    if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.exception_message_no_connection);
    } else if (exception instanceof ResultNotFoundException) {
      message = context.getString(R.string.exception_message_result_not_found);
    } else if (exception instanceof StringNotFoundException) {
      message = context.getString(R.string.exception_message_no_string);
    } else if (exception instanceof ApiLimitException){
      message = context.getString(R.string.exception_message_api_limit);
    }

    return message;
  }
}
