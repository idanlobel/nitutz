public class ID_Generator {

    private static ID_Generator idgenerator;
    private int current_id;

    private ID_Generator()
    {
        this.current_id=1;
    }

    public static ID_Generator getInstance()
    {
        if(idgenerator == null)
        {
            idgenerator=new ID_Generator();
        }

            return idgenerator;

    }

    public int Get_ID()
    {
        int current_avaible_id=this.current_id;
        this.current_id++;
        return current_avaible_id;
    }

}
