// Please note : @LinkingObjects and default values are not represented in the schema and thus will not be part of the generated models
package com.app.bandnara.RealmDB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProvinceRm extends RealmObject {
    @PrimaryKey
    @Required
    private String id;
    private String provId;
    private String provName;

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

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }
}
