// Please note : @LinkingObjects and default values are not represented in the schema and thus will not be part of the generated models
package com.app.bandnara.RealmDB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class AmphuresRm extends RealmObject {
    @PrimaryKey
    @Required
    private String id;
    private String ampId;
    private String ampName;
    private String provId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvId() {
        return provId;
    }


    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getAmpId() {
        return ampId;
    }

    public void setAmpId(String ampId) {
        this.ampId = ampId;
    }

    public String getAmpName() {
        return ampName;
    }

    public void setAmpName(String ampName) {
        this.ampName = ampName;
    }
}
