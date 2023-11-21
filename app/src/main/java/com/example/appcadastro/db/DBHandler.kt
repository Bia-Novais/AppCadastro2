package com.example.appcadastro.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appcadastro.model.CourseModel

class DBHandler //criando um construtor para o manipulador de banco de dados
    (context: Context?):
    SQLiteOpenHelper(context, DB_NAME, null, BD_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE"+TABLE_NAME+"("
                + Nome_COL+"TEXT,"
                + Endereco_COL +"TEXT,"
                + Bairro_COL + "TEXT,"
                + Cep_COL + "TEXT,"
                + Cidade_COL + "TEXT,"
                + Estado_COL +  "TEXT,"
                + Telefone_COL + "TEXT"
                + Celular_COL + "TEXT)")

        //substituir não nulo afirmado
        db!!.execSQL(query)
    }

    fun addNovoCadastro(
        nome: String?,
        endereco: String?,
        bairro: String?,
        cep: String?,
        cidade: String?,
        estado: String?,
        telefone: String?,
        celular: String?
    ){
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(Nome_COL, nome)
        values.put(Endereco_COL, endereco)
        values.put(Bairro_COL, bairro)
        values.put(Cep_COL, cep)
        values.put(Cidade_COL, cidade)
        values.put(Estado_COL, estado)
        values.put(Telefone_COL, telefone)
        values.put(Celular_COL, celular)

        db.insert(TABLE_NAME, null, values)

        db.close()

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME)
        }
       onCreate(db)
    }

    companion object{

        private const val DB_NAME = "cadastrodb"

        private const val BD_VERSION = 1

        private const val TABLE_NAME = "meucadastro"

        private const val Nome_COL = "Nome"

        private const val Endereco_COL = "Endereco"

        private const val Bairro_COL = "Bairro"

        private const val Cep_COL = "CEP"

        private const val Cidade_COL = "Cidade"

        private const val Estado_COL = "Estado"

        private const val Telefone_COL = "Telefone"

        private const val Celular_COL = "Celular"
    }

    fun readCourses(): ArrayList<CourseModel>? {

        val db = this.readableDatabase

        val cursorCourses: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val courseModelArrayList: ArrayList<CourseModel> = ArrayList()

        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModelArrayList.add(
                    CourseModel(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8)
                    )
                )
            } while (cursorCourses.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorCourses.close()
        return courseModelArrayList
    }
}