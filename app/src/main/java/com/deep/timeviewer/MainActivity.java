package com.deep.timeviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeLineView timeLineView = findViewById(R.id.timeLineView);
        TimeTextView timeTextView = findViewById(R.id.timeTextView);

        /**
         *
         // TimeLineView

         // 设置边框粗细
         timeLineView.setStrokeWidth(1);
         // 设置线条颜色
         timeLineView.setBoColor("");
         // 设置未设置颜色 状态 0
         timeLineView.setBgColor("");
         // 设置已预约颜色 状态 1
         timeLineView.setTiColor("");
         // 设置已过时颜色 状态 2
         timeLineView.setEdColor("");

         // TimeTextView

         // 设置字体颜色
         timeTextView.setFontColor("");
         // 设置字体大小
         timeTextView.setFontSize(50);
         **/

        /**
         *   时间对应参数 - 时间块序号
         *  8:00 -  8:30 -  0
         *  8:30 -  9:00 -  1
         *  9:00 -  9:30 -  2
         *  9:30 - 10:00 -  3
         * 10:00 - 10:30 -  4
         * 10:30 - 11:00 -  5
         * 11:00 - 11:30 -  6
         * 11:30 - 12:00 -  7
         * 12:00 - 12:30 -  8
         * 12:30 - 13:00 -  9
         * 13:00 - 13:30 - 10
         * 13:30 - 14:00 - 11
         * 14:00 - 14:30 - 12
         * 14:30 - 15:00 - 13
         * 15:00 - 15:30 - 14
         * 15:30 - 16:00 - 15
         * 16:00 - 16:30 - 16
         * 16:30 - 17:00 - 17
         * 17:00 - 17:30 - 18
         * 17:30 - 18:00 - 19
         * 18:00 - 18:30 - 20
         * 18:30 - 19:00 - 21
         * 19:00 - 19:30 - 22
         * 19:30 - 20:00 - 23
         * 20:00 - 20:30 - 24
         * 20:30 - 21:00 - 25
         * 21:00 - 21:30 - 26
         * 21:30 - 22:00 - 27
         * 22:00 - 22:30 - 28
         * 22:30 - 23:00 - 29
         */

        timeLineView.setZhiState(0, 2); //已选
        timeLineView.setZhiState(1, 1); //不可选
        timeLineView.setZhiState(2, 2);//已选
        timeLineView.setZhiState(3, 2);
        timeLineView.setZhiState(4, 2);
        timeLineView.setZhiState(5, 1);
        timeLineView.setZhiState(6, 0);
        timeLineView.setZhiState(7, 0);
        timeLineView.setZhiState(8, 0);
        timeLineView.setZhiState(9, 1);
        timeLineView.setZhiState(10, 1);
        timeLineView.setZhiState(11, 1);
        timeLineView.setZhiState(12, 1);
        timeLineView.setZhiState(13, 2);
        timeLineView.setZhiState(14, 1);
        timeLineView.setZhiState(15, 1);
        timeLineView.setZhiState(16, 2);
        timeLineView.setZhiState(17, 1);
        timeLineView.setZhiState(18, 0);
        timeLineView.setZhiState(19, 0);
        timeLineView.setZhiState(20, 0);
        timeLineView.setZhiState(21, 1);
        timeLineView.setZhiState(22, 1);
        timeLineView.setZhiState(23, 2);
        timeLineView.setZhiState(24, 1);
        timeLineView.setZhiState(25, 2);
        timeLineView.setZhiState(26, 1);
        timeLineView.setZhiState(27, 2);
        timeLineView.setZhiState(28, 2);
        timeLineView.setZhiState(29, 0);
    }
}