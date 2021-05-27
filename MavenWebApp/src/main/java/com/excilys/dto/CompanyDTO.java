package com.excilys.dto;

public class CompanyDTO {

    private String id;
    private String name;

    private CompanyDTO(CompanyDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static class CompanyDTOBuilder {
        private String id;
        private String name;

        /**
         *
         */
        public CompanyDTOBuilder() {
        };

        /**
         *
         * @param id
         * @return CompanyDTOBuilder
         */
        public CompanyDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param name
         * @return CompanyDTOBuilder
         */
        public CompanyDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         *
         * @return CompanyDTO
         */
        public CompanyDTO build() {
            return new CompanyDTO(this);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyDTO [id=" + id + ", name=" + name + "]";
    }

}
