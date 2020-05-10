package com.cy.app.testFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cy.app.R;
import com.cy.container.BaseFragment;

public class Fragment1 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        view.findViewById(R.id.mbtnShowFra2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TestFraActivity) getActivity()).showFra2();
            }
        });
        return view;
    }
}
