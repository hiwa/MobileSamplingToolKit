/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Pedram
 */
public class SelectableItem {

    private Integer id;
    private String name;
    private boolean selected;

    public SelectableItem(Integer id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
