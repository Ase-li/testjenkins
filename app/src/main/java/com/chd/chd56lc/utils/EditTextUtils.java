package com.chd.chd56lc.utils;

import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class EditTextUtils {
    public static Disposable AfterCountTwo(final EditText editText) {
        return RxTextView.afterTextChangeEvents(editText).filter(new Predicate<TextViewAfterTextChangeEvent>() {
            @Override
            public boolean test(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                String string = textViewAfterTextChangeEvent.editable().toString();
                if (".".equals(string)) {
                    return true;
                } else if (string.contains(".")&&string.substring(string.indexOf(".") + 1).length() > 2) {
                    return true;
                }
                return false;
            }
        }).map(new Function<TextViewAfterTextChangeEvent, CharSequence>() {
            @Override
            public CharSequence apply(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                String string = textViewAfterTextChangeEvent.editable().toString();
                if (".".equals(string)) {
                    return "0.";
                } else if (string.substring(string.indexOf(".") + 1).length() > 2) {
                    return string.substring(0, string.indexOf(".") + 3);
                }
                return string;
            }
        }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                editText.setText(charSequence);
                editText.setSelection(charSequence.length());
            }
        });
    }
}
