package model;

import java.util.*;

public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;

    private List<Tag> tags;
    private String status;

    public enum StatusEnum {
        AVAILABLE("available"),
        PENDING("pending"),
        SOLD("sold");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


    }

    public long getId() {
        return id;
    }

    public Pet setId(long id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Pet setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public List getPhotoUrls() {
        return photoUrls;
    }

    public Pet setPhotoUrls(List photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public List getTags() {
        return tags;
    }

    public Pet setTags(List tags) {
        this.tags = tags;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Pet setStatus(StatusEnum status) {
        this.status = status.getValue();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return id == pet.id && Objects.equals(category, pet.category) && name.equals(pet.name) && photoUrls.equals(pet.photoUrls) && Objects.equals(tags, pet.tags) && Objects.equals(status, pet.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, photoUrls, tags, status);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status=" + status +
                '}';
    }
}
