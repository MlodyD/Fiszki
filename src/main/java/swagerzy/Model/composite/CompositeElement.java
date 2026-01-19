/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.composite;

/**
 * Component interface for the Composite pattern.
 * Defines common operations for both leaf nodes (Flashcards) and composites (Decks).
 */
public interface CompositeElement {
    public CompositeElement getParent();
    public String getFront();
    public String getId();
    public String getType();
}
