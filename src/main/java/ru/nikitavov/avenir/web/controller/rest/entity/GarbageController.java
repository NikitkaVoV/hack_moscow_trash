package ru.nikitavov.avenir.web.controller.rest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikitavov.avenir.database.model.entity.Garbage;
import ru.nikitavov.avenir.database.model.entity.Point;
import ru.nikitavov.avenir.database.repository.realisation.GarbageRepository;
import ru.nikitavov.avenir.database.repository.realisation.PointRepository;
import ru.nikitavov.avenir.database.repository.realisation.UserRepository;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.enitiy.FindsRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.garbage.GarbageInfoRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.garbage.GarbageInfoResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PageableResponse;
import ru.nikitavov.avenir.web.util.SortUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("garbage")
public class GarbageController {

    ExampleMatcher cameraMatcher = ExampleMatcher.matchingAny()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    private final UserRepository userRepository;
    private final GarbageRepository garbageRepository;
    private final PointRepository pointRepository;

    @GetMapping()
    public ResponseEntity<MessageWrapper<PageableResponse<List<GarbageInfoResponse>>>> getViolations(FindsRequest request) {
        Garbage routeExample = Garbage.builder().name(request.filter()).build();
        Example<Garbage> example = Example.of(routeExample, cameraMatcher);
        Page<Garbage> cameras = garbageRepository.findAll(example,
                PageRequest.of(request.page(), request.size(), SortUtil.parseSort(request.sort())));

        List<GarbageInfoResponse> list = cameras.get()
                .map(garbage -> new GarbageInfoResponse(garbage.getId(), garbage.getName(), garbage.getDateCreation(), garbage.getPhoto(), new Coordinate(garbage.getPoint().getLat(), garbage.getPoint().getLon()), garbage.getGarbageType())
                )
                .toList();

        return ResponseEntity.ok(MessageWrapper.create(new PageableResponse<>(list, cameras.getTotalPages(), cameras.getTotalElements())));


    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWrapper<GarbageInfoResponse>> getCamera(@PathVariable Integer id) {
        Optional<Garbage> optionalUser = garbageRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Garbage garbage = optionalUser.get();

        GarbageInfoResponse userInfoResponse = new GarbageInfoResponse(garbage.getId(), garbage.getName(), garbage.getDateCreation(), garbage.getPhoto(), new Coordinate(garbage.getPoint().getLat(), garbage.getPoint().getLon()), garbage.getGarbageType());

        return ResponseEntity.ok(new MessageWrapper<>(userInfoResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageWrapper<Void>> remove(@PathVariable Integer id) {
        garbageRepository.deleteById(id);
        return ResponseEntity.ok(new MessageWrapper<>(null));
    }

    @PostMapping
    public ResponseEntity<MessageWrapper<Integer>> create(@RequestBody GarbageInfoRequest request) {
        Point point = Point.builder().lat(request.coordinates().lat()).lon(request.coordinates().lon()).build();
        point = pointRepository.save(point);
        Garbage garbage = garbageRepository.save(Garbage.builder().garbageType(request.garbageType()).dateCreation(request.dateCreation()).photo(request.photo()).name(request.name()).point(point).build());

        return ResponseEntity.ok(new MessageWrapper<>(garbage.getId()));
    }

    @PutMapping
    public ResponseEntity<MessageWrapper<Integer>> update(@RequestBody GarbageInfoRequest request) {
        Point point = Point.builder().lat(request.coordinates().lat()).lon(request.coordinates().lon()).build();
        point = pointRepository.save(point);
        Garbage garbage = garbageRepository.save(Garbage.builder().id(request.id()).garbageType(request.garbageType()).dateCreation(request.dateCreation()).photo(request.photo()).name(request.name()).point(point).build());

        return ResponseEntity.ok(new MessageWrapper<>(garbage.getId()));
    }


}
