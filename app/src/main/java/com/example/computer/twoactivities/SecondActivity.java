package com.example.computer.twoactivities;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.computer.twoactivities.extra.REPLY";
    private EditText mReply;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mReply = findViewById(R.id.editText_second);
        mConstraintLayout = findViewById(R.id.constraint);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
    }

    private Transition customTransition () {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
            TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
        }
        return transition;
    }

    private void animateLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this, R.layout.activity_second_keyframe);
            TransitionManager.beginDelayedTransition(mConstraintLayout, customTransition());
            constraintSet.applyTo(mConstraintLayout);
        }
    }

    public void returnReply(View view) {
        String reply = mReply.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void animate(View view) {
        animateLayout();
    }
}
