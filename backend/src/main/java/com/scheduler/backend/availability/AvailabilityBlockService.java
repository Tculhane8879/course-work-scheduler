package com.scheduler.backend.availability;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityBlockService {

    private final AvailabilityBlockRepository availabilityBlockRepository;

    public AvailabilityBlockService(AvailabilityBlockRepository availabilityBlockRepository) {
        this.availabilityBlockRepository = availabilityBlockRepository;
    }

    public List<AvailabilityBlockResponse> getAllAvailabilityBlocks() {
        return availabilityBlockRepository.findAllByOrderByDayOfWeekAscStartTimeAsc()
                .stream()
                .map(AvailabilityBlockResponse::fromEntity)
                .toList();
    }

    public AvailabilityBlockResponse createAvailabilityBlock(AvailabilityBlockRequest request) {
        AvailabilityBlock block = new AvailabilityBlock(
                request.getLabel().trim(),
                request.getDayOfWeek(),
                request.getStartTime(),
                request.getEndTime(),
                request.getBlockType()
        );

        AvailabilityBlock savedBlock = availabilityBlockRepository.save(block);
        return AvailabilityBlockResponse.fromEntity(savedBlock);
    }

    public AvailabilityBlockResponse getAvailabilityBlockById(Long id) {
        AvailabilityBlock block = availabilityBlockRepository.findById(id)
                .orElseThrow(() -> new AvailabilityBlockNotFoundException(id));

        return AvailabilityBlockResponse.fromEntity(block);
    }

    public AvailabilityBlockResponse updateAvailabilityBlock(Long id, AvailabilityBlockRequest request) {
        AvailabilityBlock block = availabilityBlockRepository.findById(id)
                .orElseThrow(() -> new AvailabilityBlockNotFoundException(id));

        block.setLabel(request.getLabel().trim());
        block.setDayOfWeek(request.getDayOfWeek());
        block.setStartTime(request.getStartTime());
        block.setEndTime(request.getEndTime());
        block.setBlockType(request.getBlockType());

        AvailabilityBlock updatedBlock = availabilityBlockRepository.save(block);
        return AvailabilityBlockResponse.fromEntity(updatedBlock);
    }

    public void deleteAvailabilityBlock(Long id) {
        if (!availabilityBlockRepository.existsById(id)) {
            throw new AvailabilityBlockNotFoundException(id);
        }

        availabilityBlockRepository.deleteById(id);
    }
}
