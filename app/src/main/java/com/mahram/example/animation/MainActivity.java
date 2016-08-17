package com.mahram.example.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity
  extends AppCompatActivity {
    private static final String LOGTAG = "ANIM8";

    @BindView (R.id.list) ListView list;
    @BindView (R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.d (LOGTAG, "Hello");
        setContentView (R.layout.activity_main);
        ButterKnife.bind (this);
        initList();
    }

    private void initList () {
        list.setAdapter (new GarbageGeneratorListAdapter());
    }

    @OnClick (R.id.fab) public void animateFab() {
        final Drawable icon = fab.getDrawable ();

        if (icon instanceof Animatable) {
            ((Animatable)icon).start ();
        }
    }

    private static class ItemObject {
        public final String Title;
        public final String Summary;

        public ItemObject (final String title, final String summary) {
            Title = title;
            Summary = summary;
        }
    }

    private static final Random RND = new Random (System.currentTimeMillis ());
    private static final String[] SUMMARY_FORMATS = {
                                                      "Item %d is a wonderful item. You'll find out in time.",
                                                      "You can'd find a better item than item %d.",
                                                      "Item %d is a bit crappy.",
                                                      "This is item %d in this wonderful list."
    };

    private class GarbageGeneratorListAdapter
      extends BaseAdapter {
        private int MAX_ITEMS = 200;

        private final ItemObject[] items = new ItemObject[RND.nextInt (MAX_ITEMS)];
        private final LayoutInflater inflater = LayoutInflater.from (MainActivity.this);

        @Override public int getCount () {
            return items.length;
        }

        @Override public ItemObject getItem (final int i) {
            if (null == items[i])
                items[i] = generate(i);

            return items[i];
        }

        private ItemObject generate (final int i) {
            final String title = String.format (Locale.ENGLISH, "Item %d", i);
            final String summary = String.format (Locale.ENGLISH,
                                                  SUMMARY_FORMATS[RND.nextInt (SUMMARY_FORMATS.length)],
                                                  i);
            return new ItemObject (title, summary);
        }

        @Override public long getItemId (final int i) {
            return i;
        }

        private View newView (final ViewGroup parent) {
            final View view = inflater.inflate (R.layout.list_item, parent, false);
            view.setTag (new ListItem (view));
            return view;
        }

        @Override public View getView (final int i, final View view, final ViewGroup parent) {
            final View itemView = null == view ? newView (parent): view;

            final ItemObject item = getItem (i);
            final ListItem row = (ListItem) itemView.getTag ();

            row.title.setText (item.Title);
            row.summary.setText (item.Summary);
            row.postAnimateCheckbox ();

            return itemView;
        }
    }
}
