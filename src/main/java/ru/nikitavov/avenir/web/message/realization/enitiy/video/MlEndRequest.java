package ru.nikitavov.avenir.web.message.realization.enitiy.video;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MlEndRequest(String data, @JsonProperty("task_id") Integer taskId) {

}
