package com.example.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

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
     * 1.make repository singleton
     * 2. DI
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

        mAllWordsLive = mWordDao.getAllWordsLive();
        mAllWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder sb = new StringBuilder();
                for (Word word : words) {
                    sb.append(word.getId() + ": " + word.getWord() + " = " + word.getChineseMeaning() + "\n");
                }
                mBinding.textView.setText(sb.toString());
            }
        });

        mBinding.buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                mViewModel.insertWords(word1, word2);
            }
        });

        mBinding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteAllWords();
            }
        });

        mBinding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "你好啊");
                word.setId(70);
                mViewModel.updateWords(word);
            }
        });

        mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "你好啊");
                word.setId(70);
                mViewModel.deleteWords(word);
            }
        });


    }




}