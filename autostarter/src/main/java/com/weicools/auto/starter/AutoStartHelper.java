package com.weicools.auto.starter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author weicools
 * @date 2020.02.17
 */
public class AutoStartHelper {
  public interface PermissionCallback {
    /**
     * 跳转手机管家主页成功
     */
    void onStartHomePageSuccess();

    /**
     * 跳转手机管家主页失败
     */
    void onStartHomePageFailed();

    /**
     * 跳转自启动授权页成功
     */
    void onStartPermissionPageSuccess();

    /**
     * 跳转自启动授权页失败
     * 没有适配自启动授权页时，则不会回调
     * 自启动授权页定义如：PACKAGE_{Brand}_COMPONENT_{x}
     */
    void onStartPermissionPageFailed();
  }

  public static class PermissionCallbackAdapter implements PermissionCallback {
    @Override public void onStartHomePageSuccess() { }

    @Override public void onStartHomePageFailed() { }

    @Override public void onStartPermissionPageSuccess() { }

    @Override public void onStartPermissionPageFailed() { }
  }

  private static class AutoStartHolder {
    static final AutoStartHelper INSTANCE = new AutoStartHelper();
  }

  /**
   * Huawei、Honor
   */
  private static final String BRAND_HUAWEI = "huawei";
  private static final String BRAND_HUAWEI_HONOR = "honor";
  private static final String PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager";
  // 华为手机管家 自启管理页面 -> 打开允许自启动
  private static final String TITLE_HUAWEI_STARTUP = "自启管理";
  private static final String COMPONENT_HUAWEI_STARTUP = "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";
  private static final String COMPONENT_HUAWEI_BOOST_START = "com.huawei.systemmanager.optimize.bootstart.BootStartActivity";
  // 华为手机管家 锁屏清理应用页面 -> 关闭锁屏清理
  private static final String TITLE_HUAWEI_PROTECT = "锁屏清理应用";
  private static final String COMPONENT_HUAWEI_PROTECT = "com.huawei.systemmanager.optimize.process.ProtectActivity";

  /**
   * Xiaomi、Redmi
   */
  private static final String BRAND_XIAOMI = "xiaomi";
  private static final String BRAND_XIAOMI_REDMI = "redmi";
  private static final String PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter";
  // 小米安全中心 自启动管理页面(路径: 授权管理 -> 自启动管理) -> 允许应用自启动
  private static final String TITLE_XIAOMI_AUTO_START = "自启动管理";
  private static final String COMPONENT_XIAOMI_AUTO_START = "com.miui.permcenter.autostart.AutoStartManagementActivity";

  /**
   * Oppo
   * OPPO手机管家：权限隐私 -> 自启动管理 -> 允许应用自启动
   */
  private static final String BRAND_OPPO = "oppo";
  private static final String PACKAGE_COLOROS_MAIN = "com.coloros.safecenter";
  private static final String TITLE_OPPO_COLOROS_STARTUP = "";
  private static final String COMPONENT_COLOROS_STARTUP = "com.coloros.safecenter.permission.startup.StartupAppListActivity";
  private static final String COMPONENT_COLOROS_STARTUP_APP = "com.coloros.safecenter.startupapp.StartupAppListActivity";
  private static final String PACKAGE_OPPO_MAIN = "com.oppo.safe";
  private static final String COMPONENT_OPPO_STARTUP = "com.oppo.safe.permission.startup.StartupAppListActivity";
  // TODO: 2020-02-18 MAIN: "com.coloros.phonemanager"  "com.coloros.oppoguardelf" 这些应用需要真机查看

