package com.example.roombasic.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roombasic.dao.WordDao;
import com.example.roombasic.data.Word;
import com.example.roombasic.database.WordDatabase;
import com.example.roombasic.repository.WordRepository;

import java.util.List;

/**
 * How to create table with init value ??
 */
public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = WordRepository.getInstance(application.getApplicationContext());
    }

    public void insertWords(Word... words) {
        repository.insertWords(words);
    }

    public void updateWords(Word... words) {
        repository.updateWords(words);
    }

    public void deleteWords(Word... words) {
        repository.deleteWords(words);
    }

    public void deleteAllWords() {
        repository.deleteAllWords();
    }






}
