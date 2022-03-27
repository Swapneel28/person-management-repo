package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    @JsonProperty
    public final String email;
    @JsonProperty
    public final String firstname;
    @JsonProperty
    public final String lastname;

    public Person(@JsonProperty("firstname") final String firstname,
                  @JsonProperty("lastname") final String lastname,
                  @JsonProperty("email") final String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    
	@Override
    public String toString() {
        return this.firstname + ' ' + this.lastname + " [" + this.email + "] ";
    }

    // DO NOT REMOVE THIS METHOD. But feel free to adjust to suit your needs.
    public static Person valueOf(final String firstname, final String lastname, final String email) {
         return new Person(firstname,lastname,email); 
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
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
		Person other = (Person) obj;
		if (email == null && other.email != null) 
			return false; 
		if (!email.equals(other.email))
			return false;
		if (firstname == null && other.firstname != null) 
		    return false;  
		if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null && other.lastname != null) 
		    return false;
		if (!lastname.equals(other.lastname))
			return false;
		return true;
	}
            
}