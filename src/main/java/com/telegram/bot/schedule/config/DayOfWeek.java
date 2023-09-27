package com.telegram.bot.schedule.config;

public enum DayOfWeek {
    MONDAY ("ПН"),
    TUESDAY ("ВТ"),
    WEDNESDAY ("СР"),
    THURSDAY ("ЧТ"),
    FRIDAY ("ПТ"),
    SATURDAY ("СБ");

    private String title;

    DayOfWeek(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
                "title='" + title + '\'' +
                '}';
    }
}
