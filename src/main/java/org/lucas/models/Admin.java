package org.lucas.models;

import org.lucas.util.ObjectBase;
import org.lucas.core.*;

public class Admin extends User implements ObjectBase {

    public Admin(String id, String loginName, String name, String password, String email, String gender,String phoneNumber){
        super(id, loginName, name, password, email, gender, phoneNumber);
    }
}
