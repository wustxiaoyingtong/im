package com.jobs.im.model.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ChatCommand
 * @description:
 * @author: Author
 * @create: 2024-02-27 15:40
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCommand implements Serializable {
    // 连接信息编码
    int code;

    // 昵称
    String nickName;
}
