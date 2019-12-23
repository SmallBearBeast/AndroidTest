package ViewModelTest;

import android.arch.core.util.Function;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.libbase.Util.ToastUtil;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.liblog.SLog;

public class ViewModelTestAct extends ComponentAct {
    private UserVM mUserVM;
    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mUserVM = ViewModelProviders.of(this).get(UserVM.class);

        mUserVM.userData_1(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                SLog.d(TAG, "userData_1: user = " + user);
            }
        });
        mUserVM.userData_1();

        mUserVM.userData_2();
        mUserVM.userData_2(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                SLog.d(TAG, "userData_2: user = " + user);
            }
        });

        mUserVM.userData_3();
        mUserVM.userData_3(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                SLog.d(TAG, "userData_3: user = " + user);
            }
        });
    }
}