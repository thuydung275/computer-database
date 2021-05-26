package com.excilys.dto;

public class ComputerDTO {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyId;

    private ComputerDTO(ComputerDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.companyId = builder.companyId;
    }

    public static class ComputerDTOBuilder {
        private String id;
        private String name;
        private String introduced;
        private String discontinued;
        private String companyId;

        /**
         *
         */
        public ComputerDTOBuilder() {
        };

        /**
         *
         * @param id
         * @return ComputerBuilder
         */
        public ComputerDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param name
         * @return ComputerBuilder
         */
        public ComputerDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         *
         * @param introduced
         * @return ComputerBuilder
         */
        public ComputerDTOBuilder withIntroduced(String introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         *
         * @param discontinued
         * @return ComputerBuilder
         */
        public ComputerDTOBuilder withDiscontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         *
         * @param company
         * @return ComputerBuilder
         */
        public ComputerDTOBuilder withCompanyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        /**
         *
         * @return ComputerBuilder
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

    @Override
    public String toString() {
        return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", companyId=" + companyId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComputerDTO other = (ComputerDTO) obj;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (discontinued == null) {
            if (other.discontinued != null)
                return false;
        } else if (!discontinued.equals(other.discontinued))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (introduced == null) {
            if (other.introduced != null)
                return false;
        } else if (!introduced.equals(other.introduced))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
