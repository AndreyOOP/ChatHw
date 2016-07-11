package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable{

    private final String SAVE_PATH = "C:\\Users\\an.yudaichev\\Desktop\\JP\\ChatHw\\web\\UserList.txt";

    private List<User> users = new ArrayList<>();

    private static final UserList userList = new UserList();

    private UserList(){

        users = loadFromDisk();
    }

    public static UserList getInstance(){
        return userList;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getByName(String name){

        for(User user: users){
            if( user.getName().equalsIgnoreCase( name))
                return user;
        }

        return null;
    }

    public synchronized void add(User user){

        users.add( user);
        saveOnDisk();
    }

    public Boolean isInList(String userName){

        for(User user: users)
            if( user.getName().contentEquals(userName))
                return true;

        return false;
    }

    private void saveOnDisk(){

        try(FileOutputStream fos = new FileOutputStream( SAVE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> loadFromDisk(){

        try(FileInputStream fis = new FileInputStream( SAVE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            UserList ul = (UserList) ois.readObject();

            for(User u: ul.getUsers())
                u.setOnline(false);

            return ul.getUsers();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }
}
