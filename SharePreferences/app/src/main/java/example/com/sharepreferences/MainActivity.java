package example.com.sharepreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText edUser, edPass, edCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_Change);
        edUser = findViewById(R.id.ed_user);
        edPass = findViewById(R.id.ed_pass);
        edCheck = findViewById(R.id.ed_check);

        //Gọi hàm getSharedPreferences
        final SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        // Tạo đổi tượng Editor để cho phép chỉnh sửa dữ liệu
        final SharedPreferences.Editor edit= pre.edit();

        // Event btn click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Đưa dữ liệu muốn lưu trữ vào edit bằng các phương thức edit.putXXX(“key”,”value”)
                edit.putString("user", edUser.getText().toString());
                edit.putString("pwd", edPass.getText().toString());
                edit.putString("checked", edCheck.getText().toString());
                //  Lưu trạng thái bằng cách gọi dòng lệnh
                edit.commit();

                // Change MainActivity to Main2Activity
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
