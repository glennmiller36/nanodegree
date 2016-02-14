package com.fluidminds.android.studiosity.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fluidminds.android.studiosity.BR;
import com.fluidminds.android.studiosity.R;
import com.fluidminds.android.studiosity.eventbus.ThemeColorChangedEvent;
import com.fluidminds.android.studiosity.models.SubjectModel;
import com.fluidminds.android.studiosity.viewmodels.SubjectViewModel;

import org.greenrobot.eventbus.EventBus;

/**
 * A fragment to Add or Edit an individual school Subject.
 */
public class SubjectEditFragment extends Fragment implements ColorPickerDialogFragment.OnColorPickerDialogListener {

    private ViewDataBinding mBinding;
    private SubjectViewModel mViewModel;

    public SubjectEditFragment() {
        // Required empty public constructor
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retain this fragment
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_edit, container, false);

        // mViewModel not null on orientation change
        if (mViewModel == null) {
            Intent intent = getActivity().getIntent();

            SubjectModel model = intent.getParcelableExtra("subjectmodel");

            mViewModel = new SubjectViewModel(model);
        }

        mBinding.setVariable(BR.viewModel, mViewModel);

        // Inflate the layout for this fragment
        View view = mBinding.getRoot();

        final Fragment targetFragment = this;

        LinearLayout button = (LinearLayout) view.findViewById(R.id.themeColor);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();

                ColorPickerDialogFragment dialogFragment = new ColorPickerDialogFragment();
                dialogFragment.setTargetFragment(targetFragment, 0);
                dialogFragment.setInitialColor(mViewModel.getModel().getColorInt());
                dialogFragment.show(fm, "SubjectEditFragment");
            }
        });

        return view;
    }

    /**
     * ColorPickerDialogFragment.OnColorPickerDialogListener
     */
    @Override
    public void onColorSelected(int color) {
        mViewModel.setColorInt(color);

        // Post the event
        EventBus bus = EventBus.getDefault();
        bus.post(new ThemeColorChangedEvent(color));
    }

    /**
     * Called from the Activity when user clicks Done button.
     */
    public SubjectModel Save() {
        return mViewModel.getModel().Save();
    }
}
