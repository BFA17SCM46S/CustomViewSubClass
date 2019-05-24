package com.example.customviewsubclass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EditTextWithClear extends AppCompatEditText {
    Drawable mClearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_clear_opaque_24dp,null);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawablesRelative()[2] !=null){
                    float clearButtonStart;
                    float clearButtonEnd;
                    boolean isClearButtonClicked = false;
                    if (getLayoutDirection()==LAYOUT_DIRECTION_RTL){
                        clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        if (event.getX()<clearButtonEnd){
                            isClearButtonClicked = true;

                        }
                    }else {
                        clearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }
                    if (isClearButtonClicked){
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Switch to the opaque version of clear button.
                            mClearButtonImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_clear_opaque_24dp, null);
                            // Clear the text and hide the clear button.
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }else {
                        return false;
                    }

                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Top of text.
                        mClearButtonImage,  // End of text.
                        null);              // Below text.
    }
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Top of text.
                        null,      // End of text.
                        null);     // Below text.
    }
}
