package fr.eyal.datalib.sample.netflix.data.model.synopsis;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.data.model.ResponseBusinessObjectDAO;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.util.Out;
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;

public class SynopsisBase extends ResponseBusinessObjectDAO {

    private static final String TAG = SynopsisBase.class.getSimpleName();

	/**
	 * List of request parameters that will define the fingerprint composition of the object
	 */
	public static final String[] FINGERPRINT_KEYS = {
	"movie_id",
	};

	//list of content
	public String error;
	public String synopsis;

    public SynopsisBase() {
        super();
    }

    public SynopsisBase(final long id) {
        super(id);
    }

    public SynopsisBase(final String url) {
        super(url);
    }

    /**
     * DATABASE MANAGEMENT
     */

    /**
     * Constants used with a ContentProvider's access
     */
    public static final String CONTENT_PATH = "synopsis";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "synopsis";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__URL = "_url";
    public static final String FIELD__TTL = "_ttl";
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    //list of content
    public static final String FIELD_ERROR = "error";
    public static final String FIELD_SYNOPSIS = "synopsis";


    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__URL, // url
            FIELD__TTL, // ttl
            FIELD__UPDATED_AT, // updated_at
		    //list of content
		    FIELD_ERROR, //error";
		    FIELD_SYNOPSIS, //synopsis";
   };

    /**
     * SQL fields' types in the table
     */
    static String[] DATABASE_TABLE_FIELDS_TYPES = {
            // prevision_meteo_meteo_weather
            // header
            "integer", "text", "integrer", "integrer",
		
		
		    //list of content
			"text",
			"text",

    };

    /**
     * SQLite scripts to create the table
     */
    public static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" 
			+ DATABASE_TABLE_FIELDS_NAMES[0] + " " + DATABASE_TABLE_FIELDS_TYPES[0] + " PRIMARY KEY AUTOINCREMENT" + ", "
            + DATABASE_TABLE_FIELDS_NAMES[1] + " " + DATABASE_TABLE_FIELDS_TYPES[1] + ", "
            + DATABASE_TABLE_FIELDS_NAMES[2] + " " + DATABASE_TABLE_FIELDS_TYPES[2] + ", "
            + DATABASE_TABLE_FIELDS_NAMES[3] + " " + DATABASE_TABLE_FIELDS_TYPES[3] + ", "

		    //list of content
			+ DATABASE_TABLE_FIELDS_NAMES[4] + " " + DATABASE_TABLE_FIELDS_TYPES[4] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[5] + " " + DATABASE_TABLE_FIELDS_TYPES[5] + "); "
            + "CREATE INDEX " + DATABASE_TABLE_NAME + "_" + DATABASE_TABLE_FIELDS_NAMES[0] + " " + DATABASE_TABLE_NAME + " (" + DATABASE_TABLE_FIELDS_NAMES[0] + ");"
            + "CREATE INDEX " + DATABASE_TABLE_NAME + "_" + DATABASE_TABLE_FIELDS_NAMES[1] + " ON " + DATABASE_TABLE_NAME + " (" + DATABASE_TABLE_FIELDS_NAMES[1] + ");";


    @Override
    protected ContentResolver getContentResolver() {
        try {
        return NetflixProvider.getContentResolver();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        // we create the values to add
        final ContentValues values = new ContentValues();

        if (_id != ID_INVALID)
            values.put(FIELD__ID, _id);

        values.put(FIELD__URL, _url);
        values.put(FIELD__TTL, _ttl);
        if (_updatedAt != null) {
            values.put(FIELD__UPDATED_AT, _updatedAt.getTimeInMillis() / 1000);
        }

	
	    //list of content
	    values.put(FIELD_ERROR, error);
	    values.put(FIELD_SYNOPSIS, synopsis);
		
        return values;
    }

    @Override
    protected void fillFromDatabaseByUrl(final String url) {
        // on defini les conditions d'acces a l'objet
        final String[] columns = new String[DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = DATABASE_TABLE_FIELDS_NAMES[i];
        }

        final String where = FIELD__URL + " LIKE ?";
        final String[] args = { url + "" };

        // we check the entry's existence
        final Cursor cursor = mResolver.query(CONTENT_URI, 
                columns, // all the table's columns
                where, // _url LIKE ?
                args, // url
                FIELD__UPDATED_AT + " DESC"); // ORDER BY updated_at DESC

        // we fill the object with the cursor content
        if (cursor.moveToFirst()) {
            fillObjectFromCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        // we get the child infos
        fillChildrenFromDatabase();
    }

    @Override
    protected void fillFromDatabaseById(final long id) {
        // we define the access condition to the object
        final String[] columns = new String[DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = DATABASE_TABLE_FIELDS_NAMES[i];
        }

        final String where = FIELD__ID + "=?";
        final String[] args = { id + "" };

        // we check the existence inside the database
        final Cursor cursor = mResolver.query(CONTENT_URI, // prevision_meteo
                columns, // _id
                where, // id=?
                args, // id
                FIELD__UPDATED_AT + " DESC"); // ORDER BY updated_at DESC

        // we fill the object thanks to the Cursor recieved
        if (cursor.moveToFirst()) {
            fillObjectFromCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        // we get the childs content
        fillChildrenFromDatabase();

    }

    @Override
    protected void fillObjectFromCursor(final Cursor cursor) {

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
			_id = cursor.getLong(i++);
            _url = cursor.getString(i++);
            _ttl = cursor.getInt(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);

		
		    //list of content
			error = cursor.getString(i++);
			synopsis = cursor.getString(i++);

        } else {
            _id = ID_INVALID;
        }

    }


    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {
        _url = request.getFingerprint(FINGERPRINT_KEYS);
        save();
    }

    @Override
    public ContentProviderResult[] save() throws OperationApplicationException, RemoteException {

        // on defini les conditions d'acces a l'objet
        final String[] columns = { FIELD__ID };
        final String where = FIELD__URL + " LIKE ?"; // id LIKE {this.id}
        final String[] args = { _url + "" };

        return save(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);

    }

	@Override
	protected int updateChildrenId(long[] ids, int index, int parentIndex) {
		// Nothing to do

		return index;
	}

	@Override
	public int updateId(long[] ids, int index, int parentIndex) {
		return updateId(ids, index);
	}

    @Override
    public ContentProviderResult[] delete() throws OperationApplicationException, RemoteException {
        // we define the access conditions to the object
        final String[] columns = { FIELD__ID };
        final String where = FIELD__URL + "=?"; // _url LIKE {this.url}
        final String[] args = { _url + "" };

        return delete(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);
    }

    @Override
    protected void updateIntoTheDatabase(final ArrayList<ContentProviderOperation> batch) {
        updateIntoTheDatabase(batch, CONTENT_URI);
    }

    @Override
    public boolean deleteFromDatabase(final ArrayList<ContentProviderOperation> batch) {
        return deleteFromDatabase(batch, CONTENT_URI);
    }


    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, final int previousResult) {
		// Nothing to do
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		// Nothing to do
    }

    @Override
    public void deleteChildsFromDatabase(final ArrayList<ContentProviderOperation> batch) {
		// Nothing to do
    }



    @Override
    protected void fillChildrenFromDatabase() {
		// Nothing to do
    }

	/**
     * This function build an array of {@link SynopsisBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link Synopsis}> if you want it as {@link Synopsis}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<SynopsisBase> result = new ArrayList<SynopsisBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final SynopsisBase newObject = new SynopsisBase();
                newObject.fillObjectFromCursor(c);
                // if it's asked we fill the childs of the item
                if (join) {
                    newObject.fillChildrenFromDatabase();
                }
                // we add the element to the result
                result.add(newObject);
            } while (c.moveToNext());
        }

        return result;
    }


    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<SynopsisBase> CREATOR = new Parcelable.Creator<SynopsisBase>() {
	    @Override
	    public SynopsisBase createFromParcel(final Parcel in) {
	        return new SynopsisBase(in);
	    }
	
	    @Override
	    public SynopsisBase[] newArray(final int size) {
	        return new SynopsisBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		// ResponseBusinessObject DAO
		dest.writeLong(_ttl);
		dest.writeString(_url);
		
		// Business Object DAO
		dest.writeLong(_id);
		dest.writeLong(_updatedAt.getTimeInMillis());
		
		//list of content
		dest.writeString(error);
		dest.writeString(synopsis);
		
	}

	public SynopsisBase(final Parcel in) {
		// ResponseBusinessObject DAO
		_ttl = in.readLong();
		_url = in.readString();
		
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of content
		error = in.readString();
		synopsis = in.readString();	
		
		
	}    


}

