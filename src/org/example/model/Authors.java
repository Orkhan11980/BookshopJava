package org.example.model;

public class Authors {
    private int authorID;
    private String name;
    private String bio;


    public Authors() {}

    public Authors(int authorID, String name, String bio) {
        this.authorID = authorID;
        this.name = name;
        this.bio = bio;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Authors{" +
                "authorID=" + authorID +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}