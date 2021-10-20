package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.dto.CategoryDto;

class CategoryFactory {

  static Category from (CategoryDto source ) {
        var category = new Category();
        category.assaignId(source.getId_category());
        category.setCategory_name(source.getCategory_name());
        category.setProducts(source.getProducts());
        return category;
    }

}
