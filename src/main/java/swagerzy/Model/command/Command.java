/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.command;

/**
 *
 * @author maciejdaszkiewicz
 */
/**
 * Command interface for the Command pattern.
 * Encapsulates a request as an object, allowing for decoupled UI actions.
 */
public interface Command {
    /**
     * Executes the specific action encapsulated by the command.
     */
    public void execute();
}
