package example.com.sharepreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tvUser, tvPass, tvCheck;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvUser = findViewById(R.id.tv_user);
        tvPass = findViewById(R.id.tv_pass);
        tvCheck = findViewById(R.id.tv_checked);
        btnBack = findViewById(R.id.btn_back);

        //Cách đọc trạng thái đã lưu

        // Gọi hàm getSharedPreferences để trả về đối tượng SharedPreferences
        SharedPreferences pre=getSharedPreferences ("my_data",MODE_PRIVATE);
        // Gọi các phương thức getXXX(“key”,giá trị mặc định) để lấy các giá trị lúc trước được lưu
        String user= pre.getString("user", "");//lấy giá trị được lưu trong key=user, nếu không thấy thì gán giá trị mặc định là chuỗi rỗng
        String pwd = pre.getString("pwd", "");//giống trên
        String ck = pre.getString("checked", "");//giống trên

        tvUser.setText(user);
        tvPass.setText(pwd);
        tvCheck.setText(ck);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change Main2Activity to MainActivity
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