  /**
   * Vivo
   * VIVO手机管家：权限管理 -> 自启动 -> 允许应用自启动
   */
  private static final String BRAND_VIVO = "vivo";
  private static final String PACKAGE_IQOO_MAIN = "com.iqoo.secure";
  private static final String TITLE_VIVO_IQOO_STARTUP = "";
  private static final String TITLE_VIVO_IQOO_WHITE_LIST = "";
  private static final String COMPONENT_IQOO_WHITE_LIST = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
  private static final String COMPONENT_IQOO_BG_STARTUP = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";
  private static final String PACKAGE_VIVO_MAIN = "com.vivo.permissionmanager";
  private static final String COMPONENT_VIVO_BG_STARTUP = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";

  /**
   * Oneplus
   */
  private static final String BRAND_ONEPLUS = "oneplus";
  private static final String PACKAGE_ONEPLUS_MAIN = "com.oneplus.security";
  private static final String TITLE_ONEPLUS_CHAIN_LAUNCH = "";
  private static final String COMPONENT_ONEPLUS_CHAIN_LAUNCH = "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity";

  /**
   * Meizu
   * 魅族手机管家：权限管理 -> 后台管理 -> 点击应用 -> 允许后台运行
   */
  private static final String BRAND_MEIZU = "meizu";
  private static final String PACKAGE_MEIZU_MAIN = "com.meizu.safe";
  private static final String TITLE_MEIZU_SMART_BG = "后台管理";
  private static final String COMPONENT_MEIZU_SMART_BG = "com.meizu.safe.SmartBGActivity";

  /**
   * Smartisan
   * 锤子手机管理：权限管理 -> 自启动权限管理 -> 点击应用 -> 允许被系统启动
   */
  private static final String BRAND_SMARTISAN = "smartisan";
  private static final String PACKAGE_SMARTISAN_MAIN = "com.smartisanos.security";

  /**
   * Letv
   * 乐视手机管家：自启动管理 -> 允许应用自启动
   */
  private static final String BRAND_LETV = "letv";
  private static final String PACKAGE_LETV_MAIN = "com.letv.android.letvsafe";
  private static final String PACKAGE_LETV_COMPONENT = "com.letv.android.letvsafe.AutobootManageActivity";

  /**
   * Samsung
   * 三星智能管理器：自动运行应用程序 -> 打开应用开关 -> 电池管理 -> 未监视的应用程序 -> 添加应用
   */
  private static final String BRAND_SAMSUNG = "samsung";
  private static final String PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool";
  private static final String PACKAGE_SAMSUNG_COMPONENT = "com.samsung.android.sm.ui.battery.BatteryActivity";
  // TODO: 2020-02-18 MAIN: "com.samsung.android.sm_cn"  "com.samsung.android.sm" 这些应用需要真机查看

  /**
   * Nokia
   */
  private static final String BRAND_NOKIA = "nokia";
  private static final String PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3";
  private static final String PACKAGE_NOKIA_COMPONENT = "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity";

  /**
   * ASUS ROG
   */
  private static final String BRAND_ASUS = "asus";
  private static final String PACKAGE_ASUS_MAIN = "com.asus.mobilemanager";
  private static final String PACKAGE_ASUS_COMPONENT_POWER_SAVER = "com.asus.mobilemanager.powersaver.PowerSaverSettings";
  private static final String PACKAGE_ASUS_COMPONENT_AUTO_START = "com.asus.mobilemanager.autostart.AutoStartActivity";

  private static final List<String> PACKAGES_TO_CHECK_FOR_PERMISSION = Arrays.asList(
      PACKAGE_HUAWEI_MAIN, PACKAGE_XIAOMI_MAIN, PACKAGE_COLOROS_MAIN, PACKAGE_OPPO_MAIN,
      PACKAGE_IQOO_MAIN, PACKAGE_VIVO_MAIN, PACKAGE_ONEPLUS_MAIN, PACKAGE_MEIZU_MAIN,
      PACKAGE_SMARTISAN_MAIN, PACKAGE_LETV_MAIN, PACKAGE_SAMSUNG_MAIN, PACKAGE_NOKIA_MAIN,
      PACKAGE_ASUS_MAIN
  );

  private AutoStartHelper() { }

