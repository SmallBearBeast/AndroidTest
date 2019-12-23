package ViewModelTest;

import android.support.annotation.NonNull;

public class User {
    public String mName;
    public int mAge;

    public User(String name, int age) {
        mName = name;
        mAge = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", mAge=" + mAge +
                '}';
    }
}
