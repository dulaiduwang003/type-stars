package com.cn.app.net;

import com.alibaba.fastjson.JSONObject;
import com.cn.app.constant.ChatStatusConstant;
import com.cn.app.dto.AiRequestDto;
import com.cn.app.exceptions.SocketColoseException;
import com.cn.app.service.strategy.AIInteractionModeStrategy;
import com.cn.app.service.strategy.AIInteractionModeStrategyFactory;
import com.cn.app.utils.SpringContextUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Dialogue socket.
 *
 * @author bdth github @dulaiduwang003
 * @version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/socket/{token}", subprotocols = {"protocol"})
@SuppressWarnings("all")
@Service
public class DialogueSocket {
    private Session session;

    private AIInteractionModeStrategyFactory factory;
    private int maxSize = 10 * 1024 * 1024;

    @OnOpen
    public void onOpen(final Session session, @PathParam("token") final String token) {
        //设置传输缓存流
        if (session.getMaxTextMessageBufferSize() <= 8192) {
            session.setMaxBinaryMessageBufferSize(maxSize);
            session.setMaxTextMessageBufferSize(maxSize);
        }
        this.session = session;
        if (factory == null) {
            factory = (AIInteractionModeStrategyFactory) SpringContextUtil.getBean("AIInteractionModeStrategyFactory");
        }

    }

    @OnMessage
    public void onMessage(final String parameter) {

        try {
            final AiRequestDto aiRequestDto = JSONObject.parseObject(parameter, AiRequestDto.class);
            //获取映射方法
            final AIInteractionModeStrategy impl = factory.getImpl(aiRequestDto.getType());
            impl.execution(aiRequestDto)
                    .doFinally(signal -> handleWebSocketCompletion())
                    .subscribe(s -> {
                        try {
                            this.session.getBasicRemote().sendText(s);
                        } catch (Exception e) {
                            throw new SocketColoseException();
                        }
                    }, throwable -> {
                        //为 Close异常时 过滤
                        if (!(throwable instanceof SocketColoseException)) {
                            sendErrorMessages(ChatStatusConstant.ERROR);
                        }
                    });

        } catch (Exception e) {
            sendErrorMessages(ChatStatusConstant.ERROR);
        }

    }

    private void sendErrorMessages(final String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
            handleWebSocketCompletion();
        } catch (Exception e) {

        }
    }

    @OnClose
    public void handleWebSocketCompletion() {
        try {
            this.session.close();
        } catch (IOException e) {
            log.error("关闭 WebSocket 会话失败.", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.warn("GPT websocket出现异常 原因:{}", throwable.getMessage());
        //打印堆栈
        //      throwable.printStackTrace();
    }
}
