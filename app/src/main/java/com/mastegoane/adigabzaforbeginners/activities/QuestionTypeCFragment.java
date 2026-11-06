package com.mastegoane.adigabzaforbeginners.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databinding.FragmentQuestionTypeCBinding;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import com.mastegoane.adigabzaforbeginners.viewmodels.LessonViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionTypeCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionTypeCFragment extends Fragment {

    public void buttonLessonQuestionClicked(View view) {
        final String sound = mLessonViewModel.getCurrentQuestionSound().getValue();
        if (sound != null) {
            SoundPlayer.getInstance().playSound("AllLessons", sound);
        }
    }

    public void buttonLessonOptionClicked(View view) {
        if (mLessonViewModel.isAnswerSelected()) {
            return;
        }
        int selectedOptionId = 0;
        boolean leftSideSelected = true;
        final int buttonId = view.getId();
        if (buttonId == R.id.buttonLessonOptionA1) {
            selectedOptionId = 1;
        } else if (buttonId == R.id.buttonLessonOptionA2) {
            selectedOptionId = 2;
        } else if (buttonId == R.id.buttonLessonOptionA3) {
            selectedOptionId = 3;
        } else if (buttonId == R.id.buttonLessonOptionA4) {
            selectedOptionId = 4;
        } else if (buttonId == R.id.buttonLessonOptionA5) {
            selectedOptionId = 5;
        } else if (buttonId == R.id.buttonLessonOptionB1) {
            selectedOptionId = 1;
            leftSideSelected = false;
        } else if (buttonId == R.id.buttonLessonOptionB2) {
            selectedOptionId = 2;
            leftSideSelected = false;
        } else if (buttonId == R.id.buttonLessonOptionB3) {
            selectedOptionId = 3;
            leftSideSelected = false;
        } else if (buttonId == R.id.buttonLessonOptionB4) {
            selectedOptionId = 4;
            leftSideSelected = false;
        } else if (buttonId == R.id.buttonLessonOptionB5) {
            selectedOptionId = 5;
            leftSideSelected = false;
        } else {
            Log.e(TAG, "-B- BUG: Unexpected button ID: " + buttonId);
            return;
        }
        int lastSelectedOptionId;
        if (leftSideSelected) {
            mLeftSideSelected = true;
            lastSelectedOptionId = mLessonViewModel.getLastSelectedOptionIdLeft();
        } else {
            mRightSideSelected = true;
            lastSelectedOptionId = mLessonViewModel.getLastSelectedOptionIdRight();
        }
        if (selectedOptionId == lastSelectedOptionId) {
            if (leftSideSelected) {
                SoundPlayer.getInstance().playSound("AllLessons", mLessonViewModel.getSelectedOptionSound());
            }
            return;
        }
        mLessonViewModel.setSelectedAnswerText(((TextView)view).getText().toString());
        if (leftSideSelected) {
            mLessonViewModel.setSelectedAnswerIdLeft(selectedOptionId);
        } else {
            mLessonViewModel.setSelectedAnswerIdRight(selectedOptionId);
        }

        mLessonViewModel.setButtonLessonOptionState(selectedOptionId, LessonViewModel.ButtonOptionState.BLUE, leftSideSelected);
        if (lastSelectedOptionId != 0) {
            mLessonViewModel.setButtonLessonOptionState(lastSelectedOptionId, LessonViewModel.ButtonOptionState.UNSELECTED, leftSideSelected);
        }
        if (leftSideSelected) {
            mLessonViewModel.setLastSelectedOptionIdLeft(selectedOptionId);
            // next is needed only for buttons which have sound (only left side)
            mLessonViewModel.setSelectedOptionId(selectedOptionId);
            SoundPlayer.getInstance().playSound("AllLessons", mLessonViewModel.getSelectedOptionSound());
        } else {
            mLessonViewModel.setLastSelectedOptionIdRight(selectedOptionId);
        }

        if (!mLeftSideSelected || !mRightSideSelected) {
            return;
        }

        boolean pickedCorrectPair = false;
        if (mLessonViewModel.getSelectedAnswerIdLeft() != 0 && mLessonViewModel.getSelectedAnswerIdRight() != 0) {
            pickedCorrectPair = mLessonViewModel.checkAnswerType9();
            animateOptionButtonPair(pickedCorrectPair);
        }
        if (pickedCorrectPair) {
            ++mNumOfCorrectPairs;
        } else {
            ++mNumOfWrongPairs;
        }

        if (mNumOfCorrectPairs == 5) {
            mNumOfCorrectPairs = 0;
            mNumOfWrongPairs = 0;
            mLeftSideSelected = false;
            mRightSideSelected = false;
            mLessonViewModel.setAnswerIsSelected(true);
            mLessonViewModel.setSelectButtonEnabled(true);
            mLessonViewModel.setSelectButtonActivated(true);
            mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select);
            mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.white));
            mLessonViewModel.setSelectButtonText(getResources().getString(R.string.next));
            mLessonViewModel.setResultStatus(1); // visible, correct
            return;
        }
        if (mNumOfWrongPairs == 1 && !pickedCorrectPair) {
            mLessonViewModel.setSelectButtonEnabled(true);
//            mLessonViewModel.setSelectButtonActivated(true);
            mLessonViewModel.setSelectButtonActivated(false);
            mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select);
            mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.white));
            mLessonViewModel.setSelectButtonText(getResources().getString(R.string.continue_selecting));
            mLessonViewModel.setResultStatus(3); // visible, first time wrong
            return;
        }
        mLessonViewModel.setSelectButtonEnabled(false);
        mLessonViewModel.setSelectButtonActivated(false);
        mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select_disabled);
        mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.black));
        mLessonViewModel.setSelectButtonText(getResources().getString(R.string.button_select));
        mLessonViewModel.setResultStatus(0); // invisible
    }

    private void animateOptionButtonPair(boolean correctAnswer) {
        final int leftOptionId = mLessonViewModel.getSelectedAnswerIdLeft();
        final int rightOptionId = mLessonViewModel.getSelectedAnswerIdRight();
        LessonViewModel.ButtonOptionState finalState;
        if (correctAnswer) {
            mLessonViewModel.setButtonLessonOptionState(leftOptionId, LessonViewModel.ButtonOptionState.GREEN, true);
            mLessonViewModel.setButtonLessonOptionState(rightOptionId, LessonViewModel.ButtonOptionState.GREEN, false);
            finalState = LessonViewModel.ButtonOptionState.DISABLED;
//            mLessonViewModel.setButtonLessonOptionState(leftOptionId, LessonViewModel.ButtonOptionState.DISABLED, true);
//            mLessonViewModel.setButtonLessonOptionState(rightOptionId, LessonViewModel.ButtonOptionState.DISABLED, false);
        } else {
            mLessonViewModel.setButtonLessonOptionState(leftOptionId, LessonViewModel.ButtonOptionState.RED, true);
            mLessonViewModel.setButtonLessonOptionState(rightOptionId, LessonViewModel.ButtonOptionState.RED, false);
            finalState = LessonViewModel.ButtonOptionState.UNSELECTED;
//            mLessonViewModel.setButtonLessonOptionState(leftOptionId, LessonViewModel.ButtonOptionState.UNSELECTED, true);
//            mLessonViewModel.setButtonLessonOptionState(rightOptionId, LessonViewModel.ButtonOptionState.UNSELECTED, false);
        }
        mLessonViewModel.setSelectedAnswerIdLeft(0);
        mLessonViewModel.setSelectedAnswerIdRight(0);
        mLessonViewModel.setLastSelectedOptionIdLeft(0);
        mLessonViewModel.setLastSelectedOptionIdRight(0);
        mLeftSideSelected = false;
        mRightSideSelected = false;
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLessonViewModel.setButtonLessonOptionState(leftOptionId, finalState, true);
                mLessonViewModel.setButtonLessonOptionState(rightOptionId, finalState, false);
            }
        }, 1200);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionTypeCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionTypeCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionTypeCFragment newInstance(String param1, String param2) {
        QuestionTypeCFragment fragment = new QuestionTypeCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        mSelectedOptionLeft = 0;
//        mSelectedOptionRight = 0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentQuestionTypeCBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLessonViewModel = new ViewModelProvider(requireActivity()).get(LessonViewModel.class);

        mNumOfCorrectPairs = 0;
        mNumOfWrongPairs = 0;
        mLeftSideSelected = false;
        mRightSideSelected = false;

//        mBinding.imageButtonLessonQuestion.setOnClickListener(this::buttonLessonQuestionClicked);
        mBinding.buttonLessonOptionA1.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionA2.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionA3.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionA4.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionA5.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionB1.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionB2.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionB3.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionB4.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOptionB5.setOnClickListener(this::buttonLessonOptionClicked);
//        mBinding.imageButtonLessonQuestion.setClipToOutline(true);


//        mBinding.textViewLessonTitle.setText(getResources().getString(R.string.lesson_title_matching_pairs));

        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.UNSELECTED);
