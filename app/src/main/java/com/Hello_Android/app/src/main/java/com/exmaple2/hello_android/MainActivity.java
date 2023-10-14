package com.exmaple2.hello_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmaple2.hello_android.data.BookName;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<BookName> booknames; // 声明为成员变量
    private BookAdapter bookAdapter; // 声明为成员变量
    private int selectedPosition; // 存储选定项的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        booknames = new ArrayList<>();
        booknames.add(new BookName("软件项目 管理案例教程(第4版)", R.drawable.book_2));
        booknames.add(new BookName("创新工程实践", R.drawable.book_no_name));
        booknames.add(new BookName("信息安全教学基础(第2版)", R.drawable.book_1));

        bookAdapter = new BookAdapter(booknames);
        mainRecyclerView.setAdapter(bookAdapter);

        registerForContextMenu(mainRecyclerView);

        addBooklauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");

                        booknames.add(new BookName(name, R.drawable.book_2));
                        bookAdapter.notifyItemInserted(booknames.size());

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                }
        );
    }

    ActivityResultLauncher<Intent> addBooklauncher;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://添加操作
                Intent intent = new Intent(MainActivity.this, BookItemDetailsActivity.class);
                addBooklauncher.launch(intent);
                break;
            case 1:// 从数据集中移除对应项
                booknames.remove(selectedPosition);
                bookAdapter.notifyItemRemoved(selectedPosition);
                break;
            case 2:// 修改操作

                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private ArrayList<BookName> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewBookName;
            private final ImageView imageViewBook;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewBookName = itemView.findViewById(R.id.textView_book_name);
                imageViewBook = itemView.findViewById(R.id.imageView_book);
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                selectedPosition = getAdapterPosition(); // 获取选定项的位置

                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, getAdapterPosition(), "添加" + getAdapterPosition());
                menu.add(0, 1, getAdapterPosition(), "删除" + getAdapterPosition());
                menu.add(0, 2, getAdapterPosition(), "修改" + getAdapterPosition());
            }
        }

        public BookAdapter(ArrayList<BookName> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_name_row, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.textViewBookName.setText(localDataSet.get(position).getTitle());
            viewHolder.imageViewBook.setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }






        /*Button button = findViewById(R.id.button_change);
        button.setText(R.string.button1);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Button clickedButton = (Button) view;
                TextView text_hello = findViewById(R.id.text_View_1);
                TextView text_jnu = findViewById(R.id.text_View_2);

                CharSequence text1 = text_hello.getText();
                CharSequence text2 = text_jnu.getText();
                text_jnu.setText(text1);
                text_hello.setText(text2);

                Toast.makeText(MainActivity.this,"交换成功", Toast.LENGTH_SHORT).show();
                showDialog(MainActivity.this, "", "交换成功！");
            };
        });
    };
        private void showDialog(MainActivity context, String title, String message)
        {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("")
                .setMessage(message)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 在确定按钮点击时执行的逻辑
                    }
                })

                .show();
                //交换文字*/

        /*// 创建 RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.WHITE);

        // 创建 TextView
        TextView textViewHelloWorld = new TextView(this);
        textViewHelloWorld.setText(R.string.hello_android);

        // 创建 TextView 的布局参数
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //文字大小
        textViewHelloWorld.setTextSize(24);
        // 调整文本视图的位置
        textParams.leftMargin = 50;
        textParams.topMargin = 10;

        // 添加 TextView 到 RelativeLayout
        relativeLayout.addView(textViewHelloWorld, textParams);

        // 创建 ImageView
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.my_image);

        // 创建 ImageView 的布局参数
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.BELOW, textViewHelloWorld.getId());
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // 添加 ImageView 到 RelativeLayout
        relativeLayout.addView(imageView, imageParams);

        // 设置 RelativeLayout 作为主视图
        setContentView(relativeLayout); */
    //相对布局
}