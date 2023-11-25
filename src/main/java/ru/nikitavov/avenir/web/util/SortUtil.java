package ru.nikitavov.avenir.web.util;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Objects;

public class SortUtil {

    public static Sort parseSort(String[] orderRows) {
        if (orderRows == null || orderRows.length == 0) return Sort.by("id");

        return Sort.by(Arrays.stream(orderRows).map(SortUtil::parseOrder).filter(Objects::nonNull).toList());
    }

        public static Sort.Order parseOrder(String orderRow) {
        if (!orderRow.contains("|")) {
            return null;
        }
        String[] split = orderRow.split("\\|");

        String type = split[1];
        if (type.equalsIgnoreCase("ASC")) return Sort.Order.asc(split[0]);
        if (type.equalsIgnoreCase("DESC")) return Sort.Order.desc(split[0]);
        return null;
    }
}
