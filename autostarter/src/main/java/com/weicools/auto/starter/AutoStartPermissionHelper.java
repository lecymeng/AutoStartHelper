package com.weicools.auto.starter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author weicools
 * @date 2020.02.17
 */
public class AutoStartPermissionHelper {
  private static class AutoStartPermissionHolder {
    static final AutoStartPermissionHelper INSTANCE = new AutoStartPermissionHelper();
  }

  /**
   * Huawei
   * 华为手机管家的启动管理页：应用启动管理 -> 关闭应用开关 -> 打开允许自启动
   */
  private static final String BRAND_HUAWEI = "huawei";
  private static final String PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager";
  private static final String PACKAGE_HUAWEI_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";
  private static final String PACKAGE_HUAWEI_COMPONENT_FALLBACK = "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";
  // TODO: 2020-02-18
  private static final String PACKAGE_HUAWEI_COMPONENT_FALLBACK_A = "com.huawei.systemmanager.optimize.bootstart.BootStartActivity";

  /**
   * Honor
   * 华为手机管家的启动管理页：应用启动管理 -> 关闭应用开关 -> 打开允许自启动
   */
  private static final String BRAND_HONOR = "honor";
  private static final String PACKAGE_HONOR_MAIN = "com.huawei.systemmanager";
  private static final String PACKAGE_HONOR_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";
  // TODO: 2020-02-18
  private static final String PACKAGE_HONOR_COMPONENT_FALLBACK_A = "com.huawei.systemmanager.optimize.bootstart.BootStartActivity";

  /**
   * Xiaomi
   * 小米安全中心的自启动管理页面：授权管理 -> 自启动管理 -> 允许应用自启动
   */
  private static final String BRAND_XIAOMI = "xiaomi";
  private static final String BRAND_XIAOMI_REDMI = "redmi";
  private static final String PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter";
  private static final String PACKAGE_XIAOMI_COMPONENT = "com.miui.permcenter.autostart.AutoStartManagementActivity";

  /**
   * Oppo
   * OPPO手机管家：权限隐私 -> 自启动管理 -> 允许应用自启动
   */
  private static final String BRAND_OPPO = "oppo";
  private static final String PACKAGE_OPPO_MAIN = "com.coloros.safecenter";
  private static final String PACKAGE_OPPO_FALLBACK = "com.oppo.safe";
  private static final String PACKAGE_OPPO_COMPONENT = "com.coloros.safecenter.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_COMPONENT_FALLBACK = "com.oppo.safe.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_COMPONENT_FALLBACK_A = "com.coloros.safecenter.startupapp.StartupAppListActivity";
  // TODO: 2020-02-18 Home: "com.coloros.phonemanager"  "com.oppo.safe"  "com.coloros.oppoguardelf"  "com.coloros.safecenter"

  /**
   * Vivo
   * VIVO手机管家：权限管理 -> 自启动 -> 允许应用自启动
   */
  private static final String BRAND_VIVO = "vivo";
  private static final String PACKAGE_VIVO_MAIN = "com.iqoo.secure";
  private static final String PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager";
  private static final String PACKAGE_VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
  private static final String PACKAGE_VIVO_COMPONENT_FALLBACK = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";
  private static final String PACKAGE_VIVO_COMPONENT_FALLBACK_A = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";
  // TODO: 2020-02-18 Home: "com.iqoo.secure"

  /**
   * One plus
   */
  private static final String BRAND_ONE_PLUS = "oneplus";
  private static final String PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security";
  private static final String PACKAGE_ONE_PLUS_COMPONENT = "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity";

  /**
   * Meizu
   * 魅族手机管家：权限管理 -> 后台管理 -> 点击应用 -> 允许后台运行
   */
  private static final String BRAND_MEIZU = "meizu";
  private static final String PACKAGE_MEIZU_MAIN = "com.meizu.safe";

  /**
   * Samsung
   * 三星智能管理器：自动运行应用程序 -> 打开应用开关 -> 电池管理 -> 未监视的应用程序 -> 添加应用
   */
  private static final String BRAND_SAMSUNG = "samsung";
  private static final String PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool";
  private static final String PACKAGE_SAMSUNG_COMPONENT = "com.samsung.android.sm.ui.battery.BatteryActivity";
  // TODO: 2020-02-18 Home: "com.samsung.android.sm_cn"  "com.samsung.android.sm"

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
  private static final String PACKAGE_ASUS_COMPONENT = "com.asus.mobilemanager.powersaver.PowerSaverSettings";
  private static final String PACKAGE_ASUS_COMPONENT_FALLBACK = "com.asus.mobilemanager.autostart.AutoStartActivity";

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

