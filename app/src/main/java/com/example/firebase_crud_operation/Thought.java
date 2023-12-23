package com.example.firebase_crud_operation;

public class Thought {
    private String key,thought,admin;

    public Thought(String key, String thought, String admin) {
        this.key = key;
        this.thought = thought;
        this.admin = admin;
    }

    public Thought() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
