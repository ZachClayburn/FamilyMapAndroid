package clayburn.familymap.ServiceResponses;


import clayburn.familymap.model.Person;

/**
 * A response to a request for a single person
 */
public class SinglePersonResponse implements ServiceResponse {

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
     * Creates a SinglePersonResponse out of a person object to be serialized and sent to the client
     * @param person The person that the client requested
     */
    public SinglePersonResponse(Person person){
        this.descendant = person.getDescendant();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.father = person.getFather();
        this.mother = person.getMother();
        this.spouse = person.getSpouse();
    }

    /**
     * Create an Person object out of the information in the response
     * @return The Person object that the client requested
     */
    public Person getPerson(){
        return new Person(descendant,personID,firstName,lastName,gender,father,mother,spouse);
    }

}