  private static final List<String> PACKAGES_TO_CHECK_FOR_PERMISSION = Arrays.asList(
      PACKAGE_ASUS_MAIN, PACKAGE_XIAOMI_MAIN, PACKAGE_LETV_MAIN, PACKAGE_HONOR_MAIN, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_FALLBACK, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_FALLBACK,
      PACKAGE_NOKIA_MAIN, PACKAGE_HUAWEI_MAIN, PACKAGE_SAMSUNG_MAIN, PACKAGE_ONE_PLUS_MAIN
  );

  private AutoStartPermissionHelper() {
  }

  public static AutoStartPermissionHelper getInstance() {
    return AutoStartPermissionHolder.INSTANCE;
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

  public boolean getAutoStartPermission(Context context) {
    if (TextUtils.isEmpty(Build.BRAND)) {
      return false;
    }
    switch (Build.BRAND.toLowerCase(Locale.getDefault())) {
      case BRAND_HUAWEI:
        return autoStartHuawei(context);
      case BRAND_HONOR:
        return autoStartHonor(context);
      case BRAND_XIAOMI:
      case BRAND_XIAOMI_REDMI:
        return autoStartXiaomi(context);
      case BRAND_OPPO:
        return autoStartOppo(context);
      case BRAND_VIVO:
        return autoStartVivo(context);
      case BRAND_ONE_PLUS:
        return autoStartOnePlus(context);
      case BRAND_SAMSUNG:
        return autoStartSamsung(context);
      case BRAND_NOKIA:
        return autoStartNokia(context);
      case BRAND_ASUS:
        return autoStartAsus(context);
      case BRAND_LETV:
        return autoStartLetv(context);
      default:
        return false;
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

  private void startIntent(Context context, String packageName, String componentName) throws Exception {
    try {
      Intent intent = new Intent();
      intent.setComponent(new ComponentName(packageName, componentName));
      context.startActivity(intent);
    } catch (Exception ex) {
      throw ex;
    }
  }

  private boolean autoStartHuawei(Context context) {
    if (isPackageExists(context, PACKAGE_HUAWEI_MAIN)) {
      try {
        startIntent(context, PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          startIntent(context, PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT_FALLBACK);
        } catch (Exception ex) {
          e.printStackTrace();
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartHonor(Context context) {
    if (isPackageExists(context, PACKAGE_HONOR_MAIN)) {
      try {
        startIntent(context, PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartXiaomi(Context context) {
    if (isPackageExists(context, PACKAGE_XIAOMI_MAIN)) {
      try {
        startIntent(context, PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartOppo(Context context) {
    if (isPackageExists(context, PACKAGE_OPPO_MAIN)) {
      try {
        startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_FALLBACK);
        } catch (Exception ex) {
          ex.printStackTrace();
          try {
            startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_FALLBACK_A);
          } catch (Exception exa) {
            exa.printStackTrace();
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartVivo(Context context) {
    if (isPackageExists(context, PACKAGE_VIVO_MAIN)) {
      try {
        startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_FALLBACK);
        } catch (Exception ex) {
          ex.printStackTrace();
          try {
            startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_FALLBACK_A);
          } catch (Exception exa) {
            exa.printStackTrace();
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartOnePlus(Context context) {
    if (isPackageExists(context, PACKAGE_ONE_PLUS_MAIN)) {
      try {
        startIntent(context, PACKAGE_ONE_PLUS_MAIN, PACKAGE_ONE_PLUS_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartSamsung(Context context) {
    if (isPackageExists(context, PACKAGE_SAMSUNG_MAIN)) {
      try {
        startIntent(context, PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartNokia(Context context) {
    if (isPackageExists(context, PACKAGE_NOKIA_MAIN)) {
      try {
        startIntent(context, PACKAGE_NOKIA_MAIN, PACKAGE_NOKIA_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartAsus(Context context) {
    if (isPackageExists(context, PACKAGE_ASUS_MAIN)) {
      try {
        startIntent(context, PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          startIntent(context, PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_FALLBACK);
        } catch (Exception ex) {
          ex.printStackTrace();
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean autoStartLetv(Context context) {
    if (isPackageExists(context, PACKAGE_LETV_MAIN)) {
      try {
        startIntent(context, PACKAGE_LETV_MAIN, PACKAGE_LETV_COMPONENT);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
    return true;
  }
}
