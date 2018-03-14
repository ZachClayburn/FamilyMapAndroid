package clayburn.familyMapServer.services;


import clayburn.familymap.ServiceResponses.FillResponse;
import clayburn.familyMapServer.database.Database;
import clayburn.familymap.model.Event;
import clayburn.familymap.model.Person;
import clayburn.familymap.model.User;

import java.util.ArrayList;
import java.util.UUID;

/**
 * FillService performs the fill service
 */
public class FillService {

    /**
     * Fills the tree of a user with random family tree information
     * @return A response stating how many Persons and events were added
     * @param userName The username of the user who will be getting new generations filled
     * @param generations The amount of generations to add
     * @throws Database.DatabaseException If there was an error filling the database
     */
    public FillResponse fill(String userName, int generations) throws Database.DatabaseException {
        //TEST

        Database db = null;
        boolean commit = false;
        try{
            db = new Database();
            db.openConnection();

            User user = db.getUserDAO().getUser(userName);
            if(user == null) {
                throw new Database.DatabaseException("No such user " + userName + " in the database");
            }

            db.getPersonDAO().removeAllFromUser(userName);
            db.getEventDAO().removeAllFromUser(userName);

            Person[] newPersons = generatePersons(user,generations);
            Event[] newEvents = generateEvents(newPersons);

            db.getPersonDAO().addPersons(newPersons);
            db.getEventDAO().addEvents(newEvents);

            commit = true;
            return new FillResponse(newPersons.length,newEvents.length);

        }finally {
            if(db != null){
                db.closeConnection(commit);
            }
        }
    }

    static Person[] generatePersons(User user, int generations){

        ArrayList<Person> personArrayList = new ArrayList<>();

        //Add the User's Person first
        personArrayList.add(new Person(user.getUserName(), user.getPersonID(),
                user.getFirstName(),user.getLastName(),user.getGender(),
                null,null,
                null));//Users are always single

        if(generations > 0){
            recursivePersonGenerator(personArrayList,user.getUserName(),generations);
        }

        Person[] persons = personArrayList.toArray(new Person[personArrayList.size()]);

        assert persons[0].getFirstName().equals(user.getFirstName());
        assert persons[0].getLastName().equals(user.getLastName());

        return persons;
    }

    private static void recursivePersonGenerator(ArrayList<Person> personArrayList,
                                                       String descendantID,
                                                       int generation){
        String motherID = UUID.randomUUID().toString();
        String fatherID = UUID.randomUUID().toString();

        String motherName = DataGenerator.getRandomFemaleName();
        String fatherName = DataGenerator.getRandomMaleName();
        String surname = DataGenerator.getRandomSurname();

        Person mother = new Person(descendantID,motherID,motherName,surname,"f",null,null,fatherID);
        Person father = new Person(descendantID,fatherID,fatherName,surname,"m",null,null,motherID);

        int previousGeneration = personArrayList.get(personArrayList.size()-1).getGeneration();
        mother.setGeneration(previousGeneration + 1);
        father.setGeneration(previousGeneration + 1);

        personArrayList.get(personArrayList.size()-1).setFather(fatherID);
        personArrayList.get(personArrayList.size()-1).setMother(motherID);

        personArrayList.add(mother);
        if(generation - 1 > previousGeneration){
            recursivePersonGenerator(personArrayList,descendantID,generation);
        }
        personArrayList.add(father);
        if(generation - 1 > previousGeneration){
            recursivePersonGenerator(personArrayList,descendantID,generation);
        }

    }

    static Event[] generateEvents(Person[] persons){

        final int numEvents = 4;

        Event[] events = new Event[persons.length * numEvents];//Four events per person

        int i = 0;

        String descendant = persons[0].getDescendant();

        for(Person person : persons) {
            DataGenerator.Location birthLocation = DataGenerator.getRandomLocation();
            DataGenerator.Location baptismLocation = DataGenerator.getRandomLocation();
            DataGenerator.Location moveLocation = DataGenerator.getRandomLocation();
            DataGenerator.Location deathLocation = DataGenerator.getRandomLocation();

            String[] years = DataGenerator.getRandomYearSequence(person.getGeneration(),numEvents);
            Event birth = new Event(UUID.randomUUID().toString(),descendant,person.getPersonID(),
                    birthLocation.getLatitude(),birthLocation.getLongitude(),
                    birthLocation.getCountry(),birthLocation.getCity(),"birth",years[0]);
            Event baptism = new Event(UUID.randomUUID().toString(),descendant,person.getPersonID(),
                    baptismLocation.getLatitude(),baptismLocation.getLongitude(),
                    baptismLocation.getCountry(),baptismLocation.getCity(),"baptism",years[1]);
            Event move = new Event(UUID.randomUUID().toString(),descendant,person.getPersonID(),
                    moveLocation.getLatitude(),moveLocation.getLongitude(),
                    moveLocation.getCountry(),moveLocation.getCity(),"move",years[2]);
            Event death = new Event(UUID.randomUUID().toString(),descendant,person.getPersonID(),
                    deathLocation.getLatitude(),deathLocation.getLongitude(),
                    deathLocation.getCountry(),deathLocation.getCity(),"death",years[3]);

            events[i]   = birth;
            events[i+1] = baptism;
            events[i+2] = move;
            events[i+3] = death;

            i+=4;
        }
        return events;
    }

}
