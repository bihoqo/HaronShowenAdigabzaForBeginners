package com.mastegoane.adigabzaforbeginners.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mastegoane.adigabzaforbeginners.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StagesViewPagerAdapter extends RecyclerView.Adapter<StagesViewPagerAdapter.ViewPagerViewHolder> {

    private final Context mContext;
    private final List<Integer> mStageNumbers;
    private final List<String> mStageTitles;
    private final Map<String, String> mStageLesson2SubTitle;
    private final List<Integer> mStageNumOfLessons;
    private Map<String, Boolean> mStageLesson2IsActivated;
    private Map<String, Integer> mStageLesson2NumStars;
    private Map<String, Integer> mStageLessonExercise2IsDone;

    public StagesViewPagerAdapter(Context context,
                                  List<Integer> stageNumbers,
                                  List<String> stageTitles,
                                  Map<String, String> stageLesson2SubTitle,
                                  List<Integer> stageNumOfLessons,
                                  Map<String, Boolean> stageLesson2IsActivated,
                                  Map<String, Integer> stageLesson2NumStars,
                                  Map<String, Integer> stageLessonExercise2IsDone) {
        mContext = context;
        mStageNumbers = stageNumbers;
        mStageTitles = stageTitles;
        mStageLesson2IsActivated = stageLesson2IsActivated;
        mStageLesson2NumStars = stageLesson2NumStars;
        mStageLessonExercise2IsDone = stageLessonExercise2IsDone;
        mStageLesson2SubTitle = stageLesson2SubTitle;
        mStageNumOfLessons = stageNumOfLessons;
    }

    public void updateLessonStatus(Map<String, Boolean> stageLesson2IsActivated) {
        mStageLesson2IsActivated = stageLesson2IsActivated;
    }

    public void updateExerciseStatus(Map<String, Integer> stageLessonExercise2IsDone) {
        mStageLessonExercise2IsDone = stageLessonExercise2IsDone;
    }

    public void updateStarsStatus(Map<String, Integer> stageLesson2NumStars) {
        mStageLesson2NumStars = stageLesson2NumStars;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.view_pager_item_stage, parent, false);
        if (view == null) {
            return null;
        }
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        if (mStageNumbers == null || mStageTitles == null || mStageLesson2IsActivated == null || mStageLesson2NumStars == null ||
                mStageLessonExercise2IsDone == null || mStageLesson2SubTitle == null ||
                mStageNumOfLessons == null || position >= mStageNumbers.size()) {
            return;
        }
        final int numOfLessons = mStageNumOfLessons.get(position);
        final String stageNumber = "" + mStageNumbers.get(position);

        for(int lessonIndex = 1; lessonIndex <= 10; ++lessonIndex) {
            ConstraintLayout constraintViewPagerItemLesson = holder.mConstraintViewPagerItemLessonList.get(lessonIndex-1);
            if (constraintViewPagerItemLesson != null) {
                if (numOfLessons < lessonIndex) {
                    constraintViewPagerItemLesson.setVisibility(View.GONE);
                } else {
                    constraintViewPagerItemLesson.setVisibility(View.VISIBLE);
                    TextView textViewPagerItemSubTitle = holder.mTextViewPagerItemSubTitleList.get(lessonIndex-1);
                    if (textViewPagerItemSubTitle != null) {
                        String lessonSubtitle = mStageLesson2SubTitle.get(stageNumber + "," + lessonIndex);
                        if (lessonSubtitle == null) {
                            lessonSubtitle = mStageTitles.get(position) + " " + lessonIndex;
                        }
                        textViewPagerItemSubTitle.setText(lessonSubtitle);
                    }
                    ImageView imageViewLock = holder.mImageViewLockList.get(lessonIndex-1);
                    if (imageViewLock != null) {
                        final Boolean isActivated = mStageLesson2IsActivated.get(stageNumber + "," + lessonIndex);
                        if (isActivated != null && isActivated) {
                            imageViewLock.setVisibility(View.INVISIBLE);
                        } else {
                            if (!GlobalSettings.inst().getUnlockAllLessons()) {
                                imageViewLock.setVisibility(View.VISIBLE);
                                imageViewLock.setAlpha(1.0f);
                            } else {
                                imageViewLock.setVisibility(View.VISIBLE);
                                imageViewLock.setAlpha(0.3f);
                            }
                        }
                    }

                    LinearLayout linearLayoutIndicators = holder.mLinearLayoutIndicatorsList.get(lessonIndex-1);
                    if (linearLayoutIndicators != null) {
                        for (int indicatorIndex = 1; indicatorIndex <= linearLayoutIndicators.getChildCount(); ++indicatorIndex) {
                            final Integer isDone = mStageLessonExercise2IsDone.get(stageNumber + "," + lessonIndex + "," + indicatorIndex);
                            ImageView imageViewIndicator = (ImageView)(linearLayoutIndicators.getChildAt(indicatorIndex-1));
                            if (imageViewIndicator != null) {
                                if (isDone == null || isDone == -1) {
                                    imageViewIndicator.setVisibility(View.GONE);
                                } else {
                                    imageViewIndicator.setVisibility(View.VISIBLE);
                                    if (isDone == 1) {
                                        imageViewIndicator.setActivated(true);
                                    } else {
                                        imageViewIndicator.setActivated(false);
                                    }
                                }
                            }
                        }
                    }

                    final Integer numStars = mStageLesson2NumStars.get(stageNumber + "," + lessonIndex);
                    ImageView imageViewStar = holder.mImageViewStarList.get(lessonIndex-1);
                    if (imageViewStar != null) {
                        if (numStars != null && numStars > 0) {
                            imageViewStar.setVisibility(View.VISIBLE);
                        } else {
                            imageViewStar.setVisibility(View.INVISIBLE);
                        }
                    }

                    TextView textViewNumStars = holder.mTextViewNumStarsList.get(lessonIndex-1);
                    if (textViewNumStars != null) {
                        if (numStars != null && numStars > 0) {
                            textViewNumStars.setVisibility(View.VISIBLE);
                            textViewNumStars.setText("" + numStars);
                        } else {
                            textViewNumStars.setVisibility(View.INVISIBLE);
                        }
                    }


                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mStageNumbers == null) {
            return 0;
        }
        return mStageNumbers.size();
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);

            mConstraintViewPagerItemLessonList = new ArrayList<>();
            mTextViewPagerItemSubTitleList = new ArrayList<>();
            mImageViewLockList = new ArrayList<>();
            mLinearLayoutIndicatorsList = new ArrayList<>();
            mImageViewStarList = new ArrayList<>();
            mTextViewNumStarsList = new ArrayList<>();

            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson1));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson2));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson3));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson4));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson5));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson6));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson7));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson8));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson9));
            mConstraintViewPagerItemLessonList.add(itemView.findViewById(R.id.constraintViewPagerItemLesson10));

            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle1));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle2));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle3));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle4));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle5));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle6));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle7));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle8));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle9));
            mTextViewPagerItemSubTitleList.add(itemView.findViewById(R.id.textViewPagerItemSubTitle10));

            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock1));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock2));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock3));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock4));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock5));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock6));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock7));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock8));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock9));
            mImageViewLockList.add(itemView.findViewById(R.id.imageViewPagerItemLock10));

            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators1));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators2));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators3));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators4));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators5));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators6));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators7));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators8));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators9));
            mLinearLayoutIndicatorsList.add(itemView.findViewById(R.id.linearLayoutIndicators10));

            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar1));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar2));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar3));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar4));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar5));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar6));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar7));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar8));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar9));
            mImageViewStarList.add(itemView.findViewById(R.id.imageViewStar10));

            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars1));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars2));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars3));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars4));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars5));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars6));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars7));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars8));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars9));
            mTextViewNumStarsList.add(itemView.findViewById(R.id.textViewNumStars10));
        }

        List<ConstraintLayout> mConstraintViewPagerItemLessonList;
        List<TextView> mTextViewPagerItemSubTitleList;
        List<ImageView> mImageViewLockList;
        List<LinearLayout> mLinearLayoutIndicatorsList;
        List<ImageView> mImageViewStarList;
        List<TextView> mTextViewNumStarsList;
    }

    private static final String TAG = StagesViewPagerAdapter.class.getSimpleName();
}
