package ru.nikitavov.avenir.web.message.realization.enitiy.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ERouteStatus {
    EN_ROUTE("enroute"), ERROR("error"), CHECKED("checked"), READY_TO("readyto");

    private final String name;

}
