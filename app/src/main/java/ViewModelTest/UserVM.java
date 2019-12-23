package ViewModelTest;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.example.liblog.SLog;

// 页面进来调用所有LiveData的observe方法，可以确保保存数据。
// postValue与setValue时候怎么确定调用具体哪个Observer，只能通过livedata命名。
public class UserVM extends ViewModel {
    private static final String TAG = "UserVM";
    private MutableLiveData<User> userData_1 = new MutableLiveData<>();
    private MutableLiveData<User> userData_2 = new MutableLiveData<>();
    private UserData userData_3 = new UserData();

    public void userData_1() {
        // do net work to get data
        User user = new User("userData_1", 21);
        userData_1.postValue(user);
    }

    public void userData_1(LifecycleOwner owner, Observer<User> observer) {
        userData_1.observe(owner, observer);
    }

    public void userData_2() {
        // do net work to get data
        User user = new User("userData_2", 22);
        userData_2.postValue(user);
    }

    public void userData_2(LifecycleOwner owner, Observer<User> observer) {
        userData_2.observe(owner, observer);
    }

    public void userData_3() {
        // do net work to get data
        User user = new User("userData_3", 23);
        userData_3.postValue(user);
    }

    public void userData_3(LifecycleOwner owner, Observer<User> observer) {
        userData_3.observe(owner, observer);
    }


    public static class UserData extends MutableLiveData<User> {
        @Override
        protected void onActive() {
            SLog.d(TAG, "onActive");
            super.onActive();
        }

        @Override
        protected void onInactive() {
            SLog.d(TAG, "onInactive");
            super.onInactive();
        }
    }
}
