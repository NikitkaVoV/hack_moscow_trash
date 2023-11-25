package ru.nikitavov.avenir.web.message.realization.enitiy.garbage;

import ru.nikitavov.avenir.web.message.intefaces.IResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.enums.GarbageType;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;

import java.util.Date;

public record GarbageInfoRequest(int id, String name, Date dateCreation, String photo, Coordinate coordinates,
                                 GarbageType garbageType) implements IResponse {
}
