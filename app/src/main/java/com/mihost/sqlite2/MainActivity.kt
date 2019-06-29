package com.mihost.sqlite2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var handler: DBaseHandler

    companion object {

        lateinit var handler: DBaseHandler
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //iniciamos DB
        handler = DBaseHandler(this)

        verUsuarios()





        agregarUsuarios_btn.setOnClickListener {

            val agregarU = Intent(this, AgregarUsuarios::class.java)
            startActivity(agregarU )

        }
    }


    private fun verUsuarios() {

        val usuariosLista : ArrayList<Usuarios> = handler.obtenerUsuarios(this )

        println("======= array de registro de usuarios en la base ========")

        println(usuariosLista.joinToString())
        println("======= / ========")


        val adpatador = AdaptadorUsuarios(this, usuariosLista)
        listaRV.layoutManager = LinearLayoutManager(this)
        listaRV.adapter = adpatador

    }

    override fun onResume() {
        verUsuarios()
        super.onResume()
    }

}
