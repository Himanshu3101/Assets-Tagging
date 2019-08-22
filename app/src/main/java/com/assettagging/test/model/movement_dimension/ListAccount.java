package com.assettagging.test.model.movement_dimension;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListAccount {

@SerializedName("MainAccountNo")
@Expose
private String mainAccountNo;
@SerializedName("Name")
@Expose
private String name;

public String getMainAccountNo() {
return mainAccountNo;
}

public void setMainAccountNo(String mainAccountNo) {
this.mainAccountNo = mainAccountNo;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}
    public ListAccount() {
    }

    @Override
    public String toString() {
        return mainAccountNo+" - "+name;
    }
}