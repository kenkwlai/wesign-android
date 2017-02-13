package com.example.waichiuyung.text_to_sign;

/**
 * Created by waichiuyung on 22/1/2017.
 */

public class BookmarkList {
    private String word;

    public BookmarkList(String bookmarkVocab){
        word=bookmarkVocab;
    }

    public String getBookmark()
    {
        return word;
    }
}
