package io.watssuggang.voda.diary.repository;

import static io.watssuggang.voda.diary.domain.QDiary.diary;
import static io.watssuggang.voda.diary.domain.QDiaryFile.diaryFile;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.watssuggang.voda.common.enums.Emotion;
import io.watssuggang.voda.diary.domain.Diary;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiaryCustomRepositoryImpl implements DiaryCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Diary> findDiariesByCondition(LocalDateTime start, LocalDateTime end,
            String emotion, int memberId) {

        return queryFactory
                .selectFrom(diary)
                .innerJoin(diary.diaryFiles, diaryFile).fetchJoin()
                .where(diary.member.memberId.eq(memberId), eqEmotion(emotion), goeStartDate(start),
                        loeEndDate(end))
                .orderBy(diary.modifiedAt.desc())
                .fetch();
    }

    private BooleanExpression goeStartDate(LocalDateTime start) {
        if (start == null) {
            return null;
        }
        return diary.createdAt.goe(start);
    }

    private BooleanExpression loeEndDate(LocalDateTime end) {
        if (end == null) {
            return null;
        }
        return diary.createdAt.loe(end);
    }

    private BooleanExpression eqEmotion(String emotion) {
        if (emotion.equals("NONE")) {
            return null;
        }
        return diary.diaryEmotion.eq(Emotion.valueOf(emotion));
    }
}


