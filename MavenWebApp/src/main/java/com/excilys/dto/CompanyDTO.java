package com.excilys.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CompanyDTO {

    private String id;
    private String name;

    public CompanyDTO() {
    }

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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
