package com.excilys.cdp.apiback.model;

import java.time.LocalDate;

public class Computer {
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	private Computer(ComputerBuilder builder) {
		this.id 			= builder.id;
		this.name 			= builder.name;
		this.introduced 	= builder.introduced;
		this.discontinued 	= builder.discontinued;
		this.company 		= builder.company;
	}
	
	public static class ComputerBuilder {
		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public ComputerBuilder() {};
		
		public ComputerBuilder setId(int id) {
            this.id = id;
            return this;
        }
		public ComputerBuilder setName(String name) {
            this.name = name;
            return this;
        }
		public ComputerBuilder setIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }
		public ComputerBuilder setDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
		public ComputerBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }
		
		public Computer build() {
			return new Computer(this);
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
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}
}
