// Please note : @LinkingObjects and default values are not represented in the schema and thus will not be part of the generated models
package com.app.bandnara.RealmDB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TumbonRm extends RealmObject {
    @PrimaryKey
    @Required
    private String id;
    private String tumbonId;
    private String tumbonName;
    private String ampId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTumbonId() {
        return tumbonId;
    }

    public void setTumbonId(String tumbonId) {
        this.tumbonId = tumbonId;
    }

    public String getTumbonName() {
        return tumbonName;
    }

    public void setTumbonName(String tumbonName) {
        this.tumbonName = tumbonName;
    }

    public String getAmpId() {
        return ampId;
    }

    public void setAmpId(String ampId) {
        this.ampId = ampId;
    }
}
