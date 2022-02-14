package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class BanLength {

    @SerializedName("ticks")
    int ticks;

    @SerializedName("days")
    int days;

    @SerializedName("hours")
    int hours;

    @SerializedName("milliseconds")
    int milliseconds;

    @SerializedName("minutes")
    int minutes;

    @SerializedName("seconds")
    int seconds;

    @SerializedName("totalDays")
    int totalDays;

    @SerializedName("totalHours")
    int totalHours;

    @SerializedName("totalMilliseconds")
    int totalMilliseconds;

    @SerializedName("totalMinutes")
    int totalMinutes;

    @SerializedName("totalSeconds")
    int totalSeconds;

    public BanLength(int ticks, int days, int hours, int milliseconds, int minutes, int seconds, int totalDays, int totalHours, int totalMilliseconds, int totalMinutes, int totalSeconds) {
        this.ticks = ticks;
        this.days = days;
        this.hours = hours;
        this.milliseconds = milliseconds;
        this.minutes = minutes;
        this.seconds = seconds;
        this.totalDays = totalDays;
        this.totalHours = totalHours;
        this.totalMilliseconds = totalMilliseconds;
        this.totalMinutes = totalMinutes;
        this.totalSeconds = totalSeconds;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getTotalMilliseconds() {
        return totalMilliseconds;
    }

    public void setTotalMilliseconds(int totalMilliseconds) {
        this.totalMilliseconds = totalMilliseconds;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }
}
