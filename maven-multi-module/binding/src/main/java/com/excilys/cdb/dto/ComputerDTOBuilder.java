package com.excilys.cdb.dto;

public class ComputerDTOBuilder {
    String id;
    String name;
    String introduced;
    String discontinued;
    String companyId;
    String companyName;

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
