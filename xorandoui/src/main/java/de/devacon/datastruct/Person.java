package de.devacon.datastruct;

import android.os.Parcel;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by @Martin@ on 01.08.2015 06:40.
 */
public class Person implements Serializable {
    enum Gender {
        UNKNOWN ,
        MALE ,
        FEMALE,
    }
    public String firstName = "";
    public String lastName = "";
    Gender gender = Gender.UNKNOWN;

    public Person() {
    }

    public Person(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
    }
    public void readFromParcel(Parcel in){
        byte b = in.readByte();
        gender = b == 1 ? Gender.MALE : b == 2 ? Gender.FEMALE : Gender.UNKNOWN;
        firstName = in.readString();
        lastName = in.readString();

    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (gender == Gender.MALE ? 1 : gender == Gender.FEMALE ? 2 : 0));
        dest.writeString(firstName);
        dest.writeString(lastName);

    }
    private static final long serialVersionUID = 1L;
    protected void writeObject(java.io.ObjectOutputStream dest)
            throws IOException {
        dest.writeByte((byte) (gender == Gender.MALE ? 1 : gender == Gender.FEMALE ? 2 : 0));
        dest.writeUTF(firstName);
        dest.writeUTF(lastName);

    }
    protected void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        byte b = in.readByte();
        gender = b == 1 ? Gender.MALE : b == 2 ? Gender.FEMALE : Gender.UNKNOWN;
        firstName = in.readUTF();
        lastName = in.readUTF();
    }
}
