package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class CarShopDTO implements Serializable {
    @JsonView(Views.List.class)
    private Long id;

    @JsonView(Views.List.class)
    @NotNull(message = "car shop name must not be null")
    @Size(max = 60, message = "car shop length must be of maximum 60 characters")
    private String name;

    @JsonView(Views.List.class)
    private String logoUrl;

    @JsonView(Views.Details.class)
    @Size(max = 500, message = "description must not be longer than 500 characters")
    private String description;

    @JsonView(Views.Details.class)
    @Size(max = 120, message = "summary must not be longer than 500 characters")
    private String summary;

    public CarShopDTO() {
    }

    private CarShopDTO(CarShopDTOBuilder builder) {
        this.name = builder.name;
        this.logoUrl = builder.logoUrl;
        this.description = builder.description;
        this.summary = builder.summary;
    }

    public static class CarShopDTOBuilder {
        private final String name; // any required fields should be initialised in the constructor, all other are optional
        private String logoUrl;
        private String description;
        private String summary;

        public CarShopDTOBuilder(String name) {
            this.name = name;
        }

        public CarShopDTOBuilder withLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public CarShopDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CarShopDTOBuilder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public CarShopDTO build() {
            return new CarShopDTO(this);
        }
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarShopDTO that = (CarShopDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(logoUrl, that.logoUrl) && Objects.equals(description, that.description) && Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, logoUrl, description, summary);
    }

    @Override
    public String toString() {
        return "CarShopDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}