package com.excilys.dto;

import java.time.LocalDate;

public class ComputerDTO {

    private int id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private int companyId;

    private ComputerDTO(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.companyId = builder.companyId;
    }

    public static class ComputerBuilder {
        private int id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private int companyId;

        /**
         *
         */
        public ComputerBuilder() {
        };

        /**
         *
         * @param id
         * @return ComputerBuilder
         */
        public ComputerBuilder setId(int id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param name
         * @return ComputerBuilder
         */
        public ComputerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         *
         * @param introduced
         * @return ComputerBuilder
         */
        public ComputerBuilder setIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         *
         * @param discontinued
         * @return ComputerBuilder
         */
        public ComputerBuilder setDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         *
         * @param company
         * @return ComputerBuilder
         */
        public ComputerBuilder setCompany(int companyId) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + companyId;
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + id;
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
        if (companyId != other.companyId)
            return false;
        if (discontinued == null) {
            if (other.discontinued != null)
                return false;
        } else if (!discontinued.equals(other.discontinued))
            return false;
        if (id != other.id)
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

    @Override
    public String toString() {
        return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", companyId=" + companyId + "]";
    }

}
