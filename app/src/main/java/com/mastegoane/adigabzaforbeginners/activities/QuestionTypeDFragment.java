package com.mastegoane.adigabzaforbeginners.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mastegoane.adigabzaforbeginners.R;
import com.mastegoane.adigabzaforbeginners.databinding.FragmentQuestionTypeDBinding;
import com.mastegoane.adigabzaforbeginners.utils.RoundedBackgroundSpan;
import com.mastegoane.adigabzaforbeginners.utils.SoundPlayer;
import com.mastegoane.adigabzaforbeginners.viewmodels.LessonViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionTypeDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionTypeDFragment extends Fragment {

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
        int selectedOptionId;
        final int buttonId = view.getId();
        if (buttonId == R.id.buttonLessonOption1) {
            selectedOptionId = 1;
        } else if (buttonId == R.id.buttonLessonOption2) {
            selectedOptionId = 2;
        } else if (buttonId == R.id.buttonLessonOption3) {
            selectedOptionId = 3;
        } else if (buttonId == R.id.buttonLessonOption4) {
            selectedOptionId = 4;
        } else {
            Log.e(TAG, "-B- BUG: Unexpected button ID: " + buttonId);
            return;
        }

        int questionTypeInt = 0;
        final Integer questionType = mLessonViewModel.getCurrentQuestionType().getValue();
        if (questionType != null) {
            questionTypeInt = questionType;
        }

        int lastSelectedOptionId = mLessonViewModel.getLastSelectedOptionId();
        if (selectedOptionId == lastSelectedOptionId) {
            if (questionTypeInt == 2) {
                SoundPlayer.getInstance().playSound("AllLessons", mLessonViewModel.getSelectedOptionSound());
            }
            return;
        }
        mLessonViewModel.setSelectedAnswerText(((TextView)view).getText().toString());
        mLessonViewModel.setSelectedAnswerId(selectedOptionId);

        mLessonViewModel.setButtonLessonOptionState(selectedOptionId, LessonViewModel.ButtonOptionState.BLUE, false);
        if (lastSelectedOptionId != 0) {
            mLessonViewModel.setButtonLessonOptionState(lastSelectedOptionId, LessonViewModel.ButtonOptionState.UNSELECTED, false);
        }

        mLessonViewModel.setLastSelectedOptionId(selectedOptionId);
        mLessonViewModel.setSelectedOptionId(selectedOptionId);
        if (questionTypeInt == 2) {
            SoundPlayer.getInstance().playSound("AllLessons", mLessonViewModel.getSelectedOptionSound());
        }
        mLessonViewModel.setSelectButtonEnabled(true);
        mLessonViewModel.setSelectButtonActivated(true);
        mLessonViewModel.setSelectButtonBackgroundResource(R.drawable.background_select);
        mLessonViewModel.setSelectButtonTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.white));
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionTypeDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionTypeDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionTypeDFragment newInstance(String param1, String param2) {
        QuestionTypeDFragment fragment = new QuestionTypeDFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentQuestionTypeDBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLessonViewModel = new ViewModelProvider(requireActivity()).get(LessonViewModel.class);
        mBinding.imageButtonLessonQuestion.setOnClickListener(this::buttonLessonQuestionClicked);
        mBinding.buttonLessonOption1.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOption2.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOption3.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.buttonLessonOption4.setOnClickListener(this::buttonLessonOptionClicked);
        mBinding.imageButtonLessonQuestion.setClipToOutline(true);

        mLessonViewModel.setAllButtonLessonOptionStates(LessonViewModel.ButtonOptionState.UNSELECTED);


//        //YYY1
//        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
//        String between = "";
//        List<String> words = new ArrayList<String>(Arrays.asList("abc", "deff", "ghi", "klmnof"));
////        for (String tag : eventListing.getTags()) {
//        for (String tag : words) {
//           stringBuilder.append(between);
//           if (between.length() == 0) {
//               between = "  ";
//           }
//           String thisTag = "  " + tag + "  ";
//           stringBuilder.append(thisTag);
//           stringBuilder.setSpan(new RoundedBackgroundSpan(requireActivity().getApplicationContext()), stringBuilder.length() - thisTag.length(), stringBuilder.length() - thisTag.length() + thisTag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////           stringBuilder.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.gray)), stringBuilder.length() - thisTag.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//
////        TextView tv = new TextView(requireActivity().getApplicationContext());
////        tv.setText(stringBuilder);
//        mBinding.textViewTextSpanExample.setText(stringBuilder);
//        //YYY1


        final LiveData<Integer> questionTypeLD = mLessonViewModel.getCurrentQuestionType();
        questionTypeLD.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer questionType) {
                switch (questionType) {
                    case 1:
                        mBinding.textViewLessonTitle.setText(getResources().getString(R.string.lesson_title_what_you_hear));
                        break;
                    case 2:
                    case 3:
                        mBinding.textViewLessonTitle.setText(getResources().getString(R.string.lesson_title_what_is_this));
                        break;
                    default:
                        Log.e(TAG, "-B- BUG: Unexpected question type in fragment D: " + questionType);
                        mBinding.textViewLessonTitle.setText(getResources().getString(R.string.lesson_title_what_is_this));
                        break;
                }
            }
        });

        final LiveData<String> questionImage = mLessonViewModel.getCurrentQuestionImage();
        questionImage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.isEmpty()) {
                    mBinding.imageButtonLessonQuestion.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.imageButtonLessonQuestion.setVisibility(View.VISIBLE);
                    mBinding.imageButtonLessonQuestion.setImageBitmap(mLessonViewModel.getBitmapFromAssets(s));
                }
            }
        });

        final LiveData<String> option1 = mLessonViewModel.getCurrentOption1();
        option1.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOption1.setText(s);
            }
        });
        final LiveData<String> option2 = mLessonViewModel.getCurrentOption2();
        option2.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOption2.setText(s);
            }
        });
        final LiveData<String> option3 = mLessonViewModel.getCurrentOption3();
        option3.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOption3.setText(s);
            }
        });
        final LiveData<String> option4 = mLessonViewModel.getCurrentOption4();
        option4.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.buttonLessonOption4.setText(s);
            }
        });

        final LiveData<Integer> buttonLessonOptionTextColor1 = mLessonViewModel.getButtonLessonOptionTextColor1();
        buttonLessonOptionTextColor1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOption1.setTextColor(val);

            }
        });
        final LiveData<Integer> buttonLessonOptionTextColor2 = mLessonViewModel.getButtonLessonOptionTextColor2();
        buttonLessonOptionTextColor2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOption2.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColor3 = mLessonViewModel.getButtonLessonOptionTextColor3();
        buttonLessonOptionTextColor3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOption3.setTextColor(val);
            }
        });
        final LiveData<Integer> buttonLessonOptionTextColor4 = mLessonViewModel.getButtonLessonOptionTextColor4();
        buttonLessonOptionTextColor4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                mBinding.buttonLessonOption4.setTextColor(val);
            }
        });

        final LiveData<Integer> buttonLessonOptionBackgroundResource1 = mLessonViewModel.getButtonLessonOptionBackgroundResource1();
        buttonLessonOptionBackgroundResource1.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOption1.setBackgroundResource(val);
                }

            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResource2 = mLessonViewModel.getButtonLessonOptionBackgroundResource2();
        buttonLessonOptionBackgroundResource2.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOption2.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResource3 = mLessonViewModel.getButtonLessonOptionBackgroundResource3();
        buttonLessonOptionBackgroundResource3.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOption3.setBackgroundResource(val);
                }
            }
        });
        final LiveData<Integer> buttonLessonOptionBackgroundResource4 = mLessonViewModel.getButtonLessonOptionBackgroundResource4();
        buttonLessonOptionBackgroundResource4.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {
                if (val > 0) {
                    mBinding.buttonLessonOption4.setBackgroundResource(val);
                }
            }
        });


        final LiveData<Boolean> buttonLessonOptionEnabled1 = mLessonViewModel.getButtonLessonOptionEnabled1();
        buttonLessonOptionEnabled1.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOption1.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabled2 = mLessonViewModel.getButtonLessonOptionEnabled2();
        buttonLessonOptionEnabled2.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOption2.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabled3 = mLessonViewModel.getButtonLessonOptionEnabled3();
        buttonLessonOptionEnabled3.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOption3.setEnabled(val);
            }
        });
        final LiveData<Boolean> buttonLessonOptionEnabled4 = mLessonViewModel.getButtonLessonOptionEnabled4();
        buttonLessonOptionEnabled4.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean val) {
                mBinding.buttonLessonOption4.setEnabled(val);
            }
        });
    }

    private LessonViewModel mLessonViewModel;
    private FragmentQuestionTypeDBinding mBinding;

    private static final String TAG = QuestionTypeDFragment.class.getSimpleName();
}
