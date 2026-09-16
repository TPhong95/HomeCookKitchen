package org.example.users.chef;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
public class ChefController {
    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chef> getChefById(@PathVariable Long id){
        Chef result = chefService.getChefById(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping()
    public ResponseEntity<List<Chef>> getAllChefs() {
        List<Chef> result = chefService.getAllChefs();
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        }


    @PostMapping()
    public ResponseEntity<Chef> saveChef(@RequestBody Chef chef) {
        Chef result = chefService.save(chef);
        return ResponseEntity.ok(result);
    }


}

