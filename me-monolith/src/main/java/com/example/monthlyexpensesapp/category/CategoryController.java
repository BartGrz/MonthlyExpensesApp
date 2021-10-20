package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.dto.CategoryDto;
import com.example.monthlyexpensesapp.controllers.IllegalExceptionProcessing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@IllegalExceptionProcessing
@Controller
class CategoryController {

    private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private CategoryQueryRepository categoryQueryRepository;

    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService, final CategoryQueryRepository categoryQueryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.categoryQueryRepository = categoryQueryRepository;
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @GetMapping("/delete-category")
    public String deleteCategory(Model model) {
        model.addAttribute("categories", getCategories());
        return "deleteCategory";
    }

    @GetMapping("/edit-category")
    public String editCategory(Model model) {
        model.addAttribute("categories", getCategories());
        return "editCategory";
    }

    @GetMapping("/show-categories")
    String showCategories(Model model) {
        model.addAttribute("categories", getCategories());
        return "showCategories";
    }

    @GetMapping("/category")
    ResponseEntity<List<CategoryDto>> readAll() {
        return ResponseEntity.ok(categoryQueryRepository.findAllDtoBy());
    }

    @GetMapping("/category/{id}")
    ResponseEntity<CategoryDto> readById(@PathVariable int id) {
        return categoryQueryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseBody
    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto toCreate) {
        var toBeCreated = CategoryFactory.from(toCreate);
        Category category = categoryService.addNewCategory(toBeCreated);
        return ResponseEntity.created(URI.create("/" + category.getId_category())).body(toCreate);

    }

    @ResponseBody
    @PutMapping(value = "/category/{id}", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto toUpdate, @PathVariable int id) {

        var toBeUpdated=CategoryFactory.from(toUpdate);
        categoryService.updateCategory(id, toBeUpdated);

        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @DeleteMapping("/category/{id}")
    ResponseEntity<Category> deleteAccount(@PathVariable int id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("categories")
    private List<Category> getCategories() {
        return categoryService.getCategories();
    }

}
