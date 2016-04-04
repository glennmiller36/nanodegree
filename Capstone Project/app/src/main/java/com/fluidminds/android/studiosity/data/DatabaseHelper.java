package com.fluidminds.android.studiosity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fluidminds.android.studiosity.data.DataContract.CardEntry;
import com.fluidminds.android.studiosity.data.DataContract.DeckEntry;
import com.fluidminds.android.studiosity.data.DataContract.SubjectEntry;
import com.fluidminds.android.studiosity.data.DataContract.QuizEntry;

/**
 * Manages a local database data.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "studiosity.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            // enable CASCADE DELETE
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold Subjects.
        final String SQL_CREATE_SUBJECT_TABLE = "CREATE TABLE " + SubjectEntry.TABLE_NAME + " (" +
                SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SubjectEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE COLLATE NOCASE, " +
                SubjectEntry.COLUMN_COLOR + " INTEGER NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_SUBJECT_TABLE);

        // Create a table to hold Card Decks.
        final String SQL_CREATE_DECK_TABLE = "CREATE TABLE " + DeckEntry.TABLE_NAME + " (" +
                DeckEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DeckEntry.COLUMN_SUBJECT_ID + " INTEGER NOT NULL, " +
                DeckEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE COLLATE NOCASE, " +
                " FOREIGN KEY(" + DeckEntry.COLUMN_SUBJECT_ID + ") REFERENCES " + SubjectEntry.TABLE_NAME + "(" + SubjectEntry._ID + ") ON DELETE CASCADE " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_DECK_TABLE);

        // Create a table to hold Cards for a Deck.
        final String SQL_CREATE_CARD_TABLE = "CREATE TABLE " + CardEntry.TABLE_NAME + " (" +
                CardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CardEntry.COLUMN_DECK_ID + " INTEGER NOT NULL, " +
                CardEntry.COLUMN_QUESTION + " TEXT NOT NULL UNIQUE COLLATE NOCASE, " +
                CardEntry.COLUMN_ANSWER + " TEXT NOT NULL, " +
                CardEntry.COLUMN_NUM_CORRECT + " INTEGER NOT NULL, " +
                CardEntry.COLUMN_NUM_ATTEMPTED + " INTEGER NOT NULL, " +
                CardEntry.COLUMN_PERCENT_CORRECT + " INTEGER NOT NULL, " +
                " FOREIGN KEY(" + CardEntry.COLUMN_DECK_ID + ") REFERENCES " + DeckEntry.TABLE_NAME + "(" + DeckEntry._ID + ") ON DELETE CASCADE " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_CARD_TABLE);

        // Create a table to hold Quiz history.
        final String SQL_CREATE_QUIZ_TABLE = "CREATE TABLE " + QuizEntry.TABLE_NAME + " (" +
                QuizEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuizEntry.COLUMN_DECK_ID + " INTEGER NOT NULL, " +
                QuizEntry.COLUMN_START_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                QuizEntry.COLUMN_NUM_CORRECT + " INTEGER NOT NULL, " +
                QuizEntry.COLUMN_TOTAL_CARDS + " INTEGER NOT NULL, " +
                QuizEntry.COLUMN_PERCENT_CORRECT + " INTEGER NOT NULL, " +
                " FOREIGN KEY(" + QuizEntry.COLUMN_DECK_ID + ") REFERENCES " + DeckEntry.TABLE_NAME + "(" + DeckEntry._ID + ") ON DELETE CASCADE " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_QUIZ_TABLE);

        seedData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    private void seedData(SQLiteDatabase sqLiteDatabase) {
        // Subjects
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT (Name, Color) VALUES ('U.S. Geography', -12627531)");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT (Name, Color) VALUES ('abcdefghijklmnopqrstuvwxyz', -14575885)");

        // Decks
        sqLiteDatabase.execSQL("INSERT INTO DECK (SubjectId, Name) VALUES (1, 'U.S. Airport Codes')");
        sqLiteDatabase.execSQL("INSERT INTO DECK (SubjectId, Name) VALUES (1, 'U.S. State Capitals')");

        // Cards
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'BNA', 'Nashville International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'BWI', 'Baltimore/Washington International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'DFW', 'Dallas Fort Worth International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'DTW', 'Detroit Metropolitan Wayne County', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'JFK', 'John F Kennedy International, New York', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'LAS', 'McCarran International, Las Vegas', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'MCO', 'Orlando International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'MSP', 'Minneapolis-St Paul International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'ORD', 'Chicago O’Hare International', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (1, 'PDX', 'Portland International', 0, 0, 0)");

        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Arizona Executing tasks: [clean, :app:generateDebugSources, :app:prepareDebugUnitTestDependencies, :app:mockable', 'Phoenix Executing tasks: [clean, :app:generateDebugSources, :app:prepareDebugUnitTestDependencies, :app:mockable', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'California', 'Sacramento', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Illinois', 'Springfield', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Louisiana', 'Baton Rouge', 0, 0, 80)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Maryland', 'Annapolis', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Massachusetts', 'Boston', 0, 0, 70)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Minnesota', 'St. Paul', 0, 0, 0)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'New York', 'Albany', 0, 0, 60)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Ohio', 'Columbus', 0, 0, 50)");
        sqLiteDatabase.execSQL("INSERT INTO CARD (DeckId, Question, Answer, NumCorrect, NumAttempted, PercentCorrect) VALUES (2, 'Washington', 'Olympia', 0, 0, 40)");

        // Quiz History
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 10)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 30)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 80)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 20)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 65)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 100)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 45)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 35)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 80)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 20)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 80)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 85)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 65)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 100)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 45)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 35)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 80)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 20)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-01 13:01:01.126', 8, 10, 80)");
        sqLiteDatabase.execSQL("INSERT INTO QUIZ (DeckId, StartDate, NumCorrect, TotalCards, PercentCorrect) VALUES (2, '2014-03-02 15:01:01.126', 5, 10, 85)");
    }
}
