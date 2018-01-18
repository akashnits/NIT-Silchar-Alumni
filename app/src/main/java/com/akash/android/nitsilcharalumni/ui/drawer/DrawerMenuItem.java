package com.akash.android.nitsilcharalumni.ui.drawer;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;


@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_RATE_US = 2;
    public static final int DRAWER_MENU_ITEM_CONTACT_US = 3;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 4;
    public static final int DRAWER_MENU_ITEM_DEVELOPED_BY= 5;


    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;
    private OnClickMenuItemHandler mHandler;


    @View(R.id.tvItemName)
    private TextView itemNameTxt;

    @View(R.id.ivItemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition, DrawerCallBack drawerCallBack,
                          OnClickMenuItemHandler onClickMenuItemHandler) {
        mContext = context;
        mMenuPosition = menuPosition;
        mCallBack= drawerCallBack;
        mHandler= onClickMenuItemHandler;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("Profile");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
                break;
            case DRAWER_MENU_ITEM_RATE_US:
                itemNameTxt.setText("Rate us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_rate_review_black_24dp));
                break;
            case DRAWER_MENU_ITEM_CONTACT_US:
                itemNameTxt.setText("Contact us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_contact_mail_black_24dp));
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp));
                itemNameTxt.setText("Logout");
                break;
            case DRAWER_MENU_ITEM_DEVELOPED_BY:
                itemNameTxt.setText("Developed by Akash");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_PROFILE:
                //Toast.makeText(mContext, "Profile", Toast.LENGTH_SHORT).show();
                mHandler.onClickMenuItemListener();
                (new Handler()).postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                mCallBack.onProfileMenuSelected();
                            }
                        }, 500);
                break;
            case DRAWER_MENU_ITEM_RATE_US:
                Toast.makeText(mContext, "Rate us", Toast.LENGTH_SHORT).show();
                mHandler.onClickMenuItemListener();
                mCallBack.onRateUsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_CONTACT_US:
                Toast.makeText(mContext, "Contact us", Toast.LENGTH_SHORT).show();
                mHandler.onClickMenuItemListener();
                mCallBack.onContactUsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                mHandler.onClickMenuItemListener();
                mCallBack.onLogoutMenuSelected();
                break;
            case DRAWER_MENU_ITEM_DEVELOPED_BY:
                Toast.makeText(mContext, "Developer", Toast.LENGTH_SHORT).show();
                mHandler.onClickMenuItemListener();
                mCallBack.onDeveloperMenuSelected();
                break;
        }
    }

    public interface DrawerCallBack{
        void onProfileMenuSelected();
        void onRateUsMenuSelected();
        void onContactUsMenuSelected();
        void onLogoutMenuSelected();
        void onDeveloperMenuSelected();
    }

    public interface OnClickMenuItemHandler{
        void onClickMenuItemListener();
    }
}
