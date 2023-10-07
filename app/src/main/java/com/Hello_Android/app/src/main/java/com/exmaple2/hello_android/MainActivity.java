package com.exmaple2.hello_android;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmaple2.hello_android.data.BookName;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<BookName> booknames= new ArrayList<>();
        for(int iLoop=0;iLoop<10;iLoop++) {
            booknames.add(new BookName("软件项目 管理案例教程(第4版)", R.drawable.book_2));
            booknames.add(new BookName("创新工程实践", R.drawable.book_no_name));
            booknames.add(new BookName("信息安全教学基础(第2版)", R.drawable.book_1));
        }

        BookAdapter bookAdapter = new BookAdapter(booknames);
        mainRecyclerView.setAdapter(bookAdapter);
    }
    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

        private ArrayList<BookName> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewBookName;
            private final ImageView imageViewBook;
            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textViewBookName = view.findViewById(R.id.textView_book_name);
                imageViewBook    = view.findViewById(R.id.imageView_book);
            }

            public TextView getTextViewBookName() {
                return textViewBookName;
            }
            public ImageView getImageViewBookName() {
                return imageViewBook;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public BookAdapter(ArrayList<BookName> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_name_row, viewGroup, false);

            return new ViewHolder(view);
        }
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextViewBookName().setText(localDataSet.get(position).getTitle());
            viewHolder.getImageViewBookName().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
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