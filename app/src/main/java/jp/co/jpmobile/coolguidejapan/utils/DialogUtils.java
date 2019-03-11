package jp.co.jpmobile.coolguidejapan.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;


/**
 * Created by monkeyismeme on 16/03/28.
 */
public class DialogUtils {

	public  static String TAG_DEFAULT = "tag_default";
	public  static String TAG_FINISH = "tag_finish";
	public static String TAG_LOGOUT = "tag_logout";

	public static void showDialog(Context context,String title,String msg,
	                              String posBtnTitle,String negBtnTitle,String neuBtnTitle,
	                              DialogInterface.OnClickListener positiveListener,
	                              DialogInterface.OnClickListener negtiveListener,
	                              DialogInterface.OnClickListener neutralListener){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// アラートダイアログのタイトルを設定します
		alertDialogBuilder.setTitle(title);
		// アラートダイアログのメッセージを設定します
		alertDialogBuilder.setMessage(msg);
		if ((posBtnTitle != null)&&(positiveListener != null)) {
			// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setPositiveButton(posBtnTitle, positiveListener);
		}
		if ((negBtnTitle != null)&&(negtiveListener != null)) {
			// アラートダイアログの中立ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setNeutralButton(negBtnTitle, negtiveListener);
		}

		if ((neuBtnTitle != null)&&(neutralListener != null)) {
			// アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setNegativeButton(neuBtnTitle, neutralListener);
		}
		// アラートダイアログのキャンセルが可能かどうかを設定します
		alertDialogBuilder.setCancelable(false);
		AlertDialog alertDialog = alertDialogBuilder.create();
		// アラートダイアログを表示します
		alertDialog.show();
	}

	public static  void showNetworkErrorDialog(Context context){
		DialogUtils.showDialog(context, context.getString(R.string.error_title), context.getString(R.string.network_error_msg),
				context.getString(R.string.error_button_positive), null, null, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}, null, null);
	}

	public static void showErrorDialog(String msg, final String tag, final Activity context){
		DialogUtils.showDialog(context, context.getString(R.string.error_title), msg, context.getString(R.string.error_button_positive), null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (tag.equals(TAG_DEFAULT)){
					dialog.dismiss();
				}else if(tag.equals(TAG_LOGOUT)){
					dialog.dismiss();

				}else if(tag.equals(TAG_FINISH)){
					context.finish();
				}
			}
		}, null, null);
	}


}
