package com.cg.hms.adm.model;


public class PhysicianModel {
    private int id;
    private String name;
    private String speciality;
    private Shift shift;
    private Status status;
    private long contact;
    private int departmentIds; // Only department IDs, not the full department

    public PhysicianModel() {}

    public PhysicianModel(int id, String name, String speciality, Shift shift, Status status, long contact, int departmentIds) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.shift = shift;
        this.status = status;
        this.contact = contact;
        this.departmentIds = departmentIds;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public int getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(int departmentIds) {
        this.departmentIds = departmentIds;
    }

    @Override
    public String toString() {
        return "PhysicianModel [id=" + id + ", name=" + name + ", speciality=" + speciality + ", shift=" + shift
                + ", status=" + status + ", contact=" + contact + ", departmentIds=" + departmentIds + "]";
    }
}
