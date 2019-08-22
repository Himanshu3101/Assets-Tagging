package com.assettagging.test.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.assettagging.test.model.all_user.UserList;
import com.assettagging.test.model.assetList.disposalassetlist;
import com.assettagging.test.model.asset_detai.BarcodeWiseDataList;
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.model.asset_disposal.DisposalWiseDataList;
import com.assettagging.test.model.locationwise.ScheduleLocation;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.model.schedule_detail.ItemCurentStatusList;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.tasklocationwise.ScheduleLocationTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AssetTagging.db";
    private static final int DATABASE_VERSION = 13;
    public static final String COMPLETED_TABLE = "COMPLETED_TABLE";
    public static final String ONGOING_TABLE = "ONGOING_TABLE";
    public static final String UPCOMING_TABLE = "UPCOMING_TABLE";

    public static final String SCHEDULEID = "SCHEDULEID";
    public static final String SCHEDULEDESCRIPTION = "SCHEDULEDESCRIPTION";
    public static final String STARTTIME = "STARTTIME";
    public static final String ENDTIME = "ENDTIME";
    public static final String Status = "Status";

    public static final String ScheduleDetail_TABLE = "ScheduleDetail_TABLE";
    public static final String ACTIVITYTYPE = "ACTIVITYTYPE";
    public static final String ASSETID = "ASSETID";
    public static final String BarCode = "BarCode";
    public static final String EMPNAME = "EMPNAME";
    public static final String EmpID = "EmpID";
    public static final String LOCATION = "LOCATION";
    public static final String TRACKING = "TRACKING";

    public static final String ScheduleLocationTask_TABLE = "ScheduleLocationTask_TABLE";
    public static final String EMPID = "EMPID";

    public static final String scheduleLocation_TABLE = "scheduleLocation_TABLE";
    public static final String LocationName = "LocationName";
    public static final String ID = "ID";
    public static final String IMAGEPATH = "IMAGEPATH";
    public static final String BLOB = "IMAGE";
    private Target target;
    private byte[] bb;
    Context context;
    private String destinationFile = "image.jpg";
    private String ITEMS = "ITEMS";
    private String BLOB_IMAGES = "BLOB_IMAGES";
    private String USER_TABLE = "USER_TABLE";
    private static String UserId = "UserId";
    private static String UserName = "UserName";
    private String UserPin = "UserPin";
    private String Password = "Password";
    private String CURRENT_STATUS = "CURRENT_STATUS";
    private String DISPOSED_TABLE = "DISPOSED_TABLE";
    private String NAME = "NAME";
    private String EBarcodeId = "EBarcodeId";
    private String ASSETS_TABLE = "ASSETS_TABLE";
    private String TYPE = "TYPE";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + "(" +
                UserId + " TEXT PRIMARY KEY, " +
                UserName + " TEXT, " + EMPID + " TEXT, " +
                UserPin + " TEXT, " +
                Password + " TEXT)");

        db.execSQL("CREATE TABLE " + DISPOSED_TABLE + "(" +
                SCHEDULEID + " TEXT PRIMARY KEY , " +
                SCHEDULEDESCRIPTION + " TEXT, " +
                TYPE + " TEXT, " + STARTTIME + " TEXT, " + ENDTIME + " TEXT, " +
                Status + " TEXT)");

        db.execSQL("CREATE TABLE " + ASSETS_TABLE + "(" +
                SCHEDULEID + " TEXT , " + ASSETID + " TEXT PRIMARY KEY , " +
                EBarcodeId + " TEXT, " +
                LOCATION + " TEXT, " + Status + " TEXT, " +
                NAME + " TEXT)");

        db.execSQL("CREATE TABLE " + COMPLETED_TABLE + "(" +
                SCHEDULEID + " TEXT PRIMARY KEY, " +
                SCHEDULEDESCRIPTION + " TEXT, " +
                STARTTIME + " TEXT, " + ENDTIME + " TEXT, " +
                Status + " TEXT)");

        db.execSQL("CREATE TABLE " + ONGOING_TABLE + "(" +
                SCHEDULEID + " TEXT PRIMARY KEY, " +
                SCHEDULEDESCRIPTION + " TEXT, " +
                STARTTIME + " TEXT, " + ENDTIME + " TEXT, " +
                Status + " TEXT)");
        db.execSQL("CREATE TABLE " + UPCOMING_TABLE + "(" +
                SCHEDULEID + " TEXT PRIMARY KEY, " +
                SCHEDULEDESCRIPTION + " TEXT, " +
                STARTTIME + " TEXT, " + ENDTIME + " TEXT, " +
                Status + " TEXT)");

        db.execSQL("CREATE TABLE " + ScheduleDetail_TABLE + "(" +
                SCHEDULEID + " TEXT, " +
                ACTIVITYTYPE + " TEXT, " +
                ID + " TEXT PRIMARY KEY, " + IMAGEPATH + " TEXT, " + STARTTIME + " TEXT, " + ENDTIME + " TEXT, " +
                ASSETID + " TEXT, " + BarCode + " TEXT, " + BLOB + " TEXT, " + BLOB_IMAGES + " BLOB, " +
                EmpID + " TEXT, " + LOCATION + " TEXT, " + ITEMS + " TEXT, " +
                TRACKING + " TEXT, " + EMPNAME + " TEXT, " + CURRENT_STATUS + " TEXT)");

        db.execSQL("CREATE TABLE " + ScheduleLocationTask_TABLE + "(" +
                SCHEDULEID + " TEXT, " +
                ID + " TEXT PRIMARY KEY, " + LOCATION + " TEXT, " +
                ACTIVITYTYPE + " TEXT, " +
                EmpID + " TEXT, " + ENDTIME + " TEXT, " +
                STARTTIME + " TEXT)");

        db.execSQL("CREATE TABLE " + scheduleLocation_TABLE + "(" +
                SCHEDULEID + " TEXT, " +
                ID + " TEXT PRIMARY KEY, " +
                EmpID + " TEXT, " +
                LOCATION + " TEXT, " +
                LocationName + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DISPOSED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSETS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ONGOING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UPCOMING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleDetail_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleLocationTask_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + scheduleLocation_TABLE);

        onCreate(db);

    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, null, null);
        // db.delete(DISPOSED_TABLE, null, null);
        //   db.delete(ASSETS_TABLE, null, null);
        db.delete(COMPLETED_TABLE, null, null);
        db.delete(ONGOING_TABLE, null, null);
        db.delete(UPCOMING_TABLE, null, null);
        db.delete(ScheduleDetail_TABLE, null, null);
        db.delete(ScheduleLocationTask_TABLE, null, null);
        db.delete(scheduleLocation_TABLE, null, null);


    }

    public void dropAssetandDisposalTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DISPOSED_TABLE, null, null);
        db.delete(ASSETS_TABLE, null, null);
    }


    public void dropASSETSTable(Context context, String Schedule_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSETS_TABLE,
                SCHEDULEID + " = ?",
                new String[]{Schedule_ID + ""});
    }
    public void dropASSETSROwTable(Context context, String Schedule_ID,String asset_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSETS_TABLE,
                SCHEDULEID + " = ?"+ " AND " + ASSETID + "=?",
                new String[]{Schedule_ID + "",asset_id});
    }
    public void dropDisposerTable(Context context, String Schedule_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DISPOSED_TABLE,
                SCHEDULEID + " = ?",
                new String[]{Schedule_ID + ""});
    }

    public void dropUSerTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, null, null);
    }

    public void dropScheduleTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMPLETED_TABLE, null, null);
    }

    public void dropOngoingScheduleTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ONGOING_TABLE, null, null);
    }

    public void dropUpcomingTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UPCOMING_TABLE, null, null);
    }

    public void dropScheduleDetailTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ScheduleDetail_TABLE, null, null);

    }

    public void dropTAskTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ScheduleLocationTask_TABLE, null, null);

    }

    public void dropLocationTable(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(scheduleLocation_TABLE, null, null);

    }

    public boolean insertUSER(List<UserList> userLists) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < userLists.size(); i++) {
            contentValues.put(UserId, userLists.get(i).getUserId());
            contentValues.put(UserName, userLists.get(i).getUserName());
            contentValues.put(EMPID, userLists.get(i).getEMPID());
            contentValues.put(UserPin, userLists.get(i).getUserPin());
            contentValues.put(Password, userLists.get(i).getPassword());
            db.insertWithOnConflict(USER_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public boolean insertAssets(BarcodeWiseDataList barcodeWiseDataLists, String scheduleid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULEID, scheduleid);
        contentValues.put(ASSETID, barcodeWiseDataLists.getASSETID());
        contentValues.put(EBarcodeId, barcodeWiseDataLists.getBarcode());
        contentValues.put(LOCATION, barcodeWiseDataLists.getLOCATION());
        contentValues.put(NAME, barcodeWiseDataLists.getNAME());
        contentValues.put(Status, barcodeWiseDataLists.getStatus());
        db.insertWithOnConflict(ASSETS_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
        return true;
    }

    public boolean insertSCHEDULE(List<Schedule> schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < schedule.size(); i++) {
            contentValues.put(SCHEDULEID, schedule.get(i).getSCHEDULEID());
            contentValues.put(SCHEDULEDESCRIPTION, schedule.get(i).getSCHEDULEDESCRIPTION());
            contentValues.put(STARTTIME, schedule.get(i).getSTARTTIME());
            contentValues.put(ENDTIME, schedule.get(i).getENDTIME());
            contentValues.put(Status, schedule.get(i).getStatus());
            db.insertWithOnConflict(COMPLETED_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public boolean insertDisposerSCHEDULE(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SCHEDULEID, schedule.getSCHEDULEID());
        contentValues.put(SCHEDULEDESCRIPTION, schedule.getSCHEDULEDESCRIPTION());
        contentValues.put(STARTTIME, schedule.getSTARTTIME());
        contentValues.put(ENDTIME, schedule.getENDTIME());
        contentValues.put(Status, schedule.getStatus());
        contentValues.put(TYPE, schedule.getType());
        db.insertWithOnConflict(DISPOSED_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        close();
        return true;
    }

    public boolean insertOngoingSCHEDULE(List<Schedule> schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < schedule.size(); i++) {
            contentValues.put(SCHEDULEID, schedule.get(i).getSCHEDULEID());
            contentValues.put(SCHEDULEDESCRIPTION, schedule.get(i).getSCHEDULEDESCRIPTION());
            contentValues.put(STARTTIME, schedule.get(i).getSTARTTIME());
            contentValues.put(ENDTIME, schedule.get(i).getENDTIME());
            contentValues.put(Status, schedule.get(i).getStatus());
            db.insertWithOnConflict(ONGOING_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public boolean insertUpcomingSCHEDULE(List<Schedule> schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < schedule.size(); i++) {
            contentValues.put(SCHEDULEID, schedule.get(i).getSCHEDULEID());
            contentValues.put(SCHEDULEDESCRIPTION, schedule.get(i).getSCHEDULEDESCRIPTION());
            contentValues.put(STARTTIME, schedule.get(i).getSTARTTIME());
            contentValues.put(ENDTIME, schedule.get(i).getENDTIME());
            contentValues.put(Status, schedule.get(i).getStatus());
            db.insertWithOnConflict(UPCOMING_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public static SQLiteDatabase sqLiteDatabase;

    public boolean insertScheduleDetail(List<ScheduleDetail_> scheduleDetail, List<ItemCurentStatusList> itemCurentStatusLists) throws IOException {
        sqLiteDatabase = this.getWritableDatabase();
        for (int i = 0; i < scheduleDetail.size(); i++) {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseHelper.SCHEDULEID, scheduleDetail.get(i).getSCHEDULEID());
            contentValues.put(DataBaseHelper.ACTIVITYTYPE, scheduleDetail.get(i).getACTIVITYTYPE());
            contentValues.put(DataBaseHelper.ASSETID, scheduleDetail.get(i).getASSETID());
            contentValues.put(DataBaseHelper.EmpID, scheduleDetail.get(i).getEmpID());
            contentValues.put(DataBaseHelper.TRACKING, scheduleDetail.get(i).getTRACKING());
            contentValues.put(DataBaseHelper.BarCode, scheduleDetail.get(i).getBarCode());
            contentValues.put(DataBaseHelper.LOCATION, scheduleDetail.get(i).getLOCATION());
            contentValues.put(DataBaseHelper.EMPNAME, scheduleDetail.get(i).getEMPNAME());

            if (scheduleDetail.get(i).getCurentStatus() == null) {
                scheduleDetail.get(i).setCurentStatus("");
            }
            contentValues.put(CURRENT_STATUS, scheduleDetail.get(i).getCurentStatus());
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(itemCurentStatusLists).getAsJsonArray();
            JsonArray ScannedList = myCustomArray;
            contentValues.put(ITEMS, ScannedList.toString());
            contentValues.put(DataBaseHelper.IMAGEPATH, scheduleDetail.get(i).getImagePath());
            contentValues.put(DataBaseHelper.STARTTIME, scheduleDetail.get(i).getSTARTTIME());
            contentValues.put(DataBaseHelper.ENDTIME, scheduleDetail.get(i).getENDTIME());
            DataBaseHelper.sqLiteDatabase.insertWithOnConflict(DataBaseHelper.ScheduleDetail_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        sqLiteDatabase.close();
        return true;
    }

    public boolean updateScheduleDetail(List<ScheduleDetail_> scheduleDetail) throws IOException {
        sqLiteDatabase = this.getWritableDatabase();
        for (int i = 0; i < scheduleDetail.size(); i++) {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseHelper.SCHEDULEID, scheduleDetail.get(i).getSCHEDULEID());
            contentValues.put(DataBaseHelper.ACTIVITYTYPE, scheduleDetail.get(i).getACTIVITYTYPE());
            contentValues.put(DataBaseHelper.ASSETID, scheduleDetail.get(i).getASSETID());
            contentValues.put(DataBaseHelper.EmpID, scheduleDetail.get(i).getEmpID());
            contentValues.put(DataBaseHelper.TRACKING, scheduleDetail.get(i).getTRACKING());
            contentValues.put(DataBaseHelper.BarCode, scheduleDetail.get(i).getBarCode());
            contentValues.put(DataBaseHelper.LOCATION, scheduleDetail.get(i).getLOCATION());
            contentValues.put(DataBaseHelper.EMPNAME, scheduleDetail.get(i).getEMPNAME());
            contentValues.put(CURRENT_STATUS, scheduleDetail.get(i).getCurentStatus());

            //  Log.d("mFilePathlast", getBase64EncodedImage(scheduleDetail.get(i).getImagePath(), i));
//            try {
//                contentValues.put(DataBaseHelper.BLOB, getBase64EncodedImage(scheduleDetail.get(i).getImagePath(), i, scheduleDetail.get(i).getASSETID()));
//                contentValues.put(BLOB_IMAGES, getByteImage(scheduleDetail.get(i).getImagePath()));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            contentValues.put(DataBaseHelper.IMAGEPATH, scheduleDetail.get(i).getImagePath());
            contentValues.put(DataBaseHelper.STARTTIME, scheduleDetail.get(i).getSTARTTIME());
            contentValues.put(DataBaseHelper.ENDTIME, scheduleDetail.get(i).getENDTIME());
            String selectQuery = SCHEDULEID + "  = '" + scheduleDetail.get(i).getSCHEDULEID() + "' and " + ACTIVITYTYPE + "  = '" + scheduleDetail.get(i).getACTIVITYTYPE() + "' and " + ASSETID + "  ='" + scheduleDetail.get(i).getASSETID() + "'";
            sqLiteDatabase.update(ScheduleDetail_TABLE, contentValues, selectQuery, null);

        }
        sqLiteDatabase.close();
        return true;
    }

    public boolean updateImages(List<String> Images) throws IOException {
        sqLiteDatabase = this.getWritableDatabase();
        for (int i = 0; i < Images.size(); i++) {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseHelper.BLOB, Images.get(i));
            String[] assetId = Images.get(i).split(":");
            String[] newAssetId = assetId[1].split(".");
            contentValues.put(BLOB, Images.get(i));
            String selectQuery = ASSETID + "  ='" + newAssetId[0] + "'";
            sqLiteDatabase.update(ScheduleDetail_TABLE, contentValues, selectQuery, null);

        }
        sqLiteDatabase.close();
        return true;
    }

    byte[] bytes;
    String mFilePath;
    File mediaFile;

    public byte[] getByteImage(final String imageURL) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    URL imageUrl = new URL(imageURL);
                    URLConnection ucon = imageUrl.openConnection();

                    InputStream is = ucon.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
                    bytes = baos.toByteArray();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bytes;
    }

    public String getBase64EncodedImage(final String imageURL, final int i,
                                        final String assetid) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    URL imageUrl = new URL(imageURL);
                    URLConnection ucon = imageUrl.openConnection();

                    InputStream is = ucon.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
                    bytes = baos.toByteArray();

                    FileOutputStream fos = null;
                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                            + "/Android/data/"
                            + "ImageFiles");
                    if (!mediaStorageDir.exists()) {
                        mediaStorageDir.mkdir();
                    }
                    // Create a media file name
                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date());

                    String mImageName = i + ":" + assetid + ".jpg";

                    try {
                        fos = context.openFileOutput(mImageName, Context.MODE_PRIVATE);
                        fos.write(bytes);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
                    mFilePath = mediaFile.getAbsolutePath();
                    Log.d("mFilePath", mFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return mFilePath;
    }


    public boolean insertScheduleLocationTask
            (List<ScheduleLocationTask> scheduleLocationTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < scheduleLocationTask.size(); i++) {
            contentValues.put(SCHEDULEID, scheduleLocationTask.get(i).getSCHEDULEID());
            contentValues.put(ACTIVITYTYPE, scheduleLocationTask.get(i).getACTIVITYTYPE());
            contentValues.put(ENDTIME, scheduleLocationTask.get(i).getENDTIME());
            contentValues.put(EmpID, scheduleLocationTask.get(i).getEMPID());
            contentValues.put(STARTTIME, scheduleLocationTask.get(i).getSTARTTIME());
            contentValues.put(LOCATION, scheduleLocationTask.get(i).getLOCATION());

            db.insertWithOnConflict(ScheduleLocationTask_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public boolean updateScheduleLocationTask
            (List<ScheduleLocationTask> scheduleLocationTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < scheduleLocationTask.size(); i++) {
            contentValues.put(SCHEDULEID, scheduleLocationTask.get(i).getSCHEDULEID());
            contentValues.put(ACTIVITYTYPE, scheduleLocationTask.get(i).getACTIVITYTYPE());
            contentValues.put(ENDTIME, scheduleLocationTask.get(i).getENDTIME());
            contentValues.put(EmpID, scheduleLocationTask.get(i).getEMPID());
            contentValues.put(STARTTIME, scheduleLocationTask.get(i).getSTARTTIME());
            contentValues.put(LOCATION, scheduleLocationTask.get(i).getLOCATION());
            String selectQuery = SCHEDULEID + "  = '" + scheduleLocationTask.get(i).getSCHEDULEID() + "' and " + EMPID + "  = '" + scheduleLocationTask.get(i).getACTIVITYTYPE() + "' and " + LOCATION + "  ='" + scheduleLocationTask.get(i).getLOCATION() + "'";
            db.update(ScheduleLocationTask_TABLE, contentValues, selectQuery, null);
        }
        db.close();
        return true;
    }

    public boolean insertscheduleLocation(List<ScheduleLocation> scheduleLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < scheduleLocation.size(); i++) {
            contentValues.put(SCHEDULEID, scheduleLocation.get(i).getSCHEDULEID());
            contentValues.put(EMPID, scheduleLocation.get(i).getEMPID());
            contentValues.put(LOCATION, scheduleLocation.get(i).getLocation());
            contentValues.put(LocationName, scheduleLocation.get(i).getLocationName());

            db.insertWithOnConflict(scheduleLocation_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return true;
    }

    public boolean updatescheduleLocation(List<ScheduleLocation> scheduleLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < scheduleLocation.size(); i++) {
            contentValues.put(SCHEDULEID, scheduleLocation.get(i).getSCHEDULEID());
            contentValues.put(EMPID, scheduleLocation.get(i).getEMPID());
            contentValues.put(LOCATION, scheduleLocation.get(i).getLocation());
            contentValues.put(LocationName, scheduleLocation.get(i).getLocationName());

            db.update(scheduleLocation_TABLE, contentValues, SCHEDULEID + " = ?",
                    new String[]{String.valueOf(scheduleLocation.get(i).getSCHEDULEID())});
        }
        db.close();
        return true;
    }

    public List<Schedule> getAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + COMPLETED_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                schedule.setSCHEDULEDESCRIPTION(cursor.getString(cursor.getColumnIndex(SCHEDULEDESCRIPTION)));
                schedule.setSTARTTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                schedule.setENDTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                schedule.setStatus(cursor.getString(cursor.getColumnIndex(Status)));
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return schedules;
    }

    public List<BarcodeWiseDataList> getDisposalAssets() {
        List<BarcodeWiseDataList> barcodeWiseDataLists = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + ASSETS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BarcodeWiseDataList barcodeWiseDataList = new BarcodeWiseDataList();
                barcodeWiseDataList.setASSETID(cursor.getString(cursor.getColumnIndex(ASSETID)));
                barcodeWiseDataList.setBarcode(cursor.getString(cursor.getColumnIndex(EBarcodeId)));
                barcodeWiseDataList.setLOCATION(cursor.getString(cursor.getColumnIndex(LOCATION)));
                barcodeWiseDataList.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
                barcodeWiseDataList.setStatus(cursor.getString(cursor.getColumnIndex(Status)));
                barcodeWiseDataLists.add(barcodeWiseDataList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return barcodeWiseDataLists;
    }

    public List<CreatedDisposalList> getAllDisposedSchedule() {
        List<CreatedDisposalList> schedules = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DISPOSED_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CreatedDisposalList schedule = new CreatedDisposalList();
                schedule.setDisposalScheduleHeaderId(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                schedule.setDescription(cursor.getString(cursor.getColumnIndex(SCHEDULEDESCRIPTION)));
                schedule.setDisposalDate(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                schedule.setStatus(cursor.getString(cursor.getColumnIndex(Status)));
                schedule.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return schedules;
    }


    public List<Schedule> getOngoingSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + ONGOING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                schedule.setSCHEDULEDESCRIPTION(cursor.getString(cursor.getColumnIndex(SCHEDULEDESCRIPTION)));
                schedule.setSTARTTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                schedule.setENDTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                schedule.setStatus(cursor.getString(cursor.getColumnIndex(Status)));
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return schedules;
    }

    public List<Schedule> getUpcomingSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + UPCOMING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                schedule.setSCHEDULEDESCRIPTION(cursor.getString(cursor.getColumnIndex(SCHEDULEDESCRIPTION)));
                schedule.setSTARTTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                schedule.setENDTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                schedule.setStatus(cursor.getString(cursor.getColumnIndex(Status)));
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return schedules;
    }

    public List<BarcodeWiseDataList> getAllDisposalAssets() {
        List<BarcodeWiseDataList> barcodeWiseDataLists = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor cursor = db.rawQuery(selectQuery, null)

        String selectQuery = "SELECT * FROM " + ASSETS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BarcodeWiseDataList barcodeWiseDataList = new BarcodeWiseDataList();
                barcodeWiseDataList.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                barcodeWiseDataList.setASSETID(cursor.getString(cursor.getColumnIndex(ASSETID)));
                barcodeWiseDataList.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
                barcodeWiseDataList.setLOCATION(cursor.getString(cursor.getColumnIndex(LOCATION)));
                barcodeWiseDataList.setBarcode(cursor.getString(cursor.getColumnIndex(EBarcodeId)));

                barcodeWiseDataLists.add(barcodeWiseDataList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return barcodeWiseDataLists;
    }

    public List<disposalassetlist> getParticularDisposalAssets(String scheduleId) {
        List<disposalassetlist> barcodeWiseDataLists = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor cursor = db.rawQuery(selectQuery, null)

        String selectQuery = "SELECT * FROM " + ASSETS_TABLE + " WHERE " + SCHEDULEID + "  = '" + scheduleId + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                disposalassetlist barcodeWiseDataList = new disposalassetlist();
                barcodeWiseDataList.setAssetId(cursor.getString(cursor.getColumnIndex(ASSETID)));
                barcodeWiseDataList.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                barcodeWiseDataList.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION)));
                barcodeWiseDataList.setBarcode(cursor.getString(cursor.getColumnIndex(EBarcodeId)));
                barcodeWiseDataList.setStatus(cursor.getString(cursor.getColumnIndex(Status)));

                barcodeWiseDataLists.add(barcodeWiseDataList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return barcodeWiseDataLists;
    }

    public List<ScheduleLocation> getAllLocationWise(String scheduleId, String empID) {
        List<ScheduleLocation> locations = new ArrayList<>();

        // Select All Query

        String whereClause = SCHEDULEID + "  =?";
        String[] whereArgs = new String[]{scheduleId};
        String orderBy = LocationName;
        String[] columns = {
                SCHEDULEID,
                EMPID,
                LOCATION, LocationName
        };
        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor cursor = db.rawQuery(selectQuery, null)

        String selectQuery = "SELECT * FROM " + scheduleLocation_TABLE + " WHERE " + SCHEDULEID + "  = '" + scheduleId + "' and " + EMPID + "  ='" + empID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScheduleLocation scheduleLocation = new ScheduleLocation();
                scheduleLocation.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                scheduleLocation.setEMPID(cursor.getString(cursor.getColumnIndex(EmpID)));
                scheduleLocation.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION)));
                scheduleLocation.setLocationName(cursor.getString(cursor.getColumnIndex(LocationName)));

                locations.add(scheduleLocation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return locations;
    }

    public List<ScheduleLocationTask> getAllTaskWise(String scheduleId, String location, String
            empId) {
        List<ScheduleLocationTask> scheduleLocationTasks = new ArrayList<>();

        // Select All Query

        String whereClause = SCHEDULEID + "  ='" + scheduleId + "' and " + LOCATION + "  ='" + location + "'";
        String[] whereArgs = new String[]{location};

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ScheduleLocationTask_TABLE + " WHERE " + SCHEDULEID + "  = '" + scheduleId + "' and " + LOCATION + "  ='" + location + "' and " + EMPID + "  ='" + empId + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.query(ScheduleLocationTask_TABLE, null, whereClause, whereArgs, null, null, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScheduleLocationTask scheduleLocationTask = new ScheduleLocationTask();
                scheduleLocationTask.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                scheduleLocationTask.setEMPID(cursor.getString(cursor.getColumnIndex(EmpID)));
                scheduleLocationTask.setACTIVITYTYPE(cursor.getString(cursor.getColumnIndex(ACTIVITYTYPE)));
                scheduleLocationTask.setENDTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                scheduleLocationTask.setSTARTTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                scheduleLocationTask.setLOCATION(cursor.getString(cursor.getColumnIndex(LOCATION)));

                scheduleLocationTasks.add(scheduleLocationTask);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return scheduleLocationTasks;
    }

    public List<ScheduleDetail_> getAllAssetDetail(String scheduleId, String
            activityType, String EmpId, String location) {
        List<ScheduleDetail_> scheduleDetail_s = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ScheduleDetail_TABLE + " WHERE " + SCHEDULEID + "  = '" + scheduleId + "' and " + ACTIVITYTYPE + "  = '" + activityType + "' and " + EMPID + "  = '" + EmpId + "' and " + LOCATION + "  = '" + location + "' and " + TRACKING + "  = '" + 0 + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.query(ScheduleLocationTask_TABLE, null, whereClause, whereArgs, null, null, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScheduleDetail_ scheduleDetail_ = new ScheduleDetail_();
                scheduleDetail_.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                scheduleDetail_.setACTIVITYTYPE(cursor.getString(cursor.getColumnIndex(ACTIVITYTYPE)));
                scheduleDetail_.setLOCATION(cursor.getString(cursor.getColumnIndex(LOCATION)));
                scheduleDetail_.setBLOB(cursor.getString(cursor.getColumnIndex(BLOB)));
                scheduleDetail_.setBLOB_IMAGE(cursor.getBlob(cursor.getColumnIndex(BLOB_IMAGES)));
                scheduleDetail_.setImagePath(cursor.getString(cursor.getColumnIndex(IMAGEPATH)));
                scheduleDetail_.setASSETID(cursor.getString(cursor.getColumnIndex(ASSETID)));
                scheduleDetail_.setBarCode(cursor.getString(cursor.getColumnIndex(BarCode)));
                scheduleDetail_.setEmpID(cursor.getString(cursor.getColumnIndex(EmpID)));
                scheduleDetail_.setITEMS(cursor.getString(cursor.getColumnIndex(ITEMS)));
                scheduleDetail_.setEMPNAME(cursor.getString(14));
                scheduleDetail_.setTRACKING(cursor.getString(cursor.getColumnIndex(TRACKING)));
                scheduleDetail_.setSTARTTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                scheduleDetail_.setENDTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                scheduleDetail_.setCurentStatus(cursor.getString(15));
                scheduleDetail_s.add(scheduleDetail_);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return scheduleDetail_s;
    }

    public List<ScheduleDetail_> getAllAssets() {
        List<ScheduleDetail_> scheduleDetail_s = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ScheduleDetail_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.query(ScheduleLocationTask_TABLE, null, whereClause, whereArgs, null, null, null);
        Log.d("count", cursor.getCount() + "");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ScheduleDetail_ scheduleDetail_ = new ScheduleDetail_();
                scheduleDetail_.setSCHEDULEID(cursor.getString(cursor.getColumnIndex(SCHEDULEID)));
                scheduleDetail_.setACTIVITYTYPE(cursor.getString(cursor.getColumnIndex(ACTIVITYTYPE)));
                scheduleDetail_.setLOCATION(cursor.getString(cursor.getColumnIndex(LOCATION)));
                scheduleDetail_.setBLOB(cursor.getString(cursor.getColumnIndex(BLOB)));
                scheduleDetail_.setBLOB_IMAGE(cursor.getBlob(cursor.getColumnIndex(BLOB_IMAGES)));
                String imagepath = cursor.getString(3) + "";
                Log.d("ColumnIndex", imagepath);
                scheduleDetail_.setImagePath(imagepath);
                scheduleDetail_.setITEMS(cursor.getString(cursor.getColumnIndex(ITEMS)));
                scheduleDetail_.setASSETID(cursor.getString(cursor.getColumnIndex(ASSETID)));
                scheduleDetail_.setBarCode(cursor.getString(cursor.getColumnIndex(BarCode)));
                scheduleDetail_.setEmpID(cursor.getString(cursor.getColumnIndex(EmpID)));
                scheduleDetail_.setEMPNAME(cursor.getString(14));
                scheduleDetail_.setTRACKING(cursor.getString(cursor.getColumnIndex(TRACKING)));
                scheduleDetail_.setSTARTTIME(cursor.getString(cursor.getColumnIndex(STARTTIME)));
                scheduleDetail_.setENDTIME(cursor.getString(cursor.getColumnIndex(ENDTIME)));
                scheduleDetail_.setCurentStatus(cursor.getString(15));
                scheduleDetail_s.add(scheduleDetail_);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return scheduleDetail_s;
    }

    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULEID, schedule.getSCHEDULEID());
        contentValues.put(SCHEDULEDESCRIPTION, schedule.getSCHEDULEDESCRIPTION());
        contentValues.put(STARTTIME, schedule.getSTARTTIME());
        contentValues.put(ENDTIME, schedule.getENDTIME());
        contentValues.put(Status, schedule.getStatus());

        // updating row
        return db.update(COMPLETED_TABLE, contentValues, SCHEDULEID + " = ?",
                new String[]{String.valueOf(schedule.getSCHEDULEID())});
    }


    public List<UserList> checkUserExist(String userPin, String password) {
        List<UserList> userLists = new ArrayList<>();

        String UserId = "", EmpID, UserName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE " + UserPin + "  ='" + userPin + "' and " + Password + "  ='" + password + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.query(ScheduleLocationTask_TABLE, null, whereClause, whereArgs, null, null, null);

        Log.d("count", cursor.getCount() + "");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserList userList = new UserList();
                UserId = cursor.getString(cursor.getColumnIndex(DataBaseHelper.UserId));
                EmpID = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EMPID));
                userList.setUserName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.UserName)));
                userList.setEMPID(EmpID);
                userList.setUserId(UserId);
                userLists.add(userList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return notes list
        return userLists;
    }


}