  public static AutoStartHelper getInstance() {
    return AutoStartHolder.INSTANCE;
  }

  public boolean isAutoStartPermissionAvailable(@NonNull Context context) {
    List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(0);
    for (ApplicationInfo applicationInfo : applicationInfoList) {
      if (PACKAGES_TO_CHECK_FOR_PERMISSION.contains(applicationInfo.packageName)) {
        return true;
      }
    }
    return false;
  }

  public boolean getAutoStartPermission(@NonNull Context context) {
    return getAutoStartPermission(context, null);
  }

  public boolean getAutoStartPermission(@NonNull Context context, @Nullable PermissionCallback callback) {
    if (TextUtils.isEmpty(Build.BRAND)) {
      return false;
    }
    switch (Build.BRAND.toLowerCase(Locale.getDefault())) {
      case BRAND_HUAWEI:
      case BRAND_HUAWEI_HONOR:
        return autoStartHuawei(context, callback);
      case BRAND_XIAOMI:
      case BRAND_XIAOMI_REDMI:
        return autoStartXiaomi(context, callback);
      case BRAND_OPPO:
        return autoStartOppo(context, callback);
      case BRAND_VIVO:
        return autoStartVivo(context, callback);
      case BRAND_ONEPLUS:
        return autoStartOnePlus(context, callback);
      case BRAND_MEIZU:
        return autoStartMaizu(context, callback);
      case BRAND_SMARTISAN:
        return autoStartSmartisan(context, callback);
      case BRAND_LETV:
        return autoStartLetv(context, callback);
      case BRAND_SAMSUNG:
        return autoStartSamsung(context, callback);
      case BRAND_NOKIA:
        return autoStartNokia(context, callback);
      case BRAND_ASUS:
        return autoStartAsus(context, callback);
      default:
        return false;
    }
  }

  private void onStartHomePageSuccess(@Nullable PermissionCallback callback) {
    if (callback != null) {
      callback.onStartHomePageSuccess();
    }
  }

  private void onStartHomePageFailed(@Nullable PermissionCallback callback) {
    if (callback != null) {
      callback.onStartHomePageFailed();
    }
  }

  private void onStartPermissionPageSuccess(@Nullable PermissionCallback callback) {
    if (callback != null) {
      callback.onStartPermissionPageSuccess();
    }
  }

  private void onStartPermissionPageFailed(@Nullable PermissionCallback callback) {
    if (callback != null) {
      callback.onStartPermissionPageFailed();
    }
  }

