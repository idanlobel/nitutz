package BusinessLayer;

public class ContactPerson {
    private final String name;
    String Email;
    String cellNumber;

    public ContactPerson(String name,String Email,String cellNumber){
        this.name=name;
        this.Email=Email;
        this.cellNumber=cellNumber;
    }

    public String getName() {
        return name;
    }
}
