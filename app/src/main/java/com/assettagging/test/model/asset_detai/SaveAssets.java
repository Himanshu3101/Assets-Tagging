package com.assettagging.test.model.asset_detai;

public class SaveAssets {
    String UserId;
    String DesCription;
    String Date;
    String ScannedList;
    String Type;

    public SaveAssets(String UserId, String DesCription, String Date, String ScannedList, String Type) {
        this.UserId = UserId;
        this.DesCription = DesCription;
        this.Date = Date;
        this.ScannedList = ScannedList;
        this.Type = Type;
    }
}
