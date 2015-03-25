package com.ecruit.dbase;

import java.util.ArrayList;
import java.util.List;

import com.ecruit.disp.RowItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler {

	public static final String TAG = "com.ecruit.dbase.DbHandler";

	// All Static Variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "HospMgmt";

	// Tables Name
	public static final String TABLE_USERS = "HospUsers";
	public static final String TABLE_DOCTORS = "Doctors";
	public static final String TABLE_PATIENTS = "Patients";
	public static final String TABLE_PAT_PRESC = "PatPresc";
	public static final String TABLE_PAT_FEES = "PatFees";
	public static final String TABLE_PAT_TEST = "PatTest";
	public static final String TABLE_DOC_TIME = "DocAvailableTime";

	// Common Column Names
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_BRANCH = "branch";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";

	// Column Names - Users table
	public static final String KEY_UNAME = "username";
	public static final String KEY_PASS = "password";
	public static final String KEY_CAT = "usertype";

	// Column Name - Patients table
	public static final String KEY_GENDER = "gender";
	public static final String KEY_AGE = "age";
	public static final String KEY_TYPE = "type";
	public static final String KEY_APPOINT = "appointment";

	// Column Name - Patients Prescription table
	public static final String KEY_TESTS = "tests";
	public static final String KEY_MEDICINES = "medicines";
	public static final String KEY_INJECTIONS = "injections";

	// Column Name - Patients Test Report
	public static final String KEY_REPORT = "testreport";
	public static final String KEY_STATUS = "teststatus";

	// Column Name - Patients Fee table
	public static final String KEY_DOC_FEE = "doctorfee";
	public static final String KEY_TEST_FEE = "testfee";
	public static final String KEY_MED_FEE = "medicinefee";
	public static final String KEY_PAID_FEE = "paidfee";

	// Create Statement - Users table
	private static final String CREATE_USER_TABLE = "CREATE TABLE "
			+ TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME
			+ " TEXT, " + KEY_UNAME + " TEXT, " + KEY_PASS + " TEXT, "
			+ KEY_CAT + " TEXT" + ")";

	// Create Statement - Doctors table
	private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE "
			+ TABLE_DOCTORS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT, " + KEY_BRANCH + " TEXT, " + KEY_PHONE
			+ " TEXT, " + KEY_EMAIL + " TEXT," + KEY_ADDRESS + " TEXT" + ")";

	// Create Statement - Patients table
	private static final String CREATE_PATIENTS_TABLE = "CREATE TABLE "
			+ TABLE_PATIENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT, " + KEY_GENDER + " TEXT, " + KEY_AGE
			+ " TEXT, " + KEY_ADDRESS + " TEXT," + KEY_BRANCH + " TEXT,"
			+ KEY_APPOINT + " TEXT," + KEY_PHONE + " TEXT" + ")";

	// Create Statement - Prescription table
	private static final String CREATE_PRESC_TABLE = "CREATE TABLE "
			+ TABLE_PAT_PRESC + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT, " + KEY_TESTS + " TEXT, " + KEY_MEDICINES
			+ " TEXT, " + KEY_INJECTIONS + " TEXT" + ")";

	// Create Statement - Patient Fee table
	private static final String CREATE_FEES_TABLE = "CREATE TABLE "
			+ TABLE_PAT_FEES + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT, " + KEY_DOC_FEE + " TEXT, " + KEY_TEST_FEE
			+ " TEXT, " + KEY_MED_FEE + " TEXT," + KEY_PAID_FEE + " TEXT" + ")";

	// Create Statement - Patient Fee table
	private static final String CREATE_TEST_TABLE = "CREATE TABLE "
			+ TABLE_PAT_TEST + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT, " + KEY_TESTS + " TEXT, " + KEY_REPORT
			+ " TEXT, " + KEY_STATUS + " TEXT" + ")";

	// Context of application who uses us.
	private final Context context;

	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public DbHandler(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
		db = myDBHelper.getWritableDatabase();
	}

	// Add new User
	public void addUser(Users user) {

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_UNAME, user.getUser_name());
		values.put(KEY_PASS, user.getPassword());
		values.put(KEY_CAT, user.getUser_cat());

		db.insert(TABLE_USERS, null, values);
		Log.d(TAG, user.getName() + " created...");
	}

	// Getting Single User
	public Users getUser(String name) {
		Users user = new Users();

		try {
			Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID,
					KEY_NAME, KEY_UNAME, KEY_PASS, KEY_CAT }, KEY_NAME + "=?",
					new String[] { name }, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			user = new Users(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4));
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}

		return user;
	}

	// Getting All Contacts
	public List<Users> getAllUsers() {
		List<Users> userlist = new ArrayList<Users>();
		// Select all users
		String selectQuery = "SELECT * FROM " + TABLE_USERS;

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					Users user = new Users();
					user.setUser_id(cursor.getString(0));
					user.setName(cursor.getString(1));
					user.setUser_name(cursor.getString(2));
					user.setPassword(cursor.getString(3));
					user.setUser_cat(cursor.getString(4));

					// Adding Users to List
					userlist.add(user);
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		// return users list
		return userlist;
	}

	// Updating Single User
	public int updateUser(Users user) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_UNAME, user.getUser_name());
		values.put(KEY_PASS, user.getPassword());
		values.put(KEY_CAT, user.getUser_cat());

		// updating row
		return db.update(TABLE_USERS, values, KEY_ID + "=?",
				new String[] { String.valueOf(user.getUser_id()) });
	}

	// Deleting Single User
	public void deleteUser(Users user) {
		db.delete(TABLE_USERS, KEY_ID + "=?",
				new String[] { String.valueOf(user.getUser_id()) });
	}

	// Getting All Contacts
	public Users getUsers(String uname, String pass, String ucat) {
		Users user = new Users();
		// Select all users
		String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
				+ KEY_UNAME + " = '" + uname + "' AND " + KEY_PASS + " = '"
				+ pass + "' AND " + KEY_CAT + " = '" + ucat + "'";

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor != null) {
				cursor.moveToFirst();
			}
			user.setUser_id(cursor.getString(0));
			user.setName(cursor.getString(1));
			user.setUser_name(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setUser_cat(cursor.getString(4));
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		return user;
	}

	// Add new Patient
	public void addPatient(Patient patient) {

		String name = patient.getName();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_GENDER, patient.getGender());
		values.put(KEY_AGE, patient.getAge());
		values.put(KEY_ADDRESS, patient.getAddress());
		values.put(KEY_BRANCH, patient.getBranch());
		values.put(KEY_APPOINT, patient.getDoc());
		values.put(KEY_PHONE, patient.getPh_no());

		ContentValues values2 = new ContentValues();
		values2.put(KEY_NAME, name);

		ContentValues values3 = new ContentValues();
		values3.put(KEY_NAME, name);
		values3.put(KEY_DOC_FEE, "500");

		ContentValues values4 = new ContentValues();
		values4.put(KEY_NAME, name);

		db.insert(TABLE_PATIENTS, null, values);
		db.insert(TABLE_PAT_PRESC, null, values2);
		db.insert(TABLE_PAT_FEES, null, values3);
		db.insert(TABLE_PAT_TEST, null, values4);

		Log.d(TAG, "New " + patient.getName() + " registered...");
	}
	
	// Get All Patients
	public List<RowItem> getAllPatients() {
		List<RowItem> rowItems = new ArrayList<RowItem>();
		String selectQuery = "select id,name,gender,age from patients";
		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					RowItem rowItem = new RowItem();
					rowItem.setId(cursor.getString(0));
					rowItem.setName(cursor.getString(1));
					rowItem.setGender(cursor.getString(2));
					rowItem.setAge(cursor.getString(3));

					// Adding Users to List
					rowItems.add(rowItem);
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		return rowItems;
	}

	// Get Patients based on Doctor's Name
	public List<RowItem> getDocPatients(String docName) {
		List<RowItem> rowItems = new ArrayList<RowItem>();
		String selectQuery = "select id,name,gender,age from patients where appointment='"
				+ docName + "'";
		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					RowItem rowItem = new RowItem();
					rowItem.setId(cursor.getString(0));
					rowItem.setName(cursor.getString(1));
					rowItem.setGender(cursor.getString(2));
					rowItem.setAge(cursor.getString(3));

					// Adding Users to List
					rowItems.add(rowItem);
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		return rowItems;
	}

	// Get Single patient
	public Patient getPatient(String id, String name) {
		Patient patient = new Patient();
		String selectQuery = "select * from patients where id = " + id
				+ " and name='" + name + "'";
		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor != null) {
				cursor.moveToFirst();
			}
			patient = new Patient(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7));

		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		return patient;
	}

	// Add doctor details
	public void addDoctor(Doctors doc) {
		ContentValues values = new ContentValues();

		values.put(KEY_NAME, doc.getDoc_name());
		values.put(KEY_BRANCH, doc.getDoc_branch());
		values.put(KEY_PHONE, doc.getDoc_phone());
		values.put(KEY_EMAIL, doc.getDoc_email());
		values.put(KEY_ADDRESS, doc.getDoc_address());

		db.insert(TABLE_DOCTORS, null, values);
		Log.d(TAG, "Doctor " + doc.getDoc_name() + " updated details");
	}

	// Update doctor details
	public int updateDoctor(Doctors doc) {
		ContentValues values = new ContentValues();

		values.put(KEY_NAME, doc.getDoc_name());
		values.put(KEY_BRANCH, doc.getDoc_branch());
		values.put(KEY_PHONE, doc.getDoc_phone());
		values.put(KEY_EMAIL, doc.getDoc_email());
		values.put(KEY_ADDRESS, doc.getDoc_address());

		Log.d(TAG, "Doctor " + doc.getDoc_name() + " updated details");
		return db.update(TABLE_DOCTORS, values, KEY_NAME + "= ?",
				new String[] { doc.getDoc_name() });
	}

	// Getting Single Doctor
	public Doctors getDoctor(String name) {
		Doctors doctor = new Doctors();
		try {
			Cursor cursor = db.query(TABLE_DOCTORS, new String[] { KEY_ID,
					KEY_NAME, KEY_BRANCH, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS },
					KEY_NAME + "=?", new String[] { name }, null, null, null,
					null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
			doctor = new Doctors(cursor.getString(1), cursor.getString(2),
					cursor.getString(3), cursor.getString(4),
					cursor.getString(5));
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());

		}
		return doctor;
	}

	// Getting Doctors list of particular branch
	public List<String> getAllDocs(String branch) {
		List<String> docList = new ArrayList<String>();
		docList.clear();
		docList.add("Select doctor");
		Cursor cursor = null;

		// Select all doctors by branch
		String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_DOCTORS
				+ " WHERE " + KEY_BRANCH + " = '" + branch + "'";

		try {
			cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					// Adding Doctors name to List
					docList.add(cursor.getString(0));
					Log.d("TAG", "Doctors list : " + cursor.getString(0));
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		cursor.close();
		return docList;
	}

	// Getting All Contacts
	public List<Doctors> getAllDoctors() {
		List<Doctors> doctorlist = new ArrayList<Doctors>();
		// Select all users
		String selectQuery = "SELECT * FROM " + TABLE_DOCTORS;

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					Doctors doctor = new Doctors();
					doctor.setDoc_name(cursor.getString(1));
					doctor.setDoc_branch(cursor.getString(2));
					doctor.setDoc_phone(cursor.getString(3));
					doctor.setDoc_email(cursor.getString(4));
					doctor.setDoc_address(cursor.getString(5));

					// Adding Users to List
					doctorlist.add(doctor);
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		// return users list
		return doctorlist;
	}

	// Database helper Class
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			// Creating Tables
			_db.execSQL(CREATE_USER_TABLE);
			_db.execSQL(CREATE_DOCTOR_TABLE);
			_db.execSQL(CREATE_PATIENTS_TABLE);
			_db.execSQL(CREATE_PRESC_TABLE);
			_db.execSQL(CREATE_FEES_TABLE);
			_db.execSQL(CREATE_TEST_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			// Upgrading Table
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAT_PRESC);
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAT_FEES);
			_db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAT_TEST);

			// CREATE TABLE
			onCreate(_db);
		}
	}
}
