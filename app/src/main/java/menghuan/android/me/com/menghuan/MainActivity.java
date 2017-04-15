package menghuan.android.me.com.menghuan;




import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tabDeal;
    private TextView tabMore;
    private TextView tabShop;
    private TextView tabRelease;
    private TextView tabResume;

    private FrameLayout ly_content;

    private FirstFragment f1,f2,f3,f4,f5;
    private FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bindView();
        selected();
        transaction = getFragmentManager().beginTransaction();
        tabDeal.setSelected(true);
        if(f1==null){
            f1 = new FirstFragment("第一个Fragment");
            transaction.add(R.id.fragment_container,f1);
        }else{
            transaction.show(f1);
        }

    }

    //UI组件初始化与事件绑定
    private void bindView() {
        tabDeal = (TextView)this.findViewById(R.id.txt_deal);
        tabMore = (TextView)this.findViewById(R.id.txt_more);
        tabShop = (TextView)this.findViewById(R.id.txt_shop);
        tabRelease = (TextView)this.findViewById(R.id.txt_release);
        tabResume = (TextView)this.findViewById(R.id.txt_resume);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabShop.setOnClickListener(this);
        tabRelease.setOnClickListener(this);
        tabResume.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    public void selected(){
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        tabShop.setSelected(false);
        tabRelease.setSelected(false);
        tabResume.setSelected(false);

    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
        if(f5!=null){
            transaction.hide(f5);
        }
    }

    @Override
    public void onClick(View v) {
        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                if(f2==null){
                    f2 = new FirstFragment("第二个Fragment");
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.txt_release:
                selected();
                tabRelease.setSelected(true);
                if(f3==null){
                    f3 = new FirstFragment("第三个Fragment");
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;

            case R.id.txt_shop:
                selected();
                tabShop.setSelected(true);
                if(f4==null){
                    f4 = new FirstFragment("第四个Fragment");
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;

            case R.id.txt_resume:
                selected();
                tabResume.setSelected(true);
                if(f5==null){
                    f5 = new FirstFragment("第五个Fragment");
                    transaction.add(R.id.fragment_container,f5);
                }else{
                    transaction.show(f5);
                }
                break;
        }

        transaction.commit();
    }
}
