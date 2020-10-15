package com.example.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.roombasic.dao.WordDao;
import com.example.roombasic.data.Word;
import com.example.roombasic.database.WordDatabase;
import com.example.roombasic.databinding.ActivityMainBinding;
import com.example.roombasic.viewModel.WordViewModel;

import java.util.List;

import static com.example.roombasic.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    /**
     * TODO:
     * 1. DI with repository
     * 2. RecyclerView with newer version
     */


    ActivityMainBinding mBinding;
    WordViewModel mViewModel;

    WordDatabase mWordDatabase;
    WordDao mWordDao;

    LiveData<List<Word>> mAllWordsLive;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, activity_main);

        mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mWordDatabase = WordDatabase.getInstance(this);

        mWordDao = mWordDatabase.getWordDao();

        final MyAdapter adapterNormal = new MyAdapter(false, mViewModel);
        final MyAdapter adapterCard = new MyAdapter(true, mViewModel);

        mBinding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBinding.recyclerView.setAdapter(adapterCard);
                } else {
                    mBinding.recyclerView.setAdapter(adapterNormal);
                }
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(adapterNormal);

        mAllWordsLive = mWordDao.getAllWordsLive();
        mAllWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int size = adapterNormal.getItemCount();

                adapterNormal.setAllWords(words);
                adapterNormal.notifyDataSetChanged();

                if(size != words.size()) {
                    adapterCard.setAllWords(words);
                    adapterCard.notifyDataSetChanged();
                }
            }
        });

        mBinding.buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                  "Hello",
                  "World",
                  "Android",
                  "Studio",
                  "Room",
                  "Basic",
                  "Database",
                  "Persistence",
                  "Library",
                  "Project",
                  "Fun",
                  "Cool"
                };

                String[] chinese = {
                  "你好",
                  "世界",
                  "安卓",
                  "工坊",
                  "房間",
                  "基本",
                  "資料庫",
                  "持久化",
                  "函式庫",
                  "計畫",
                  "有趣",
                  "酷"
                };

                for (int i = 0; i < english.length; i++) {
                    Word word = new Word(english[i], chinese[i]);
                    mViewModel.insertWords(word);
                }
            }
        });

        mBinding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteAllWords();
            }
        });

    }




}