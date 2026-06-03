package com.scheduler.backend.availability;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability-blocks")
public class AvailabilityBlockController {

    private final AvailabilityBlockService availabilityBlockService;

    public AvailabilityBlockController(AvailabilityBlockService availabilityBlockService) {
        this.availabilityBlockService = availabilityBlockService;
    }

    @GetMapping
    public List<AvailabilityBlockResponse> getAllAvailabilityBlocks() {
        return availabilityBlockService.getAllAvailabilityBlocks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityBlockResponse createAvailabilityBlock(
            @Valid @RequestBody AvailabilityBlockRequest request
    ) {
        return availabilityBlockService.createAvailabilityBlock(request);
    }

    @GetMapping("/{id}")
    public AvailabilityBlockResponse getAvailabilityBlockById(@PathVariable Long id) {
        return availabilityBlockService.getAvailabilityBlockById(id);
    }

    @PutMapping("/{id}")
    public AvailabilityBlockResponse updateAvailabilityBlock(
            @PathVariable Long id,
            @Valid @RequestBody AvailabilityBlockRequest request
    ) {
        return availabilityBlockService.updateAvailabilityBlock(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvailabilityBlock(@PathVariable Long id) {
        availabilityBlockService.deleteAvailabilityBlock(id);
    }
}
