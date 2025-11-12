package com.sastachasma.order.lensclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "lens-service")
public interface LensServiceClient {

    @PutMapping("/lenses/{lensId}/stock")
    ResponseEntity<Void> updateLensStock(
            @PathVariable Long lensId,
            @RequestParam Integer quantity
    );

    @GetMapping("/lenses/{lensId}")
    Optional<LensDto> getLensById(@PathVariable Long lensId);
}
