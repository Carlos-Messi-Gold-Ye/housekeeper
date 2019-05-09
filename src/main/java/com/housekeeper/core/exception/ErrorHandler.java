package com.housekeeper.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.housekeeper.core.log.action.ActionLog;
import com.housekeeper.core.log.action.ActionLogHolder;

/**
 * @author yezy
 * @since 2019/1/21
 */
public class ErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public void handle(Throwable throwable) {
        if (null == throwable) {
            return;
        }
        logger.error(throwable.getMessage(), throwable);
        ActionLog actionLog = ActionLogHolder.get().currentActionLog();
        actionLog.setStatus(ActionLog.Status.ERROR);
        actionLog.setErrorMsg(StringUtils.substring(throwable.getMessage(), 0, 500));
    }
}
