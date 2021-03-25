package com.example.greendetect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GreenDetect.db";
    public static final String USER_TABLE = "user_table";
    public static final String GROCERY_TABLE = "grocery_table";
    private static final int version = 16;
    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_GROCERYPRODUCT = "GROCERYPRODUCT";
    public static final String COL_INFO="INFORMATION";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + USER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,EMAIL TEXT,PASSWORD TEXT)");
        db.execSQL("Create table " + GROCERY_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,GROCERYPRODUCT TEXT,INFORMATION TEXT)");
        insertProduct(db);

        //db.execSQL("INSERT INTO " + GROCERY_TABLE +"(ID,GROCERYPRODUCT,INFORMATION) VALUES(1,Banana,niceee)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+USER_TABLE);
        db.execSQL("drop table if exists "+GROCERY_TABLE);
        onCreate(db);
    }

    public void insertProduct(SQLiteDatabase db){
        insertGrocery("Banana","Vitamin A, B1, B2, C and E, as well as potassium and magnesium content is high banana, iron, calcium, phosphorus, sodium and iodine rich in.  Banana, which is a mineral and vitamin store, has also established hearts with its taste.They may help prevent asthma, cancer, high blood pressure, diabetes, cardiovascular disease, and digestive problems.\n" +
                "Ripen bananas at room temperature and add them to cereal for a tasty breakfast.\n" +
                "People who use beta blockers should not suddenly increase their intake of banana.\n",db);
        insertGrocery("Pinapple","Citrus fruits include up vitamin C, pineapple, Vitamin A, B1, B3, B6, omega 3 and omega 6 fatty acids, potassium, iron, manganese, calcium, copper, zinc, folate and phosphorus.\n" +
                "Pineapple, thanks to its high fiber content, is a source of energy and this feature helps to lose weight. A powerful antioxidant, strengthens pineapple immunity. The skin is smooth, the hair and nails are strong.\n" +
                "Warning: excessive and unconsciously consumed bananas can cause allergic reactions. If you think you're in a risk group, it's better to get a doctor's opinion. In addition, the pineapple should not be used with blood thinners.\n",db);
        insertGrocery("Apricot","Apricot with high nutritional value; A, B, C, E vitamins as well as iron, calcium, phosphorus, magnesium and fiber store. The apricot, which can be consumed as dry and dry, facilitates digestion, provides a feeling of satiety, helps weight control and is good for constipation. With its antioxidant properties, it protects the body from diseases.",db);
        insertGrocery("Apple","Ideal for slimming. There are only 50 calories in 1 apple. Due to the \"petkin\" substance it contains, it is \"satisfying feature\". Because it is low calorie, it allows you to get tired without weight.\n" +
                "Before eating, a renewed apple prevents the formation of bacteria in the gut and prevents constipation.\n" +
                "Because it contains vitamin C, it strengthens the immune system and protects it from diseases.\n" +
                "When eaten after a meal, it cleans the teeth and protects your teeth.\n",db);
        insertGrocery("Strawberry","As a great source of fiber, vitamin C, and antioxidants, strawberries can benefit your health in the following ways.Improves ımmune functıonınglowers blood pressure..\n" +
                "Treats symptoms of arthrıtıs and gout.Offers protectıon agaınst cancer.Promotes healthy eyesıght.Regulates blood sugar.Lowers rısk of stroke.\n",db);
        insertGrocery("Cherry","Some of the benefits of cherry containing many types of vitamins such as vitamins A, B and C are as follows;\n" +
                "\n" +
                "Relaxes the digestive system. Fixes the problem of constipation. Makes diarrhea effect in people with constipation. Likewise, the problem of indigestion is also solved.\n" +
                "It is a good source of vitamin C. It also gives strength to the body and helps to eliminate vitamin deficiencies.\n" +
                "It is also a fruit useful to muscles and bones. Rheumatism, joint, muscle and bone pain has been reported to provide a great benefit.\n",db);
        insertGrocery("Lychee","Lychee is a small fruit with a sweet smell and taste, which is available in the summer. It is a small fruit packed with healthy nutrients. It has rough skin outside while contains juicy flesh inside. It is used as medicine in China\n" +
                "Lychee contains good amount of antioxidant Vitamin C, Vitamin B-complex and phytonutrient flavonoids.\n",db);


    }

    public boolean insert(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PASSWORD,password);
        long result=db.insert(USER_TABLE,null,contentValues);
        if(result==-1){
            //If there is an error
            return false;
        }
        else
            return true;
    }

    public void insertGrocery(String product,String info, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_GROCERYPRODUCT,product);
        contentValues.put(COL_INFO,info);
        db.insert(GROCERY_TABLE,null,contentValues);
    }

    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE,COL_ID + " =?", new String[]{id});

    }

    public boolean checkemail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where "+ COL_EMAIL +" =?",new String[]{email});
        if(cursor.getCount()>0){
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_TABLE,null);
        return res;
    }

    public Cursor getAllDataGrocery(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + GROCERY_TABLE,null);
        return res;
    }
    public Cursor getGroceryData(String groceryName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + GROCERY_TABLE + " where "+ COL_GROCERYPRODUCT + " = ?",new String[]{groceryName});
        return res;

    }

    public boolean changePassword(String email,String oldpassword,String newpassword) {
        if (checkemailpass(email, oldpassword)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_PASSWORD, newpassword);
            db.update(USER_TABLE, contentValues, "ID = ?", new String[]{oldpassword});
            return true;
        } else {
            return false;
        }
    }
    public boolean changeEmail(String oldemail,String newemail, String password){
        if(checkemailpass(oldemail,password)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EMAIL,newemail);
            db.update(USER_TABLE,contentValues, COL_EMAIL + " = ?",new String[]{oldemail});
            return true;
        }
        else{
            return false;
        }

    }

    public boolean checkemailpass(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where "+ COL_EMAIL + " = ? and " +COL_PASSWORD + " =?",new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }
        else
            return false;
    }


}
