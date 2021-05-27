package com.excilys.dto;

public class ComputerDTO {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyId;
    private String companyName;

    private ComputerDTO(ComputerDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
    }

    public static class ComputerDTOBuilder {
        private String id;
        private String name;
        private String introduced;
        private String discontinued;
        private String companyId;
        private String companyName;

        /**
         *
         */
        public ComputerDTOBuilder() {
        };

        /**
         *
         * @param id
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param name
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         *
         * @param introduced
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withIntroduced(String introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         *
         * @param discontinued
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withDiscontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         *
         * @param company
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withCompanyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        /**
         *
         * @param company
         * @return ComputerDTOBuilder
         */
        public ComputerDTOBuilder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         *
         * @return ComputerDTOBuilder
         */
        public ComputerDTO build() {
            return new ComputerDTO(this);
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

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
    }

}
