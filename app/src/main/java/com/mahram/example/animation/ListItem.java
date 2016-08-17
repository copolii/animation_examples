package com.mahram.example.animation;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 Created by mahra on 2016-08-16.
 */
public class ListItem {
    public final View view;
    @BindView (android.R.id.checkbox) CheckBox checkBox;
    @BindView (android.R.id.title) TextView title;
    @BindView (android.R.id.summary) TextView summary;

    private ViewPropertyAnimator animator = null;

    public ListItem (final View v) {
        view = v;
        ButterKnife.bind (this, view);
    }

    @OnCheckedChanged (android.R.id.checkbox) public void animateOnCheck () {
        animate ();
    }

    private void animate () {
        if (null != animator) {
            animator.cancel ();
        }

        checkBox.setScaleX (1);
        checkBox.setScaleY (1);

        animator = checkBox.animate ()
                .scaleX (1.3f)
                .scaleY (1.3f)
                .setDuration (300)
                .setInterpolator (new BoingInterpolator ());
        animator.start ();
    }

    public void postAnimateCheckbox () {
        checkBox.post (new Runnable () {
            @Override public void run () {
                animate ();
            }
        });
    }
}
