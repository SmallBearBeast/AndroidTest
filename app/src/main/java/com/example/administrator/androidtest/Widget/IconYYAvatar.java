package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ResourceUtil;

public class IconYYAvatar extends FrameLayout {
    public static final int NONE = -1;
    public static final int CIRCLE_LEFT_TOP = 1; //左上角圆弧位置
    public static final int CIRCLE_LEFT_BOTTOM = CIRCLE_LEFT_TOP + 1; //左下角圆弧位置
    public static final int CIRCLE_RIGHT_TOP = CIRCLE_LEFT_BOTTOM + 1; //右上角圆弧位置
    public static final int CIRCLE_RIGHT_BOTTOM = CIRCLE_RIGHT_TOP + 1; //右下角圆弧位置
    public static final int CIRCLE_LEFT = CIRCLE_RIGHT_BOTTOM + 1; //左边位置
    public static final int CIRCLE_TOP = CIRCLE_LEFT + 1; //上边位置
    public static final int CIRCLE_RIGHT = CIRCLE_TOP + 1; //右边位置
    public static final int CIRCLE_BOTTOM = CIRCLE_RIGHT + 1; //底边位置
    public static final int CIRCLE_CENTER = CIRCLE_BOTTOM + 1; //中心位置
    public static final int SQUARE_LEFT_TOP = CIRCLE_CENTER + 1; //方形左上角顶点位置
    public static final int SQUARE_LEFT_BOTTOM = SQUARE_LEFT_TOP + 1; //方形左下角顶点位置
    public static final int SQUARE_RIGHT_TOP = SQUARE_LEFT_BOTTOM + 1; //方形右上角顶点位置
    public static final int SQUARE_RIGHT_BOTTOM = SQUARE_RIGHT_TOP + 1; //方形右下角顶点位置

    public static final int CENTER = 1;
    public static final int CENTER_HORIZONTAL = CENTER + 1;
    public static final int CENTER_VERTICAL = CENTER_HORIZONTAL + 1;

    /**YYAvatar相关属性**/
    private String mImageUrl;
    private int mDefaultImageId;
    private int mErrorImageId;
    private int mResImageId;
    private boolean mIsCircle;
    private int mBorderColor;
    private float mBorderWidth;
    /**YYAvatar相关属性**/

    private int mIconPosType = CIRCLE_RIGHT_BOTTOM;
    private int mIconPosX = 0; //icon中心在头像的位置
    private int mIconPosY = 0; //icon中心在头像的位置
    private int mViewHeight;//xml设置的view高度
    private int mViewWidth; //xml设置的view宽度
    private float mAvatarSize; //头像的宽度 方形 大小为IconYYAvatar初始传入的宽度 必须有值
    private float mIconSize; // icon的大小 必须有值
    private int mIconImageId;// icon 资源id
    private boolean mIsAddIcon; //是否添加icon
    private int mGravity; //内部对其  Gravity.CENTER_HORIZONTAL Gravity.CENTER_VERTICAL Gravity.CENTER
    private boolean mIsGetSize = false;

    private RectF avatarRectF;
    private RectF iconRectF;
    private RectF mTotalRectF;
    private ImageView mIcon;
    private ImageView mYYAvatar;

    public IconYYAvatar(@NonNull Context context) {
        this(context, null);
    }

