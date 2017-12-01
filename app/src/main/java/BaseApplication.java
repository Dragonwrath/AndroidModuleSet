import android.app.Application;


public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler{
  @Override
  public void onCreate(){
    super.onCreate();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  @Override
  public void uncaughtException(Thread t,Throwable e){
    e.printStackTrace();
  }
}
