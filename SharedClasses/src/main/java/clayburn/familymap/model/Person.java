package clayburn.familymap.model;


import java.util.Objects;

/**
 * Person represents a person in a family tree
 * @author Zach Clayburn
 */
public class Person {

    /**
     * Name of user account this person belongs to
     */
    private String descendant;

    /**
     * The person's unique ID
     */
    private String personID;

    /**
     * The Person's first name
     */
    private String firstName;

    /**
     * The Person's last name
     */
    private String lastName;

    /**
     * The Person's Gender (m or f)
     */
    private String gender;

    /**
     * The PersonID of the Person's father (Can be left null)
     */
    private String father;

    /**
     * The PersonID of the Person's mother (Can be left null)
     */
    private String mother;

    /**
     * The PersonID of the Person's spouse (Can be left null)
     */
    private String spouse;

    /**
     * How many Generations back this person is (ie. 0 = user, 1 = user's parents etc.). Only used in generating events
     */
    private transient int generation;

    /**
     * Creates a new Person object representing a row from the person table
     * @param descendant Name of user account this person belongs to
     * @param personID The person's unique ID
     * @param firstName The Person's first name
     * @param lastName The Person's last name
     * @param gender The Person's Gender (m or f)
     * @param father The PersonID of the Person's father (Can be left null)
     * @param mother The PersonID of the Person's mother (Can be left null)
     * @param spouse The PersonID of the Person's spouse (Can be left null)
     */
    public Person(String descendant, String personID,
                  String firstName, String lastName,
                  String gender, String father,
                  String mother, String spouse) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender == null ? null : gender.toLowerCase();
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.generation = 0;
    }

    /**
     * Constructs a Person with all members set to null, to be initialized with setters
     */
    public static Person newEmptyPerson() {
        return new Person(null,null,
                null,null,null,
                null,null,null);
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.toLowerCase();
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    /**
     * Creates a String out ot the Person
     * @return The String containing the Person's fields
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("descendant='").append(descendant).append('\'');
        sb.append(", personID='").append(personID).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", father='").append(father).append('\'');
        sb.append(", mother='").append(mother).append('\'');
        sb.append(", spouse='").append(spouse).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Compares the given Person object with another object o
     * @param o The object to be compared
     * @return False if o is null, not an Person or if it's fields don't match, true otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(descendant, person.descendant) &&
                Objects.equals(personID, person.personID) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(father, person.father) &&
                Objects.equals(mother, person.mother) &&
                Objects.equals(spouse, person.spouse);
    }

    /**
     * Generate a hash code for the given Person
     * @return The Person's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(descendant, personID, firstName, lastName, gender, father, mother, spouse);
    }
}
