package menghuan.android.me.com.menghuan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import menghuan.android.me.com.menghuan.authenticator.AuthenticatorActivity;

public class start extends Activity {
    private ImageView welcomeImg;
    private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        welcomeImg = (ImageView) findViewById(R.id.welcome);
        anim = new AlphaAnimation(0.1f, 1.0f); //设置透明度动画效果
        anim.setDuration(2000); //设置动画持续时间 2 秒钟
        welcomeImg.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
            //这里将启动注册和登录界面，目前什么都不做，直接退出
                Intent intent = new Intent(start.this, AuthenticatorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}