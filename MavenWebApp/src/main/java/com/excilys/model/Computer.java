package com.excilys.model;

import java.time.LocalDate;

public class Computer {

    private int id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    private Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public static class ComputerBuilder {
        private int id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

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
        public ComputerBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param name
         * @return ComputerBuilder
         */
        public ComputerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         *
         * @param introduced
         * @return ComputerBuilder
         */
        public ComputerBuilder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         *
         * @param discontinued
         * @return ComputerBuilder
         */
        public ComputerBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         *
         * @param company
         * @return ComputerBuilder
         */
        public ComputerBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }

        /**
         *
         * @return ComputerBuilder
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public static Computer copy(Computer computer) {
        Computer copyComputer = new Computer.ComputerBuilder().withId(computer.getId()).withName(computer.getName())
                .withIntroduced(computer.getIntroduced()).withDiscontinued(computer.getDiscontinued())
                .withCompany(computer.getCompany()).build();
        return copyComputer;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + id;
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }
}
