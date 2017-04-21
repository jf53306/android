# 6.0后运行时权限，运行时权限管理

Android 6.0之后，运行时权限不仅仅是在要在配置文件中声明，还要经过用户同意，才能授予App访问权限。

## PROTECTION_NORMAL

此类权限只需要在配置文件中声明即可
```java
android.permission.ACCESS_LOCATION_EXTRA_COMMANDS
android.permission.ACCESS_NETWORK_STATE
android.permission.ACCESS_NOTIFICATION_POLICY
android.permission.ACCESS_WIFI_STATE
android.permission.ACCESS_WIMAX_STATE
android.permission.BLUETOOTH
android.permission.BLUETOOTH_ADMIN
android.permission.BROADCAST_STICKY
android.permission.CHANGE_NETWORK_STATE
android.permission.CHANGE_WIFI_MULTICAST_STATE
android.permission.CHANGE_WIFI_STATE
android.permission.CHANGE_WIMAX_STATE
android.permission.DISABLE_KEYGUARD
android.permission.EXPAND_STATUS_BAR
android.permission.FLASHLIGHT
android.permission.GET_ACCOUNTS
android.permission.GET_PACKAGE_SIZE
android.permission.INTERNET
android.permission.KILL_BACKGROUND_PROCESSES
android.permission.MODIFY_AUDIO_SETTINGS
android.permission.NFC
android.permission.READ_SYNC_SETTINGS
android.permission.READ_SYNC_STATS
android.permission.RECEIVE_BOOT_COMPLETED
android.permission.REORDER_TASKS
android.permission.REQUEST_INSTALL_PACKAGES
android.permission.SET_TIME_ZONE
android.permission.SET_WALLPAPER
android.permission.SET_WALLPAPER_HINTS
android.permission.SUBSCRIBED_FEEDS_READ
android.permission.TRANSMIT_IR
android.permission.USE_FINGERPRINT
android.permission.VIBRATE
android.permission.WAKE_LOCK
android.permission.WRITE_SYNC_SETTINGS
com.android.alarm.permission.SET_ALARM
com.android.launcher.permission.INSTALL_SHORTCUT
com.android.launcher.permission.UNINSTALL_SHORTCUT

```

## 运行时权限

运行时权限需要与用户发生交互，当用户同意之后，App才能做接下来的事情。简单说一下权限申请的流程
    * 判断权限是否已经申请
    * 如果未申请，申请权限，如果权限已经申请，不会重复申请
    * 申请权限过程：初次弹出提示框（拒绝、接受），接受：申请成功。不接受：申请失败
    * 如果初次申请失败，下一次进入时，再次弹出提示框（拒绝，接受，不再提示），不再提示：完全拒绝，下次进入不再弹出申请提示框
    
JPermissionManager用法

```java
//权限申请
JPermissionManager.getInstance().requestPs(this,this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE});
```

```java
//申请回馈处理
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    JPermissionManager.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
}
```

```java
//申请成功（已申请，初次申请成功，再次申请成功）
@Override
public void onPsRequestSuc() {
    Toast.makeText(this,"request suc",Toast.LENGTH_SHORT).show();
}
//申请失败
@Override
public void onPsRequestFai(String permission,int faiCode) {
    switch (faiCode){
        case JPermissionManager.FAI_REFUSE_ALLTIME_POSSIBLE:
            Toast.makeText(this,"ps("+permission+") request fai,set by yourself",Toast.LENGTH_SHORT).show();
            break;
        case JPermissionManager.FAI_REFUSE_ONETIME_POSSIBLE:
            Toast.makeText(this,"ps("+permission+") request fai",Toast.LENGTH_SHORT).show();
            break;
    }
}
```



