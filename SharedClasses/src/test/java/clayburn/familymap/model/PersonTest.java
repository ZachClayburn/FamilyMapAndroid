package clayburn.familymap.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @BeforeEach
    void setUp() {
        person1 = new Person(descendant1,personID1,firstName1,lastName1,gender1,father1,mother1,spouse1);
        person2 = new Person(descendant2,personID2,firstName2,lastName2,gender2,father2,mother2,spouse2);
        person2.setGeneration(generation2);
    }

    Person person1;
    Person person2;

    static final String descendant1 = "descendant1";
    static final String personID1 = "personID1";
    static final String firstName1 = "firstName1";
    static final String lastName1 = "lastName1";
    static final String gender1 = "m";
    static final String father1 = "father1";
    static final String mother1 = "mother1";
    static final String spouse1 = "spouse";
    static final int generation1 = 0;

    static final String descendant2 = "descendant2";
    static final String personID2 = "personID2";
    static final String firstName2 = "firstName2";
    static final String lastName2 = "lastName2";
    static final String gender2 = "f";
    static final String father2 = "father2";
    static final String mother2 = "mother2";
    static final String spouse2 = "spouse";
    static final int generation2 = 1;

    @Test
    void gettersAndSetters(){
        assertEquals(descendant1,person1.getDescendant());
        assertEquals(personID1,person1.getPersonID());
        assertEquals(firstName1,person1.getFirstName());
        assertEquals(lastName1,person1.getLastName());
        assertEquals(gender1,person1.getGender());
        assertEquals(father1,person1.getFather());
        assertEquals(mother1,person1.getMother());
        assertEquals(spouse1,person1.getSpouse());
        assertEquals(generation1,person1.getGeneration());

        assertEquals(descendant2,person2.getDescendant());
        assertEquals(personID2,person2.getPersonID());
        assertEquals(firstName2,person2.getFirstName());
        assertEquals(lastName2,person2.getLastName());
        assertEquals(gender2,person2.getGender());
        assertEquals(father2,person2.getFather());
        assertEquals(mother2,person2.getMother());
        assertEquals(spouse2,person2.getSpouse());
        assertEquals(generation2,person2.getGeneration());

        person2.setDescendant(descendant1);
        person2.setPersonID(personID1);
        person2.setFirstName(firstName1);
        person2.setLastName(lastName1);
        person2.setGender(gender1);
        person2.setFather(father1);
        person2.setMother(mother1);
        person2.setSpouse(spouse1);
        person2.setGeneration(generation1);

        assertEquals(descendant1,person2.getDescendant());
        assertEquals(personID1,person2.getPersonID());
        assertEquals(firstName1,person2.getFirstName());
        assertEquals(lastName1,person2.getLastName());
        assertEquals(gender1,person2.getGender());
        assertEquals(father1,person2.getFather());
        assertEquals(mother1,person2.getMother());
        assertEquals(spouse1,person2.getSpouse());
        assertEquals(generation1,person2.getGeneration());

    }

    @Test
    void newEmptyPerson() {
        person2 = Person.newEmptyPerson();

        assertNull(person2.getDescendant());
        assertNull(person2.getPersonID());
        assertNull(person2.getFirstName());
        assertNull(person2.getLastName());
        assertNull(person2.getGender());
        assertNull(person2.getFather());
        assertNull(person2.getMother());
        assertNull(person2.getSpouse());
        assertEquals(person2.getGeneration(),0);

    }

    @Test
    void testToString() {
        String string = "Person{Descendant='" + descendant1 +
                "', personID='" + personID1 +
                "', firstName='" + firstName1 +
                "', lastName='" + lastName1 +
                "', gender='" + gender1 +
                "', father='" + father1 +
                "', mother='" + mother1 +
                "', ='" + spouse1 +
                "'}";
    }

    @Test
    void equals() {
        assertTrue(person1.equals(person1));
        assertFalse(person1.equals(person2));

        person2.setDescendant(descendant1);
        person2.setPersonID(personID1);
        person2.setFirstName(firstName1);
        person2.setLastName(lastName1);
        person2.setGender(gender1);
        person2.setFather(father1);
        person2.setMother(mother1);
        person2.setSpouse(spouse1);
        person2.setGeneration(generation1);

        assertTrue(person1.equals(person2));
    }

    @Test
    void testHashCode() {

        person2.setDescendant(descendant1);
        person2.setPersonID(personID1);
        person2.setFirstName(firstName1);
        person2.setLastName(lastName1);
        person2.setGender(gender1);
        person2.setFather(father1);
        person2.setMother(mother1);
        person2.setSpouse(spouse1);
        person2.setGeneration(generation1);

        assertEquals(person1.hashCode(),person2.hashCode());
    }

}