  private boolean isPackageExists(@NonNull Context context, @NonNull String targetPackage) {
    List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(0);
    for (ApplicationInfo applicationInfo : applicationInfoList) {
      if (targetPackage.equals(applicationInfo.packageName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 跳转到指定应用的首页
   */
  private void startHomePage(@NonNull Context context, @NonNull String packageName) throws Exception {
    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
    try {
      context.startActivity(intent);
    } catch (Exception ex) {
      throw ex;
    }
  }

  /**
   * 跳转到授权页面
   */
  private void startPermissionPage(@NonNull Context context, @NonNull String packageName, @NonNull String componentName) throws Exception {
    try {
      Intent intent = new Intent();
      intent.setComponent(new ComponentName(packageName, componentName));
      context.startActivity(intent);
    } catch (Exception ex) {
      throw ex;
    }
  }

  private boolean autoStart(Context context, @NonNull String mainPackage,
                            @NonNull List<String> componentNameList, @Nullable PermissionCallback callback) {
    return autoStart(context, true, mainPackage, componentNameList, callback);
  }

  private boolean autoStart(Context context, boolean allowStartHome, @NonNull String mainPackage,
                            @NonNull List<String> componentNameList, @Nullable PermissionCallback callback) {
    if (isPackageExists(context, mainPackage)) {
      for (String componentName : componentNameList) {
        try {
          startPermissionPage(context, mainPackage, componentName);
          onStartPermissionPageSuccess(callback);
          return true;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      if (!componentNameList.isEmpty()) {
        onStartPermissionPageFailed(callback);
      }

      if (allowStartHome) {
        try {
          startHomePage(context, mainPackage);
          onStartHomePageSuccess(callback);
          return true;
        } catch (Exception e) {
          e.printStackTrace();
          onStartHomePageFailed(callback);
        }
      }
    }

    if (allowStartHome) {
      onStartHomePageFailed(callback);
    }
    return false;
  }

  private boolean autoStartHuawei(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_HUAWEI_MAIN,
        Arrays.asList(COMPONENT_HUAWEI_STARTUP, COMPONENT_HUAWEI_BOOST_START, COMPONENT_HUAWEI_PROTECT), callback);
  }

  private boolean autoStartXiaomi(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_XIAOMI_MAIN, Collections.singletonList(COMPONENT_XIAOMI_AUTO_START), callback);
  }

  private boolean autoStartOppo(Context context, PermissionCallback callback) {
    if (autoStart(context, false, PACKAGE_COLOROS_MAIN,
        Arrays.asList(COMPONENT_COLOROS_STARTUP, COMPONENT_COLOROS_STARTUP_APP), callback)) {
      return true;
    }
    if (autoStart(context, false, PACKAGE_OPPO_MAIN,
        Collections.singletonList(COMPONENT_OPPO_STARTUP), callback)) {
      return true;
    }
    try {
      startHomePage(context, PACKAGE_COLOROS_MAIN);
      onStartHomePageSuccess(callback);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      try {
        startHomePage(context, PACKAGE_OPPO_MAIN);
        onStartHomePageSuccess(callback);
        return true;
      } catch (Exception ex) {
        ex.printStackTrace();
        onStartHomePageFailed(callback);
        return false;
      }
    }
  }

  private boolean autoStartVivo(Context context, PermissionCallback callback) {
    if (autoStart(context, false, PACKAGE_IQOO_MAIN,
        Arrays.asList(COMPONENT_IQOO_WHITE_LIST, COMPONENT_IQOO_BG_STARTUP), callback)) {
      return true;
    }
    if (autoStart(context, false, PACKAGE_VIVO_MAIN,
        Collections.singletonList(COMPONENT_VIVO_BG_STARTUP), callback)) {
      return true;
    }
    try {
      startHomePage(context, PACKAGE_IQOO_MAIN);
      onStartHomePageSuccess(callback);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      try {
        startHomePage(context, PACKAGE_VIVO_MAIN);
        onStartHomePageSuccess(callback);
        return true;
      } catch (Exception ex) {
        ex.printStackTrace();
        onStartHomePageFailed(callback);
        return false;
      }
    }
  }

  private boolean autoStartOnePlus(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_ONEPLUS_MAIN, Collections.singletonList(COMPONENT_ONEPLUS_CHAIN_LAUNCH), callback);
  }

  private boolean autoStartMaizu(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_MEIZU_MAIN, Collections.singletonList(COMPONENT_MEIZU_SMART_BG), callback);
  }

  private boolean autoStartSmartisan(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_SMARTISAN_MAIN, new ArrayList<String>(), callback);
  }

  private boolean autoStartLetv(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_LETV_MAIN, Collections.singletonList(PACKAGE_LETV_COMPONENT), callback);
  }

  private boolean autoStartSamsung(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_SAMSUNG_MAIN, Collections.singletonList(PACKAGE_SAMSUNG_COMPONENT), callback);
  }

  private boolean autoStartNokia(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_NOKIA_MAIN, Collections.singletonList(PACKAGE_NOKIA_COMPONENT), callback);
  }

  private boolean autoStartAsus(Context context, PermissionCallback callback) {
    return autoStart(context, PACKAGE_ASUS_MAIN, Arrays.asList(PACKAGE_ASUS_COMPONENT_POWER_SAVER, PACKAGE_ASUS_COMPONENT_AUTO_START), callback);
  }
}
