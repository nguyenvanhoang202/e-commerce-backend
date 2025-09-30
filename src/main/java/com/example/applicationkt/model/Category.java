package com.example.applicationkt.model;

public class Category {
    private Long id;
    private String name;
    private String Slug;
    private String description;

    public Category() {}

    public Category(Long id, String name, String slug, String description) {
        this.id = id;
        this.name = name;
        Slug = slug;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
