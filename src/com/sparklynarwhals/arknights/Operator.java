package com.sparklynarwhals.arknights;

public class Operator {
    private String name; // name of operator (Exusiai)
    private int numSkills; // number of skills (3)
    private int selectedSkill; // selected skill (S2)
    private int stars;
    private String position; // operator type (Sniper)
    private String damage; // type of damage (Physical)

    public Operator(String name, int numSkills, int stars, String position, String damage) {
        this.name = name;
        this.numSkills = numSkills;
        this.selectedSkill = -1;
        this.position = position;
        this.damage = damage;
        this.stars = stars;
    }

    public void setSelectedSkill(int selectedSkill)
    {
        this.selectedSkill = selectedSkill;
    }

    public String getName() {
        return this.name;
    }

    public int getNumSkills() {
        return this.numSkills;
    }

    public String getPosition() {
        return this.position;
    }

    public String getDamage() {
        return this.damage;
    }

    public int getSelectedSkill() {
        return this.selectedSkill;
    }

    public int getStars() {
        return this.stars;
    }

    public Operator clone()
    {
        return new Operator(this.name, this.numSkills, this.stars, this.position, this.damage);
    }

    public String toString() {
        if (this.selectedSkill == -1) {
            return this.name;
        } else {
            return this.name + ": S" + this.selectedSkill;
        }
    }
}
