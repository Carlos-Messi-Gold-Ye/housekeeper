package com.housekeeper.core.log.action;

import java.util.Date;

/**
 * @author yezy
 * @since 2018/4/18
 */
public class ActionLogHolder {

    private ActionLogHolder() {
    }

    private static final ActionLogHolder INSTANCE = new ActionLogHolder();

    public static ActionLogHolder get() {
        return INSTANCE;
    }

    private static final ThreadLocal<ActionLog> LOG_HOLDER = new ThreadLocal<>();

    public void initialize() {
        LOG_HOLDER.set(new ActionLog(new Date()));
    }

    public ActionLog currentActionLog() {
        return LOG_HOLDER.get();
    }

    public void clear() {
        ActionLog actionLog = currentActionLog();
        LOG_HOLDER.remove();
        ActionLog.LOGGER.info("{}", actionLog);
    }

    public ActionLogHolder appendLog(String key, String value) {
        ActionLog actionLog = LOG_HOLDER.get();
        actionLog.getAdditionalContext().put(key, value);
        return this;
    }
}
