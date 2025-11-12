package com.sastachasma.lens.controller;

import com.sastachasma.lens.dto.LensDTO;
import com.sastachasma.lens.service.LensService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lens", description = "APIs for managing lenses")
@RestController
@RequestMapping("/lenses")
@RequiredArgsConstructor
public class LensController {

    private final LensService lensService;

    @Operation(summary = "Get all lenses",
              description = "Retrieves a list of all available lenses")
    @ApiResponse(responseCode = "200",
                 description = "Successfully retrieved list of lenses",
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = LensDTO.class))))
    @GetMapping
    public ResponseEntity<List<LensDTO>> getAllLenses() {
        return ResponseEntity.ok(lensService.getAllLenses());
    }

    @Operation(summary = "Get lens by ID",
              description = "Retrieves a specific lens by its ID")
    @ApiResponse(responseCode = "200",
                 description = "Successfully retrieved lens",
                 content = @Content(schema = @Schema(implementation = LensDTO.class)))
    @ApiResponse(responseCode = "404",
                 description = "Lens not found")
    @GetMapping("/{id}")
    public ResponseEntity<LensDTO> getLensById(
            @Parameter(description = "ID of the lens to be retrieved")
            @PathVariable Long id) {
        return ResponseEntity.ok(lensService.getLensById(id));
    }

    @Operation(summary = "Get lenses by type",
              description = "Retrieves all lenses of a specific type")
    @ApiResponse(responseCode = "200",
                 description = "Successfully retrieved lenses by type",
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = LensDTO.class))))
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LensDTO>> getLensesByType(
            @Parameter(description = "Type of lenses to retrieve")
            @PathVariable String type) {
        return ResponseEntity.ok(lensService.getLensesByType(type));
    }

    @Operation(summary = "Get lenses by material",
              description = "Retrieves all lenses with a specific material")
    @ApiResponse(responseCode = "200",
                 description = "Successfully retrieved lenses by material",
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = LensDTO.class))))
    @GetMapping("/material/{material}")
    public ResponseEntity<List<LensDTO>> getLensesByMaterial(
            @Parameter(description = "Material to filter by")
            @PathVariable String material) {
        return ResponseEntity.ok(lensService.getLensesByMaterial(material));
    }

    @Operation(summary = "Get active lenses",
              description = "Retrieves all active lenses")
    @ApiResponse(responseCode = "200",
                 description = "Successfully retrieved active lenses",
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = LensDTO.class))))
    @GetMapping("/active")
    public ResponseEntity<List<LensDTO>> getActiveLenses() {
        return ResponseEntity.ok(lensService.getActiveLenses());
    }

    @Operation(summary = "Create a new lens",
              description = "Creates a new lens with the provided details")
    @ApiResponse(responseCode = "201",
                 description = "Lens created successfully",
                 content = @Content(schema = @Schema(implementation = LensDTO.class)))
    @PostMapping
    public ResponseEntity<LensDTO> createLens(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lens details to be created",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LensDTO.class)))
            @Valid @RequestBody LensDTO lensDTO) {
        return new ResponseEntity<>(lensService.createLens(lensDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing lens",
              description = "Updates an existing lens with the provided details")
    @ApiResponse(responseCode = "200",
                 description = "Lens updated successfully",
                 content = @Content(schema = @Schema(implementation = LensDTO.class)))
    @ApiResponse(responseCode = "404",
                 description = "Lens not found")
    @PutMapping("/{id}")
    public ResponseEntity<LensDTO> updateLens(
            @Parameter(description = "ID of the lens to be updated")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated lens details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LensDTO.class)))
            @Valid @RequestBody LensDTO lensDTO) {
        return ResponseEntity.ok(lensService.updateLens(id, lensDTO));
    }

    @Operation(summary = "Delete a lens",
              description = "Deletes a lens by its ID")
    @ApiResponse(responseCode = "204",
                 description = "Lens deleted successfully")
    @ApiResponse(responseCode = "404",
                 description = "Lens not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLens(
            @Parameter(description = "ID of the lens to be deleted")
            @PathVariable Long id) {
        lensService.deleteLens(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update lens stock",
            description = "Updates the stock quantity for a specific lens")
    @ApiResponse(responseCode = "200",
            description = "Lens stock updated successfully")
    @ApiResponse(responseCode = "404",
            description = "Lens not found")
    @PutMapping("/{lensId}/stock")
    public ResponseEntity<Void> updateLensStock(
            @Parameter(description = "ID of the lens to update stock for")
            @PathVariable Long lensId,
            @Parameter(description = "Quantity to add or subtract from stock (can be negative)")
            @RequestParam Integer quantity) {
        lensService.updateLensStock(lensId, quantity);
        return ResponseEntity.ok().build();
    }
}
