package ru.nikitavov.avenir.web.message.realization.enitiy.misc;

import ru.nikitavov.avenir.web.message.intefaces.IResponse;

public record PageableResponse<R>(R response, long pages, long totalElements) implements IResponse {
}
