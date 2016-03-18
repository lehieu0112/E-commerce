/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

public class ChangeableString {
    String str;

    public ChangeableString(String str) {
        this.str = str;
    }

    public void changeTo(String newStr) {
        str = newStr;
    }

    public String toString() {
        return str;
    }
}
