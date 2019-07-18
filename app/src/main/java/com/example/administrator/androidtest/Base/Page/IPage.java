package com.example.administrator.androidtest.Base.Page;


public interface IPage {

    int PageNone = -2;
    int Background = -1;
    int VpFragVisibilityAct = 1;
    int FragmentOneVp = VpFragVisibilityAct + 1;
    int FragmentOne = FragmentOneVp + 1;
    int FragmentTwo = FragmentOne + 1;
    int FragmentThree = FragmentTwo + 1;
    int FragmentFour = FragmentThree + 1;
    int FragmentFive = FragmentFour + 1;
    int FragmentSix = FragmentFive + 1;
    int FragmentSeven = FragmentSix + 1;
    int FragmentThreeVp = FragmentSeven + 1;
    int FragVisibilityAct = FragmentThreeVp + 1;
    int pageId();
}
