package com.example.administrator.androidtest.Stat;

public interface IStat {

    /**埋点位置（不够可以补充）**/
    byte STAT_1 = 1;
    byte STAT_2 = 2;
    byte STAT_3 = 3;
    byte STAT_4 = 4;
    byte STAT_5 = 5;
    byte STAT_6 = 6;
    /**埋点位置（不够可以补充）**/

    /**
     * 通过statCode判断埋点位置上报埋点，params携带参数自己转（别传null）
     */
    void reportStat(byte statCode, Object... params);
}
