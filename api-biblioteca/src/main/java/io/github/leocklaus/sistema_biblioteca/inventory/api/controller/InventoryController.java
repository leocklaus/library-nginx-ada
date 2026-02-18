package io.github.leocklaus.sistema_biblioteca.inventory.api.controller;

import io.github.leocklaus.sistema_biblioteca.inventory.api.dto.AddInventoryRequest;
import io.github.leocklaus.sistema_biblioteca.inventory.api.dto.InventoryResponse;
import io.github.leocklaus.sistema_biblioteca.inventory.application.usecase.AddInventoryUseCase;
import io.github.leocklaus.sistema_biblioteca.inventory.application.usecase.GetInventoryUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/books/{bookId}/inventory")
public class InventoryController {

    private final AddInventoryUseCase addInventoryUseCase;
    private final GetInventoryUseCase getInventoryUseCase;

    public InventoryController(AddInventoryUseCase addInventoryUseCase, GetInventoryUseCase getInventoryUseCase) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.getInventoryUseCase = getInventoryUseCase;
    }

    @GetMapping
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable UUID libraryId,
                                                          @PathVariable UUID bookId){
        InventoryResponse response = getInventoryUseCase.execute(libraryId, bookId);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Void> addInventory(
            @PathVariable UUID libraryId,
            @PathVariable UUID bookId,
            @RequestBody AddInventoryRequest request
    ) {
        addInventoryUseCase.execute(
                libraryId,
                bookId,
                request
        );

        return ResponseEntity.noContent().build();
    }
}
