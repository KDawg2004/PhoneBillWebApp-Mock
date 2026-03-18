package edu.pdx.cs.joy.kbarta;

import edu.pdx.cs.joy.AbstractPhoneCall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The PhoneCall class for the Phone Bill Project
 * stores information about the caller, callee, begin time, and end time in an
 * object named PhoneCall
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{

    /**
     * The Person who made the call
     */
    private final String caller;
    /**
     * the person who was called by the caller
     */
    private final String callee;
    /**
     *The time the call started
     */
    private final LocalDateTime beginTime;
    /**
     * The time the call ended
     */
    private final LocalDateTime endTime;

    /**
     *Constructor for a Phone Call obj
     * assigns caller, callee, begin time, and endtime
     * @param caller caller phone number
     * @param callee callee phone number
     * @param beginTime start date of the call
     * @param endTime end date and time of the call
     */
    public PhoneCall(String caller, String callee, LocalDateTime beginTime, LocalDateTime  endTime){
        if (endTime.isBefore(beginTime)) {
            throw new IllegalArgumentException("End time cannot be before begin time");
        }

        this.caller = caller;
        this.callee = callee;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /**
     * This retrives caller info
     * @return string of caller phone num/name
     */
    @Override
    public String getCaller() {
        return this.caller;
    }

    /**
     * retrives info about who was called
     * @return string of the callee
     */
    @Override
    public String getCallee() {
        return this.callee;
    }

    /**
     * retrives the start time for the call
     * @return string with start time
     */
    @Override
    public String getBeginTimeString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return this.beginTime.format(formatter);
    }

    /**
     *gets end time of call
     * @return string with end time
     */
    @Override
    public String getEndTimeString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return this.endTime.format(formatter);
    }

    @Override
    public LocalDateTime getBeginTime() {
        return this.beginTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    public int compareTo(PhoneCall other) {
        int cmp = this.getBeginTime().compareTo(other.getBeginTime());
        if (cmp != 0) return cmp;

        cmp = this.getCaller().compareTo(other.getCaller());
        if (cmp != 0) return cmp;

        return this.getCallee().compareTo(other.getCallee());
    }
}