    public IconYYAvatar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconYYAvatar);
        mDefaultImageId = array.getResourceId(R.styleable.IconYYAvatar_placeHolderImage, NONE);
        mErrorImageId = array.getResourceId(R.styleable.IconYYAvatar_failureImage, NONE);
        mResImageId = array.getResourceId(R.styleable.IconYYAvatar_resImage, NONE);
        mIconImageId = array.getResourceId(R.styleable.IconYYAvatar_iconImage, NONE);
        mIsCircle = array.getBoolean(R.styleable.IconYYAvatar_roundAsCircle, true);
        mBorderWidth = array.getDimension(R.styleable.IconYYAvatar_roundingBorderWidth, 0);
        mBorderColor = array.getColor(R.styleable.IconYYAvatar_roundingBorderColor, Color.TRANSPARENT);
        mIconSize = array.getDimension(R.styleable.IconYYAvatar_iconSize, NONE);
        mAvatarSize = array.getDimension(R.styleable.IconYYAvatar_avatarSize, NONE);
        mIsAddIcon = array.getBoolean(R.styleable.IconYYAvatar_isAddIcon, false);
        mIconPosType = array.getInteger(R.styleable.IconYYAvatar_iconPosType, CIRCLE_CENTER);
        mGravity = array.getInteger(R.styleable.IconYYAvatar_gravity, CENTER_VERTICAL);
        array.recycle();

        if(mAvatarSize == NONE)
            throw new RuntimeException("Must set avatar size");
        if(mIconSize == NONE)
            throw new RuntimeException("Must set icon size");
        setIconPosType(mIconPosType);
        addYYAvatar();
        addIcon();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth == 0 && w > 0 && mViewHeight == 0 && h > 0){
            mViewWidth = w;
            mViewHeight = h;
            mIsGetSize = true;
            if(mIsAddIcon) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        adjustSize();
                    }
                });
            }
        }
    }

    /**
     * 调整imageview yyAvatar位置 调整framelayout大小
     */
    private void adjustSize() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = (int) Math.max(mViewWidth, mTotalRectF.width());
        lp.height = (int) Math.max(mViewHeight, mTotalRectF.height());
        setLayoutParams(lp);

        float dx = (lp.width - mTotalRectF.width()) / 2;
        float dy = (lp.height - mTotalRectF.height()) / 2;
        if(mGravity == CENTER_HORIZONTAL){
            move(iconRectF, dx, 0);
            move(avatarRectF, dx, 0);
        }else if(mGravity == CENTER_VERTICAL){
            move(iconRectF, 0, dy);
            move(avatarRectF, 0, dy);
        }else if(mGravity == CENTER){
            move(iconRectF, dx, dy);
            move(avatarRectF, dx, dy);
        }

        LayoutParams iconLp = new LayoutParams((int) mIconSize, (int) mIconSize);
        iconLp.topMargin = (int) iconRectF.top;
        iconLp.leftMargin = (int) iconRectF.left;
        mIcon.setLayoutParams(iconLp);

        LayoutParams avatarLp = new LayoutParams((int)mAvatarSize, (int)mAvatarSize);
        avatarLp.topMargin = (int) avatarRectF.top;
        avatarLp.leftMargin = (int) avatarRectF.left;
        mYYAvatar.setLayoutParams(avatarLp);
    }

    /**
     * 添加imageView到FrameLayout
     */
    private void addIcon() {
        if(mIcon == null && mIsAddIcon){
            mIcon = new ImageView(getContext());
            LayoutParams lp = new LayoutParams((int) mIconSize, (int) mIconSize);
            addView(mIcon, lp);
        }
        setImageId(mIconImageId);
    }

    public void setIsAddIcon(boolean isAddIcon){
        if(mIsAddIcon != isAddIcon) {
            mIsAddIcon = isAddIcon;
            if (mIsAddIcon) {
                addIcon();
                if (mIsGetSize) {
                    adjustSize();
                }
            } else {
                removeView(mIcon);
                mIcon = null;
            }
        }
    }

    /**
     * 添加YYAvatar到FrameLayout
     */
    private void addYYAvatar() {
        if(mYYAvatar == null){
            mYYAvatar = new ImageView(getContext());
            LayoutParams lp = new LayoutParams((int)mAvatarSize, (int)mAvatarSize);
            lp.gravity = toGravity();
            addView(mYYAvatar, lp);
            setImageUrl(mImageUrl);
            setImageResource(mResImageId);
        }
    }

    /**
     * 确定icon和yyAvatar位置大小信息
     */
    private void adjustRect() {
        if(iconRectF == null){
            iconRectF = new RectF(-mIconSize / 2, -mIconSize / 2, mIconSize / 2, mIconSize / 2);
        }
        if(avatarRectF == null){
            avatarRectF = new RectF(0, 0, mAvatarSize, mAvatarSize);
        }
        if(mTotalRectF == null){
            mTotalRectF = new RectF();
        }

        move(iconRectF, mIconPosX, mIconPosY);
        mTotalRectF.left = Math.min(iconRectF.left, avatarRectF.left);
        mTotalRectF.right = Math.max(iconRectF.right, avatarRectF.right);
        mTotalRectF.top = Math.min(iconRectF.top, avatarRectF.top);
        mTotalRectF.bottom = Math.max(iconRectF.bottom, avatarRectF.bottom);

        //原点变换成左上角
        float dx = -mTotalRectF.left;
        float dy = -mTotalRectF.top;
        move(iconRectF, dx, dy);
        move(avatarRectF, dx, dy);
        move(mTotalRectF, dx, dy);
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
        if(mYYAvatar != null && mImageUrl != null){
            // TODO: 2019/2/1 设置url
        }
    }

    /**
     * 根据iconPosType设置mIconPosX，mIconPosY值
     * 设置这个会覆盖setIconPos() 需要在设置头像方法上面调用
     */
    public void setIconPosType(int iconPosType){
        if(iconPosType == NONE)
            return;
        mIconPosType = iconPosType;
        int radius = (int) (mAvatarSize / 2);
        switch (iconPosType){
            case CIRCLE_LEFT_TOP:
                mIconPosX = (int) (radius - 0.707 * radius);
                mIconPosY = (int) (radius - 0.707 * radius);
                break;
            case CIRCLE_LEFT_BOTTOM:
                mIconPosX = (int) (radius - 0.707 * radius);
                mIconPosY = (int) (radius + 0.707 * radius);
                break;
            case CIRCLE_RIGHT_TOP:
                mIconPosX = (int) (radius + 0.707 * radius);
                mIconPosY = (int) (radius - 0.707 * radius);
                break;
            case CIRCLE_RIGHT_BOTTOM:
                mIconPosX = (int) (radius + 0.707 * radius);
                mIconPosY = (int) (radius + 0.707 * radius);
                break;

            case CIRCLE_LEFT:
                mIconPosX = 0;
                mIconPosY = radius;
                break;

            case CIRCLE_TOP:
                mIconPosX = radius;
                mIconPosY = 0;
                break;

            case CIRCLE_RIGHT:
                mIconPosX = radius * 2;
                mIconPosY = radius;
                break;

            case CIRCLE_BOTTOM:
                mIconPosX = radius;
                mIconPosY = radius * 2;
                break;

            case CIRCLE_CENTER:
                mIconPosX = radius;
                mIconPosY = radius;
                break;

            case SQUARE_LEFT_TOP:
                mIconPosX = 0;
                mIconPosY = 0;
                break;

            case SQUARE_LEFT_BOTTOM:
                mIconPosX = 0;
                mIconPosY = radius * 2;
                break;

            case SQUARE_RIGHT_TOP:
                mIconPosX = radius * 2;
                mIconPosY = 0;
                break;

            case SQUARE_RIGHT_BOTTOM:
                mIconPosX = radius * 2;
                mIconPosY = radius * 2;
                break;
            // TODO: 2019/1/30 补充其他特殊点
        }
        adjustRect();
    }

    private void setImageId(int imageId){
        mIconImageId = imageId;
        if(mIconImageId == NONE){
            setBitmap(null);
        }else {
            setBitmap(BitmapFactory.decodeResource(ResourceUtil.getResources(), mIconImageId));
        }
    }

    private void setBitmap(Bitmap bitmap){
        if(mIcon != null){
            mIcon.setImageBitmap(bitmap);
        }
    }

    public void setIconSize(int iconSize){
        mIconSize = iconSize;
    }

    private void move(RectF rectF, float dx, float dy){
        rectF.left = rectF.left + dx;
        rectF.right = rectF.right + dx;
        rectF.top = rectF.top + dy;
        rectF.bottom = rectF.bottom + dy;
    }

    private void setIconPos(int iconPosX, int iconPosY){
        mIconPosX = iconPosX;
        mIconPosY = iconPosY;
    }

    public void setImageResource(int resId) {
        mResImageId = resId;
        if(mYYAvatar != null && mResImageId != NONE){
            mYYAvatar.setImageResource(resId);
        }
    }

    private int toGravity(){
        switch (mGravity){
            default:
            case CENTER:
                return Gravity.CENTER;
            case CENTER_HORIZONTAL:
                return Gravity.CENTER_HORIZONTAL;
            case CENTER_VERTICAL:
                return Gravity.CENTER_VERTICAL;
        }
    }
}
