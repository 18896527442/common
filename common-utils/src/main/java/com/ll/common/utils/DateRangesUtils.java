package com.ll.common.utils;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateRangesUtils {

    public static Boolean overlap(List<DateRange> ranges) {
        ImmutableList<DateRange> dateRanges = ImmutableList.sortedCopyOf(Comparator.comparing(DateRange::getStart), ranges);
        return checkOverlap(dateRanges);
    }

    private static Boolean checkOverlap(ImmutableList<DateRange> dateRanges) {
        for (int i = 0; i < dateRanges.size() - 1; i++) {
            if (overlap(dateRanges, i))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static boolean overlap(ImmutableList<DateRange> dateRanges, int i) {
        return !dateRanges.get(i).getEnd().before(dateRanges.get(i + 1).getStart());
    }

    @Builder
    @Data
    public static class DateRange {

        private Date start;

        private Date end;
    }
}
