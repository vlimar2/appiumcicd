package com.qa.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.util.HashMap;

public class ServerManager {
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();
    TestUtils utils = new TestUtils();

    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    public void startServer (){
        utils.log().info("starting appium server");
       AppiumDriverLocalService server = MacGetAppiumService();
       server.start();
       if (server == null || !server.isRunning()){
           utils.log().fatal("Appium server not started ABORT!");
           throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
       }
       this.server.set(server);
       utils.log().info("Appium server started");

        //server.clearOutPutStreams();
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = new GlobalParams();
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/Library/apache-maven-3.8.3/bin:/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/apple/" +
                "Library/Android/sdk/tools:/Users/apple/Library/Android/sdk/platform-tools:/Library/apache-maven-3.8.3/bin:/" +
                "Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/apple/" +
                "Library/Android/sdk/tools:/Users/apple/Library/Android/sdk/platform-tools:/" +
                "Library/apache-maven-3.8.3/bin:/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/apple/" +
                "Library/Android/sdk/tools:/Users/apple/Library/Android/sdk/platform-tools:/Library/apache-maven-3.8.3/bin:/" +
                "Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/apple/" +
                "Library/Android/sdk/tools:/Users/apple/" +
                "Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/share/dotnet:~/.dotnet/tools:/" +
                "Library/Apple/usr/bin:/Library/Frameworks/Mono.framework/Versions/Current/Commands" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "/Users/apple/Library/Android/sdk");
        environment.put("JAVA_HOME", "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }
}
