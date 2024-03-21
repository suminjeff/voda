package io.watssuggang.voda.diary.controller;

import io.watssuggang.voda.common.security.annotation.CurrentUser;
import io.watssuggang.voda.common.security.dto.SecurityUserDto;
import io.watssuggang.voda.diary.dto.req.TalkListRequest;
import io.watssuggang.voda.diary.dto.res.DiaryChatResponseDto;
import io.watssuggang.voda.diary.dto.res.DiaryDetailResponse;
import io.watssuggang.voda.diary.service.DiaryService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    public final DiaryService diaryService;

    @GetMapping("/init")
    public ResponseEntity<?> init() throws Exception {
        DiaryChatResponseDto result = diaryService.init();
        return ResponseEntity.ok(result.getContent().get(0).getText());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDiary(@RequestBody TalkListRequest talkList) {
        System.out.println("일기 생성 컨트롤러 들어옴!!!");
        diaryService.createDiary(talkList.getTalk_list(), talkList.getDiaryId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/talk/{id}")
    public ResponseEntity<Map<String, Object>> getTalkList(@PathVariable int id) {
        System.out.println("채팅 리스트 받기");
        Map<String, Object> chatList = diaryService.getChatList(id);

        return ResponseEntity.ok(chatList);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(@CurrentUser SecurityUserDto userDto,
        @PathVariable int id) {
        System.out.println("일기 상세 정보 받기");

        DiaryDetailResponse diaryDetailResponse = diaryService.getDiaryDetail(userDto.getMemberId(),
            id);

        return ResponseEntity.ok(diaryDetailResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DiaryDetailResponse>> getList(
        @RequestParam(defaultValue = "") LocalDateTime start,
        @RequestParam(defaultValue = "") LocalDateTime end,
        @RequestParam(defaultValue = "NONE") String emotion,
        @CurrentUser SecurityUserDto securityUserDto) {

        List<DiaryDetailResponse> diaryList = diaryService.getDiaryList(start, end, emotion,
            securityUserDto.getMemberId());

        return ResponseEntity.ok(diaryList);
    }

}
