/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package theintelligentminds.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import theintelligentminds.messenger.emoji.Emojicon;
import theintelligentminds.messenger.emoji.EmojiconGridFragment;
import theintelligentminds.messenger.emoji.EmojiconsFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    EditText mEditEmojicon;
    ToggleButton emoToggleButton;
    Fragment fragment;
    LinearLayout emojilayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditEmojicon = (EditText) findViewById(R.id.editEmojicon);
        emoToggleButton = (ToggleButton) findViewById(R.id.emoToggleButton);
        fragment = getSupportFragmentManager().findFragmentById(R.id.emojicons);
        mEditEmojicon.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        emoToggleButton.setOnClickListener(this);
        emojilayout =(LinearLayout) findViewById(R.id.emojilayout);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }

    @Override
    public void onClick(View v) {
        if(emoToggleButton.isChecked()){
            System.out.println("DEACTIVATE!!!");
            emojilayout.setVisibility(View.GONE);
        }else{
            System.out.println("ACTIVATE!!!");
            emojilayout.setVisibility(View.VISIBLE);
        }
    }
}
