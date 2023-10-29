package com.exmaple2.hello_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmaple2.hello_android.data.BookName;
import com.exmaple2.hello_android.data.DataBank;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        booknames = new DataBank().LoadBookNames(requireActivity());
        if(0 == booknames.size()) {
            booknames.add(new BookName("软件项目 管理案例教程(第4版)", R.drawable.book_2));
            booknames.add(new BookName("创新工程实践", R.drawable.book_no_name));
            booknames.add(new BookName("信息安全教学基础(第2版)", R.drawable.book_1));
        }

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

                        new DataBank().SaveBookNames(requireActivity(),booknames);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        updateBooklauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position",0);
                        String name = data.getStringExtra("name");

                        BookName bookName = booknames.get(position);
                        bookName.setName(name);
                        bookAdapter.notifyItemChanged(position);

                        new DataBank().SaveBookNames(requireActivity(),booknames);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        return rootView;
    }
    //第八次实验
    private DataBank dataBank;
    private ArrayList<BookName> booknames = new ArrayList<>(); // 声明为成员变量
    private BookAdapter bookAdapter; // 声明为成员变量

    ActivityResultLauncher<Intent> addBooklauncher;
    ActivityResultLauncher<Intent> updateBooklauncher;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://添加操作
                Intent intent = new Intent(requireActivity(), BookItemDetailsActivity.class);
                addBooklauncher.launch(intent);
                break;
            case 1:// 从数据集中移除对应项
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("You want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        booknames.remove(item.getOrder());
                        bookAdapter.notifyItemRemoved(item.getOrder());

                        new DataBank().SaveBookNames(requireActivity(),booknames);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                builder.create().show();
                break;
            case 2:// 修改操作
                Intent intentUpdate = new Intent(requireActivity(), BookItemDetailsActivity.class);
                BookName bookName = booknames.get(item.getOrder());
                intentUpdate.putExtra("name",bookName.getTitle());
                intentUpdate.putExtra("position",item.getOrder());
                updateBooklauncher.launch(intentUpdate);

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
    //第八次实验
}