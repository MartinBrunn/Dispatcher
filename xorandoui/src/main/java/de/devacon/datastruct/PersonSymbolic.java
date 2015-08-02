package de.devacon.datastruct;

/**
 * Created by @Martin@ on 01.08.2015 06:29.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.IOException;

/**
 * Created by @Martin@ on 31.07.2015 09:52.
 */
public class PersonSymbolic extends Person implements Parcelable {
    public PersonSymbolic(String internalName, Gender gender, String firstName, String lastName, int resIcon, File picture) {
        super(firstName,lastName,gender);
        name = internalName;
        this.resIcon = resIcon;
        this.picture = picture;
    }
    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);

        super.writeToParcel(dest,flags);
        dest.writeString(picture != null? picture.getAbsolutePath():"");
    }
    private PersonSymbolic(Parcel in){
        name = in.readString();
        super.readFromParcel(in);
        String fileName = in.readString();
        if(fileName.isEmpty()){
            picture = null;
        }
        else
            picture = new File(fileName);
    }
    static final Parcelable.Creator<PersonSymbolic> CREATOR = new Parcelable.Creator<PersonSymbolic>(){
        public PersonSymbolic createFromParcel(Parcel in) {
            return new PersonSymbolic(in);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry
         * initialized to null.
         */
        @Override
        public PersonSymbolic[] newArray(int size) {
            return new PersonSymbolic[size];
        }
    };
    public int resIcon = 0;
    public File picture = null;
    public String name = null;
    PersonSymbolic(String internalName) {
        name = internalName;
    }
    PersonSymbolic(){

    }
    @Override
    public String toString() {
        String ret = "";
        if(firstName.isEmpty()) {
            if(gender == Gender.FEMALE) {
                ret += "Frau ";
            }
            else if(gender == Gender.MALE) {
                ret += "Herr ";
            }
            if(lastName.isEmpty()){
                ret += "Unbekannt";
            }
            else
                ret += lastName;
        }
        else {
            ret += firstName;
            if(!lastName.isEmpty()){
                ret += " " + lastName;
            }
        }
        return ret;
    }
    private static final long serialVersionUID = 1L;
    protected void writeObject(java.io.ObjectOutputStream dest)
            throws IOException {
        super.writeObject(dest);
        dest.writeUTF(name);
        dest.writeUTF(picture != null ? picture.getAbsolutePath() : "");

    }
    protected void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        super.readObject(in);
        name = in.readUTF();
        String fileName = in.readUTF();
        if(fileName.isEmpty()){
            picture = null;
        }
        else
            picture = new File(fileName);
    }

}