//        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.DISABLED);
//        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.RED);
//        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.GREEN);
//        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.BLUE);

        final LiveData<String> optionA1 = mLessonViewModel.getCurrentOptionA1();
        optionA1.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionA1.setText(s);
            }
        });
        final LiveData<String> optionA2 = mLessonViewModel.getCurrentOptionA2();
        optionA2.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionA2.setText(s);
            }
        });
        final LiveData<String> optionA3 = mLessonViewModel.getCurrentOptionA3();
        optionA3.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionA3.setText(s);
            }
        });
        final LiveData<String> optionA4 = mLessonViewModel.getCurrentOptionA4();
        optionA4.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionA4.setText(s);
            }
        });
        final LiveData<String> optionA5 = mLessonViewModel.getCurrentOptionA5();
        optionA5.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionA5.setText(s);
            }
        });

        final LiveData<String> optionB1 = mLessonViewModel.getCurrentOptionB1();
        optionB1.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionB1.setText(s);
            }
        });
        final LiveData<String> optionB2 = mLessonViewModel.getCurrentOptionB2();
        optionB2.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionB2.setText(s);
            }
        });
        final LiveData<String> optionB3 = mLessonViewModel.getCurrentOptionB3();
        optionB3.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionB3.setText(s);
            }
        });
        final LiveData<String> optionB4 = mLessonViewModel.getCurrentOptionB4();
        optionB4.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionB4.setText(s);
            }
        });
        final LiveData<String> optionB5 = mLessonViewModel.getCurrentOptionB5();
        optionB5.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOptionB5.setText(s);
            }
        });




        final LiveData<Integer> buttonLessonOptionTextColorA1 = mLessonViewModel.getButtonLessonOptionTextColorA1();
        buttonLessonOptionTextColorA1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionA1.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorA2 = mLessonViewModel.getButtonLessonOptionTextColorA2();
        buttonLessonOptionTextColorA2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionA2.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorA3 = mLessonViewModel.getButtonLessonOptionTextColorA3();
        buttonLessonOptionTextColorA3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionA3.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorA4 = mLessonViewModel.getButtonLessonOptionTextColorA4();
        buttonLessonOptionTextColorA4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionA4.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorA5 = mLessonViewModel.getButtonLessonOptionTextColorA5();
        buttonLessonOptionTextColorA5.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionA5.setTextColor(val);
            }
        });



        final LiveData<Integer> buttonLessonOptionBackgroundResourceA1 = mLessonViewModel.getButtonLessonOptionBackgroundResourceA1();
        buttonLessonOptionBackgroundResourceA1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionA1.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceA2 = mLessonViewModel.getButtonLessonOptionBackgroundResourceA2();
        buttonLessonOptionBackgroundResourceA2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionA2.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceA3 = mLessonViewModel.getButtonLessonOptionBackgroundResourceA3();
        buttonLessonOptionBackgroundResourceA3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionA3.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceA4 = mLessonViewModel.getButtonLessonOptionBackgroundResourceA4();
        buttonLessonOptionBackgroundResourceA4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionA4.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceA5 = mLessonViewModel.getButtonLessonOptionBackgroundResourceA5();
        buttonLessonOptionBackgroundResourceA5.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionA5.setBackgroundResource(val);
                }
            }
        });


        final LiveData<Boolean> buttonLessonOptionEnabledA1 = mLessonViewModel.getButtonLessonOptionEnabledA1();
        buttonLessonOptionEnabledA1.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionA1.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledA2 = mLessonViewModel.getButtonLessonOptionEnabledA2();
        buttonLessonOptionEnabledA2.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionA2.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledA3 = mLessonViewModel.getButtonLessonOptionEnabledA3();
        buttonLessonOptionEnabledA3.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionA3.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledA4 = mLessonViewModel.getButtonLessonOptionEnabledA4();
        buttonLessonOptionEnabledA4.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionA4.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledA5 = mLessonViewModel.getButtonLessonOptionEnabledA5();
        buttonLessonOptionEnabledA5.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionA5.setEnabled(val);
            }
        });




        final LiveData<Integer> buttonLessonOptionTextColorB1 = mLessonViewModel.getButtonLessonOptionTextColorB1();
        buttonLessonOptionTextColorB1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionB1.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorB2 = mLessonViewModel.getButtonLessonOptionTextColorB2();
        buttonLessonOptionTextColorB2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionB2.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorB3 = mLessonViewModel.getButtonLessonOptionTextColorB3();
        buttonLessonOptionTextColorB3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionB3.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorB4 = mLessonViewModel.getButtonLessonOptionTextColorB4();
        buttonLessonOptionTextColorB4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionB4.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColorB5 = mLessonViewModel.getButtonLessonOptionTextColorB5();
        buttonLessonOptionTextColorB5.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOptionB5.setTextColor(val);
            }
        });



        final LiveData<Integer> buttonLessonOptionBackgroundResourceB1 = mLessonViewModel.getButtonLessonOptionBackgroundResourceB1();
        buttonLessonOptionBackgroundResourceB1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionB1.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceB2 = mLessonViewModel.getButtonLessonOptionBackgroundResourceB2();
        buttonLessonOptionBackgroundResourceB2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionB2.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceB3 = mLessonViewModel.getButtonLessonOptionBackgroundResourceB3();
        buttonLessonOptionBackgroundResourceB3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionB3.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceB4 = mLessonViewModel.getButtonLessonOptionBackgroundResourceB4();
        buttonLessonOptionBackgroundResourceB4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionB4.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResourceB5 = mLessonViewModel.getButtonLessonOptionBackgroundResourceB5();
        buttonLessonOptionBackgroundResourceB5.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOptionB5.setBackgroundResource(val);
                }
            }
        });


        final LiveData<Boolean> buttonLessonOptionEnabledB1 = mLessonViewModel.getButtonLessonOptionEnabledB1();
        buttonLessonOptionEnabledB1.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionB1.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledB2 = mLessonViewModel.getButtonLessonOptionEnabledB2();
        buttonLessonOptionEnabledB2.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionB2.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledB3 = mLessonViewModel.getButtonLessonOptionEnabledB3();
        buttonLessonOptionEnabledB3.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionB3.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledB4 = mLessonViewModel.getButtonLessonOptionEnabledB4();
        buttonLessonOptionEnabledB4.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionB4.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabledB5 = mLessonViewModel.getButtonLessonOptionEnabledB5();
        buttonLessonOptionEnabledB5.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOptionB5.setEnabled(val);
            }
        });
    }

//    private int mSelectedOptionLeft = 0;
//    private int mSelectedOptionRight = 0;

    private LessonViewModel mLessonViewModel;
    private FragmentQuestionTypeCBinding mBinding;

    private int mNumOfCorrectPairs = 0;
    private int mNumOfWrongPairs = 0;
    private boolean mLeftSideSelected = false;
    private boolean mRightSideSelected = false;

    private static final String TAG = QuestionTypeCFragment.class.getSimpleName();
}
