package br.com.dio.controller;

import br.com.dio.service.*;
import br.com.dio.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/ultrapets")
@Tag(name = "Ultrapets Controller", description = "RESTful API for managing veterinary clinic.")
public record UltrapetsController(
        AgendamentoService agendamentoService,
        DonoService donoService,
        PetService petService,
        UnidadeService unidadeService,
        VeterinarioService veterinarioService
) {
    // Implementação dos endpoints para Agendamento, Dono, Pet, Unidade e Veterinario
    // Cada entidade terá seus próprios endpoints para operações CRUD

    //===============================DONOS================================
    @GetMapping("/donos")
    @Operation(summary = "Get all Donos", description = "Retrieve a list of all registered donos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<DonoDto>> findAll() {
        var donos = donoService.findAll();
        var donosDto = donos.stream().map(DonoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(donosDto);
    }

    @GetMapping("/donos/{id}")
    @Operation(summary = "Get a dono by ID", description = "Retrieve a specific dono based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Dono not found")
    })
    public ResponseEntity<DonoDto> findById(@PathVariable Long id) {
        var dono = donoService.findById(id);
        return  ResponseEntity.ok(new DonoDto(dono));
    }

    @PostMapping("/donos")
    @Operation(summary = "Create a new dono", description = "Create a new dono and return the created dono's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dono created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid dono data provided")
    })
    public ResponseEntity<DonoDto> create(@RequestBody DonoDto donoDto) {
        var dono = donoService.create(donoDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dono.getId())
                .toUri();
        return ResponseEntity.created(location).body(new DonoDto(dono));
    }

    @PutMapping("/donos/{id}")
    @Operation(summary = "Update a dono", description = "Update the data of an existing dono based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dono updated successfully"),
            @ApiResponse(responseCode = "404", description = "Dono not found"),
            @ApiResponse(responseCode = "422", description = "Invalid dono data provided")
    })
    public ResponseEntity<DonoDto> update(@PathVariable Long id, @RequestBody DonoDto donoDto) {
        var dono = donoService.update(id, donoDto.toModel());
        return ResponseEntity.ok(new DonoDto(dono));
    }

    @DeleteMapping("/donos/{id}")
    @Operation(summary = "Delete a dono", description = "Delete an existing dono based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dono deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Dono not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //============================PETS====================================
    @GetMapping("/pets")
    @Operation(summary = "Get all Pets", description = "Retrieve a list of all registered pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<PetDto>> findAllPets() {
        var pets = petService.findAll();
        var petsDto = pets.stream().map(PetDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(petsDto);
    }

    @GetMapping("/pets/{id}")
    @Operation(summary = "Get a pet by ID", description = "Retrieve a specific pet based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<PetDto> findPetById(@PathVariable Long id) {
        var pet = petService.findById(id);
        return ResponseEntity.ok(new PetDto(pet));
    }

    @PostMapping("/pets")
    @Operation(summary = "Create a new pet", description = "Create a new pet and return the created pet's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid pet data provided")
    })
    public ResponseEntity<PetDto> createPet(@RequestBody PetDto petDto) {
        var pet = petService.create(petDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pet.getId())
                .toUri();
        return ResponseEntity.created(location).body(new PetDto(pet));
    }

    @PutMapping("/pets/{id}")
    @Operation(summary = "Update a pet", description = "Update the data of an existing pet based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found"),
            @ApiResponse(responseCode = "422", description = "Invalid pet data provided")
    })
    public ResponseEntity<PetDto> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        var pet = petService.update(id, petDto.toModel());
        return ResponseEntity.ok(new PetDto(pet));
    }

    @DeleteMapping("/pets/{id}")
    @Operation(summary = "Delete a pet", description = "Delete an existing pet based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //================================AGENDAMENTOS==================================

    @GetMapping("/agendamentos")
    @Operation(summary = "Get all Agendamentos", description = "Retrieve a list of all registered agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<AgendamentoDto>> findAllAgendamentos() {
        var agendamentos = agendamentoService.findAll();
        var agendamentosDto = agendamentos.stream().map(AgendamentoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(agendamentosDto);
    }

    @GetMapping("/agendamentos/{id}")
    @Operation(summary = "Get a agendamento by ID", description = "Retrieve a specific agendamento based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Agendamento not found")
    })
    public ResponseEntity<AgendamentoDto> findAgendamentoById(@PathVariable Long id) {
        var agendamento = agendamentoService.findById(id);
        return ResponseEntity.ok(new AgendamentoDto(agendamento));
    }

    @PostMapping("/agendamentos")
    @Operation(summary = "Create a new agendamento", description = "Create a new agendamento and return the created agendamento's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid agendamento data provided")
    })
    public ResponseEntity<AgendamentoDto> createAgendamento(@RequestBody AgendamentoDto agendamentoDto) {
        var agendamento = agendamentoService.create(agendamentoDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agendamento.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AgendamentoDto(agendamento));
    }

    @PutMapping("/agendamentos/{id}")
    @Operation(summary = "Update a agendamento", description = "Update the data of an existing agendamento based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento updated successfully"),
            @ApiResponse(responseCode = "404", description = "Agendamento not found"),
            @ApiResponse(responseCode = "422", description = "Invalid agendamento data provided")
    })
    public ResponseEntity<AgendamentoDto> updateAgendamento(@PathVariable Long id, @RequestBody AgendamentoDto agendamentoDto) {
        var agendamento = agendamentoService.update(id, agendamentoDto.toModel());
        return ResponseEntity.ok(new AgendamentoDto(agendamento));
    }

    @DeleteMapping("/agendamentos/{id}")
    @Operation(summary = "Delete a agendamento", description = "Delete an existing agendamento based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Agendamento not found")
    })
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        agendamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //============================================UNIDADES=================================================

    @GetMapping("/unidades")
    @Operation(summary = "Get all Unidades", description = "Retrieve a list of all registered unidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<UnidadeDto>> findAllUnidades() {
        var unidades = unidadeService.findAll();
        var unidadesDto = unidades.stream().map(UnidadeDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(unidadesDto);
    }

    @GetMapping("/unidades/{id}")
    @Operation(summary = "Get a unidade by ID", description = "Retrieve a specific unidade based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Unidade not found")
    })
    public ResponseEntity<UnidadeDto> findUnidadeById(@PathVariable Long id) {
        var unidade = unidadeService.findById(id);
        return ResponseEntity.ok(new UnidadeDto(unidade));
    }

    @PostMapping("/unidades")
    @Operation(summary = "Create a new unidade", description = "Create a new unidade and return the created unidade's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid unidade data provided")
    })
    public ResponseEntity<UnidadeDto> createUnidade(@RequestBody UnidadeDto unidadeDto) {
        var unidade = unidadeService.create(unidadeDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(unidade.getId())
                .toUri();
        return ResponseEntity.created(location).body(new UnidadeDto(unidade));
    }

    @PutMapping("/unidades/{id}")
    @Operation(summary = "Update a unidade", description = "Update the data of an existing unidade based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade updated successfully"),
            @ApiResponse(responseCode = "404", description = "Unidade not found"),
            @ApiResponse(responseCode = "422", description = "Invalid unidade data provided")
    })
    public ResponseEntity<UnidadeDto> updateUnidade(@PathVariable Long id, @RequestBody UnidadeDto unidadeDto) {
        var unidade = unidadeService.update(id, unidadeDto.toModel());
        return ResponseEntity.ok(new UnidadeDto(unidade));
    }

    @DeleteMapping("/unidades/{id}")
    @Operation(summary = "Delete a unidade", description = "Delete an existing unidade based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Unidade not found")
    })
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        unidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //===========================VETERINARIOS========================================

    @GetMapping("/veterinarios")
    @Operation(summary = "Get all Veterinarios", description = "Retrieve a list of all registered veterinarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<VeterinarioDto>> findAllVeterinarios() {
        var veterinarios = veterinarioService.findAll();
        var veterinariosDto = veterinarios.stream().map(VeterinarioDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(veterinariosDto);
    }

    @GetMapping("/veterinarios/{id}")
    @Operation(summary = "Get a veterinario by ID", description = "Retrieve a specific veterinario based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Veterinario not found")
    })
    public ResponseEntity<VeterinarioDto> findVeterinarioById(@PathVariable Long id) {
        var veterinario = veterinarioService.findById(id);
        return ResponseEntity.ok(new VeterinarioDto(veterinario));
    }

    @PostMapping("/veterinarios")
    @Operation(summary = "Create a new veterinario", description = "Create a new veterinario and return the created veterinario's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veterinario created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid veterinario data provided")
    })
    public ResponseEntity<VeterinarioDto> createVeterinario(@RequestBody VeterinarioDto veterinarioDto) {
        var veterinario = veterinarioService.create(veterinarioDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(veterinario.getId())
                .toUri();
        return ResponseEntity.created(location).body(new VeterinarioDto(veterinario));
    }

    @PutMapping("/veterinarios/{id}")
    @Operation(summary = "Update a veterinario", description = "Update the data of an existing veterinario based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veterinario updated successfully"),
            @ApiResponse(responseCode = "404", description = "Veterinario not found"),
            @ApiResponse(responseCode = "422", description = "Invalid veterinario data provided")
    })
    public ResponseEntity<VeterinarioDto> updateVeterinario(@PathVariable Long id, @RequestBody VeterinarioDto veterinarioDto) {
        var veterinario = veterinarioService.update(id, veterinarioDto.toModel());
        return ResponseEntity.ok(new VeterinarioDto(veterinario));
    }

    @DeleteMapping("/veterinarios/{id}")
    @Operation(summary = "Delete a veterinario", description = "Delete an existing veterinario based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Veterinario deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Veterinario not found")
    })
    public ResponseEntity<Void> deleteVeterinario(@PathVariable Long id) {
        veterinarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
