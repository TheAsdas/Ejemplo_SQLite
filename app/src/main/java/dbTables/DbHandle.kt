package dbTables

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandle (context: Context): SQLiteOpenHelper(context, dbName, null, dbVersion)
{
    companion object
    {
        private var dbName = "appDb";
        private var dbVersion = 1;

        private var users_table = "usersTable";
        private var users_id = "id";
        private var users_col_nombre = "nombre";
        private var users_col_apellido = "apellido";
        private var users_col_rut = "rut";
        private var users_col_pass = "clave";
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val dbCreator = "CREATE TABLE $users_table (" +
                "$users_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$users_col_nombre TEXT, " +
                "$users_col_apellido TEXT, " +
                "$users_col_rut TEXT, " +
                "$users_col_pass TEXT)";

        p0?.execSQL(dbCreator);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $users_table")
    }

    fun insertPersona(person: Person): Long
    {
        val db = this.writableDatabase;
        val values = ContentValues();

        values.put(users_col_nombre, person.nombre);
        values.put(users_col_apellido, person.apellido);
        values.put(users_col_rut, person.rut);
        values.put(users_col_pass, person.pass);

        return db.insert(users_table, null, values);
    }

    fun selectPersona(): List<Person>
    {
        val listPersons: ArrayList<Person> = ArrayList();
        val query = "SELECT * FROM $users_table";
        val cursor: Cursor? = readableDatabase.rawQuery(query, null);

        if (cursor!!.moveToFirst())
        {
            do
            {
                listPersons.add(Person(
                    cursor.getInt(cursor.getColumnIndex(users_id)),
                    cursor.getString(cursor.getColumnIndex(users_col_nombre)),
                    cursor.getString(cursor.getColumnIndex(users_col_apellido)),
                    cursor.getString(cursor.getColumnIndex(users_col_rut)),
                    cursor.getString(cursor.getColumnIndex(users_col_pass)))
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listPersons;
    }

    fun deletePersona(p: Person)
    {
        writableDatabase.delete(
                users_table,
                "$users_id = ${p.id}",
                null
        );
    }

    fun deletePersona(id: Int)
    {
        writableDatabase.delete(
            users_table,
            "$users_id = $id",
            null
        );
    }

    fun updatePersona(p: Person)
    {
        val values = ContentValues();

        values.put(users_col_nombre, p.nombre);
        values.put(users_col_apellido, p.apellido);
        values.put(users_col_rut, p.rut);
        values.put(users_col_pass, p.pass);

        writableDatabase.update(
                users_table,
                values,
                "$users_id = ${p.id}",
                null
        );
    }

}