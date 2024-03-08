package com.jobs.im.model.bean;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ChatMessageResult
 * @description:
 * @author: Author
 * @create: 2024-02-27 16:17
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResult {
    String name;

    String nickName;

    LocalDateTime time;

    String message;
}