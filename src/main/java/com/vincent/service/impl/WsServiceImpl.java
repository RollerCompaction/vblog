package com.vincent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vincent.entity.UserMessage;
import com.vincent.service.UserMessageService;
import com.vincent.service.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * WebSocket服务实现类
 */
@Service
public class WsServiceImpl implements WsService {
    @Autowired
    UserMessageService messageService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Async
    @Override
    public void sendMessCountToUser(Long toUserId) {
        int count = messageService.count(new QueryWrapper<UserMessage>()
                .eq("to_user_id", toUserId)
                .eq("status", "0")
        );

        //websocket通知（/user/1/messCount）
        messagingTemplate.convertAndSendToUser(toUserId.toString(), "/messCount", count);
    }
}
