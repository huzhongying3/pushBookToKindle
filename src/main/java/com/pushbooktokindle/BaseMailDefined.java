package com.pushbooktokindle;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @auth:huzhongying
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BaseMailDefined {
    protected String to;
    protected String from;
    protected String subject;
    protected String text;

    public String getText() {
        return text;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
