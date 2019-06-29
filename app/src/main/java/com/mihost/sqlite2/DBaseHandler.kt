package com.mihost.sqlite2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBaseHandler(context: Context) :SQLiteOpenHelper(context, DB_NAME, FACTORY, VERSION){

    companion object {

        internal val DB_NAME = "DBusers"
        internal val FACTORY = null
        internal val VERSION = 1
        val NOMBRE_TABLA = "usuarios"
        val COLUMNA_ID = "id"
        val COLUMNA_NOMBRE = "usuario"
        val COLUMNA_PASSWORD = "password"
        val COLUMNA_NIVEL = "nivel"
    }


    override fun onCreate(db: SQLiteDatabase?) {

        val CREAR_TABLA_USUARIOS = "CREATE TABLE $NOMBRE_TABLA (" +
                "$COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMNA_NOMBRE TEXT," +
                "$COLUMNA_PASSWORD TEXT," +
                "$COLUMNA_NIVEL INTEGER)"

        db?.execSQL(CREAR_TABLA_USUARIOS)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }



    fun obtenerUsuarios(mContext: Context) : ArrayList<Usuarios> {

        val consulta = "Select * From $NOMBRE_TABLA"

        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery(consulta, null)
        val usuarios = ArrayList<Usuarios>()

        if(cursor.count == 0){
            Toast.makeText(mContext, "NO  se encontró registros", Toast.LENGTH_LONG).show()

        } else {
/*            while (cursor.moveToNext()){
                val usuario = Usuarios()
                usuario.idUsuario = cursor.getInt(cursor.getColumnIndex(COLUMNA_ID))
                usuario.nombreUsuario = cursor.getString(cursor.getColumnIndex(COLUMNA_NOMBRE))
                usuario.passwordUsuario= cursor.getString(cursor.getColumnIndex(COLUMNA_PASSWORD))
                usuario.nivelUsuario = cursor.getInt(cursor.getColumnIndex(COLUMNA_NIVEL))
                usuarios.add(usuario)

            }*/

            // que se adpate parar borrar tambien adaptamos este while y vez del anterior
            cursor.moveToFirst()
            while (!cursor.isAfterLast()){
                val usuario = Usuarios()
                usuario.idUsuario = cursor.getInt(cursor.getColumnIndex(COLUMNA_ID))
                usuario.nombreUsuario = cursor.getString(cursor.getColumnIndex(COLUMNA_NOMBRE))
                usuario.passwordUsuario= cursor.getString(cursor.getColumnIndex(COLUMNA_PASSWORD))
                usuario.nivelUsuario = cursor.getInt(cursor.getColumnIndex(COLUMNA_NIVEL))
                usuarios.add(usuario)
                cursor.moveToNext()

            }


            Toast.makeText(mContext, "${cursor.count.toString()} registros encontrados", Toast.LENGTH_LONG).show()

        }

        cursor.close()
        db.close()
        return usuarios


    }

    fun agregarUsuarios(mContext: Context, datosUsuario: Usuarios){


        // habilitamos la base en modo escritura  poder escribir en ella
        val db: SQLiteDatabase = writableDatabase

        //mapeo  de las columnas con valores a insertar
        val valores = ContentValues() //clase que permite empezar agrupar la informacion para mapearlo en la tabla
        valores.put(COLUMNA_NOMBRE, datosUsuario.nombreUsuario)
        valores.put(COLUMNA_PASSWORD, datosUsuario.passwordUsuario)
        valores.put(COLUMNA_NIVEL, datosUsuario.nivelUsuario)



        try {
            // insertamos nueva fila en la tabla
            db.insert(NOMBRE_TABLA, null, valores)

            Toast.makeText(mContext, "Registro insertado", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {

            Toast.makeText(mContext, e.message, Toast.LENGTH_SHORT).show()

        }

        db.close()

    }

    fun borrarUsuario(userID: Int) : Boolean {

        val consulta = "Delete From $NOMBRE_TABLA where $COLUMNA_ID = $userID"
        val db: SQLiteDatabase = this.writableDatabase
        var resultado: Boolean = false

        try {
            //val cursoe: Int = db.delete(NOMBRE_TABLA, "$COLUMNA_ID = ?", arrayOf(userID.toString()))
            val cursor: Unit = db.execSQL(consulta)
            resultado = true


        } catch (e: Exception) {

           Log.e(ContentValues.TAG, "Error al borrar")

        }

        db.close()
        return resultado

    }

    fun actualizarUsuario(id: String, nombreUser: String, passUser: String, nivelUser: String): Boolean {

        val db: SQLiteDatabase = writableDatabase
        var resultado = false
        val valores = ContentValues()
        valores.put(COLUMNA_NOMBRE, nombreUser)
        valores.put(COLUMNA_PASSWORD, passUser)
        valores.put(COLUMNA_NIVEL, nivelUser)


        try {
            db.update(NOMBRE_TABLA,valores, "$COLUMNA_ID = ?", arrayOf(id))
            resultado = true


        } catch (e: Exception) {

            Log.e(ContentValues.TAG, "Error al actualizar")
            resultado = false

        }

        return resultado
    }


}