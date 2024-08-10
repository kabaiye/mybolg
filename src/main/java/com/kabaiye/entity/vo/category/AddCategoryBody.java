package com.kabaiye.entity.vo.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryBody {
    private String name;
    private String parentId;
    public AddCategoryBody(String name) {
        this.name = name;
        this.parentId = "0";
    }
}
