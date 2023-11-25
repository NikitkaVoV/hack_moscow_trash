package ru.nikitavov.avenir.web.controller.rest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitavov.avenir.database.model.entity.Camera;
import ru.nikitavov.avenir.database.model.entity.Route;
import ru.nikitavov.avenir.database.repository.realisation.CameraOffenseRepository;
import ru.nikitavov.avenir.database.repository.realisation.RouteRepository;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.enitiy.FindsRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PageableResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PointResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.route.RouteInfoResponse;
import ru.nikitavov.avenir.web.util.SortUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("route")
public class RouteController {

    ExampleMatcher cameraMatcher = ExampleMatcher.matchingAny()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    private final CameraOffenseRepository cameraOffenseRepository;
    private final RouteRepository routeRepository;

    @GetMapping()
    public ResponseEntity<MessageWrapper<PageableResponse<List<RouteInfoResponse>>>> getViolations(FindsRequest request) {
        Route routeExample = Route.builder().name(request.filter()).build();
        Example<Route> example = Example.of(routeExample, cameraMatcher);
        Page<Route> cameras = routeRepository.findAll(example,
                PageRequest.of(request.page(), request.size(), SortUtil.parseSort(request.sort())));

        List<RouteInfoResponse> list = cameras.get()
                .map(route -> new RouteInfoResponse(route.getId(), route.getName(), route.getStatus().getName(), route.getCameras().size(),
                                route.getDateDeparture(), route.getDateArrival(), route.getWasteType().name().toLowerCase()
                        , route.getPoints().stream().map(point -> new PointResponse(point.getName(), new Coordinate(point.getLat(), point.getLon()))).toArray(PointResponse[]::new),
                        route.getCameras().stream().mapToInt(Camera::getId).toArray())
                )
                .toList();

        return ResponseEntity.ok(MessageWrapper.create(new PageableResponse<>(list, cameras.getTotalPages(), cameras.getTotalElements())));


    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWrapper<RouteInfoResponse>> getCamera(@PathVariable Integer id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (routeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Route route = routeOptional.get();

        RouteInfoResponse routeInfoResponse = new RouteInfoResponse(route.getId(), route.getName(), route.getStatus().getName(), route.getCameras().size(),
                route.getDateDeparture(), route.getDateArrival(), route.getWasteType().name().toLowerCase()
                , route.getPoints().stream().map(point -> new PointResponse(point.getName(), new Coordinate(point.getLat(), point.getLon()))).toArray(PointResponse[]::new),
                route.getCameras().stream().mapToInt(Camera::getId).toArray());

        return ResponseEntity.ok(new MessageWrapper<>(routeInfoResponse));
    }
}
