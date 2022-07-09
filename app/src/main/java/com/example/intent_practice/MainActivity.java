package com.example.intent_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;

import com.example.intent_practice.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void implicitIntentEvent(View view) {

        Intent intent = null;

        switch (view.getId()) {
            case R.id.web: /* WebのUrlを読み込む */
                intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://developer.android.com/guide")
                );
                break;

            case R.id.map: /* 地図を開く */
                // geo:latitude,longitude
                Uri geoLocation = Uri.parse("geo:35.6585805,139.7432389");
                // クエリ文字列で検索する場合
                // Uri geoLocation = Uri.parse("geo:0,0?q=東京タワー");
                intent = new Intent(
                        Intent.ACTION_VIEW,
                        geoLocation
                );
                break;

            case R.id.dial: /* 電話をかける */
                intent = new Intent(Intent.ACTION_DIAL)
                        .setData(Uri.parse("tel:0123456789"));
                break;

            case R.id.mail: /* メールを作成する */
                intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"宛先1"});
                intent.putExtra(Intent.EXTRA_CC, new String[]{"宛先2"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "件名をいれます");
                intent.putExtra(Intent.EXTRA_TEXT, "本文をいれます");

                break;

            case R.id.camera: /* カメラを起動する */
                // カメラモードで起動
                String camera = MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA;
                // 動画モードで起動
                // String movie = MediaStore.INTENT_ACTION_VIDEO_CAMERA;
                intent = new Intent(camera);

                break;

            case R.id.alarm: /* アラームを作成する */
                // ACTION_SET_ALARM インテントを呼び出すには
                // AndroidManifest.xmlにSET_ALARMパーミッションが必要です。
                // <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
                intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                // アラームを識別するメッセージ
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, "set alarm");
                // アラームの時間
                intent.putExtra(AlarmClock.EXTRA_HOUR, 10);
                // アラームの分
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
                // UIをスキップするか
                // true = アプリの画面に遷移しない（ステータスバーに通知される）
                // false = アプリに遷移する
                intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);

                break;

            case R.id.timer: /* タイマーを作成する */
                intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, "set timer")
                        .putExtra(AlarmClock.EXTRA_LENGTH, 5)
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, false);

                break;

            case R.id.calendar: /* カレンダー イベントを追加する */
                intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, "イベントのタイトルを入れます");
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "イベントの説明を入れます");
                // 終日のイベントかどうかを示すブール値
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                // イベントの開始時間
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 0);
                // イベントの終了時間
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 0);
                // 参加者のメールアドレス
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hoge@mail.com", "foo@gmail.com"});

                break;

            case R.id.contact: /* 連絡先を選択する*/
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                break;

            case R.id.file: /* ファイルを選択する */
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                break;
        }

        // 端末に暗黙的インテントを受け取ることができるアプリが存在するか確認する
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}