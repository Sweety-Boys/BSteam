package com.example.bsteam.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bsteam.R;
import com.example.bsteam.adapter.CardAdapter;
import com.example.bsteam.entity.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardFragment extends Fragment {
    public static final String USER_ID = "user_id";
    private String mUserId;

    private Card[] fruits = {new Card("Apple", R.drawable.apple), new Card("Banana", R.drawable.banana),
            new Card("Orange", R.drawable.orange), new Card("Watermelon", R.drawable.watermelon),
            new Card("Pear", R.drawable.pear), new Card("Grape", R.drawable.grape),
            new Card("Pineapple", R.drawable.pineapple), new Card("Strawberry", R.drawable.strawberry),
            new Card("Cherry", R.drawable.cherry), new Card("Mango", R.drawable.mango)};
    private List<Card> cardList = new ArrayList<>();
    private CardAdapter adapter;

    public static CardFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserId = arguments.getString(USER_ID, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCards();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardAdapter(cardList,R.layout.card_item);
        recyclerView.setAdapter(adapter);
    }

    private void initCards() {
        cardList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            cardList.add(fruits[index]);
        }
    }
}
